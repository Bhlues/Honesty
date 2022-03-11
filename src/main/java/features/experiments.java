package features;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import utils.ChestSlotClickedEvent;

import utils.GuiChestBackgroundDrawnEvent;
import utils.TextRenderer;

public class experiments {

	static int lastChronomatronRound = 0;
	static List<String> chronomatronPattern = new ArrayList<>();
	static int chronomatronMouseClicks = 0;
	public static int CHRONOMATRON_NEXT;
	public static int CHRONOMATRON_NEXT_TO_NEXT;
	public static boolean chronomatronToggled = false;
	public static boolean ultrasequencerToggled = true;
	static Slot[] clickInOrderSlots = new Slot[36];
    static int lastUltraSequencerClicked = 0;
    public static int ULTRASEQUENCER_NEXT;
    public static int ULTRASEQUENCER_NEXT_TO_NEXT;

	@SubscribeEvent
	public void onSlotClick(ChestSlotClickedEvent event) {
		if (chronomatronToggled && event.inventoryName.startsWith("Chronomatron (")) {
			IInventory inventory = event.inventory;
			ItemStack item = event.item;
			if (item == null)
				return;

			if (inventory.getStackInSlot(49).getDisplayName().startsWith("§7Timer: §a")
					&& (item.getItem() == Item.getItemFromBlock(Blocks.stained_glass)
							|| item.getItem() == Item.getItemFromBlock(Blocks.stained_hardened_clay))) {
				chronomatronMouseClicks++;
			}
		}
	}

	public static void drawOnSlot(int size, int xSlotPos, int ySlotPos, int colour) {
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		int guiLeft = (sr.getScaledWidth() - 176) / 2;
		int guiTop = (sr.getScaledHeight() - 222) / 2;
		int x = guiLeft + xSlotPos;
		int y = guiTop + ySlotPos;
		// Move down when chest isn't 6 rows if (size != 90) y += (6 - (size - 36) / 9)
		// 9;

		GL11.glTranslated(0, 0, 1);
		Gui.drawRect(x, y, x + 16, y + 16, colour);
		GL11.glTranslated(0, 0, -1);
	}

	 @SubscribeEvent
	    public void onGuiRender(GuiChestBackgroundDrawnEvent event) {
	        if (chronomatronToggled && event.displayName.startsWith("Chronomatron (")) {
	            int chestSize = event.chestSize;
	            List<Slot> invSlots = event.slots;
	            if (invSlots.size() > 48 && invSlots.get(49).getStack() != null) {
	                if (invSlots.get(49).getStack().getDisplayName().startsWith("§7Timer: §a") && invSlots.get(4).getStack() != null) {
	                    int round = invSlots.get(4).getStack().stackSize;
	                    int timerSeconds = Integer.parseInt(StringUtils.stripControlCodes(invSlots.get(49).getStack().getDisplayName()).replaceAll("[^\\d]", ""));
	                    if (round != lastChronomatronRound && timerSeconds == round + 2) {
	                        lastChronomatronRound = round;
	                        for (int i = 10; i <= 43; i++) {
	                            ItemStack stack = invSlots.get(i).getStack();
	                            if (stack == null) continue;
	                            if (stack.getItem() == Item.getItemFromBlock(Blocks.stained_hardened_clay)) {
	                                chronomatronPattern.add(stack.getDisplayName());
	                                break;
	                            }
	                        }
	                    }
	                    if (chronomatronMouseClicks < chronomatronPattern.size()) {
	                        for (int i = 10; i <= 43; i++) {
	                            ItemStack glass = invSlots.get(i).getStack();
	                            if (glass == null) continue;

	                            Slot glassSlot = invSlots.get(i);

	                            if (chronomatronMouseClicks + 1 < chronomatronPattern.size()) {
	                                if (chronomatronPattern.get(chronomatronMouseClicks).equals(chronomatronPattern.get(chronomatronMouseClicks + 1))) {
	                                    if (glass.getDisplayName().equals(chronomatronPattern.get(chronomatronMouseClicks))) {
	                                        drawOnSlot(chestSize, glassSlot.xDisplayPosition, glassSlot.yDisplayPosition, CHRONOMATRON_NEXT + 0xE5000000);
	                                    }
	                                } else if (glass.getDisplayName().equals(chronomatronPattern.get(chronomatronMouseClicks))) {
	                                    drawOnSlot(chestSize, glassSlot.xDisplayPosition, glassSlot.yDisplayPosition, CHRONOMATRON_NEXT + 0xE5000000);
	                                } else if (glass.getDisplayName().equals(chronomatronPattern.get(chronomatronMouseClicks + 1))) {
	                                    drawOnSlot(chestSize, glassSlot.xDisplayPosition, glassSlot.yDisplayPosition, CHRONOMATRON_NEXT_TO_NEXT + 0XBE000000);
	                                }
	                            } else if (glass.getDisplayName().equals(chronomatronPattern.get(chronomatronMouseClicks))) {
	                                drawOnSlot(chestSize, glassSlot.xDisplayPosition, glassSlot.yDisplayPosition, CHRONOMATRON_NEXT + 0xE5000000);
	                            }
	                        }
	                    }
	                } else if (invSlots.get(49).getStack().getDisplayName().equals("§aRemember the pattern!")) {
	                    chronomatronMouseClicks = 0;
	                }
	            }
	            Minecraft mc = Minecraft.getMinecraft();
	            ScaledResolution sr = new ScaledResolution(mc);
	            int guiLeft = (sr.getScaledWidth() - 176) / 2;
	            new TextRenderer(mc, String.join("\n", chronomatronPattern), (int) (guiLeft * 0.8), 10, 1);
	        }
	        if (ultrasequencerToggled && event.displayName.startsWith("Ultrasequencer (")) {
	            List<Slot> invSlots = event.slots;
	            if (invSlots.size() > 48 && invSlots.get(49).getStack() != null) {
	                if (invSlots.get(49).getStack().getDisplayName().startsWith("§7Timer: §a")) {
	                    lastUltraSequencerClicked = 0;
	                    for (Slot slot : clickInOrderSlots) {
	                        if (slot != null && slot.getStack() != null && StringUtils.stripControlCodes(slot.getStack().getDisplayName()).matches("\\d+")) {
	                            int number = Integer.parseInt(StringUtils.stripControlCodes(slot.getStack().getDisplayName()));
	                            if (number > lastUltraSequencerClicked) {
	                                lastUltraSequencerClicked = number;
	                            }
	                        }
	                    }
	                    if (clickInOrderSlots[lastUltraSequencerClicked] != null) {
	                        Slot nextSlot = clickInOrderSlots[lastUltraSequencerClicked];
	                        drawOnSlot(event.chestSize, nextSlot.xDisplayPosition, nextSlot.yDisplayPosition, ULTRASEQUENCER_NEXT + 0xE5000000);
	                    }
	                    if (lastUltraSequencerClicked + 1 < clickInOrderSlots.length) {
	                        if (clickInOrderSlots[lastUltraSequencerClicked + 1] != null) {
	                            Slot nextSlot = clickInOrderSlots[lastUltraSequencerClicked + 1];
	                            drawOnSlot(event.chestSize, nextSlot.xDisplayPosition, nextSlot.yDisplayPosition, ULTRASEQUENCER_NEXT_TO_NEXT + 0xD7000000);
	                        }
	                    }
	                }
	            }
	        }
	    }


	@SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;
        if (mc.currentScreen instanceof GuiChest) {
            if (player == null) return;
            ContainerChest chest = (ContainerChest) player.openContainer;
            List<Slot> invSlots = ((GuiChest) mc.currentScreen).inventorySlots.inventorySlots;
            String chestName = chest.getLowerChestInventory().getDisplayName().getUnformattedText().trim();

            if (ultrasequencerToggled && chestName.startsWith("Ultrasequencer (")) {
                if (invSlots.get(49).getStack() != null && invSlots.get(49).getStack().getDisplayName().equals("§aRemember the pattern!")) {
                    for (int i = 9; i <= 44; i++) {
                        if (invSlots.get(i) == null || invSlots.get(i).getStack() == null) continue;
                        String itemName = StringUtils.stripControlCodes(invSlots.get(i).getStack().getDisplayName());
                        if (itemName.matches("\\d+")) {
                            int number = Integer.parseInt(itemName);
                            clickInOrderSlots[number - 1] = invSlots.get(i);
                        }
                    }
                }
            }
        }
    }

    
    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        clickInOrderSlots = new Slot[36];
        lastChronomatronRound = 0;
		chronomatronPattern.clear();
		chronomatronMouseClicks = 0;
    }
	
}