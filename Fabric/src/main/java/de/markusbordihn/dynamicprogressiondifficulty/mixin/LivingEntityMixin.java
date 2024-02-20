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

package de.markusbordihn.dynamicprogressiondifficulty.mixin;

import de.markusbordihn.dynamicprogressiondifficulty.entity.LivingEntityEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

  @Unique private float damageAmount = 0.0f;

  @Inject(method = "hurt", at = @At("HEAD"))
  public void hurt(
      DamageSource damageSource, float damageAmount, CallbackInfoReturnable<Boolean> callback) {
    LivingEntity livingEntity = (LivingEntity) (Object) this;
    if (!(livingEntity.isSpectator()
        || livingEntity.isInvulnerableTo(damageSource)
        || livingEntity.level.isClientSide
        || livingEntity.isDeadOrDying()
        || (damageSource.isFire() && livingEntity.hasEffect(MobEffects.FIRE_RESISTANCE)))) {
      float adjustedDamageAmount =
          LivingEntityEvents.handleLivingHurtEvent(livingEntity, damageSource, damageAmount);
      this.damageAmount =
          adjustedDamageAmount != damageAmount ? adjustedDamageAmount : damageAmount;
    }
  }

  @ModifyVariable(
      method = "actuallyHurt",
      at = @At(value = "STORE", ordinal = 0),
      ordinal = 0,
      argsOnly = true)
  private float modifiedDamageAmount(float damageAmount) {
    return this.damageAmount;
  }

  @Inject(method = "die", at = @At("HEAD"))
  public void die(DamageSource damageSource, CallbackInfo callback) {
    LivingEntity livingEntity = (LivingEntity) (Object) this;
    if (!livingEntity.isRemoved()
        && !livingEntity.level.isClientSide
        && livingEntity.getPose() != Pose.DYING) {
      LivingEntityEvents.handleLivingDeathEvent(livingEntity, damageSource);
    }
  }
}
