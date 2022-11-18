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

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;

import de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.Constants;
import de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.data.Experience;
import de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.data.PlayerData;
import de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.data.PlayerDataManager;
import de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.data.WeaponClass;

public class StatsCommand extends CustomCommand {
  private static final StatsCommand command = new StatsCommand();

  public static ArgumentBuilder<CommandSourceStack, ?> register() {
    return Commands.literal("stats").requires(cs -> cs.hasPermission(0)).executes(command);
  }

  @Override
  public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
    ServerPlayer player = context.getSource().getPlayerOrException();
    PlayerData playerData = PlayerDataManager.getPlayer(player);

    // Update stats to make sure we should current values
    playerData.updateStats();
    sendFeedback(context,
        Component.translatable(Constants.STATS_CMD_TEXT_PREFIX, player.getName().getString()));

    // General
    sendFeedback(context,
        Component.translatable(Constants.STATS_CMD_TEXT_PREFIX + "penalty",
            playerData.getNumberOfDeaths(),
            playerData.getNumberOfDeaths() * Experience.getExperienceDeathPenalty(),
            playerData.getNumberOfDeaths() * Experience.getExperienceDeathPenaltyItems()));

    // Damage Levels
    sendFeedback(context,
        Component.translatable(Constants.STATS_CMD_TEXT_PREFIX + "damage_level_mob",
            playerData.getDamageLevelMob(), playerData.getDamageExperienceMob(),
            playerData.getDealtDamageAdjustmentMob(), playerData.getHurtDamageAdjustmentMob()));
    if (playerData.getPvPEnabled()) {
      sendFeedback(context,
          Component.translatable(Constants.STATS_CMD_TEXT_PREFIX + "damage_level_player",
              playerData.getDamageLevelPlayer(), playerData.getDamageExperiencePlayer(),
              playerData.getDealtDamageAdjustmentPlayer(),
              playerData.getHurtDamageAdjustmentPlayer()));
    }

    // Weapon Classes Stats
    sendFeedback(context,
        Component.translatable(Constants.STATS_CMD_TEXT_PREFIX + "weapon_classes"));
    for (WeaponClass weaponClass : WeaponClass.values()) {
      float damageAdjustment = playerData.getWeaponClassDamageAdjustment(weaponClass);
      float durabilityAdjustment = playerData.getWeaponClassDurabilityAdjustment(weaponClass);
      int weaponClassLevel = playerData.getWeaponClassLevel(weaponClass);
      sendFeedback(context, Component.translatable(Constants.STATS_CMD_TEXT_PREFIX + "weapon_class",
          Component.literal(weaponClass.textIcon).withStyle(ChatFormatting.BLUE),
          weaponClass.text.withStyle(ChatFormatting.RESET), weaponClassLevel,
          Experience.getMaxLevel(), playerData.getWeaponClassExperience(weaponClass),
          Experience.getExperienceForNextLevel(weaponClassLevel),
          Component
              .literal(
                  String.format("+%.4s%%", damageAdjustment > 0 ? (damageAdjustment - 1) * 100 : 0))
              .setStyle(Style.EMPTY
                  .withColor(damageAdjustment > 0 ? ChatFormatting.GREEN : ChatFormatting.GRAY)),
          Component
              .literal(String.format("+%.4s%%",
                  durabilityAdjustment > 0 ? (durabilityAdjustment - 1) * 100 : 0))
              .setStyle(Style.EMPTY.withColor(
                  durabilityAdjustment > 0 ? ChatFormatting.GREEN : ChatFormatting.GRAY))));
    }

    return 0;
  }

}
