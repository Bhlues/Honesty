package features;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import org.lwjgl.input.Keyboard;

import apec_beta.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class soulwhip {
	ArrayList<String> StartItems = new ArrayList<>(Arrays.asList("Emerald", "Whip", "Flux", "flux"));

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

	private static final KeyBinding SWAP_KEY = new KeyBinding("Soulwhip", Keyboard.KEY_J, Main.MODID);
	static {
		ClientRegistry.registerKeyBinding(SWAP_KEY);
	}

	@SubscribeEvent
	public void onKeyEvent(KeyInputEvent e) {
		if (SWAP_KEY.isPressed()) {
			boolean StartCheck = false;
			for (String Starting : StartItems) {
				if (Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem().getDisplayName().contains(Starting))
					StartCheck = true;
			}
			if (!StartCheck) {
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA
						+ "Toggled SoulWhip: " + EnumChatFormatting.RED + "Not holding starting item"));
				return;
			}
			active = !active;
			Minecraft.getMinecraft().thePlayer
					.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "Toggled SoulWhip: " + active));
		}
	}

	int tick = 0;

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent e) {
		if (e.phase != TickEvent.Phase.START || !active)
			return;
		tick++;
		if (tick > 20)
			tick = 1;
		if (tick == 3) {
			int SlotWhip =  findItemInHotbar("Whip");
				if (SlotWhip <0) return;
			Minecraft.getMinecraft().thePlayer.inventory.currentItem = SlotWhip;
		}
		if (tick == 5) {
			rightClick();
		}
		if (tick == 8) {
			int SlotEm = findItemInHotbar("Emerald");
				if (SlotEm <0) return;
			Minecraft.getMinecraft().thePlayer.inventory.currentItem = findItemInHotbar("Emerald");
		}

	}

}