package features;

import java.lang.reflect.Method;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import honesty.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import utils.location;

public class foraging {
	public static boolean IslandForaging = false;
	public static boolean foragingcheck = false;
	public static boolean grow = false;
	public static boolean StartAgain = false;
	private boolean direction = false;
	private boolean guiOpened = false;

	public static int tick = 20;
	public static int updater = 20;

	KeyBinding attack = Minecraft.getMinecraft().gameSettings.keyBindAttack;
	KeyBinding place = Minecraft.getMinecraft().gameSettings.keyBindUseItem;
	KeyBinding forward = Minecraft.getMinecraft().gameSettings.keyBindForward;
	KeyBinding left = Minecraft.getMinecraft().gameSettings.keyBindLeft;
	KeyBinding right = Minecraft.getMinecraft().gameSettings.keyBindRight;

	public static int findItemInHotbar(String name) {
		InventoryPlayer inv = Minecraft.getMinecraft().thePlayer.inventory;
		for (int i = 0; i < 9; i++) {
			ItemStack curStack = inv.getStackInSlot(i);
			if (curStack != null) {
				if (curStack.getDisplayName().toLowerCase().contains(name.toLowerCase())) {
					return i;
				}
			}
		}
		return -1;
	}

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

	public static void click() {
		try {
			Method clickMouse;
			try {
				clickMouse = Minecraft.class.getDeclaredMethod("func_147116_af");
			} catch (NoSuchMethodException e) {
				clickMouse = Minecraft.class.getDeclaredMethod("clickMouse");
			}
			clickMouse.setAccessible(true);
			clickMouse.invoke(Minecraft.getMinecraft());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SubscribeEvent
	public void openGui(GuiOpenEvent event) {
		guiOpened = event.gui != null;
	}

	@SubscribeEvent
	public void onChat(ClientChatReceivedEvent e) {
		String msg = StringUtils.stripControlCodes(e.message.getUnformattedText()).toLowerCase();
		if (msg.contains("leond3")) {
			return;
		}
		if (msg.contains(":"))
			return;
		if (msg.endsWith(" is visiting your island!"))
			IslandForaging = false;
		if (msg.startsWith("co-op")) {
			if (msg.contains("go waste time and start foraging")) {
				if (StartAgain) {
					IslandForaging = true;
				}
			}
		}
	}

	private static void KeyDown(KeyBinding key) {
		if (!key.isKeyDown())
			KeyBinding.setKeyBindState(key.getKeyCode(), true);
	}

	private static void KeyUp(KeyBinding key) {
		if (key.isKeyDown())
			KeyBinding.setKeyBindState(key.getKeyCode(), false);
	}

	private static final KeyBinding SWAP_KEY = new KeyBinding("Turn off foraging macro", Keyboard.KEY_PERIOD,
			Main.MODID);
	static {
		ClientRegistry.registerKeyBinding(SWAP_KEY);
	}

	@SubscribeEvent
	public void onKeyEvent(KeyInputEvent e) {
		if (SWAP_KEY.isPressed()) {
			IslandForaging = false;
			KeyUp(attack);
			KeyUp(place);
			KeyUp(forward);
			KeyUp(left);
			KeyUp(right);
			Minecraft.getMinecraft().thePlayer
					.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Turned off all foraging macro"));
		}
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent e) {
		if (e.phase != TickEvent.Phase.START || !foragingcheck)
			return;
		tick++;
		if (tick > 20)
			tick = 1;
		if (tick == 18) {
			if (IslandForaging) {
				placing();
				foragingcheck = false;
			}
		}
		if (tick == 19) {
			foragingcheck = false;
		}
	}

	public void placing() {
		if (!guiOpened) {
			if (IslandForaging) {
				int plant = findItemInHotbar("Sapling");
				if (plant == -1) {
					Minecraft.getMinecraft().thePlayer
							.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "No sapplings in hotbar"));
					return;
				}
				if (plant != -1) {
					if (location.onIsland) {
						if (!grow) {
							new Thread(() -> {
								try {
									Minecraft.getMinecraft().thePlayer.inventory.currentItem = plant;
									Thread.sleep(new Random().nextInt(50) + 50);
									rightClick();
									Thread.sleep(new Random().nextInt(50) + 50);
									rightClick();
									walking();
								} catch (InterruptedException ex) {
									ex.printStackTrace();
								}
							}).start();
						} else if (grow) {
							new Thread(() -> {
								try {
									Minecraft.getMinecraft().thePlayer.inventory.currentItem = plant;
									Thread.sleep(new Random().nextInt(50) + 50);
									rightClick();
									Thread.sleep(new Random().nextInt(50) + 50);
									rightClick();
									grow = false;
									growing();
								} catch (InterruptedException ex) {
									ex.printStackTrace();
								}
							}).start();
						}
					}
				}
			}
		}
	}

	public void walking() {
		if (!guiOpened) {
			if (IslandForaging) {
				if (location.onIsland) {
					if (direction) {
						new Thread(() -> {
							try {
								KeyDown(right);
								Thread.sleep(new Random().nextInt(500) + 150);
								KeyUp(right);
								grow = true;
								placing();
							} catch (InterruptedException ex) {
								ex.printStackTrace();
							}
						}).start();
					}

					else if (!direction) {
						new Thread(() -> {
							try {
								KeyDown(left);
								Thread.sleep(new Random().nextInt(500) + 150);
								KeyUp(left);
								grow = true;
								placing();
							} catch (InterruptedException ex) {
								ex.printStackTrace();
							}
						}).start();
					}
				}
			}
		}
	}

	public void growing() {
		if (!guiOpened) {
			if (IslandForaging) {
				int meal = findItemInHotbar("Bone");
				if (meal == -1) {
					Minecraft.getMinecraft().thePlayer
							.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "No Bonemeal in hotbar"));
				}
				if (meal != -1) {
					if (location.onIsland) {
						new Thread(() -> {
							try {
								Thread.sleep(new Random().nextInt(75) + 50);
								Minecraft.getMinecraft().thePlayer.inventory.currentItem = meal;
								rightClick();
								Thread.sleep(new Random().nextInt(25) + 10);
								swapping();
							} catch (InterruptedException ex) {
								ex.printStackTrace();
							}
						}).start();
					}
				}
			}
		}
	}

	public void swapping() {
		if (!guiOpened) {
			if (IslandForaging) {
				int rod = findItemInHotbar("Rod");
				if (rod == -1) {
					Minecraft.getMinecraft().thePlayer
							.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "No fishing rod in hotbar"));
					return;
				}
				if (rod != -1) {
					if (location.onIsland) {
						new Thread(() -> {
							try {
								Thread.sleep(new Random().nextInt(50) + 50);
								Minecraft.getMinecraft().thePlayer.inventory.currentItem = rod;
								Thread.sleep(new Random().nextInt(75) + 100);
								rightClick();
								Thread.sleep(new Random().nextInt(75) + 50);
								breaking();
							} catch (InterruptedException ex) {
								ex.printStackTrace();
							}
						}).start();
					}
				}
			}
		}
	}

	public void breaking() {
		if (!guiOpened) {
			if (IslandForaging) {
				int tools = findItemInHotbar("Treecapitator");
				if (tools == -1) {
					Minecraft.getMinecraft().thePlayer
							.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "No Treecap in hotbar"));
					return;
				}
				if (tools != -1) {
					if (location.onIsland) {
						new Thread(() -> {
							try {
								Thread.sleep(new Random().nextInt(25) + 50);
								Minecraft.getMinecraft().thePlayer.inventory.currentItem = tools;
								Thread.sleep(new Random().nextInt(75) + 50);
								click();
								Thread.sleep(new Random().nextInt(1000) + 400);
								placing();
								direction = !direction;
							} catch (InterruptedException ex) {
								ex.printStackTrace();
							}
						}).start();
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onWorldChange(WorldEvent.Unload e) {
		if (IslandForaging) {
			Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.AQUA
					+ "Foraging stopped:" + EnumChatFormatting.DARK_RED + "Due to server close"));
			IslandForaging = false;
		}
	}
}
