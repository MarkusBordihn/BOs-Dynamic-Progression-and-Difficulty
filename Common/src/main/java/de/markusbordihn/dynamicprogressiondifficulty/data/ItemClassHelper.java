package de.markusbordihn.dynamicprogressiondifficulty.data;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TridentItem;

public class ItemClassHelper {

  protected static final EnumMap<ItemClass, Set<String>> itemClassItemsKeywords =
      new EnumMap<>(ItemClass.class);

  static {
    // Pre-fill item class keywords with default hash sets.
    itemClassItemsKeywords.put(
        ItemClass.AXE,
        new HashSet<>(
            Set.of(
                "battleaxe",
                "battle_axe",
                "handaxe",
                "waraxe",
                "greataxe",
                "hatchet",
                "tomahawk")));
    itemClassItemsKeywords.put(ItemClass.BOOTS, new HashSet<>(Set.of("shoes", "footwear")));
    itemClassItemsKeywords.put(ItemClass.BOW, new HashSet<>(Set.of("longbow", "shortbow")));
    itemClassItemsKeywords.put(ItemClass.CHESTPLATE, new HashSet<>(Set.of("breastplate")));
    itemClassItemsKeywords.put(ItemClass.CLAW, new HashSet<>(Set.of("claws")));
    itemClassItemsKeywords.put(ItemClass.CLAYMORE, new HashSet<>(Set.of()));
    itemClassItemsKeywords.put(ItemClass.CROSSBOW, new HashSet<>(Set.of()));
    itemClassItemsKeywords.put(ItemClass.DAGGER, new HashSet<>(Set.of("dirk", "knife", "messer")));
    itemClassItemsKeywords.put(ItemClass.FIST, new HashSet<>(Set.of("knuckle")));
    itemClassItemsKeywords.put(
        ItemClass.GREAT_SWORD,
        new HashSet<>(
            Set.of(
                "bastardsword",
                "greatsword",
                "great_sword",
                "flamebladedsword",
                "zweihander",
                "longsword",
                "broadsword")));
    itemClassItemsKeywords.put(
        ItemClass.GUN,
        new HashSet<>(
            Set.of(
                "pistol",
                "revolver",
                "rifle",
                "shotgun",
                "magnum",
                "sniper",
                "blunderbuss",
                "musket")));
    itemClassItemsKeywords.put(
        ItemClass.HAMMER,
        new HashSet<>(Set.of("mallet", "sledgehammer", "warhammer", "heavywarhammer", "maul")));
    itemClassItemsKeywords.put(
        ItemClass.HAND_TO_HAND,
        new HashSet<>(Set.of("sai", "nunchaku", "tonfa", "kama", "kusarigama", "shuriken")));
    itemClassItemsKeywords.put(ItemClass.HELMET, new HashSet<>(Set.of("headgear", "headpiece")));
    itemClassItemsKeywords.put(ItemClass.HOE, new HashSet<>(Set.of()));
    itemClassItemsKeywords.put(ItemClass.KATANA, new HashSet<>(Set.of()));
    itemClassItemsKeywords.put(ItemClass.KEYBLADE, new HashSet<>(Set.of()));
    itemClassItemsKeywords.put(ItemClass.LEGGINGS, new HashSet<>(Set.of("greaves")));
    itemClassItemsKeywords.put(
        ItemClass.MACE, new HashSet<>(Set.of("heavymace", "club", "metal_club", "flail", "bat")));
    itemClassItemsKeywords.put(
        ItemClass.SPELL_BOOK, new HashSet<>(Set.of("grimoire", "weapon_book")));
    itemClassItemsKeywords.put(ItemClass.PAXEL, new HashSet<>(Set.of("aiot", "multitool")));
    itemClassItemsKeywords.put(ItemClass.PICKAXE, new HashSet<>(Set.of()));
    itemClassItemsKeywords.put(
        ItemClass.POLEARM, new HashSet<>(Set.of("ahlspiess", "ranseur", "pike")));
    itemClassItemsKeywords.put(ItemClass.SCYTHE, new HashSet<>(Set.of()));
    itemClassItemsKeywords.put(
        ItemClass.SHIELD,
        new HashSet<>(
            Set.of(
                "buckler",
                "targe",
                "kite_shield",
                "tower_shield",
                "tartsche",
                "roundshield",
                "rondache",
                "pavese")));
    itemClassItemsKeywords.put(ItemClass.SHOVEL, new HashSet<>(Set.of("excavator", "spade")));
    itemClassItemsKeywords.put(
        ItemClass.SPEAR,
        new HashSet<>(
            Set.of(
                "lance", "javelin", "glaive", "halberd", "voulge", "guisarme", "chivalrylance")));
    itemClassItemsKeywords.put(ItemClass.STAFF, new HashSet<>(Set.of("quarterstaff")));
    itemClassItemsKeywords.put(
        ItemClass.SWORD, new HashSet<>(Set.of("shortsword", "rapier", "gladius")));
    itemClassItemsKeywords.put(ItemClass.TACHI, new HashSet<>(Set.of()));
    itemClassItemsKeywords.put(ItemClass.TRIDENT, new HashSet<>(Set.of()));
    itemClassItemsKeywords.put(ItemClass.WAND, new HashSet<>(Set.of("scepter")));
  }

  protected ItemClassHelper() {}

  public static ItemClass getItemClassByTag(Item item) {
    if (item == null) {
      return null;
    }
    ItemStack itemStack = item.getDefaultInstance();
    for (ItemClass itemClass : ItemClass.values()) {
      TagKey<Item> tagKeyItem = itemClass.tagKeyItem();
      if (tagKeyItem != null && itemStack.is(itemClass.tagKeyItem())) {
        return itemClass;
      }
    }
    return null;
  }

  public static ItemClass getItemClassByItemInstance(Item item) {
    if (item == null) {
      return null;
    }
    if (item instanceof AxeItem) {
      return ItemClass.AXE;
    } else if (item instanceof BowItem) {
      return ItemClass.BOW;
    } else if (item instanceof CrossbowItem) {
      return ItemClass.CROSSBOW;
    } else if (item instanceof HoeItem) {
      return ItemClass.HOE;
    } else if (item instanceof PickaxeItem) {
      return ItemClass.PICKAXE;
    } else if (item instanceof ShieldItem) {
      return ItemClass.SHIELD;
    } else if (item instanceof SwordItem) {
      return ItemClass.SWORD;
    } else if (item instanceof ShovelItem) {
      return ItemClass.SHOVEL;
    } else if (item instanceof TridentItem) {
      return ItemClass.TRIDENT;
    } else if (item instanceof ArmorItem armorItem) {
      return switch (armorItem.getEquipmentSlot()) {
        case FEET -> ItemClass.BOOTS;
        case CHEST -> ItemClass.CHESTPLATE;
        case HEAD -> ItemClass.HELMET;
        case LEGS -> ItemClass.LEGGINGS;
        default -> null;
      };
    }
    return null;
  }

  public static ItemClass getItemClassByItemName(String itemName) {
    if (itemName == null) {
      return null;
    }

    // Remove namespace from item name, if present.
    if (itemName.contains(":")) {
      itemName = itemName.split(":")[1];
    }

    // Iterate over all item classes and check for matches with the item name.
    for (ItemClass itemClass : ItemClass.values()) {
      // Quick check for match with the item class name.
      String itemClassName = itemClass.name().toLowerCase();
      if (itemName.equals(itemClassName) || itemName.endsWith("_" + itemClassName)) {
        return itemClass;
      }

      // Check for keyword matche with the item name.
      Set<String> keywords = itemClassItemsKeywords.get(itemClass);
      if (keywords != null && !keywords.isEmpty()) {
        for (String keyword : keywords) {
          if (itemName.equals(keyword)
              || itemName.startsWith(keyword + "_")
              || itemName.endsWith("_" + keyword)) {
            return itemClass;
          }
        }
      }
    }
    return null;
  }
}
