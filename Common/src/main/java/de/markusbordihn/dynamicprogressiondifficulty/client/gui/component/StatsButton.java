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

package de.markusbordihn.dynamicprogressiondifficulty.client.gui.component;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.markusbordihn.dynamicprogressiondifficulty.Constants;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class StatsButton extends Button {

  public static final int OFFSET_X = 30;
  public static final int OFFSET_Y = 26;
  private static final ResourceLocation TEXTURE =
      new ResourceLocation(Constants.MOD_ID, "textures/gui/texture.png");
  private static final int WIDTH = 20;
  private static final int HEIGHT = 20;

  public StatsButton(int x, int y, OnPress onPress) {
    super(x, y, WIDTH, HEIGHT, Component.literal(""), onPress, Button.DEFAULT_NARRATION);
  }

  public static int getOffsetX() {
    return OFFSET_X;
  }

  public static int getOffsetY() {
    return OFFSET_Y;
  }

  @Override
  public void renderWidget(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
    super.renderWidget(poseStack, mouseX, mouseY, partialTicks);
    RenderSystem.setShader(GameRenderer::getPositionTexShader);
    RenderSystem.setShaderTexture(0, TEXTURE);
    if (isHoveredOrFocused()) {
      blit(poseStack, this.getX() + 6, this.getY() + 5, 34, 1, 10, 10);
    } else {
      blit(poseStack, this.getX() + 6, this.getY() + 5, 24, 1, 10, 10);
    }
  }
}
