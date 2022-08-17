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

package de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.data;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

import de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.Constants;

public enum WeaponClass {
  //@formatter:off
  AXE("🪓", null),
  BOW("🏹", null),
  CROSSBOW("🏹", null),
  DAGGER("🗡", null),
  GREAT_SWORD("⚔", null),
  GUN("╗", null),
  HAMMER("⚒", null),
  HOE("⚒", null),
  KEYBLADE("⚔", null),
  PICKAXE("⛏", null),
  SHIELD("🛡", null),
  SPEAR("🔱", null),
  SWORD("⚔", null);
  //@formatter:on

  public final ResourceLocation icon;
  public final String textIcon;
  public final String translationId;
  public final TranslatableComponent text;

  private WeaponClass(String textIcon, ResourceLocation icon) {
    this.icon = icon;
    this.textIcon = textIcon;
    this.translationId = Constants.CLASS_TEXT_PREFIX + this.name().toLowerCase();
    this.text = new TranslatableComponent(this.translationId);
  }
}
