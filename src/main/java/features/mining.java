package features;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
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
    

    public void miningactive() {
        if (mithrilmining) {
                mithril();
        } else if (jademining) {
            if (location.divan)
                jade();
        } else if (powder) {

        }
    }

    public void mithril() {
        for (String gear : Tool)
    }

    public void jade() {

    }



}
