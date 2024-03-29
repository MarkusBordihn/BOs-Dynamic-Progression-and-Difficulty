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

package de.markusbordihn.dynamicprogressiondifficulty.entity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class LivingEntityEventHandler {

  private LivingEntityEventHandler() {}

  @SubscribeEvent(priority = EventPriority.LOW)
  public static void handleLivingHurtEvent(LivingHurtEvent event) {
    float damageAmount =
        LivingEntityEvents.handleLivingHurtEvent(
            event.getEntity(), event.getSource(), event.getAmount());
    if (damageAmount != event.getAmount()) {
      event.setAmount(damageAmount);
    }
  }

  @SubscribeEvent(priority = EventPriority.LOW)
  public static void handleLivingDeathEvent(LivingDeathEvent event) {
    LivingEntity livingEntity = event.getEntity();
    if (livingEntity != null && !livingEntity.level().isClientSide) {
      LivingEntityEvents.handleLivingDeathEvent(livingEntity, event.getSource());
    }
  }
}
