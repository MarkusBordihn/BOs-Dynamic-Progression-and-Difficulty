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

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.Constants;

@EventBusSubscriber
public class PlayerDataManager {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  private static ConcurrentHashMap<UUID, PlayerData> playerMap = new ConcurrentHashMap<>();

  protected PlayerDataManager() {}

  @SubscribeEvent
  public static void handleServerAboutToStartEvent(ServerAboutToStartEvent event) {
    playerMap = new ConcurrentHashMap<>();
  }

  public static PlayerData addPlayer(ServerPlayer player) {
    if (player == null) {
      return null;
    }
    PlayerData playerData = new PlayerData(player);
    playerMap.put(player.getUUID(), playerData);
    return playerData;
  }

  public static PlayerData getPlayer(ServerPlayer player) {
    return getPlayer(player.getUUID());
  }

  public static PlayerData getPlayer(UUID playerUUID) {
    if (playerUUID == null) {
      return null;
    }
    return playerMap.getOrDefault(playerUUID, null);
  }

  public static void updatePlayer(ServerPlayer player) {
    updatePlayer(player.getUUID());
  }

  public static void updatePlayer(UUID playerUUID) {
    PlayerData playerData = getPlayer(playerUUID);
    if (playerData != null) {
      playerData.updateStats();
    }
  }
}
