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

package de.markusbordihn.dynamicprogressiondifficulty.data;

import de.markusbordihn.dynamicprogressiondifficulty.Constants;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.ServerStatsCounter;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerStatsUtils {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  private static final Map<String, Integer> playerItemStackDamageHistoryMap = new HashMap<>();
  private static final Map<String, Float> playerItemStackDurabilityStackCacheMap = new HashMap<>();
  private static final double DEALT_DAMAGE_MODIFIER = 0.25;

  protected PlayerStatsUtils() {}

  public static void updatePlayerStats(
      ServerStatsCounter serverStatsCounter, PlayerStats playerStats, ServerPlayer serverPlayer) {
    // General stats
    updatePlayerDamageStats(serverStatsCounter, playerStats);
    updatePlayerMiscStats(serverStatsCounter, playerStats);
    updatePlayerKillStats(serverStatsCounter, playerStats);
    updatePlayerRaidStats(serverStatsCounter, playerStats);
    updatePlayerEnchantStats(serverStatsCounter, playerStats);
    updatePlayerFishStats(serverStatsCounter, playerStats);

    // Internal stats
    updatePlayerLevelStats(playerStats);
    updatePlayerItemClassStats(serverStatsCounter, playerStats, serverPlayer);
  }

  public static void updatePlayerDamageStats(
      ServerStatsCounter serverStatsCounter, PlayerStats playerStats) {
    playerStats.set(
        PlayerStatsType.DAMAGE_ABSORBED,
        serverStatsCounter.getValue(Stats.CUSTOM, Stats.DAMAGE_ABSORBED));
    playerStats.set(
        PlayerStatsType.DAMAGE_BLOCKED_BY_SHIELD,
        serverStatsCounter.getValue(Stats.CUSTOM, Stats.DAMAGE_BLOCKED_BY_SHIELD));
    playerStats.set(
        PlayerStatsType.DAMAGE_DEALT,
        serverStatsCounter.getValue(Stats.CUSTOM, Stats.DAMAGE_DEALT) / 10);
    playerStats.set(
        PlayerStatsType.DAMAGE_DEALT_ABSORBED,
        serverStatsCounter.getValue(Stats.CUSTOM, Stats.DAMAGE_DEALT_ABSORBED));
    playerStats.set(
        PlayerStatsType.DAMAGE_DEALT_RESISTED,
        serverStatsCounter.getValue(Stats.CUSTOM, Stats.DAMAGE_DEALT_RESISTED));
    playerStats.set(
        PlayerStatsType.DAMAGE_RESISTED,
        serverStatsCounter.getValue(Stats.CUSTOM, Stats.DAMAGE_RESISTED));
    playerStats.set(
        PlayerStatsType.DAMAGE_TAKEN,
        serverStatsCounter.getValue(Stats.CUSTOM, Stats.DAMAGE_TAKEN));
    playerStats.set(
        PlayerStatsType.TARGET_HIT, serverStatsCounter.getValue(Stats.CUSTOM, Stats.TARGET_HIT));
  }

  public static void updatePlayerMiscStats(
      ServerStatsCounter serverStatsCounter, PlayerStats playerStats) {
    playerStats.set(
        PlayerStatsType.CROUCH_TIME, serverStatsCounter.getValue(Stats.CUSTOM, Stats.CROUCH_TIME));
  }

  public static void updatePlayerKillStats(
      ServerStatsCounter serverStatsCounter, PlayerStats playerStats) {
    playerStats.set(
        PlayerStatsType.DEATHS, serverStatsCounter.getValue(Stats.CUSTOM, Stats.DEATHS));
    playerStats.set(
        PlayerStatsType.MOB_KILLS, serverStatsCounter.getValue(Stats.CUSTOM, Stats.MOB_KILLS));
    playerStats.set(
        PlayerStatsType.ANIMALS_BRED,
        serverStatsCounter.getValue(Stats.CUSTOM, Stats.ANIMALS_BRED));
    playerStats.set(
        PlayerStatsType.PLAYER_KILLS,
        serverStatsCounter.getValue(Stats.CUSTOM, Stats.PLAYER_KILLS));
  }

  public static void updatePlayerRaidStats(
      ServerStatsCounter serverStatsCounter, PlayerStats playerStats) {
    playerStats.set(
        PlayerStatsType.RAID_TRIGGER,
        serverStatsCounter.getValue(Stats.CUSTOM, Stats.RAID_TRIGGER));
    playerStats.set(
        PlayerStatsType.RAID_WIN, serverStatsCounter.getValue(Stats.CUSTOM, Stats.RAID_WIN));
  }

  public static void updatePlayerEnchantStats(
      ServerStatsCounter serverStatsCounter, PlayerStats playerStats) {
    playerStats.set(
        PlayerStatsType.ENCHANT_ITEM,
        serverStatsCounter.getValue(Stats.CUSTOM, Stats.ENCHANT_ITEM));
  }

  public static void updatePlayerFishStats(
      ServerStatsCounter serverStatsCounter, PlayerStats playerStats) {
    playerStats.set(
        PlayerStatsType.FISH_CAUGHT, serverStatsCounter.getValue(Stats.CUSTOM, Stats.FISH_CAUGHT));
  }

  public static void updatePlayerLevelStats(PlayerStats playerStats) {
    int playerKills = playerStats.get(PlayerStatsType.PLAYER_KILLS);
    int mobKills = playerStats.get(PlayerStatsType.MOB_KILLS);
    int damageDealt = playerStats.get(PlayerStatsType.DAMAGE_DEALT);
    int playerDeaths = playerStats.get(PlayerStatsType.DEATHS);
    float damageBase = (float) playerKills + (float) mobKills;

    // Calculate experience for damage dealt and mob kills.
    int mobKillsPlayerDeathsRatio = (mobKills > playerDeaths) ? mobKills - playerDeaths : 0;
    int damageExperienceMob =
        (int)
            Math.round(
                (mobKillsPlayerDeathsRatio > 0)
                    ? damageDealt * DEALT_DAMAGE_MODIFIER * (mobKillsPlayerDeathsRatio / damageBase)
                    : 0);
    int damageLevelMob = Experience.getLevelFromExperience(damageExperienceMob);
    playerStats.set(PlayerStatsType.INTERNAL_DAMAGE_EXPERIENCE_MOB, damageExperienceMob);
    playerStats.set(PlayerStatsType.INTERNAL_DAMAGE_LEVEL_MOB, damageLevelMob);
    playerStats.set(
        PlayerStatsType.INTERNAL_DEALT_DAMAGE_ADJUSTMENT_MOB,
        Math.round(Experience.getDealtDamageAdjustment(damageLevelMob) * 100));
    playerStats.set(
        PlayerStatsType.INTERNAL_HURT_DAMAGE_ADJUSTMENT_MOB,
        Math.round(Experience.getHurtDamageAdjustment(damageLevelMob) * 100));

    // Calculate experience for damage dealt and player kills.
    int playerKillsDeathRatio = (playerKills > playerDeaths) ? playerKills - playerDeaths : 0;
    int damageExperiencePlayer =
        (int)
            Math.round(
                (playerKillsDeathRatio > 0)
                    ? damageDealt * DEALT_DAMAGE_MODIFIER * (playerKillsDeathRatio / damageBase)
                    : 0);
    int damageLevelPlayer = Experience.getLevelFromExperience(damageExperiencePlayer);
    playerStats.set(PlayerStatsType.INTERNAL_DAMAGE_EXPERIENCE_PLAYER, damageExperiencePlayer);
    playerStats.set(PlayerStatsType.INTERNAL_DAMAGE_LEVEL_PLAYER, damageLevelPlayer);
    playerStats.set(
        PlayerStatsType.INTERNAL_DEALT_DAMAGE_ADJUSTMENT_PLAYER,
        Math.round(Experience.getDealtDamageAdjustment(damageLevelPlayer) * 100));
    playerStats.set(
        PlayerStatsType.INTERNAL_HURT_DAMAGE_ADJUSTMENT_PLAYER,
        Math.round(Experience.getHurtDamageAdjustment(damageLevelPlayer) * 100));
  }

  public static void updatePlayerItemClassStats(
      ServerStatsCounter serverStatsCounter, PlayerStats playerStats, ServerPlayer serverPlayer) {
    int playerDeaths = playerStats.get(PlayerStatsType.DEATHS);
    float experienceFactorItems = Experience.getExperienceFactorItems();
    for (ItemClass itemClass : ItemClass.values()) {

      // Ignore disabled item classes
      if (!itemClass.isEnabled()) {
        continue;
      }

      // Get all items for the item class
      Set<Item> itemClassItems = ItemClassData.getItemClassItems(itemClass);
      int itemClassUsage = 0;
      for (Item item : itemClassItems) {
        itemClassUsage += serverStatsCounter.getValue(Stats.ITEM_USED.get(item));
      }

      // Calculate experience and level for item class usage.
      int itemClassUsageExperience =
          itemClassUsage > playerDeaths
              ? Math.round((itemClassUsage - playerDeaths) * experienceFactorItems)
              : 0;
      int itemClassUsageLevel = Experience.getLevelFromExperience(itemClassUsageExperience);

      // Update item class stats, if experience has changed.
      if (itemClassUsageExperience != playerStats.getExperience(itemClass)) {

        // Show level up message if level has changed and user has experience for the item class.
        if (playerStats.hasExperience(itemClass)) {
          handleItemClassLevelUp(serverPlayer, itemClass, itemClassUsageLevel, playerStats);
        }

        // Update item class stats, if usage is greater than 0.
        playerStats.setExperience(itemClass, itemClassUsageExperience);
        playerStats.setLevel(
            itemClass, Experience.getLevelFromExperience(itemClassUsageExperience));
      }
    }
  }

  public static void handleItemClassLevelUp(
      ServerPlayer serverPlayer,
      ItemClass itemClass,
      int itemClassUsageLevel,
      PlayerStats playerStats) {
    int lastItemClassLevel = playerStats.getLevel(itemClass);
    if (itemClassUsageLevel > lastItemClassLevel) {
      serverPlayer.sendMessage(
          new TranslatableComponent(
                  Constants.TEXT_PREFIX + ".level_up",
                  serverPlayer.getName(),
                  itemClass.getTranslatedText(),
                  lastItemClassLevel,
                  itemClassUsageLevel)
              .withStyle(ChatFormatting.GREEN),
          Util.NIL_UUID);
    } else if (itemClassUsageLevel < lastItemClassLevel) {
      serverPlayer.sendMessage(
          new TranslatableComponent(
                  Constants.TEXT_PREFIX + ".level_down",
                  serverPlayer.getName(),
                  itemClass.getTranslatedText(),
                  lastItemClassLevel,
                  itemClassUsageLevel)
              .withStyle(ChatFormatting.RED),
          Util.NIL_UUID);
    }
  }

  public static void handleDurabilityModifier(
      ServerPlayer serverPlayer, ItemClass itemClass, PlayerStats playerStats) {
    handleDurabilityModifier(
        serverPlayer,
        serverPlayer.getItemInHand(InteractionHand.MAIN_HAND),
        itemClass,
        playerStats);
  }

  public static void handleDurabilityModifier(
      ServerPlayer serverPlayer,
      ItemStack handItemStack,
      ItemClass itemClass,
      PlayerStats playerStats) {

    //  Validate item stack
    if (handItemStack == null || handItemStack.isEmpty()) {
      return;
    }

    // Validate item damage
    int itemDamage = handItemStack.getDamageValue();
    int maxItemDamage = handItemStack.getMaxDamage();
    if (itemDamage <= 0 || itemDamage > maxItemDamage) {
      return;
    }

    // Validate durability modifier
    float durabilityModifier = playerStats.getDurabilityModifier(itemClass);
    if (durabilityModifier == 0) {
      return;
    }

    // Validate item damage history
    String playerItemStackKey =
        serverPlayer.getUUID() + ":" + handItemStack.getItem().getDescriptionId();
    int itemDamageHistory = playerItemStackDamageHistoryMap.getOrDefault(playerItemStackKey, 0);
    if (itemDamageHistory < itemDamage && itemDamage + 10 < maxItemDamage) {
      float damageReduction = Math.max(0, (itemDamage - itemDamageHistory) * durabilityModifier);

      // Store small adjustments in stack cache to avoid unnecessary updates.
      if (damageReduction < 1.0f && damageReduction > 0.0f) {
        float formerItemStackDurabilityStackCache =
            playerItemStackDurabilityStackCacheMap.getOrDefault(playerItemStackKey, 0.0f);
        playerItemStackDurabilityStackCacheMap.put(
            playerItemStackKey, formerItemStackDurabilityStackCache + damageReduction);
      }

      // Check if we have a stack cache and apply it to the item damage, if greater than 1.0f.
      float itemStackDurabilityStack =
          playerItemStackDurabilityStackCacheMap.getOrDefault(playerItemStackKey, 0.0f);
      if (itemStackDurabilityStack >= 1.0f) {
        int itemDamageValue = Math.round(Math.max(0, itemDamage - itemStackDurabilityStack));
        log.debug(
            "[Item Durability Stack {}] {} {} ({}/{}) by {} stacked damage reduction and factor {} to {}",
            itemClass,
            serverPlayer,
            handItemStack,
            itemDamage,
            maxItemDamage,
            itemStackDurabilityStack,
            durabilityModifier,
            itemDamageValue);
        playerItemStackDurabilityStackCacheMap.put(playerItemStackKey, 0.0f);
        handItemStack.setDamageValue(itemDamageValue);
      } else if (itemStackDurabilityStack > 0.0f && damageReduction > 0.0f) {
        log.debug(
            "[Item Durability Stack {}] {} {} ({}/{}) stack {} durability to total of {}",
            itemClass,
            serverPlayer,
            handItemStack,
            itemDamage,
            maxItemDamage,
            damageReduction,
            itemStackDurabilityStack);
      } else if (damageReduction > 1f) {
        int itemDamageValue = Math.round(Math.max(0, itemDamage - damageReduction));
        log.debug(
            "[Item Durability {}] {} {} ({}/{}) by factor {} to {}",
            itemClass,
            serverPlayer,
            handItemStack,
            itemDamage,
            maxItemDamage,
            durabilityModifier,
            itemDamageValue);
        handItemStack.setDamageValue(itemDamageValue);
      }
    } else {
      log.debug(
          "[Item Durability {}] Not enough damage history for {} {}.",
          itemClass,
          serverPlayer,
          handItemStack);
    }
    playerItemStackDamageHistoryMap.put(playerItemStackKey, handItemStack.getDamageValue());
  }
}
