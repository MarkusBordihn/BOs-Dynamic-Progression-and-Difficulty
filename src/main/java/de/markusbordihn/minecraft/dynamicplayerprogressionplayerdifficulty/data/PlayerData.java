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

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.ServerStatsCounter;
import net.minecraft.stats.StatType;
import net.minecraft.stats.Stats;

public class PlayerData {

  private ServerPlayer player;
  private ServerStatsCounter stats;

  private String username;

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

  // Specific stats level
  private int damageLevelMob = 1;
  private int damageLevelPlayer = 1;

  // Adjustments
  private float dealtDamageAdjustmentMob = 0;
  private float dealtDamageAdjustmentPlayer = 0;
  private float hurtDamageAdjustmentMob = 0;
  private float hurtDamageAdjustmentPlayer = 0;

  public PlayerData(ServerPlayer player) {
    if (player == null) {
      return;
    }
    this.player = player;
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

    // Calculate experience
    double damageBase = this.playerKills + (double) this.mobKills;
    int experienceMalus = Math.round(this.numberOfDeaths * Experience.getExperienceForMinLevel() * 0.75f);
    this.damageExperienceMob = Math.max(
        (int) Math.round(this.damageDealt * (this.mobKills / damageBase)) - experienceMalus, 0);
    this.damageExperiencePlayer = Math.max(
        (int) Math.round(this.damageDealt * (this.playerKills / damageBase)) - experienceMalus, 0);

    // Calculate levels
    this.damageLevelMob = Experience.getLevelFromExperience(this.damageExperienceMob);
    this.damageLevelPlayer = Experience.getLevelFromExperience(this.damageExperiencePlayer);

    // Calculate adjustments
    this.dealtDamageAdjustmentMob = Experience.getDealtDamageAdjustment(this.damageLevelMob);
    this.dealtDamageAdjustmentPlayer = Experience.getDealtDamageAdjustment(this.damageLevelPlayer);
    this.hurtDamageAdjustmentMob = Experience.getHurtDamageAdjustment(this.damageLevelMob);
    this.hurtDamageAdjustmentPlayer = Experience.getHurtDamageAdjustment(this.damageLevelPlayer);
  }

  public String getUsername() {
    return username;
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
