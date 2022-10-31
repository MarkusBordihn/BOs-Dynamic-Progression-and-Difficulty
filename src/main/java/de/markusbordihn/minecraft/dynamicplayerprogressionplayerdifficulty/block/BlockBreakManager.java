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

package de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.block;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.Constants;
import de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.data.PlayerDataManager;
import de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.data.WeaponClass;
import de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.data.WeaponClassData;

@EventBusSubscriber
public class BlockBreakManager {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  protected BlockBreakManager() {}

  @SubscribeEvent
  public static void handleLivingDeathEvent(BlockEvent.BreakEvent event) {
    if (event.getResult() == Result.DENY) {
      return;
    }

    // Update stats if source was player with weapon in hand.
    if (event.getPlayer() instanceof ServerPlayer serverPlayer) {
      ItemStack handItemStack = serverPlayer.getItemInHand(InteractionHand.MAIN_HAND);
      if (!handItemStack.isEmpty() && handItemStack.getItem() != null) {
        Item handItem = handItemStack.getItem();
        WeaponClass weaponClass = WeaponClassData.getWeaponClass(handItem);
        if (weaponClass != null) {
          BlockState blockState = event.getState();
          log.debug("[Block Damage {}] {} destroyed {} with {}", weaponClass, serverPlayer,
              blockState, handItem);
          PlayerDataManager.updatePlayer(serverPlayer);
        }
      }
    }
  }

}
