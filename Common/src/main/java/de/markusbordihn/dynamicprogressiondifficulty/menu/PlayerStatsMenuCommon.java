package de.markusbordihn.dynamicprogressiondifficulty.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class PlayerStatsMenuCommon extends AbstractContainerMenu {

  protected PlayerStatsMenuCommon(int windowId, Inventory playerInventory, FriendlyByteBuf data) {
    this(windowId, playerInventory);
  }

  public PlayerStatsMenuCommon(final int windowId, final Inventory playerInventory) {
    this(null, windowId);
  }

  public PlayerStatsMenuCommon(MenuType<?> menuType, final int windowId) {
    super(menuType, windowId);
  }

  @Override
  public boolean stillValid(Player player) {
    return player != null && player.isAlive();
  }

  @Override
  public ItemStack quickMoveStack(Player player, int slotIndex) {
    Slot slot = this.slots.get(slotIndex);
    if (!slot.hasItem()) {
      return ItemStack.EMPTY;
    }

    ItemStack itemStack = slot.getItem();

    // Store changes if itemStack is not empty.
    if (itemStack.isEmpty()) {
      slot.set(ItemStack.EMPTY);
    } else {
      slot.setChanged();
    }

    return ItemStack.EMPTY;
  }
}
