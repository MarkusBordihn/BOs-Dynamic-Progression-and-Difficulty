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
import io.netty.buffer.Unpooled;
import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public class UpdatePlayerStatsMessage implements CustomPacketPayload {

  public static final ResourceLocation MESSAGE_ID =
      new ResourceLocation(Constants.MOD_ID, "update_player_stats_message");

  private final UUID uuid;

  public UpdatePlayerStatsMessage(UUID uuid) {
    this.uuid = uuid;
  }

  public UpdatePlayerStatsMessage(final FriendlyByteBuf friendlyByteBuf) {
    this(friendlyByteBuf.readUUID());
  }

  public static UpdatePlayerStatsMessage decode(final FriendlyByteBuf buffer) {
    return new UpdatePlayerStatsMessage(buffer.readUUID());
  }

  public static FriendlyByteBuf encode(
      final UpdatePlayerStatsMessage message, final FriendlyByteBuf buffer) {
    buffer.writeUUID(message.uuid);
    return buffer;
  }

  public FriendlyByteBuf encode() {
    return encode(this, new FriendlyByteBuf(Unpooled.buffer()));
  }

  @Override
  public void write(FriendlyByteBuf friendlyByteBuf) {
    friendlyByteBuf.writeUUID(this.uuid);
  }

  @Override
  public ResourceLocation id() {
    return MESSAGE_ID;
  }
}
