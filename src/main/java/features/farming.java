package features;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class farming {
	public static boolean active = false;
	public static boolean activenoreturn = false;
	public static boolean farming = false;
	public static boolean leftClick = false;
	public static boolean rightClick = false;
	public static boolean wart = false;
	public static boolean cane = false;
	public static boolean crops = false;

	KeyBinding attack = Minecraft.getMinecraft().gameSettings.keyBindAttack;
	KeyBinding place = Minecraft.getMinecraft().gameSettings.keyBindUseItem;
	KeyBinding forward = Minecraft.getMinecraft().gameSettings.keyBindForward;
	KeyBinding back = Minecraft.getMinecraft().gameSettings.keyBindBack;
	KeyBinding left = Minecraft.getMinecraft().gameSettings.keyBindLeft;
	KeyBinding right = Minecraft.getMinecraft().gameSettings.keyBindRight;
	KeyBinding jump = Minecraft.getMinecraft().gameSettings.keyBindJump;

	public static int tick = 0;
	public static int updater = 20;

	int delay = 0, errored = 0;
	int x = 0;
	int y = -127;
	int z = 0;
	int random = 5;
	int layer = y;

	private static void KeyDown(KeyBinding key) {
		if (!key.isKeyDown())
			KeyBinding.setKeyBindState(key.getKeyCode(), true);
	}

	private static void KeyUp(KeyBinding key) {
		if (key.isKeyDown())
			KeyBinding.setKeyBindState(key.getKeyCode(), false);
	}

	public static int findItemInHotbar(String name) {
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

	public void OnTick(TickEvent.ClientTickEvent e) {
		if (e.phase != TickEvent.Phase.START)
			return;
		// if (player == null)
		//return;
		tick++;
		if (tick > updater) {
			if (farming) {
				if (wart)
					WartFarmer();
				if (cane)
					CaneFarmer();
				if (crops)
					CropFarmer();
			}
			tick = 1;
		}

	}

	public void WartFarmer() {
		int tool = findItemInHotbar("Newton");
		if (tool != -1)
			Minecraft.getMinecraft().thePlayer.inventory.currentItem = tool;
		// adjust angle toevoegen
		KeyDown(attack);
		delay++;
		if (delay < 5)
		return;
		int PosY = Minecraft.getMinecraft().thePlayer.getPosition().getY();
		if (y != PosY) {
			KeyUp (attack);
		}
	}

	public void CaneFarmer() {
		KeyDown(attack);
	}

	public void CropFarmer() {
		KeyDown(attack);
	}
	
	
	public void CropsMovement() {
		
	}

	public void onWorldChange(WorldEvent.Unload e) {
		if (farming)
			Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(
					EnumChatFormatting.AQUA + "Farming paused:" + EnumChatFormatting.DARK_RED + "Due to server close"));
		disable();

	}

	public void disable() {
		farming = false;
		leftClick = false;
		rightClick = false;
		wart = false;
		cane = false;
		crops = false;
		x = 0;
		y = -127;
		z = 0;
		layer = y;
		delay = 0;
		errored = 0;
		KeyUp(attack);
		KeyUp(place);
		KeyUp(forward);
		KeyUp(back);
		KeyUp(left);
		KeyUp(right);
		KeyUp(jump);
		// everything that needs to be turned off
	}

}
