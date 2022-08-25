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

  private static final String AXE_TEXT = "axe";
  private static final String BOW_TEXT = "bow";
  private static final String CROSSBOW_TEXT = "crossbow";
  private static final String DAGGER_TEXT = "dagger";
  private static final String GREAT_SWORD_TEXT = "great sword";
  private static final String GUN_TEXT = "gun";
  private static final String HAMMER_TEXT = "hammer";
  private static final String HAND_TO_HAND_TEXT = "hand to hand";
  private static final String HOE_TEXT = "hoe";
  private static final String KATANA_TEXT = "katana";
  private static final String KEYBLADE_TEXT = "keyblade";
  private static final String PICKAXE_TEXT = "pickaxe";
  private static final String POLEARM_TEXT = "polearm";
  private static final String SCYTHE_TEXT = "scythe";
  private static final String SHIELD_TEXT = "shield";
  private static final String SHOVEL_TEXT = "shovel";
  private static final String STAFF_TEXT = "staff";
  private static final String SWORD_TEXT = "sword";
  private static final String TACHI_TEXT = "tachi";
  private static final String WAND_TEXT = "wand";

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
    public final ForgeConfigSpec.BooleanValue weaponClassLevelUpMessage;

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

    public final ForgeConfigSpec.ConfigValue<List<String>> handToHandItems;
    public final ForgeConfigSpec.IntValue handToHandItemDamageIncrease;
    public final ForgeConfigSpec.IntValue handToHandItemDurabilityIncrease;

    public final ForgeConfigSpec.ConfigValue<List<String>> hoeItems;
    public final ForgeConfigSpec.IntValue hoeItemDamageIncrease;
    public final ForgeConfigSpec.IntValue hoeItemDurabilityIncrease;

    public final ForgeConfigSpec.ConfigValue<List<String>> katanaItems;
    public final ForgeConfigSpec.IntValue katanaItemDamageIncrease;
    public final ForgeConfigSpec.IntValue katanaItemDurabilityIncrease;

    public final ForgeConfigSpec.ConfigValue<List<String>> keybladeItems;
    public final ForgeConfigSpec.IntValue keybladeItemDamageIncrease;
    public final ForgeConfigSpec.IntValue keybladeItemDurabilityIncrease;

    public final ForgeConfigSpec.ConfigValue<List<String>> pickaxeItems;
    public final ForgeConfigSpec.IntValue pickaxeItemDamageIncrease;
    public final ForgeConfigSpec.IntValue pickaxeItemDurabilityIncrease;

    public final ForgeConfigSpec.ConfigValue<List<String>> polearmItems;
    public final ForgeConfigSpec.IntValue polearmItemDamageIncrease;
    public final ForgeConfigSpec.IntValue polearmItemDurabilityIncrease;

    public final ForgeConfigSpec.ConfigValue<List<String>> scytheItems;
    public final ForgeConfigSpec.IntValue scytheItemDamageIncrease;
    public final ForgeConfigSpec.IntValue scytheItemDurabilityIncrease;

    public final ForgeConfigSpec.ConfigValue<List<String>> shieldItems;
    public final ForgeConfigSpec.IntValue shieldItemDamageIncrease;
    public final ForgeConfigSpec.IntValue shieldItemDurabilityIncrease;

    public final ForgeConfigSpec.ConfigValue<List<String>> shovelItems;
    public final ForgeConfigSpec.IntValue shovelItemDamageIncrease;
    public final ForgeConfigSpec.IntValue shovelItemDurabilityIncrease;

    public final ForgeConfigSpec.ConfigValue<List<String>> staffItems;
    public final ForgeConfigSpec.IntValue staffItemDamageIncrease;
    public final ForgeConfigSpec.IntValue staffItemDurabilityIncrease;

    public final ForgeConfigSpec.ConfigValue<List<String>> swordItems;
    public final ForgeConfigSpec.IntValue swordItemDamageIncrease;
    public final ForgeConfigSpec.IntValue swordItemDurabilityIncrease;

    public final ForgeConfigSpec.ConfigValue<List<String>> tachiItems;
    public final ForgeConfigSpec.IntValue tachiItemDamageIncrease;
    public final ForgeConfigSpec.IntValue tachiItemDurabilityIncrease;

    public final ForgeConfigSpec.ConfigValue<List<String>> wandItems;
    public final ForgeConfigSpec.IntValue wandItemDamageIncrease;
    public final ForgeConfigSpec.IntValue wandItemDurabilityIncrease;

    Config(ForgeConfigSpec.Builder builder) {
      builder.comment(
          "Dynamic Player Progression and Difficulty\nNote: Some changes requires are server restart!");

      builder.push("Gui");
      guiButtonEnabled = builder.comment("Enable/Disable stats button in the inventory screen.")
          .define("guiButtonEnabled", true);
      guiButtonPositionLeft =
          builder.comment("Defines the left position relative to the inventory screen.")
              .defineInRange("guiButtonPositionLeft", 63, 0, 640);
      guiButtonPositionTop =
          builder.comment("Defines the top position relative to the inventory screen.")
              .defineInRange("guiButtonPositionTop", 10, 0, 480);
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
      weaponClassIgnoredItems = builder.comment(
          "List of ignored items for the weapon class mapping e.g. [\"immersiveengineering:gunpart_hammer\", \"tconstruct:hammer_head\", ...]")
          .define("weaponClassIgnoredItems", new ArrayList<String>(Arrays.asList()));
      weaponClassLevelUpMessage =
          builder.comment("Enable/Disable level up player messages for weapon classes.")
              .define("weaponClassLevelUpMessage", true);
      builder.pop();

      builder.push("Axes");
      axeItems = builder.comment(listOfItemsDescription(AXE_TEXT)).define("axeItems",
          new ArrayList<String>(Arrays.asList()));
      axeItemDamageIncrease = builder.comment(increaseDealtDamageDescription(AXE_TEXT))
          .defineInRange("axeItemDamageIncrease", 75, 0, 1000);
      axeItemDurabilityIncrease = builder.comment(increaseDurabilityDescription(AXE_TEXT))
          .defineInRange("axeItemDurabilityIncrease", 150, 0, 1000);
      builder.pop();

      builder.push("Bows");
      bowItems = builder.comment(listOfItemsDescription(BOW_TEXT)).define("bowItems",
          new ArrayList<String>(Arrays.asList()));
      bowItemDamageIncrease = builder.comment(increaseDealtDamageDescription(BOW_TEXT))
          .defineInRange("bowItemDamageIncrease", 50, 0, 1000);
      bowItemDurabilityIncrease = builder.comment(increaseDurabilityDescription(BOW_TEXT))
          .defineInRange("bowItemDurabilityIncrease", 0, 0, 1000);
      builder.pop();

      builder.push("Crossbows");
      crossbowItems = builder.comment(listOfItemsDescription(CROSSBOW_TEXT)).define("crossbowItems",
          new ArrayList<String>(Arrays.asList()));
      crossbowItemDamageIncrease = builder.comment(increaseDealtDamageDescription(CROSSBOW_TEXT))
          .defineInRange("crossbowItemDamageIncrease", 50, 0, 1000);
      crossbowItemDurabilityIncrease = builder.comment(increaseDurabilityDescription(CROSSBOW_TEXT))
          .defineInRange("crossbowItemDurabilityIncrease", 0, 0, 1000);
      builder.pop();

      builder.push("Dagger");
      daggerItems = builder.comment(listOfItemsDescription(DAGGER_TEXT)).define("daggerItems",
          new ArrayList<String>(Arrays.asList()));
      daggerItemDamageIncrease = builder.comment(increaseDealtDamageDescription(DAGGER_TEXT))
          .defineInRange("daggerItemDamageIncrease", 50, 0, 1000);
      daggerItemDurabilityIncrease = builder.comment(increaseDurabilityDescription(DAGGER_TEXT))
          .defineInRange("daggerItemDurabilityIncrease", 25, 0, 1000);
      builder.pop();

      builder.push("Great Sword");
      greatSwordItems = builder.comment(listOfItemsDescription(GREAT_SWORD_TEXT))
          .define("greatSwordItems", new ArrayList<String>(Arrays.asList()));
      greatSwordItemDamageIncrease =
          builder.comment(increaseDealtDamageDescription(GREAT_SWORD_TEXT))
              .defineInRange("greatSwordItemDamageIncrease", 50, 0, 1000);
      greatSwordItemDurabilityIncrease =
          builder.comment(increaseDurabilityDescription(GREAT_SWORD_TEXT))
              .defineInRange("greatSwordItemDurabilityIncrease", 0, 0, 1000);
      builder.pop();

      builder.push("Guns");
      gunItems = builder.comment(listOfItemsDescription(GUN_TEXT)).define("gunItems",
          new ArrayList<String>(Arrays.asList()));
      gunItemDamageIncrease = builder.comment(increaseDealtDamageDescription(GUN_TEXT))
          .defineInRange("gunItemDamageIncrease", 50, 0, 1000);
      gunItemDurabilityIncrease = builder.comment(increaseDurabilityDescription(GUN_TEXT))
          .defineInRange("gunItemDurabilityIncrease", 0, 0, 1000);
      builder.pop();

      builder.push("Hammers");
      hammerItems = builder.comment(listOfItemsDescription(HAMMER_TEXT)).define("hammerItems",
          new ArrayList<String>(Arrays.asList()));
      hammerItemDamageIncrease = builder.comment(increaseDealtDamageDescription(HAMMER_TEXT))
          .defineInRange("hammerItemDamageIncrease", 50, 0, 1000);
      hammerItemDurabilityIncrease = builder.comment(increaseDurabilityDescription(HAMMER_TEXT))
          .defineInRange("hammerItemDurabilityIncrease", 25, 0, 1000);
      builder.pop();

      builder.push("Hand to Hand");
      handToHandItems = builder.comment(listOfItemsDescription(HAND_TO_HAND_TEXT))
          .define("handToHandItems", new ArrayList<String>(Arrays.asList()));
      handToHandItemDamageIncrease =
          builder.comment(increaseDealtDamageDescription(HAND_TO_HAND_TEXT))
              .defineInRange("handToHandItemDamageIncrease", 50, 0, 1000);
      handToHandItemDurabilityIncrease =
          builder.comment(increaseDurabilityDescription(HAND_TO_HAND_TEXT))
              .defineInRange("handToHandItemDurabilityIncrease", 25, 0, 1000);
      builder.pop();

      builder.push("Hoe");
      hoeItems = builder.comment(listOfItemsDescription(HOE_TEXT)).define("hoeItems",
          new ArrayList<String>(Arrays.asList()));
      hoeItemDamageIncrease = builder.comment(increaseDealtDamageDescription(HOE_TEXT))
          .defineInRange("hoeItemDamageIncrease", 200, 0, 1000);
      hoeItemDurabilityIncrease = builder.comment(increaseDurabilityDescription(HOE_TEXT))
          .defineInRange("hoeItemDurabilityIncrease", 25, 0, 1000);
      builder.pop();

      builder.push("Katana");
      katanaItems = builder.comment(listOfItemsDescription(KATANA_TEXT)).define("katanaItems",
          new ArrayList<String>(Arrays.asList()));
      katanaItemDamageIncrease = builder.comment(increaseDealtDamageDescription(KATANA_TEXT))
          .defineInRange("katanaItemDamageIncrease", 75, 0, 1000);
      katanaItemDurabilityIncrease = builder.comment(increaseDurabilityDescription(KATANA_TEXT))
          .defineInRange("katanaItemDurabilityIncrease", 25, 0, 1000);
      builder.pop();

      builder.push("Keyblades");
      keybladeItems = builder.comment(listOfItemsDescription(KEYBLADE_TEXT)).define("keybladeItems",
          new ArrayList<String>(Arrays.asList()));
      keybladeItemDamageIncrease = builder.comment(increaseDealtDamageDescription(KEYBLADE_TEXT))
          .defineInRange("keybladeItemDamageIncrease", 50, 0, 1000);
      keybladeItemDurabilityIncrease = builder.comment(increaseDurabilityDescription(KEYBLADE_TEXT))
          .defineInRange("keybladeItemDurabilityIncrease", 0, 0, 1000);
      builder.pop();

      builder.push("Pickaxes");
      pickaxeItems = builder.comment(listOfItemsDescription(PICKAXE_TEXT)).define("pickaxeItems",
          new ArrayList<String>(Arrays.asList()));
      pickaxeItemDamageIncrease = builder.comment(increaseDealtDamageDescription(PICKAXE_TEXT))
          .defineInRange("pickaxeItemDamageIncrease", 75, 0, 1000);
      pickaxeItemDurabilityIncrease = builder.comment(increaseDurabilityDescription(PICKAXE_TEXT))
          .defineInRange("pickaxeItemDurabilityIncrease", 50, 0, 1000);
      builder.pop();

      builder.push("Polearms");
      polearmItems = builder.comment(listOfItemsDescription(POLEARM_TEXT)).define("polearmItems",
          new ArrayList<String>(Arrays.asList()));
      polearmItemDamageIncrease = builder.comment(increaseDealtDamageDescription(POLEARM_TEXT))
          .defineInRange("polearmItemDamageIncrease", 50, 0, 1000);
      polearmItemDurabilityIncrease = builder.comment(increaseDurabilityDescription(POLEARM_TEXT))
          .defineInRange("polearmItemDurabilityIncrease", 0, 0, 1000);
      builder.pop();

      builder.push("Scythes");
      scytheItems = builder.comment(listOfItemsDescription(SCYTHE_TEXT)).define("scytheItems",
          new ArrayList<String>(Arrays.asList()));
      scytheItemDamageIncrease = builder.comment(increaseDealtDamageDescription(SCYTHE_TEXT))
          .defineInRange("scytheItemDamageIncrease", 100, 0, 1000);
      scytheItemDurabilityIncrease = builder.comment(increaseDurabilityDescription(SCYTHE_TEXT))
          .defineInRange("scytheItemDurabilityIncrease", 50, 0, 1000);
      builder.pop();

      builder.push("Shields");
      shieldItems = builder.comment(listOfItemsDescription(SHIELD_TEXT)).define("shieldItems",
          new ArrayList<String>(Arrays.asList()));
      shieldItemDamageIncrease = builder.comment(increaseDealtDamageDescription(SHIELD_TEXT))
          .defineInRange("shieldItemDamageIncrease", 50, 0, 1000);
      shieldItemDurabilityIncrease = builder.comment(increaseDurabilityDescription(SHIELD_TEXT))
          .defineInRange("shieldItemDurabilityIncrease", 0, 0, 1000);
      builder.pop();

      builder.push("Shovels");
      shovelItems = builder.comment(listOfItemsDescription(SHOVEL_TEXT)).define("shovelItems",
          new ArrayList<String>(Arrays.asList()));
      shovelItemDamageIncrease = builder.comment(increaseDealtDamageDescription(SHOVEL_TEXT))
          .defineInRange("shovelItemDamageIncrease", 75, 0, 1000);
      shovelItemDurabilityIncrease = builder.comment(increaseDurabilityDescription(SHOVEL_TEXT))
          .defineInRange("shovelItemDurabilityIncrease", 50, 0, 1000);
      builder.pop();

      builder.push("Staff");
      staffItems = builder.comment(listOfItemsDescription(STAFF_TEXT)).define("staffItems",
          new ArrayList<String>(Arrays.asList()));
      staffItemDamageIncrease = builder.comment(increaseDealtDamageDescription(STAFF_TEXT))
          .defineInRange("staffItemDamageIncrease", 50, 0, 1000);
      staffItemDurabilityIncrease = builder.comment(increaseDurabilityDescription(STAFF_TEXT))
          .defineInRange("staffItemDurabilityIncrease", 50, 0, 1000);
      builder.pop();

      builder.push("Swords");
      swordItems = builder.comment(listOfItemsDescription(SWORD_TEXT)).define("swordItems",
          new ArrayList<String>(Arrays.asList()));
      swordItemDamageIncrease = builder.comment(increaseDealtDamageDescription(SWORD_TEXT))
          .defineInRange("swordItemDamageIncrease", 50, 0, 1000);
      swordItemDurabilityIncrease = builder.comment(increaseDurabilityDescription(SWORD_TEXT))
          .defineInRange("swordItemDurabilityIncrease", 0, 0, 1000);
      builder.pop();

      builder.push("Tachi");
      tachiItems = builder.comment(listOfItemsDescription(TACHI_TEXT)).define("tachiItems",
          new ArrayList<String>(Arrays.asList()));
      tachiItemDamageIncrease = builder.comment(increaseDealtDamageDescription(TACHI_TEXT))
          .defineInRange("tachiItemDamageIncrease", 100, 0, 1000);
      tachiItemDurabilityIncrease = builder.comment(increaseDurabilityDescription(TACHI_TEXT))
          .defineInRange("tachiItemDurabilityIncrease", 25, 0, 1000);
      builder.pop();

      builder.push("Wand");
      wandItems = builder.comment(listOfItemsDescription(WAND_TEXT)).define("wandItems",
          new ArrayList<String>(Arrays.asList()));
      wandItemDamageIncrease = builder.comment(increaseDealtDamageDescription(WAND_TEXT))
          .defineInRange("wandItemDamageIncrease", 50, 0, 1000);
      wandItemDurabilityIncrease = builder.comment(increaseDurabilityDescription(WAND_TEXT))
          .defineInRange("wandItemDurabilityIncrease", 50, 0, 1000);
      builder.pop();
    }

    private static String listOfItemsDescription(String weaponType) {
      return "List of " + weaponType + " items for the  " + weaponType
          + " weapon class mapping e.g. [\"minecraft:iron_sword\", \"minecraft:diamond_sword\", ...]";
    }

    private static String increaseDealtDamageDescription(String weaponType) {
      return "Increases the dealt damage with the " + weaponType
          + " by the amount of % (0 = disabled).";
    }

    private static String increaseDurabilityDescription(String weaponType) {
      return "Increases the durability of the " + weaponType
          + " by the amount of % (0 = disabled).";
    }

  }

}
