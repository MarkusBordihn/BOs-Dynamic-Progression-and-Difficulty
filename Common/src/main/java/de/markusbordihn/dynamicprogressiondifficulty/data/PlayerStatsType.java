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

public enum PlayerStatsType {
  DAMAGE_ABSORBED,
  DAMAGE_BLOCKED_BY_SHIELD,
  DAMAGE_DEALT,
  DAMAGE_DEALT_ABSORBED,
  DAMAGE_DEALT_RESISTED,
  DAMAGE_RESISTED,
  DAMAGE_TAKEN,
  CROUCH_TIME,
  TARGET_HIT,
  DEATHS,
  MOB_KILLS,
  PLAYER_KILLS,
  RAID_TRIGGER,
  RAID_WIN,
  ENCHANT_ITEM,
  FISH_CAUGHT,
  ANIMALS_BRED,
  BELL_RING,
  INTERNAL_DAMAGE_EXPERIENCE_MOB,
  INTERNAL_DAMAGE_EXPERIENCE_PLAYER,
  INTERNAL_DAMAGE_LEVEL_MOB,
  INTERNAL_DAMAGE_LEVEL_PLAYER,
  INTERNAL_DEALT_DAMAGE_ADJUSTMENT_MOB,
  INTERNAL_DEALT_DAMAGE_ADJUSTMENT_PLAYER,
  INTERNAL_HURT_DAMAGE_ADJUSTMENT_MOB,
  INTERNAL_HURT_DAMAGE_ADJUSTMENT_PLAYER,
}
