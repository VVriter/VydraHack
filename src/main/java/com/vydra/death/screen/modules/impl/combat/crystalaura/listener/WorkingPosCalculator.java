package com.vydra.death.screen.modules.impl.combat.crystalaura.listener;

import com.vydra.death.screen.events.UpdateWalkingPlayerEvent;
import com.vydra.death.screen.modules.impl.combat.crystalaura.AutoCystalConstants;
import com.vydra.death.screen.modules.impl.combat.crystalaura.util.CrystalUtil;
import com.vydra.death.screen.modules.settings.types.BooleanSetting;
import com.vydra.death.screen.modules.settings.types.SliderSetting;
import com.vydra.death.screen.utils.Render3d;
import com.vydra.death.screen.utils.player.BlockUtil;
import com.vydra.death.screen.utils.player.RotationUtil;
import jdk.nashorn.internal.ir.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

import static com.vydra.death.screen.modules.impl.combat.crystalaura.util.CrystalUtil.canPlaceCrystal;
import static com.vydra.death.screen.utils.Render2d.mc;

public class WorkingPosCalculator {

    private SliderSetting range = new SliderSetting.Builder()
            .withModule(AutoCystalConstants.instance)
            .min(1)
            .max(6)
            .withDefaultValue(5)
            .withName("Place range")
            .build();

    private SliderSetting minTargetDmg = new SliderSetting.Builder()
            .withModule(AutoCystalConstants.instance)
            .min(1)
            .max(20)
            .withDefaultValue(3)
            .withName("MinTargetDmg")
            .build();

    private SliderSetting maxSelfDmg = new SliderSetting.Builder()
            .withModule(AutoCystalConstants.instance)
            .min(1)
            .max(20)
            .withDefaultValue(3)
            .withName("MaxSelfDmg")
            .build();

    private BooleanSetting raytrace = new BooleanSetting.Builder()
            .withModule(AutoCystalConstants.instance)
            .withName("Raytrace")
            .withDefaultValue(true)
            .build();

    @SubscribeEvent
    public void onUpdate(UpdateWalkingPlayerEvent event) {
        if (AutoCystalConstants.target == null) return;
        List<BlockPos> posList = BlockUtil.getSphere((float) range.getValue(), true, mc.player);
        List<BlockPos> posesFiltered =  posList.stream()
                .filter(CrystalUtil::canPlaceCrystal)
                .filter(e-> {
                    if (raytrace.isValue()) {
                        if (BlockUtil.canPlayerSeeBlock(e))
                            return true;
                    } else return true;

                    return false;
                }).collect(Collectors.toList());
        //AutoCystalConstants.workingPos =
    }

    @SubscribeEvent
    public void render(RenderWorldLastEvent event) {
        if (AutoCystalConstants.target == null) return;
        if (AutoCystalConstants.workingPos == null) return;
        Render3d.drawProperBoxESP(
                AutoCystalConstants.workingPos,
                new Color(0x62F6000A, true),
                3,
                true,
                true,
                100,
                1
        );
    }





}
