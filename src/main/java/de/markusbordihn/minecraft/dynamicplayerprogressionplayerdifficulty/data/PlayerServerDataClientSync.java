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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.nbt.CompoundTag;

import de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.Constants;
import de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.network.NetworkHandler;

public class PlayerServerDataClientSync {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  protected PlayerServerDataClientSync() {}

  public static void syncPlayerData(PlayerData playerData) {
    if (playerData == null) {
      return;
    }
    log.debug("Sync player data for {} with {}", playerData.getUserUUID(), playerData);
    CompoundTag data = exportPlayerData(playerData);
    if (!data.isEmpty()) {
      NetworkHandler.updatePlayerData(playerData.getUserUUID(), data);
    }
  }

  public static CompoundTag exportPlayerData(PlayerData playerData) {
    CompoundTag compoundTag = new CompoundTag();
    if (playerData != null && playerData.getUserUUID() != null) {
      playerData.save(compoundTag);
    }
    return compoundTag;
  }

}
