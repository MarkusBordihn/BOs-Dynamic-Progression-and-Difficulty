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

import java.util.Set;
import java.util.UUID;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.ServerStatsCounter;
import net.minecraft.stats.StatType;
import net.minecraft.stats.Stats;
import net.minecraft.world.item.Item;

public class PlayerData {

  // Tag definitions
  public static final String DAMAGE_EXPERIENCE_MOB_TAG = "DamageExperienceMob";
  public static final String DAMAGE_EXPERIENCE_PLAYER_TAG = "DamageExperiencePlayer";
  public static final String DAMAGE_LEVEL_MOB_TAG = "DamageLevelMob";
  public static final String DAMAGE_LEVEL_PLAYER_TAG = "DamageLevelPlayer";
  public static final String DEALT_DAMAGE_ADJUSTMENT_MOB_TAG = "DealtDamageAdjustmentMob";
  public static final String DEALT_DAMAGE_ADJUSTMENT_PLAYER_TAG = "DealtDamageAdjustmentPlayer";
  public static final String HURT_DAMAGE_ADJUSTMENT_MOB_TAG = "HurtDamageAdjustmentMob";
  public static final String HURT_DAMAGE_ADJUSTMENT_PLAYER_TAG = "HurtDamageAdjustmentPlayer";

  public static final String ITEM_DAMAGE_ADJUSTMENT_AXE_TAG = "ItemDamageAdjustmentAxe";
  public static final String ITEM_DAMAGE_ADJUSTMENT_BOW_TAG = "ItemDamageAdjustmentBow";
  public static final String ITEM_DAMAGE_ADJUSTMENT_CROSSBOW_TAG = "ItemDamageAdjustmentCrossbow";
  public static final String ITEM_DAMAGE_ADJUSTMENT_DAGGER_TAG = "ItemDamageAdjustmentDagger";
  public static final String ITEM_DAMAGE_ADJUSTMENT_GREAT_SWORD_TAG =
      "ItemDamageAdjustmentGreatSword";
  public static final String ITEM_DAMAGE_ADJUSTMENT_GUN_TAG = "ItemDamageAdjustmentGun";
  public static final String ITEM_DAMAGE_ADJUSTMENT_HAMMER_TAG = "ItemDamageAdjustmentHammer";
  public static final String ITEM_DAMAGE_ADJUSTMENT_HOE_TAG = "ItemDamageAdjustmentHoe";
  public static final String ITEM_DAMAGE_ADJUSTMENT_KEYBLADE_TAG = "ItemDamageAdjustmentKeyblade";
  public static final String ITEM_DAMAGE_ADJUSTMENT_PICKAXE_TAG = "ItemDamageAdjustmentPickaxe";
  public static final String ITEM_DAMAGE_ADJUSTMENT_SHIELD_TAG = "ItemDamageAdjustmentShield";
  public static final String ITEM_DAMAGE_ADJUSTMENT_SPEAR_TAG = "ItemDamageAdjustmentSpear";
  public static final String ITEM_DAMAGE_ADJUSTMENT_SWORD_TAG = "ItemDamageAdjustmentSword";

  public static final String ITEM_DURABILITY_ADJUSTMENT_AXE_TAG = "ItemDurabilityAdjustmentAxe";
  public static final String ITEM_DURABILITY_ADJUSTMENT_BOW_TAG = "ItemDurabilityAdjustmentBow";
  public static final String ITEM_DURABILITY_ADJUSTMENT_CROSSBOW_TAG =
      "ItemDurabilityAdjustmentCrossbow";
  public static final String ITEM_DURABILITY_ADJUSTMENT_DAGGER_TAG =
      "ItemDurabilityAdjustmentDagger";
  public static final String ITEM_DURABILITY_ADJUSTMENT_GREAT_SWORD_TAG =
      "ItemDurabilityAdjustmentGreatSword";
  public static final String ITEM_DURABILITY_ADJUSTMENT_GUN_TAG = "ItemDurabilityAdjustmentGun";
  public static final String ITEM_DURABILITY_ADJUSTMENT_HAMMER_TAG =
      "ItemDurabilityAdjustmentHammer";
  public static final String ITEM_DURABILITY_ADJUSTMENT_HOE_TAG = "ItemDurabilityAdjustmentHoe";
  public static final String ITEM_DURABILITY_ADJUSTMENT_KEYBLADE_TAG =
      "ItemDurabilityAdjustmentKeyblade";
  public static final String ITEM_DURABILITY_ADJUSTMENT_PICKAXE_TAG =
      "ItemDurabilityAdjustmentPickaxe";
  public static final String ITEM_DURABILITY_ADJUSTMENT_SHIELD_TAG =
      "ItemDurabilityAdjustmentShield";
  public static final String ITEM_DURABILITY_ADJUSTMENT_SPEAR_TAG = "ItemDurabilityAdjustmentSpear";
  public static final String ITEM_DURABILITY_ADJUSTMENT_SWORD_TAG = "ItemDurabilityAdjustmentSword";

  public static final String ITEM_EXPERIENCE_AXE_TAG = "ItemExperienceAxe";
  public static final String ITEM_EXPERIENCE_BOW_TAG = "ItemExperienceBow";
  public static final String ITEM_EXPERIENCE_CROSSBOW_TAG = "ItemExperienceCrossbow";
  public static final String ITEM_EXPERIENCE_DAGGER_TAG = "ItemExperienceDagger";
  public static final String ITEM_EXPERIENCE_GREAT_SWORD_TAG = "ItemExperienceGreatSword";
  public static final String ITEM_EXPERIENCE_GUN_TAG = "ItemExperienceGun";
  public static final String ITEM_EXPERIENCE_HAMMER_TAG = "ItemExperienceHammer";
  public static final String ITEM_EXPERIENCE_HOE_TAG = "ItemExperienceHoe";
  public static final String ITEM_EXPERIENCE_KEYBLADE_TAG = "ItemExperienceKeyblade";
  public static final String ITEM_EXPERIENCE_PICKAXE_TAG = "ItemExperiencePickaxe";
  public static final String ITEM_EXPERIENCE_SHIELD_TAG = "ItemExperienceShield";
  public static final String ITEM_EXPERIENCE_SPEAR_TAG = "ItemExperienceSpear";
  public static final String ITEM_EXPERIENCE_SWORD_TAG = "ItemExperienceSword";

  public static final String ITEM_LEVEL_AXE_TAG = "ItemLevelAxe";
  public static final String ITEM_LEVEL_BOW_TAG = "ItemLevelBow";
  public static final String ITEM_LEVEL_CROSSBOW_TAG = "ItemLevelCrossbow";
  public static final String ITEM_LEVEL_DAGGER_TAG = "ItemLevelDagger";
  public static final String ITEM_LEVEL_GREAT_SWORD_TAG = "ItemLevelGreatSword";
  public static final String ITEM_LEVEL_GUN_TAG = "ItemLevelGun";
  public static final String ITEM_LEVEL_HAMMER_TAG = "ItemLevelHammer";
  public static final String ITEM_LEVEL_HOE_TAG = "ItemLevelHoe";
  public static final String ITEM_LEVEL_KEYBLADE_TAG = "ItemLevelKeyblade";
  public static final String ITEM_LEVEL_PICKAXE_TAG = "ItemLevelPickaxe";
  public static final String ITEM_LEVEL_SHIELD_TAG = "ItemLevelShield";
  public static final String ITEM_LEVEL_SPEAR_TAG = "ItemLevelSpear";
  public static final String ITEM_LEVEL_SWORD_TAG = "ItemLevelSword";

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
  private int itemExperienceAxe = 0;
  private int itemExperienceBow = 0;
  private int itemExperienceCrossbow = 0;
  private int itemExperienceDagger = 0;
  private int itemExperienceGreatSword = 0;
  private int itemExperienceGun = 0;
  private int itemExperienceHammer = 0;
  private int itemExperienceHoe = 0;
  private int itemExperienceKeyblade = 0;
  private int itemExperiencePickaxe = 0;
  private int itemExperienceShield = 0;
  private int itemExperienceSpear = 0;
  private int itemExperienceSword = 0;

  // Specific stats level
  private int damageLevelMob = 1;
  private int damageLevelPlayer = 1;
  private int itemLevelAxe = 1;
  private int itemLevelBow = 1;
  private int itemLevelCrossbow = 1;
  private int itemLevelDagger = 1;
  private int itemLevelGreatSword = 1;
  private int itemLevelGun = 1;
  private int itemLevelHammer = 1;
  private int itemLevelHoe = 1;
  private int itemLevelKeyblade = 1;
  private int itemLevelPickaxe = 1;
  private int itemLevelShield = 1;
  private int itemLevelSpear = 1;
  private int itemLevelSword = 1;

  // Player adjustments
  private float dealtDamageAdjustmentMob = 0;
  private float dealtDamageAdjustmentPlayer = 0;
  private float hurtDamageAdjustmentMob = 0;
  private float hurtDamageAdjustmentPlayer = 0;

  // Item damage adjustments.
  private float itemDamageAdjustmentAxe = 0;
  private float itemDamageAdjustmentBow = 0;
  private float itemDamageAdjustmentCrossbow = 0;
  private float itemDamageAdjustmentDagger = 0;
  private float itemDamageAdjustmentGreatSword = 0;
  private float itemDamageAdjustmentGun = 0;
  private float itemDamageAdjustmentHammer = 0;
  private float itemDamageAdjustmentHoe = 0;
  private float itemDamageAdjustmentKeyblade = 0;
  private float itemDamageAdjustmentPickaxe = 0;
  private float itemDamageAdjustmentShield = 0;
  private float itemDamageAdjustmentSpear = 0;
  private float itemDamageAdjustmentSword = 0;

  // Item durability adjustments.
  private float itemDurabilityAdjustmentAxe = 0;
  private float itemDurabilityAdjustmentBow = 0;
  private float itemDurabilityAdjustmentCrossbow = 0;
  private float itemDurabilityAdjustmentDagger = 0;
  private float itemDurabilityAdjustmentGreatSword = 0;
  private float itemDurabilityAdjustmentGun = 0;
  private float itemDurabilityAdjustmentHammer = 0;
  private float itemDurabilityAdjustmentHoe = 0;
  private float itemDurabilityAdjustmentKeyblade = 0;
  private float itemDurabilityAdjustmentPickaxe = 0;
  private float itemDurabilityAdjustmentShield = 0;
  private float itemDurabilityAdjustmentSpear = 0;
  private float itemDurabilityAdjustmentSword = 0;

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

    // Calculate damage experience
    double damageBase = this.playerKills + (double) this.mobKills;
    this.damageExperienceMob = (int) Math.round(this.damageDealt * (this.mobKills / damageBase));
    this.damageExperiencePlayer =
        (int) Math.round(this.damageDealt * (this.playerKills / damageBase));

    // Calculate item experience
    float itemExperienceFactor = Experience.getExperienceFactorItems();
    this.itemExperienceAxe =
        Math.round(getItemsUsage(WeaponClassData.getAxeItems()) * itemExperienceFactor);
    this.itemExperienceBow =
        Math.round(getItemsUsage(WeaponClassData.getBowItems()) * itemExperienceFactor);
    this.itemExperienceCrossbow =
        Math.round(getItemsUsage(WeaponClassData.getCrossbowItems()) * itemExperienceFactor);
    this.itemExperienceDagger =
        Math.round(getItemsUsage(WeaponClassData.getDaggerItems()) * itemExperienceFactor);
    this.itemExperienceGreatSword =
        Math.round(getItemsUsage(WeaponClassData.getGreatSwordItems()) * itemExperienceFactor);
    this.itemExperienceGun =
        Math.round(getItemsUsage(WeaponClassData.getGunItems()) * itemExperienceFactor);
    this.itemExperienceHammer =
        Math.round(getItemsUsage(WeaponClassData.getHammerItems()) * itemExperienceFactor);
    this.itemExperienceHoe =
        Math.round(getItemsUsage(WeaponClassData.getHoeItems()) * itemExperienceFactor);
    this.itemExperienceKeyblade =
        Math.round(getItemsUsage(WeaponClassData.getKeybladeItems()) * itemExperienceFactor);
    this.itemExperiencePickaxe =
        Math.round(getItemsUsage(WeaponClassData.getPickaxeItems()) * itemExperienceFactor);
    this.itemExperienceShield =
        Math.round(getItemsUsage(WeaponClassData.getShieldItems()) * itemExperienceFactor);
    this.itemExperienceSpear =
        Math.round(getItemsUsage(WeaponClassData.getSpearItems()) * itemExperienceFactor);
    this.itemExperienceSword =
        Math.round(getItemsUsage(WeaponClassData.getSwordItems()) * itemExperienceFactor);

    // Calculate levels with death penalty
    int experienceDeathPenalty = this.numberOfDeaths * Experience.getExperienceDeathPenalty();
    int experienceDeathPenaltyItems =
        this.numberOfDeaths * Experience.getExperienceDeathPenaltyItems();
    this.damageLevelMob =
        Experience.getLevelFromExperience(this.damageExperienceMob, experienceDeathPenalty);
    this.damageLevelPlayer =
        Experience.getLevelFromExperience(this.damageExperiencePlayer, experienceDeathPenalty);
    this.itemLevelAxe =
        Experience.getLevelFromExperience(this.itemExperienceAxe, experienceDeathPenaltyItems);
    this.itemLevelBow =
        Experience.getLevelFromExperience(this.itemExperienceBow, experienceDeathPenaltyItems);
    this.itemLevelCrossbow =
        Experience.getLevelFromExperience(this.itemExperienceCrossbow, experienceDeathPenaltyItems);
    this.itemLevelDagger =
        Experience.getLevelFromExperience(this.itemExperienceDagger, experienceDeathPenaltyItems);
    this.itemLevelGreatSword = Experience.getLevelFromExperience(this.itemExperienceGreatSword,
        experienceDeathPenaltyItems);
    this.itemLevelGun =
        Experience.getLevelFromExperience(this.itemExperienceGun, experienceDeathPenaltyItems);
    this.itemLevelHammer =
        Experience.getLevelFromExperience(this.itemExperienceHammer, experienceDeathPenaltyItems);
    this.itemLevelHoe =
        Experience.getLevelFromExperience(this.itemExperienceHoe, experienceDeathPenaltyItems);
    this.itemLevelKeyblade =
        Experience.getLevelFromExperience(this.itemExperienceKeyblade, experienceDeathPenaltyItems);
    this.itemLevelPickaxe =
        Experience.getLevelFromExperience(this.itemExperiencePickaxe, experienceDeathPenaltyItems);
    this.itemLevelShield =
        Experience.getLevelFromExperience(this.itemExperienceShield, experienceDeathPenaltyItems);
    this.itemLevelSword =
        Experience.getLevelFromExperience(this.itemExperienceSword, experienceDeathPenaltyItems);
    this.itemLevelSpear =
        Experience.getLevelFromExperience(this.itemExperienceSpear, experienceDeathPenaltyItems);

    // Calculate player adjustments
    this.dealtDamageAdjustmentMob = Experience.getDealtDamageAdjustment(this.damageLevelMob);
    this.dealtDamageAdjustmentPlayer = Experience.getDealtDamageAdjustment(this.damageLevelPlayer);
    this.hurtDamageAdjustmentMob = Experience.getHurtDamageAdjustment(this.damageLevelMob);
    this.hurtDamageAdjustmentPlayer = Experience.getHurtDamageAdjustment(this.damageLevelPlayer);

    // Calculate item damage adjustments
    this.itemDamageAdjustmentAxe = Experience.getItemDamageAdjustmentAxe(this.itemLevelAxe);
    this.itemDamageAdjustmentBow = Experience.getItemDamageAdjustmentBow(this.itemLevelBow);
    this.itemDamageAdjustmentCrossbow =
        Experience.getItemDamageAdjustmentCrossbow(this.itemLevelCrossbow);
    this.itemDamageAdjustmentDagger = Experience.getItemDamageAdjustmentDagger(this.itemLevelDagger);
    this.itemDamageAdjustmentGreatSword =
        Experience.getItemDamageAdjustmentGreatSword(this.itemLevelGreatSword);
    this.itemDamageAdjustmentGun = Experience.getItemDamageAdjustmentGun(this.itemLevelGun);
    this.itemDamageAdjustmentHammer = Experience.getItemDamageAdjustmentHammer(this.itemLevelHammer);
    this.itemDamageAdjustmentHoe = Experience.getItemDamageAdjustmentHoe(this.itemLevelHoe);
    this.itemDamageAdjustmentKeyblade =
        Experience.getItemDamageAdjustmentKeyblade(this.itemLevelKeyblade);
    this.itemDamageAdjustmentPickaxe =
        Experience.getItemDamageAdjustmentPickaxe(this.itemLevelPickaxe);
    this.itemDamageAdjustmentShield =
        Experience.getItemDamageAdjustmentShield(this.itemLevelShield);
    this.itemDamageAdjustmentSpear = Experience.getItemDamageAdjustmentSpear(this.itemLevelSpear);
    this.itemDamageAdjustmentSword = Experience.getItemDamageAdjustmentSword(this.itemLevelSword);

    // Calculate item damage adjustments
    this.itemDurabilityAdjustmentAxe = Experience.getItemDurabilityAdjustmentAxe(this.itemLevelAxe);
    this.itemDurabilityAdjustmentBow = Experience.getItemDurabilityAdjustmentBow(this.itemLevelBow);
    this.itemDurabilityAdjustmentCrossbow =
        Experience.getItemDurabilityAdjustmentCrossbow(this.itemLevelCrossbow);
    this.itemDurabilityAdjustmentDagger =
        Experience.getItemDurabilityAdjustmentDagger(this.itemLevelDagger);
    this.itemDurabilityAdjustmentGreatSword =
        Experience.getItemDurabilityAdjustmentGreatSword(this.itemLevelGreatSword);
    this.itemDurabilityAdjustmentGun = Experience.getItemDurabilityAdjustmentGun(this.itemLevelGun);
    this.itemDurabilityAdjustmentHammer =
        Experience.getItemDurabilityAdjustmentHammer(this.itemLevelHammer);
    this.itemDurabilityAdjustmentHoe = Experience.getItemDurabilityAdjustmentHoe(this.itemLevelHoe);
    this.itemDurabilityAdjustmentKeyblade =
        Experience.getItemDurabilityAdjustmentKeyblade(this.itemLevelKeyblade);
    this.itemDurabilityAdjustmentPickaxe =
        Experience.getItemDurabilityAdjustmentPickaxe(this.itemLevelPickaxe);
    this.itemDurabilityAdjustmentShield =
        Experience.getItemDurabilityAdjustmentShield(this.itemLevelShield);
    this.itemDurabilityAdjustmentSpear =
        Experience.getItemDurabilityAdjustmentSpear(this.itemLevelSpear);
    this.itemDurabilityAdjustmentSword =
        Experience.getItemDurabilityAdjustmentSword(this.itemLevelSword);
  }

  public String getUsername() {
    return username;
  }

  public UUID getUserUUID() {
    return userUUID;
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

  public int getItemExperienceAxe() {
    return itemExperienceAxe;
  }

  public int getItemExperienceBow() {
    return itemExperienceBow;
  }

  public int getItemExperienceCrossbow() {
    return itemExperienceCrossbow;
  }

  public int getItemExperienceDagger() {
    return itemExperienceDagger;
  }

  public int getItemExperienceGreatSword() {
    return itemExperienceGreatSword;
  }

  public int getItemExperienceGun() {
    return itemExperienceGun;
  }

  public int getItemExperienceHammer() {
    return itemExperienceHammer;
  }

  public int getItemExperienceHoe() {
    return itemExperienceHoe;
  }

  public int getItemExperienceKeyblade() {
    return itemExperienceKeyblade;
  }

  public int getItemExperiencePickaxe() {
    return itemExperiencePickaxe;
  }

  public int getItemExperienceShield() {
    return itemExperienceShield;
  }

  public int getItemExperienceSpear() {
    return itemExperienceSpear;
  }

  public int getItemExperienceSword() {
    return itemExperienceSword;
  }

  public int getItemLevelAxe() {
    return itemLevelAxe;
  }

  public int getItemLevelBow() {
    return itemLevelBow;
  }

  public int getItemLevelCrossbow() {
    return itemLevelCrossbow;
  }

  public int getItemLevelDagger() {
    return itemLevelDagger;
  }

  public int getItemLevelGreatSword() {
    return itemLevelGreatSword;
  }

  public int getItemLevelGun() {
    return itemLevelGun;
  }

  public int getItemLevelHammer() {
    return itemLevelHammer;
  }

  public int getItemLevelHoe() {
    return itemLevelHoe;
  }

  public int getItemLevelKeyblade() {
    return itemLevelKeyblade;
  }

  public int getItemLevelPickaxe() {
    return itemLevelPickaxe;
  }

  public int getItemLevelShield() {
    return itemLevelShield;
  }

  public int getItemLevelSpear() {
    return itemLevelSpear;
  }

  public int getItemLevelSword() {
    return itemLevelSword;
  }

  public float getItemDamageAdjustmentAxe() {
    return itemDamageAdjustmentAxe;
  }

  public float getItemDamageAdjustmentBow() {
    return itemDamageAdjustmentBow;
  }

  public float getItemDamageAdjustmentCrossbow() {
    return itemDamageAdjustmentCrossbow;
  }

  public float getItemDamageAdjustmentDagger() {
    return itemDamageAdjustmentDagger;
  }

  public float getItemDamageAdjustmentGreatSword() {
    return itemDamageAdjustmentGreatSword;
  }

  public float getItemDamageAdjustmentGun() {
    return itemDamageAdjustmentGun;
  }

  public float getItemDamageAdjustmentHammer() {
    return itemDamageAdjustmentHammer;
  }

  public float getItemDamageAdjustmentHoe() {
    return itemDamageAdjustmentHoe;
  }

  public float getItemDamageAdjustmentKeyblade() {
    return itemDamageAdjustmentKeyblade;
  }

  public float getItemDamageAdjustmentPickaxe() {
    return itemDamageAdjustmentPickaxe;
  }

  public float getItemDamageAdjustmentShield() {
    return itemDamageAdjustmentShield;
  }

  public float getItemDamageAdjustmentSpear() {
    return itemDamageAdjustmentSpear;
  }

  public float getItemDamageAdjustmentSword() {
    return itemDamageAdjustmentSword;
  }

  public float getWeaponClassDamageAdjustment(WeaponClass weaponClass) {
    switch (weaponClass) {
      case AXE:
        return itemDamageAdjustmentAxe;
      case BOW:
        return itemDamageAdjustmentBow;
      case CROSSBOW:
        return itemDamageAdjustmentCrossbow;
      case DAGGER:
        return itemDamageAdjustmentDagger;
      case GREAT_SWORD:
        return itemDamageAdjustmentGreatSword;
      case GUN:
        return itemDamageAdjustmentGun;
      case HAMMER:
        return itemDamageAdjustmentHammer;
      case HOE:
        return itemDamageAdjustmentHoe;
      case KEYBLADE:
        return itemDamageAdjustmentKeyblade;
      case PICKAXE:
        return itemDamageAdjustmentPickaxe;
      case SHIELD:
        return itemDamageAdjustmentShield;
      case SPEAR:
        return itemDamageAdjustmentSpear;
      case SWORD:
        return itemDamageAdjustmentSword;
      default:
        return 0.0f;
    }
  }

  public float getWeaponClassDurabilityAdjustment(WeaponClass weaponClass) {
    switch (weaponClass) {
      case AXE:
        return itemDurabilityAdjustmentAxe;
      case BOW:
        return itemDurabilityAdjustmentBow;
      case CROSSBOW:
        return itemDurabilityAdjustmentCrossbow;
      case DAGGER:
        return itemDurabilityAdjustmentDagger;
      case GREAT_SWORD:
        return itemDurabilityAdjustmentGreatSword;
      case GUN:
        return itemDurabilityAdjustmentGun;
      case HAMMER:
        return itemDurabilityAdjustmentHammer;
      case HOE:
        return itemDurabilityAdjustmentHoe;
      case KEYBLADE:
        return itemDurabilityAdjustmentKeyblade;
      case PICKAXE:
        return itemDurabilityAdjustmentPickaxe;
      case SHIELD:
        return itemDurabilityAdjustmentShield;
      case SPEAR:
        return itemDurabilityAdjustmentSpear;
      case SWORD:
        return itemDurabilityAdjustmentSword;
      default:
        return 0.0f;
    }
  }

  public int getWeaponClassExperience(WeaponClass weaponClass) {
    switch (weaponClass) {
      case AXE:
        return itemExperienceAxe;
      case BOW:
        return itemExperienceBow;
      case CROSSBOW:
        return itemExperienceCrossbow;
      case DAGGER:
        return itemExperienceDagger;
      case GREAT_SWORD:
        return itemExperienceGreatSword;
      case GUN:
        return itemExperienceGun;
      case HAMMER:
        return itemExperienceHammer;
      case HOE:
        return itemExperienceHoe;
      case KEYBLADE:
        return itemExperienceKeyblade;
      case PICKAXE:
        return itemExperiencePickaxe;
      case SHIELD:
        return itemExperienceShield;
      case SPEAR:
        return itemExperienceSpear;
      case SWORD:
        return itemExperienceSword;
      default:
        return 1;
    }
  }

  public int getWeaponClassLevel(WeaponClass weaponClass) {
    switch (weaponClass) {
      case AXE:
        return itemLevelAxe;
      case BOW:
        return itemLevelBow;
      case CROSSBOW:
        return itemLevelCrossbow;
      case DAGGER:
        return itemLevelDagger;
      case GREAT_SWORD:
        return itemLevelGreatSword;
      case GUN:
        return itemLevelGun;
      case HAMMER:
        return itemLevelHammer;
      case HOE:
        return itemLevelHoe;
      case KEYBLADE:
        return itemLevelKeyblade;
      case PICKAXE:
        return itemLevelPickaxe;
      case SHIELD:
        return itemLevelShield;
      case SPEAR:
        return itemLevelSpear;
      case SWORD:
        return itemLevelSword;
      default:
        return 1;
    }
  }

  public void load(CompoundTag compoundTag) {

    // Player adjustments.
    this.damageExperienceMob = compoundTag.getInt(DAMAGE_EXPERIENCE_MOB_TAG);
    this.damageExperiencePlayer = compoundTag.getInt(DAMAGE_EXPERIENCE_PLAYER_TAG);
    this.damageLevelMob = compoundTag.getInt(DAMAGE_LEVEL_MOB_TAG);
    this.damageLevelPlayer = compoundTag.getInt(DAMAGE_LEVEL_PLAYER_TAG);
    this.dealtDamageAdjustmentMob = compoundTag.getFloat(DEALT_DAMAGE_ADJUSTMENT_MOB_TAG);
    this.dealtDamageAdjustmentPlayer = compoundTag.getFloat(DEALT_DAMAGE_ADJUSTMENT_PLAYER_TAG);
    this.hurtDamageAdjustmentMob = compoundTag.getFloat(HURT_DAMAGE_ADJUSTMENT_MOB_TAG);
    this.hurtDamageAdjustmentPlayer = compoundTag.getFloat(HURT_DAMAGE_ADJUSTMENT_PLAYER_TAG);

    // Item Damage adjustments
    this.itemDamageAdjustmentAxe = compoundTag.getFloat(ITEM_DAMAGE_ADJUSTMENT_AXE_TAG);
    this.itemDamageAdjustmentBow = compoundTag.getFloat(ITEM_DAMAGE_ADJUSTMENT_BOW_TAG);
    this.itemDamageAdjustmentCrossbow = compoundTag.getFloat(ITEM_DAMAGE_ADJUSTMENT_CROSSBOW_TAG);
    this.itemDamageAdjustmentDagger = compoundTag.getFloat(ITEM_DAMAGE_ADJUSTMENT_DAGGER_TAG);
    this.itemDamageAdjustmentGreatSword =
        compoundTag.getFloat(ITEM_DAMAGE_ADJUSTMENT_GREAT_SWORD_TAG);
    this.itemDamageAdjustmentGun = compoundTag.getFloat(ITEM_DAMAGE_ADJUSTMENT_GUN_TAG);
    this.itemDamageAdjustmentHammer = compoundTag.getFloat(ITEM_DAMAGE_ADJUSTMENT_HAMMER_TAG);
    this.itemDamageAdjustmentHoe = compoundTag.getFloat(ITEM_DAMAGE_ADJUSTMENT_HOE_TAG);
    this.itemDamageAdjustmentKeyblade = compoundTag.getFloat(ITEM_DAMAGE_ADJUSTMENT_KEYBLADE_TAG);
    this.itemDamageAdjustmentPickaxe = compoundTag.getFloat(ITEM_DAMAGE_ADJUSTMENT_PICKAXE_TAG);
    this.itemDamageAdjustmentShield = compoundTag.getFloat(ITEM_DAMAGE_ADJUSTMENT_SHIELD_TAG);
    this.itemDamageAdjustmentSpear = compoundTag.getFloat(ITEM_DAMAGE_ADJUSTMENT_SPEAR_TAG);
    this.itemDamageAdjustmentSword = compoundTag.getFloat(ITEM_DAMAGE_ADJUSTMENT_SWORD_TAG);

    // Item Durability adjustments
    this.itemDurabilityAdjustmentAxe = compoundTag.getFloat(ITEM_DURABILITY_ADJUSTMENT_AXE_TAG);
    this.itemDurabilityAdjustmentBow = compoundTag.getFloat(ITEM_DURABILITY_ADJUSTMENT_BOW_TAG);
    this.itemDurabilityAdjustmentCrossbow =
        compoundTag.getFloat(ITEM_DURABILITY_ADJUSTMENT_CROSSBOW_TAG);
    this.itemDurabilityAdjustmentDagger =
        compoundTag.getFloat(ITEM_DURABILITY_ADJUSTMENT_DAGGER_TAG);
    this.itemDurabilityAdjustmentGreatSword =
        compoundTag.getFloat(ITEM_DURABILITY_ADJUSTMENT_GREAT_SWORD_TAG);
    this.itemDurabilityAdjustmentGun = compoundTag.getFloat(ITEM_DURABILITY_ADJUSTMENT_GUN_TAG);
    this.itemDurabilityAdjustmentHammer =
        compoundTag.getFloat(ITEM_DURABILITY_ADJUSTMENT_HAMMER_TAG);
    this.itemDurabilityAdjustmentHoe = compoundTag.getFloat(ITEM_DURABILITY_ADJUSTMENT_HOE_TAG);
    this.itemDurabilityAdjustmentKeyblade =
        compoundTag.getFloat(ITEM_DURABILITY_ADJUSTMENT_KEYBLADE_TAG);
    this.itemDurabilityAdjustmentPickaxe =
        compoundTag.getFloat(ITEM_DURABILITY_ADJUSTMENT_PICKAXE_TAG);
    this.itemDurabilityAdjustmentShield =
        compoundTag.getFloat(ITEM_DURABILITY_ADJUSTMENT_SHIELD_TAG);
    this.itemDurabilityAdjustmentSpear = compoundTag.getFloat(ITEM_DURABILITY_ADJUSTMENT_SPEAR_TAG);
    this.itemDurabilityAdjustmentSword = compoundTag.getFloat(ITEM_DURABILITY_ADJUSTMENT_SWORD_TAG);

    // Item Experience
    this.itemExperienceAxe = compoundTag.getInt(ITEM_EXPERIENCE_AXE_TAG);
    this.itemExperienceBow = compoundTag.getInt(ITEM_EXPERIENCE_BOW_TAG);
    this.itemExperienceCrossbow = compoundTag.getInt(ITEM_EXPERIENCE_CROSSBOW_TAG);
    this.itemExperienceDagger = compoundTag.getInt(ITEM_EXPERIENCE_DAGGER_TAG);
    this.itemExperienceGreatSword = compoundTag.getInt(ITEM_EXPERIENCE_GREAT_SWORD_TAG);
    this.itemExperienceGun = compoundTag.getInt(ITEM_EXPERIENCE_GUN_TAG);
    this.itemExperienceHammer = compoundTag.getInt(ITEM_EXPERIENCE_HAMMER_TAG);
    this.itemExperienceHoe = compoundTag.getInt(ITEM_EXPERIENCE_HOE_TAG);
    this.itemExperienceKeyblade = compoundTag.getInt(ITEM_EXPERIENCE_KEYBLADE_TAG);
    this.itemExperiencePickaxe = compoundTag.getInt(ITEM_EXPERIENCE_PICKAXE_TAG);
    this.itemExperienceShield = compoundTag.getInt(ITEM_EXPERIENCE_SHIELD_TAG);
    this.itemExperienceSpear = compoundTag.getInt(ITEM_EXPERIENCE_SPEAR_TAG);
    this.itemExperienceSword = compoundTag.getInt(ITEM_EXPERIENCE_SWORD_TAG);

    // Item Levels
    this.itemLevelAxe = compoundTag.getInt(ITEM_LEVEL_AXE_TAG);
    this.itemLevelBow = compoundTag.getInt(ITEM_LEVEL_BOW_TAG);
    this.itemLevelCrossbow = compoundTag.getInt(ITEM_LEVEL_CROSSBOW_TAG);
    this.itemLevelDagger = compoundTag.getInt(ITEM_LEVEL_DAGGER_TAG);
    this.itemLevelGreatSword = compoundTag.getInt(ITEM_LEVEL_GREAT_SWORD_TAG);
    this.itemLevelGun = compoundTag.getInt(ITEM_LEVEL_GUN_TAG);
    this.itemLevelHammer = compoundTag.getInt(ITEM_LEVEL_HAMMER_TAG);
    this.itemLevelHoe = compoundTag.getInt(ITEM_LEVEL_HOE_TAG);
    this.itemLevelKeyblade = compoundTag.getInt(ITEM_LEVEL_KEYBLADE_TAG);
    this.itemLevelPickaxe = compoundTag.getInt(ITEM_LEVEL_PICKAXE_TAG);
    this.itemLevelShield = compoundTag.getInt(ITEM_LEVEL_SHIELD_TAG);
    this.itemLevelSpear = compoundTag.getInt(ITEM_LEVEL_SPEAR_TAG);
    this.itemLevelSword = compoundTag.getInt(ITEM_LEVEL_SWORD_TAG);

    this.mobKills = compoundTag.getInt(KILLS_MOB_TAG);
    this.playerKills = compoundTag.getInt(KILLS_PLAYER_TAG);
    this.numberOfDeaths = compoundTag.getInt(PLAYER_DEATHS_TAG);
    this.userUUID = compoundTag.getUUID(UUID_TAG);
    this.username = compoundTag.getString(NAME_TAG);
  }

  public CompoundTag save(CompoundTag compoundTag) {
    // Player adjustments.
    compoundTag.putInt(DAMAGE_EXPERIENCE_MOB_TAG, this.damageExperienceMob);
    compoundTag.putInt(DAMAGE_EXPERIENCE_PLAYER_TAG, this.damageExperiencePlayer);
    compoundTag.putInt(DAMAGE_LEVEL_MOB_TAG, this.damageLevelMob);
    compoundTag.putInt(DAMAGE_LEVEL_PLAYER_TAG, this.damageLevelPlayer);
    compoundTag.putFloat(DEALT_DAMAGE_ADJUSTMENT_MOB_TAG, this.dealtDamageAdjustmentMob);
    compoundTag.putFloat(DEALT_DAMAGE_ADJUSTMENT_PLAYER_TAG, this.dealtDamageAdjustmentPlayer);
    compoundTag.putFloat(HURT_DAMAGE_ADJUSTMENT_MOB_TAG, this.hurtDamageAdjustmentMob);
    compoundTag.putFloat(HURT_DAMAGE_ADJUSTMENT_PLAYER_TAG, this.hurtDamageAdjustmentPlayer);

    // Item Damage adjustments.
    compoundTag.putFloat(ITEM_DAMAGE_ADJUSTMENT_AXE_TAG, this.itemDamageAdjustmentAxe);
    compoundTag.putFloat(ITEM_DAMAGE_ADJUSTMENT_BOW_TAG, this.itemDamageAdjustmentBow);
    compoundTag.putFloat(ITEM_DAMAGE_ADJUSTMENT_CROSSBOW_TAG, this.itemDamageAdjustmentCrossbow);
    compoundTag.putFloat(ITEM_DAMAGE_ADJUSTMENT_DAGGER_TAG, this.itemDamageAdjustmentDagger);
    compoundTag.putFloat(ITEM_DAMAGE_ADJUSTMENT_GREAT_SWORD_TAG,
        this.itemDamageAdjustmentGreatSword);
    compoundTag.putFloat(ITEM_DAMAGE_ADJUSTMENT_GUN_TAG, this.itemDamageAdjustmentGun);
    compoundTag.putFloat(ITEM_DAMAGE_ADJUSTMENT_HAMMER_TAG, this.itemDamageAdjustmentHammer);
    compoundTag.putFloat(ITEM_DAMAGE_ADJUSTMENT_HOE_TAG, this.itemDamageAdjustmentHoe);
    compoundTag.putFloat(ITEM_DAMAGE_ADJUSTMENT_KEYBLADE_TAG, this.itemDamageAdjustmentKeyblade);
    compoundTag.putFloat(ITEM_DAMAGE_ADJUSTMENT_PICKAXE_TAG, this.itemDamageAdjustmentPickaxe);
    compoundTag.putFloat(ITEM_DAMAGE_ADJUSTMENT_SHIELD_TAG, this.itemDamageAdjustmentShield);
    compoundTag.putFloat(ITEM_DAMAGE_ADJUSTMENT_SPEAR_TAG, this.itemDamageAdjustmentSpear);
    compoundTag.putFloat(ITEM_DAMAGE_ADJUSTMENT_SWORD_TAG, this.itemDamageAdjustmentSword);

    // Item Durability adjustments.
    compoundTag.putFloat(ITEM_DURABILITY_ADJUSTMENT_AXE_TAG, this.itemDurabilityAdjustmentAxe);
    compoundTag.putFloat(ITEM_DURABILITY_ADJUSTMENT_BOW_TAG, this.itemDurabilityAdjustmentBow);
    compoundTag.putFloat(ITEM_DURABILITY_ADJUSTMENT_CROSSBOW_TAG,
        this.itemDurabilityAdjustmentCrossbow);
    compoundTag.putFloat(ITEM_DURABILITY_ADJUSTMENT_DAGGER_TAG,
        this.itemDurabilityAdjustmentDagger);
    compoundTag.putFloat(ITEM_DURABILITY_ADJUSTMENT_GREAT_SWORD_TAG,
        this.itemDurabilityAdjustmentGreatSword);
    compoundTag.putFloat(ITEM_DURABILITY_ADJUSTMENT_GUN_TAG, this.itemDurabilityAdjustmentGun);
    compoundTag.putFloat(ITEM_DURABILITY_ADJUSTMENT_HAMMER_TAG,
        this.itemDurabilityAdjustmentHammer);
    compoundTag.putFloat(ITEM_DURABILITY_ADJUSTMENT_HOE_TAG, this.itemDurabilityAdjustmentHoe);
    compoundTag.putFloat(ITEM_DURABILITY_ADJUSTMENT_KEYBLADE_TAG,
        this.itemDurabilityAdjustmentKeyblade);
    compoundTag.putFloat(ITEM_DURABILITY_ADJUSTMENT_PICKAXE_TAG,
        this.itemDurabilityAdjustmentPickaxe);
    compoundTag.putFloat(ITEM_DURABILITY_ADJUSTMENT_SHIELD_TAG,
        this.itemDurabilityAdjustmentShield);
    compoundTag.putFloat(ITEM_DURABILITY_ADJUSTMENT_SPEAR_TAG, this.itemDurabilityAdjustmentSpear);
    compoundTag.putFloat(ITEM_DURABILITY_ADJUSTMENT_SWORD_TAG, this.itemDurabilityAdjustmentSword);

    // Item Experience
    compoundTag.putInt(ITEM_EXPERIENCE_AXE_TAG, this.itemExperienceAxe);
    compoundTag.putInt(ITEM_EXPERIENCE_BOW_TAG, this.itemExperienceBow);
    compoundTag.putInt(ITEM_EXPERIENCE_CROSSBOW_TAG, this.itemExperienceCrossbow);
    compoundTag.putInt(ITEM_EXPERIENCE_DAGGER_TAG, this.itemExperienceDagger);
    compoundTag.putInt(ITEM_EXPERIENCE_GREAT_SWORD_TAG, this.itemExperienceGreatSword);
    compoundTag.putInt(ITEM_EXPERIENCE_GUN_TAG, this.itemExperienceGun);
    compoundTag.putInt(ITEM_EXPERIENCE_HAMMER_TAG, this.itemExperienceHammer);
    compoundTag.putInt(ITEM_EXPERIENCE_HOE_TAG, this.itemExperienceHoe);
    compoundTag.putInt(ITEM_EXPERIENCE_KEYBLADE_TAG, this.itemExperienceKeyblade);
    compoundTag.putInt(ITEM_EXPERIENCE_PICKAXE_TAG, this.itemExperiencePickaxe);
    compoundTag.putInt(ITEM_EXPERIENCE_SHIELD_TAG, this.itemExperienceShield);
    compoundTag.putInt(ITEM_EXPERIENCE_SPEAR_TAG, this.itemExperienceSpear);
    compoundTag.putInt(ITEM_EXPERIENCE_SWORD_TAG, this.itemExperienceSword);

    // Item Levels
    compoundTag.putInt(ITEM_LEVEL_AXE_TAG, this.itemLevelAxe);
    compoundTag.putInt(ITEM_LEVEL_BOW_TAG, this.itemLevelBow);
    compoundTag.putInt(ITEM_LEVEL_CROSSBOW_TAG, this.itemLevelCrossbow);
    compoundTag.putInt(ITEM_LEVEL_DAGGER_TAG, this.itemLevelDagger);
    compoundTag.putInt(ITEM_LEVEL_GREAT_SWORD_TAG, this.itemLevelGreatSword);
    compoundTag.putInt(ITEM_LEVEL_GUN_TAG, this.itemLevelGun);
    compoundTag.putInt(ITEM_LEVEL_HAMMER_TAG, this.itemLevelHammer);
    compoundTag.putInt(ITEM_LEVEL_HOE_TAG, this.itemLevelHoe);
    compoundTag.putInt(ITEM_LEVEL_KEYBLADE_TAG, this.itemLevelKeyblade);
    compoundTag.putInt(ITEM_LEVEL_PICKAXE_TAG, this.itemLevelPickaxe);
    compoundTag.putInt(ITEM_LEVEL_SHIELD_TAG, this.itemLevelShield);
    compoundTag.putInt(ITEM_LEVEL_SPEAR_TAG, this.itemLevelSpear);
    compoundTag.putInt(ITEM_LEVEL_SWORD_TAG, this.itemLevelSword);

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
