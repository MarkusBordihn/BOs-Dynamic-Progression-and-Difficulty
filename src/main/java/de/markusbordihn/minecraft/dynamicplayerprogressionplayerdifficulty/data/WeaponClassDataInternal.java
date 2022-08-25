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

package de.markusbordihn.minecraft.dynamicplayerprogressionplayerdifficulty.data;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;

public class WeaponClassDataInternal {

  protected static final Set<String> ignoredItems = new HashSet<>(Arrays.asList(
  //@formatter:off,
    "immersiveengineering:bannerpattern_hammer",
    "immersiveengineering:gunpart_hammer",
    "tombstone:fishing_rod_of_misadventure",
    "tconstruct:hammer_head"
  //@formatter:on
  ));

  protected static final Set<String> ignoredItemsSuffix = new HashSet<>(
      Arrays.asList("_block", "_roof", "fishing_rod", "_cast", "_spawn_egg", "seeditem", "_ingot"));

  protected static final EnumMap<WeaponClass, Set<String>> weaponClassItemsNames =
      new EnumMap<>(WeaponClass.class);
  protected static final EnumMap<WeaponClass, Set<String>> weaponClassItemsSuffixes =
      new EnumMap<>(WeaponClass.class);
  protected static final EnumMap<WeaponClass, Set<String>> weaponClassItemsKeywords =
      new EnumMap<>(WeaponClass.class);

  protected WeaponClassDataInternal() {}

  static {
    // Pre-fill weapon class data with default hash sets.
    for (WeaponClass weaponClass : WeaponClass.values()) {
      weaponClassItemsNames.putIfAbsent(weaponClass, new HashSet<>());
      weaponClassItemsSuffixes.putIfAbsent(weaponClass, new HashSet<>());
      weaponClassItemsKeywords.putIfAbsent(weaponClass, new HashSet<>());
    }
  }

  // Axe Class
  static {
    weaponClassItemsSuffixes.put(WeaponClass.AXE,
        new HashSet<>(Arrays.asList("_axe", "battleaxe", "waraxe")));
    weaponClassItemsKeywords.put(WeaponClass.AXE,
        new HashSet<>(Arrays.asList(":axe", ":battle_axe", ":waraxe")));
  }

  // Bow Class
  static {
    weaponClassItemsSuffixes.put(WeaponClass.BOW, new HashSet<>(Arrays.asList("_bow", "longbow")));
  }

  // Crossbow Class
  static {
    weaponClassItemsSuffixes.put(WeaponClass.CROSSBOW, new HashSet<>(Arrays.asList("_crossbow")));
  }

  // Dagger Class
  static {
    weaponClassItemsSuffixes.put(WeaponClass.DAGGER,
        new HashSet<>(Arrays.asList("_knife", "_dagger", ":diamondknife", ":netheritknife")));
    weaponClassItemsKeywords.put(WeaponClass.DAGGER,
        new HashSet<>(Arrays.asList(":dagger", ":knife")));
  }

  // Great Sword Class
  static {
    weaponClassItemsNames.put(WeaponClass.GREAT_SWORD, new HashSet<>(Arrays.asList(
    //@formatter:off
      "rpg_style_more_weapins:newgenplzmasword",
      "rpg_style_more_weapins:plazmaswordhollow",
      "useless_sword:ancient_guardian_sword",
      "useless_sword:bugged_diamond_sword",
      "useless_sword:elder_guardian_sword",
      "useless_sword:guardian_sword",
      "useless_sword:nether_striker",
      "useless_sword:soul_glaive",
      "useless_sword:sword_of_fate",
      "useless_sword:sword_of_immortality",
      "useless_sword:wither_sword"
    //@formatter:on
    )));
    weaponClassItemsSuffixes.put(WeaponClass.GREAT_SWORD,
        new HashSet<>(Arrays.asList("_bastardsword", "_broadsword", "_claymore",
            "_flamebladedsword", "_zweihander", ":cleaver", "_greatsword", "_longsword")));
  }

  // Gun Class
  static {
    weaponClassItemsNames.put(WeaponClass.GUN, new HashSet<>(Arrays.asList(
    //@formatter:off
  "additionalguns:9a91",
    "additionalguns:ace_of_spades",
    "additionalguns:ak104",
    "additionalguns:ak105",
    "additionalguns:ak12",
    "additionalguns:ak15",
    "additionalguns:ak74m",
    "additionalguns:akm",
    "additionalguns:akm_custom",
    "additionalguns:aks74u",
    "additionalguns:awm",
    "additionalguns:banshee",
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
    "cgm:bazooka",
    "cgm:grenade_launcher",
    "immersiveengineering:railgun",
    "moguns:m14",
    "moguns:mp5",
    "oldguns:flintlock_nock_gun",
    "pneumaticcraft:minigun"
    //@formatter:on
    )));
    weaponClassItemsSuffixes.put(WeaponClass.GUN,
        new HashSet<>(Arrays.asList("_gun", "_rifle", "_pistol", "_gatling", "_shotgun", "_sniper",
            "_gunblade", "_musket", "_blunderbuss", "_arquebus", "_caliver", "_musketoon",
            ":desert_eagle", ":aug", ":ak74", ":magnum", ":uzi")));
    weaponClassItemsKeywords.put(WeaponClass.GUN,
        new HashSet<>(Arrays.asList(":pistol", ":rifle", ":shotgun", ":gunblade")));
  }

  // Hammer Class
  static {
    weaponClassItemsNames.put(WeaponClass.HAMMER, new HashSet<>(Arrays.asList(
    //@formatter:off
      "rpg_style_more_weapins:newgenhammer"
    //@formatter:on
    )));
    weaponClassItemsSuffixes.put(WeaponClass.HAMMER,
        new HashSet<>(Arrays.asList("_hammer", "warhammer", "_lucernhammer", "_hammer_end",
            "_hammer_neth", ":sledgehammer", ":ironhammer")));
    weaponClassItemsKeywords.put(WeaponClass.HAMMER,
        new HashSet<>(Arrays.asList(":hammer", ":warhammer", ":mattock")));
  }

  // Hammer Class
  static {
    weaponClassItemsSuffixes.put(WeaponClass.HAND_TO_HAND,
        new HashSet<>(Arrays.asList("_knuckle", ":knuckle")));
  }

  // Hoe Class
  static {
    weaponClassItemsSuffixes.put(WeaponClass.HOE, new HashSet<>(Arrays.asList("_hoe", ":kama")));
    weaponClassItemsKeywords.put(WeaponClass.HOE, new HashSet<>(Arrays.asList(":hoe")));
  }

  // Katana Class
  static {
    weaponClassItemsSuffixes.put(WeaponClass.KATANA,
        new HashSet<>(Arrays.asList("_katana", ":katana")));
  }

  // Keyblade Class
  static {
    weaponClassItemsNames.put(WeaponClass.KEYBLADE, new HashSet<>(Arrays.asList(
    //@formatter:off
      "botania:king_key",
      "kingdomkeys:abaddon_plasma",
      "kingdomkeys:abyssal_tide",
      "kingdomkeys:advent_red",
      "kingdomkeys:all_for_one",
      "kingdomkeys:astral_blast",
      "kingdomkeys:aubade",
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
      "kingdomkeys:gull_wing",
      "kingdomkeys:happy_gear",
      "kingdomkeys:heros_crest",
      "kingdomkeys:heros_origin",
      "kingdomkeys:hidden_dragon",
      "kingdomkeys:hunny_spout",
      "kingdomkeys:hyperdrive",
      "kingdomkeys:incomplete_kiblade",
      "kingdomkeys:jungle_king",
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
      "kingdomkeys:wooden_stick",
      "kingdomkeys:zero_one"
    //@formatter:on
    )));
    weaponClassItemsSuffixes.put(WeaponClass.KEYBLADE, new HashSet<>(Arrays.asList("_keyblade")));
    weaponClassItemsKeywords.put(WeaponClass.KEYBLADE, new HashSet<>(Arrays.asList(":keyblade_")));
  }

  // Pickaxe
  static {
    weaponClassItemsSuffixes.put(WeaponClass.PICKAXE, new HashSet<>(
        Arrays.asList("_pickaxe", "_pickaxe_end", "_pickaxe_nether", "_pick", ":pickadze")));
    weaponClassItemsKeywords.put(WeaponClass.PICKAXE, new HashSet<>(Arrays.asList(":pickaxe")));
  }


  // Polearm Class
  static {
    weaponClassItemsNames.put(WeaponClass.POLEARM, new HashSet<>(Arrays.asList(
    //@formatter:off
      "blue_skies:soulbound"
    //@formatter:on
    )));
    weaponClassItemsSuffixes.put(WeaponClass.POLEARM,
        new HashSet<>(Arrays.asList("_spear", "_ahlspiess", "_pike", "_ranseur", ":spear")));
  }

  // Scythe Class
  static {
    weaponClassItemsSuffixes.put(WeaponClass.SCYTHE,
        new HashSet<>(Arrays.asList("_scythe", ":scythe")));
  }

  // Shield Class
  static {
    weaponClassItemsNames.put(WeaponClass.SHIELD, new HashSet<>(Arrays.asList(
    //@formatter:off
      "ageofweapons:shield_diamond",
      "ageofweapons:shield_gold",
      "ageofweapons:shield_iron",
      "ageofweapons:shield_stone",
      "ageofweapons:shield_wood",
      "magistuarmory:bronze_target",
      "magistuarmory:copper_target",
      "magistuarmory:diamond_target",
      "magistuarmory:gold_target",
      "magistuarmory:iron_target",
      "magistuarmory:netherite_target",
      "magistuarmory:silver_target",
      "magistuarmory:steel_target",
      "magistuarmory:stone_target",
      "magistuarmory:tin_target",
      "magistuarmory:wood_target"
    //@formatter:on
    )));
    weaponClassItemsSuffixes.put(WeaponClass.SHIELD,
        new HashSet<>(Arrays.asList("_shield", "_buckler", "_ellipticalshield", "_heatershield",
            "_kiteshield", "_pavese", "_rondache", "_roundshield", "_tartsche", ":shield")));
  }

  // Shovel Class
  static {
    weaponClassItemsSuffixes.put(WeaponClass.SHOVEL,
        new HashSet<>(Arrays.asList("_shovel", ":shovel")));
  }

  // Staff Class
  static {
    weaponClassItemsSuffixes.put(WeaponClass.STAFF,
        new HashSet<>(Arrays.asList("_staff", ":staff", "_rod")));
  }

  // Sword Class
  static {
    weaponClassItemsNames.put(WeaponClass.SWORD, new HashSet<>(Arrays.asList(
    //@formatter:off
      "immersiveengineering:sword_steel",
      "magistuarmory:bronze_stylet",
      "magistuarmory:copper_stylet",
      "magistuarmory:diamond_stylet",
      "magistuarmory:gold_stylet",
      "magistuarmory:iron_stylet",
      "magistuarmory:netherite_stylet",
      "magistuarmory:silver_stylet",
      "magistuarmory:steel_stylet",
      "magistuarmory:stone_stylet",
      "magistuarmory:tin_stylet",
      "magistuarmory:wood_stylet"
    //@formatter:on
    )));
    weaponClassItemsSuffixes.put(WeaponClass.SWORD,
        new HashSet<>(Arrays.asList("_sword", "_shortsword", ":sword")));
  }

  // Tachi Class
  static {
    weaponClassItemsSuffixes.put(WeaponClass.TACHI,
        new HashSet<>(Arrays.asList("_tachi", ":tachi")));
  }

  // Wand Class
  static {
    weaponClassItemsSuffixes.put(WeaponClass.WAND,
        new HashSet<>(Arrays.asList("_wand", ":wand", "_scepter")));
  }

}
