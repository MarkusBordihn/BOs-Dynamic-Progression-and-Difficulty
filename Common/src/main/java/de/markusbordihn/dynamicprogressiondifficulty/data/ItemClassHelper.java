package de.markusbordihn.dynamicprogressiondifficulty.data;

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
      return switch (armorItem.getSlot()) {
        case FEET -> ItemClass.BOOTS;
        case CHEST -> ItemClass.CHESTPLATE;
        case HEAD -> ItemClass.HELMET;
        case LEGS -> ItemClass.LEGGINGS;
        default -> null;
      };
    }
    return null;
  }
}
