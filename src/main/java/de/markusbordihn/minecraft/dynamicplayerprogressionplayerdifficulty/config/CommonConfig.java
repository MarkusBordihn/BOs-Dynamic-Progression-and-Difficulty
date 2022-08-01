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

package de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

import de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.Constants;

public class CommonConfig {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  public static final ForgeConfigSpec commonSpec;
  public static final Config COMMON;

  protected CommonConfig() {}

  static {
    com.electronwill.nightconfig.core.Config.setInsertionOrderPreserved(true);
    final Pair<Config, ForgeConfigSpec> specPair =
        new ForgeConfigSpec.Builder().configure(Config::new);
    commonSpec = specPair.getRight();
    COMMON = specPair.getLeft();
    log.info("{} Common config ...", Constants.LOG_REGISTER_PREFIX);
    ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, commonSpec);
  }

  public static class Config {

    public final ForgeConfigSpec.IntValue levelMax;
    public final ForgeConfigSpec.IntValue levelFactor;
    public final ForgeConfigSpec.IntValue levelFactorItems;
    public final ForgeConfigSpec.IntValue levelExperienceDeathPenalty;
    public final ForgeConfigSpec.IntValue levelExperienceDeathPenaltyItems;

    public final ForgeConfigSpec.IntValue dealtDamageIncrease;
    public final ForgeConfigSpec.IntValue dealtDamageReduction;
    public final ForgeConfigSpec.IntValue dealtDamageLevelCap;

    public final ForgeConfigSpec.IntValue hurtDamageIncrease;
    public final ForgeConfigSpec.IntValue hurtDamageReduction;
    public final ForgeConfigSpec.IntValue hurtDamageLevelCap;

    public final ForgeConfigSpec.ConfigValue<List<String>> axeItems;
    public final ForgeConfigSpec.IntValue axeItemDamageIncrease;

    public final ForgeConfigSpec.ConfigValue<List<String>> bowItems;
    public final ForgeConfigSpec.IntValue bowItemDamageIncrease;

    public final ForgeConfigSpec.ConfigValue<List<String>> crossbowItems;
    public final ForgeConfigSpec.IntValue crossbowItemDamageIncrease;

    public final ForgeConfigSpec.ConfigValue<List<String>> pickaxeItems;
    public final ForgeConfigSpec.IntValue pickaxeItemDamageIncrease;

    public final ForgeConfigSpec.ConfigValue<List<String>> shieldItems;
    public final ForgeConfigSpec.IntValue shieldItemDamageIncrease;

    public final ForgeConfigSpec.ConfigValue<List<String>> swordItems;
    public final ForgeConfigSpec.IntValue swordItemDamageIncrease;

    Config(ForgeConfigSpec.Builder builder) {
      builder.comment(
          "Dynamic Player Progression and Difficulty\nNote: Changes requires are server restart!");

      builder.push("General");
      builder.pop();

      builder.push("Level System");
      levelMax =
          builder.comment("Max level for the level system.").defineInRange("levelMax", 100, 0, 999);
      levelFactor = builder
          .comment("Level factor to distribute experience between min level and max level.\n"
              + "Recommended: 300 for lvl 1-100, 150 for lvl 1-200, 100 for lvl 1-300, ...")
          .defineInRange("levelFactor", 300, 1, 999);
      levelFactorItems = builder
          .comment("Level factor to distribute item experience between min level and max level.\n"
              + "Recommended: 125 for lvl 1-100, 250 for lvl 1-200, 275 for lvl 1-300, ...")
          .defineInRange("levelFactorItems", 125, 1, 999);
      levelExperienceDeathPenalty =
          builder.comment("Experience penalty for each death (0 = disabled)")
              .defineInRange("levelExperienceDeathPenalty", 65, 0, 1000);
      levelExperienceDeathPenaltyItems =
          builder.comment("Experience penalty for each death for items (0 = disabled)")
              .defineInRange("levelExperienceDeathPenaltyItems", 32, 0, 1000);
      builder.pop();

      builder.push("Dealt Damage (from the player)");
      dealtDamageIncrease =
          builder.comment("Increases the dealt damage by the amount of % (0 = disabled).")
              .defineInRange("dealtDamageIncrease", 50, 0, 1000);
      dealtDamageReduction =
          builder.comment("Reduces the dealt damage by the amount of % (0 = disabled).")
              .defineInRange("dealtDamageReduction", 50, 0, 100);
      dealtDamageLevelCap = builder
          .comment("Level cap when dealt damage should be reduced or increased.\n"
              + "e.g. 10 = means as long the player is <= lvl 10, their dealt damage will be increased"
              + "as soon the player is > lvl 10, their dealt damage will be decreased")
          .defineInRange("dealtDamageLevelCap", 10, 1, 999);
      builder.pop();

      builder.push("Hurt Damage (to the player)");
      hurtDamageIncrease =
          builder.comment("Increases the hurt damage by the amount of % (0 = disabled).")
              .defineInRange("hurtDamageIncrease", 100, 0, 1000);
      hurtDamageReduction =
          builder.comment("Reduces the hurt damage by the amount of % (0 = disabled).")
              .defineInRange("hurtDamageReduction", 50, 0, 100);
      hurtDamageLevelCap = builder
          .comment("Level cap when hurt damage should be reduced or increased.\n"
              + "e.g. 10 = means as long the player is <= lvl 10, their hurt damage will be decreased"
              + "as soon the player is > lvl 10, their hurt damage will be increased")
          .defineInRange("hurtDamageLevelCap", 10, 1, 999);
      builder.pop();

      builder.push("Axe Items");
      axeItems = builder.comment("List of axe items.").define("axeItems",
          new ArrayList<String>(Arrays.asList("minecraft:wooden_axe", "minecraft:stone_axe",
              "minecraft:golden_axe", "minecraft:iron_axe", "minecraft:diamond_axe",
              "minecraft:netherite_axe", "material_elements_armor_tools_weapons:copper_axe",
              "material_elements_armor_tools_weapons:steel_axe")));
      axeItemDamageIncrease = builder
          .comment("Increases the dealt damage with the axe by the amount of % (0 = disabled).")
          .defineInRange("axeItemDamageIncrease", 75, 0, 1000);
      builder.pop();

      builder.push("Bow Items");
      bowItems = builder.comment("List of bow items.").define("bowItems",
          new ArrayList<String>(Arrays.asList("minecraft:bow")));
      bowItemDamageIncrease = builder
          .comment("Increases the dealt damage with the bow by the amount of % (0 = disabled).")
          .defineInRange("bowItemDamageIncrease", 50, 0, 1000);
      builder.pop();

      builder.push("Crossbow Items");
      crossbowItems = builder.comment("List of bow items.").define("crossbowItems",
          new ArrayList<String>(Arrays.asList("minecraft:crossbow",
              "material_elements_armor_tools_weapons:copper_crossbow",
              "material_elements_armor_tools_weapons:steel_crossbow")));
      crossbowItemDamageIncrease = builder
          .comment(
              "Increases the dealt damage with the crossbow by the amount of % (0 = disabled).")
          .defineInRange("crossbowItemDamageIncrease", 50, 0, 1000);
      builder.pop();

      builder.push("Pickaxe Items");
      pickaxeItems = builder.comment("List of pickaxe items.").define("pickaxeItems",
          new ArrayList<String>(Arrays.asList("minecraft:wooden_pickaxe", "minecraft:stone_pickaxe",
              "minecraft:golden_pickaxe", "minecraft:iron_pickaxe", "minecraft:diamond_pickaxe",
              "minecraft:netherite_pickaxe", "material_elements_armor_tools_weapons:copper_pickaxe",
              "material_elements_armor_tools_weapons:steel_pickaxe")));
      pickaxeItemDamageIncrease = builder
          .comment("Increases the dealt damage with the pickaxe by the amount of % (0 = disabled).")
          .defineInRange("pickaxeItemDamageIncrease", 75, 0, 1000);
      builder.pop();

      builder.push("Shield Items");
      shieldItems = builder.comment("List of shield items.").define("shieldItems",
          new ArrayList<String>(Arrays.asList("minecraft:shield")));
      shieldItemDamageIncrease = builder
          .comment("Increases the dealt damage with the shield by the amount of % (0 = disabled).")
          .defineInRange("shieldItemDamageIncrease", 50, 0, 1000);
      builder.pop();

      builder.push("Sword Items");
      swordItems = builder.comment("List of sword items.").define("swordItems",
          new ArrayList<String>(Arrays.asList("minecraft:wooden_sword", "minecraft:stone_sword",
              "minecraft:golden_sword", "minecraft:iron_sword", "minecraft:diamond_sword",
              "minecraft:netherite_sword", "material_elements_armor_tools_weapons:copper_sword",
              "material_elements_armor_tools_weapons:steel_sword")));
      swordItemDamageIncrease = builder
          .comment("Increases the dealt damage with the sword by the amount of % (0 = disabled).")
          .defineInRange("swordItemDamageIncrease", 50, 0, 1000);
      builder.pop();
    }
  }

}
