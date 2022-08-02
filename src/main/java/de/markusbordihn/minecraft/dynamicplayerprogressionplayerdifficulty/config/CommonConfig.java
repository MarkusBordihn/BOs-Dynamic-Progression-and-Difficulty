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
          new ArrayList<String>(Arrays.asList("advancednetherite:netherite_diamond_axe",
              "advancednetherite:netherite_emerald_axe", "advancednetherite:netherite_gold_axe",
              "advancednetherite:netherite_iron_axe", "aquaculture:neptunium_axe",
              "blue_skies:aquite_axe", "blue_skies:bluebright_axe", "blue_skies:charoite_axe",
              "blue_skies:cherry_axe", "blue_skies:diopside_axe", "blue_skies:dusk_axe",
              "blue_skies:frostbright_axe", "blue_skies:horizonite_axe", "blue_skies:lunar_axe",
              "blue_skies:lunar_stone_axe", "blue_skies:maple_axe", "blue_skies:pyrope_axe",
              "blue_skies:starlit_axe", "blue_skies:turquoise_stone_axe", "botania:elementium_axe",
              "botania:manasteel_axe", "botania:terra_axe", "buddycards:buddysteel_axe",
              "byg:pendorite_axe", "fire_extinguisher:fire_axe", "gobber2:gobber2_axe",
              "gobber2:gobber2_tree_axe", "material_elements_armor_tools_weapons:copper_axe",
              "material_elements_armor_tools_weapons:steel_axe", "mekanismtools:bronze_axe",
              "mekanismtools:lapis_lazuli_axe", "mekanismtools:osmium_axe",
              "mekanismtools:refined_glowstone_axe", "mekanismtools:refined_obsidian_axe",
              "mekanismtools:steel_axe", "minecraft:diamond_axe", "minecraft:golden_axe",
              "minecraft:iron_axe", "minecraft:netherite_axe", "minecraft:stone_axe",
              "minecraft:wooden_axe", "miningmaster:haste_peridot_axe",
              "miningmaster:kinetic_opal_axe", "miningmaster:power_pyrite_axe",
              "miningmaster:spirit_garnet_axe", "miningmaster:ultima_axe",
              "mythicbotany:alfsteel_axe", "nethers_exoticism:kiwano_axe", "seadwellers:aqua_axe",
              "tconstruct:broad_axe", "tconstruct:hand_axe", "tropicraft:eudialyte_axe",
              "tropicraft:zircon_axe", "tropicraft:zirconium_axe",
              "twilightforest:diamond_minotaur_axe", "twilightforest:gold_minotaur_axe",
              "twilightforest:ironwood_axe", "twilightforest:knightmetal_axe",
              "twilightforest:steeleaf_axe", "undergarden:cloggrum_axe",
              "undergarden:forgotten_axe", "undergarden:froststeel_axe",
              "undergarden:utherium_axe")));
      axeItemDamageIncrease = builder
          .comment("Increases the dealt damage with the axe by the amount of % (0 = disabled).")
          .defineInRange("axeItemDamageIncrease", 75, 0, 1000);
      builder.pop();

      builder.push("Bow Items");
      bowItems = builder.comment("List of bow items.").define("bowItems",
          new ArrayList<String>(Arrays.asList("aquaculture:neptunium_bow", "botania:crystal_bow",
              "botania:livingwood_bow", "gobber2:gobber2_bow", "mekanism:electric_bow",
              "minecraft:bow", "miningmaster:air_malachite_bow", "twilightforest:ender_bow",
              "twilightforest:ice_bow", "twilightforest:seeker_bow", "twilightforest:triple_bow")));
      bowItemDamageIncrease = builder
          .comment("Increases the dealt damage with the bow by the amount of % (0 = disabled).")
          .defineInRange("bowItemDamageIncrease", 50, 0, 1000);
      builder.pop();

      builder.push("Crossbow Items");
      crossbowItems =
          builder.comment("List of bow items.").define("crossbowItems",
              new ArrayList<String>(Arrays.asList(
                  "material_elements_armor_tools_weapons:copper_crossbow",
                  "material_elements_armor_tools_weapons:steel_crossbow", "minecraft:crossbow")));
      crossbowItemDamageIncrease = builder
          .comment(
              "Increases the dealt damage with the crossbow by the amount of % (0 = disabled).")
          .defineInRange("crossbowItemDamageIncrease", 50, 0, 1000);
      builder.pop();

      builder.push("Pickaxe Items");
      pickaxeItems = builder.comment("List of pickaxe items.").define("pickaxeItems",
          new ArrayList<String>(Arrays.asList("advancednetherite:netherite_diamond_pickaxe",
              "advancednetherite:netherite_emerald_pickaxe",
              "advancednetherite:netherite_gold_pickaxe",
              "advancednetherite:netherite_iron_pickaxe", "aquaculture:neptunium_pickaxe",
              "blue_skies:aquite_pickaxe", "blue_skies:bluebright_pickaxe",
              "blue_skies:charoite_pickaxe", "blue_skies:cherry_pickaxe",
              "blue_skies:diopside_pickaxe", "blue_skies:dusk_pickaxe",
              "blue_skies:frostbright_pickaxe", "blue_skies:horizonite_pickaxe",
              "blue_skies:lunar_pickaxe", "blue_skies:lunar_stone_pickaxe",
              "blue_skies:maple_pickaxe", "blue_skies:pyrope_pickaxe", "blue_skies:starlit_pickaxe",
              "blue_skies:turquoise_stone_pickaxe", "botania:elementium_pickaxe",
              "botania:glass_pickaxe", "buddycards:buddysteel_pickaxe",
              "buddycards:luminis_pickaxe", "byg:pendorite_pickaxe", "gobber2:gobber2_pickaxe",
              "material_elements_armor_tools_weapons:copper_pickaxe",
              "material_elements_armor_tools_weapons:steel_pickaxe", "mekanismtools:bronze_pickaxe",
              "mekanismtools:lapis_lazuli_pickaxe", "mekanismtools:osmium_pickaxe",
              "mekanismtools:refined_glowstone_pickaxe", "mekanismtools:refined_obsidian_pickaxe",
              "mekanismtools:steel_pickaxe", "minecraft:diamond_pickaxe",
              "minecraft:golden_pickaxe", "minecraft:iron_pickaxe", "minecraft:netherite_pickaxe",
              "minecraft:stone_pickaxe", "minecraft:wooden_pickaxe",
              "miningmaster:haste_peridot_pickaxe", "miningmaster:kinetic_opal_pickaxe",
              "miningmaster:lucky_citrine_pickaxe", "miningmaster:power_pyrite_pickaxe",
              "miningmaster:ultima_pickaxe", "nethers_exoticism:kiwano_pickaxe",
              "seadwellers:aqua_pickaxe", "tropicraft:eudialyte_pickaxe",
              "tropicraft:zircon_pickaxe", "tropicraft:zirconium_pickaxe",
              "twilightforest:fiery_pickaxe", "twilightforest:giant_pickaxe",
              "twilightforest:ironwood_pickaxe", "twilightforest:knightmetal_pickaxe",
              "twilightforest:mazebreaker_pickaxe", "twilightforest:steeleaf_pickaxe",
              "undergarden:cloggrum_pickaxe", "undergarden:forgotten_pickaxe",
              "undergarden:froststeel_pickaxe", "undergarden:utherium_pickaxe")));
      pickaxeItemDamageIncrease = builder
          .comment("Increases the dealt damage with the pickaxe by the amount of % (0 = disabled).")
          .defineInRange("pickaxeItemDamageIncrease", 75, 0, 1000);
      builder.pop();

      builder.push("Shield Items");
      shieldItems =
          builder.comment("List of shield items.").define("shieldItems",
              new ArrayList<String>(Arrays.asList("blue_skies:moonstone_shield",
                  "blue_skies:spike_shield", "botania:cosmetic_lusitanic_shield",
                  "mekanismtools:bronze_shield", "mekanismtools:lapis_lazuli_shield",
                  "mekanismtools:osmium_shield", "mekanismtools:refined_glowstone_shield",
                  "mekanismtools:refined_obsidian_shield", "mekanismtools:steel_shield",
                  "minecraft:shield", "nethers_exoticism:rambutan_shield",
                  "twilightforest:knightmetal_shield", "twilightforest:stronghold_shield",
                  "undergarden:cloggrum_shield")));
      shieldItemDamageIncrease = builder
          .comment("Increases the dealt damage with the shield by the amount of % (0 = disabled).")
          .defineInRange("shieldItemDamageIncrease", 50, 0, 1000);
      builder.pop();

      builder.push("Sword Items");
      swordItems = builder.comment("List of sword items.").define("swordItems",
          new ArrayList<String>(Arrays.asList("advancednetherite:netherite_diamond_sword",
              "advancednetherite:netherite_emerald_sword", "advancednetherite:netherite_gold_sword",
              "advancednetherite:netherite_iron_sword", "aquaculture:neptunium_sword",
              "blue_skies:aquite_sword", "blue_skies:bluebright_sword", "blue_skies:charoite_sword",
              "blue_skies:cherry_sword", "blue_skies:debug_sword", "blue_skies:different_sword",
              "blue_skies:diopside_sword", "blue_skies:dusk_sword", "blue_skies:frostbright_sword",
              "blue_skies:horizonite_sword", "blue_skies:infused_arc_sword",
              "blue_skies:lunar_stone_sword", "blue_skies:lunar_sword", "blue_skies:maple_sword",
              "blue_skies:pyrope_sword", "blue_skies:starlit_sword",
              "blue_skies:turquoise_stone_sword", "botania:elementium_sword",
              "botania:manasteel_sword", "botania:star_sword", "botania:terra_sword",
              "botania:thunder_sword", "buddycards:buddysteel_sword", "byg:pendorite_sword",
              "gobber2:gobber2_sword", "material_elements_armor_tools_weapons:copper_sword",
              "material_elements_armor_tools_weapons:steel_sword", "mekanismtools:bronze_sword",
              "mekanismtools:lapis_lazuli_sword", "mekanismtools:osmium_sword",
              "mekanismtools:refined_glowstone_sword", "mekanismtools:refined_obsidian_sword",
              "mekanismtools:steel_sword", "minecraft:diamond_sword", "minecraft:golden_sword",
              "minecraft:iron_sword", "minecraft:netherite_sword", "minecraft:stone_sword",
              "minecraft:wooden_sword", "miningmaster:fire_ruby_sword",
              "miningmaster:ice_sapphire_sword", "miningmaster:kinetic_opal_sword",
              "miningmaster:lucky_citrine_sword", "miningmaster:power_pyrite_sword",
              "miningmaster:spirit_garnet_sword", "miningmaster:ultima_sword",
              "mythicbotany:alfsteel_sword", "nethers_exoticism:kiwano_sword",
              "seadwellers:aqua_sword", "tropicraft:eudialyte_sword", "tropicraft:zircon_sword",
              "tropicraft:zirconium_sword", "twilightforest:fiery_sword",
              "twilightforest:giant_sword", "twilightforest:glass_sword",
              "twilightforest:ice_sword", "twilightforest:ironwood_sword",
              "twilightforest:knightmetal_sword", "twilightforest:steeleaf_sword",
              "undergarden:cloggrum_sword", "undergarden:forgotten_sword",
              "undergarden:froststeel_sword", "undergarden:utherium_sword")));
      swordItemDamageIncrease = builder
          .comment("Increases the dealt damage with the sword by the amount of % (0 = disabled).")
          .defineInRange("swordItemDamageIncrease", 50, 0, 1000);
      builder.pop();
    }
  }

}
