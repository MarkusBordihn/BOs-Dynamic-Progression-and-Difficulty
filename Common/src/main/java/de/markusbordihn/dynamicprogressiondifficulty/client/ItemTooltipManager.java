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

package de.markusbordihn.dynamicprogressiondifficulty.client;

import de.markusbordihn.dynamicprogressiondifficulty.Constants;
import de.markusbordihn.dynamicprogressiondifficulty.data.Experience;
import de.markusbordihn.dynamicprogressiondifficulty.data.ItemClass;
import de.markusbordihn.dynamicprogressiondifficulty.data.ItemClassData;
import de.markusbordihn.dynamicprogressiondifficulty.data.PlayerStats;
import de.markusbordihn.dynamicprogressiondifficulty.data.PlayerStatsManager;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class ItemTooltipManager {

  protected ItemTooltipManager() {}

  public static void handleItemTooltip(
      ItemStack itemStack, List<Component> tooltip, TooltipFlag tooltipFlag) {
    ItemClass itemClass = ItemClassData.getItemClass(itemStack);
    if (itemClass == null) {
      return;
    }

    PlayerStats playerStats = PlayerStatsManager.getLocalPlayerStats();
    int index = 1;

    // Add item class and icon.
    tooltip.add(index++, formatItemClass(itemClass));

    // Add item class level
    tooltip.add(index++, formatLevelAndExperience(itemClass));

    // Add damage adjustment, if any.
    float damageModifier = playerStats.getDamageModifier(itemClass);
    if (damageModifier != 0) {
      tooltip.add(index++, formatDamageAdjustment(damageModifier));
    }

    // Add durability adjustment, if any.
    float durabilityModifier = playerStats.getDurabilityModifier(itemClass);
    if (durabilityModifier != 0) {
      tooltip.add(index++, formatDurabilityAdjustment(durabilityModifier));
    }
  }

  private static MutableComponent formatLevelAndExperience(ItemClass itemClass) {
    PlayerStats playerStats = PlayerStatsManager.getLocalPlayerStats();
    int experienceLevel = playerStats.getLevel(itemClass);
    return Component.literal("")
        .append(
            Component.translatable(
                    Constants.TOOLTIP_TEXT_PREFIX + "level",
                    experienceLevel,
                    Experience.getMaxLevel())
                .withStyle(ChatFormatting.YELLOW))
        .append(
            Component.translatable(
                    Constants.TOOLTIP_TEXT_PREFIX + "level_experience",
                    playerStats.getExperience(itemClass),
                    Experience.getExperienceForNextLevel(experienceLevel) - 1)
                .withStyle(ChatFormatting.DARK_GREEN));
  }

  private static MutableComponent formatItemClass(ItemClass itemClass) {
    return Component.translatable(
            Constants.CLASS_TEXT_PREFIX,
            itemClass.getTranslatedText().withStyle(ChatFormatting.BLUE),
            itemClass.getIcon())
        .withStyle(ChatFormatting.GRAY);
  }

  private static MutableComponent formatDamageAdjustment(float damageAdjustment) {
    return Component.translatable(
            Constants.TOOLTIP_TEXT_PREFIX + "attack_damage",
            Component.literal(
                    String.format(
                        "+%.2f%%",
                        damageAdjustment > 1 ? (damageAdjustment - 1) * 100 : damageAdjustment))
                .withStyle(ChatFormatting.GREEN))
        .withStyle(ChatFormatting.GRAY);
  }

  private static MutableComponent formatDurabilityAdjustment(float durabilityAdjustment) {
    return Component.translatable(
            Constants.TOOLTIP_TEXT_PREFIX + "durability",
            Component.literal(String.format("+%.2f%%", durabilityAdjustment * 100))
                .withStyle(ChatFormatting.GREEN))
        .withStyle(ChatFormatting.GRAY);
  }
}
