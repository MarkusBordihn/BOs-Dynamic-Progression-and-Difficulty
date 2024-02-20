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
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@EventBusSubscriber
public class NetworkEventHandler {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);
  private static final String PROTOCOL_VERSION = "1";
  public static final SimpleChannel INSTANCE =
      NetworkRegistry.newSimpleChannel(
          new ResourceLocation(Constants.MOD_ID, "network"),
          () -> PROTOCOL_VERSION,
          PROTOCOL_VERSION::equals,
          PROTOCOL_VERSION::equals);
  private static int id = 0;

  protected NetworkEventHandler() {}

  public static void registerNetworkHandler(final FMLCommonSetupEvent event) {
    INSTANCE.registerMessage(
        id++,
        SyncPlayerStatsMessage.class,
        SyncPlayerStatsMessage::encode,
        SyncPlayerStatsMessage::decode,
        NetworkEventHandler::handlePlayerStatsMessage);

    INSTANCE.registerMessage(
        id++,
        OpenPlayerStatsMessage.class,
        OpenPlayerStatsMessage::encode,
        OpenPlayerStatsMessage::decode,
        NetworkEventHandler::handleOpenPlayerStatsMessage);

    INSTANCE.registerMessage(
        id++,
        UpdatePlayerStatsMessage.class,
        UpdatePlayerStatsMessage::encode,
        UpdatePlayerStatsMessage::decode,
        NetworkEventHandler::handleUpdatePlayerStatsMessage);
  }

  private static void handlePlayerStatsMessage(
      SyncPlayerStatsMessage message, Supplier<Context> contextSupplier) {
    NetworkEvent.Context context = contextSupplier.get();
    context.enqueueWork(
        () ->
            DistExecutor.unsafeRunWhenOn(
                Dist.CLIENT, () -> () -> SyncPlayerStatsMessage.handle(message)));
    context.setPacketHandled(true);
  }

  private static void handleOpenPlayerStatsMessage(
      OpenPlayerStatsMessage message, Supplier<Context> contextSupplier) {
    NetworkEvent.Context context = contextSupplier.get();
    ServerPlayer serverPlayer = context.getSender();
    if (serverPlayer != null) {
      PlayerStatsManager.updatePlayerStats(serverPlayer);
      serverPlayer.openMenu(PlayerStatsMenu.createMenuProvider());
    }
    context.setPacketHandled(true);
  }

  private static void handleUpdatePlayerStatsMessage(
      UpdatePlayerStatsMessage message, Supplier<Context> contextSupplier) {
    NetworkEvent.Context context = contextSupplier.get();
    NetworkMessageHandler.syncPlayerStats(context.getSender());
    context.setPacketHandled(true);
  }

  public static <M> void sendToServer(M message) {
    try {
      INSTANCE.sendToServer(message);
    } catch (Exception e) {
      log.error("Failed to send {} to server, got error: {}", message, e.getMessage());
    }
  }

  public static <M> void sendToPlayer(M message, ServerPlayer serverPlayer) {
    try {
      INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverPlayer), message);
    } catch (Exception e) {
      log.error(
          "Failed to send {} to player {}, got error: {}",
          message,
          serverPlayer.getName().getString(),
          e.getMessage());
    }
  }
}
