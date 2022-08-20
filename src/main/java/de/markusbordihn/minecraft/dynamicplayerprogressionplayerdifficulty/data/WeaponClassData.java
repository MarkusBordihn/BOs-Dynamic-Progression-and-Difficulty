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
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TridentItem;

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

  // Storage for item class
  private static Set<Item> axeItems = new HashSet<>();
  private static Set<Item> bowItems = new HashSet<>();
  private static Set<Item> crossbowItems = new HashSet<>();
  private static Set<Item> daggerItems = new HashSet<>();
  private static Set<Item> greatSwordItems = new HashSet<>();
  private static Set<Item> gunItems = new HashSet<>();
  private static Set<Item> hammerItems = new HashSet<>();
  private static Set<Item> hoeItems = new HashSet<>();
  private static Set<Item> keybladeItems = new HashSet<>();
  private static Set<Item> pickaxeItems = new HashSet<>();
  private static Set<Item> shieldItems = new HashSet<>();
  private static Set<Item> spearItems = new HashSet<>();
  private static Set<Item> swordItems = new HashSet<>();
  private static List<String> ignoredItems = Arrays.asList();

  // Other
  private static boolean init = false;

  protected WeaponClassData() {}

  @SubscribeEvent
  public static void handleWorldEventLoad(WorldEvent.Load event) {
    if (!init) {
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

      log.info("Clearing item lists ...");
      axeItems.clear();
      bowItems.clear();
      crossbowItems.clear();
      daggerItems.clear();
      greatSwordItems.clear();
      gunItems.clear();
      hammerItems.clear();
      hoeItems.clear();
      keybladeItems.clear();
      pickaxeItems.clear();
      shieldItems.clear();
      spearItems.clear();
      swordItems.clear();

      log.info("Using config entries for mapping ...");
      processConfigItems(COMMON.axeItems.get(), axeItems, WeaponClass.AXE);
      processConfigItems(COMMON.bowItems.get(), bowItems, WeaponClass.BOW);
      processConfigItems(COMMON.crossbowItems.get(), crossbowItems, WeaponClass.CROSSBOW);
      processConfigItems(COMMON.daggerItems.get(), daggerItems, WeaponClass.DAGGER);
      processConfigItems(COMMON.greatSwordItems.get(), greatSwordItems, WeaponClass.GREAT_SWORD);
      processConfigItems(COMMON.gunItems.get(), gunItems, WeaponClass.GUN);
      processConfigItems(COMMON.hammerItems.get(), hammerItems, WeaponClass.HAMMER);
      processConfigItems(COMMON.hoeItems.get(), hoeItems, WeaponClass.HOE);
      processConfigItems(COMMON.keybladeItems.get(), keybladeItems, WeaponClass.KEYBLADE);
      processConfigItems(COMMON.pickaxeItems.get(), pickaxeItems, WeaponClass.PICKAXE);
      processConfigItems(COMMON.shieldItems.get(), shieldItems, WeaponClass.SHIELD);
      processConfigItems(COMMON.spearItems.get(), spearItems, WeaponClass.SPEAR);
      processConfigItems(COMMON.swordItems.get(), swordItems, WeaponClass.SWORD);

      log.info("Using registry for extended mapping like dagger, great sword, gun, ...");
      processRegistryItems(daggerItems, WeaponClass.DAGGER, WeaponClassDataInternal.daggerItems,
          WeaponClassDataInternal.daggerItemsSuffix, WeaponClassDataInternal.daggerItemsKeywords);
      processRegistryItems(greatSwordItems, WeaponClass.GREAT_SWORD,
          WeaponClassDataInternal.greatSwordItems, WeaponClassDataInternal.greatSwordItemsSuffix,
          WeaponClassDataInternal.greatSwordItemsKeywords);
      processRegistryItems(gunItems, WeaponClass.GUN, WeaponClassDataInternal.gunItems,
          WeaponClassDataInternal.gunItemsSuffix, WeaponClassDataInternal.gunItemsKeywords);
      processRegistryItems(hammerItems, WeaponClass.HAMMER, WeaponClassDataInternal.hammerItems,
          WeaponClassDataInternal.hammerItemsSuffix, WeaponClassDataInternal.hammerItemsKeywords);
      processRegistryItems(keybladeItems, WeaponClass.KEYBLADE,
          WeaponClassDataInternal.keybladeItems, WeaponClassDataInternal.keybladeItemsSuffix,
          WeaponClassDataInternal.keybladeItemsKeywords);
      processRegistryItems(spearItems, WeaponClass.SPEAR, TridentItem.class,
          WeaponClassDataInternal.spearItems, WeaponClassDataInternal.spearItemsSuffix,
          WeaponClassDataInternal.spearItemsKeywords);

      log.info("Using registry for classic mapping like axe, bow, crossbow, ...");
      processRegistryItems(axeItems, WeaponClass.AXE, AxeItem.class,
          WeaponClassDataInternal.axeItems, WeaponClassDataInternal.axeItemsSuffix,
          WeaponClassDataInternal.axeItemsKeywords);
      processRegistryItems(bowItems, WeaponClass.BOW, BowItem.class,
          WeaponClassDataInternal.bowItems, WeaponClassDataInternal.bowItemsSuffix,
          WeaponClassDataInternal.bowItemsKeywords);
      processRegistryItems(crossbowItems, WeaponClass.CROSSBOW, CrossbowItem.class,
          WeaponClassDataInternal.crossbowItems, WeaponClassDataInternal.crossbowItemsSuffix,
          WeaponClassDataInternal.crossbowItemsKeywords);
      processRegistryItems(hoeItems, WeaponClass.HOE, HoeItem.class,
          WeaponClassDataInternal.hoeItems, WeaponClassDataInternal.hoeItemsSuffix,
          WeaponClassDataInternal.hoeItemsKeywords);
      processRegistryItems(pickaxeItems, WeaponClass.PICKAXE, PickaxeItem.class,
          WeaponClassDataInternal.pickaxeItems, WeaponClassDataInternal.pickaxeItemsSuffix,
          WeaponClassDataInternal.pickaxeItemsKeywords);
      processRegistryItems(shieldItems, WeaponClass.SHIELD, ShieldItem.class,
          WeaponClassDataInternal.shieldItems, WeaponClassDataInternal.shieldItemsSuffix,
          WeaponClassDataInternal.shieldItemsKeywords);
      processRegistryItems(swordItems, WeaponClass.SWORD, SwordItem.class,
          WeaponClassDataInternal.swordItems, WeaponClassDataInternal.swordItemsSuffix,
          WeaponClassDataInternal.swordItemsKeywords);


      log.info("Weapon class mapping for about {} items took {}ms.", weaponClassMap.size(),
          System.currentTimeMillis() - startTime);

      init = true;
    }
  }

  public static Set<Item> getDaggerItems() {
    return daggerItems;
  }

  public static Set<Item> getGreatSwordItems() {
    return greatSwordItems;
  }

  public static Set<Item> getGunItems() {
    return gunItems;
  }

  public static Set<Item> getHammerItems() {
    return hammerItems;
  }

  public static Set<Item> getHoeItems() {
    return hoeItems;
  }

  public static Set<Item> getKeybladeItems() {
    return keybladeItems;
  }

  public static Set<Item> getSpearItems() {
    return spearItems;
  }

  public static Set<Item> getAxeItems() {
    return axeItems;
  }

  public static Set<Item> getBowItems() {
    return bowItems;
  }

  public static Set<Item> getCrossbowItems() {
    return crossbowItems;
  }

  public static Set<Item> getPickaxeItems() {
    return pickaxeItems;
  }

  public static Set<Item> getShieldItems() {
    return shieldItems;
  }

  public static Set<Item> getSwordItems() {
    return swordItems;
  }

  public static WeaponClass getWeaponClass(Item item) {
    if (item != null) {
      return weaponClassMap.getOrDefault(item.getRegistryName().toString(), null);
    }
    return null;
  }

  public static boolean isValidItemForClass(Item item, Set<Item> searchItems) {
    // Otherwise search item in the full list.
    for (Item searchItem : searchItems) {
      if (searchItem.equals(item)) {
        return true;
      }
    }
    return false;
  }

  private static void processRegistryItems(Set<Item> targetedItemSet, WeaponClass weaponClass,
      List<String> matchList, List<String> suffixList, List<String> keywordList) {
    processRegistryItems(targetedItemSet, weaponClass, null, matchList, suffixList, keywordList);
  }

  private static void processRegistryItems(Set<Item> targetedItemSet, WeaponClass weaponClass,
      Class<?> itemTypeClass, List<String> matchList, List<String> suffixList,
      List<String> keywordList) {
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

        // 1. Map the item over the item name directly.
        if (matchList != null && !matchList.isEmpty()) {
          for (String match : matchList) {
            if (!match.isEmpty() && itemName.equals(match)) {
              isMapped = addItemToWeaponClass(item, itemName, targetedItemSet, weaponClass);
              break;
            }
          }
        }

        // 2. Map the item over the item type class.
        if (!isMapped && itemTypeClass != null && itemTypeClass.isInstance(item)) {
          isMapped = addItemToWeaponClass(item, itemName, targetedItemSet, weaponClass);
          continue;
        }

        // 3. Map the item over a suffix.
        if (!isMapped && suffixList != null && !suffixList.isEmpty()) {
          for (String suffix : suffixList) {
            if (!suffix.isEmpty() && itemName.endsWith(suffix)) {
              isMapped = addItemToWeaponClass(item, itemName, targetedItemSet, weaponClass);
              break;
            }
          }
        }

        // 4. Map the item over the keyword.
        if (!isMapped && keywordList != null && !keywordList.isEmpty()) {
          for (String keyword : keywordList) {
            if (!keyword.isEmpty() && itemName.contains(keyword)) {
              isMapped = addItemToWeaponClass(item, itemName, targetedItemSet, weaponClass);
              break;
            }
          }
        }

      }
    }
  }

  private static void processConfigItems(List<String> itemNames, Set<Item> targetedItemSet,
      WeaponClass weaponClass) {
    if (itemNames.isEmpty()) {
      return;
    }
    log.info("Try to mapping about {} items for the weapon class {} ...", itemNames.size(),
        weaponClass);
    for (String itemName : itemNames) {
      if (!itemName.isBlank()) {
        ResourceLocation resourceLocation = new ResourceLocation(itemName);
        Item item = ForgeRegistries.ITEMS.getValue(resourceLocation);

        // Ignore all items which are unknown or mapped to air.
        if (item != null && !item.equals(Items.AIR) && !weaponClassMap.containsKey(itemName)) {
          addItemToWeaponClass(item, itemName, targetedItemSet, weaponClass);
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
      log.info("[Weapon Class {}] Mapped {}", weaponClass, itemName);
      targetedItemSet.add(item);
      weaponClassMap.put(itemName, weaponClass);
      return true;
    }
    return false;
  }

}
