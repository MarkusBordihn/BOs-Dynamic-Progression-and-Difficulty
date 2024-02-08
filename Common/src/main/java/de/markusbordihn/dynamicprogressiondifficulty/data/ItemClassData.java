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
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemFrameItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.SpawnEggItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ItemClassData {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);
  private static final Map<Item, ItemClass> itemClassMap = new HashMap<>();
  private static final EnumMap<ItemClass, Set<Item>> itemClassItems =
      new EnumMap<>(ItemClass.class);
  private static boolean isRegistered = false;

  // Pre-fill item class with default hash sets.
  static {
    for (ItemClass itemClass : ItemClass.values()) {
      itemClassItems.putIfAbsent(itemClass, new HashSet<>());
    }
  }

  protected ItemClassData() {}

  public static void registerItemClasses() {
    if (isRegistered) {
      return;
    }

    long startTime = System.currentTimeMillis();

    log.info("Pre-Mapping items to categories ...");
    for (Entry<ResourceKey<Item>, Item> itemEntry : Registry.ITEM.entrySet()) {
      ResourceKey<Item> resourceKey = itemEntry.getKey();
      Item item = itemEntry.getValue();

      // Ignore items which are not enabled or are not relevant for the item class mapping.
      if (item == null
          || item.equals(Items.AIR)
          || item instanceof BlockItem
          || item instanceof SpawnEggItem
          || item instanceof DyeItem
          || item instanceof ItemFrameItem
          || item instanceof RecordItem
          || item.isEdible()
          || item.getDefaultInstance().is(ModItemTags.IGNORED_ITEMS)) {
        continue;
      }

      String itemName = resourceKey.location().toString();

      // 1. Mapping items to categorize by tags.
      ItemClass tagItemClass = ItemClassHelper.getItemClassByTag(item);
      if (tagItemClass != null) {
        log.info("Item {} was mapped to category {} by tag.", itemName, tagItemClass);
        itemClassMap.put(item, tagItemClass);
        itemClassItems.get(tagItemClass).add(item);
        tagItemClass.setEnabled(true);
        continue;
      }

      // 2. Mapping items by name

      // 3. Mapping items by item instance
      ItemClass instanceItemClass = ItemClassHelper.getItemClassByItemInstance(item);
      if (instanceItemClass != null) {
        log.info("Item {} was mapped to category {} by instance.", itemName, instanceItemClass);
        itemClassMap.put(item, instanceItemClass);
        itemClassItems.get(instanceItemClass).add(item);
        instanceItemClass.setEnabled(true);
        continue;
      }

      // Give up and ignore item
      log.info("Unable to map item {} to a category.", itemName);
    }

    log.info(
        "Finished mapping items to categories in {} ms.", System.currentTimeMillis() - startTime);
    isRegistered = true;
  }

  public static ItemClass getItemClass(ItemStack itemStack) {
    if (itemStack == null || itemStack.isEmpty()) {
      return null;
    }
    if (!isRegistered) {
      registerItemClasses();
    }
    return getItemClass(itemStack.getItem());
  }

  public static ItemClass getItemClassForServerPlayer(ServerPlayer serverPlayer) {
    // Validate server player
    if (serverPlayer == null) {
      return null;
    }

    // Validate player holding item
    ItemStack handItemStack = serverPlayer.getItemInHand(InteractionHand.MAIN_HAND);
    if (handItemStack.isEmpty()) {
      return null;
    }

    // Validate item class
    ItemClass itemClass = getItemClass(handItemStack);
    if (itemClass == null || !itemClass.isEnabled()) {
      return null;
    }

    return itemClass;
  }

  public static ItemClass getItemClass(Item item) {
    if (item == null) {
      return null;
    }
    return itemClassMap.get(item);
  }

  public static Set<Item> getItemClassItems(ItemClass itemClass) {
    if (itemClass == null) {
      return null;
    }
    return itemClassItems.get(itemClass);
  }
}
