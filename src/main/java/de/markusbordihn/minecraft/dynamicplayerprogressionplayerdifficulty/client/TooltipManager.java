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

package de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.client;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.Constants;
import de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.data.Experience;
import de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.data.PlayerData;
import de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.data.PlayerDataManager;
import de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.data.WeaponClass;
import de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.data.WeaponClassData;

@EventBusSubscriber(value = Dist.CLIENT)
public class TooltipManager {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  protected TooltipManager() {}

  @SubscribeEvent
  public static void onItemTooltipEvent(ItemTooltipEvent event) {

    ItemStack itemStack = event.getItemStack();
    if (itemStack.isEmpty()) {
      return;
    }

    Item item = itemStack.getItem();
    List<Component> tooltip = event.getToolTip();
    WeaponClass weaponClass = WeaponClassData.getWeaponClass(item);
    int index = 1;
    if (weaponClass != null && WeaponClassData.isWeaponClassEnabled(weaponClass)) {
      tooltip.add(index++, formatWeaponClass(weaponClass));
      PlayerData playerData = PlayerDataManager.getLocalPlayer();
      if (playerData != null) {
        int experiencePenaltyWeaponClass = playerData.getExperiencePenaltyWeaponClass();
        int weaponClassExperience = playerData.getWeaponClassExperience(weaponClass);
        int weaponClassExperienceBase = playerData.getWeaponClassExperienceBase(weaponClass);
        int weaponClassLevel = playerData.getWeaponClassLevel(weaponClass);

        // Display 0 experience with death penalty, to make it more visible for the user.
        if (weaponClassExperience == 0 && experiencePenaltyWeaponClass > 0
            && weaponClassExperienceBase - experiencePenaltyWeaponClass < 0) {
          tooltip.add(index++, formatLevel(weaponClassLevel).append(formatExperienceWithPenalty(
              weaponClassExperienceBase - experiencePenaltyWeaponClass, weaponClassLevel)));
        } else {
          tooltip.add(index++, formatLevel(weaponClassLevel)
              .append(formatExperience(weaponClassExperience, weaponClassLevel)));
        }

        // Display item damage adjustments
        float itemDamageAdjustment = playerData.getWeaponClassDamageAdjustment(weaponClass);
        if (itemDamageAdjustment > 0.0f) {
          tooltip.add(index++, formatDamageAdjustment(itemDamageAdjustment));
        }

        // Display item durability adjustments
        float itemDurabilityAdjustment = playerData.getWeaponClassDurabilityAdjustment(weaponClass);
        if (itemDurabilityAdjustment > 0.0f) {
          tooltip.add(index++, formatDurabilityAdjustment(itemDurabilityAdjustment));
        }
      }
    }
  }

  private static MutableComponent formatWeaponClass(WeaponClass weaponClass) {
    return Component.translatable(Constants.CLASS_TEXT_PREFIX,
        weaponClass.text.withStyle(ChatFormatting.BLUE), Component.literal(weaponClass.textIcon))
        .withStyle(ChatFormatting.GRAY);
  }

  private static MutableComponent formatLevel(int level) {
    return Component
        .translatable(Constants.TOOLTIP_TEXT_PREFIX + "level", level, Experience.getMaxLevel())
        .withStyle(ChatFormatting.YELLOW);
  }

  private static MutableComponent formatDamageAdjustment(float attackDamage) {
    return Component.translatable(Constants.TOOLTIP_TEXT_PREFIX + "attack_damage", Component
        .literal(
            String.format("+%.2f%%", attackDamage > 1 ? (attackDamage - 1) * 100 : attackDamage))
        .withStyle(ChatFormatting.GREEN)).withStyle(ChatFormatting.GRAY);
  }

  private static MutableComponent formatExperience(int experience, int level) {
    int experienceNextLevel = Experience.getExperienceForNextLevel(level) - 1;
    return Component.translatable(Constants.TOOLTIP_TEXT_PREFIX + "level_experience", experience,
        experienceNextLevel).withStyle(ChatFormatting.DARK_GREEN);
  }

  private static MutableComponent formatExperienceWithPenalty(int experience, int level) {
    int experienceNextLevel = Experience.getExperienceForNextLevel(level) - 1;
    return Component.translatable(Constants.TOOLTIP_TEXT_PREFIX + "level_experience",
        Component.literal(experience + "☠").withStyle(ChatFormatting.DARK_RED), experienceNextLevel)
        .withStyle(ChatFormatting.DARK_GREEN);
  }

  private static MutableComponent formatDurabilityAdjustment(float durability) {
    return Component.translatable(Constants.TOOLTIP_TEXT_PREFIX + "durability",
        Component
            .literal(String.format("+%.2f%%", durability > 1 ? (durability - 1) * 100 : durability))
            .withStyle(ChatFormatting.GREEN))
        .withStyle(ChatFormatting.GRAY);
  }

}
