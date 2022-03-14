package features;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import utils.GuiChestEvent;
import utils.location;

public class experiments {

	@SubscribeEvent
	public void onGuiRender(GuiScreenEvent.BackgroundDrawnEvent e) {
		if (!location.onIsland)
			return;
		if (e.gui instanceof GuiChest) {
			Container inventory = ((GuiChest) e.gui).inventorySlots;
			if (inventory instanceof ContainerChest) {
				ContainerChest chest = (ContainerChest) inventory;
				MinecraftForge.EVENT_BUS.post(new GuiChestEvent(inventory.inventorySlots,
						chest.getLowerChestInventory().getDisplayName().getUnformattedText()));
			}
		}
	}

	int last = -1;
	private ArrayList<Integer> order = new ArrayList<>();
	private boolean added = false;
	private boolean goSolve = false;
	public static boolean ExperimentSolver = true;

	@SubscribeEvent
	public void onChestRender(GuiChestEvent e) {
		if (!ExperimentSolver) return;
		if (e.getSize() != 54) return; 
		String displayName = e.getName();
		if (displayName.contains("Chronomatron")) { //!displayName??
			if (order.size() > 12) {
				Minecraft.getMinecraft().thePlayer.closeScreen();
				return;
			}
			List<Slot> slots = e.getSlots();
			ItemStack item = slots.get(49).getStack();
			if (item.getItem() == Items.clock && !added) {
				for (int i = 20; i <= 24; i++) {
					ItemStack first = slots.get(i).getStack();
					ItemStack second = slots.get(i).getStack();
					if (first.getItem() == Item.getItemFromBlock(Blocks.stained_hardened_clay)) {
						order.add(i);
					} else if (second.getItem() == Item.getItemFromBlock(Blocks.stained_hardened_clay)) {
						order.add(i + 9);
					}
				}
				added = true;
				solve();
			} else if (item.getItem() == Item.getItemFromBlock(Blocks.glowstone)) {
				added = false;
				goSolve = false;
			}
		} else if (displayName.contains("Ultrasequencer")) { //!displayName??
				if (order.size() > 9) {
					Minecraft.getMinecraft().thePlayer.closeScreen();
					return;
				}
			List<Slot> slots = e.getSlots();
			ItemStack item = slots.get(49).getStack();
			if (item.getItem() != Item.getItemFromBlock(Blocks.glowstone)) {
				added = false;
				solve();
				return;
			}
			goSolve = false;
			if (added)
				return;
			HashMap<Integer, Integer> orderMap = new HashMap<>();
			for (int i = 9; i <= 44; i++) {
				if (slots.get(i).getStack().getItem() != Items.dye) {
					orderMap.put(slots.get(i).getStack().stackSize, i);
				}
			}

			for (int i = 0; i < orderMap.size(); i++) {
				order.add(orderMap.get(i));
			}
		}
	}
	
	@SubscribeEvent
	public void onGui(GuiOpenEvent e) {
		order.clear();
	}
	

	public void solve() {
		if (goSolve)
			return;
		goSolve = true;
		new Thread(() -> {
			int goClick = 0;
			try {
				while (goSolve) {
					int ClickSlot = order.get(goClick);
					Minecraft mc = Minecraft.getMinecraft();
					EntityPlayerSP player = mc.thePlayer;
					if (player.inventory.getCurrentItem() != null) {
						Thread.sleep(300);
						continue;
					}
					goClick++;
					mc.playerController.windowClick(player.openContainer.windowId, ClickSlot, 0, 0, (EntityPlayer) player);
					Thread.sleep(300 + new Random().nextInt(200));
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}).start();
	}
}