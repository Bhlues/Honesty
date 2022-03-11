package features;

import java.awt.Color;
import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import utils.location;

public class experiments {

	private long lastInteractTime;
	private boolean getNextChronomatronClick;
	private int[] pattern;
	private int clickCount;
	private int nextSlot;
	public static boolean ExperimentSolver = false;
	public int UltrasequencerDelay = 250;
	public int ChronomatronDelay = 150;

	public class ChestBackgroundDrawnEvent extends Event {
		public Container chest;
		public String displayName;
		public int chestSize;
		public List<Slot> slots;
		public IInventory chestInv;

		public ChestBackgroundDrawnEvent(final Container chest, final String displayName, final int chestSize,
				final List<Slot> slots, final IInventory chestInv) {
			this.chest = chest;
			this.displayName = displayName;
			this.chestSize = chestSize;
			this.slots = slots;
			this.chestInv = chestInv;
		}
	}

	public static void drawOnSlot(final Slot slot, final int color) {
		Gui.drawRect(slot.xDisplayPosition, slot.yDisplayPosition, slot.xDisplayPosition + 16,
				slot.yDisplayPosition + 16, color);
	}

	public static void drawOnSlot(final Slot slot, final Color color) {
		Gui.drawRect(slot.xDisplayPosition, slot.yDisplayPosition, slot.xDisplayPosition + 16,
				slot.yDisplayPosition + 16, color.getRGB());
	}

	@SubscribeEvent
	public void onRenderGui(final ChestBackgroundDrawnEvent event) {
		if (!ExperimentSolver || !location.onIsland || event.chest.inventorySlots.get(49).getStack() == null) {
			return;
		}
		final List<Slot> invSlots = (List<Slot>) event.chest.inventorySlots;
		if (event.displayName.startsWith("Ultrasequencer (")) {
			if (invSlots.get(49).getStack().getItem().equals(Items.clock)) {
				if (this.pattern[this.clickCount] != 0) {
					final Slot slot = event.slots.get(this.pattern[this.clickCount]);
					drawOnSlot(slot, new Color(0, 255, 0).getRGB());
					if (System.currentTimeMillis() - this.lastInteractTime >= UltrasequencerDelay) {
						UltrasequencerDelay = new Random().nextInt((150)+300);
						Minecraft.getMinecraft().windowClick(Minecraft.getMinecraft().thePlayer.openContainer.windowId,
								this.pattern[this.clickCount], 2, 0, (EntityPlayer) Minecraft.getMinecraft().thePlayer);
						this.lastInteractTime = System.currentTimeMillis();
						this.pattern[this.clickCount] = 0;
						++this.clickCount;
					}
				}
			} else if (invSlots.get(49).getStack().getItem().equals(Item.getItemFromBlock(Blocks.glowstone))) {
				for (int i = 9; i <= 44; ++i) {
					if (invSlots.get(i).getStack() != null) {
						if (this.pattern[invSlots.get(i).getStack().stackSize - 1] == 0
								&& !invSlots.get(i).getStack().getDisplayName().startsWith(" ")) {
							this.pattern[invSlots.get(i).getStack().stackSize - 1] = i;
						}
					}
				}
				this.clickCount = 0;
			}
		} else if (event.displayName.startsWith("Chronomatron (")) {
			if (invSlots.get(49).getStack().getItem().equals(Items.clock)) {
				if (this.getNextChronomatronClick) {
					for (int i = 11; i <= 33; ++i) {
						if (invSlots.get(i).getStack() != null) {
							if (invSlots.get(i).getStack().getItem() == Item
									.getItemFromBlock(Blocks.stained_hardened_clay)
									&& this.pattern[this.nextSlot] == 0) {
								this.getNextChronomatronClick = false;
								this.pattern[this.nextSlot] = i;
								++this.nextSlot;
								break;
							}
						}
					}
				}
				if (this.pattern[this.clickCount] != 0) {
					final Slot slot = event.slots.get(this.pattern[this.clickCount]);
					drawOnSlot(slot, new Color(0, 255, 0).getRGB());
					if (System.currentTimeMillis() - this.lastInteractTime >= ChronomatronDelay) {
						ChronomatronDelay = new Random ().nextInt(200)+250);
						Minecraft.getMinecraft().playerController.windowClick(Minecraft.getMinecraft(),
								this.pattern[this.clickCount], 2, 0, (EntityPlayer) Minecraft.getMinecraft().thePlayer);
						this.lastInteractTime = System.currentTimeMillis();
						++this.clickCount;
					}
				}
			} else if (invSlots.get(49).getStack().getItem().equals(Item.getItemFromBlock(Blocks.glowstone))) {
				this.clickCount = 0;
				this.getNextChronomatronClick = true;
			}
		}
	}

	@SubscribeEvent
	public void onOpenGui(final GuiOpenEvent event) {
		this.clickCount = 0;
		this.pattern = new int[66];
		this.nextSlot = 0;
	}

	/*
	 * static int lastChronomatronRound = 0;
	 * static List<String> chronomatronPattern = new ArrayList<>();
	 * static int chronomatronMouseClicks = 0;
	 * public static int CHRONOMATRON_NEXT;
	 * public static int CHRONOMATRON_NEXT_TO_NEXT;
	 * public static boolean chronomatronToggled = false;
	 * public static boolean ultrasequencerToggled = true;
	 * static Slot[] clickInOrderSlots = new Slot[36];
	 * static int lastUltraSequencerClicked = 0;
	 * public static int ULTRASEQUENCER_NEXT;
	 * public static int ULTRASEQUENCER_NEXT_TO_NEXT;
	 * public static int Randomizer = 0;
	 * public static int PrevRandomizer = 0;
	 * public static boolean InExperiment = false;
	 * 
	 * @SubscribeEvent
	 * public void onSlotClick(ChestSlotClickedEvent event) {
	 * if (chronomatronToggled && event.inventoryName.startsWith("Chronomatron ("))
	 * {
	 * IInventory inventory = event.inventory;
	 * ItemStack item = event.item;
	 * if (item == null)
	 * return;
	 * 
	 * if (inventory.getStackInSlot(49).getDisplayName().startsWith("�7Timer: �a")
	 * && (item.getItem() == Item.getItemFromBlock(Blocks.stained_glass)
	 * || item.getItem() == Item.getItemFromBlock(Blocks.stained_hardened_clay))) {
	 * chronomatronMouseClicks++;
	 * }
	 * }
	 * }
	 * 
	 * 
	 * public static void drawOnSlot(int size, int xSlotPos, int ySlotPos, int
	 * colour) {
	 * ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
	 * int guiLeft = (sr.getScaledWidth() - 176) / 2;
	 * int guiTop = (sr.getScaledHeight() - 222) / 2;
	 * int x = guiLeft + xSlotPos;
	 * int y = guiTop + ySlotPos;
	 * // Move down when chest isn't 6 rows if (size != 90) y += (6 - (size - 36) /
	 * 9)
	 * // 9;
	 * 
	 * GL11.glTranslated(0, 0, 1);
	 * Gui.drawRect(x, y, x + 16, y + 16, colour);
	 * GL11.glTranslated(0, 0, -1);
	 * }
	 * 
	 * @SubscribeEvent
	 * public void onGuiRender(GuiChestBackgroundDrawnEvent event) {
	 * if (chronomatronToggled && event.displayName.startsWith("Chronomatron (")) {
	 * int chestSize = event.chestSize;
	 * List<Slot> invSlots = event.slots;
	 * InExperiment = true;
	 * if (invSlots.size() > 48 && invSlots.get(49).getStack() != null) {
	 * if (invSlots.get(49).getStack().getDisplayName().startsWith("�7Timer: �a") &&
	 * invSlots.get(4).getStack() != null) {
	 * int round = invSlots.get(4).getStack().stackSize;
	 * int timerSeconds =
	 * Integer.parseInt(StringUtils.stripControlCodes(invSlots.get(49).getStack().
	 * getDisplayName()).replaceAll("[^\\d]", ""));
	 * if (round != lastChronomatronRound && timerSeconds == round + 2) {
	 * lastChronomatronRound = round;
	 * for (int i = 10; i <= 43; i++) {
	 * ItemStack stack = invSlots.get(i).getStack();
	 * if (stack == null) continue;
	 * if (stack.getItem() == Item.getItemFromBlock(Blocks.stained_hardened_clay)) {
	 * chronomatronPattern.add(stack.getDisplayName());
	 * break;
	 * }
	 * }
	 * }
	 * if (chronomatronMouseClicks < chronomatronPattern.size()) {
	 * for (int i = 10; i <= 43; i++) {
	 * ItemStack glass = invSlots.get(i).getStack();
	 * if (glass == null) continue;
	 * 
	 * Slot glassSlot = invSlots.get(i);
	 * 
	 * if (chronomatronMouseClicks + 1 < chronomatronPattern.size()) {
	 * if
	 * (chronomatronPattern.get(chronomatronMouseClicks).equals(chronomatronPattern.
	 * get(chronomatronMouseClicks + 1))) {
	 * if (glass.getDisplayName().equals(chronomatronPattern.get(
	 * chronomatronMouseClicks))) {
	 * drawOnSlot(chestSize, glassSlot.xDisplayPosition, glassSlot.yDisplayPosition,
	 * CHRONOMATRON_NEXT + 0xE5000000);
	 * }
	 * } else if (glass.getDisplayName().equals(chronomatronPattern.get(
	 * chronomatronMouseClicks))) {
	 * drawOnSlot(chestSize, glassSlot.xDisplayPosition, glassSlot.yDisplayPosition,
	 * CHRONOMATRON_NEXT + 0xE5000000);
	 * } else if (glass.getDisplayName().equals(chronomatronPattern.get(
	 * chronomatronMouseClicks + 1))) {
	 * drawOnSlot(chestSize, glassSlot.xDisplayPosition, glassSlot.yDisplayPosition,
	 * CHRONOMATRON_NEXT_TO_NEXT + 0XBE000000);
	 * }
	 * } else if (glass.getDisplayName().equals(chronomatronPattern.get(
	 * chronomatronMouseClicks))) {
	 * drawOnSlot(chestSize, glassSlot.xDisplayPosition, glassSlot.yDisplayPosition,
	 * CHRONOMATRON_NEXT + 0xE5000000);
	 * }
	 * }
	 * }
	 * } else if (invSlots.get(49).getStack().getDisplayName().
	 * equals("�aRemember the pattern!")) {
	 * chronomatronMouseClicks = 0;
	 * }
	 * }
	 * Minecraft mc = Minecraft.getMinecraft();
	 * ScaledResolution sr = new ScaledResolution(mc);
	 * int guiLeft = (sr.getScaledWidth() - 176) / 2;
	 * new TextRenderer(mc, String.join("\n", chronomatronPattern), (int) (guiLeft *
	 * 0.8), 10, 1);
	 * }
	 * else if (ultrasequencerToggled &&
	 * event.displayName.startsWith("Ultrasequencer (")) {
	 * List<Slot> invSlots = event.slots;
	 * InExperiment = true;
	 * if (invSlots.size() > 48 && invSlots.get(49).getStack() != null) {
	 * if (invSlots.get(49).getStack().getDisplayName().startsWith("�7Timer: �a")) {
	 * lastUltraSequencerClicked = 0;
	 * for (Slot slot : clickInOrderSlots) {
	 * if (slot != null && slot.getStack() != null &&
	 * StringUtils.stripControlCodes(slot.getStack().getDisplayName()).matches(
	 * "\\d+")) {
	 * int number =
	 * Integer.parseInt(StringUtils.stripControlCodes(slot.getStack().getDisplayName
	 * ()));
	 * if (number > lastUltraSequencerClicked) {
	 * lastUltraSequencerClicked = number;
	 * }
	 * }
	 * }
	 * if (clickInOrderSlots[lastUltraSequencerClicked] != null) {
	 * Slot nextSlot = clickInOrderSlots[lastUltraSequencerClicked];
	 * drawOnSlot(event.chestSize, nextSlot.xDisplayPosition,
	 * nextSlot.yDisplayPosition, ULTRASEQUENCER_NEXT + 0xE5000000);
	 * }
	 * if (lastUltraSequencerClicked + 1 < clickInOrderSlots.length) {
	 * if (clickInOrderSlots[lastUltraSequencerClicked + 1] != null) {
	 * Slot nextSlot = clickInOrderSlots[lastUltraSequencerClicked + 1];
	 * drawOnSlot(event.chestSize, nextSlot.xDisplayPosition,
	 * nextSlot.yDisplayPosition, ULTRASEQUENCER_NEXT_TO_NEXT + 0xD7000000);
	 * }
	 * }
	 * }
	 * }
	 * } else {
	 * InExperiment = false;
	 * }
	 * }
	 * 
	 * @SubscribeEvent
	 * public void onTick(TickEvent.ClientTickEvent event) {
	 * if (InExperiment) {
	 * if (Randomizer == PrevRandomizer) {
	 * Randomizer = new Random().nextInt(200) + 250;
	 * } else {
	 * new Thread(() -> {
	 * try {
	 * Thread.sleep(Randomizer);
	 * 
	 * PrevRandomizer = Randomizer;
	 * } catch (InterruptedException ex) {
	 * ex.printStackTrace();
	 * }
	 * }).start();
	 * 
	 * }
	 * }
	 * }
	 * 
	 * @SubscribeEvent
	 * public void onTick(TickEvent.ClientTickEvent event) {
	 * if (event.phase != TickEvent.Phase.START) return;
	 * 
	 * Minecraft mc = Minecraft.getMinecraft();
	 * EntityPlayerSP player = mc.thePlayer;
	 * if (mc.currentScreen instanceof GuiChest) {
	 * if (player == null) return;
	 * ContainerChest chest = (ContainerChest) player.openContainer;
	 * List<Slot> invSlots = ((GuiChest)
	 * mc.currentScreen).inventorySlots.inventorySlots;
	 * String chestName =
	 * chest.getLowerChestInventory().getDisplayName().getUnformattedText().trim();
	 * 
	 * if (ultrasequencerToggled && chestName.startsWith("Ultrasequencer (")) {
	 * if (invSlots.get(49).getStack() != null &&
	 * invSlots.get(49).getStack().getDisplayName().equals("�aRemember the pattern!"
	 * )) {
	 * for (int i = 9; i <= 44; i++) {
	 * if (invSlots.get(i) == null || invSlots.get(i).getStack() == null) continue;
	 * String itemName =
	 * StringUtils.stripControlCodes(invSlots.get(i).getStack().getDisplayName());
	 * if (itemName.matches("\\d+")) {
	 * int number = Integer.parseInt(itemName);
	 * clickInOrderSlots[number - 1] = invSlots.get(i);
	 * }
	 * }
	 * }
	 * }
	 * }
	 * }
	 * 
	 * 
	 * @SubscribeEvent
	 * public void onGuiOpen(GuiOpenEvent event) {
	 * clickInOrderSlots = new Slot[36];
	 * lastChronomatronRound = 0;
	 * chronomatronPattern.clear();
	 * chronomatronMouseClicks = 0;
	 * }
	 */
}