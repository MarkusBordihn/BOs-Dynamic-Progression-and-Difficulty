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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetworkEventHandler {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);
  private static final int PROTOCOL_VERSION = 1;
  private static final SimpleChannel SIMPLE_CHANNEL =
      ChannelBuilder.named(new ResourceLocation(Constants.MOD_ID, "network"))
          .networkProtocolVersion(PROTOCOL_VERSION)
          .simpleChannel();
  private static int id = 0;

  protected NetworkEventHandler() {}

  public static void registerNetworkHandler(final FMLCommonSetupEvent event) {
    log.info(
        "{} Network Handler for {} with version {} ...",
        Constants.LOG_REGISTER_PREFIX,
        SIMPLE_CHANNEL,
        PROTOCOL_VERSION);

    event.enqueueWork(
        () -> {
          SIMPLE_CHANNEL
              .messageBuilder(SyncPlayerStatsMessage.class, id++, NetworkDirection.PLAY_TO_CLIENT)
              .encoder(SyncPlayerStatsMessage::encode)
              .decoder(SyncPlayerStatsMessage::decode)
              .consumerNetworkThread(NetworkEventHandler::handlePlayerStatsMessage)
              .add();

          SIMPLE_CHANNEL
              .messageBuilder(OpenPlayerStatsMessage.class, id++, NetworkDirection.PLAY_TO_SERVER)
              .encoder(OpenPlayerStatsMessage::encode)
              .decoder(OpenPlayerStatsMessage::decode)
              .consumerNetworkThread(NetworkEventHandler::handleOpenPlayerStatsMessage)
              .add();

          SIMPLE_CHANNEL
              .messageBuilder(UpdatePlayerStatsMessage.class, id++, NetworkDirection.PLAY_TO_SERVER)
              .encoder(UpdatePlayerStatsMessage::encode)
              .decoder(UpdatePlayerStatsMessage::decode)
              .consumerNetworkThread(NetworkEventHandler::handleUpdatePlayerStatsMessage)
              .add();
        });
  }

  private static void handlePlayerStatsMessage(
      SyncPlayerStatsMessage message, CustomPayloadEvent.Context context) {
    context.enqueueWork(
        () ->
            DistExecutor.unsafeRunWhenOn(
                Dist.CLIENT, () -> () -> SyncPlayerStatsMessage.handle(message)));
    context.setPacketHandled(true);
  }

  private static void handleOpenPlayerStatsMessage(
      OpenPlayerStatsMessage message, CustomPayloadEvent.Context context) {
    ServerPlayer serverPlayer = context.getSender();
    if (serverPlayer != null) {
      PlayerStatsManager.updatePlayerStats(serverPlayer);
      serverPlayer.openMenu(PlayerStatsMenu.createMenuProvider());
    }
    context.setPacketHandled(true);
  }

  private static void handleUpdatePlayerStatsMessage(
      UpdatePlayerStatsMessage message, CustomPayloadEvent.Context context) {
    NetworkMessageHandler.syncPlayerStats(context.getSender());
    context.setPacketHandled(true);
  }

  public static <M> void sendToServer(M message) {
    try {
      SIMPLE_CHANNEL.send(message, PacketDistributor.SERVER.noArg());
    } catch (Exception e) {
      log.error("Failed to send {} to server, got error: {}", message, e.getMessage());
    }
  }

  public static <M> void sendToPlayer(M message, ServerPlayer serverPlayer) {
    try {
      SIMPLE_CHANNEL.send(message, PacketDistributor.PLAYER.with(serverPlayer));
    } catch (Exception e) {
      log.error(
          "Failed to send {} to player {}, got error: {}",
          message,
          serverPlayer.getName().getString(),
          e.getMessage());
    }
  }
}
