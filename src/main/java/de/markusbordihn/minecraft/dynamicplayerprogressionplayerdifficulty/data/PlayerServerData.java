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

import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;

import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.Constants;

public class PlayerServerData extends SavedData {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  private static final String PLAYER_FILE_ID = Constants.MOD_ID;

  public static final String PLAYER_DATA_TAG = "PlayerData";

  private static MinecraftServer server;
  private static PlayerServerData data;

  public PlayerServerData() {
    this.setDirty();
  }

  @SubscribeEvent
  public static void handleServerAboutToStartEvent(ServerAboutToStartEvent event) {
    server = null;
    data = null;
  }

  public static PlayerServerData load(CompoundTag compoundTag) {
    // Create a new data instance and set last update field.
    PlayerServerData playerServerData = new PlayerServerData();
    log.info("{} loading data ...", Constants.LOG_ICON_NAME);

    return playerServerData;
  }

  public static void prepare(MinecraftServer server) {
    // Make sure we preparing the data only once for the same server!
    if (server == null || server == PlayerServerData.server && PlayerServerData.data != null) {
      return;
    }

    log.info("{} preparing data for {}", Constants.LOG_ICON_NAME, server);
    PlayerServerData.server = server;

    // Using a global approach and storing relevant data in the overworld only!
    PlayerServerData.data = server.getLevel(Level.OVERWORLD).getDataStorage().computeIfAbsent(
        PlayerServerData::load, PlayerServerData::new, PlayerServerData.getFileId());
  }

  public static String getFileId() {
    return PLAYER_FILE_ID;
  }

  @Override
  public CompoundTag save(CompoundTag compoundTag) {
    log.info("{} saving data ... {}", Constants.LOG_ICON_NAME, this);

    // Iterate throw all players and store their player data.
    ListTag playerDataListTag = new ListTag();
    Iterator<PlayerData> playerDataIterator = PlayerDataManager.getPlayerMap().values().iterator();
    while (playerDataIterator.hasNext()) {
      PlayerData playerData = playerDataIterator.next();
      if (playerData != null) {
        CompoundTag playerDataCompoundTag = new CompoundTag();
        playerData.save(playerDataCompoundTag);
        playerDataListTag.add(playerDataCompoundTag);
      }
    }
    compoundTag.put(PLAYER_DATA_TAG, playerDataListTag);

    return compoundTag;
  }

}
