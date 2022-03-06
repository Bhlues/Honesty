package features;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class soulwhipwithanything {
	//giants, emblade, claymore, fot, atomsplit, katana, reaper falch, daeda, wither blades?
	ArrayList<String> SlayerWeapons = new ArrayList<>(Arrays.asList("Daedalus", "Falchion"));
	ArrayList<String> HighDmgWeapons = new ArrayList<>(Arrays.asList("Giant", "Blade", "Claymore"));
	ArrayList<String> DungeonWeapon = new ArrayList<>(Arrays.asList("Truth", "Katana", "Atomsplit"));
	ArrayList<String> Soulwhip = new ArrayList<>(Arrays.asList("Soul"));
	
	public static boolean SW = false;
	public static boolean HDmgW = false;
	public static boolean DW = false;
	public boolean guiOpened = false;
	
	public long time = 0;

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

	
	@SubscribeEvent
	public void openGui (GuiOpenEvent event) {
		guiOpened = event.gui != null;
	}
	
	
	@SubscribeEvent
	public void Slayer(PlayerInteractEvent event) {
		if (!SW) return;
		if (System.currentTimeMillis() < time) return;
		if (guiOpened) return;
		if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR
				|| event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
			if (Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem() == null) return;
			for (String List3 : SlayerWeapons) {
				if (Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem().getDisplayName().contains(List3)) {
					for (String List4 : Soulwhip) {
						int Slot = findItemInHotbar(List4);
						if (Slot >= 0) {
							event.setCanceled(true);
							new Thread(() -> {
								try {
								int prevSlot = Minecraft.getMinecraft().thePlayer.inventory.currentItem;
								Minecraft.getMinecraft().thePlayer.inventory.currentItem = Slot;
								time = System.currentTimeMillis() + new Random().nextInt(375) + 125;
									Thread.sleep(new Random().nextInt(75) + 50);
									rightClick();
									Thread.sleep(new Random().nextInt(75) + 50);
									Minecraft.getMinecraft().thePlayer.inventory.currentItem = prevSlot;
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}).start();
							return;
						}
					}

				}
			}
		}
	}
	@SubscribeEvent
	public void HighDmg(PlayerInteractEvent event) {
		if (!HDmgW) return;
		if (System.currentTimeMillis() < time) return;
		if (guiOpened) return;
		if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR
				|| event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
			if (Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem() == null) return;
			for (String List3 : HighDmgWeapons) {
				if (Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem().getDisplayName().contains(List3)) {
					for (String List4 : Soulwhip) {
						int Slot = findItemInHotbar(List4);
						if (Slot >= 0) {
							event.setCanceled(true);
							new Thread(() -> {
								try {
								int prevSlot = Minecraft.getMinecraft().thePlayer.inventory.currentItem;
								Minecraft.getMinecraft().thePlayer.inventory.currentItem = Slot;
								time = System.currentTimeMillis() + new Random().nextInt(375) + 125;
									Thread.sleep(new Random().nextInt(75) + 50);
									rightClick();
									Thread.sleep(new Random().nextInt(75) + 50);
									Minecraft.getMinecraft().thePlayer.inventory.currentItem = prevSlot;
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}).start();
							return;
						}
					}

				}
			}
		}
	}

	@SubscribeEvent
	public void Dungeon(PlayerInteractEvent event) {
		if (!DW) return;
		if (System.currentTimeMillis() < time) return;
		if (guiOpened) return;
		if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR
				|| event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
			if (Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem() == null) return;
			for (String List3 : DungeonWeapon) {
				if (Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem().getDisplayName().contains(List3)) {
					for (String List4 : Soulwhip) {
						int Slot = findItemInHotbar(List4);
						if (Slot >= 0) {
							new Thread(() -> {
								try {
								int prevSlot = Minecraft.getMinecraft().thePlayer.inventory.currentItem;
								Minecraft.getMinecraft().thePlayer.inventory.currentItem = Slot;
								time = System.currentTimeMillis() + new Random().nextInt(375) + 125;
									Thread.sleep(new Random().nextInt(75) + 50);
									rightClick();
									Thread.sleep(new Random().nextInt(75) + 50);
									Minecraft.getMinecraft().thePlayer.inventory.currentItem = prevSlot;
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}).start();
							return;
						}
					}

				}
			}
		}
	}

}