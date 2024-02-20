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

import net.minecraft.resources.ResourceLocation;

public final class Constants {

  // General Mod definitions
  public static final String LOG_NAME = "Dynamic Progression and Difficulty";
  public static final String LOG_ICON = "⚔️";
  public static final String LOG_ICON_NAME = LOG_ICON + " " + LOG_NAME;
  public static final String LOG_REGISTER_PREFIX = LOG_ICON + " Register";
  public static final String MOD_COMMAND = "player_progress";
  public static final String MOD_ID = "dynamic_progression_and_difficulty";
  public static final String MOD_NAME = "Dynamic Progression and Difficulty";
  public static final String MOD_URL =
      "https://www.curseforge.com/minecraft/mc-mods/dynamic-player-progression-and-difficulty";

  // Colors
  public static final int FONT_COLOR_BLACK = 0;
  public static final int FONT_COLOR_DARK_GREEN = 43520;
  public static final int FONT_COLOR_DEFAULT = 4210752;
  public static final int FONT_COLOR_GRAY = 11184810;
  public static final int FONT_COLOR_GREEN = 5635925;
  public static final int FONT_COLOR_RED = 16733525;
  public static final int FONT_COLOR_WARNING = FONT_COLOR_RED;
  public static final int FONT_COLOR_YELLOW = 16777045;

  // Prefixes
  public static final String MINECRAFT_PREFIX = "minecraft";
  public static final String TEXT_PREFIX = "text." + MOD_ID;
  public static final String CLASS_TEXT_PREFIX = TEXT_PREFIX + ".class.";
  public static final String LEVEL_TEXT_PREFIX = TEXT_PREFIX + ".level.";
  public static final String STATS_CMD_TEXT_PREFIX = TEXT_PREFIX + ".stats_cmd.";
  public static final String STATS_TEXT_PREFIX = TEXT_PREFIX + ".stats.";
  public static final String TOOLTIP_TEXT_PREFIX = TEXT_PREFIX + ".tooltip.";

  // Fonts
  public static final ResourceLocation ICONS_FONT = new ResourceLocation(Constants.MOD_ID, "icons");

  // Textures
  public static final ResourceLocation TEXTURE_DEMO_BACKGROUND =
      new ResourceLocation(MINECRAFT_PREFIX, "textures/gui/demo_background.png");

  private Constants() {}
}
