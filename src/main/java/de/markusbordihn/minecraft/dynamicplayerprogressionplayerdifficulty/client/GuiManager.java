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

package de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.Constants;
import de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.client.gui.components.StatsButton;
import de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.config.CommonConfig;

@OnlyIn(Dist.CLIENT)
public class GuiManager {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  private static final CommonConfig.Config COMMON = CommonConfig.COMMON;

  protected GuiManager() {}

  public static void registerScreens(final FMLClientSetupEvent event) {
    log.info("{} Client Screens ...", Constants.LOG_REGISTER_PREFIX);
    event.enqueueWork(() -> {
    });
  }

  public static void handleScreenEventInitPost(ScreenEvent.InitScreenEvent.Post event) {
    Screen screen = event.getScreen();
    if (screen instanceof CreativeModeInventoryScreen creativeModeInventoryScreen
        && creativeModeInventoryScreen.getGuiLeft() > 0
        && creativeModeInventoryScreen.getGuiTop() > 0) {
      if (Boolean.FALSE.equals(COMMON.guiButtonEnabled.get())) {
        return;
      }
      log.debug("Found creative mode inventory screen {} with left:{} top:{} ...",
          creativeModeInventoryScreen, creativeModeInventoryScreen.getGuiLeft(),
          creativeModeInventoryScreen.getGuiTop());
    } else if (screen instanceof InventoryScreen inventoryScreen
        && (inventoryScreen.getGuiLeft() > 0 || inventoryScreen.getGuiTop() > 0)) {
      if (Boolean.FALSE.equals(COMMON.guiButtonEnabled.get())) {
        return;
      }
      log.debug("Found inventory screen {} with left:{} top:{} ...", inventoryScreen,
          inventoryScreen.getGuiLeft(), inventoryScreen.getGuiTop());
      event.addListener(
          new StatsButton(inventoryScreen.getGuiLeft() + COMMON.guiButtonPositionLeft.get(),
              inventoryScreen.getGuiTop() + COMMON.guiButtonPositionTop.get(), 10, 10, button -> {
                log.debug("Click clack ...");
              }));
    } else if (screen != null) {
      log.debug("Skip unsupported screen {}", screen);
    }
  }

}
