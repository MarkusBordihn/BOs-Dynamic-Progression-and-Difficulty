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
import java.util.List;

public class WeaponClassDataInternal {

  protected static final List<String> ignoredItems = Arrays.asList(
  //@formatter:off
    "immersiveengineering:bannerpattern_hammer",
    "immersiveengineering:gunpart_hammer",
    "tconstruct:hammer_head_cast",
    "tconstruct:hammer_head_red_sand_cast",
    "tconstruct:hammer_head_sand_cast",
    "tropicraft:hammerhead_spawn_egg",
    "tconstruct:hammer_head"
  //@formatter:on
  );

  // == Axe Class
  protected static final List<String> axeItems = Arrays.asList(
  //@formatter:off
  //@formatter:on
  );
  protected static final List<String> axeItemsSuffix = Arrays.asList("_axe", "battleaxe", "waraxe");
  protected static final List<String> axeItemsKeywords =
      Arrays.asList(":axe", ":battle_axe", ":waraxe");

  // == Bow Class
  protected static final List<String> bowItems = Arrays.asList(
  //@formatter:off
  //@formatter:on
  );
  protected static final List<String> bowItemsSuffix = Arrays.asList("_bow", "longbow");
  protected static final List<String> bowItemsKeywords = Arrays.asList();

  // == Crossbow Class
  protected static final List<String> crossbowItems = Arrays.asList(
  //@formatter:off
  //@formatter:on
  );
  protected static final List<String> crossbowItemsSuffix = Arrays.asList("_crossbow");
  protected static final List<String> crossbowItemsKeywords = Arrays.asList();

  // == Dagger Class
  protected static final List<String> daggerItems = Arrays.asList(
  //@formatter:off
  //@formatter:on
  );
  protected static final List<String> daggerItemsSuffix =
      Arrays.asList("_knife", "_dagger", ":diamondknife", ":netheritknife");
  protected static final List<String> daggerItemsKeywords = Arrays.asList(":dagger", ":knife");

  // == Great Sword Class
  protected static final List<String> greatSwordItems = Arrays.asList(
  //@formatter:off
    "rpg_style_more_weapins:newgenplzmasword",
    "rpg_style_more_weapins:plazmaswordhollow",
    "useless_sword:ancient_guardian_sword",
    "useless_sword:bugged_diamond_sword",
    "useless_sword:elder_guardian_sword",
    "useless_sword:guardian_sword",
    "useless_sword:nether_striker",
    "useless_sword:nether_striker",
    "useless_sword:soul_glaive",
    "useless_sword:sword_of_fate",
    "useless_sword:sword_of_immortality",
    "useless_sword:wither_sword"
  //@formatter:on
  );
  protected static final List<String> greatSwordItemsSuffix = Arrays.asList("_bastardsword",
      "_broadsword", "_claymore", "_flamebladedsword", "_zweihander", ":cleaver");
  protected static final List<String> greatSwordItemsKeywords = Arrays.asList();

  // == Gun Class
  protected static final List<String> gunItems = Arrays.asList(
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
  );
  protected static final List<String> gunItemsSuffix = Arrays.asList("_gun", "_rifle", "_pistol",
      "_gatling", "_shotgun", "_sniper", "_gunblade", "_musket", "_blunderbuss", "_arquebus",
      "_caliver", "_musketoon", ":desert_eagle", ":aug", ":ak74", ":magnum", ":uzi");
  protected static final List<String> gunItemsKeywords =
      Arrays.asList(":pistol", ":rifle", ":shotgun", ":gunblade");

  // == Hammer Class
  protected static final List<String> hammerItems = Arrays.asList(
  //@formatter:off
    "rpg_style_more_weapins:newgenhammer"
  //@formatter:on
  );
  protected static final List<String> hammerItemsSuffix = Arrays.asList("_hammer", "warhammer",
      "_lucernhammer", "_hammer_end", "_hammer_neth", ":sledgehammer", ":ironhammer");
  protected static final List<String> hammerItemsKeywords =
      Arrays.asList(":hammer", ":warhammer", ":mattock");

  // == Hoe Class
  protected static final List<String> hoeItems = Arrays.asList(
  //@formatter:off
  //@formatter:on
  );
  protected static final List<String> hoeItemsSuffix = Arrays.asList("_hoe", ":kama");
  protected static final List<String> hoeItemsKeywords = Arrays.asList(":hoe");

  // == Keyblade Class
  protected static final List<String> keybladeItems = Arrays.asList(
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
  );
  protected static final List<String> keybladeItemsSuffix = Arrays.asList("_keyblade");
  protected static final List<String> keybladeItemsKeywords = Arrays.asList(":keyblade_");

  // == Pickaxe Class
  protected static final List<String> pickaxeItems = Arrays.asList(
  //@formatter:off
  //@formatter:on
  );
  protected static final List<String> pickaxeItemsSuffix =
      Arrays.asList("_pickaxe", "_pickaxe_end", "_pickaxe_nether", "_pick");
  protected static final List<String> pickaxeItemsKeywords = Arrays.asList(":pickaxe");

  // == Shield Class
  protected static final List<String> shieldItems = Arrays.asList(
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
  );
  protected static final List<String> shieldItemsSuffix =
      Arrays.asList("_shield", "_buckler", "_ellipticalshield", "_heatershield", "_kiteshield",
          "_pavese", "_rondache", "_roundshield", "_tartsche", ":shield");
  protected static final List<String> shieldItemsKeywords = Arrays.asList();

  // == Spear Class
  protected static final List<String> spearItems = Arrays.asList(
  //@formatter:off
    "blue_skies:soulbound"
  //@formatter:on
  );
  protected static final List<String> spearItemsSuffix =
      Arrays.asList("_spear", "_ahlspiess", "_pike", "_ranseur", ":spear");
  protected static final List<String> spearItemsKeywords = Arrays.asList();

  // == Sword Class
  protected static final List<String> swordItems = Arrays.asList(
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
  );
  protected static final List<String> swordItemsSuffix =
      Arrays.asList("_sword", "_shortsword", ":sword");
  protected static final List<String> swordItemsKeywords = Arrays.asList();

  protected WeaponClassDataInternal() {}
}
