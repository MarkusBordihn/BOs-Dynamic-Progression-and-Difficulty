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

package de.markusbordihn.dynamicprogressiondifficulty.network;

import de.markusbordihn.dynamicprogressiondifficulty.data.PlayerStatsManager;
import de.markusbordihn.dynamicprogressiondifficulty.network.message.OpenPlayerStatsMessage;
import de.markusbordihn.dynamicprogressiondifficulty.network.message.SyncPlayerStatsMessage;
import de.markusbordihn.dynamicprogressiondifficulty.network.message.UpdatePlayerStatsMessage;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerPlayer;

public class NetworkMessageHandler {

  protected NetworkMessageHandler() {}

  public static void syncPlayerStats(ServerPlayer serverPlayer) {
    if (serverPlayer == null) {
      return;
    }
    NetworkEventHandler.sendToPlayer(
        new SyncPlayerStatsMessage(
            serverPlayer.getUUID(), PlayerStatsManager.getPlayerStats(serverPlayer)),
        serverPlayer);
  }

  public static void openPlayerStats(LocalPlayer localPlayer) {
    NetworkEventHandler.sendToServer(new OpenPlayerStatsMessage(localPlayer.getUUID()));
  }

  public static void updatePlayerStats(LocalPlayer localPlayer) {
    NetworkEventHandler.sendToServer(new UpdatePlayerStatsMessage(localPlayer.getUUID()));
  }
}
