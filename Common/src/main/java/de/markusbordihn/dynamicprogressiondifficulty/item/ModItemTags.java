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

package de.markusbordihn.dynamicprogressiondifficulty.item;

import de.markusbordihn.dynamicprogressiondifficulty.Constants;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {

  // Armor Tags
  public static final TagKey<Item> ARMOR_BOOTS =
      TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(Constants.MOD_ID, "armor/boots"));
  public static final TagKey<Item> ARMOR_CHESTPLATE =
      TagKey.create(
          Registry.ITEM_REGISTRY, new ResourceLocation(Constants.MOD_ID, "armor/chestplate"));
  public static final TagKey<Item> ARMOR_HELMET =
      TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(Constants.MOD_ID, "armor/helmet"));
  public static final TagKey<Item> ARMOR_LEGGINGS =
      TagKey.create(
          Registry.ITEM_REGISTRY, new ResourceLocation(Constants.MOD_ID, "armor/leggings"));

  // Magic Weapon Tags
  public static final TagKey<Item> MAGIC_WEAPON_SPELL_BOOK =
      TagKey.create(
          Registry.ITEM_REGISTRY,
          new ResourceLocation(Constants.MOD_ID, "magic_weapon/spell_book"));
  public static final TagKey<Item> MAGIC_WEAPON_STAFF =
      TagKey.create(
          Registry.ITEM_REGISTRY, new ResourceLocation(Constants.MOD_ID, "magic_weapon/staff"));
  public static final TagKey<Item> MAGIC_WEAPON_WAND =
      TagKey.create(
          Registry.ITEM_REGISTRY, new ResourceLocation(Constants.MOD_ID, "magic_weapon/wand"));

  // Melee Weapon Tags
  public static final TagKey<Item> MELEE_WEAPON_AXE =
      TagKey.create(
          Registry.ITEM_REGISTRY, new ResourceLocation(Constants.MOD_ID, "melee_weapon/axe"));
  public static final TagKey<Item> MELEE_WEAPON_CLAYMORE =
      TagKey.create(
          Registry.ITEM_REGISTRY, new ResourceLocation(Constants.MOD_ID, "melee_weapon/claymore"));
  public static final TagKey<Item> MELEE_WEAPON_DAGGER =
      TagKey.create(
          Registry.ITEM_REGISTRY, new ResourceLocation(Constants.MOD_ID, "melee_weapon/dagger"));
  public static final TagKey<Item> MELEE_WEAPON_GREAT_SWORD =
      TagKey.create(
          Registry.ITEM_REGISTRY,
          new ResourceLocation(Constants.MOD_ID, "melee_weapon/great_sword"));
  public static final TagKey<Item> MELEE_WEAPON_KATANA =
      TagKey.create(
          Registry.ITEM_REGISTRY, new ResourceLocation(Constants.MOD_ID, "melee_weapon/katana"));
  public static final TagKey<Item> MELEE_WEAPON_MACE =
      TagKey.create(
          Registry.ITEM_REGISTRY, new ResourceLocation(Constants.MOD_ID, "melee_weapon/mace"));
  public static final TagKey<Item> MELEE_WEAPON_POLEARM =
      TagKey.create(
          Registry.ITEM_REGISTRY, new ResourceLocation(Constants.MOD_ID, "melee_weapon/polearm"));
  public static final TagKey<Item> MELEE_WEAPON_SCYTHE =
      TagKey.create(
          Registry.ITEM_REGISTRY, new ResourceLocation(Constants.MOD_ID, "melee_weapon/scythe"));
  public static final TagKey<Item> MELEE_WEAPON_SPEAR =
      TagKey.create(
          Registry.ITEM_REGISTRY, new ResourceLocation(Constants.MOD_ID, "melee_weapon/spear"));
  public static final TagKey<Item> MELEE_WEAPON_SWORD =
      TagKey.create(
          Registry.ITEM_REGISTRY, new ResourceLocation(Constants.MOD_ID, "melee_weapon/sword"));
  public static final TagKey<Item> MELEE_WEAPON_TACHI =
      TagKey.create(
          Registry.ITEM_REGISTRY, new ResourceLocation(Constants.MOD_ID, "melee_weapon/tachi"));
  public static final TagKey<Item> MELEE_WEAPON_TRIDENT =
      TagKey.create(
          Registry.ITEM_REGISTRY, new ResourceLocation(Constants.MOD_ID, "melee_weapon/trident"));

  // Ranged Weapon Tags
  public static final TagKey<Item> RANGED_WEAPON_BOW =
      TagKey.create(
          Registry.ITEM_REGISTRY, new ResourceLocation(Constants.MOD_ID, "ranged_weapon/bow"));
  public static final TagKey<Item> RANGED_WEAPON_CROSSBOW =
      TagKey.create(
          Registry.ITEM_REGISTRY, new ResourceLocation(Constants.MOD_ID, "ranged_weapon/crossbow"));
  public static final TagKey<Item> RANGED_WEAPON_GUN =
      TagKey.create(
          Registry.ITEM_REGISTRY, new ResourceLocation(Constants.MOD_ID, "ranged_weapon/gun"));

  // Shield Tags
  public static final TagKey<Item> SHIELD =
      TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(Constants.MOD_ID, "shield"));

  // Special Weapon Tags
  public static final TagKey<Item> SPECIAL_WEAPON_CLAWS =
      TagKey.create(
          Registry.ITEM_REGISTRY, new ResourceLocation(Constants.MOD_ID, "special_weapon/claws"));
  public static final TagKey<Item> SPECIAL_WEAPON_HAMMER =
      TagKey.create(
          Registry.ITEM_REGISTRY, new ResourceLocation(Constants.MOD_ID, "special_weapon/hammer"));
  public static final TagKey<Item> SPECIAL_WEAPON_KEYBLADE =
      TagKey.create(
          Registry.ITEM_REGISTRY,
          new ResourceLocation(Constants.MOD_ID, "special_weapon/keyblade"));

  // Tool Tags
  public static final TagKey<Item> TOOL_HOE =
      TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(Constants.MOD_ID, "tool/hoe"));
  public static final TagKey<Item> TOOL_PAXEL =
      TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(Constants.MOD_ID, "tool/paxel"));
  public static final TagKey<Item> TOOL_PICKAXE =
      TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(Constants.MOD_ID, "tool/pickaxe"));
  public static final TagKey<Item> TOOL_SHOVEL =
      TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(Constants.MOD_ID, "tool/shovel"));

  // Other Tags
  public static final TagKey<Item> IGNORED_ITEMS =
      TagKey.create(
          Registry.ITEM_REGISTRY, new ResourceLocation(Constants.MOD_ID, "ignored_items"));

  ModItemTags() {}
}
