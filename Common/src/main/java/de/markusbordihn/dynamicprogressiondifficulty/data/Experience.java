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

import com.google.common.collect.Maps;
import de.markusbordihn.dynamicprogressiondifficulty.Constants;
import java.util.Map;
import net.minecraft.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Experience {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);
  private static final int minLevel = 1;
  private static final int maxLevel = 150;
  private static final int experienceFactor = 300;
  private static final float experienceFactorItems = 0.75f;
  private static final int hurtDamageLevelCap = 15;
  private static final int dealtDamageLevelCap = 15;
  private static final int dealtDamageReduction = 50;
  private static final int dealtDamageIncrease = 50;
  private static final int hurtDamageReduction = 50;
  private static final int hurtDamageIncrease = 100;
  private static Map<Integer, Integer> levelExperienceMap =
      Util.make(
          Maps.newHashMap(),
          hashMap -> {
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

  static {
    calculateLevelExperience();
  }

  protected Experience() {}

  public static void calculateLevelExperience() {
    log.info(
        "Calculations will use {} as general experience factor and {} as item experience factor ...",
        experienceFactor,
        experienceFactorItems);
    calculateLevelExperienceMap();
  }

  public static void calculateLevelExperienceMap() {
    log.info(
        "Calculate base experience level from {} to {} with exp. factor {}",
        minLevel,
        maxLevel,
        experienceFactor);
    int experience = 0;

    // Reset former calculations
    levelExperienceMap = Maps.newHashMap();

    // Min level doesn't need any calculate
    log.debug("level: {} | exp: {}", minLevel, experience);
    levelExperienceMap.put(minLevel, experience);

    // Calculate experience for the rest based on https://oldschool.runescape.wiki/w/Experience
    for (int level = minLevel; level < maxLevel; level++) {
      experience +=
          (int) Math.floor(0.25 * (level + experienceFactor * Math.pow(2.0, level / 7.0)));
      log.debug("level: {} | exp: {}", level + 1, experience);
      levelExperienceMap.put(level + 1, experience);
    }
  }

  public static int getMaxLevel() {
    return maxLevel;
  }

  public static float getExperienceFactorItems() {
    return experienceFactorItems;
  }

  public static Map<Integer, Integer> getLevelExperienceMap() {
    return levelExperienceMap;
  }

  public static int getExperienceForMinLevel() {
    return levelExperienceMap.getOrDefault(minLevel + 1, 83);
  }

  public static int getExperienceForMaxLevel() {
    return levelExperienceMap.getOrDefault(maxLevel, 14391123);
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

  public static int getLevelFromExperience(int experience) {

    // Check for min. level
    if (experience < getExperienceForMinLevel()) {
      return minLevel;
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
    if (level <= hurtDamageLevelCap) {
      return 1.0f
          - ((((float) ((hurtDamageLevelCap + 1) - level) / hurtDamageLevelCap)
                  * hurtDamageReduction)
              / 100f);
    } else {
      return 1.0f + ((((float) level / maxLevel) * hurtDamageIncrease) / 100f);
    }
  }

  public static float getDealtDamageAdjustment(int level) {
    if (level <= dealtDamageLevelCap) {
      return 1.0f
          + ((((float) ((dealtDamageLevelCap + 1) - level) / dealtDamageLevelCap)
                  * dealtDamageIncrease)
              / 100f);
    } else {
      return 1.0f - ((((float) level / maxLevel) * dealtDamageReduction) / 100f);
    }
  }
}
