package com.vydra.death.screen.modules.impl.combat.crystalaura.util;

import com.vydra.death.screen.utils.player.BlockUtil;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import static com.vydra.death.screen.utils.Render2d.mc;

public class CrystalUtil {



    public static boolean canPlaceCrystal(BlockPos pos) {
        Block block = BlockUtil.getBlock(pos);
        if (block == Blocks.OBSIDIAN || block == Blocks.BEDROCK) {
            Block floor = mc.world.getBlockState(pos.add(0, 1, 0)).getBlock();
            Block ceil = mc.world.getBlockState(pos.add(0, 2, 0)).getBlock();

            if (floor == Blocks.AIR && ceil == Blocks.AIR) {
                for (Entity entity : mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(pos.add(0, 1, 0)))) {
                    if (!entity.isDead && !(entity instanceof EntityEnderCrystal) ) {
                        return false;
                    }
                }
                return true;
            }

        }
        return false;
    }









    public static float calculateDamage(BlockPos pos, Entity entity) {
        float doubleExplosionSize = 12.0f;
        double distancedsize = entity.getDistance(pos.getX(), pos.getY(), pos.getZ()) / (double)doubleExplosionSize;
        Vec3d vec3d = new Vec3d(pos.getX(), pos.getY(), pos.getZ());
        double blockDensity = 0.0;
        try {
            blockDensity = entity.world.getBlockDensity(vec3d, entity.getEntityBoundingBox());
        }
        catch (Exception exception) {
            // empty catch block
        }
        double v = (1.0 - distancedsize) * blockDensity;
        float damage = (int)((v * v + v) / 2.0 * 7.0 * (double)doubleExplosionSize + 1.0);
        double finald = 1.0;
        if (entity instanceof EntityLivingBase) {
            finald = getBlastReduction((EntityLivingBase)entity, getDamageMultiplied(damage), new Explosion((World)mc.world, null, pos.getX(), pos.getY(), pos.getZ(), 6.0f, false, true));
        }
        return (float)finald;
    }


























    private static float getDamageMultiplied(float damage) {
        int diff = mc.world.getDifficulty().getId();
        return damage * (diff == 0 ? 0.0f : (diff == 2 ? 1.0f : (diff == 1 ? 0.5f : 1.5f)));
    }

    private static float getBlastReduction(EntityLivingBase entity, float damageI, Explosion explosion) {
        float damage = damageI;
        if (entity instanceof EntityPlayer) {
            EntityPlayer ep = (EntityPlayer)entity;
            DamageSource ds = DamageSource.causeExplosionDamage((Explosion)explosion);
            damage = CombatRules.getDamageAfterAbsorb((float)damage, (float)ep.getTotalArmorValue(), (float)((float)ep.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue()));
            int k = 0;
            try {
                k = EnchantmentHelper.getEnchantmentModifierDamage((Iterable)ep.getArmorInventoryList(), (DamageSource)ds);
            }
            catch (Exception exception) {
                // empty catch block
            }
            float f = MathHelper.clamp((float)k, (float)0.0f, (float)20.0f);
            damage *= 1.0f - f / 25.0f;
            if (entity.isPotionActive(MobEffects.RESISTANCE)) {
                damage -= damage / 4.0f;
            }
            damage = Math.max(damage, 0.0f);
            return damage;
        }
        damage = CombatRules.getDamageAfterAbsorb((float)damage, (float)entity.getTotalArmorValue(), (float)((float)entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue()));
        return damage;
    }


}
