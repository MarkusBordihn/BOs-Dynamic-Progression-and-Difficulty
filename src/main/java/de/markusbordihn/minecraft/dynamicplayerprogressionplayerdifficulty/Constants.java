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

package de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty;

import net.minecraft.ChatFormatting;

public final class Constants {

  protected Constants() {}

  // General Mod definitions
  public static final String LOG_NAME = "Dynamic Player Progression and Player Difficulty";
  public static final String LOG_ICON = "⚔️";
  public static final String LOG_ICON_NAME = LOG_ICON + " " + LOG_NAME;
  public static final String LOG_REGISTER_PREFIX = LOG_ICON + " Register";
  public static final String MOD_COMMAND = "player_progression";
  public static final String MOD_ID = "dynamic_player_progression_and_player_difficulty";
  public static final String MOD_NAME = "Dynamic Player Progression and Player Difficulty";
  public static final String MOD_URL =
      "https://www.curseforge.com/minecraft/mc-mods/dynamic-player-progression-and-difficulty";

  // Colors
  public static final int FONT_COLOR_BLACK = ChatFormatting.BLACK.getColor();
  public static final int FONT_COLOR_DARK_GREEN = ChatFormatting.DARK_GREEN.getColor();
  public static final int FONT_COLOR_GRAY = ChatFormatting.GRAY.getColor();
  public static final int FONT_COLOR_GREEN = ChatFormatting.GREEN.getColor();
  public static final int FONT_COLOR_WARNING = ChatFormatting.RED.getColor();
  public static final int FONT_COLOR_YELLOW = ChatFormatting.YELLOW.getColor();

  // Prefixes
  public static final String TEXT_PREFIX = "text." + MOD_ID;
  public static final String CLASS_TEXT_PREFIX = TEXT_PREFIX + ".class.";
  public static final String LEVEL_TEXT_PREFIX = TEXT_PREFIX + ".level.";
  public static final String TOOLTIP_TEXT_PREFIX = TEXT_PREFIX + ".tooltip.";
}
