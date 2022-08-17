/**
 * Copyright 2022 Markus Bordihn
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.item;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.Constants;
import de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.data.PlayerData;
import de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.data.PlayerDataManager;
import de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.data.WeaponClass;
import de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.data.WeaponClassData;

@EventBusSubscriber
public class WeaponAdjustmentManager {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  private static Map<ItemStack, Integer> damageHistory = new HashMap<>();
  private static Map<ItemStack, Float> damageStackCache = new HashMap<>();

  protected WeaponAdjustmentManager() {}

  @SubscribeEvent(priority = EventPriority.LOWEST)
  public static void handleLivingDamageEvent(LivingDamageEvent event) {
    DamageSource damageSource = event.getSource();

    // We only care about damage caused by server players for now.
    if (damageSource.getEntity() instanceof ServerPlayer serverPlayer) {
      PlayerData playerData = PlayerDataManager.getPlayer(serverPlayer);
      if (playerData != null) {
        float lastAttackDamage = event.getAmount();
        float attackDamage = lastAttackDamage;
        ItemStack handItemStack = serverPlayer.getItemInHand(InteractionHand.MAIN_HAND);
        if (!handItemStack.isEmpty() && handItemStack.getItem() != null) {
          Item handItem = handItemStack.getItem();

          // Modification per weapon class
          WeaponClass weaponClass = WeaponClassData.getWeaponClass(handItem);
          if (weaponClass != null) {

            // Simple damage adjustments, if any.
            float damageAdjustment = playerData.getWeaponClassDamageAdjustment(weaponClass);
            if (damageAdjustment > 0.0f) {
              attackDamage = attackDamage * damageAdjustment;
              log.debug("[Item Damage {}] {} from {} by {} to {}", weaponClass, serverPlayer,
                  lastAttackDamage, damageAdjustment, attackDamage);
            }

            // More complex durability adjustments, if any.
            float durabilityAdjustment =
                playerData.getWeaponClassDurabilityAdjustment(weaponClass) - 1;
            if (durabilityAdjustment > 0.0f) {
              int itemDamage = handItem.getDamage(handItemStack);
              int maxItemDamage = handItem.getMaxDamage(handItemStack);
              int itemDamageHistory = damageHistory.getOrDefault(handItemStack, itemDamage);

              // In some cases we don't want to adjust the item damage to make sure the item could
              // still get broken and will not live forever.
              if (itemDamage > 0 && itemDamageHistory < itemDamage
                  && itemDamage + 10 < maxItemDamage) {
                float damageReduction = (itemDamage - itemDamageHistory) * durabilityAdjustment;
                int adjustedItemDamage = Math
                    .round(itemDamage - ((itemDamage - itemDamageHistory) * durabilityAdjustment));

                // Store small adjustments in damage stack so that they get could be used later.
                if (damageReduction < 1.0f) {
                  damageStackCache.put(handItemStack,
                      damageStackCache.getOrDefault(handItemStack, 0.0f) + damageReduction);
                }

                // Check for damage stack values and use them if we have more than 1.0f.
                float itemDamageReductionStack = damageStackCache.get(handItemStack);
                if (itemDamageReductionStack >= 1.0f) {
                  adjustedItemDamage = adjustedItemDamage - Math.round(itemDamageReductionStack);
                  log.debug(
                      "[Item Durability {}] {} {} from {}/{} by {} stacked damage reduction and factor {} to {}",
                      weaponClass, serverPlayer, handItem, itemDamage, maxItemDamage,
                      itemDamageReductionStack, durabilityAdjustment, adjustedItemDamage);
                  damageStackCache.put(handItemStack, 0.0f);
                  handItem.setDamage(handItemStack, Math.max(0, adjustedItemDamage));
                } else if (itemDamageReductionStack > 0.0f && damageReduction > 0.0f) {
                  log.debug("[Item Durability Stack {}] {} {} stack {} durability to total of {}",
                      weaponClass, serverPlayer, handItem, damageReduction,
                      itemDamageReductionStack);
                } else if (adjustedItemDamage > 0 && adjustedItemDamage != itemDamage) {
                  log.debug("[Item Durability {}] {} {} from {}/{} by factor {} to {}", weaponClass,
                      serverPlayer, handItem, itemDamage, maxItemDamage, durabilityAdjustment,
                      adjustedItemDamage);
                  handItem.setDamage(handItemStack, Math.max(0, adjustedItemDamage));
                }
              }
              damageHistory.put(handItemStack, handItem.getDamage(handItemStack));
            }

          } else {
            log.debug("[Unknown Weapon Class] {} is unknown.", handItem);
          }

          // Update last attack damage for clear attack damage chain calculation.
          lastAttackDamage = attackDamage;
        }

        // Adjust attack damage, if there is any different
        if (event.getAmount() != attackDamage) {
          event.setAmount(attackDamage);
        }
      }
    }
  }

}
