package de.markusbordihn.dynamicprogressiondifficulty.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class PlayerStatsMenu extends PlayerStatsMenuCommon {

  public PlayerStatsMenu(MenuType<?> menuType, int windowId) {
    super(menuType, windowId);
  }

  public PlayerStatsMenu(int windowId) {
    this(ModMenuTypes.PLAYER_STATS_MENU.get(), windowId);
  }

  public PlayerStatsMenu(int windowId, Inventory inventory, FriendlyByteBuf friendlyByteBuf) {
    this(windowId);
  }

  public static MenuProvider createMenuProvider() {
    return new MenuProvider() {

      @Override
      public Component getDisplayName() {
        return Component.literal("Player stats");
      }

      @Override
      public AbstractContainerMenu createMenu(
          int windowId, Inventory playerInventory, Player playerEntity) {
        return new PlayerStatsMenu(windowId);
      }
    };
  }
}
