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
import java.util.Optional;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetworkEventHandler {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  protected NetworkEventHandler() {}

  public static void registerNetworkHandler(final RegisterPayloadHandlerEvent event) {
    final IPayloadRegistrar registrar = event.registrar(Constants.MOD_ID);

    log.info("{} Network Handler with {} ...", Constants.LOG_REGISTER_PREFIX, registrar);

    registrar.play(
        SyncPlayerStatsMessage.MESSAGE_ID,
        SyncPlayerStatsMessage::new,
        handler -> handler.client(NetworkEventHandler::handlePlayerStatsMessage));

    registrar.play(
        OpenPlayerStatsMessage.MESSAGE_ID,
        OpenPlayerStatsMessage::new,
        handler -> handler.server(NetworkEventHandler::handleOpenPlayerStatsMessage));

    registrar.play(
        UpdatePlayerStatsMessage.MESSAGE_ID,
        UpdatePlayerStatsMessage::new,
        handler -> handler.server(NetworkEventHandler::handleUpdatePlayerStatsMessage));
  }

  private static void handlePlayerStatsMessage(
      SyncPlayerStatsMessage message, PlayPayloadContext playPayloadContext) {
    playPayloadContext.workHandler().submitAsync(() -> SyncPlayerStatsMessage.handle(message));
  }

  private static void handleOpenPlayerStatsMessage(
      OpenPlayerStatsMessage message, PlayPayloadContext playPayloadContext) {
    Optional<Player> player = playPayloadContext.player();
    if (player.isPresent() && player.get() instanceof ServerPlayer serverPlayer) {
      PlayerStatsManager.updatePlayerStats(serverPlayer);
      serverPlayer.openMenu(PlayerStatsMenu.createMenuProvider());
    }
  }

  private static void handleUpdatePlayerStatsMessage(
      UpdatePlayerStatsMessage message, PlayPayloadContext playPayloadContext) {
    Optional<Player> player = playPayloadContext.player();
    if (player.isPresent() && player.get() instanceof ServerPlayer serverPlayer) {
      PlayerStatsManager.updatePlayerStats(serverPlayer);
    }
  }

  public static void sendToServer(CustomPacketPayload message) {
    try {
      PacketDistributor.SERVER.with(null).send(message);
    } catch (Exception e) {
      log.error("Failed to send {} to server, got error: {}", message, e.getMessage());
    }
  }

  public static void sendToPlayer(CustomPacketPayload message, ServerPlayer serverPlayer) {
    try {
      PacketDistributor.PLAYER.with(serverPlayer).send(message);
    } catch (Exception e) {
      log.error(
          "Failed to send {} to player {}, got error: {}",
          message,
          serverPlayer.getName().getString(),
          e.getMessage());
    }
  }
}
