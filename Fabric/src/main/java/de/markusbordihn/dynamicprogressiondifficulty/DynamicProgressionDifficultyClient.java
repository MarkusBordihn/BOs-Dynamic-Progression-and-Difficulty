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

import de.markusbordihn.dynamicprogressiondifficulty.client.ItemTooltipManager;
import de.markusbordihn.dynamicprogressiondifficulty.client.screen.ClientScreens;
import de.markusbordihn.dynamicprogressiondifficulty.client.screen.ScreenEventHandler;
import de.markusbordihn.dynamicprogressiondifficulty.menu.ModMenuTypes;
import de.markusbordihn.dynamicprogressiondifficulty.network.NetworkEventHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DynamicProgressionDifficultyClient implements ClientModInitializer {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  @Override
  public void onInitializeClient() {
    log.info("Initializing {} (Fabric-Client) ...", Constants.MOD_NAME);

    log.info("{} Item Tooltip event ...", Constants.LOG_REGISTER_PREFIX);
    ItemTooltipCallback.EVENT.register(
        (itemStack, tooltipFlag, tooltip) -> {
          if (itemStack == null || itemStack.isEmpty()) {
            return;
          }
          ItemTooltipManager.handleItemTooltip(itemStack, tooltip, tooltipFlag);
        });

    log.info("{} Client Network Handler ...", Constants.LOG_REGISTER_PREFIX);
    NetworkEventHandler.registerClientNetworkHandler();

    log.info("{} Screen Handler ...", Constants.LOG_REGISTER_PREFIX);
    ScreenEvents.BEFORE_INIT.register(ScreenEventHandler::handleScreenEventInitPre);
    ScreenEvents.AFTER_INIT.register(ScreenEventHandler::handleScreenEventInitPost);

    ModMenuTypes.register();

    ClientScreens.registerScreens();
  }
}
