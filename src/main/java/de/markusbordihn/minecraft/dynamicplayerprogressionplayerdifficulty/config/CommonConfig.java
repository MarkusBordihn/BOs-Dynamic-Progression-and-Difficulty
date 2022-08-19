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

    public final ForgeConfigSpec.ConfigValue<List<String>> weaponClassIgnoredItems;

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

      builder.push("Weapon Class");
      weaponClassIgnoredItems = builder.comment("List of ignored items for the mapping.")
          .define("weaponClassIgnoredItems", new ArrayList<String>(Arrays.asList(

          )));
      builder.pop();

      builder.push("Axes");
      axeItems = builder.comment(listOfItemsDescription("axe")).define("axeItems",
          new ArrayList<String>(Arrays.asList()));
      axeItemDamageIncrease = builder
          .comment("Increases the dealt damage with the axe by the amount of % (0 = disabled).")
          .defineInRange("axeItemDamageIncrease", 75, 0, 1000);
      axeItemDurabilityIncrease =
          builder.comment("Increases the durability of the axe by the amount of % (0 = disabled).")
              .defineInRange("axeItemDurabilityIncrease", 150, 0, 1000);
      builder.pop();

      builder.push("Bows");
      bowItems = builder.comment(listOfItemsDescription("bow")).define("bowItems",
          new ArrayList<String>(Arrays.asList()));
      bowItemDamageIncrease = builder
          .comment("Increases the dealt damage with the bow by the amount of % (0 = disabled).")
          .defineInRange("bowItemDamageIncrease", 50, 0, 1000);
      bowItemDurabilityIncrease =
          builder.comment("Increases the durability of the bow by the amount of % (0 = disabled).")
              .defineInRange("bowItemDurabilityIncrease", 0, 0, 1000);
      builder.pop();

      builder.push("Crossbows");
      crossbowItems = builder.comment(listOfItemsDescription("crossbow")).define("crossbowItems",
          new ArrayList<String>(Arrays.asList()));
      crossbowItemDamageIncrease = builder
          .comment(
              "Increases the dealt damage with the crossbow by the amount of % (0 = disabled).")
          .defineInRange("crossbowItemDamageIncrease", 50, 0, 1000);
      crossbowItemDurabilityIncrease =
          builder.comment("Increases the durability of the bow by the amount of % (0 = disabled).")
              .defineInRange("crossbowItemDurabilityIncrease", 0, 0, 1000);
      builder.pop();

      builder.push("Dagger");
      daggerItems = builder.comment(listOfItemsDescription("dagger")).define("daggerItems",
          new ArrayList<String>(Arrays.asList()));
      daggerItemDamageIncrease = builder
          .comment("Increases the dealt damage with the dagger by the amount of % (0 = disabled).")
          .defineInRange("daggerItemDamageIncrease", 50, 0, 1000);
      daggerItemDurabilityIncrease = builder
          .comment("Increases the durability of the dagger by the amount of % (0 = disabled).")
          .defineInRange("daggerItemDurabilityIncrease", 25, 0, 1000);
      builder.pop();

      builder.push("Great Sword");
      greatSwordItems = builder.comment(listOfItemsDescription("greatsword"))
          .define("greatSwordItems", new ArrayList<String>(Arrays.asList()));
      greatSwordItemDamageIncrease = builder
          .comment(
              "Increases the dealt damage with the great sword by the amount of % (0 = disabled).")
          .defineInRange("greatSwordItemDamageIncrease", 50, 0, 1000);
      greatSwordItemDurabilityIncrease = builder
          .comment("Increases the durability of the great sword by the amount of % (0 = disabled).")
          .defineInRange("greatSwordItemDurabilityIncrease", 0, 0, 1000);
      builder.pop();

      builder.push("Guns");
      gunItems = builder.comment(listOfItemsDescription("guns")).define("gunItems",
          new ArrayList<String>(Arrays.asList()));
      gunItemDamageIncrease = builder
          .comment("Increases the dealt damage with the gun by the amount of % (0 = disabled).")
          .defineInRange("gunItemDamageIncrease", 50, 0, 1000);
      gunItemDurabilityIncrease =
          builder.comment("Increases the durability of the gun by the amount of % (0 = disabled).")
              .defineInRange("gunItemDurabilityIncrease", 0, 0, 1000);
      builder.pop();

      builder.push("Hammers");
      hammerItems = builder.comment(listOfItemsDescription("hammer")).define("hammerItems",
          new ArrayList<String>(Arrays.asList()));
      hammerItemDamageIncrease = builder
          .comment("Increases the dealt damage with the hammer by the amount of % (0 = disabled).")
          .defineInRange("hammerItemDamageIncrease", 50, 0, 1000);
      hammerItemDurabilityIncrease = builder
          .comment("Increases the durability of the hammer by the amount of % (0 = disabled).")
          .defineInRange("hammerItemDurabilityIncrease", 25, 0, 1000);
      builder.pop();

      builder.push("Hoe");
      hoeItems = builder.comment(listOfItemsDescription("hoe")).define("hoeItems",
          new ArrayList<String>(Arrays.asList()));
      hoeItemDamageIncrease = builder
          .comment("Increases the dealt damage with the hoe by the amount of % (0 = disabled).")
          .defineInRange("hoeItemDamageIncrease", 200, 0, 1000);
      hoeItemDurabilityIncrease =
          builder.comment("Increases the durability of the hoe by the amount of % (0 = disabled).")
              .defineInRange("hoeItemDurabilityIncrease", 25, 0, 1000);
      builder.pop();

      builder.push("Keyblades");
      keybladeItems = builder.comment(listOfItemsDescription("keyblade")).define("keybladeItems",
          new ArrayList<String>(Arrays.asList()));
      keybladeItemDamageIncrease = builder
          .comment(
              "Increases the dealt damage with the keyblade by the amount of % (0 = disabled).")
          .defineInRange("keybladeItemDamageIncrease", 50, 0, 1000);
      keybladeItemDurabilityIncrease = builder
          .comment("Increases the durability of the keyblade by the amount of % (0 = disabled).")
          .defineInRange("keybladeItemDurabilityIncrease", 0, 0, 1000);
      builder.pop();

      builder.push("Pickaxes");
      pickaxeItems = builder.comment(listOfItemsDescription("pickaxe")).define("pickaxeItems",
          new ArrayList<String>(Arrays.asList()));
      pickaxeItemDamageIncrease = builder
          .comment("Increases the dealt damage with the pickaxe by the amount of % (0 = disabled).")
          .defineInRange("pickaxeItemDamageIncrease", 75, 0, 1000);
      pickaxeItemDurabilityIncrease = builder
          .comment("Increases the durability of the pickaxe by the amount of % (0 = disabled).")
          .defineInRange("pickaxeItemDurabilityIncrease", 50, 0, 1000);
      builder.pop();

      builder.push("Shields");
      shieldItems = builder.comment(listOfItemsDescription("shield")).define("shieldItems",
          new ArrayList<String>(Arrays.asList()));
      shieldItemDamageIncrease = builder
          .comment("Increases the dealt damage with the shield by the amount of % (0 = disabled).")
          .defineInRange("shieldItemDamageIncrease", 50, 0, 1000);
      shieldItemDurabilityIncrease = builder
          .comment("Increases the durability of the shield by the amount of % (0 = disabled).")
          .defineInRange("shieldItemDurabilityIncrease", 0, 0, 1000);
      builder.pop();

      builder.push("Spears");
      spearItems = builder.comment(listOfItemsDescription("spear")).define("spearItems",
          new ArrayList<String>(Arrays.asList()));
      spearItemDamageIncrease = builder
          .comment("Increases the dealt damage with the spear by the amount of % (0 = disabled).")
          .defineInRange("spearItemDamageIncrease", 50, 0, 1000);
      spearItemDurabilityIncrease = builder
          .comment("Increases the durability of the spear by the amount of % (0 = disabled).")
          .defineInRange("spearItemDurabilityIncrease", 0, 0, 1000);
      builder.pop();

      builder.push("Swords");
      swordItems = builder.comment(listOfItemsDescription("sword")).define("swordItems",
          new ArrayList<String>(Arrays.asList()));
      swordItemDamageIncrease = builder
          .comment("Increases the dealt damage with the sword by the amount of % (0 = disabled).")
          .defineInRange("swordItemDamageIncrease", 50, 0, 1000);
      swordItemDurabilityIncrease = builder
          .comment("Increases the durability of the sword by the amount of % (0 = disabled).")
          .defineInRange("swordItemDurabilityIncrease", 0, 0, 1000);
      builder.pop();
    }

    private static String listOfItemsDescription(String weaponType) {
      return "List of " + weaponType + " items for the  " + weaponType
          + " weapon class mapping e.g. [\"minecraft:iron_sword\", \"minecraft:diamond_sword\", ...]";
    }
  }

}
