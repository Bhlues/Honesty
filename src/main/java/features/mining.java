package features;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.world.WorldEvent;
import utils.location;

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

    public void miningactive() {
        if (mithrilmining) {
            mithril();
        } else if (jademining) {
            jade();
        } else if (powder) {
            powder();
        }
    }

    public void mithril() {
        for (String gear : tool) {
            if (!returning)
                if (Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem().getDisplayName().contains(gear)) {
                    int SlotTool = findItemInHotbar(gear);
                    if (SlotTool < 0)
                        return;
                    Minecraft.getMinecraft().thePlayer.inventory.currentItem = SlotTool;
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
