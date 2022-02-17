package gui.opengui;

import java.util.ArrayList;
import java.util.List;

import features.teleport;
import gui.honestygui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;

public class otheritemsgui extends GuiScreen {

    private int page;
    
    private List<GuiButton> allButtons = new ArrayList<>();
	private List<GuiButton> foundButtons = new ArrayList<>();

    //Gui options
    private GuiButton closeGui;
    private GuiButton backPage;
	private GuiButton nextPage;

    //farming start
    private GuiButton SoulWhip;
    private GuiButton Teleport;
    private GuiButton FishingRod;



    public otheritemsgui (int page) {
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

		closeGui = new GuiButton(0, width / 2 - 100, (int) (height * 0.9), "Back Main Gui");
		backPage = new GuiButton(0, width / 2 - 100, (int) (height * 0.8), 80, 20, "< Back");
		nextPage = new GuiButton(0, width / 2 + 20, (int) (height * 0.8), 80, 20, "Next >");

		Teleport = new GuiButton(0, 0, 0, "Teleport: " + (teleport.active ? EnumChatFormatting.GREEN : EnumChatFormatting.RED) + teleport.active);
		SoulWhip = new GuiButton(0, 0, 0, "Soulwhip: " + (teleport.active2 ? EnumChatFormatting.GREEN : EnumChatFormatting.RED) + teleport.active2);
		FishingRod = new GuiButton(0, 0, 0, "Fishing Rod: " + (teleport.active3 ? EnumChatFormatting.GREEN : EnumChatFormatting.RED) + teleport.active3);

		allButtons.add(SoulWhip);
		allButtons.add(Teleport);
		allButtons.add(FishingRod);


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

		this.buttonList.add(closeGui);
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button == closeGui) {
			Minecraft.getMinecraft().displayGuiScreen(new honestygui(1));
		} else if (button == nextPage) {
			mc.displayGuiScreen(new otheritemsgui(page + 1));
		} else if (button == backPage) {
			mc.displayGuiScreen(new otheritemsgui(page - 1));
		} else if (button == Teleport) {
			teleport.active = !teleport.active;
			Teleport.displayString = "Teleport: " + (teleport.active ? EnumChatFormatting.GREEN : EnumChatFormatting.RED) + teleport.active;
		} else if (button == SoulWhip) {
			teleport.active2 = !teleport.active2;
			SoulWhip.displayString = "SoulWhip: " + (teleport.active2 ? EnumChatFormatting.GREEN : EnumChatFormatting.RED) + teleport.active2;
		} else if (button == FishingRod) {
			teleport.active3 = !teleport.active3;
			FishingRod.displayString = "Fishing Rod: " + (teleport.active3 ? EnumChatFormatting.GREEN : EnumChatFormatting.RED) + teleport.active3;
		}
    }
}