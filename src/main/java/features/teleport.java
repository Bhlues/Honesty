package features;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class teleport {
	ArrayList<String> ItemList = new ArrayList<>(Arrays.asList("Stonk", "Daedalus", "Metal"));
	ArrayList<String> TeleportList = new ArrayList<>(Arrays.asList("Void", "Hyperion", "Scylla", "Valk", "Astrea"));
	ArrayList<String> RodList = new ArrayList<>(Arrays.asList("Rod"));
	ArrayList<String> ForagingList = new ArrayList<>(Arrays.asList("Treecapitator", "Jungle"));
	ArrayList<String> Soulwhip = new ArrayList<>(Arrays.asList("Soul"));
	ArrayList<String> Swapdps = new ArrayList<>(Arrays.asList("Blade", "Giant"));
	
	public static boolean active = false;
	public static boolean active2 = false;
	public static boolean active3 = false;

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
	public void ability(PlayerInteractEvent event) {
		if (Minecraft.getMinecraft().thePlayer.isSneaking() || !active) return;
		if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR
				|| event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
			if (Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem() == null) return;
			for (String List : ItemList) {
				if (Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem().getDisplayName().contains(List)) {
					for (String List2 : TeleportList) {
						int Slot = findItemInHotbar(List2);
						if (Slot >= 0) {
							event.setCanceled(true);
							new Thread(() -> {
								int prevSlot = Minecraft.getMinecraft().thePlayer.inventory.currentItem;
								Minecraft.getMinecraft().thePlayer.inventory.currentItem = Slot;
								try {
									Thread.sleep(new Random().nextInt(75) + 50);
									rightClick();
									Thread.sleep(new Random().nextInt(75) + 50);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								Minecraft.getMinecraft().thePlayer.inventory.currentItem = prevSlot;
							}).start();
							return;
						}
					}

				}
			}
		}
	}
	@SubscribeEvent
	public void rod(PlayerInteractEvent event) {
		if (Minecraft.getMinecraft().thePlayer.isSneaking() || !active3) return;
		if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR
				|| event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
			if (Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem() == null) return;
			for (String List3 : ForagingList) {
				if (Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem().getDisplayName().contains(List3)) {
					for (String List4 : RodList) {
						int Slot = findItemInHotbar(List4);
						if (Slot >= 0) {
							event.setCanceled(true);
							new Thread(() -> {
								int prevSlot = Minecraft.getMinecraft().thePlayer.inventory.currentItem;
								Minecraft.getMinecraft().thePlayer.inventory.currentItem = Slot;
								try {
									Thread.sleep(new Random().nextInt(75) + 50);
									rightClick();
									Thread.sleep(new Random().nextInt(75) + 50);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								Minecraft.getMinecraft().thePlayer.inventory.currentItem = prevSlot;
							}).start();
							return;
						}
					}

				}
			}
		}
	}
	@SubscribeEvent
	public void SoulWhip(PlayerInteractEvent event) {
		if (!active2) return;
		if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR
				|| event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
			if (Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem() == null) return;
			for (String List3 : Swapdps) {
				if (Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem().getDisplayName().contains(List3)) {
					for (String List4 : Soulwhip) {
						int Slot = findItemInHotbar(List4);
						if (Slot >= 0) {
							event.setCanceled(true);
							new Thread(() -> {
								int prevSlot = Minecraft.getMinecraft().thePlayer.inventory.currentItem;
								Minecraft.getMinecraft().thePlayer.inventory.currentItem = Slot;
								try {
									Thread.sleep(new Random().nextInt(75) + 50);
									rightClick();
									Thread.sleep(new Random().nextInt(75) + 50);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								Minecraft.getMinecraft().thePlayer.inventory.currentItem = prevSlot;
							}).start();
							return;
						}
					}

				}
			}
		}
	}

}