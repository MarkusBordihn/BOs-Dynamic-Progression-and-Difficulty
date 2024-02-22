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

package de.markusbordihn.dynamicprogressiondifficulty.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.markusbordihn.dynamicprogressiondifficulty.Constants;
import de.markusbordihn.dynamicprogressiondifficulty.data.ItemClass;
import de.markusbordihn.dynamicprogressiondifficulty.data.ItemClassData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegistryCommand implements Command<CommandSourceStack> {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  private static final RegistryCommand command = new RegistryCommand();

  public static ArgumentBuilder<CommandSourceStack, ?> register() {
    return Commands.literal("registry")
        .requires(cs -> cs.hasPermission(2))
        .executes(command)
        .then(Commands.literal("all").executes(command::showAllItems))
        .then(Commands.literal("mapped").executes(command::showMappedItems))
        .then(Commands.literal("unmapped").executes(command::showUnmappedItems));
  }

  @Override
  public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
    CommandSourceStack commandSource = context.getSource();
    commandSource.sendSuccess(Component.literal("registry command"), false);
    return 0;
  }

  public int showAllItems(CommandContext<CommandSourceStack> context) {
    CommandSourceStack commandSource = context.getSource();
    commandSource.sendSuccess(
        Component.literal("All relevant Item's will be dumped into the latest.log file ..."),
        false);

    List<String> items = new ArrayList<>();
    for (Entry<ResourceKey<Item>, Item> itemEntry : BuiltInRegistries.ITEM.entrySet()) {
      Item item = itemEntry.getValue();
      String itemName = itemEntry.getKey().location().toString();
      if (ItemClassData.isRelevantItem(item, itemName)) {
        items.add(itemEntry.getKey().location().toString());
      }
    }

    log.info("Relevant Items: {}", items);
    return 0;
  }

  public int showMappedItems(CommandContext<CommandSourceStack> context) {
    CommandSourceStack commandSource = context.getSource();
    commandSource.sendSuccess(
        Component.literal("All mapped Item's will be dumped into the latest.log file ..."), false);

    for (ItemClass itemClass : ItemClass.values()) {
      Set<Item> items = ItemClassData.getItemClassItems(itemClass);
      log.info("[{}] {}", itemClass, items);
    }
    return 0;
  }

  public int showUnmappedItems(CommandContext<CommandSourceStack> context) {
    CommandSourceStack commandSource = context.getSource();
    commandSource.sendSuccess(
        Component.literal("All unmapped Item's will be dumped into the latest.log file ..."),
        false);

    List<String> items = new ArrayList<>();
    for (Entry<ResourceKey<Item>, Item> itemEntry : BuiltInRegistries.ITEM.entrySet()) {
      Item item = itemEntry.getValue();
      String itemName = itemEntry.getKey().location().toString();
      if (ItemClassData.isRelevantItem(item, itemName)) {
        ItemClass itemClass = ItemClassData.getItemClass(item);
        if (itemClass == null) {
          items.add(itemEntry.getKey().location().toString());
        }
      }
    }

    log.info("Unmapped Items: {}", items);
    return 0;
  }
}
