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

package de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.client.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;

import de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.Constants;

public class StatsButton extends Button {

  private final Minecraft minecraft;
  private final Font font;

  private static final ResourceLocation texture =
      new ResourceLocation(Constants.MOD_ID, "textures/gui/texture.png");

  public StatsButton(int x, int y, int width, int height, OnPress onPress) {
    super(x, y, width, height, new TextComponent(""), onPress);
    this.minecraft = Minecraft.getInstance();
    this.font = this.minecraft.font;
  }

  @Override
  public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
    RenderSystem.setShader(GameRenderer::getPositionTexShader);
    RenderSystem.setShaderTexture(0, texture);
    RenderSystem.disableDepthTest();
    if (isHoveredOrFocused()) {
      blit(poseStack, x, y, 34, 1, 10, 10);
    } else {
      blit(poseStack, x, y, 24, 1, 10, 10);
    }
    RenderSystem.enableDepthTest();
  }

}
