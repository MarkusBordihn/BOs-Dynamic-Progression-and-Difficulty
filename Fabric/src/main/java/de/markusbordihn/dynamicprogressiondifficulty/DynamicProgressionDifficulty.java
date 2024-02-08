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

package de.markusbordihn.dynamicprogressiondifficulty;

import de.markusbordihn.dynamicprogressiondifficulty.block.BlockEvents;
import de.markusbordihn.dynamicprogressiondifficulty.commands.CommandManager;
import de.markusbordihn.dynamicprogressiondifficulty.debug.DebugManager;
import de.markusbordihn.dynamicprogressiondifficulty.menu.ModMenuTypes;
import de.markusbordihn.dynamicprogressiondifficulty.network.NetworkEventHandler;
import de.markusbordihn.dynamicprogressiondifficulty.network.NetworkMessageHandler;
import de.markusbordihn.dynamicprogressiondifficulty.player.PlayerEvents;
import de.markusbordihn.dynamicprogressiondifficulty.server.ServerEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.server.level.ServerPlayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DynamicProgressionDifficulty implements ModInitializer {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  @Override
  public void onInitialize() {
    log.info("Initializing {} (Fabric) ...", Constants.MOD_NAME);

    log.info("{} Debug Manager ...", Constants.LOG_REGISTER_PREFIX);
    if (System.getProperty("fabric.development") != null) {
      DebugManager.setDevelopmentEnvironment(true);
    }
    DebugManager.checkForDebugLogging(Constants.LOG_NAME);

    log.info("{} Player login event ...", Constants.LOG_REGISTER_PREFIX);
    ServerEntityEvents.ENTITY_LOAD.register(
        (entity, level) -> {
          if (entity instanceof ServerPlayer serverPlayer) {
            PlayerEvents.handlePlayerLogin(serverPlayer);
            NetworkMessageHandler.syncPlayerStats(serverPlayer);
          }
        });

    log.info("{} Player logout event ...", Constants.LOG_REGISTER_PREFIX);
    ServerEntityEvents.ENTITY_UNLOAD.register(
        (entity, level) -> {
          if (entity instanceof ServerPlayer serverPlayer) {
            PlayerEvents.handlePlayerLogout(serverPlayer);
          }
        });

    log.info("{} Block break event ...", Constants.LOG_REGISTER_PREFIX);
    PlayerBlockBreakEvents.AFTER.register(
        (world, player, pos, state, blockEntity) -> {
          if (player != null) {
            BlockEvents.handleBlockBreak(state.getBlock(), player);
          }
        });

    log.info("{} Server starting event ...", Constants.LOG_REGISTER_PREFIX);
    ServerLifecycleEvents.SERVER_STARTING.register(ServerEvents::handleServerStarting);

    log.info("{} Command register event ...", Constants.LOG_REGISTER_PREFIX);
    CommandRegistrationCallback.EVENT.register(
        (dispatcher, dedicated) -> CommandManager.registerCommands(dispatcher));

    log.info("{} Server Network Handler ...", Constants.LOG_REGISTER_PREFIX);
    NetworkEventHandler.registerServerNetworkHandler();

    log.info("{} Menu Types ...", Constants.LOG_REGISTER_PREFIX);
    ModMenuTypes.register();
  }
}
