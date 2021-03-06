package features;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import honesty.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Vec3;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import utils.location;
import utils.rotation;


public class farming {
	public static boolean active = false; // crops swap walking direction
	public static boolean swap = false; // cane swap walking direction
	public static boolean farming = false; // any running
	public static boolean facingwart = false;
	public static boolean facingcrops = false;
	public static boolean facingcane = false;
	public static boolean leftClick = false;
	public static boolean rightClick = false;
	public static boolean wart = false;
	public static boolean cane = false;
	public static boolean crops = false;
	public static boolean CropsFarmingCheck = false;
	public static boolean CaneFarmingCheck = false;
	public static boolean stuck = false;
	public static boolean pause = false;
	public static boolean returning = false;
	public static boolean TeleporterCrops = false;
	public static boolean TeleporterCane = false;

	KeyBinding attack = Minecraft.getMinecraft().gameSettings.keyBindAttack;
	KeyBinding place = Minecraft.getMinecraft().gameSettings.keyBindUseItem;
	KeyBinding forward = Minecraft.getMinecraft().gameSettings.keyBindForward;
	KeyBinding back = Minecraft.getMinecraft().gameSettings.keyBindBack;
	KeyBinding left = Minecraft.getMinecraft().gameSettings.keyBindLeft;
	KeyBinding right = Minecraft.getMinecraft().gameSettings.keyBindRight;
	KeyBinding jump = Minecraft.getMinecraft().gameSettings.keyBindJump;

	private static final KeyBinding SWAP_KEY = new KeyBinding("Turn off all macro's", Keyboard.KEY_PERIOD,
			Main.MODID);
	static {
		ClientRegistry.registerKeyBinding(SWAP_KEY);
	}

	public static int tick = 20;
	public static int updater = 20;
	public static int cropdirection = 0;

	int delay = 0, errored = 0;
	int random = 5;
	int OldY = 0;
	int OldX = 0;
	int OldZ = 0;
	int FacingX = 0;
	int FacingZ = 0;

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

	@SubscribeEvent
	public void onKeyEvent(KeyInputEvent e) {
		if (SWAP_KEY.isPressed()) {
			active = false; // crops swap walking direction
			swap = false; // cane swap walking direction
			farming = false; // any running
			facingwart = false;
			facingcrops = false;
			facingcane = false;
			leftClick = false;
			rightClick = false;
			wart = false;
			cane = false;
			crops = false;
			CropsFarmingCheck = false;
			CaneFarmingCheck = false;
			stuck = false;
			pause = false;
			returning = false;
			TeleporterCrops = false;
			TeleporterCane = false;
			OldY = 0;
			delay = 0;
			errored = 0;
			FacingX = 0;
			FacingZ = 0;
			KeyUp(attack);
			KeyUp(place);
			KeyUp(forward);
			KeyUp(back);
			KeyUp(left);
			KeyUp(right);
			KeyUp(jump);
			Minecraft.getMinecraft().thePlayer
					.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Turned off all farming macro's"));
		}
	}

	public void DirectionCoordsZ() { // if facing ZCoords
		if (cropdirection == 0) {
			// TODO FacingX = ???
		} else if (cropdirection == 2) {
			// TODO FacringX = ???
		} else {
			FacingX = 0;
		}
	}

	public void DirectionCoordsX() { // if facing XCoords
		if (cropdirection == 0) {
			// TODO FacingZ = ???
		} else if (cropdirection == 2) {
			// TODO FacringZ = ???
		} else {
			FacingZ = 0;
		}
	}

	public void hub() {
		if (returning) {
			if (location.inSkyblock && !location.onIsland) {
				if (wart) {
					new Thread(() -> {
						try {
							wart = false;
							KeyUp(attack);
							KeyUp(place);
							KeyUp(forward);
							KeyUp(back);
							KeyUp(left);
							KeyUp(right);
							KeyUp(jump);
							Thread.sleep(new Random().nextInt(15000) + 10000);
							Minecraft.getMinecraft().thePlayer.sendChatMessage("/warp home");
							Thread.sleep(new Random().nextInt(15000) + 10000);
							wart = true;
							running();
							BlockPos wart = Minecraft.getMinecraft().thePlayer.getPosition().add(0, 0.6, 4);
							rotation.facePos(new Vec3(Minecraft.getMinecraft().thePlayer.getPositionVector().xCoord,
									wart.getY(), wart.getZ()));
						} catch (InterruptedException ex) {
							ex.printStackTrace();
						}
					}).start();
				} else if (cane) {
					new Thread(() -> {
						try {
							cane = false;
							KeyUp(attack);
							KeyUp(place);
							KeyUp(forward);
							KeyUp(back);
							KeyUp(left);
							KeyUp(right);
							KeyUp(jump);
							Thread.sleep(new Random().nextInt(15000) + 10000);
							Minecraft.getMinecraft().thePlayer.sendChatMessage("/warp home");
							Thread.sleep(new Random().nextInt(15000) + 10000);
							cane = true;
							running();
						} catch (InterruptedException ex) {
							ex.printStackTrace();
						}
					}).start();
				} else if (crops) {
					new Thread(() -> {
						try {
							crops = false;
							KeyUp(attack);
							KeyUp(place);
							KeyUp(forward);
							KeyUp(back);
							KeyUp(left);
							KeyUp(right);
							KeyUp(jump);
							Thread.sleep(new Random().nextInt(15000) + 10000);
							Minecraft.getMinecraft().thePlayer.sendChatMessage("/warp home");
							Thread.sleep(new Random().nextInt(15000) + 10000);
							crops = true;
							running();
						} catch (InterruptedException ex) {
							ex.printStackTrace();
						}
					}).start();
				}
			}
		}
	}

	public static void running() {
		if (wart || crops) {
			CropsFarmingCheck = true;
			farming = true;
		} else if (cane) {
			CaneFarmingCheck = true;
			farming = true;
		}
	}

	@SubscribeEvent
	public void OnTick(TickEvent.ClientTickEvent e) {
		if (e.phase != TickEvent.Phase.START)
			return;
		// if (player == null)
		// return;
		tick++;
		if (tick > updater) {
			location.checkForSkyblock();
			location.checkForIsland();
			tick = 1;
		}
		if (farming) {
			if (location.onIsland) {
				if (wart) {
					WartFarmer();
					Minecraft.getMinecraft().thePlayer
							.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_PURPLE + "Wart started"));
				}
				if (cane) {
					CaneFarmer();
				}
				if (crops) {
					CropFarmer();
				}
			} else {
				farming = false;
				returning = true;
				hub();
//				pause();
			}
		}
	}

	public void WartFarmer() {
		int tool = findItemInHotbar("Newton");
		if (tool == -1) {
			Minecraft.getMinecraft().thePlayer.addChatMessage(
					new ChatComponentText(EnumChatFormatting.RED + "Required farming tool not in hotbar"));
			return;
		}
		if (tool != -1) {
			Minecraft.getMinecraft().thePlayer.inventory.currentItem = tool;
			KeyDown(attack);
			CropsMovement();
			Minecraft.getMinecraft().thePlayer
					.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Farming tool in hotbar"));
		}
		if (tick == 2) {
			OldY = Minecraft.getMinecraft().thePlayer.getPosition().getY();
			OldX = Minecraft.getMinecraft().thePlayer.getPosition().getX();
			OldZ = Minecraft.getMinecraft().thePlayer.getPosition().getZ();
			Minecraft.getMinecraft().thePlayer
					.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Gets old coords"));
			if (tick == 8 && !stuck) {
				if (OldY != Minecraft.getMinecraft().thePlayer.getPosition().getY()) {
					active = !active;
					Minecraft.getMinecraft().thePlayer
							.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Swaps and falls"));

				}
				if (tick == 14) {
					if (OldX == Minecraft.getMinecraft().thePlayer.getPosition().getX()
							&& OldZ == Minecraft.getMinecraft().thePlayer.getPosition().getZ()) {
						active = !active; // swap direction
						Minecraft.getMinecraft().thePlayer
								.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Swapping directions"));
					}
					if (tick == 19) {
						if (OldX == Minecraft.getMinecraft().thePlayer.getPosition().getX()
								&& OldZ == Minecraft.getMinecraft().thePlayer.getPosition().getZ()) {
							stuck = true;
							Minecraft.getMinecraft().thePlayer
									.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Stuck"));
						}
					}
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
		if (CropsFarmingCheck) {
			if (stuck) {
				random = new Random().nextInt(100) + 100;
				new Thread(() -> {
					try {
						KeyBinding space = Minecraft.getMinecraft().gameSettings.keyBindJump;
						KeyBinding left = Minecraft.getMinecraft().gameSettings.keyBindLeft;
						KeyBinding right = Minecraft.getMinecraft().gameSettings.keyBindRight;
						Thread.sleep(new Random().nextInt(250) + 500);
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
		if (location.onIsland) {
			if (CaneFarmingCheck) {
				if (stuck) {
					random = new Random().nextInt(100) + 100;
					new Thread(() -> {
						try {
							wart = false;
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
							wart = true;
						} catch (InterruptedException ex) {
							ex.printStackTrace();
						}
					});
				} else if (swap) {
					KeyUp(back);
					KeyDown(right);
				} else {
					KeyUp(right);
					KeyDown(left);
				}
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

	public void release() {
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
	}

	public void onWorldChange(WorldEvent.Unload e) {
		if (farming)
			Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(
					EnumChatFormatting.AQUA + "Farming paused:" + EnumChatFormatting.DARK_RED + "Due to server close"));
		returning = true;
		hub();
	}

	public void disable() {
		active = false; // crops swap walking direction
		swap = false; // cane swap walking direction
		farming = false; // any running
		facingwart = false;
		facingcrops = false;
		facingcane = false;
		leftClick = false;
		rightClick = false;
		wart = false;
		cane = false;
		crops = false;
		CropsFarmingCheck = false;
		CaneFarmingCheck = false;
		stuck = false;
		pause = false;
		returning = false;
		TeleporterCrops = false;
		TeleporterCane = false;
		OldY = 0;
		delay = 0;
		errored = 0;
		FacingX = 0;
		FacingZ = 0;
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
