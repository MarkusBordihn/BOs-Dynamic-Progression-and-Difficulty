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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;

import de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.Constants;
import de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.config.CommonConfig;

@EventBusSubscriber
public class PlayerDataManager {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  private static final CommonConfig.Config COMMON = CommonConfig.COMMON;

  private static PlayerData localPlayerData = null;
  private static ConcurrentHashMap<UUID, PlayerData> playerMap = new ConcurrentHashMap<>();

  private static Set<Item> axeItems = new HashSet<>();
  private static Set<Item> bowItems = new HashSet<>();
  private static Set<Item> crossbowItems = new HashSet<>();
  private static Set<Item> pickaxeItems = new HashSet<>();
  private static Set<Item> shieldItems = new HashSet<>();
  private static Set<Item> swordItems = new HashSet<>();
  private static boolean init = false;

  protected PlayerDataManager() {}

  @SubscribeEvent
  public static void handleWorldEventLoad(WorldEvent.Load event) {
    if (!init) {

      if (!event.getWorld().isClientSide()) {
        log.info("Preparing Player Data Manager (server)...");
        playerMap = new ConcurrentHashMap<>();
      } else {
        log.info("Preparing Player Data Manager (client)...");
        localPlayerData = new PlayerData();
      }

      log.info("Mapping weapon items to weapon categories");
      processConfigItems(COMMON.axeItems.get(), axeItems);
      processConfigItems(COMMON.bowItems.get(), bowItems);
      processConfigItems(COMMON.crossbowItems.get(), crossbowItems);
      processConfigItems(COMMON.pickaxeItems.get(), pickaxeItems);
      processConfigItems(COMMON.shieldItems.get(), shieldItems);
      processConfigItems(COMMON.swordItems.get(), swordItems);
      init = true;
    }
  }

  public static PlayerData addPlayer(ServerPlayer player) {
    if (player == null) {
      return null;
    }
    PlayerData playerData = new PlayerData(player);
    playerMap.put(player.getUUID(), playerData);
    return playerData;
  }

  public static PlayerData getPlayer(ServerPlayer player) {
    return getPlayer(player.getUUID());
  }

  public static PlayerData getPlayer(UUID playerUUID) {
    if (playerUUID == null) {
      return null;
    }
    return playerMap.getOrDefault(playerUUID, null);
  }

  public static PlayerData getLocalPlayer() {
    return localPlayerData;
  }

  public static ConcurrentMap<UUID, PlayerData> getPlayerMap() {
    return playerMap;
  }

  public static void updatePlayer(ServerPlayer player) {
    updatePlayer(player.getUUID());
  }

  public static void updatePlayer(UUID playerUUID) {
    PlayerData playerData = getPlayer(playerUUID);
    if (playerData != null) {
      playerData.updateStats();
      PlayerServerDataClientSync.syncPlayerData(playerData);
    }
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

  public static boolean isAxeItem(Item item) {
    return isItemInItems(item, axeItems);
  }

  public static boolean isBowItem(Item item) {
    return isItemInItems(item, bowItems);
  }

  public static boolean isCrossbowItem(Item item) {
    return isItemInItems(item, crossbowItems);
  }

  public static boolean isPickaxeItem(Item item) {
    return isItemInItems(item, pickaxeItems);
  }

  public static boolean isShieldItem(Item item) {
    return isItemInItems(item, shieldItems);
  }

  public static boolean isSwordItem(Item item) {
    return isItemInItems(item, swordItems);
  }

  public static boolean isItemInItems(Item item, Set<Item> searchItems) {
    for (Item searchItem : searchItems) {
      if (searchItem.equals(item)) {
        return true;
      }
    }
    return false;
  }

  public static void loadClientData(CompoundTag compoundTag) {
    localPlayerData = new PlayerData(compoundTag);
  }

  private static void processConfigItems(List<String> itemNames, Set<Item> targetedItemSet) {
    targetedItemSet.clear();
    for (String itemName : itemNames) {
      if (!itemName.isBlank()) {
        ResourceLocation resourceLocation = new ResourceLocation(itemName);
        Item item = ForgeRegistries.ITEMS.getValue(resourceLocation);

        // Ignore all items which are unknown or mapped to air.
        if (item != null && !item.equals(Items.AIR)) {
          log.info("Mapped {} to {}", itemName, item);
          targetedItemSet.add(item);
        } else {
          log.debug("Skipping item {}, not found.", itemName);
        }
      }
    }
  }

}
