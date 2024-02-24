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

package de.markusbordihn.dynamicprogressiondifficulty.network.message;

import de.markusbordihn.dynamicprogressiondifficulty.Constants;
import de.markusbordihn.dynamicprogressiondifficulty.data.PlayerStats;
import de.markusbordihn.dynamicprogressiondifficulty.data.PlayerStatsManager;
import io.netty.buffer.Unpooled;
import java.util.Date;
import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SyncPlayerStatsMessage implements CustomPacketPayload {

  public static final ResourceLocation MESSAGE_ID =
      new ResourceLocation(Constants.MOD_ID, "sync_player_stats_message");
  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);
  private final UUID uuid;
  private final Date lastUpdate;

  private final PlayerStats playerStats;

  public SyncPlayerStatsMessage(UUID uuid, PlayerStats playerStats) {
    this(uuid, playerStats, new Date());
  }

  public SyncPlayerStatsMessage(UUID uuid, PlayerStats playerStats, Date lastUpdate) {
    this.uuid = uuid;
    this.playerStats = playerStats;
    this.lastUpdate = lastUpdate;
  }

  public SyncPlayerStatsMessage(final FriendlyByteBuf friendlyByteBuf) {
    this(
        friendlyByteBuf.readUUID(),
        new PlayerStats(friendlyByteBuf.readNbt()),
        new Date(friendlyByteBuf.readLong()));
  }

  public static SyncPlayerStatsMessage decode(final FriendlyByteBuf buffer) {
    return new SyncPlayerStatsMessage(
        buffer.readUUID(), new PlayerStats(buffer.readNbt()), new Date(buffer.readLong()));
  }

  public static FriendlyByteBuf encode(
      final SyncPlayerStatsMessage message, final FriendlyByteBuf buffer) {
    buffer.writeUUID(message.uuid);
    buffer.writeNbt(message.playerStats.createTag());
    buffer.writeLong(message.lastUpdate.getTime());
    return buffer;
  }

  public static void handle(final FriendlyByteBuf buffer) {
    handle(decode(buffer));
  }

  public static void handle(final SyncPlayerStatsMessage message) {
    log.info("Received sync player stats message for {}.", message.getUUID());
    PlayerStats playerStats = message.getPlayerStats();
    PlayerStatsManager.updateLocalPlayerStats(playerStats);
  }

  public FriendlyByteBuf encode() {
    return encode(this, new FriendlyByteBuf(Unpooled.buffer()));
  }

  public UUID getUUID() {
    return this.uuid;
  }

  public PlayerStats getPlayerStats() {
    return this.playerStats;
  }

  @Override
  public void write(FriendlyByteBuf friendlyByteBuf) {
    friendlyByteBuf.writeUUID(this.uuid);
    friendlyByteBuf.writeNbt(this.playerStats.createTag());
    friendlyByteBuf.writeLong(this.lastUpdate.getTime());
  }

  @Override
  public ResourceLocation id() {
    return MESSAGE_ID;
  }
}
