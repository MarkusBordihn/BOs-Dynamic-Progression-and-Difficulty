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

import java.util.EnumMap;
import java.util.Set;
import java.util.UUID;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.ServerStatsCounter;
import net.minecraft.stats.StatType;
import net.minecraft.stats.Stats;
import net.minecraft.world.item.Item;

import de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.Constants;
import de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.config.CommonConfig;

public class PlayerData {

  private static final CommonConfig.Config COMMON = CommonConfig.COMMON;

  // Tag definitions
  public static final String EXPERIENCE_PENALTY_GENERAL_TAG = "ExperiencePenaltyGeneral";
  public static final String EXPERIENCE_PENALTY_WEAPON_CLASS_TAG = "ExperiencePenaltyWeaponClass";

  public static final String DAMAGE_EXPERIENCE_MOB_TAG = "DamageExperienceMob";
  public static final String DAMAGE_EXPERIENCE_PLAYER_TAG = "DamageExperiencePlayer";
  public static final String DAMAGE_LEVEL_MOB_TAG = "DamageLevelMob";
  public static final String DAMAGE_LEVEL_PLAYER_TAG = "DamageLevelPlayer";
  public static final String DEALT_DAMAGE_ADJUSTMENT_MOB_TAG = "DealtDamageAdjustmentMob";
  public static final String DEALT_DAMAGE_ADJUSTMENT_PLAYER_TAG = "DealtDamageAdjustmentPlayer";
  public static final String HURT_DAMAGE_ADJUSTMENT_MOB_TAG = "HurtDamageAdjustmentMob";
  public static final String HURT_DAMAGE_ADJUSTMENT_PLAYER_TAG = "HurtDamageAdjustmentPlayer";

  public static final String WEAPON_CLASSES_TAG = "WeaponClasses";
  public static final String WEAPON_CLASS_TAG = "WeaponClass";
  public static final String EXPERIENCE_TAG = "Experience";
  public static final String LEVEL_TAG = "Level";
  public static final String DAMAGE_ADJUSTMENT = "DamageAdjustment";
  public static final String DURABILITY_ADJUSTMENT = "DurabilityAdjustment";

  public static final String KILLS_MOB_TAG = "KillsMob";
  public static final String KILLS_PLAYER_TAG = "KillsPlayer";
  public static final String PLAYER_DEATHS_TAG = "PlayerDeaths";
  public static final String NAME_TAG = "Name";
  public static final String UUID_TAG = "UUID";

  private ServerPlayer player;
  private ServerStatsCounter stats;

  private String username;
  private UUID userUUID;

  // General stats
  private float damageAbsorbed = 0;
  private float damageBlockedByShield = 0;
  private float damageDealt = 0;
  private float damageDealtAbsorbed = 0;
  private float damageDealtResisted = 0;
  private float damageResisted = 0;
  private float damageTaken = 0;
  private float sneakTime = 0;
  private int mobKills = 0;
  private int numberOfDeaths = 0;
  private int playerKills = 0;
  private int raidsTriggered = 0;
  private int raidsWon = 0;
  private int targetsHit = 0;

  // Specific stats experience
  private int damageExperienceMob = 0;
  private int damageExperiencePlayer = 0;
  private int damageExperienceMobBase = 0;
  private int damageExperiencePlayerBase = 0;

  // Penalties
  private int experiencePenaltyGeneral = 0;
  private int experiencePenaltyWeaponClass = 0;

  // Specific stats level
  private int damageLevelMob = 1;
  private int damageLevelPlayer = 1;

  // Weapon Class
  private EnumMap<WeaponClass, Integer> weaponClassExperienceMap = new EnumMap<>(WeaponClass.class);
  private EnumMap<WeaponClass, Integer> weaponClassExperienceBaseMap =
      new EnumMap<>(WeaponClass.class);
  private EnumMap<WeaponClass, Integer> weaponClassLevelMap = new EnumMap<>(WeaponClass.class);
  private EnumMap<WeaponClass, Float> weaponClassDamageAdjustmentMap =
      new EnumMap<>(WeaponClass.class);
  private EnumMap<WeaponClass, Float> weaponClassDurabilityAdjustmentMap =
      new EnumMap<>(WeaponClass.class);

  // Weapon Class Cache for level up messages
  private EnumMap<WeaponClass, Integer> weaponClassLevelHistoryMap =
      new EnumMap<>(WeaponClass.class);

  // Player adjustments
  private float dealtDamageAdjustmentMob = 0;
  private float dealtDamageAdjustmentPlayer = 0;
  private float hurtDamageAdjustmentMob = 0;
  private float hurtDamageAdjustmentPlayer = 0;

  public PlayerData() {}

  public PlayerData(CompoundTag compoundTag) {
    if (compoundTag == null || compoundTag.isEmpty()) {
      return;
    }
    load(compoundTag);
  }

  public PlayerData(ServerPlayer player) {
    if (player == null) {
      return;
    }
    this.player = player;
    this.username = player.getName().getString();
    this.userUUID = player.getUUID();
    this.stats = player.getStats();
    updateStats();
  }

  public void updateStats() {
    if (this.stats == null && this.player != null) {
      this.stats = player.getStats();
    }
    updateStats(this.stats);
  }

  public void updateStats(ServerStatsCounter stats) {
    if (stats == null) {
      return;
    }

    // Stats which needs conversions
    this.damageAbsorbed = getStatsFloatValue(stats, Stats.CUSTOM, Stats.DAMAGE_ABSORBED);
    this.damageBlockedByShield =
        getStatsFloatValue(stats, Stats.CUSTOM, Stats.DAMAGE_BLOCKED_BY_SHIELD);
    this.damageDealt = getStatsFloatValue(stats, Stats.CUSTOM, Stats.DAMAGE_DEALT) / 10;
    this.damageDealtAbsorbed = getStatsFloatValue(stats, Stats.CUSTOM, Stats.DAMAGE_DEALT_ABSORBED);
    this.damageDealtResisted = getStatsFloatValue(stats, Stats.CUSTOM, Stats.DAMAGE_DEALT_RESISTED);
    this.damageResisted = getStatsFloatValue(stats, Stats.CUSTOM, Stats.DAMAGE_RESISTED);
    this.damageTaken = getStatsFloatValue(stats, Stats.CUSTOM, Stats.DAMAGE_TAKEN);
    this.sneakTime = getStatsFloatValue(stats, Stats.CUSTOM, Stats.CROUCH_TIME);

    // Stats without conversion
    this.mobKills = stats.getValue(Stats.CUSTOM, Stats.MOB_KILLS);
    this.numberOfDeaths = stats.getValue(Stats.CUSTOM, Stats.DEATHS);
    this.playerKills = stats.getValue(Stats.CUSTOM, Stats.PLAYER_KILLS);
    this.raidsTriggered = stats.getValue(Stats.CUSTOM, Stats.RAID_TRIGGER);
    this.raidsWon = stats.getValue(Stats.CUSTOM, Stats.RAID_WIN);
    this.targetsHit = stats.getValue(Stats.CUSTOM, Stats.TARGET_HIT);

    // Calculate base damage experience
    double damageBase = this.playerKills + (double) this.mobKills;
    this.damageExperienceMobBase =
        (int) Math.round(this.damageDealt * (this.mobKills / damageBase));
    this.damageExperiencePlayerBase =
        (int) Math.round(this.damageDealt * (this.playerKills / damageBase));

    // Calculate damage experience including any kind of penalty.
    this.experiencePenaltyGeneral = this.numberOfDeaths * Experience.getExperienceDeathPenalty();
    this.damageExperienceMob = this.damageExperienceMobBase;
    this.damageExperiencePlayer = this.damageExperiencePlayerBase;
    if (this.experiencePenaltyGeneral > 0) {
      this.damageExperienceMob =
          Math.max(this.damageExperienceMobBase - this.experiencePenaltyGeneral, 0);
      this.damageExperiencePlayer =
          Math.max(this.damageExperiencePlayerBase - this.experiencePenaltyGeneral, 0);
    }

    // Calculate levels with death penalty
    this.damageLevelMob = Experience.getLevelFromExperience(this.damageExperienceMob);
    this.damageLevelPlayer = Experience.getLevelFromExperience(this.damageExperiencePlayer);

    // Calculate player adjustments
    this.dealtDamageAdjustmentMob = Experience.getDealtDamageAdjustment(this.damageLevelMob);
    this.dealtDamageAdjustmentPlayer = Experience.getDealtDamageAdjustment(this.damageLevelPlayer);
    this.hurtDamageAdjustmentMob = Experience.getHurtDamageAdjustment(this.damageLevelMob);
    this.hurtDamageAdjustmentPlayer = Experience.getHurtDamageAdjustment(this.damageLevelPlayer);

    // Weapon Class Calculations
    int maxLevel = Experience.getMaxLevel();
    boolean weaponClassLevelUpMessage = COMMON.weaponClassLevelUpMessage.get();
    this.experiencePenaltyWeaponClass =
        this.numberOfDeaths * Experience.getExperienceDeathPenaltyItems();

    for (WeaponClass weaponClass : WeaponClass.values()) {

      // Calculate weapon class experience based on item usage.
      float experienceFactorItems = Experience.getExperienceFactorItems();
      int weaponClassExperienceBase = 0;
      if (experienceFactorItems > 0.0f) {
        weaponClassExperienceBase =
            Math.round(getItemsUsage(weaponClass.getItems()) * experienceFactorItems);
      }
      weaponClassExperienceBaseMap.put(weaponClass, weaponClassExperienceBase);

      // Calculate weapon class experience including any kind of penalty.
      int weaponClassExperience = weaponClassExperienceBase;
      if (this.experiencePenaltyWeaponClass > 0) {
        weaponClassExperience =
            Math.max(weaponClassExperienceBase - this.experiencePenaltyWeaponClass, 0);
      }
      weaponClassExperienceMap.put(weaponClass, weaponClassExperience);

      // Calculate weapon class level based on item experience.
      int weaponClassLevel = 1;
      if (weaponClassExperience > 0) {
        weaponClassLevel = Experience.getLevelFromExperience(weaponClassExperience);
      }
      weaponClassLevelMap.put(weaponClass, weaponClassLevel);

      // Display level up message, if enabled.
      if (weaponClassLevelUpMessage) {

        // Cache former weapon class level, in the case we have none.
        int lastWeaponClassLevel = weaponClassLevelHistoryMap.getOrDefault(weaponClass, -1);
        if (lastWeaponClassLevel == -1) {
          weaponClassLevelHistoryMap.put(weaponClass, weaponClassLevel);
        }

        // Show level up message if new level is higher than former weapon class level.
        else if (weaponClassLevel > 1
            && weaponClassLevel > weaponClassLevelHistoryMap.getOrDefault(weaponClass, 1)) {
          this.player.sendMessage(new TranslatableComponent(Constants.TEXT_PREFIX + ".level_up",
              player.getName(), weaponClass.text, lastWeaponClassLevel, weaponClassLevel)
                  .withStyle(ChatFormatting.GREEN),
              Util.NIL_UUID);
          weaponClassLevelHistoryMap.put(weaponClass, weaponClassLevel);
        }
      }

      // Calculate weapon class damage adjustments based on item level.
      float weaponClassDamageAdjustment = 0.0f;
      if (weaponClassLevel > 1) {
        weaponClassDamageAdjustment = weaponClass.getDamageAdjustment(weaponClassLevel, maxLevel);
      }
      weaponClassDamageAdjustmentMap.put(weaponClass, weaponClassDamageAdjustment);

      // Calculate weapon class durability adjustments based on item level.
      float weaponClassDurabilityAdjustment = 0.0f;
      if (weaponClassLevel > 1) {
        weaponClassDurabilityAdjustment =
            weaponClass.getDurabilityAdjustment(weaponClassLevel, maxLevel);
      }
      weaponClassDurabilityAdjustmentMap.put(weaponClass, weaponClassDurabilityAdjustment);
    }
  }

  public String getUsername() {
    return username;
  }

  public UUID getUserUUID() {
    return userUUID;
  }

  public int getExperiencePenaltyGeneral() {
    return this.experiencePenaltyGeneral;
  }

  public int getExperiencePenaltyWeaponClass() {
    return this.experiencePenaltyWeaponClass;
  }

  public float getDamageAbsorbed() {
    return damageAbsorbed;
  }

  public float getDamageBlockedByShield() {
    return damageBlockedByShield;
  }

  public float getDamageDealt() {
    return damageDealt;
  }

  public float getDamageDealtAbsorbed() {
    return damageDealtAbsorbed;
  }

  public float getDamageDealtResisted() {
    return damageDealtResisted;
  }

  public int getDamageExperienceMob() {
    return damageExperienceMob;
  }

  public int getDamageExperiencePlayer() {
    return damageExperiencePlayer;
  }

  public int getDamageExperienceMobBase() {
    return damageExperienceMobBase;
  }

  public int getDamageExperiencePlayerBase() {
    return damageExperiencePlayerBase;
  }

  public float getDamageResisted() {
    return damageResisted;
  }

  public float getDamageTaken() {
    return damageTaken;
  }

  public float getSneakTime() {
    return sneakTime;
  }

  public int getMobKills() {
    return mobKills;
  }

  public int getNumberOfDeaths() {
    return numberOfDeaths;
  }

  public int getPlayerKills() {
    return playerKills;
  }

  public int getRaidsTriggered() {
    return raidsTriggered;
  }

  public int getRaidsWon() {
    return raidsWon;
  }

  public int getTargetsHit() {
    return targetsHit;
  }

  public int getDamageLevelMob() {
    return damageLevelMob;
  }

  public int getDamageLevelPlayer() {
    return damageLevelPlayer;
  }

  public float getDealtDamageAdjustmentMob() {
    return dealtDamageAdjustmentMob;
  }

  public float getDealtDamageAdjustmentPlayer() {
    return dealtDamageAdjustmentPlayer;
  }

  public float getHurtDamageAdjustmentMob() {
    return hurtDamageAdjustmentMob;
  }

  public float getHurtDamageAdjustmentPlayer() {
    return hurtDamageAdjustmentPlayer;
  }

  public int getWeaponClassExperience(WeaponClass weaponClass) {
    return weaponClassExperienceMap.getOrDefault(weaponClass, 0);
  }

  public int getWeaponClassExperienceBase(WeaponClass weaponClass) {
    return weaponClassExperienceBaseMap.getOrDefault(weaponClass, 0);
  }

  public int getWeaponClassLevel(WeaponClass weaponClass) {
    return weaponClassLevelMap.getOrDefault(weaponClass, 1);
  }

  public float getWeaponClassDamageAdjustment(WeaponClass weaponClass) {
    return weaponClassDamageAdjustmentMap.getOrDefault(weaponClass, 0.0f);
  }

  public float getWeaponClassDurabilityAdjustment(WeaponClass weaponClass) {
    return weaponClassDurabilityAdjustmentMap.getOrDefault(weaponClass, 0.0f);
  }

  public void load(CompoundTag compoundTag) {

    // Experience Penalty
    this.experiencePenaltyGeneral = compoundTag.getInt(EXPERIENCE_PENALTY_GENERAL_TAG);
    this.experiencePenaltyWeaponClass = compoundTag.getInt(EXPERIENCE_PENALTY_WEAPON_CLASS_TAG);

    // Player adjustments
    this.damageExperienceMob = compoundTag.getInt(DAMAGE_EXPERIENCE_MOB_TAG);
    this.damageExperiencePlayer = compoundTag.getInt(DAMAGE_EXPERIENCE_PLAYER_TAG);
    this.damageLevelMob = compoundTag.getInt(DAMAGE_LEVEL_MOB_TAG);
    this.damageLevelPlayer = compoundTag.getInt(DAMAGE_LEVEL_PLAYER_TAG);
    this.dealtDamageAdjustmentMob = compoundTag.getFloat(DEALT_DAMAGE_ADJUSTMENT_MOB_TAG);
    this.dealtDamageAdjustmentPlayer = compoundTag.getFloat(DEALT_DAMAGE_ADJUSTMENT_PLAYER_TAG);
    this.hurtDamageAdjustmentMob = compoundTag.getFloat(HURT_DAMAGE_ADJUSTMENT_MOB_TAG);
    this.hurtDamageAdjustmentPlayer = compoundTag.getFloat(HURT_DAMAGE_ADJUSTMENT_PLAYER_TAG);

    // Weapon Classes
    if (compoundTag.contains(WEAPON_CLASSES_TAG)) {
      ListTag weaponClassesListTag = compoundTag.getList(WEAPON_CLASSES_TAG, 10);
      for (int i = 0; i < weaponClassesListTag.size(); ++i) {
        CompoundTag weaponClassTag = weaponClassesListTag.getCompound(i);
        WeaponClass weaponClass = WeaponClass.valueOf(weaponClassTag.getString(WEAPON_CLASS_TAG));
        if (weaponClass != null && !weaponClassTag.isEmpty()) {
          weaponClassExperienceMap.put(weaponClass, weaponClassTag.getInt(EXPERIENCE_TAG));
          weaponClassLevelMap.put(weaponClass, weaponClassTag.getInt(LEVEL_TAG));
          weaponClassDamageAdjustmentMap.put(weaponClass,
              weaponClassTag.getFloat(DAMAGE_ADJUSTMENT));
          weaponClassDurabilityAdjustmentMap.put(weaponClass,
              weaponClassTag.getFloat(DURABILITY_ADJUSTMENT));
        }
      }
    }

    // Other
    this.mobKills = compoundTag.getInt(KILLS_MOB_TAG);
    this.playerKills = compoundTag.getInt(KILLS_PLAYER_TAG);
    this.numberOfDeaths = compoundTag.getInt(PLAYER_DEATHS_TAG);
    this.userUUID = compoundTag.getUUID(UUID_TAG);
    this.username = compoundTag.getString(NAME_TAG);
  }

  public CompoundTag save(CompoundTag compoundTag) {
    // Experience Penalty
    compoundTag.putInt(EXPERIENCE_PENALTY_GENERAL_TAG, this.experiencePenaltyGeneral);
    compoundTag.putInt(EXPERIENCE_PENALTY_WEAPON_CLASS_TAG, this.experiencePenaltyWeaponClass);

    // Player adjustments.
    compoundTag.putInt(DAMAGE_EXPERIENCE_MOB_TAG, this.damageExperienceMob);
    compoundTag.putInt(DAMAGE_EXPERIENCE_PLAYER_TAG, this.damageExperiencePlayer);
    compoundTag.putInt(DAMAGE_LEVEL_MOB_TAG, this.damageLevelMob);
    compoundTag.putInt(DAMAGE_LEVEL_PLAYER_TAG, this.damageLevelPlayer);
    compoundTag.putFloat(DEALT_DAMAGE_ADJUSTMENT_MOB_TAG, this.dealtDamageAdjustmentMob);
    compoundTag.putFloat(DEALT_DAMAGE_ADJUSTMENT_PLAYER_TAG, this.dealtDamageAdjustmentPlayer);
    compoundTag.putFloat(HURT_DAMAGE_ADJUSTMENT_MOB_TAG, this.hurtDamageAdjustmentMob);
    compoundTag.putFloat(HURT_DAMAGE_ADJUSTMENT_PLAYER_TAG, this.hurtDamageAdjustmentPlayer);

    // Weapon Classes
    ListTag weaponClassesListTag = new ListTag();
    for (WeaponClass weaponClass : WeaponClass.values()) {
      CompoundTag weaponClassTag = new CompoundTag();
      weaponClassTag.putString(WEAPON_CLASS_TAG, weaponClass.name());
      weaponClassTag.putInt(EXPERIENCE_TAG, getWeaponClassExperience(weaponClass));
      weaponClassTag.putInt(LEVEL_TAG, getWeaponClassLevel(weaponClass));
      weaponClassTag.putFloat(DAMAGE_ADJUSTMENT, getWeaponClassDamageAdjustment(weaponClass));
      weaponClassTag.putFloat(DURABILITY_ADJUSTMENT,
          getWeaponClassDurabilityAdjustment(weaponClass));
      weaponClassesListTag.add(weaponClassTag);
    }
    compoundTag.put(WEAPON_CLASSES_TAG, weaponClassesListTag);

    // Other
    compoundTag.putInt(KILLS_MOB_TAG, this.mobKills);
    compoundTag.putInt(KILLS_PLAYER_TAG, this.playerKills);
    compoundTag.putInt(PLAYER_DEATHS_TAG, this.numberOfDeaths);
    compoundTag.putString(NAME_TAG, this.username);
    compoundTag.putUUID(UUID_TAG, this.userUUID);
    return compoundTag;
  }

  private int getItemUsage(Item item) {
    return stats.getValue(Stats.ITEM_USED, item);
  }

  private int getItemsUsage(Set<Item> items) {
    int usage = 0;
    for (Item item : items) {
      int itemUsage = getItemUsage(item);
      if (itemUsage > 0) {
        usage += itemUsage;
      }
    }
    return usage;
  }

  @Override
  public String toString() {
    return "PlayerData['" + player.getDisplayName().getString() + "', damage dealt="
        + this.damageDealt + "]";
  }

  private static float getStatsFloatValue(ServerStatsCounter stats,
      StatType<ResourceLocation> statType, ResourceLocation state) {
    if (stats != null) {
      int value = stats.getValue(statType, state);
      if (value != 0) {
        return value / 1.0f;
      }
    }
    return 0.0f;
  }

}
