package gui.opengui;

import java.util.ArrayList;
import java.util.List;

import features.farming;
import gui.honestygui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Vec3;
import utils.rotation;

public class farminggui extends GuiScreen {

    private int page;
    
    private List<GuiButton> allButtons = new ArrayList<>();
	private List<GuiButton> foundButtons = new ArrayList<>();

    //Gui options
    private GuiButton closeGui;
    private GuiButton backPage;
	private GuiButton nextPage;

    //farming start
    private GuiButton Cane_Macro;
    private GuiButton Wart_Macro;
    private GuiButton Crop_Macro;

    //farming options
    private GuiButton Facing_start;


    public farminggui (int page) {
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

		Cane_Macro = new GuiButton(0, 0, 0, "Cane: " + (farming.cane ? EnumChatFormatting.GREEN : EnumChatFormatting.RED) + farming.cane);
		Wart_Macro = new GuiButton(0, 0, 0, "Wart: " + (farming.wart ? EnumChatFormatting.GREEN : EnumChatFormatting.RED) + farming.wart);
		Crop_Macro = new GuiButton(0, 0, 0, "Crops: " + (farming.crops ? EnumChatFormatting.GREEN : EnumChatFormatting.RED) + farming.crops);
        Facing_start = new GuiButton (0, 0, 0, "Directions");

		allButtons.add(Cane_Macro);
		allButtons.add(Wart_Macro);
		allButtons.add(Crop_Macro);
        allButtons.add(Facing_start);


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
			mc.displayGuiScreen(new farminggui(page + 1));
		} else if (button == backPage) {
			mc.displayGuiScreen(new farminggui(page - 1));
		} else if (button == Cane_Macro) {
			farming.cane = !farming.cane;
			farming.running();
            Cane_Macro.displayString = "Enabled" + (farming.cane ? EnumChatFormatting.GREEN : EnumChatFormatting.RED) + farming.cane;
            Minecraft.getMinecraft().thePlayer.closeScreen();
		} else if (button == Wart_Macro) {
			farming.wart = !farming.wart;
			farming.running();
            Wart_Macro.displayString = "Enabled" + (farming.wart ? EnumChatFormatting.GREEN : EnumChatFormatting.RED) + farming.wart;
            Minecraft.getMinecraft().thePlayer.closeScreen();
    		BlockPos wart = Minecraft.getMinecraft().thePlayer.getPosition().add(0, 0.1, 4);
    		rotation.facePos(new Vec3(Minecraft.getMinecraft().thePlayer.getPositionVector().xCoord, wart.getY(), wart.getZ()));
		} else if (button == Cane_Macro) {
			farming.crops = !farming.crops;
			farming.running();
            Cane_Macro.displayString = "Enabled" + (farming.crops ? EnumChatFormatting.GREEN : EnumChatFormatting.RED) + farming.crops;
            Minecraft.getMinecraft().thePlayer.closeScreen();
		} else if (button == Facing_start) {
            farming.cropdirection++;
            if (farming.cropdirection == 1) {
              Facing_start.displayString = " Crop Facing: East" + EnumChatFormatting.RED + "color coding werkt";
            } else if (farming.cropdirection == 2) {
              Facing_start.displayString = "Crop Facing: South" + EnumChatFormatting.GREEN + "color coding werkt";
            } else if (farming.cropdirection == 3) {
              Facing_start.displayString = "Crop Facing: West" + EnumChatFormatting.YELLOW + "color coding werkt";
            } else {
              Facing_start.displayString = "Crop Facing: North" + EnumChatFormatting.BLUE + "color coding werkt";
              farming.cropdirection = 0;
            }
        }
    }
}