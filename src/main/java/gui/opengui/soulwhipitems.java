package gui.opengui;

import java.util.ArrayList;
import java.util.List;

import features.soulwhipwithanything;
import gui.honestygui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;

public class soulwhipitems extends GuiScreen {

    private int page;
    
    private List<GuiButton> allButtons = new ArrayList<>();
	private List<GuiButton> foundButtons = new ArrayList<>();

    //Gui options
    private GuiButton closeGui;
    private GuiButton backPage;
	private GuiButton nextPage;

    //farming start
    private GuiButton DW;
    private GuiButton HDmgW;
    private GuiButton SW;



    public soulwhipitems (int page) {
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

		DW = new GuiButton(0, 0, 0, "Dungeon Weapons: " + (soulwhipwithanything.DW ? EnumChatFormatting.GREEN : EnumChatFormatting.RED) + soulwhipwithanything.DW);
		HDmgW = new GuiButton(0, 0, 0, "High Damage Weapons: " + (soulwhipwithanything.HDmgW ? EnumChatFormatting.GREEN : EnumChatFormatting.RED) + soulwhipwithanything.HDmgW);
		SW = new GuiButton(0, 0, 0, "Slayer Weapons: " + (soulwhipwithanything.SW ? EnumChatFormatting.GREEN : EnumChatFormatting.RED) + soulwhipwithanything.SW);

		allButtons.add(DW);
		allButtons.add(HDmgW);
		allButtons.add(SW);


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
			mc.displayGuiScreen(new soulwhipitems(page + 1));
		} else if (button == backPage) {
			mc.displayGuiScreen(new soulwhipitems(page - 1));
		} else if (button == DW) {
			soulwhipwithanything.DW = !soulwhipwithanything.DW;
			DW.displayString = "Dungeon Weapons: " + (soulwhipwithanything.DW ? EnumChatFormatting.GREEN : EnumChatFormatting.RED) + soulwhipwithanything.DW;
		} else if (button == HDmgW) {
			soulwhipwithanything.HDmgW = !soulwhipwithanything.HDmgW;
			HDmgW.displayString = "High Damage Weapons: " + (soulwhipwithanything.HDmgW ? EnumChatFormatting.GREEN : EnumChatFormatting.RED) + soulwhipwithanything.HDmgW;
		} else if (button == SW) {
			soulwhipwithanything.SW = !soulwhipwithanything.SW;
			SW.displayString = "Slayer Weapons: " + (soulwhipwithanything.SW ? EnumChatFormatting.GREEN : EnumChatFormatting.RED) + soulwhipwithanything.SW;
		}
    }
}