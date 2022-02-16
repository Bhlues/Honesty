package features;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;


public class perspective {

	public boolean persepective = false;
    private final Config config = Honesty.getInstance().getConfig();

    // InputEvent has to be the absolute worst event in forge
    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
        if (!config.perspective) return;
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