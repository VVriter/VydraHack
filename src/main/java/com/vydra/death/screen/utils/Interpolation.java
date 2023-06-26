package com.vydra.death.screen.utils;

import com.vydra.death.screen.mixins.IMinecraft;
import com.vydra.death.screen.mixins.IRenderManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.awt.*;

/**
 * https://github.com/EpicGames/UnrealEngine/blob/f8f4b403eb682ffc055613c7caf9d2ba5df7f319/Engine/Source/Runtime/Core/Public/Math/UnrealMathUtility.h
 * https://github.com/EpicGames/UnrealEngine/blob/283e412aa843210f2d6e9ed0236861cf749b3429/Engine/Source/Runtime/Core/Private/Math/UnrealMath.cpp
 */
public class Interpolation {
    static Minecraft mc = Minecraft.getMinecraft();
    public static final float SMALL_NUMBER = 1.e-8f;
    public static final float KINDA_SMALL_NUMBER = 1.e-4f;
    public static final float BIG_NUMBER = 3.4e+38f;

    public static boolean isNearlyZero(double value) {
        return isNearlyZero(value, SMALL_NUMBER);
    }

    public static boolean isNearlyZero(double value, double tolerance) {
        return Math.abs(value) <= tolerance;
    }

    public static boolean isNearlyZero(float value) {
        return isNearlyZero(value, SMALL_NUMBER);
    }

    public static boolean isNearlyZero(float value, float tolerance) {
        return Math.abs(value) <= tolerance;
    }

    public static boolean isNearlyEqual(double a, double b) {
        return isNearlyEqual(a, b, KINDA_SMALL_NUMBER);
    }

    public static boolean isNearlyEqual(double a, double b, double tolerance) {
        return Math.abs(a - b) <= tolerance;
    }

    public static boolean isNearlyEqual(float a, float b) {
        return isNearlyEqual(a, b, KINDA_SMALL_NUMBER);
    }

    public static boolean isNearlyEqual(float a, float b, float tolerance) {
        return Math.abs(a - b) <= tolerance;
    }

    /**
     * Applies a linear interpolation between the two values
     */
    public static float lerp(float a, float b, float partial) {
        return (a * (1f - partial)) + (b * partial);
    }

    /**
     * Applies a linear interpolation between the two values
     */
    public static double lerp(double a, double b, float partial) {
        return (a * (1.0 - partial)) + (b * partial);
    }

    /**
     * Interpolate float from Current to Target. Scaled by distance to Target, so it has a strong start speed and ease out.
     */
    public static float finterpTo(float current, float target, float deltaTime, float interpSpeed) {
        if (interpSpeed <= 0f) {
            return target;
        }

        float distance = target - current;

        if (isNearlyZero(Math.pow(distance, 2), KINDA_SMALL_NUMBER)) {
            return target;
        }

        float deltaMove = distance * clamp(deltaTime * interpSpeed, 0f, 1f);
        return current + deltaMove;
    }

    /**
     * Interpolate float from Current to Target with constant step
     */
    public static float finterpConstantTo(float current, float target, float deltaTime, float interpSpeed) {
        float distance = target - current;

        if (isNearlyZero(Math.pow(distance, 2), KINDA_SMALL_NUMBER)) {
            return target;
        }

        float step = interpSpeed * deltaTime;
        return current + clamp(distance, -step, step);
    }

    /**
     * Interpolate Linear Color from Current to Target. Scaled by distance to Target, so it has a strong start speed and ease out.
     */
    public static int cinterpTo(int current, int target, float deltaTime, float interpSpeed) {
        return v4ToC(cinterpTo(cToV4(current), cToV4(target), deltaTime, interpSpeed)).getRGB();
    }

    public static Color cinterpTo(Color current, Color target, float deltaTime, float interpSpeed) {
        int[] out = cinterpTo(cToV4(current), cToV4(target), deltaTime, interpSpeed);
        return v4ToC(out);
    }

    public static int[] cinterpTo(int[] current, int[] target, float deltaTime, float interpSpeed) {
        if (interpSpeed <= 0f) {
            return target;
        }

        float distance = vDistance(current, target);

        if (distance < KINDA_SMALL_NUMBER) {
            return target;
        }

        int[] deltaMove = vMul(vSub(target, current), clamp(deltaTime * interpSpeed, 0f, 1f));
        return vAdd(current, deltaMove);
    }

    public static float vDistance(int[] v1, int[] v2) {
        double current = 0;
        for (int i = 0; i < v1.length; i++) {
            current += Math.pow(v2[i] - v1[i], 2);
        }
        return (float) Math.sqrt(current);
    }

    public static int[] vMul(int[] v1, int[] v2) {
        int[] out = new int[v1.length];
        for (int i = 0; i < v1.length; i++) {
            out[i] = v1[i] * v2[i];
        }
        return out;
    }

    public static int[] vMul(int[] v1, float c2) {
        int[] out = new int[v1.length];
        for (int i = 0; i < v1.length; i++) {
            out[i] = (int) (v1[i] * c2);
        }
        return out;
    }

    public static int[] vSub(int[] v1, int[] v2) {
        int[] out = new int[v1.length];
        for (int i = 0; i < v1.length; i++) {
            out[i] = v1[i] - v2[i];
        }
        return out;
    }

    public static int[] vAdd(int[] v1, int[] v2) {
        int[] out = new int[v1.length];
        for (int i = 0; i < v1.length; i++) {
            out[i] = v1[i] + v2[i];
        }
        return out;
    }

    public static float clamp(float num, float min, float max) {
        return num < min ? min : num > max ? max : num;
    }

    public static int[] cToV4(Color color) {
        return new int[]{color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()};
    }

    public static int[] cToV4(int rgb) {
        return new int[]{(rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, (rgb >> 0) & 0xFF, (rgb >> 24) & 0xff};
    }

    public static Color v4ToC(int[] v4) {
        return new Color(v4[0], v4[1], v4[2], v4[3]);
    }



    public static Vec3d interpolatedEyePos()
    {
        return mc.player.getPositionEyes(mc.getRenderPartialTicks());
    }

    public static Vec3d interpolatedEyeVec()
    {
        return mc.player.getLook(mc.getRenderPartialTicks());
    }

    public static Vec3d interpolatedEyeVec(EntityPlayer player) {
        return player.getLook(mc.getRenderPartialTicks());
    }

    public static Vec3d interpolateEntity(Entity entity)
    {
        double x;
        double y;
        double z;

        x = interpolateLastTickPos(entity.posX, entity.lastTickPosX)
                - getRenderPosX();
        y = interpolateLastTickPos(entity.posY, entity.lastTickPosY)
                - getRenderPosY();
        z = interpolateLastTickPos(entity.posZ, entity.lastTickPosZ)
                - getRenderPosZ();

        return new Vec3d(x, y, z);
    }

    public static Vec3d interpolateVectors(Vec3d current, Vec3d last) {
        double x = interpolateLastTickPos(current.x, last.x);
        double y = interpolateLastTickPos(current.y, last.y);
        double z = interpolateLastTickPos(current.z, last.z);
        return new Vec3d(x, y, z);
    }

    public static double interpolateLastTickPos(double pos, double lastPos)
    {
        return lastPos + (pos - lastPos) * ((IMinecraft) mc).getTimer().renderPartialTicks;
    }

    // TODO: instead of making a new AxisAlignedBB
    //  all the time maybe make a mutable AxisAlignedBB?
    public static AxisAlignedBB interpolatePos(BlockPos pos, float height)
    {
        return new AxisAlignedBB(
                pos.getX() - mc.getRenderManager().viewerPosX,
                pos.getY() - mc.getRenderManager().viewerPosY,
                pos.getZ() - mc.getRenderManager().viewerPosZ,
                pos.getX() - mc.getRenderManager().viewerPosX + 1,
                pos.getY() - mc.getRenderManager().viewerPosY + height,
                pos.getZ() - mc.getRenderManager().viewerPosZ + 1);
    }

    public static AxisAlignedBB interpolateAxis(AxisAlignedBB bb)
    {
        return new AxisAlignedBB(
                bb.minX - mc.getRenderManager().viewerPosX,
                bb.minY - mc.getRenderManager().viewerPosY,
                bb.minZ - mc.getRenderManager().viewerPosZ,
                bb.maxX - mc.getRenderManager().viewerPosX,
                bb.maxY - mc.getRenderManager().viewerPosY,
                bb.maxZ - mc.getRenderManager().viewerPosZ);
    }

    public static AxisAlignedBB offsetRenderPos(AxisAlignedBB bb)
    {
        return bb.offset(-getRenderPosX(), -getRenderPosY(), -getRenderPosZ());
    }

    public static double getRenderPosX()
    {
        return ((IRenderManager) mc.getRenderManager()).getRenderPosX();
    }

    public static double getRenderPosY()
    {
        return ((IRenderManager) mc.getRenderManager()).getRenderPosY();
    }

    public static double getRenderPosZ()
    {
        return ((IRenderManager) mc.getRenderManager()).getRenderPosZ();
    }

    public static Frustum createFrustum(Entity entity)
    {
        Frustum frustum = new Frustum();
        // NoInterp shouldn't really be required here
        double x = interpolateLastTickPos(entity.posX, entity.lastTickPosX);
        double y = interpolateLastTickPos(entity.posY, entity.lastTickPosY);
        double z = interpolateLastTickPos(entity.posZ, entity.lastTickPosZ);

        frustum.setPosition(x, y, z);

        return frustum;
    }
}