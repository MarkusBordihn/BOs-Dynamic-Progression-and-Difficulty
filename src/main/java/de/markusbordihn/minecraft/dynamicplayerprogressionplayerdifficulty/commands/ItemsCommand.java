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

package de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

import net.minecraftforge.registries.ForgeRegistries;

public class ItemsCommand extends CustomCommand {
  private static final ItemsCommand command = new ItemsCommand();

  public static ArgumentBuilder<CommandSourceStack, ?> register() {
    return Commands.literal("items").requires(cs -> cs.hasPermission(2)).executes(command);
  }

  @Override
  public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
    sendFeedback(context, "Item's output will be dumped into the latest.log logfile ...");

    List<String> items = new ArrayList<>();
    List<String> axeItems = new ArrayList<>();
    List<String> bowItems = new ArrayList<>();
    List<String> crossbowItems = new ArrayList<>();
    List<String> daggerItems = new ArrayList<>();
    List<String> greatSwordItems = new ArrayList<>();
    List<String> gunItems = new ArrayList<>();
    List<String> hammerItems = new ArrayList<>();
    List<String> hoeItems = new ArrayList<>();
    List<String> keybladeItems = new ArrayList<>();
    List<String> pickaxeItems = new ArrayList<>();
    List<String> shieldItems = new ArrayList<>();
    List<String> spearItems = new ArrayList<>();
    List<String> swordItems = new ArrayList<>();

    Iterator<Entry<ResourceKey<Item>, Item>> itemsIterator =
        ForgeRegistries.ITEMS.getEntries().iterator();
    while (itemsIterator.hasNext()) {
      Entry<ResourceKey<Item>, Item> itemEntry = itemsIterator.next();
      ResourceKey<Item> resourceKey = itemEntry.getKey();
      String resourceLocation = resourceKey.location().toString();
      // Axe
      if (resourceLocation.endsWith("_axe") || resourceLocation.contains("battle_axe")
          || resourceLocation.contains("battleaxe") || resourceLocation.contains("war_axe")
          || resourceLocation.contains("waraxe") || resourceLocation.contains(":axe")) {
        axeItems.add("\"" + resourceLocation + "\"");
      }

      // Bow
      else if (resourceLocation.endsWith("_bow") || resourceLocation.endsWith("longbow")
          || resourceLocation.contains(":bow") || "minecraft:bow".equals(resourceLocation)) {
        bowItems.add("\"" + resourceLocation + "\"");
      }

      // Crossbow
      else if (resourceLocation.endsWith("_crossbow") || resourceLocation.contains(":crossbow")
          || "minecraft:crossbow".equals(resourceLocation)) {
        crossbowItems.add("\"" + resourceLocation + "\"");
      }

      // Dagger
      else if (resourceLocation.endsWith("_dagger") || resourceLocation.endsWith("_knife")
          || resourceLocation.contains(":dagger") || resourceLocation.contains(":knife")) {
        daggerItems.add("\"" + resourceLocation + "\"");
      }

      // Great Sword
      else if (resourceLocation.endsWith("_claymore") || resourceLocation.endsWith("_zweihander")
          || resourceLocation.endsWith("_flamebladedsword")
          || resourceLocation.endsWith("_bastardsword") || resourceLocation.endsWith("_broadsword")
          || resourceLocation.contains(":greatsword")) {
        greatSwordItems.add("\"" + resourceLocation + "\"");
      }

      // Gun
      else if (resourceLocation.endsWith("_gun") || resourceLocation.endsWith("_gatling")
          || resourceLocation.endsWith("_snipper") || resourceLocation.endsWith("_rifle")
          || resourceLocation.endsWith("_pistol") || resourceLocation.endsWith("_railgun")
          || resourceLocation.endsWith("_shotgun")) {
        gunItems.add("\"" + resourceLocation + "\"");
      }

      // Hammer
      else if (resourceLocation.endsWith("_hammer") || resourceLocation.endsWith("hammer")
          || resourceLocation.contains(":hammer") || resourceLocation.contains(":warhammer")) {
        hammerItems.add("\"" + resourceLocation + "\"");
      }

      // Hoe
      else if (resourceLocation.endsWith("_hoe") || resourceLocation.contains(":hoe")) {
        hoeItems.add("\"" + resourceLocation + "\"");
      }

      // Keyblade
      else if (resourceLocation.endsWith("_keyblade") || resourceLocation.contains(":keyblade")) {
        keybladeItems.add("\"" + resourceLocation + "\"");
      }

      // Pickaxe
      else if (resourceLocation.endsWith("_pickaxe") || resourceLocation.contains(":pickaxe")) {
        pickaxeItems.add("\"" + resourceLocation + "\"");
      }

      // Shield
      else if (resourceLocation.endsWith("_shield") || resourceLocation.endsWith("_buckler")
          || resourceLocation.endsWith("_tartsche") || resourceLocation.endsWith("shield")
          || "minecraft:shield".equals(resourceLocation)) {
        shieldItems.add("\"" + resourceLocation + "\"");
      }

      // Spear
      else if (resourceLocation.endsWith("_spear") || resourceLocation.endsWith("_pike")
          || resourceLocation.endsWith("_ranseur") || resourceLocation.endsWith("_ahlspiess")
          || resourceLocation.contains(":spear") || "minecraft:trident".equals(resourceLocation)) {
        spearItems.add("\"" + resourceLocation + "\"");
      }

      // Sword
      else if (resourceLocation.endsWith("_sword") || resourceLocation.contains(":sword")
          || resourceLocation.contains("_sword_")) {
        swordItems.add("\"" + resourceLocation + "\"");
      }
      items.add("\"" + resourceLocation + "\"");
    }

    log.info("All Item: {}", items);
    log.info("========================");
    log.info("Axe Items: {}", axeItems);
    log.info("Bow Items: {}", bowItems);
    log.info("Crossbow Items: {}", crossbowItems);
    log.info("Dagger Items: {}", daggerItems);
    log.info("Great Sword Items: {}", greatSwordItems);
    log.info("Gun Items: {}", gunItems);
    log.info("Hammer Items: {}", hammerItems);
    log.info("Hoe Items: {}", hoeItems);
    log.info("Keyblade Items: {}", keybladeItems);
    log.info("Pickaxe Items: {}", pickaxeItems);
    log.info("Shield Items: {}", shieldItems);
    log.info("Spear Items: {}", spearItems);
    log.info("Sword Items: {}", swordItems);

    return 0;
  }

}
