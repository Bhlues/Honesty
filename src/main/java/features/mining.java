package features;

import utils.player;
import utils.render;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class mining {
  /*  public static boolean pinglessMining;
    private static BlockPos block = null;
    private static final ArrayList<BlockPos> broken = new ArrayList<>();
    private final KeyBinding lc = Minecraft.getMinecraft().gameSettings.keyBindAttack;
    private static int ticks = 0;
    private int pinglessSpeed = 20;

    @SubscribeEvent
    public void onTick80(TickEvent.PlayerTickEvent event) {
        if (pinglessMining)
            return;
        if (pinglessSpeed == 0 || pinglessSpeed == 1)
            return;
        ticks++;
        if (ticks % 80 == 0) {
            broken.clear();
        }
        if (lc != null && lc.isKeyDown()) {
            if (block != null) {
                MovingObjectPosition movingObjectPosition = Minecraft.getMinecraft().objectMouseOver;
                if (movingObjectPosition != null
                        && movingObjectPosition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                    Block b = Minecraft.getMinecraft().getBlockState(movingObjectPosition.getBlockPos()).getBlock();
                    if (b == Blocks.stone || b == Blocks.emerald_ore || b == Blocks.lapis_ore
                            || b == Blocks.redstone_ore ||
                            b == Blocks.iron_ore || b == Blocks.gold_ore || b == Blocks.coal_ore
                            || b == Blocks.diamond_ore ||
                            b == Blocks.nether_wart || b == Blocks.reeds || b == Blocks.potatoes
                            || b == Blocks.carrots) {
                        broken.add(block);
                        Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(
                                C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, block, EnumFacing.DOWN));
                        player.swingItem();
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onTick40(TickEvent.ClientTickEvent event) {
        if (!pinglessMining)
            return;
        if (pinglessSpeed == 2)
            return;
        if (pinglessSpeed == 0 && event.phase == TickEvent.Phase.END)
            return;
        ticks++;
        if (ticks % 80 == 0) {
            broken.clear();
        }
        if (lc != null && lc.isKeyDown()) {
            if (block != null) {
                MovingObjectPosition movingObjectPosition = Minecraft.getMinecraft().objectMouseOver;
                if (movingObjectPosition != null
                        && movingObjectPosition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                    Block b = Minecraft.getMinecraft().getBlockState(movingObjectPosition.getBlockPos()).getBlock();
                    if (b == Blocks.stone || b == Blocks.emerald_ore || b == Blocks.lapis_ore
                            || b == Blocks.redstone_ore ||
                            b == Blocks.iron_ore || b == Blocks.gold_ore || b == Blocks.coal_ore
                            || b == Blocks.diamond_ore) {
                        broken.add(block);
                        Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(
                                C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, block, EnumFacing.DOWN));
                        player.swingItem();
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent event) {
        if (!pinglessMining || Minecraft.getMinecraft().thePlayer == null
                || Minecraft.getMinecraft().theWorld == null) {
            broken.clear();
            return;
        }
        block = closestBlock(event);
        if (block != null) {
            render.drawBlockBox(block, new Color(255, 0, 0), Main.configFile.lineWidth, event.partialTicks);
        }
    }

    private BlockPos closestBlock(RenderWorldLastEvent event) {
        int r = 5;
        BlockPos playerPos = Minecraft.getMinecraft().getPosition().add(0, 1, 0);
        Vec3 playerVec = Minecraft.getMinecraft().thePlayer.getPositionVector();
        Vec3i vec3i = new Vec3i(r, r, r);
        ArrayList<Vec3> blocks = new ArrayList<>();
        for (BlockPos blockPos : BlockPos.getAllInBox(playerPos.add(vec3i), playerPos.subtract(vec3i))) {
            IBlockState blockState = Minecraft.getMinecraft().theWorld.getBlockState(blockPos);
            if (isLookingAtBlock(blockPos, event) && !broken.contains(blockPos)
                    && blockState.getBlock() != Blocks.air) {
                blocks.add(new Vec3(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5));
            }
        }
        double smallest = 9999;
        Vec3 closest = null;
        for (Vec3 block : blocks) {
            double dist = block.distanceTo(playerVec);
            if (dist < smallest) {
                smallest = dist;
                closest = block;
            }
        }
        if (closest != null) {
            return new BlockPos(closest.xCoord, closest.yCoord, closest.zCoord);
        }
        return null;
    }

    private boolean isLookingAtBlock(BlockPos blockPos, RenderWorldLastEvent event) {
        AxisAlignedBB aabb = AxisAlignedBB.fromBounds(blockPos.getX(), blockPos.getY(), blockPos.getZ(),
                blockPos.getX() + 1, blockPos.getY() + 1, blockPos.getZ() + 1);
        Vec3 position = new Vec3(Minecraft.getMinecraft().thePlayer.posX,
                (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight()),
                Minecraft.getMinecraft().thePlayer.posZ);
        Vec3 look = Minecraft.getMinecraft().thePlayer.getLook(event.partialTicks);
        look = scaleVec(look, 0.2F);
        for (int i = 0; i < 40; i++) {
            if (aabb.minX <= position.xCoord && aabb.maxX >= position.xCoord && aabb.minY <= position.yCoord
                    && aabb.maxY >= position.yCoord && aabb.minZ <= position.zCoord && aabb.maxZ >= position.zCoord) {
                return true;
            }
            position = position.add(look);
        }

        return false;
    }

    private static Vec3 scaleVec(Vec3 vec, float f) {
        return new Vec3(vec.xCoord * (double) f, vec.yCoord * (double) f, vec.zCoord * (double) f);
    }
 */
}
