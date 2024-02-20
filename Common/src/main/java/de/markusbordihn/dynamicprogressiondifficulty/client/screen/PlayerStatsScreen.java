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

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.markusbordihn.dynamicprogressiondifficulty.Constants;
import de.markusbordihn.dynamicprogressiondifficulty.data.ItemClass;
import de.markusbordihn.dynamicprogressiondifficulty.data.PlayerStats;
import de.markusbordihn.dynamicprogressiondifficulty.data.PlayerStatsManager;
import de.markusbordihn.dynamicprogressiondifficulty.data.PlayerStatsType;
import de.markusbordihn.dynamicprogressiondifficulty.debug.DebugManager;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class PlayerStatsScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {

  private final PlayerStats playerStats;

  public PlayerStatsScreen(T menu, Inventory inventory, Component component) {
    super(menu, inventory, component);
    this.playerStats = PlayerStatsManager.getLocalPlayerStats();
  }

  @Override
  public void init() {
    super.init();

    // Default stats
    this.imageHeight = 243;
    this.imageWidth = 318;

    // Core Positions
    this.topPos = ((this.height - this.imageHeight) / 2) + 2;
    this.leftPos = (this.width - this.imageWidth) / 2;
  }

  @Override
  public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
    this.renderBackground(poseStack);
    super.render(poseStack, mouseX, mouseY, partialTicks);
    renderPenaltyStats(poseStack, this.leftPos + 10, this.topPos + 25);
    renderPlayerStats(poseStack, this.leftPos + 10, this.topPos + 30);
    renderDamageStats(poseStack, this.leftPos + 10, this.topPos + 60);
    renderItemClassStats(poseStack, this.leftPos + 10, this.topPos + 110);
    this.renderTooltip(poseStack, mouseX, mouseY);
  }

  private void renderPenaltyStats(PoseStack poseStack, int x, int y) {
    this.font.draw(poseStack, "Penalty Stats", x, y, Constants.FONT_COLOR_DEFAULT);
    this.font.draw(
        poseStack,
        new TranslatableComponent(Constants.STATS_TEXT_PREFIX + "experience_penalty"),
        x,
        y + 10f,
        Constants.FONT_COLOR_DEFAULT);
    this.font.draw(
        poseStack,
        new TranslatableComponent(
            Constants.STATS_TEXT_PREFIX + "deaths", this.playerStats.get(PlayerStatsType.DEATHS)),
        x,
        y + 20f,
        Constants.FONT_COLOR_DEFAULT);
  }

  private void renderPlayerStats(PoseStack poseStack, int x, int y) {}

  private void renderDamageStats(PoseStack poseStack, int x, int y) {
    this.font.draw(
        poseStack,
        new TranslatableComponent(Constants.STATS_TEXT_PREFIX + "damage_stats"),
        x,
        y,
        Constants.FONT_COLOR_DEFAULT);
    this.font.draw(
        poseStack,
        new TranslatableComponent(
            Constants.STATS_TEXT_PREFIX + "dealt_damage",
            this.playerStats.get(PlayerStatsType.DAMAGE_DEALT)),
        x,
        y + 10f,
        Constants.FONT_COLOR_DEFAULT);

    // Mod damage adjustments
    float dealtDamageAdjustmentMob = this.playerStats.getMobDealtDamageModifier();
    float hurtDamageAdjustmentMob = this.playerStats.getMobHurtDamageModifier();
    this.font.draw(
        poseStack,
        new TranslatableComponent(
            Constants.STATS_TEXT_PREFIX + "mob_damage",
            this.playerStats.get(PlayerStatsType.MOB_KILLS),
            this.playerStats.get(PlayerStatsType.INTERNAL_DAMAGE_LEVEL_MOB),
            new TextComponent(String.valueOf(dealtDamageAdjustmentMob))
                .withStyle(
                    dealtDamageAdjustmentMob > 1 ? ChatFormatting.GREEN : ChatFormatting.RED),
            new TextComponent(String.valueOf(hurtDamageAdjustmentMob))
                .withStyle(
                    hurtDamageAdjustmentMob > 1 ? ChatFormatting.RED : ChatFormatting.GREEN)),
        x,
        y + 20f,
        Constants.FONT_COLOR_DEFAULT);

    // Player damage adjustments
    float dealtDamageAdjustmentPlayer = this.playerStats.getPlayerDealtDamageModifier();
    float hurtDamageAdjustmentPlayer = this.playerStats.getPlayerHurtDamageModifier();
    this.font.draw(
        poseStack,
        new TranslatableComponent(
            Constants.STATS_TEXT_PREFIX + "player_damage",
            this.playerStats.get(PlayerStatsType.PLAYER_KILLS),
            this.playerStats.get(PlayerStatsType.INTERNAL_DAMAGE_LEVEL_PLAYER),
            new TextComponent(String.valueOf(dealtDamageAdjustmentPlayer))
                .withStyle(
                    dealtDamageAdjustmentPlayer > 1 ? ChatFormatting.GREEN : ChatFormatting.RED),
            new TextComponent(String.valueOf(hurtDamageAdjustmentPlayer))
                .withStyle(
                    hurtDamageAdjustmentPlayer > 1 ? ChatFormatting.RED : ChatFormatting.GREEN)),
        x,
        y + 30f,
        Constants.FONT_COLOR_DEFAULT);
  }

  private void renderItemClassStats(PoseStack poseStack, int x, int y) {
    this.font.draw(poseStack, "Item Class Level", x, y, Constants.FONT_COLOR_DEFAULT);
    int itemClassStatsX = x;
    int itemClassStatsY = y + 10;
    int itemClassStatsColumn = 0;
    int itemClassStatsColumnSpace = 100;
    for (ItemClass itemClass : ItemClass.values()) {
      if (!itemClass.isEnabled() && !DebugManager.isDebugMode()) {
        continue;
      }
      TextComponent textComponent = new TextComponent("");
      textComponent
          .append(new TextComponent(itemClass.getTextIcon()))
          .append(" ")
          .append(itemClass.getTranslatedText())
          .append(": ")
          .append("" + this.playerStats.getLevel(itemClass));
      this.font.draw(
          poseStack,
          textComponent,
          itemClassStatsX + (itemClassStatsColumn++ * itemClassStatsColumnSpace),
          itemClassStatsY,
          Constants.FONT_COLOR_DEFAULT);
      if (itemClassStatsColumn == 3) {
        itemClassStatsColumn = 0;
        itemClassStatsY += 10;
      }
    }
  }

  @Override
  protected void renderLabels(PoseStack poseStack, int x, int y) {
    this.font.draw(
        poseStack, this.title, this.titleLabelX, this.titleLabelY, Constants.FONT_COLOR_DEFAULT);
  }

  @Override
  protected void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY) {
    RenderSystem.setShader(GameRenderer::getPositionTexShader);
    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    RenderSystem.setShaderTexture(0, Constants.TEXTURE_DEMO_BACKGROUND);

    // Main screen: top left
    this.blit(poseStack, leftPos, topPos, 0, 0, 210, 160);

    // Main screen: top right
    this.blit(poseStack, leftPos + 203, topPos, 132, 0, 120, 160);

    // Main screen: bottom left
    this.blit(poseStack, leftPos, topPos + 77, 0, 5, 210, 170);

    // Main screen: bottom right
    this.blit(poseStack, leftPos + 203, topPos + 77, 132, 5, 120, 170);
  }
}
