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

import cpw.mods.modlauncher.Launcher;
import cpw.mods.modlauncher.api.IEnvironment.Keys;
import de.markusbordihn.dynamicprogressiondifficulty.client.screen.ClientScreens;
import de.markusbordihn.dynamicprogressiondifficulty.client.screen.ScreenEventHandler;
import de.markusbordihn.dynamicprogressiondifficulty.debug.DebugManager;
import de.markusbordihn.dynamicprogressiondifficulty.menu.ModMenuTypes;
import de.markusbordihn.dynamicprogressiondifficulty.network.NetworkEventHandler;
import java.util.Optional;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.DistExecutor;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.common.NeoForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Constants.MOD_ID)
public class DynamicProgressionDifficulty {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  public DynamicProgressionDifficulty() {

    final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
    final IEventBus neoForgeEventBus = NeoForge.EVENT_BUS;

    log.info("Initializing {} (Forge) ...", Constants.MOD_NAME);

    log.info("{} Debug Manager ...", Constants.LOG_REGISTER_PREFIX);
    Optional<String> version = Launcher.INSTANCE.environment().getProperty(Keys.VERSION.get());
    Optional<String> launchTarget =
        Launcher.INSTANCE.environment().getProperty(Keys.LAUNCHTARGET.get());
    if ((version.isPresent() && "MOD_DEV".equals(version.get()))
        || (launchTarget.isPresent()
            && ("forgeclientuserdev".equals(launchTarget.get())
                || "forgeserveruserdev".equals(launchTarget.get())))) {
      DebugManager.setDevelopmentEnvironment(true);
    }
    DebugManager.checkForDebugLogging(Constants.LOG_NAME);

    modEventBus.addListener(NetworkEventHandler::registerNetworkHandler);

    log.info("{} Menu Types ...", Constants.LOG_REGISTER_PREFIX);
    ModMenuTypes.MENU_TYPES.register(modEventBus);

    DistExecutor.unsafeRunWhenOn(
        Dist.CLIENT,
        () ->
            () -> {
              log.info("{} Client Screens ...", Constants.LOG_REGISTER_PREFIX);
              modEventBus.addListener(ClientScreens::registerScreens);
              neoForgeEventBus.addListener(ScreenEventHandler::handleScreenEventInitPre);
              neoForgeEventBus.addListener(ScreenEventHandler::handleScreenEventInitPost);
            });
  }
}
