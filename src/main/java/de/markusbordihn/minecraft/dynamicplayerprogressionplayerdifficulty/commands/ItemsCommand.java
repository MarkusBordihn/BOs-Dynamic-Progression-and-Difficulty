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

    List<String> axeItems = new ArrayList<>();
    List<String> bowItems = new ArrayList<>();
    List<String> crossbowItems = new ArrayList<>();
    List<String> pickaxeItems = new ArrayList<>();
    List<String> shieldItems = new ArrayList<>();
    List<String> swordItems = new ArrayList<>();

    Iterator<Entry<ResourceKey<Item>, Item>> itemsIterator =
        ForgeRegistries.ITEMS.getEntries().iterator();
    while (itemsIterator.hasNext()) {
      Entry<ResourceKey<Item>, Item> itemEntry = itemsIterator.next();
      ResourceKey<Item> resourceKey = itemEntry.getKey();
      String resourceLocation = resourceKey.location().toString();
      if (resourceLocation.endsWith("_axe")) {
        axeItems.add("\""+ resourceLocation + "\"");
      } else if (resourceLocation.endsWith("_bow") || "minecraft:bow".equals(resourceLocation)) {
        bowItems.add("\""+ resourceLocation + "\"");
      } else if (resourceLocation.endsWith("_crossbow")
          || "minecraft:crossbow".equals(resourceLocation)) {
        crossbowItems.add("\""+ resourceLocation + "\"");
      } else if (resourceLocation.endsWith("_pickaxe")) {
        pickaxeItems.add("\""+ resourceLocation + "\"");
      } else if (resourceLocation.endsWith("_shield")
          || "minecraft:shield".equals(resourceLocation)) {
        shieldItems.add("\""+ resourceLocation + "\"");
      } else if (resourceLocation.endsWith("_sword")) {
        swordItems.add("\""+ resourceLocation + "\"");
      }
      log.info("Item: {}", resourceLocation);
    }

    log.info("Axe Items: {}", axeItems);
    log.info("Bow Items: {}", bowItems);
    log.info("Crossbow Items: {}", crossbowItems);
    log.info("Pickaxe Items: {}", pickaxeItems);
    log.info("Shield Items: {}", shieldItems);
    log.info("Sword Items: {}", swordItems);

    return 0;
  }

}
