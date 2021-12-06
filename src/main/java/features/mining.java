package features;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.event.world.WorldEvent;

public class mining {
    public static boolean mithril = false;
    public static boolean gems = false;
    public static boolean mines = false;
    public static boolean hollows = false;
    public static boolean mithrilmining = false;
    public static boolean jademining = false;
    public static boolean powder = false;

    ArrayList<String> tool = new ArrayList<>(Arrays.asList("gauntlet", "x655"));

    private static boolean returning = false;

    private static int findItemInHotbar(String name) {
        InventoryPlayer inv = Minecraft.getMinecraft().thePlayer.inventory;
        for (int i = 0; i < 9; i++) {
            ItemStack curStack = inv.getStackInSlot(i);
            if (curStack != null) {
                if (curStack.getDisplayName().contains(name)) {
                    return i;
                }
            }
        }
        return -1;
    }

    /*
     * public void hub() {
     * if (returning) {
     * if (location.inSkyblock && !location.dwarven) {
     * Thread.sleep(new Random().nextInt(15000) + 10000);
     * Minecraft.getMinecraft().thePlayer.sendChatMessage("/warpforge");
     * Thread.sleep(new Random().nextInt(15000) + 10000);
     * } else if (location.dwarven) {
     * // TODO ETHERWARPS AND TP's TO LOCATION
     * // TODO mithril = true;
     * }
     * }
     * }
     */

    public void refuel() {
        
    }


    public void mithril() {
        for (String gear : tool) {
            if (!returning)
                if (Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem().getDisplayName().contains(gear)) {
                    int SlotTool = findItemInHotbar(gear);
                    if (SlotTool < 0)
                        return;
                    Minecraft.getMinecraft().thePlayer.inventory.currentItem = SlotTool;
                    mithrildwarven();
                }
        }
    }

    public void mithrildwarven() {        
        MovingObjectPosition objectMouseOver = Minecraft.getMinecraft().objectMouseOver;
        if (objectMouseOver != null && objectMouseOver.typeOfHit.toString() == "wool") {
            BlockPos pos = objectMouseOver.getBlockPos();
            Block block = Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock();
            if  (block == Blocks.wool) {
                //TODO Actie
            }
        }
    }
    

    public void jade() {

    }

    public void powder() {

    }

    public void onWorldChange(WorldEvent.Unload e) {
        if (mithrilmining) {
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(
                    EnumChatFormatting.RED + "Mithril mining paused:" + EnumChatFormatting.DARK_RED
                            + "Due to server close"));
            returning = false;
            mithril = false;
        } else if (jademining) {
            jademining = false;
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(
                    new ChatComponentText(EnumChatFormatting.RED + "Jade mining stopped due to server close"));
        } else if (powder) {
            powder = false;
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(
                    new ChatComponentText(EnumChatFormatting.RED + "Powder mining stopped due to server closing"));
        }
    }
}
