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

package de.markusbordihn.dynamicprogressiondifficulty.data;

import de.markusbordihn.dynamicprogressiondifficulty.Constants;
import de.markusbordihn.dynamicprogressiondifficulty.item.ModItemTags;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public enum ItemClass {
  AXE(
      MainItemClass.MELEE_WEAPON,
      "\uDB80\uDC00",
      "\uD83E\uDE93",
      ModItemTags.MELEE_WEAPON_AXE,
      0,
      0.75f,
      1.5f),
  BOOTS(MainItemClass.ARMOR, "⛨", ModItemTags.ARMOR_BOOTS, 0.5f, 0, 0),
  BOW(MainItemClass.RANGED_WEAPON, "\uD83C\uDFF9", ModItemTags.RANGED_WEAPON_BOW, 0, 0.5f, 0.25f),
  CHESTPLATE(MainItemClass.ARMOR, "⛨", ModItemTags.ARMOR_CHESTPLATE, 0.5f, 0, 0),
  CLAW(MainItemClass.SPECIAL_WEAPON, "╽╽╽", ModItemTags.SPECIAL_WEAPON_CLAWS, 0, 0.5f, 0.25f),
  CLAYMORE(MainItemClass.MELEE_WEAPON, "⚔", ModItemTags.MELEE_WEAPON_CLAYMORE, 0, 0.5f, 0.25f),
  CROSSBOW(
      MainItemClass.RANGED_WEAPON,
      "\uD83C\uDFF9",
      ModItemTags.RANGED_WEAPON_CROSSBOW,
      0,
      0.5f,
      0.25f),
  DAGGER(MainItemClass.MELEE_WEAPON, "⚔", ModItemTags.MELEE_WEAPON_DAGGER, 0, 0.5f, 0.25f),
  FIST(MainItemClass.UNARMED_COMBAT, "╽", null, 0, 1.0f, 0),
  GREAT_SWORD(
      MainItemClass.MELEE_WEAPON, "⚔", ModItemTags.MELEE_WEAPON_GREAT_SWORD, 0, 0.5f, 0.25f),
  GUN(MainItemClass.RANGED_WEAPON, "▝▜", ModItemTags.RANGED_WEAPON_GUN, 0, 0.5f, 0.25f),
  HAMMER(MainItemClass.SPECIAL_WEAPON, "╤", ModItemTags.SPECIAL_WEAPON_HAMMER, 0, 0.5f, 0.25f),
  HAND_TO_HAND(MainItemClass.UNARMED_COMBAT, "╽", null, 0, 0.5f, 0.25f),
  HELMET(MainItemClass.ARMOR, "⛨", ModItemTags.ARMOR_HELMET, 0.5f, 0, 0),
  HOE(MainItemClass.TOOL, "↿", ModItemTags.TOOL_HOE, 0, 2f, 0.25f),
  KATANA(MainItemClass.MELEE_WEAPON, "⚔", ModItemTags.MELEE_WEAPON_KATANA, 0, 0.75f, 0.25f),
  KEYBLADE(MainItemClass.SPECIAL_WEAPON, "⚷", ModItemTags.SPECIAL_WEAPON_KEYBLADE, 0, 0.5f, 0.25f),
  LEGGINGS(MainItemClass.ARMOR, "⛨", ModItemTags.ARMOR_LEGGINGS, 0.5f, 0, 0),
  MACE(MainItemClass.MELEE_WEAPON, "╿", ModItemTags.MELEE_WEAPON_MACE, 0, 0.5f, 0.25f),
  SPELL_BOOK(MainItemClass.MAGIC_WEAPON, "⁂", ModItemTags.MAGIC_WEAPON_SPELL_BOOK, 0, 0.5f, 0.25f),
  PAXEL(MainItemClass.TOOL, "⚒", ModItemTags.TOOL_PAXEL, 0, 0.75f, 0.5f),
  PICKAXE(MainItemClass.TOOL, "⛏", ModItemTags.TOOL_PICKAXE, 0, 0.75f, 0.5f),
  POLEARM(
      MainItemClass.MELEE_WEAPON, "\uD83D\uDD31", ModItemTags.MELEE_WEAPON_POLEARM, 0, 0.5f, 0.25f),
  SCYTHE(MainItemClass.MELEE_WEAPON, "⚳", ModItemTags.MELEE_WEAPON_SCYTHE, 0, 0.5f, 0.25f),
  SHIELD(MainItemClass.SHIELD, "⛨", ModItemTags.SHIELD, 0.5f, 0.25f, 0.25f),
  SHOVEL(MainItemClass.TOOL, "⚒", ModItemTags.TOOL_SHOVEL, 0, 0.75f, 0.5f),
  SPEAR(MainItemClass.MELEE_WEAPON, "╲", ModItemTags.MELEE_WEAPON_SPEAR, 0, 0.5f, 0.25f),
  STAFF(MainItemClass.MAGIC_WEAPON, "╲", ModItemTags.MAGIC_WEAPON_STAFF, 0, 0.5f, 0.25f),
  SWORD(MainItemClass.MELEE_WEAPON, "⚔", ModItemTags.MELEE_WEAPON_SWORD, 0, 0.5f, 0.25f),
  TACHI(MainItemClass.MELEE_WEAPON, "⚔", ModItemTags.MELEE_WEAPON_TACHI, 0, 1f, 0.25f),
  TRIDENT(
      MainItemClass.RANGED_WEAPON,
      "\uD83D\uDD31",
      ModItemTags.MELEE_WEAPON_TRIDENT,
      0,
      0.5f,
      0.25f),
  WAND(MainItemClass.MAGIC_WEAPON, "⚚", ModItemTags.MAGIC_WEAPON_WAND, 0, 0.5f, 0.5f),
  ;

  public final String translationId;
  private final String fontIcon;
  private final String textIcon;
  private final MainItemClass mainItemClass;
  private final TagKey<Item> tagKeyItem;
  private final float armorIncrease;
  private final float damageIncrease;
  private final float durabilityIncrease;
  private boolean isEnabled = false;

  ItemClass(
      MainItemClass mainItemClass,
      String textIcon,
      TagKey<Item> tagKeyItem,
      float armorIncrease,
      float damageIncrease,
      float durabilityIncrease) {
    this(
        mainItemClass,
        null,
        textIcon,
        tagKeyItem,
        armorIncrease,
        damageIncrease,
        durabilityIncrease);
  }

  ItemClass(
      MainItemClass mainItemClass,
      String fontIcon,
      String textIcon,
      TagKey<Item> tagKeyItem,
      float armorIncrease,
      float damageIncrease,
      float durabilityIncrease) {
    this.mainItemClass = mainItemClass;
    this.fontIcon = fontIcon;
    this.textIcon = textIcon;
    this.translationId = Constants.CLASS_TEXT_PREFIX + this.name().toLowerCase();
    this.tagKeyItem = tagKeyItem;
    this.armorIncrease = armorIncrease;
    this.damageIncrease = damageIncrease;
    this.durabilityIncrease = durabilityIncrease;
  }

  public TagKey<Item> tagKeyItem() {
    return this.tagKeyItem;
  }

  public MainItemClass getMainItemClass() {
    return this.mainItemClass;
  }

  public String getFontIcon() {
    return this.fontIcon;
  }

  public String getTextIcon() {
    return this.textIcon;
  }

  public boolean isEnabled() {
    return this.isEnabled;
  }

  public void setEnabled(boolean isEnabled) {
    this.isEnabled = isEnabled;
  }

  public TranslatableComponent getTranslatedText() {
    return new TranslatableComponent(this.translationId);
  }

  public MutableComponent getIcon() {
    return this.fontIcon != null
        ? new TextComponent(this.fontIcon).withStyle(Style.EMPTY.withFont(Constants.ICONS_FONT))
        : null;
  }

  public float getArmorIncrease() {
    return this.armorIncrease;
  }

  public float getDamageIncrease() {
    return this.damageIncrease;
  }

  public float getDurabilityIncrease() {
    return this.durabilityIncrease;
  }
}
