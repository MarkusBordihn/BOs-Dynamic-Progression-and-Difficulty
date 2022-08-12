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

package de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.network;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.Constants;
import de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.network.message.MessagePlayerData;

@EventBusSubscriber
public class NetworkHandler {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  private static final String PROTOCOL_VERSION = "1";
  public static final SimpleChannel INSTANCE =
      NetworkRegistry.newSimpleChannel(new ResourceLocation(Constants.MOD_ID, "network"),
          () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

  private static ConcurrentHashMap<UUID, ServerPlayer> serverPlayerMap = new ConcurrentHashMap<>();
  private static int id = 0;
  private static CompoundTag lastPlayerDataPackage;

  protected NetworkHandler() {}

  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public static void handlePlayerChangedDimensionEvent(PlayerChangedDimensionEvent event) {
    addServerPlayer(event.getEntity());
  }

  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public static void handlePlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
    addServerPlayer(event.getEntity());
  }

  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public static void handlePlayerLoggedOutEvent(PlayerEvent.PlayerLoggedOutEvent event) {
    removeServerPlayer(event.getEntity());
  }

  public static void registerNetworkHandler(final FMLCommonSetupEvent event) {

    log.info("{} Network Handler for {} with version {} ...", Constants.LOG_REGISTER_PREFIX,
        INSTANCE, PROTOCOL_VERSION);

    event.enqueueWork(() -> {
      // Sync single Player Data: Server -> Client
      INSTANCE.registerMessage(id++, MessagePlayerData.class, (message, buffer) -> {
        buffer.writeUtf(message.getPlayerUUID());
        buffer.writeNbt(message.getData());
      }, buffer -> new MessagePlayerData(buffer.readUtf(), buffer.readNbt()),
          MessagePlayerData::handle);
    });
  }

  /** Send specific player companion data to the owner, if data has changed. */
  public static void updatePlayerData(UUID playerUUID, CompoundTag data) {
    if (playerUUID != null && data != null && !data.isEmpty()
        && !data.equals(lastPlayerDataPackage)) {
      ServerPlayer serverPlayer = getServerPlayer(playerUUID);
      if (serverPlayer == null) {
        return;
      }
      log.debug("Sending Player data to {}: {}", serverPlayer, data);
      INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverPlayer),
          new MessagePlayerData(playerUUID.toString(), data));
      lastPlayerDataPackage = data;
    }
  }

  private static void addServerPlayer(Player player) {
    if (player instanceof ServerPlayer serverPlayer) {
      addServerPlayer(serverPlayer.getUUID(), serverPlayer);
    }
  }

  private static void addServerPlayer(UUID uuid, ServerPlayer serverPlayer) {
    serverPlayerMap.put(uuid, serverPlayer);
  }

  private static void removeServerPlayer(Player player) {
    if (player instanceof ServerPlayer serverPlayer) {
      removeServerPlayer(serverPlayer.getUUID());
    }
  }

  private static void removeServerPlayer(UUID uuid) {
    serverPlayerMap.remove(uuid);
  }

  private static ServerPlayer getServerPlayer(UUID uuid) {
    return serverPlayerMap.get(uuid);
  }

}
