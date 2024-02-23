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

package de.markusbordihn.dynamicprogressiondifficulty.client.screen;

import de.markusbordihn.dynamicprogressiondifficulty.client.gui.PostScreenEvents;
import de.markusbordihn.dynamicprogressiondifficulty.client.gui.PreScreenEvents;
import de.markusbordihn.dynamicprogressiondifficulty.client.gui.component.StatsButton;
import de.markusbordihn.dynamicprogressiondifficulty.network.NetworkMessageHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.Event.Result;

public class ScreenEventHandler {

  protected ScreenEventHandler() {}

  public static void handleScreenEventInitPre(ScreenEvent.Init.Pre event) {
    if (event.getResult() == Result.DENY) {
      return;
    }

    // Request player stats from server for inventory and creative inventory screen.
    Screen screen = event.getScreen();
    if (PreScreenEvents.isValidScreen(screen) && Minecraft.getInstance().player != null) {
      NetworkMessageHandler.updatePlayerStats(Minecraft.getInstance().player);
    }
    PreScreenEvents.handlePreScreenEvent(screen);
  }

  public static void handleScreenEventInitPost(ScreenEvent.Init.Post event) {
    if (event.getResult() == Result.DENY) {
      return;
    }

    // Add stats button to inventory and creative inventory screen.
    Screen screen = event.getScreen();
    Minecraft minecraft = Minecraft.getInstance();
    if (PostScreenEvents.isValidScreen(screen)
        && minecraft.screen != null
        && minecraft.player != null) {
      event.addListener(
          new StatsButton(
              StatsButton.getOffsetX(),
              minecraft.screen.height - StatsButton.getOffsetY(),
              button -> NetworkMessageHandler.openPlayerStats(minecraft.player)));
    }
    PostScreenEvents.handlePostScreenEvent(event.getScreen());
  }
}
