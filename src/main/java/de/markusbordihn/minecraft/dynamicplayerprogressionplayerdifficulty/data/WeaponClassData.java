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

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import net.minecraftforge.event.level.LevelEvent;
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
  private static boolean init = false;

  protected WeaponClassData() {}

  @SubscribeEvent
  public static void handleWorldEventLoad(LevelEvent.Load event) {
    if (!init) {
      log.info("Mapping weapon items to weapon categories");
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
      return weaponClassMap.getOrDefault(ForgeRegistries.ITEMS.getKey(item).toString(), null);
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

  private static void processConfigItems(List<String> itemNames, Set<Item> targetedItemSet,
      WeaponClass weaponClass) {
    if (itemNames.isEmpty()) {
      return;
    }
    targetedItemSet.clear();
    log.info("Try to mapping about {} items for the weapon class {} ...", itemNames.size(),
        weaponClass);
    for (String itemName : itemNames) {
      if (!itemName.isBlank()) {
        ResourceLocation resourceLocation = new ResourceLocation(itemName);
        Item item = ForgeRegistries.ITEMS.getValue(resourceLocation);

        // Ignore all items which are unknown or mapped to air.
        if (item != null && !item.equals(Items.AIR)) {
          log.info("[Weapon Class {}] Mapped {} to {}", weaponClass, itemName, item);
          targetedItemSet.add(item);
          weaponClassMap.put(itemName, weaponClass);
        } else {
          log.debug("Skipping item {}, not found.", itemName);
        }
      }
    }
  }

}
