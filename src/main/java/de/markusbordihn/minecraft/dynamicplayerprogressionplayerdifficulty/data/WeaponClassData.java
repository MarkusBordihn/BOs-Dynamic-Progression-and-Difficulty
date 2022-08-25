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

import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TridentItem;

import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;

import de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.Constants;
import de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.config.CommonConfig;

@EventBusSubscriber
public class WeaponClassData {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  private static final CommonConfig.Config COMMON = CommonConfig.COMMON;

  // Weapon Class to Item mapping
  private static Map<String, WeaponClass> weaponClassMap = new HashMap<>();

  // Enabled weapon class
  private static EnumMap<WeaponClass, Boolean> weaponClassEnabled =
      new EnumMap<>(WeaponClass.class);

  // Storage for weapon class
  private static EnumMap<WeaponClass, Set<Item>> weaponClassItems =
      new EnumMap<>(WeaponClass.class);
  private static List<String> ignoredItems = Arrays.asList();

  // Other
  private static boolean init = false;

  // Pre-fill weapon class with default hash sets.
  static {
    for (WeaponClass weaponClass : WeaponClass.values()) {
      weaponClassEnabled.putIfAbsent(weaponClass, false);
      weaponClassItems.putIfAbsent(weaponClass, new HashSet<>());
    }
  }

  protected WeaponClassData() {}

  @SubscribeEvent
  public static void handleServerAboutToStartEvent(ServerAboutToStartEvent event) {
    mappingWeaponClasses();

    // Show weapon class mapping
    for (WeaponClass weaponClass : WeaponClass.values()) {
      Set<Item> itemSet = weaponClassItems.get(weaponClass);
      log.info("[Weapon Class {}] Mapped items: {}", weaponClass, itemSet);
    }
  }

  @SubscribeEvent
  public static void handleWorldEventLoad(WorldEvent.Load event) {
    if (event.getWorld().isClientSide() && !init) {
      mappingWeaponClasses();
      init = true;
    }
  }

  public static void mappingWeaponClasses() {
    long startTime = System.currentTimeMillis();

    log.info("Mapping weapon items to weapon categories ...");

    log.info("Ignoring the following items for the weapon categories: {}",
        WeaponClassDataInternal.ignoredItems);
    ignoredItems = COMMON.weaponClassIgnoredItems.get();
    if (ignoredItems != null && !ignoredItems.isEmpty()) {
      log.info(
          "Additional ignoring the following items according the config for the weapon categories: {}",
          ignoredItems);
    }

    log.info("Set default values for weapon class ...");
    for (WeaponClass weaponClass : WeaponClass.values()) {
      weaponClassItems.putIfAbsent(weaponClass, new HashSet<>());
    }

    log.info("Using config entries for mapping ...");
    processConfigItems(COMMON.axeItems.get(), WeaponClass.AXE);
    processConfigItems(COMMON.bowItems.get(), WeaponClass.BOW);
    processConfigItems(COMMON.crossbowItems.get(), WeaponClass.CROSSBOW);
    processConfigItems(COMMON.daggerItems.get(), WeaponClass.DAGGER);
    processConfigItems(COMMON.greatSwordItems.get(), WeaponClass.GREAT_SWORD);
    processConfigItems(COMMON.gunItems.get(), WeaponClass.GUN);
    processConfigItems(COMMON.hammerItems.get(), WeaponClass.HAMMER);
    processConfigItems(COMMON.handToHandItems.get(), WeaponClass.HAND_TO_HAND);
    processConfigItems(COMMON.hoeItems.get(), WeaponClass.HOE);
    processConfigItems(COMMON.katanaItems.get(), WeaponClass.KATANA);
    processConfigItems(COMMON.keybladeItems.get(), WeaponClass.KEYBLADE);
    processConfigItems(COMMON.pickaxeItems.get(), WeaponClass.PICKAXE);
    processConfigItems(COMMON.polearmItems.get(), WeaponClass.POLEARM);
    processConfigItems(COMMON.scytheItems.get(), WeaponClass.SCYTHE);
    processConfigItems(COMMON.shieldItems.get(), WeaponClass.SHIELD);
    processConfigItems(COMMON.shovelItems.get(), WeaponClass.SHOVEL);
    processConfigItems(COMMON.staffItems.get(), WeaponClass.STAFF);
    processConfigItems(COMMON.swordItems.get(), WeaponClass.SWORD);
    processConfigItems(COMMON.tachiItems.get(), WeaponClass.TACHI);
    processConfigItems(COMMON.wandItems.get(), WeaponClass.WAND);

    log.info("Using registry for extended mapping like dagger, great sword, gun, ...");
    processRegistryItems(WeaponClass.DAGGER);
    processRegistryItems(WeaponClass.GREAT_SWORD);
    processRegistryItems(WeaponClass.GUN);
    processRegistryItems(WeaponClass.HAMMER);
    processRegistryItems(WeaponClass.HAND_TO_HAND);
    processRegistryItems(WeaponClass.KATANA);
    processRegistryItems(WeaponClass.KEYBLADE);
    processRegistryItems(WeaponClass.POLEARM, TridentItem.class);
    processRegistryItems(WeaponClass.SCYTHE);
    processRegistryItems(WeaponClass.STAFF);
    processRegistryItems(WeaponClass.TACHI);
    processRegistryItems(WeaponClass.WAND);

    log.info("Using registry for classic mapping like axe, bow, crossbow, ...");
    processRegistryItems(WeaponClass.AXE, AxeItem.class);
    processRegistryItems(WeaponClass.BOW, BowItem.class);
    processRegistryItems(WeaponClass.CROSSBOW, CrossbowItem.class);
    processRegistryItems(WeaponClass.HOE, HoeItem.class);
    processRegistryItems(WeaponClass.PICKAXE, PickaxeItem.class);
    processRegistryItems(WeaponClass.SHIELD, ShieldItem.class);
    processRegistryItems(WeaponClass.SHOVEL, ShovelItem.class);
    processRegistryItems(WeaponClass.SWORD, SwordItem.class);

    log.info("Weapon class mapping for about {} items to {} weapons and {} classes took {}ms.",
        ForgeRegistries.ITEMS.getEntries().size(), weaponClassMap.size(),
        WeaponClass.values().length, System.currentTimeMillis() - startTime);
  }

  public static WeaponClass getWeaponClass(Item item) {
    if (item != null) {
      return weaponClassMap.getOrDefault(item.getRegistryName().toString(), null);
    }
    return null;
  }

  public static boolean isWeaponClassEnabled(WeaponClass weaponClass) {
    return weaponClassEnabled.get(weaponClass);
  }

  public static Set<Item> getItems(WeaponClass weaponClass) {
    return weaponClassItems.get(weaponClass);
  }

  private static void processRegistryItems(WeaponClass weaponClass) {
    processRegistryItems(weaponClass, null);
  }

  private static void processRegistryItems(WeaponClass weaponClass, Class<?> itemTypeClass) {
    Set<Item> itemSet = weaponClassItems.get(weaponClass);
    Set<String> itemNames = WeaponClassDataInternal.weaponClassItemsNames.get(weaponClass);
    Set<String> itemSuffixes = WeaponClassDataInternal.weaponClassItemsSuffixes.get(weaponClass);
    Set<String> itemKeywords = WeaponClassDataInternal.weaponClassItemsKeywords.get(weaponClass);

    Iterator<Entry<ResourceKey<Item>, Item>> itemsIterator =
        ForgeRegistries.ITEMS.getEntries().iterator();
    while (itemsIterator.hasNext()) {
      Entry<ResourceKey<Item>, Item> itemEntry = itemsIterator.next();
      ResourceKey<Item> resourceKey = itemEntry.getKey();
      Item item = itemEntry.getValue();
      String itemName = resourceKey.location().toString();

      // Ignore all items which are unknown or mapped to air.
      if (item != null && !item.equals(Items.AIR)
          && !WeaponClassDataInternal.ignoredItems.contains(itemName)
          && !weaponClassMap.containsKey(itemName)) {

        boolean isMapped = false;

        // Map the item over the item name directly.
        if (itemNames != null && !itemNames.isEmpty()) {
          for (String match : itemNames) {
            if (!match.isEmpty() && itemName.equals(match)) {
              isMapped = addItemToWeaponClass(item, itemName, itemSet, weaponClass);
              break;
            }
          }
        }

        // Ignore Items which are matching the ignore suffix list like "_cast".
        if (!isMapped && WeaponClassDataInternal.ignoredItemsSuffix != null
            && !WeaponClassDataInternal.ignoredItemsSuffix.isEmpty()) {
          for (String ignoreSuffix : WeaponClassDataInternal.ignoredItemsSuffix) {
            if (!ignoreSuffix.isEmpty() && itemName.endsWith(ignoreSuffix)) {
              isMapped = true;
              break;
            }
          }
        }

        // Map the item over the item type class.
        if (!isMapped && itemTypeClass != null && itemTypeClass.isInstance(item)) {
          isMapped = addItemToWeaponClass(item, itemName, itemSet, weaponClass);
          continue;
        }

        // Map the item over a suffix.
        if (!isMapped && itemSuffixes != null && !itemSuffixes.isEmpty()) {
          for (String suffix : itemSuffixes) {
            if (!suffix.isEmpty() && itemName.endsWith(suffix)) {
              isMapped = addItemToWeaponClass(item, itemName, itemSet, weaponClass);
              break;
            }
          }
        }

        // Map the item over the keyword.
        if (!isMapped && itemKeywords != null && !itemKeywords.isEmpty()) {
          for (String keyword : itemKeywords) {
            if (!keyword.isEmpty() && itemName.contains(keyword)) {
              isMapped = addItemToWeaponClass(item, itemName, itemSet, weaponClass);
              break;
            }
          }
        }

      }
    }
  }

  private static void processConfigItems(List<String> itemNames, WeaponClass weaponClass) {
    if (itemNames.isEmpty()) {
      return;
    }
    Set<Item> itemSet = weaponClassItems.get(weaponClass);
    log.info("Try to mapping about {} items for the weapon class {} ...", itemNames.size(),
        weaponClass);
    for (String itemName : itemNames) {
      if (!itemName.isBlank()) {
        ResourceLocation resourceLocation = new ResourceLocation(itemName);
        Item item = ForgeRegistries.ITEMS.getValue(resourceLocation);

        // Ignore all items which are unknown or mapped to air.
        if (item != null && !item.equals(Items.AIR) && !weaponClassMap.containsKey(itemName)) {
          addItemToWeaponClass(item, itemName, itemSet, weaponClass);
        } else {
          log.debug("Skipping item {}, not found.", itemName);
        }
      }
    }
  }

  private static boolean addItemToWeaponClass(Item item, String itemName, Set<Item> targetedItemSet,
      WeaponClass weaponClass) {
    if (ignoredItems != null && !ignoredItems.isEmpty() && ignoredItems.contains(itemName)) {
      log.warn("[Weapon Class {}] Ignore {}", weaponClass, itemName);
    } else if (targetedItemSet.contains(item)) {
      log.warn("[Weapon Class {}] Warning duplicated mapping for {} inside the same weapon class!",
          weaponClass, itemName);
    } else if (weaponClassMap.containsKey(itemName)) {
      log.warn("[Weapon Class {}] Warning duplicated mapping for {}!", weaponClass, itemName);
    } else {
      log.debug("[Weapon Class {}] Mapped {}", weaponClass, itemName);
      targetedItemSet.add(item);
      weaponClassMap.put(itemName, weaponClass);
      if (Boolean.FALSE.equals(weaponClassEnabled.get(weaponClass))) {
        weaponClassEnabled.put(weaponClass, true);
      }
      return true;
    }
    return false;
  }

}
