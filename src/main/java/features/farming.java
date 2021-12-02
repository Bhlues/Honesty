package features;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Vec3;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import utils.location;
import utils.rotation;

public class farming {
	public static boolean active = false; // crops
	public static boolean swap = false; // cane
	public static boolean farming = false; // any running
	public static boolean cropfarming = false;
	public static boolean canefarming = false;
	public static boolean wartfarming = false;
	public static boolean leftClick = false;
	public static boolean rightClick = false;
	public static boolean wart = false;
	public static boolean cane = false;
	public static boolean crops = false;
	public static boolean stuck = false;
	public static boolean pause = false;
	public static boolean returning = false;

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
	int random = 5;
	int OldY = 0;
	int OldX = 0;
	int OldZ = 0;

	ArrayList<String> CropTools = new ArrayList<>(Arrays.asList("Euclid", "Pythagorean", "Gauss"));

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

	public void hub() {
		if (returning = true) {
			if (location.inSkyblock && !location.onIsland) {
				tick++;
				if (tick == 500)
					tick = 1;
				if (tick == 150) {
					Minecraft.getMinecraft().thePlayer.sendChatMessage("/warp home");
				}
				if (tick == 400) {
					returning = false;
				}

			}
		}
	}

	public void running() {
		if (wart || cane || crops)
			;
		farming = true;
	}

	public void OnTick(TickEvent.ClientTickEvent e) {
		if (e.phase != TickEvent.Phase.START)
			return;
		// if (player == null)
		// return;
		tick++;
		if (tick > updater) {
			if (farming) {
				if (location.onIsland) {
					if (wart)
						WartFarmer();
					if (cane)
						CaneFarmer();
					if (crops)
						CropFarmer();
				} else {
					returning = true;
					pause();
				}
			}
			tick = 1;

		}

	}

	public void WartFarmer() {
		int tool = findItemInHotbar("Newton");
		if (tool != -1)
			Minecraft.getMinecraft().thePlayer.inventory.currentItem = tool;
		Vec3 current = Minecraft.getMinecraft().thePlayer.getPositionVector();
		rotation.facePos(new Vec3(current.xCoord, current.yCoord + 1.875, current.zCoord + 1));
		KeyDown(attack);
		delay++;
		if (delay < 5)
			return;
		int PosX = Minecraft.getMinecraft().thePlayer.getPosition().getX();
		if (PosX == 88 || PosX == 83) {
			KeyUp(attack);
			tick++;
			if (tick == 20)
				tick = 1;
			if (tick == 2)
				OldY = Minecraft.getMinecraft().thePlayer.getPosition().getY();
			OldX = Minecraft.getMinecraft().thePlayer.getPosition().getX();
			OldZ = Minecraft.getMinecraft().thePlayer.getPosition().getZ();
			if (tick == 15 && stuck) {
				if (OldY != Minecraft.getMinecraft().thePlayer.getPosition().getY()) {
					active = !active;
				}
				if (tick == 19) {
					if (OldX == Minecraft.getMinecraft().thePlayer.getPosition().getX()
							&& OldZ == Minecraft.getMinecraft().thePlayer.getPosition().getZ())
						;
					stuck = true;
					pause();
				}
			}
		}
	}

	public void CropFarmer() {
		for (String tool : CropTools) {
			int Slot = findItemInHotbar(tool);
			if (Slot != -1) {

			}
		}
	}

	public void CaneFarmer() {
		KeyDown(attack);
	}

	public void CropsMovement() {
		if (cropfarming) {
			if (stuck) {
				random = new Random().nextInt(100) + 100;
				new Thread(() -> {
					try {
						KeyBinding space = Minecraft.getMinecraft().gameSettings.keyBindJump;
						KeyBinding left = Minecraft.getMinecraft().gameSettings.keyBindLeft;
						KeyBinding right = Minecraft.getMinecraft().gameSettings.keyBindRight;
						Thread.sleep(new Random().nextInt(75) + 50);
						KeyDown(space);
						Thread.sleep(new Random().nextInt(75) + 50);
						KeyDown(left);
						KeyUp(space);
						Thread.sleep(new Random().nextInt(500) + 250);
						KeyUp(left);
						KeyDown(right);
						Thread.sleep(new Random().nextInt(500) + 250);
						KeyUp(right);
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
				});
			} else if (active) {
				KeyDown(right);
				KeyUp(left);
			} else {
				KeyUp(right);
				KeyDown(left);
			}
		}
	}

	public void CaneMovement() {
		if (canefarming) {
			if (swap) {
				KeyUp(back);
				KeyDown(right);
			} else {
				KeyUp(right);
				KeyDown(left);
			}
		}
	}

	public void pause() {
		new Thread(() -> {
			try {
				KeyBinding attack = Minecraft.getMinecraft().gameSettings.keyBindAttack;
				KeyBinding place = Minecraft.getMinecraft().gameSettings.keyBindUseItem;
				KeyBinding forward = Minecraft.getMinecraft().gameSettings.keyBindForward;
				KeyBinding back = Minecraft.getMinecraft().gameSettings.keyBindBack;
				KeyBinding left = Minecraft.getMinecraft().gameSettings.keyBindLeft;
				KeyBinding right = Minecraft.getMinecraft().gameSettings.keyBindRight;
				KeyBinding jump = Minecraft.getMinecraft().gameSettings.keyBindJump;
				KeyUp(attack);
				KeyUp(place);
				KeyUp(forward);
				KeyUp(back);
				KeyUp(left);
				KeyUp(right);
				KeyUp(jump);
				Thread.sleep(new Random().nextInt(1200) + 1200);
				Thread.sleep(new Random().nextInt(3000) + 3000);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}).start();
	}

	public void onWorldChange(WorldEvent.Unload e) {
		if (farming)
			Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(
					EnumChatFormatting.AQUA + "Farming paused:" + EnumChatFormatting.DARK_RED + "Due to server close"));
		returning = true;
	}

	public void disable() {
		farming = false;
		leftClick = false;
		rightClick = false;
		wart = false;
		cane = false;
		crops = false;
		OldY = 0;
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
