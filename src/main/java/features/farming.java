package features;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class farming {
	
	public boolean wart = false;
	public boolean cane = false;
	public boolean crops = false;
	public boolean cocoa = false;
	public boolean active = false;
	public boolean activenoreset = false;
	
	private static void KeyDown(KeyBinding key) {
		if (!key.isKeyDown())
			KeyBinding.setKeyBindState(key.getKeyCode(), true);
	}

	private static void KeyUp(KeyBinding key) {
		if (key.isKeyDown())
			KeyBinding.setKeyBindState(key.getKeyCode(), false);
	}

	KeyBinding attack = Minecraft.getMinecraft().gameSettings.keyBindAttack;
	KeyBinding forward = Minecraft.getMinecraft().gameSettings.keyBindForward;
	KeyBinding back = Minecraft.getMinecraft().gameSettings.keyBindBack;
	KeyBinding right = Minecraft.getMinecraft().gameSettings.keyBindRight;
	KeyBinding left = Minecraft.getMinecraft().gameSettings.keyBindLeft;
	KeyBinding jump = Minecraft.getMinecraft().gameSettings.keyBindJump;
	
	int delay = 0, errored = 0;
	int x = 0;
	int y = -127;
	int z = 0;
	//int layer = y????
	
	@SubscribeEvent
	public void onWorldChange(WorldEvent.Unload e) {
		if (activenoreset)
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA
					+ "Script: " + EnumChatFormatting.RED + "Stopped due to server closing"));
		reset();
	}
	
	/*
	public void message (string msg) {
		Minecraft.getMinecraft().thePlayer
		.addChatMessage(new ChatComponentText)
	}
	*/
	public void reset () {
		wart = false;
		cane = false;
		crops = false;
		cocoa = false;
		active = false;
		KeyUp(attack);
		KeyUp(forward);
		KeyUp(back);
		KeyUp(right);
		KeyUp(left);
		KeyUp(jump);	
		message (EnumChatFormatting.DARK_RED + "Turned farming off");
	}
	
}