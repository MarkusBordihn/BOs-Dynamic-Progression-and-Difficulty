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
import de.markusbordihn.dynamicprogressiondifficulty.data.Experience;
import de.markusbordihn.dynamicprogressiondifficulty.data.ItemClass;
import de.markusbordihn.dynamicprogressiondifficulty.data.PlayerStats;
import de.markusbordihn.dynamicprogressiondifficulty.data.PlayerStatsManager;
import de.markusbordihn.dynamicprogressiondifficulty.data.PlayerStatsType;
import java.util.Map;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;

public class StatsCommand implements Command<CommandSourceStack> {

  private static final StatsCommand command = new StatsCommand();

  public static ArgumentBuilder<CommandSourceStack, ?> register() {
    return Commands.literal("stats")
        .requires(cs -> cs.hasPermission(0))
        .executes(command)
        .then(Commands.literal("player").executes(command::showPlayerStats))
        .then(Commands.literal("item").executes(command::showItemStats));
  }

  @Override
  public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
    CommandSourceStack commandSource = context.getSource();
    commandSource.sendSuccess(new TextComponent("stats command"), false);
    return 0;
  }

  public int showPlayerStats(CommandContext<CommandSourceStack> context)
      throws CommandSyntaxException {
    CommandSourceStack commandSource = context.getSource();
    ServerPlayer serverPlayer = commandSource.getPlayerOrException();
    PlayerStats playerStats = PlayerStatsManager.getPlayerStats(serverPlayer);
    commandSource.sendSuccess(
        new TranslatableComponent(Constants.STATS_CMD_TEXT_PREFIX, serverPlayer.getName()), false);
    for (Map.Entry<PlayerStatsType, Integer> entry : playerStats.getStatsMap().entrySet()) {
      PlayerStatsType playerStatsType = entry.getKey();
      float value = entry.getValue();
      if (playerStatsType == PlayerStatsType.INTERNAL_DEALT_DAMAGE_ADJUSTMENT_MOB
          || playerStatsType == PlayerStatsType.INTERNAL_DEALT_DAMAGE_ADJUSTMENT_PLAYER
          || playerStatsType == PlayerStatsType.INTERNAL_HURT_DAMAGE_ADJUSTMENT_MOB
          || playerStatsType == PlayerStatsType.INTERNAL_HURT_DAMAGE_ADJUSTMENT_PLAYER) {
        value = value / 100.0f;
      }
      commandSource.sendSuccess(new TextComponent(playerStatsType.name() + ": " + value), false);
    }
    return 0;
  }

  public int showItemStats(CommandContext<CommandSourceStack> context)
      throws CommandSyntaxException {
    CommandSourceStack commandSource = context.getSource();
    PlayerStats playerStats =
        PlayerStatsManager.getPlayerStats(commandSource.getPlayerOrException());
    commandSource.sendSuccess(
        new TranslatableComponent(Constants.STATS_CMD_TEXT_PREFIX + "weapon_classes"), false);
    for (ItemClass itemClass : ItemClass.values()) {
      float damageAdjustment = playerStats.getDamageModifier(itemClass);
      float durabilityAdjustment = playerStats.getDurabilityModifier(itemClass);
      int itemClassLevel = playerStats.getLevel(itemClass);
      commandSource.sendSuccess(
          new TranslatableComponent(
              Constants.STATS_CMD_TEXT_PREFIX + "weapon_class",
              new TextComponent(itemClass.getTextIcon()).withStyle(ChatFormatting.BLUE),
              itemClass.getTranslatedText().withStyle(ChatFormatting.RESET),
              itemClassLevel,
              Experience.getMaxLevel(),
              playerStats.getExperience(itemClass),
              Experience.getExperienceForNextLevel(itemClassLevel),
              new TextComponent(
                      String.format(
                          "+%.4s%%", damageAdjustment > 0 ? (damageAdjustment - 1) * 100 : 0))
                  .setStyle(
                      Style.EMPTY.withColor(
                          damageAdjustment > 0 ? ChatFormatting.GREEN : ChatFormatting.GRAY)),
              new TextComponent(String.format("+%.4s%%", durabilityAdjustment * 100))
                  .setStyle(
                      Style.EMPTY.withColor(
                          durabilityAdjustment > 0 ? ChatFormatting.GREEN : ChatFormatting.GRAY))),
          false);
    }

    return 0;
  }
}
