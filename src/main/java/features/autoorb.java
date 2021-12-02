package features;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.text.JTextComponent.KeyBinding;

import org.lwjgl.input.Keyboard;

import honesty.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraft.client.settings.KeyBinding;

public class autoorb {
	ArrayList<String> ShortOrbs = new ArrayList<>(Arrays.asList("Radiant", "Mana Flux", "IQ Flux"));
	ArrayList<String> LongOrbs = new ArrayList<>(Arrays.asList("Overflux", "Plasmaflux"));

	public static void rightClick() {
		try {
			Method rightClickMouse = null;
			try {
				rightClickMouse = Minecraft.class.getDeclaredMethod("rightClickMouse");
			} catch (NoSuchMethodException e) {
				rightClickMouse = Minecraft.class.getDeclaredMethod("func_147121_ag");
			}
			rightClickMouse.setAccessible(true);
			rightClickMouse.invoke(Minecraft.getMinecraft());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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

	boolean active = false;

	private static final KeyBinding SWAP_KEY = new KeyBinding("AutoOrb", Keyboard.KEY_I, Main.MODID);
	static {
		ClientRegistry.registerKeyBinding(SWAP_KEY);
	}

	@SubscribeEvent
	public void onKeyEvent(KeyInputEvent e) {
		if (SWAP_KEY.isPressed()) {
			active = !active;
			Minecraft.getMinecraft().thePlayer
					.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "Toggled Auto Orb: " + active));
		}
	}

	
	int tick = 0;

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent e) {
		if (e.phase != TickEvent.Phase.START || !active)
			return;
		tick++;
		if (tick % 5 == 0) {
		}
		if (tick > 1200)
			tick = 1;
		if (tick == 3) {
			Minecraft.getMinecraft().thePlayer.inventory.currentItem = findItemInHotbar ("");
		}
		if (tick == 5 ) {
			rightClick();
		}
		if (tick == 8) {
			Minecraft.getMinecraft().thePlayer.inventory.currentItem = findItemInHotbar ("");
		}
		
	}

}