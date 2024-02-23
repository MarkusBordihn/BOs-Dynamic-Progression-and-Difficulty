/*
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

package de.markusbordihn.dynamicprogressiondifficulty.entity;

import de.markusbordihn.dynamicprogressiondifficulty.Constants;
import de.markusbordihn.dynamicprogressiondifficulty.data.ItemClass;
import de.markusbordihn.dynamicprogressiondifficulty.data.ItemClassData;
import de.markusbordihn.dynamicprogressiondifficulty.data.PlayerStats;
import de.markusbordihn.dynamicprogressiondifficulty.data.PlayerStatsManager;
import de.markusbordihn.dynamicprogressiondifficulty.data.PlayerStatsUtils;
import de.markusbordihn.dynamicprogressiondifficulty.server.ServerEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LivingEntityEvents {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  private static final String UNKOWN_ENTITY = "unknown";

  private LivingEntityEvents() {}

  /*
   * @param livingEntity
   * @param damageSource
   * @param damageAmount
   * @return adjusted damage amount
   */
  public static float handleLivingHurtEvent(
      LivingEntity livingEntity, DamageSource damageSource, float damageAmount) {

    if (!(damageSource.getEntity() instanceof ServerPlayer)
        && !(livingEntity instanceof ServerPlayer)) {
      log.debug(
          "Untracked LivingEntity {} should get hurt by {}({}) with {} damage.",
          livingEntity.getName().getString(),
          damageSource.getMsgId(),
          damageSource.getEntity() != null
              ? damageSource.getEntity().getName().getString()
              : UNKOWN_ENTITY,
          damageAmount);
      return damageAmount;
    }

    // Handle dealt damage and durability modifications for players.
    if (damageSource.getEntity() instanceof ServerPlayer serverPlayer) {
      log.debug(
          "Server Player {} should deal {} damage to {}.",
          serverPlayer.getName().getString(),
          damageAmount,
          livingEntity.getName().getString());

      // Handle dealt mob and player modifications.
      PlayerStats playerStats = PlayerStatsManager.getPlayerStats(serverPlayer);
      if (playerStats != null) {
        if (!(livingEntity instanceof ServerPlayer)) {
          float mobDamageModifier = playerStats.getMobDealtDamageModifier();
          if (mobDamageModifier > 0) {
            log.debug(
                "[Mob Damage] {} from {} by {} to {}",
                serverPlayer,
                damageAmount,
                mobDamageModifier,
                damageAmount * mobDamageModifier);
            damageAmount = damageAmount * mobDamageModifier;
          }
        } else if (livingEntity instanceof ServerPlayer && ServerEvents.isPvpEnabled()) {
          float playerDamageModifier = playerStats.getPlayerDealtDamageModifier();
          if (playerDamageModifier > 0) {
            log.debug(
                "[Player Damage] {} from {} by {} to {}",
                serverPlayer,
                damageAmount,
                playerDamageModifier,
                damageAmount * playerDamageModifier);
            damageAmount = damageAmount * playerDamageModifier;
          }
        }
      }

      // Handle item class damage modifications.
      ItemClass itemClass = ItemClassData.getItemClassForServerPlayer(serverPlayer);
      if (itemClass != null && playerStats != null) {
        float damageModifier = playerStats.getDamageModifier(itemClass);
        if (damageModifier > 0) {
          log.debug(
              "[Item Damage {}] {} from {} by {} to {}",
              itemClass,
              serverPlayer,
              damageAmount,
              damageModifier,
              damageAmount * damageModifier);
          damageAmount = damageAmount * damageModifier;
        }

        // Handle durability modifications, which happen retroactively.
        PlayerStatsUtils.handleDurabilityModifier(serverPlayer, itemClass, playerStats);
      }
    }

    // Handle hurt damage for players.
    if (livingEntity instanceof ServerPlayer serverPlayer) {
      log.debug(
          "Server Player {} should get hurt by {} ({}) with {} damage.",
          serverPlayer.getName().getString(),
          damageSource.getMsgId(),
          damageSource.getEntity() != null
              ? damageSource.getEntity().getName().getString()
              : UNKOWN_ENTITY,
          damageAmount);

      // Get player stats.
      PlayerStats playerStats = PlayerStatsManager.getPlayerStats(serverPlayer);
      if (playerStats == null) {
        return damageAmount;
      }

      // Adjust hurt damage caused by mobs.
      if (!(damageSource.getEntity() instanceof ServerPlayer)
          && damageSource.getEntity() instanceof LivingEntity) {
        float mobDamageModifier = playerStats.getMobHurtDamageModifier();
        if (mobDamageModifier > 0) {
          log.debug(
              "[Mob Hurt Damage] {} from {} by {} to {}",
              serverPlayer,
              damageAmount,
              mobDamageModifier,
              damageAmount * mobDamageModifier);
          damageAmount = damageAmount * mobDamageModifier;
        }
      }

      // Adjust hurt damage caused by players.
      if (damageSource.getEntity() instanceof ServerPlayer && ServerEvents.isPvpEnabled()) {
        float playerDamageModifier = playerStats.getPlayerHurtDamageModifier();
        if (playerDamageModifier > 0) {
          log.debug(
              "[Player Hurt Damage] {} from {} by {} to {}",
              serverPlayer,
              damageAmount,
              playerDamageModifier,
              damageAmount * playerDamageModifier);
          damageAmount = damageAmount * playerDamageModifier;
        }
      }
    }

    return damageAmount;
  }

  /*
   * @param livingEntity
   * @param damageSource
   */
  public static void handleLivingDeathEvent(LivingEntity livingEntity, DamageSource damageSource) {
    log.debug(
        "LivingEntity {} died by {}({}).",
        livingEntity.getName().getString(),
        damageSource.getMsgId(),
        damageSource.getEntity() != null
            ? damageSource.getEntity().getName().getString()
            : UNKOWN_ENTITY);

    // Update stats if player died.
    if (livingEntity instanceof ServerPlayer serverPlayer) {
      PlayerStatsManager.updatePlayerStats(serverPlayer);
    }

    // Update stats if player killed entity.
    if (damageSource.getEntity() instanceof ServerPlayer serverPlayer) {
      PlayerStatsManager.updatePlayerStats(serverPlayer);
    }
  }
}
