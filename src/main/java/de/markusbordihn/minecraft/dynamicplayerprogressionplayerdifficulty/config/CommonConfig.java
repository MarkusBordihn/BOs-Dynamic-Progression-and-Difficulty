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

    public final ForgeConfigSpec.BooleanValue guiButtonEnabled;
    public final ForgeConfigSpec.IntValue guiButtonPositionLeft;
    public final ForgeConfigSpec.IntValue guiButtonPositionTop;

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
    public final ForgeConfigSpec.IntValue axeItemDurabilityIncrease;

    public final ForgeConfigSpec.ConfigValue<List<String>> bowItems;
    public final ForgeConfigSpec.IntValue bowItemDamageIncrease;
    public final ForgeConfigSpec.IntValue bowItemDurabilityIncrease;

    public final ForgeConfigSpec.ConfigValue<List<String>> crossbowItems;
    public final ForgeConfigSpec.IntValue crossbowItemDamageIncrease;
    public final ForgeConfigSpec.IntValue crossbowItemDurabilityIncrease;

    public final ForgeConfigSpec.ConfigValue<List<String>> daggerItems;
    public final ForgeConfigSpec.IntValue daggerItemDamageIncrease;
    public final ForgeConfigSpec.IntValue daggerItemDurabilityIncrease;

    public final ForgeConfigSpec.ConfigValue<List<String>> greatSwordItems;
    public final ForgeConfigSpec.IntValue greatSwordItemDamageIncrease;
    public final ForgeConfigSpec.IntValue greatSwordItemDurabilityIncrease;

    public final ForgeConfigSpec.ConfigValue<List<String>> gunItems;
    public final ForgeConfigSpec.IntValue gunItemDamageIncrease;
    public final ForgeConfigSpec.IntValue gunItemDurabilityIncrease;

    public final ForgeConfigSpec.ConfigValue<List<String>> hammerItems;
    public final ForgeConfigSpec.IntValue hammerItemDamageIncrease;
    public final ForgeConfigSpec.IntValue hammerItemDurabilityIncrease;

    public final ForgeConfigSpec.ConfigValue<List<String>> hoeItems;
    public final ForgeConfigSpec.IntValue hoeItemDamageIncrease;
    public final ForgeConfigSpec.IntValue hoeItemDurabilityIncrease;

    public final ForgeConfigSpec.ConfigValue<List<String>> keybladeItems;
    public final ForgeConfigSpec.IntValue keybladeItemDamageIncrease;
    public final ForgeConfigSpec.IntValue keybladeItemDurabilityIncrease;

    public final ForgeConfigSpec.ConfigValue<List<String>> pickaxeItems;
    public final ForgeConfigSpec.IntValue pickaxeItemDamageIncrease;
    public final ForgeConfigSpec.IntValue pickaxeItemDurabilityIncrease;

    public final ForgeConfigSpec.ConfigValue<List<String>> shieldItems;
    public final ForgeConfigSpec.IntValue shieldItemDamageIncrease;
    public final ForgeConfigSpec.IntValue shieldItemDurabilityIncrease;

    public final ForgeConfigSpec.ConfigValue<List<String>> spearItems;
    public final ForgeConfigSpec.IntValue spearItemDamageIncrease;
    public final ForgeConfigSpec.IntValue spearItemDurabilityIncrease;

    public final ForgeConfigSpec.ConfigValue<List<String>> swordItems;
    public final ForgeConfigSpec.IntValue swordItemDamageIncrease;
    public final ForgeConfigSpec.IntValue swordItemDurabilityIncrease;

    Config(ForgeConfigSpec.Builder builder) {
      builder.comment(
          "Dynamic Player Progression and Difficulty\nNote: Some changes requires are server restart!");

      builder.push("Gui");
      guiButtonEnabled = builder.comment("Enable/Disable stats button in the inventory screen.")
          .define("guiButtonEnabled", true);
      guiButtonPositionLeft =
          builder.comment("Defines the left position relative to the inventory screen.")
              .defineInRange("guiButtonPositionLeft", 64, 0, 640);
      guiButtonPositionTop =
          builder.comment("Defines the top position relative to the inventory screen.")
              .defineInRange("guiButtonPositionTop", 9, 0, 480);
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

      builder.push("Axes");
      axeItems = builder.comment("List of axe items.").define("axeItems",
          new ArrayList<String>(Arrays.asList(
          //@formatter:off
            "advancednetherite:netherite_diamond_axe",
            "advancednetherite:netherite_emerald_axe",
            "advancednetherite:netherite_gold_axe",
            "advancednetherite:netherite_iron_axe",
            "ageofweapons:battle_axe_diamond",
            "ageofweapons:battle_axe_gold",
            "ageofweapons:battle_axe_iron",
            "ageofweapons:battle_axe_stone",
            "ageofweapons:battle_axe_wood",
            "ageofweapons:waraxe_diamond",
            "ageofweapons:waraxe_gold",
            "ageofweapons:waraxe_iron",
            "ageofweapons:waraxe_stone",
            "ageofweapons:waraxe_wood",
            "aquaculture:neptunium_axe",
            "blue_skies:aquite_axe",
            "blue_skies:bluebright_axe",
            "blue_skies:charoite_axe",
            "blue_skies:cherry_axe",
            "blue_skies:diopside_axe",
            "blue_skies:dusk_axe",
            "blue_skies:frostbright_axe",
            "blue_skies:horizonite_axe",
            "blue_skies:lunar_axe",
            "blue_skies:lunar_stone_axe",
            "blue_skies:maple_axe",
            "blue_skies:pyrope_axe",
            "blue_skies:starlit_axe",
            "blue_skies:turquoise_stone_axe",
            "botania:elementium_axe",
            "botania:manasteel_axe",
            "botania:terra_axe",
            "buddycards:buddysteel_axe",
            "byg:pendorite_axe",
            "fire_extinguisher:fire_axe",
            "gobber2:gobber2_axe",
            "gobber2:gobber2_tree_axe",
            "gobber2:gobber2_tree_axe_end",
            "gobber2:gobber2_tree_axe_nether",
            "guns_galore:rubber_1_axe",
            "guns_galore:steel_axe",
            "immersiveengineering:axe_steel",
            "material_elements_armor_tools_weapons:copper_axe",
            "material_elements_armor_tools_weapons:steel_axe",
            "mekanismtools:bronze_axe",
            "mekanismtools:lapis_lazuli_axe",
            "mekanismtools:osmium_axe",
            "mekanismtools:refined_glowstone_axe",
            "mekanismtools:refined_obsidian_axe",
            "mekanismtools:steel_axe",
            "minecraft:diamond_axe",
            "minecraft:golden_axe",
            "minecraft:iron_axe",
            "minecraft:netherite_axe",
            "minecraft:stone_axe",
            "minecraft:wooden_axe",
            "minecraftdungeons:double_axe",
            "minecraftdungeons:highland_axe",
            "miningmaster:haste_peridot_axe",
            "miningmaster:kinetic_opal_axe",
            "miningmaster:power_pyrite_axe",
            "miningmaster:spirit_garnet_axe",
            "miningmaster:ultima_axe",
            "mythicbotany:alfsteel_axe",
            "nethers_exoticism:kiwano_axe",
            "rpg_style_more_weapins:ironbattleaxe",
            "rpg_style_more_weapins:netheritbattleaxe",
            "rpg_style_more_weapins:steelbattleaxe",
            "seadwellers:aqua_axe",
            "tconstruct:broad_axe",
            "tconstruct:hand_axe",
            "tropicraft:eudialyte_axe",
            "tropicraft:zircon_axe",
            "tropicraft:zirconium_axe",
            "twilightforest:diamond_minotaur_axe",
            "twilightforest:gold_minotaur_axe",
            "twilightforest:ironwood_axe",
            "twilightforest:knightmetal_axe",
            "twilightforest:steeleaf_axe",
            "undergarden:cloggrum_axe",
            "undergarden:forgotten_axe",
            "undergarden:froststeel_axe",
            "undergarden:utherium_axe"
          //@formatter:on
          )));
      axeItemDamageIncrease = builder
          .comment("Increases the dealt damage with the axe by the amount of % (0 = disabled).")
          .defineInRange("axeItemDamageIncrease", 75, 0, 1000);
      axeItemDurabilityIncrease =
          builder.comment("Increases the durability of the axe by the amount of % (0 = disabled).")
              .defineInRange("axeItemDurabilityIncrease", 150, 0, 1000);
      builder.pop();

      builder.push("Bows");
      bowItems = builder.comment("List of bow items.").define("bowItems",
          new ArrayList<String>(Arrays.asList(
          //@formatter:off
            "ageofweapons:compound_bow",
            "ageofweapons:iron_bow",
            "ageofweapons:recurve_bow",
            "aquaculture:neptunium_bow",
            "botania:crystal_bow",
            "botania:livingwood_bow",
            "gobber2:gobber2_bow",
            "magistuarmory:longbow",
            "mekanism:electric_bow",
            "minecraft:bow",
            "miningmaster:air_malachite_bow",
            "twilightforest:ender_bow",
            "twilightforest:ice_bow",
            "twilightforest:seeker_bow",
            "twilightforest:triple_bow"
          //@formatter:on
          )));
      bowItemDamageIncrease = builder
          .comment("Increases the dealt damage with the bow by the amount of % (0 = disabled).")
          .defineInRange("bowItemDamageIncrease", 50, 0, 1000);
      bowItemDurabilityIncrease =
          builder.comment("Increases the durability of the bow by the amount of % (0 = disabled).")
              .defineInRange("bowItemDurabilityIncrease", 0, 0, 1000);
      builder.pop();

      builder.push("Crossbows");
      crossbowItems = builder.comment("List of cross bow items.").define("crossbowItems",
          new ArrayList<String>(Arrays.asList(
          //@formatter:off
            "magistuarmory:heavy_crossbow",
            "material_elements_armor_tools_weapons:copper_crossbow",
            "material_elements_armor_tools_weapons:steel_crossbow",
            "minecraft:crossbow"
          //@formatter:on
          )));
      crossbowItemDamageIncrease = builder
          .comment(
              "Increases the dealt damage with the crossbow by the amount of % (0 = disabled).")
          .defineInRange("crossbowItemDamageIncrease", 50, 0, 1000);
      crossbowItemDurabilityIncrease =
          builder.comment("Increases the durability of the bow by the amount of % (0 = disabled).")
              .defineInRange("crossbowItemDurabilityIncrease", 0, 0, 1000);
      builder.pop();

      builder.push("Dagger");
      daggerItems = builder.comment("List of dagger items.").define("daggerItems",
          new ArrayList<String>(Arrays.asList(
          //@formatter:off
            "ageofweapons:bone_knife",
            "ageofweapons:caveman_knife",
            "ageofweapons:dagger_diamond",
            "ageofweapons:dagger_gold",
            "ageofweapons:dagger_iron",
            "ageofweapons:dagger_stone",
            "ageofweapons:dagger_wood",
            "ageofweapons:knife_diamond",
            "ageofweapons:knife_gold",
            "ageofweapons:knife_iron",
            "ageofweapons:knife_stone",
            "ageofweapons:knife_wood",
            "aquaculture:diamond_fillet_knife",
            "aquaculture:gold_fillet_knife",
            "aquaculture:iron_fillet_knife",
            "aquaculture:neptunium_fillet_knife",
            "aquaculture:stone_fillet_knife",
            "aquaculture:wooden_fillet_knife",
            "botania:ender_dagger",
            "croptopia:knife",
            "farmersdelight:diamond_knife",
            "farmersdelight:flint_knife",
            "farmersdelight:golden_knife",
            "farmersdelight:iron_knife",
            "farmersdelight:netherite_knife",
            "material_elements:leather_knife",
            "minecraftdungeons:chill_gale_knife",
            "rpg_style_more_weapins:copper_knife",
            "rpg_style_more_weapins:diamondknife",
            "rpg_style_more_weapins:netheritknife",
            "tconstruct:dagger",
            "tropicraft:dagger",
            "useless_sword:pillager_dagger"
          //@formatter:on
          )));
      daggerItemDamageIncrease = builder
          .comment("Increases the dealt damage with the dagger by the amount of % (0 = disabled).")
          .defineInRange("daggerItemDamageIncrease", 50, 0, 1000);
      daggerItemDurabilityIncrease = builder
          .comment("Increases the durability of the dagger by the amount of % (0 = disabled).")
          .defineInRange("daggerItemDurabilityIncrease", 25, 0, 1000);
      builder.pop();

      builder.push("Great Sword");
      greatSwordItems = builder.comment("List of greatSword items.").define("greatSwordItems",
          new ArrayList<String>(Arrays.asList(
          //@formatter:off
            "magistuarmory:bronze_bastardsword",
            "magistuarmory:bronze_claymore",
            "magistuarmory:bronze_flamebladedsword",
            "magistuarmory:bronze_zweihander",
            "magistuarmory:copper_bastardsword",
            "magistuarmory:copper_claymore",
            "magistuarmory:copper_flamebladedsword",
            "magistuarmory:copper_zweihander",
            "magistuarmory:diamond_bastardsword",
            "magistuarmory:diamond_claymore",
            "magistuarmory:diamond_flamebladedsword",
            "magistuarmory:diamond_zweihander",
            "magistuarmory:gold_bastardsword",
            "magistuarmory:gold_claymore",
            "magistuarmory:gold_flamebladedsword",
            "magistuarmory:gold_zweihander",
            "magistuarmory:iron_bastardsword",
            "magistuarmory:iron_claymore",
            "magistuarmory:iron_flamebladedsword",
            "magistuarmory:iron_zweihander",
            "magistuarmory:netherite_bastardsword",
            "magistuarmory:netherite_claymore",
            "magistuarmory:netherite_flamebladedsword",
            "magistuarmory:netherite_zweihander",
            "magistuarmory:silver_bastardsword",
            "magistuarmory:silver_claymore",
            "magistuarmory:silver_flamebladedsword",
            "magistuarmory:silver_zweihander",
            "magistuarmory:steel_bastardsword",
            "magistuarmory:steel_claymore",
            "magistuarmory:steel_flamebladedsword",
            "magistuarmory:steel_zweihander",
            "magistuarmory:stone_bastardsword",
            "magistuarmory:stone_claymore",
            "magistuarmory:stone_flamebladedsword",
            "magistuarmory:stone_zweihander",
            "magistuarmory:tin_bastardsword",
            "magistuarmory:tin_claymore",
            "magistuarmory:tin_flamebladedsword",
            "magistuarmory:tin_zweihander",
            "magistuarmory:wood_bastardsword",
            "magistuarmory:wood_claymore",
            "magistuarmory:wood_flamebladedsword",
            "magistuarmory:wood_zweihander",
            "useless_sword:ancient_guardian_sword",
            "useless_sword:basalt_broadsword",
            "useless_sword:bugged_diamond_sword",
            "useless_sword:elder_guardian_sword",
            "useless_sword:hemolymph_broadsword",
            "useless_sword:nether_striker",
            "useless_sword:nether_striker",
            "useless_sword:soul_glaive",
            "useless_sword:sword_of_fate",
            "useless_sword:sword_of_immortality",
            "useless_sword:wither_sword"
          //@formatter:on
          )));
      greatSwordItemDamageIncrease = builder
          .comment(
              "Increases the dealt damage with the great sword by the amount of % (0 = disabled).")
          .defineInRange("greatSwordItemDamageIncrease", 50, 0, 1000);
      greatSwordItemDurabilityIncrease = builder
          .comment("Increases the durability of the great sword by the amount of % (0 = disabled).")
          .defineInRange("greatSwordItemDurabilityIncrease", 0, 0, 1000);
      builder.pop();

      builder.push("Guns");
      gunItems = builder.comment("List of gun items.").define("gunItems",
          new ArrayList<String>(Arrays.asList(
          //@formatter:off
            "additionalguns:9a91",
            "additionalguns:ace_of_spades",
            "additionalguns:ak104",
            "additionalguns:ak105",
            "additionalguns:ak12",
            "additionalguns:ak15",
            "additionalguns:ak74",
            "additionalguns:ak74m",
            "additionalguns:akm",
            "additionalguns:akm_custom",
            "additionalguns:aks74u",
            "additionalguns:aug",
            "additionalguns:awm",
            "additionalguns:banshee",
            "additionalguns:desert_eagle",
            "additionalguns:fn2000",
            "additionalguns:g11",
            "additionalguns:glock18",
            "additionalguns:m1014",
            "additionalguns:m16a2",
            "additionalguns:m1911",
            "additionalguns:m4a1s",
            "additionalguns:m4a4",
            "additionalguns:mac10",
            "additionalguns:magnum",
            "additionalguns:mammoth",
            "additionalguns:mat_49",
            "additionalguns:mp7a2",
            "additionalguns:ots_03",
            "additionalguns:p250",
            "additionalguns:pp_19",
            "additionalguns:ravens_claw",
            "additionalguns:schwarzlose",
            "additionalguns:ssg08",
            "additionalguns:usp",
            "additionalguns:val",
            "additionalguns:vector",
            "additionalguns:vintorez",
            "botania:mana_gun",
            "cgm:assault_rifle",
            "cgm:bazooka",
            "cgm:grenade_launcher",
            "cgm:heavy_rifle",
            "cgm:machine_pistol",
            "cgm:mini_gun",
            "cgm:pistol",
            "cgm:rifle",
            "cgm:shotgun",
            "gunswithoutroses:diamond_gatling",
            "gunswithoutroses:diamond_shotgun",
            "gunswithoutroses:diamond_sniper",
            "gunswithoutroses:gold_gun",
            "gunswithoutroses:iron_gun",
            "immersiveengineering:railgun",
            "moguns:m14",
            "moguns:mp5",
            "oldguns:flintlock_musket",
            "oldguns:flintlock_nock_gun",
            "oldguns:flintlock_pistol",
            "oldguns:wheellock_doublebarrel_pistol",
            "pneumaticcraft:minigun",
            "sonicraft:egg_gun",
            "sonicraft:eggrobo_gun",
            "tropicraft:blow_gun"
          //@formatter:on
          )));
      gunItemDamageIncrease = builder
          .comment("Increases the dealt damage with the gun by the amount of % (0 = disabled).")
          .defineInRange("gunItemDamageIncrease", 50, 0, 1000);
      gunItemDurabilityIncrease =
          builder.comment("Increases the durability of the gun by the amount of % (0 = disabled).")
              .defineInRange("gunItemDurabilityIncrease", 0, 0, 1000);
      builder.pop();

      builder.push("Hammers");
      hammerItems = builder.comment("List of hammer items.").define("hammerItems",
          new ArrayList<String>(Arrays.asList(
          //@formatter:off
            "ageofweapons:warhammer_diamond",
            "ageofweapons:warhammer_gold",
            "ageofweapons:warhammer_iron",
            "ageofweapons:warhammer_stone",
            "ageofweapons:warhammer_wood",
            "beyond_earth:hammer",
            "blue_skies:crushing_hammer",
            "gobber2:gobber2_hammer",
            "immersiveengineering:hammer",
            "magistuarmory:blacksmith_hammer",
            "magistuarmory:bronze_heavywarhammer",
            "magistuarmory:bronze_lucernhammer",
            "magistuarmory:copper_heavywarhammer",
            "magistuarmory:copper_lucernhammer",
            "magistuarmory:diamond_heavywarhammer",
            "magistuarmory:diamond_lucernhammer",
            "magistuarmory:gold_heavywarhammer",
            "magistuarmory:gold_lucernhammer",
            "magistuarmory:iron_heavywarhammer",
            "magistuarmory:iron_lucernhammer",
            "magistuarmory:netherite_heavywarhammer",
            "magistuarmory:netherite_lucernhammer",
            "magistuarmory:silver_heavywarhammer",
            "magistuarmory:silver_lucernhammer",
            "magistuarmory:steel_heavywarhammer",
            "magistuarmory:steel_lucernhammer",
            "magistuarmory:stone_heavywarhammer",
            "magistuarmory:stone_lucernhammer",
            "magistuarmory:tin_heavywarhammer",
            "magistuarmory:tin_lucernhammer",
            "magistuarmory:wood_heavywarhammer",
            "magistuarmory:wood_lucernhammer",
            "mcwroofs:roofing_hammer",
            "mcwwindows:hammer",
            "rpg_style_more_weapins:ironhammer",
            "rpg_style_more_weapins:newgenhammer",
            "sonicraft:piko_piko_hammer",
            "tconstruct:sledge_hammer",
            "tconstruct:vein_hammer"
          //@formatter:on
          )));
      hammerItemDamageIncrease = builder
          .comment("Increases the dealt damage with the hammer by the amount of % (0 = disabled).")
          .defineInRange("hammerItemDamageIncrease", 50, 0, 1000);
      hammerItemDurabilityIncrease = builder
          .comment("Increases the durability of the hammer by the amount of % (0 = disabled).")
          .defineInRange("hammerItemDurabilityIncrease", 25, 0, 1000);
      builder.pop();

      builder.push("Hoe");
      hoeItems = builder.comment("List of hoe items.").define("hoeItems",
          new ArrayList<String>(Arrays.asList(
          //@formatter:off
            "advancednetherite:netherite_diamond_hoe",
            "advancednetherite:netherite_emerald_hoe",
            "advancednetherite:netherite_gold_hoe",
            "advancednetherite:netherite_iron_hoe",
            "aquaculture:neptunium_hoe",
            "blue_skies:aquite_hoe",
            "blue_skies:bluebright_hoe",
            "blue_skies:charoite_hoe",
            "blue_skies:cherry_hoe",
            "blue_skies:diopside_hoe",
            "blue_skies:dusk_hoe",
            "blue_skies:frostbright_hoe",
            "blue_skies:horizonite_hoe",
            "blue_skies:lunar_hoe",
            "blue_skies:lunar_stone_hoe",
            "blue_skies:maple_hoe",
            "blue_skies:pyrope_hoe",
            "blue_skies:starlit_hoe",
            "blue_skies:turquoise_stone_hoe",
            "botania:elementium_hoe",
            "botania:manasteel_hoe",
            "buddycards:buddysteel_hoe",
            "buddycards:zylex_hoe",
            "byg:pendorite_hoe",
            "gobber2:gobber2_hoe",
            "guns_galore:rubber_1_hoe",
            "guns_galore:steel_hoe",
            "immersiveengineering:hoe_steel",
            "material_elements_armor_tools_weapons:copper_hoe",
            "material_elements_armor_tools_weapons:steel_hoe",
            "mekanismtools:bronze_hoe",
            "mekanismtools:lapis_lazuli_hoe",
            "mekanismtools:osmium_hoe",
            "mekanismtools:refined_glowstone_hoe",
            "mekanismtools:refined_obsidian_hoe",
            "mekanismtools:steel_hoe",
            "minecraft:diamond_hoe",
            "minecraft:golden_hoe",
            "minecraft:iron_hoe",
            "minecraft:netherite_hoe",
            "minecraft:stone_hoe",
            "minecraft:wooden_hoe",
            "miningmaster:haste_peridot_hoe",
            "miningmaster:ultima_hoe",
            "nethers_exoticism:kiwano_hoe",
            "seadwellers:aqua_hoe",
            "tropicraft:eudialyte_hoe",
            "tropicraft:zircon_hoe",
            "tropicraft:zirconium_hoe",
            "twilightforest:ironwood_hoe",
            "twilightforest:steeleaf_hoe",
            "undergarden:cloggrum_hoe",
            "undergarden:forgotten_hoe",
            "undergarden:froststeel_hoe",
            "undergarden:utherium_hoe"
          //@formatter:on
          )));
      hoeItemDamageIncrease = builder
          .comment("Increases the dealt damage with the hoe by the amount of % (0 = disabled).")
          .defineInRange("hoeItemDamageIncrease", 200, 0, 1000);
      hoeItemDurabilityIncrease =
          builder.comment("Increases the durability of the hoe by the amount of % (0 = disabled).")
              .defineInRange("hoeItemDurabilityIncrease", 25, 0, 1000);
      builder.pop();

      builder.push("Keyblades");
      keybladeItems = builder.comment("List of keyblade items.").define("keybladeItems",
          new ArrayList<String>(Arrays.asList(
          //@formatter:off
            "botania:king_key",
            "keyblademod:keyblade_diamond",
            "keyblademod:keyblade_gold",
            "keyblademod:keyblade_iron",
            "keyblademod:keyblade_netherite",
            "keyblademod:keyblade_stone",
            "keyblademod:keyblade_wood",
            "kingdomkeys:abaddon_plasma",
            "kingdomkeys:abyssal_tide",
            "kingdomkeys:aceds_keyblade",
            "kingdomkeys:aceds_keyblade",
            "kingdomkeys:advent_red",
            "kingdomkeys:all_for_one",
            "kingdomkeys:astral_blast",
            "kingdomkeys:aubade",
            "kingdomkeys:avas_keyblade",
            "kingdomkeys:avas_keyblade",
            "kingdomkeys:bond_of_flame",
            "kingdomkeys:bond_of_the_blaze",
            "kingdomkeys:braveheart",
            "kingdomkeys:brightcrest",
            "kingdomkeys:chaos_ripper",
            "kingdomkeys:circle_of_life",
            "kingdomkeys:classic_tone",
            "kingdomkeys:counterpoint",
            "kingdomkeys:crabclaw",
            "kingdomkeys:crown_of_guilt",
            "kingdomkeys:crystal_snow",
            "kingdomkeys:darker_than_dark",
            "kingdomkeys:darkgnaw",
            "kingdomkeys:dawn_till_dusk",
            "kingdomkeys:decisive_pumpkin",
            "kingdomkeys:destinys_embrace",
            "kingdomkeys:diamond_dust",
            "kingdomkeys:divewing",
            "kingdomkeys:divine_rose",
            "kingdomkeys:dual_disc",
            "kingdomkeys:earthshaker",
            "kingdomkeys:elemental_encoder",
            "kingdomkeys:end_of_pain",
            "kingdomkeys:ends_of_the_earth",
            "kingdomkeys:ever_after",
            "kingdomkeys:fairy_harp",
            "kingdomkeys:fairy_stars",
            "kingdomkeys:fatal_crest",
            "kingdomkeys:favorite_deputy",
            "kingdomkeys:fenrir",
            "kingdomkeys:ferris_gear",
            "kingdomkeys:follow_the_wind",
            "kingdomkeys:frolic_flame",
            "kingdomkeys:glimpse_of_darkness",
            "kingdomkeys:grand_chef",
            "kingdomkeys:guardian_bell",
            "kingdomkeys:guardian_soul",
            "kingdomkeys:gulas_keyblade",
            "kingdomkeys:gulas_keyblade",
            "kingdomkeys:gull_wing",
            "kingdomkeys:happy_gear",
            "kingdomkeys:heros_crest",
            "kingdomkeys:heros_origin",
            "kingdomkeys:hidden_dragon",
            "kingdomkeys:hunny_spout",
            "kingdomkeys:hyperdrive",
            "kingdomkeys:incomplete_kiblade",
            "kingdomkeys:invis_keyblade",
            "kingdomkeys:invis_keyblade",
            "kingdomkeys:iras_keyblade",
            "kingdomkeys:iras_keyblade",
            "kingdomkeys:jungle_king",
            "kingdomkeys:keyblade_of_peoples_hearts",
            "kingdomkeys:kiblade",
            "kingdomkeys:kingdom_key",
            "kingdomkeys:kingdom_key_d",
            "kingdomkeys:knockout_punch",
            "kingdomkeys:lady_luck",
            "kingdomkeys:leviathan",
            "kingdomkeys:lionheart",
            "kingdomkeys:lost_memory",
            "kingdomkeys:lunar_eclipse",
            "kingdomkeys:mark_of_a_hero",
            "kingdomkeys:masters_defender",
            "kingdomkeys:maverick_flare",
            "kingdomkeys:metal_chocobo",
            "kingdomkeys:midnight_blue",
            "kingdomkeys:midnight_roar",
            "kingdomkeys:mirage_split",
            "kingdomkeys:missing_ache",
            "kingdomkeys:monochrome",
            "kingdomkeys:moogle_o_glory",
            "kingdomkeys:mysterious_abyss",
            "kingdomkeys:nightmares_end",
            "kingdomkeys:nightmares_end_and_mirage_split",
            "kingdomkeys:no_name",
            "kingdomkeys:no_name_bbs",
            "kingdomkeys:oathkeeper",
            "kingdomkeys:oblivion",
            "kingdomkeys:oceans_rage",
            "kingdomkeys:olympia",
            "kingdomkeys:omega_weapon",
            "kingdomkeys:ominous_blight",
            "kingdomkeys:one_winged_angel",
            "kingdomkeys:pain_of_solitude",
            "kingdomkeys:phantom_green",
            "kingdomkeys:photon_debugger",
            "kingdomkeys:pixie_petal",
            "kingdomkeys:pumpkinhead",
            "kingdomkeys:rainfell",
            "kingdomkeys:rejection_of_fate",
            "kingdomkeys:royal_radiance",
            "kingdomkeys:rumbling_rose",
            "kingdomkeys:shooting_star",
            "kingdomkeys:sign_of_innocence",
            "kingdomkeys:silent_dirge",
            "kingdomkeys:skull_noise",
            "kingdomkeys:sleeping_lion",
            "kingdomkeys:soul_eater",
            "kingdomkeys:spellbinder",
            "kingdomkeys:star_cluster",
            "kingdomkeys:star_seeker",
            "kingdomkeys:starlight",
            "kingdomkeys:stormfall",
            "kingdomkeys:stroke_of_midnight",
            "kingdomkeys:sweet_dreams",
            "kingdomkeys:sweet_memories",
            "kingdomkeys:sweetstack",
            "kingdomkeys:three_wishes",
            "kingdomkeys:total_eclipse",
            "kingdomkeys:treasure_trove",
            "kingdomkeys:true_lights_flight",
            "kingdomkeys:twilight_blaze",
            "kingdomkeys:two_become_one",
            "kingdomkeys:ultima_weapon_bbs",
            "kingdomkeys:ultima_weapon_ddd",
            "kingdomkeys:ultima_weapon_kh1",
            "kingdomkeys:ultima_weapon_kh2",
            "kingdomkeys:ultima_weapon_kh3",
            "kingdomkeys:umbrella",
            "kingdomkeys:unbound",
            "kingdomkeys:victory_line",
            "kingdomkeys:void_gear",
            "kingdomkeys:void_gear_remnant",
            "kingdomkeys:way_to_the_dawn",
            "kingdomkeys:wayward_wind",
            "kingdomkeys:wheel_of_fate",
            "kingdomkeys:winners_proof",
            "kingdomkeys:wishing_lamp",
            "kingdomkeys:wishing_star",
            "kingdomkeys:wooden_keyblade",
            "kingdomkeys:wooden_keyblade",
            "kingdomkeys:wooden_stick",
            "kingdomkeys:young_xehanorts_keyblade",
            "kingdomkeys:young_xehanorts_keyblade",
            "kingdomkeys:zero_one"
          //@formatter:on
          )));
      keybladeItemDamageIncrease = builder
          .comment(
              "Increases the dealt damage with the keyblade by the amount of % (0 = disabled).")
          .defineInRange("keybladeItemDamageIncrease", 50, 0, 1000);
      keybladeItemDurabilityIncrease = builder
          .comment("Increases the durability of the keyblade by the amount of % (0 = disabled).")
          .defineInRange("keybladeItemDurabilityIncrease", 0, 0, 1000);
      builder.pop();

      builder.push("Pickaxes");
      pickaxeItems = builder.comment("List of pickaxe items.").define("pickaxeItems",
          new ArrayList<String>(Arrays.asList(
          //@formatter:off
            "advancednetherite:netherite_diamond_pickaxe",
            "advancednetherite:netherite_emerald_pickaxe",
            "advancednetherite:netherite_gold_pickaxe",
            "advancednetherite:netherite_iron_pickaxe",
            "aquaculture:neptunium_pickaxe",
            "blue_skies:aquite_pickaxe",
            "blue_skies:bluebright_pickaxe",
            "blue_skies:charoite_pickaxe",
            "blue_skies:cherry_pickaxe",
            "blue_skies:diopside_pickaxe",
            "blue_skies:dusk_pickaxe",
            "blue_skies:frostbright_pickaxe",
            "blue_skies:horizonite_pickaxe",
            "blue_skies:lunar_pickaxe",
            "blue_skies:lunar_stone_pickaxe",
            "blue_skies:maple_pickaxe",
            "blue_skies:pyrope_pickaxe",
            "blue_skies:starlit_pickaxe",
            "blue_skies:turquoise_stone_pickaxe",
            "botania:elementium_pickaxe",
            "botania:glass_pickaxe",
            "botania:manasteel_pick",
            "buddycards:buddysteel_pickaxe",
            "buddycards:luminis_pickaxe",
            "byg:pendorite_pickaxe",
            "gobber2:gobber2_pickaxe",
            "gobber2:gobber2_pickaxe_end",
            "gobber2:gobber2_pickaxe_nether",
            "guns_galore:rubber_1_pickaxe",
            "guns_galore:steel_pickaxe",
            "immersiveengineering:pickaxe_steel",
            "material_elements_armor_tools_weapons:copper_pickaxe",
            "material_elements_armor_tools_weapons:steel_pickaxe",
            "mekanismtools:bronze_pickaxe",
            "mekanismtools:lapis_lazuli_pickaxe",
            "mekanismtools:osmium_pickaxe",
            "mekanismtools:refined_glowstone_pickaxe",
            "mekanismtools:refined_obsidian_pickaxe",
            "mekanismtools:steel_pickaxe",
            "minecraft:diamond_pickaxe",
            "minecraft:golden_pickaxe",
            "minecraft:iron_pickaxe",
            "minecraft:netherite_pickaxe",
            "minecraft:stone_pickaxe",
            "minecraft:wooden_pickaxe",
            "miningmaster:haste_peridot_pickaxe",
            "miningmaster:kinetic_opal_pickaxe",
            "miningmaster:lucky_citrine_pickaxe",
            "miningmaster:power_pyrite_pickaxe",
            "miningmaster:ultima_pickaxe",
            "nethers_exoticism:kiwano_pickaxe",
            "seadwellers:aqua_pickaxe",
            "tconstruct:pickaxe",
            "tropicraft:eudialyte_pickaxe",
            "tropicraft:zircon_pickaxe",
            "tropicraft:zirconium_pickaxe",
            "twilightforest:fiery_pickaxe",
            "twilightforest:giant_pickaxe",
            "twilightforest:ironwood_pickaxe",
            "twilightforest:knightmetal_pickaxe",
            "twilightforest:mazebreaker_pickaxe",
            "twilightforest:steeleaf_pickaxe",
            "undergarden:cloggrum_pickaxe",
            "undergarden:forgotten_pickaxe",
            "undergarden:froststeel_pickaxe",
            "undergarden:utherium_pickaxe"
          //@formatter:on
          )));
      pickaxeItemDamageIncrease = builder
          .comment("Increases the dealt damage with the pickaxe by the amount of % (0 = disabled).")
          .defineInRange("pickaxeItemDamageIncrease", 75, 0, 1000);
      pickaxeItemDurabilityIncrease = builder
          .comment("Increases the durability of the pickaxe by the amount of % (0 = disabled).")
          .defineInRange("pickaxeItemDurabilityIncrease", 50, 0, 1000);
      builder.pop();

      builder.push("Shields");
      shieldItems = builder.comment("List of shield items.").define("shieldItems",
          new ArrayList<String>(Arrays.asList(
          //@formatter:off
            "ageofweapons:shield_diamond",
            "ageofweapons:shield_gold",
            "ageofweapons:shield_iron",
            "ageofweapons:shield_stone",
            "ageofweapons:shield_wood",
            "blue_skies:moonstone_shield",
            "blue_skies:spike_shield",
            "botania:cosmetic_lusitanic_shield",
            "immersiveengineering:shield",
            "kingdomkeys:cryolite_shield",
            "kingdomkeys:diamond_shield",
            "kingdomkeys:dream_shield",
            "magistuarmory:bronze_buckler",
            "magistuarmory:bronze_ellipticalshield",
            "magistuarmory:bronze_heatershield",
            "magistuarmory:bronze_kiteshield",
            "magistuarmory:bronze_pavese",
            "magistuarmory:bronze_rondache",
            "magistuarmory:bronze_roundshield",
            "magistuarmory:bronze_target",
            "magistuarmory:bronze_tartsche",
            "magistuarmory:copper_buckler",
            "magistuarmory:copper_ellipticalshield",
            "magistuarmory:copper_heatershield",
            "magistuarmory:copper_kiteshield",
            "magistuarmory:copper_pavese",
            "magistuarmory:copper_rondache",
            "magistuarmory:copper_roundshield",
            "magistuarmory:copper_target",
            "magistuarmory:copper_tartsche",
            "magistuarmory:diamond_buckler",
            "magistuarmory:diamond_ellipticalshield",
            "magistuarmory:diamond_heatershield",
            "magistuarmory:diamond_kiteshield",
            "magistuarmory:diamond_pavese",
            "magistuarmory:diamond_rondache",
            "magistuarmory:diamond_roundshield",
            "magistuarmory:diamond_target",
            "magistuarmory:diamond_tartsche",
            "magistuarmory:gold_buckler",
            "magistuarmory:gold_ellipticalshield",
            "magistuarmory:gold_heatershield",
            "magistuarmory:gold_kiteshield",
            "magistuarmory:gold_pavese",
            "magistuarmory:gold_rondache",
            "magistuarmory:gold_roundshield",
            "magistuarmory:gold_target",
            "magistuarmory:gold_tartsche",
            "magistuarmory:iron_buckler",
            "magistuarmory:iron_ellipticalshield",
            "magistuarmory:iron_heatershield",
            "magistuarmory:iron_kiteshield",
            "magistuarmory:iron_pavese",
            "magistuarmory:iron_rondache",
            "magistuarmory:iron_roundshield",
            "magistuarmory:iron_target",
            "magistuarmory:iron_tartsche",
            "magistuarmory:netherite_buckler",
            "magistuarmory:netherite_ellipticalshield",
            "magistuarmory:netherite_heatershield",
            "magistuarmory:netherite_kiteshield",
            "magistuarmory:netherite_pavese",
            "magistuarmory:netherite_rondache",
            "magistuarmory:netherite_roundshield",
            "magistuarmory:netherite_target",
            "magistuarmory:netherite_tartsche",
            "magistuarmory:silver_buckler",
            "magistuarmory:silver_ellipticalshield",
            "magistuarmory:silver_heatershield",
            "magistuarmory:silver_kiteshield",
            "magistuarmory:silver_pavese",
            "magistuarmory:silver_rondache",
            "magistuarmory:silver_roundshield",
            "magistuarmory:silver_target",
            "magistuarmory:silver_tartsche",
            "magistuarmory:steel_buckler",
            "magistuarmory:steel_ellipticalshield",
            "magistuarmory:steel_heatershield",
            "magistuarmory:steel_kiteshield",
            "magistuarmory:steel_pavese",
            "magistuarmory:steel_rondache",
            "magistuarmory:steel_roundshield",
            "magistuarmory:steel_target",
            "magistuarmory:steel_tartsche",
            "magistuarmory:stone_buckler",
            "magistuarmory:stone_ellipticalshield",
            "magistuarmory:stone_heatershield",
            "magistuarmory:stone_kiteshield",
            "magistuarmory:stone_pavese",
            "magistuarmory:stone_rondache",
            "magistuarmory:stone_roundshield",
            "magistuarmory:stone_target",
            "magistuarmory:stone_tartsche",
            "magistuarmory:tin_buckler",
            "magistuarmory:tin_ellipticalshield",
            "magistuarmory:tin_heatershield",
            "magistuarmory:tin_kiteshield",
            "magistuarmory:tin_pavese",
            "magistuarmory:tin_rondache",
            "magistuarmory:tin_roundshield",
            "magistuarmory:tin_target",
            "magistuarmory:tin_tartsche",
            "magistuarmory:wood_buckler",
            "magistuarmory:wood_ellipticalshield",
            "magistuarmory:wood_heatershield",
            "magistuarmory:wood_kiteshield",
            "magistuarmory:wood_pavese",
            "magistuarmory:wood_rondache",
            "magistuarmory:wood_roundshield",
            "magistuarmory:wood_target",
            "magistuarmory:wood_tartsche",
            "mekanismtools:bronze_shield",
            "mekanismtools:lapis_lazuli_shield",
            "mekanismtools:osmium_shield",
            "mekanismtools:refined_glowstone_shield",
            "mekanismtools:refined_obsidian_shield",
            "mekanismtools:steel_shield",
            "minecraft:shield",
            "nethers_exoticism:rambutan_shield",
            "twilightforest:knightmetal_shield",
            "twilightforest:stronghold_shield",
            "undergarden:cloggrum_shield"
          //@formatter:on
          )));
      shieldItemDamageIncrease = builder
          .comment("Increases the dealt damage with the shield by the amount of % (0 = disabled).")
          .defineInRange("shieldItemDamageIncrease", 50, 0, 1000);
      shieldItemDurabilityIncrease = builder
          .comment("Increases the durability of the shield by the amount of % (0 = disabled).")
          .defineInRange("shieldItemDurabilityIncrease", 0, 0, 1000);
      builder.pop();

      builder.push("Spears");
      spearItems = builder.comment("List of spear items.").define("spearItems",
          new ArrayList<String>(Arrays.asList(
          //@formatter:off
            "ageofweapons:caveman_spear",
            "blue_skies:bluebright_spear",
            "blue_skies:cherry_spear",
            "blue_skies:dusk_spear",
            "blue_skies:frostbright_spear",
            "blue_skies:lunar_spear",
            "blue_skies:maple_spear",
            "blue_skies:soulbound",
            "blue_skies:starlit_spear",
            "magistuarmory:bronze_ahlspiess",
            "magistuarmory:bronze_pike",
            "magistuarmory:bronze_ranseur",
            "magistuarmory:copper_ahlspiess",
            "magistuarmory:copper_pike",
            "magistuarmory:copper_ranseur",
            "magistuarmory:diamond_ahlspiess",
            "magistuarmory:diamond_pike",
            "magistuarmory:diamond_ranseur",
            "magistuarmory:gold_ahlspiess",
            "magistuarmory:gold_pike",
            "magistuarmory:gold_ranseur",
            "magistuarmory:iron_ahlspiess",
            "magistuarmory:iron_pike",
            "magistuarmory:iron_ranseur",
            "magistuarmory:netherite_ahlspiess",
            "magistuarmory:netherite_pike",
            "magistuarmory:netherite_ranseur",
            "magistuarmory:silver_ahlspiess",
            "magistuarmory:silver_pike",
            "magistuarmory:silver_ranseur",
            "magistuarmory:steel_ahlspiess",
            "magistuarmory:steel_pike",
            "magistuarmory:steel_ranseur",
            "magistuarmory:stone_ahlspiess",
            "magistuarmory:stone_pike",
            "magistuarmory:stone_ranseur",
            "magistuarmory:tin_ahlspiess",
            "magistuarmory:tin_pike",
            "magistuarmory:tin_ranseur",
            "magistuarmory:wood_ahlspiess",
            "magistuarmory:wood_pike",
            "magistuarmory:wood_ranseur",
            "minecolonies:spear",
            "minecraft:trident",
            "minecraftdungeons:whispering_spear",
            "tropicraft:bamboo_spear"
          //@formatter:on
          )));
      spearItemDamageIncrease = builder
          .comment("Increases the dealt damage with the spear by the amount of % (0 = disabled).")
          .defineInRange("spearItemDamageIncrease", 50, 0, 1000);
      spearItemDurabilityIncrease = builder
          .comment("Increases the durability of the spear by the amount of % (0 = disabled).")
          .defineInRange("spearItemDurabilityIncrease", 0, 0, 1000);
      builder.pop();

      builder.push("Swords");
      swordItems = builder.comment("List of sword items.").define("swordItems",
          new ArrayList<String>(Arrays.asList(
          //@formatter:off
            "advancednetherite:netherite_diamond_sword",
            "advancednetherite:netherite_emerald_sword",
            "advancednetherite:netherite_gold_sword",
            "advancednetherite:netherite_iron_sword",
            "aquaculture:neptunium_sword",
            "blue_skies:aquite_sword",
            "blue_skies:bluebright_sword",
            "blue_skies:charoite_sword",
            "blue_skies:cherry_sword",
            "blue_skies:debug_sword",
            "blue_skies:different_sword",
            "blue_skies:diopside_sword",
            "blue_skies:dusk_sword",
            "blue_skies:frostbright_sword",
            "blue_skies:horizonite_sword",
            "blue_skies:infused_arc_sword",
            "blue_skies:lunar_stone_sword",
            "blue_skies:lunar_sword",
            "blue_skies:maple_sword",
            "blue_skies:pyrope_sword",
            "blue_skies:starlit_sword",
            "blue_skies:turquoise_stone_sword",
            "botania:elementium_sword",
            "botania:manasteel_sword",
            "botania:star_sword",
            "botania:terra_sword",
            "botania:thunder_sword",
            "buddycards:buddysteel_sword",
            "byg:pendorite_sword",
            "cwsr:atlantis_sword",
            "cwsr:beast_sword",
            "cwsr:combustion_sword",
            "cwsr:cyan_sword",
            "cwsr:dark_sword",
            "cwsr:earth_sword",
            "cwsr:ender_sword",
            "cwsr:fire_sword",
            "cwsr:golem_sword",
            "cwsr:ice_sword",
            "cwsr:light_sword",
            "cwsr:meteor_sword",
            "cwsr:steam_sword",
            "cwsr:thunder_sword",
            "cwsr:thunderstorm_sword",
            "cwsr:time_sword",
            "cwsr:water_sword",
            "cwsr:wind_sword",
            "elementalswords:air_sword",
            "elementalswords:earth_sword",
            "elementalswords:fire_sword",
            "elementalswords:fusion_sword",
            "elementalswords:water_sword",
            "gobber2:gobber2_sword",
            "gobber2:gobber2_sword_end",
            "gobber2:gobber2_sword_nether",
            "gobber2:gobber2_sword_sniper",
            "gobber2:gobber2_sword_traveler",
            "guns_galore:rubber_1_sword",
            "guns_galore:steel_sword",
            "immersiveengineering:sword_steel",
            "kingdomkeys:dream_sword",
            "magistuarmory:bronze_shortsword",
            "magistuarmory:bronze_stylet",
            "magistuarmory:copper_shortsword",
            "magistuarmory:copper_stylet",
            "magistuarmory:diamond_shortsword",
            "magistuarmory:diamond_stylet",
            "magistuarmory:gold_shortsword",
            "magistuarmory:gold_stylet",
            "magistuarmory:iron_shortsword",
            "magistuarmory:iron_stylet",
            "magistuarmory:messer_sword",
            "magistuarmory:netherite_shortsword",
            "magistuarmory:netherite_stylet",
            "magistuarmory:noble_sword",
            "magistuarmory:silver_shortsword",
            "magistuarmory:silver_stylet",
            "magistuarmory:steel_shortsword",
            "magistuarmory:steel_stylet",
            "magistuarmory:stone_shortsword",
            "magistuarmory:stone_stylet",
            "magistuarmory:tin_shortsword",
            "magistuarmory:tin_stylet",
            "magistuarmory:wood_shortsword",
            "magistuarmory:wood_stylet",
            "material_elements_armor_tools_weapons:copper_sword",
            "material_elements_armor_tools_weapons:steel_sword",
            "mekanismtools:bronze_sword",
            "mekanismtools:lapis_lazuli_sword",
            "mekanismtools:osmium_sword",
            "mekanismtools:refined_glowstone_sword",
            "mekanismtools:refined_obsidian_sword",
            "mekanismtools:steel_sword",
            "minecraft:diamond_sword",
            "minecraft:golden_sword",
            "minecraft:iron_sword",
            "minecraft:netherite_sword",
            "minecraft:stone_sword",
            "minecraft:wooden_sword",
            "miningmaster:fire_ruby_sword",
            "miningmaster:ice_sapphire_sword",
            "miningmaster:kinetic_opal_sword",
            "miningmaster:lucky_citrine_sword",
            "miningmaster:power_pyrite_sword",
            "miningmaster:spirit_garnet_sword",
            "miningmaster:ultima_sword",
            "mythicbotany:alfsteel_sword",
            "nethers_exoticism:kiwano_sword",
            "seadwellers:aqua_sword",
            "tconstruct:sword",
            "tropicraft:eudialyte_sword",
            "tropicraft:zircon_sword",
            "tropicraft:zirconium_sword",
            "twilightforest:fiery_sword",
            "twilightforest:giant_sword",
            "twilightforest:glass_sword",
            "twilightforest:ice_sword",
            "twilightforest:ironwood_sword",
            "twilightforest:knightmetal_sword",
            "twilightforest:steeleaf_sword",
            "undergarden:cloggrum_sword",
            "undergarden:forgotten_sword",
            "undergarden:froststeel_sword",
            "undergarden:utherium_sword",
            "useless_sword:alloy_sword",
            "useless_sword:amethyst_sword",
            "useless_sword:beacon_sword",
            "useless_sword:brain_coral_sword",
            "useless_sword:bubble_coral_sword",
            "useless_sword:bugged_diamond_sword",
            "useless_sword:bugged_netherite_sword",
            "useless_sword:cactus_sword",
            "useless_sword:charged_copper_sword",
            "useless_sword:charged_sword",
            "useless_sword:chocolate_sword",
            "useless_sword:clay_sword",
            "useless_sword:coal_sword",
            "useless_sword:copper_sword",
            "useless_sword:crismon_sword",
            "useless_sword:crying_sword",
            "useless_sword:dragon_breath_sword",
            "useless_sword:emerald_sword",
            "useless_sword:end_crystal_sword",
            "useless_sword:ender_catalyst_sword",
            "useless_sword:enderman_sword",
            "useless_sword:endstone_sword",
            "useless_sword:fire_coral_sword",
            "useless_sword:frosted_sword",
            "useless_sword:frozen_sword",
            "useless_sword:furnace_sword",
            "useless_sword:ghast_sword",
            "useless_sword:glass_sword",
            "useless_sword:glowstone_sword",
            "useless_sword:guardian_sword",
            "useless_sword:guster_sword",
            "useless_sword:honneycomb_sword",
            "useless_sword:horn_coral_sword",
            "useless_sword:lapis_lazuli_sword",
            "useless_sword:lava_sword",
            "useless_sword:life_draining_sword",
            "useless_sword:magma_sword",
            "useless_sword:magmatic_sword",
            "useless_sword:message_in_a_sword",
            "useless_sword:molten_sword",
            "useless_sword:mushroom_sword",
            "useless_sword:neptunium_grab_sword",
            "useless_sword:nether_catalyst_sword",
            "useless_sword:netherite_slash_sword",
            "useless_sword:netherrack_sword",
            "useless_sword:prismarine_sword",
            "useless_sword:pyro_soul_sword",
            "useless_sword:pyro_sword",
            "useless_sword:quartz_sword",
            "useless_sword:ravager_sword",
            "useless_sword:red_sand_sword",
            "useless_sword:red_sandstone_sword",
            "useless_sword:redstone_sword",
            "useless_sword:sand_sword",
            "useless_sword:sandstone_sword",
            "useless_sword:shulker_sword",
            "useless_sword:slime_sword",
            "useless_sword:soul_sand_sword",
            "useless_sword:spider_sword",
            "useless_sword:tamed_sword",
            "useless_sword:thorny_diamond_sword",
            "useless_sword:thorny_golden_sword",
            "useless_sword:thorny_iron_sword",
            "useless_sword:thorny_stone_sword",
            "useless_sword:thorny_wooden_sword",
            "useless_sword:tube_coral_sword",
            "useless_sword:turtle_sword",
            "useless_sword:undying_sword",
            "useless_sword:valhalla_sword",
            "useless_sword:vex_sword",
            "useless_sword:warped_sword",
            "useless_sword:water_sword",
            "useless_sword:witherred_sword"
          //@formatter:on
          )));
      swordItemDamageIncrease = builder
          .comment("Increases the dealt damage with the sword by the amount of % (0 = disabled).")
          .defineInRange("swordItemDamageIncrease", 50, 0, 1000);
      swordItemDurabilityIncrease = builder
          .comment("Increases the durability of the sword by the amount of % (0 = disabled).")
          .defineInRange("swordItemDurabilityIncrease", 0, 0, 1000);
      builder.pop();
    }
  }

}
