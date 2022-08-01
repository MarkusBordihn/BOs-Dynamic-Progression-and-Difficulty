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

import java.util.Map;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

import de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.data.Experience;

public class LevelCommand extends CustomCommand {
  private static final LevelCommand command = new LevelCommand();

  public static ArgumentBuilder<CommandSourceStack, ?> register() {
    return Commands.literal("level").requires(cs -> cs.hasPermission(2)).executes(command)
        .then(Commands.literal("overview").executes(command::runOverview))
        .then(Commands.literal("recalculate").executes(command::runRecalculate));
  }

  @Override
  public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
    sendFeedback(context, "The level command which show you the current level definition.\n===");
    return 0;
  }

  public int runOverview(CommandContext<CommandSourceStack> context) {
    Map<Integer, Integer> experienceMap = Experience.getLevelExperienceMap();
    for (var entry : experienceMap.entrySet()) {
      sendFeedback(context, String.format("lvl %s : %s exp", entry.getKey(), entry.getValue()));
      log.info("Level overview: {}", experienceMap);
    }
    return 0;
  }

  public int runRecalculate(CommandContext<CommandSourceStack> context) {
    sendFeedback(context, "Not implemented yet!");
    return 0;
  }

}
