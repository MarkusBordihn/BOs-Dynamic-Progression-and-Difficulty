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

import de.markusbordihn.dynamicprogressiondifficulty.Constants;
import de.markusbordihn.dynamicprogressiondifficulty.data.ItemClass;
import de.markusbordihn.dynamicprogressiondifficulty.data.PlayerStats;
import de.markusbordihn.dynamicprogressiondifficulty.data.PlayerStatsManager;
import de.markusbordihn.dynamicprogressiondifficulty.data.PlayerStatsType;
import de.markusbordihn.dynamicprogressiondifficulty.debug.DebugManager;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
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
  public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
    this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
    super.render(guiGraphics, mouseX, mouseY, partialTicks);
    renderPenaltyStats(guiGraphics, this.leftPos + 10, this.topPos + 25);
    renderPlayerStats(guiGraphics, this.leftPos + 10, this.topPos + 30);
    renderDamageStats(guiGraphics, this.leftPos + 10, this.topPos + 60);
    renderItemClassStats(guiGraphics, this.leftPos + 10, this.topPos + 110);
    this.renderTooltip(guiGraphics, mouseX, mouseY);
  }

  private void renderPenaltyStats(GuiGraphics guiGraphics, int x, int y) {
    guiGraphics.drawString(this.font, "Penalty Stats", x, y, Constants.FONT_COLOR_DEFAULT, false);
    guiGraphics.drawString(
        this.font,
        Component.translatable(Constants.STATS_TEXT_PREFIX + "experience_penalty"),
        x,
        y + 10,
        Constants.FONT_COLOR_DEFAULT,
        false);
    guiGraphics.drawString(
        this.font,
        Component.translatable(
            Constants.STATS_TEXT_PREFIX + "deaths", this.playerStats.get(PlayerStatsType.DEATHS)),
        x,
        y + 20,
        Constants.FONT_COLOR_DEFAULT,
        false);
  }

  private void renderPlayerStats(GuiGraphics guiGraphics, int x, int y) {}

  private void renderDamageStats(GuiGraphics guiGraphics, int x, int y) {
    guiGraphics.drawString(
        this.font,
        Component.translatable(Constants.STATS_TEXT_PREFIX + "damage_stats"),
        x,
        y,
        Constants.FONT_COLOR_DEFAULT,
        false);
    guiGraphics.drawString(
        this.font,
        Component.translatable(
            Constants.STATS_TEXT_PREFIX + "dealt_damage",
            this.playerStats.get(PlayerStatsType.DAMAGE_DEALT)),
        x,
        y + 10,
        Constants.FONT_COLOR_DEFAULT,
        false);

    // Mod damage adjustments
    float dealtDamageAdjustmentMob = this.playerStats.getMobDealtDamageModifier();
    float hurtDamageAdjustmentMob = this.playerStats.getMobHurtDamageModifier();
    guiGraphics.drawString(
        this.font,
        Component.translatable(
            Constants.STATS_TEXT_PREFIX + "mob_damage",
            this.playerStats.get(PlayerStatsType.MOB_KILLS),
            this.playerStats.get(PlayerStatsType.INTERNAL_DAMAGE_LEVEL_MOB),
            Component.literal(String.valueOf(dealtDamageAdjustmentMob))
                .withStyle(
                    dealtDamageAdjustmentMob > 1 ? ChatFormatting.GREEN : ChatFormatting.RED),
            Component.literal(String.valueOf(hurtDamageAdjustmentMob))
                .withStyle(
                    hurtDamageAdjustmentMob > 1 ? ChatFormatting.RED : ChatFormatting.GREEN)),
        x,
        y + 20,
        Constants.FONT_COLOR_DEFAULT,
        false);

    // Player damage adjustments
    float dealtDamageAdjustmentPlayer = this.playerStats.getPlayerDealtDamageModifier();
    float hurtDamageAdjustmentPlayer = this.playerStats.getPlayerHurtDamageModifier();
    guiGraphics.drawString(
        this.font,
        Component.translatable(
            Constants.STATS_TEXT_PREFIX + "player_damage",
            this.playerStats.get(PlayerStatsType.PLAYER_KILLS),
            this.playerStats.get(PlayerStatsType.INTERNAL_DAMAGE_LEVEL_PLAYER),
            Component.literal(String.valueOf(dealtDamageAdjustmentPlayer))
                .withStyle(
                    dealtDamageAdjustmentPlayer > 1 ? ChatFormatting.GREEN : ChatFormatting.RED),
            Component.literal(String.valueOf(hurtDamageAdjustmentPlayer))
                .withStyle(
                    hurtDamageAdjustmentPlayer > 1 ? ChatFormatting.RED : ChatFormatting.GREEN)),
        x,
        y + 30,
        Constants.FONT_COLOR_DEFAULT,
        false);
  }

  private void renderItemClassStats(GuiGraphics guiGraphics, int x, int y) {
    guiGraphics.drawString(
        this.font, "Item Class Level", x, y, Constants.FONT_COLOR_DEFAULT, false);
    int itemClassStatsX = x;
    int itemClassStatsY = y + 10;
    int itemClassStatsColumn = 0;
    int itemClassStatsColumnSpace = 100;
    for (ItemClass itemClass : ItemClass.values()) {
      if (!itemClass.isEnabled() && !DebugManager.isDebugMode()) {
        continue;
      }
      MutableComponent textComponent = Component.literal("");
      textComponent
          .append(Component.literal(itemClass.getTextIcon()))
          .append(" ")
          .append(itemClass.getTranslatedText())
          .append(": ")
          .append("" + this.playerStats.getLevel(itemClass));
      guiGraphics.drawString(
          this.font,
          textComponent,
          itemClassStatsX + (itemClassStatsColumn++ * itemClassStatsColumnSpace),
          itemClassStatsY,
          Constants.FONT_COLOR_DEFAULT,
          false);
      if (itemClassStatsColumn == 3) {
        itemClassStatsColumn = 0;
        itemClassStatsY += 10;
      }
    }
  }

  @Override
  protected void renderLabels(GuiGraphics guiGraphics, int x, int y) {
    guiGraphics.drawString(
        this.font,
        this.title,
        this.titleLabelX,
        this.titleLabelY,
        Constants.FONT_COLOR_DEFAULT,
        false);
  }

  @Override
  protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
    // Main screen: top left
    guiGraphics.blit(Constants.TEXTURE_DEMO_BACKGROUND, leftPos, topPos, 0, 0, 210, 160);

    // Main screen: top right
    guiGraphics.blit(Constants.TEXTURE_DEMO_BACKGROUND, leftPos + 203, topPos, 132, 0, 120, 160);

    // Main screen: bottom left
    guiGraphics.blit(Constants.TEXTURE_DEMO_BACKGROUND, leftPos, topPos + 77, 0, 5, 210, 170);

    // Main screen: bottom right
    guiGraphics.blit(
        Constants.TEXTURE_DEMO_BACKGROUND, leftPos + 203, topPos + 77, 132, 5, 120, 170);
  }
}
