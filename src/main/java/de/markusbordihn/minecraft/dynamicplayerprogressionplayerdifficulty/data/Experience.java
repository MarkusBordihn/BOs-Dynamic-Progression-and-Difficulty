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

package de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.data;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Maps;

import net.minecraft.Util;

import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.Constants;
import de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.config.CommonConfig;

@EventBusSubscriber
public class Experience {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  private static final CommonConfig.Config COMMON = CommonConfig.COMMON;

  private static Map<Integer, Integer> levelExperienceMap =
      Util.make(Maps.newHashMap(), hashMap -> {
        hashMap.put(1, 1);
        hashMap.put(2, 4);
        hashMap.put(3, 12);
        hashMap.put(4, 24);
        hashMap.put(5, 40);
        hashMap.put(6, 60);
        hashMap.put(7, 84);
        hashMap.put(8, 112);
        hashMap.put(9, 144);
        hashMap.put(10, 180);
      });

  private static int minLevel = 1;
  private static int maxLevel = 100;
  private static int experienceFactor = 300;
  private static float experienceFactorItems = 1.25f;

  protected Experience() {}

  @SubscribeEvent
  public static void handleServerAboutToStartEvent(ServerAboutToStartEvent event) {
    maxLevel = COMMON.levelMax.get();
    experienceFactor = COMMON.levelFactor.get();
    if (COMMON.levelFactorItems.get() > 0) {
      experienceFactorItems = COMMON.levelFactorItems.get() / 100.0f;
    }
    log.info(
        "Calculations will use {} as general experience factor and {} as item experience factor ...",
        experienceFactor, experienceFactorItems);
    calculateLevelExperienceMap();
  }

  public static void calculateLevelExperienceMap() {
    log.info("Calculate base experience level from {} to {} with exp. factor {}", minLevel,
        maxLevel, experienceFactor);
    int experience = 0;

    // Reset former calculations
    levelExperienceMap = Maps.newHashMap();

    // Min level doesn't need any calculate
    log.debug("level: {} | exp: 0", minLevel, experience);
    levelExperienceMap.put(minLevel, experience);

    // Calculate experience for the rest based on https://oldschool.runescape.wiki/w/Experience
    for (int level = minLevel; level < maxLevel; level++) {
      experience += Math.floor(0.25 * (level + experienceFactor * Math.pow(2.0, level / 7.0)));
      log.debug("level: {} | exp: {}", level + 1, experience);
      levelExperienceMap.put(level + 1, experience);
    }
  }

  public static int getMinLevel() {
    return minLevel;
  }

  public static int getMaxLevel() {
    return maxLevel;
  }

  public static int getExperienceFactor() {
    return experienceFactor;
  }

  public static float getExperienceFactorItems() {
    return experienceFactorItems;
  }

  public static Map<Integer, Integer> getLevelExperienceMap() {
    return levelExperienceMap;
  }

  public static int getExperienceForMinLevel() {
    return levelExperienceMap.getOrDefault(minLevel + 1, null);
  }

  public static int getExperienceForMaxLevel() {
    return levelExperienceMap.getOrDefault(maxLevel, null);
  }

  public static int getExperienceForLevel(int level) {
    return levelExperienceMap.getOrDefault(level, null);
  }

  public static int getExperienceForNextLevel(int level) {
    if (level < maxLevel) {
      return levelExperienceMap.getOrDefault(level + 1, null);
    }
    return levelExperienceMap.get(maxLevel);
  }

  public static int getExperienceForPreviousLevel(int level) {
    if (level > 1 && level <= maxLevel) {
      return levelExperienceMap.get(level - 1);
    }
    return levelExperienceMap.get(minLevel);
  }

  public static int getExperienceDifferenceForLevel(int level) {
    if (level > 1) {
      return levelExperienceMap.getOrDefault(level, 0)
          - levelExperienceMap.getOrDefault(level - 1, 0);
    }
    return 0;
  }

  public static int getExperienceDeathPenalty() {
    return COMMON.levelExperienceDeathPenalty.get();
  }

  public static int getExperienceDeathPenaltyItems() {
    return COMMON.levelExperienceDeathPenaltyItems.get();
  }

  public static int getLevelFromExperience(int experience) {
    return getLevelFromExperience(experience, 0);
  }

  public static int getLevelFromExperience(int experience, int adjustments) {

    // Check for min. level
    if (experience < getExperienceForMinLevel()) {
      return minLevel;
    }

    // Consider adjustments. if needed.
    if (adjustments != 0) {
      experience = Math.max(experience - adjustments, 0);
    }

    // Check for max. level
    if (experience >= getExperienceForMaxLevel()) {
      return maxLevel;
    }

    // Check all other levels
    for (int level = minLevel + 1; level < maxLevel; level++) {
      if (getExperienceForLevel(level) > experience) {
        return level - 1;
      }
    }

    return 1;
  }

  public static float getHurtDamageAdjustment(int level) {
    int levelCap = COMMON.hurtDamageLevelCap.get();
    if (level <= levelCap) {
      int hurtDamageReduction = COMMON.hurtDamageReduction.get();
      return hurtDamageReduction == 0 ? 0.0f
          : 1.0f - ((((float) ((levelCap + 1) - level) / levelCap) * hurtDamageReduction) / 100f);
    } else {
      int hurtDamageIncrease = COMMON.hurtDamageIncrease.get();
      return hurtDamageIncrease == 0 ? 0.0f
          : 1.0f + ((((float) level / maxLevel) * hurtDamageIncrease) / 100f);
    }
  }

  public static float getDealtDamageAdjustment(int level) {
    int levelCap = COMMON.dealtDamageLevelCap.get();
    if (level <= levelCap) {
      int dealtDamageIncrease = COMMON.dealtDamageIncrease.get();
      return dealtDamageIncrease == 0 ? 0.0f
          : 1.0f + ((((float) ((levelCap + 1) - level) / levelCap) * dealtDamageIncrease) / 100f);
    } else {
      int dealtDamageReduction = COMMON.dealtDamageReduction.get();
      return dealtDamageReduction == 0 ? 0.0f
          : 1.0f - ((((float) level / maxLevel) * dealtDamageReduction) / 100f);
    }
  }

  public static float getItemDamageAdjustmentAxe(int level) {
    int adjustment = COMMON.axeItemDamageIncrease.get();
    return adjustment == 0 || level == 1 ? 0.0f
        : 1.0f + ((((float) level / maxLevel) * adjustment) / 100f);
  }

  public static float getItemDamageAdjustmentBow(int level) {
    int adjustment = COMMON.bowItemDamageIncrease.get();
    return adjustment == 0 || level == 1 ? 0.0f
        : 1.0f + ((((float) level / maxLevel) * adjustment) / 100f);
  }

  public static float getItemDamageAdjustmentCrossbow(int level) {
    int adjustment = COMMON.crossbowItemDamageIncrease.get();
    return adjustment == 0 || level == 1 ? 0.0f
        : 1.0f + ((((float) level / maxLevel) * adjustment) / 100f);
  }

  public static float getItemDamageAdjustmentPickaxe(int level) {
    int adjustment = COMMON.pickaxeItemDamageIncrease.get();
    return adjustment == 0 || level == 1 ? 0.0f
        : 1.0f + ((((float) level / maxLevel) * adjustment) / 100f);
  }

  public static float getItemDamageAdjustmentShield(int level) {
    int adjustment = COMMON.shieldItemDamageIncrease.get();
    return adjustment == 0 || level == 1 ? 0.0f
        : 1.0f + ((((float) level / maxLevel) * adjustment) / 100f);
  }

  public static float getItemDamageAdjustmentSword(int level) {
    int adjustment = COMMON.swordItemDamageIncrease.get();
    return adjustment == 0 || level == 1 ? 0.0f
        : 1.0f + ((((float) level / maxLevel) * adjustment) / 100f);
  }

  public static float getItemDamageIncreaseAxe() {
    return COMMON.axeItemDamageIncrease.get();
  }

  public static float getItemDamageIncreaseBow() {
    return COMMON.bowItemDamageIncrease.get();
  }

  public static float getItemDamageIncreaseCrossbow() {
    return COMMON.crossbowItemDamageIncrease.get();
  }

  public static float getItemDamageIncreasePickaxe() {
    return COMMON.pickaxeItemDamageIncrease.get();
  }

  public static float getItemDamageIncreaseShield() {
    return COMMON.shieldItemDamageIncrease.get();
  }

  public static float getItemDamageIncreaseSword() {
    return COMMON.swordItemDamageIncrease.get();
  }

}
