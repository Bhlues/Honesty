package features;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;


public class perspective {

	public static boolean togglef5 = false;
   

    // InputEvent has to be the absolute worst event in forge
    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
        if (!togglef5) return;
        if (!Keyboard.getEventKeyState()) return;
        if (Keyboard.getEventKey() == Minecraft.getMinecraft().gameSettings.keyBindTogglePerspective.getKeyCode()) {
            if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 2) {
                ItemStack item = Minecraft.getMinecraft().thePlayer.getHeldItem();
                if (item == null) return;
                if (item.hasTagCompound()) {
                    if (item.getTagCompound().hasKey("ExtraAttributes")) {
                        if (item.getTagCompound().getCompoundTag("ExtraAttributes").hasKey("ability_scroll")) {
                            if (item.getTagCompound().getCompoundTag("ExtraAttributes").getTagList("ability_scroll", 8).tagCount() == 3) {
                                Minecraft.getMinecraft().gameSettings.thirdPersonView = 0;
                            }
                        }
                    }
                }
            }
        }
    }
}