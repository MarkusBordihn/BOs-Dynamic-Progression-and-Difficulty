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

import de.markusbordihn.dynamicprogressiondifficulty.Constants;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.ServerStatsCounter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerStatsManager {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  private static final ConcurrentHashMap<UUID, PlayerStats> playerStatsMap =
      new ConcurrentHashMap<>();

  private static PlayerStats localPlayerStats = new PlayerStats();

  private PlayerStatsManager() {}

  public static boolean hasPlayerStats(ServerPlayer serverPlayer) {
    return hasPlayerStats(serverPlayer.getUUID());
  }

  public static boolean hasPlayerStats(UUID uuid) {
    return playerStatsMap.containsKey(uuid);
  }

  public static PlayerStats getPlayerStats(ServerPlayer serverPlayer) {
    return getPlayerStats(serverPlayer.getUUID());
  }

  public static PlayerStats getPlayerStats(UUID uuid) {
    return playerStatsMap.get(uuid);
  }

  public static void removePlayerStats(ServerPlayer serverPlayer) {
    removePlayerStats(serverPlayer.getUUID());
  }

  public static void removePlayerStats(UUID uuid) {
    log.debug("Remove player stats for {}.", uuid);
    playerStatsMap.remove(uuid);
  }

  public static void clearPlayerStats() {
    playerStatsMap.clear();
  }

  public static void addPlayerStats(ServerPlayer serverPlayer) {
    log.debug("Add player stats for {}.", serverPlayer);
    PlayerStats playerStats = new PlayerStats();
    UUID uuid = serverPlayer.getUUID();
    playerStatsMap.put(uuid, playerStats);
    updatePlayerStats(serverPlayer, playerStats);
  }

  public static PlayerStats updatePlayerStats(ServerPlayer serverPlayer) {
    UUID uuid = serverPlayer.getUUID();
    PlayerStats playerStats = playerStatsMap.getOrDefault(uuid, new PlayerStats());
    updatePlayerStats(serverPlayer, playerStats);
    return playerStats;
  }

  public static PlayerStats updatePlayerStats(ServerPlayer serverPlayer, PlayerStats playerStats) {
    ServerStatsCounter serverStatsCounter = serverPlayer.getStats();
    log.debug("Update player stats for {}.", serverPlayer);
    PlayerStatsUtils.updatePlayerStats(serverStatsCounter, playerStats, serverPlayer);
    return playerStats;
  }

  public static void updateLocalPlayerStats(PlayerStats playerStats) {
    log.debug("Update local player stats {}.", playerStats);
    localPlayerStats = playerStats;
  }

  public static PlayerStats getLocalPlayerStats() {
    return localPlayerStats;
  }
}
