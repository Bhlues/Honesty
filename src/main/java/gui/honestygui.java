package gui;

import java.util.ArrayList;
import java.util.List;

import features.corleone;
import features.perspective;
import features.soulwhip;
import features.foraging;
import gui.opengui.farminggui;
import gui.opengui.otheritemsgui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;

public class honestygui extends GuiScreen {

	private int page;
	// private int page;
	private List<GuiButton> allButtons = new ArrayList<>();
	private List<GuiButton> foundButtons = new ArrayList<>();

	// gui buttons
	private GuiButton closeGUI;
	private GuiButton backPage;
	private GuiButton nextPage;

	// toggles
	private GuiButton corleoneafk;
	private GuiButton farmingbutton;
	private GuiButton isforaging;
	private GuiButton soulwhipSS;
	private GuiButton itemswithother;
	private GuiButton perspectivef5;


	public honestygui(int page) {
		this.page = page;
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void initGui() {
		super.initGui();

		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		int height = sr.getScaledHeight();
		int width = sr.getScaledWidth();

		closeGUI = new GuiButton(0, width / 2 - 100, (int) (height * 0.9), "Close");
		backPage = new GuiButton(0, width / 2 - 100, (int) (height * 0.8), 80, 20, "< Back");
		nextPage = new GuiButton(0, width / 2 + 20, (int) (height * 0.8), 80, 20, "Next >");

		farmingbutton = new GuiButton(0, 0, 0, "Farming Menu");
		isforaging = new GuiButton(0, 0, 0, "Island Foraging" + (foraging.IslandForaging ? EnumChatFormatting.GREEN : EnumChatFormatting.RED) + foraging.IslandForaging);
		corleoneafk = new GuiButton(0, 0, 0, "Corleone AFK: " + (corleone.afk ? EnumChatFormatting.GREEN : EnumChatFormatting.RED)
				+ corleone.afk);
				foraging.foragingcheck = true;
				Minecraft.getMinecraft().thePlayer.closeScreen();
		soulwhipSS = new GuiButton(0, 0, 0, "Soulwhip Swordswap: "
				+ (soulwhip.active ? EnumChatFormatting.GREEN : EnumChatFormatting.RED) + soulwhip.active);
		itemswithother = new GuiButton(0, 0, 0, "Items with other items");
		perspectivef5 = new GuiButton(0, 0, 0, "Remove Second Person: "
				+ (perspective.togglef5 ? EnumChatFormatting.GREEN : EnumChatFormatting.RED) + perspective.togglef5);

		allButtons.add(farmingbutton);
		allButtons.add(isforaging);
		allButtons.add(corleoneafk);
		allButtons.add(soulwhipSS);
		allButtons.add(itemswithother);
		allButtons.add(perspectivef5);
		

		reInit();
	}

	public void reInit() {
		this.buttonList.clear();
		foundButtons.clear();

		for (GuiButton button : allButtons) {
			foundButtons.add(button);
		}

		for (int i = (page - 1) * 7, iteration = 0; iteration < 7 && i < foundButtons.size(); i++, iteration++) {
			GuiButton button = foundButtons.get(i);
			button.xPosition = width / 2 - 100;
			button.yPosition = (int) (height * (0.1 * (iteration + 1)));
			this.buttonList.add(button);
		}

		if (page > 1)
			this.buttonList.add(backPage);
		if (page < Math.ceil(foundButtons.size() / 7D))
			this.buttonList.add(nextPage);

		this.buttonList.add(closeGUI);
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button == closeGUI) {
			Minecraft.getMinecraft().thePlayer.closeScreen();
		} else if (button == nextPage) {
			mc.displayGuiScreen(new honestygui(page + 1));
		} else if (button == backPage) {
			mc.displayGuiScreen(new honestygui(page - 1));
		} else if (button == corleoneafk) {
			corleone.afk = !corleone.afk;
			corleoneafk.displayString = "Corleone AFK: " + (corleone.afk ? EnumChatFormatting.GREEN : EnumChatFormatting.RED)
					+ corleone.afk;
					Minecraft.getMinecraft().thePlayer.closeScreen();
		} else if (button == soulwhipSS) {
			soulwhip.active = !soulwhip.active;
			soulwhipSS.displayString = "Soulwhip Swordswap: "
					+ (soulwhip.active ? EnumChatFormatting.GREEN : EnumChatFormatting.RED) + soulwhip.active;
					Minecraft.getMinecraft().thePlayer.closeScreen();
		} else if (button == itemswithother) {
			Minecraft.getMinecraft().displayGuiScreen(new otheritemsgui(1));
		} else if (button == farmingbutton) {
			Minecraft.getMinecraft().displayGuiScreen(new farminggui(1));
					Minecraft.getMinecraft().thePlayer.closeScreen();
		} else if (button == isforaging) {
			foraging.IslandForaging = !foraging.IslandForaging;
			isforaging.displayString = "Island Foraging: " + (foraging.IslandForaging ? EnumChatFormatting.GREEN : EnumChatFormatting.RED)
					+ foraging.IslandForaging;
					Minecraft.getMinecraft().thePlayer.closeScreen();
			foraging.foragingcheck = true;
		}
	}
}
