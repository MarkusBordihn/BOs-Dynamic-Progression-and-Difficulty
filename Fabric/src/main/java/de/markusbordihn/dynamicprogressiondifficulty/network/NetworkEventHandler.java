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

import de.markusbordihn.dynamicprogressiondifficulty.Constants;
import de.markusbordihn.dynamicprogressiondifficulty.data.PlayerStatsManager;
import de.markusbordihn.dynamicprogressiondifficulty.menu.PlayerStatsMenu;
import de.markusbordihn.dynamicprogressiondifficulty.network.message.OpenPlayerStatsMessage;
import de.markusbordihn.dynamicprogressiondifficulty.network.message.SyncPlayerStatsMessage;
import de.markusbordihn.dynamicprogressiondifficulty.network.message.UpdatePlayerStatsMessage;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetworkEventHandler {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  private NetworkEventHandler() {}

  public static void registerClientNetworkHandler() {

    ClientPlayNetworking.registerGlobalReceiver(
        SyncPlayerStatsMessage.MESSAGE_ID,
        (client, handler, buffer, responseSender) -> SyncPlayerStatsMessage.handle(buffer));
  }

  public static void registerServerNetworkHandler() {

    ServerPlayNetworking.registerGlobalReceiver(
        OpenPlayerStatsMessage.MESSAGE_ID,
        (server, serverPlayer, handler, buffer, responseSender) -> {
          if (serverPlayer != null) {
            PlayerStatsManager.updatePlayerStats(serverPlayer);
            serverPlayer.openMenu(PlayerStatsMenu.createMenuProvider());
          }
        });

    ServerPlayNetworking.registerGlobalReceiver(
        UpdatePlayerStatsMessage.MESSAGE_ID,
        (server, serverPlayer, handler, buffer, responseSender) ->
            NetworkMessageHandler.syncPlayerStats(serverPlayer));
  }
}
