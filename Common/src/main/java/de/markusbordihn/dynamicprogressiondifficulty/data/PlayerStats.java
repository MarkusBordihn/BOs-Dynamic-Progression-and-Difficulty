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

import java.util.EnumMap;
import java.util.Map;
import net.minecraft.nbt.CompoundTag;

public class PlayerStats {

  private static final String TAG_PLAYER_STATS = "PlayerStats";
  private static final String TAG_ITEM_CLASS = "ItemClass";
  private static final String TAG_EXPERIENCE = "Experience";
  private static final String TAG_LEVEL = "Level";

  private final EnumMap<PlayerStatsType, Integer> playerStatsMap =
      new EnumMap<>(PlayerStatsType.class);
  private final EnumMap<ItemClass, Integer> itemClassExperienceMap = new EnumMap<>(ItemClass.class);
  private final EnumMap<ItemClass, Integer> itemClassLevelMap = new EnumMap<>(ItemClass.class);

  public PlayerStats() {}

  public PlayerStats(CompoundTag compoundTag) {
    load(compoundTag);
  }

  public Map<PlayerStatsType, Integer> getStatsMap() {
    return this.playerStatsMap;
  }

  public Integer get(PlayerStatsType playerStatsType) {
    return this.playerStatsMap.getOrDefault(playerStatsType, 0);
  }

  public void set(PlayerStatsType playerStatsType, Integer value) {
    this.playerStatsMap.put(playerStatsType, value);
  }

  public void setExperience(ItemClass itemClass, Integer experience) {
    this.itemClassExperienceMap.put(itemClass, experience);
  }

  public Integer getExperience(ItemClass itemClass) {
    return this.itemClassExperienceMap.getOrDefault(itemClass, 0);
  }

  public boolean hasExperience(ItemClass itemClass) {
    return this.itemClassExperienceMap.containsKey(itemClass);
  }

  public void setLevel(ItemClass itemClass, Integer level) {
    this.itemClassLevelMap.put(itemClass, level);
  }

  public Integer getLevel(ItemClass itemClass) {
    return this.itemClassLevelMap.getOrDefault(itemClass, 1);
  }

  public float getMobDealtDamageModifier() {
    return get(PlayerStatsType.INTERNAL_DEALT_DAMAGE_ADJUSTMENT_MOB) / 100f;
  }

  public float getPlayerDealtDamageModifier() {
    return get(PlayerStatsType.INTERNAL_DEALT_DAMAGE_ADJUSTMENT_PLAYER) / 100f;
  }

  public float getMobHurtDamageModifier() {
    return get(PlayerStatsType.INTERNAL_HURT_DAMAGE_ADJUSTMENT_MOB) / 100f;
  }

  public float getPlayerHurtDamageModifier() {
    return get(PlayerStatsType.INTERNAL_HURT_DAMAGE_ADJUSTMENT_PLAYER) / 100f;
  }

  public float getDamageModifier(ItemClass itemClass) {
    if (itemClass == null || !itemClass.isEnabled()) {
      return 0f;
    }
    int level = getLevel(itemClass);
    float damageModifier = itemClass.getDamageIncrease();
    return damageModifier == 0 || level == 1
        ? 0f
        : 1.0f + (((float) level / Experience.getMaxLevel()) * damageModifier);
  }

  public float getDurabilityModifier(ItemClass itemClass) {
    if (itemClass == null || !itemClass.isEnabled()) {
      return 0f;
    }
    int level = getLevel(itemClass);
    float durabilityModifier = itemClass.getDurabilityIncrease();
    return durabilityModifier == 0 || level == 1
        ? 0f
        : (((float) level / Experience.getMaxLevel()) * durabilityModifier);
  }

  public void load(CompoundTag compoundTag) {
    // Loading player stats
    CompoundTag playerStatsTag = compoundTag.getCompound(TAG_PLAYER_STATS);
    for (PlayerStatsType playerStatsType : PlayerStatsType.values()) {
      if (playerStatsTag.contains(playerStatsType.name())) {
        playerStatsMap.put(playerStatsType, playerStatsTag.getInt(playerStatsType.name()));
      }
    }

    // Loading item class stats like level and experience
    CompoundTag itemClassTag = compoundTag.getCompound(TAG_ITEM_CLASS);
    for (ItemClass itemClass : ItemClass.values()) {
      if (itemClassTag.contains(itemClass.name())) {
        CompoundTag itemClassCompoundTag = itemClassTag.getCompound(itemClass.name());
        if (itemClassCompoundTag.contains(TAG_EXPERIENCE)) {
          itemClassExperienceMap.put(itemClass, itemClassCompoundTag.getInt(TAG_EXPERIENCE));
        }
        if (itemClassCompoundTag.contains(TAG_LEVEL)) {
          itemClassLevelMap.put(itemClass, itemClassCompoundTag.getInt(TAG_LEVEL));
        }
      }
    }
  }

  public CompoundTag save(CompoundTag compoundTag) {
    // Saving player stats
    CompoundTag playerStatsTag = new CompoundTag();
    for (var playerStatsEntry : playerStatsMap.entrySet()) {
      playerStatsTag.putInt(playerStatsEntry.getKey().name(), playerStatsEntry.getValue());
    }
    compoundTag.put(TAG_PLAYER_STATS, playerStatsTag);

    // Saving item class stats like level and experience
    CompoundTag itemClassTag = new CompoundTag();
    for (var itemClassEntry : itemClassExperienceMap.entrySet()) {
      int itemClassExperience = itemClassEntry.getValue();
      if (itemClassExperience > 0) {
        CompoundTag itemClassCompoundTag = new CompoundTag();
        itemClassCompoundTag.putInt(TAG_EXPERIENCE, itemClassExperience);
        itemClassCompoundTag.putInt(TAG_LEVEL, itemClassLevelMap.get(itemClassEntry.getKey()));
        itemClassTag.put(itemClassEntry.getKey().name(), itemClassCompoundTag);
      }
    }
    compoundTag.put(TAG_ITEM_CLASS, itemClassTag);

    return compoundTag;
  }

  public CompoundTag createTag() {
    return this.save(new CompoundTag());
  }
}
