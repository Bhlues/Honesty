package features;

import java.util.Random;

import org.lwjgl.input.Keyboard;

import honesty.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class corleone {
	boolean afk = false;

	private static void KeyDown(KeyBinding key) {
		if (!key.isKeyDown())
			KeyBinding.setKeyBindState(key.getKeyCode(), true);
	}

	private static void KeyUp(KeyBinding key) {
		if (key.isKeyDown())
			KeyBinding.setKeyBindState(key.getKeyCode(), false);
	}

	private static final KeyBinding SWAP_KEY = new KeyBinding("Corleone.afk", Keyboard.KEY_Z, Main.MODID);
	static {
		ClientRegistry.registerKeyBinding(SWAP_KEY);
	}

	@SubscribeEvent
	public void onKeyEvent(KeyInputEvent e) {
		if (SWAP_KEY.isPressed()) {
			afk = !afk;
			Minecraft.getMinecraft().thePlayer
					.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "Toggled Corleone afk: " + afk));
		}
	}

	int tick = 0;
	int random = 15;

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent e) {
		if (e.phase != TickEvent.Phase.START || !afk)
			return;
		tick++;
		if (tick == random) {
			random = new Random().nextInt(100) + 750;
			new Thread(() -> {
				try {
					KeyBinding space = Minecraft.getMinecraft().gameSettings.keyBindJump;
					Thread.sleep(new Random().nextInt(75) + 50);
					KeyDown(space);
					Thread.sleep(new Random().nextInt(75) + 50);
					KeyUp(space);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				tick = 1;

			}).start();
		}
	}

	@SubscribeEvent
	public void onWorldChange(WorldEvent.Unload e) {
		if (afk)
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA
					+ "CorleoneAfk: " + EnumChatFormatting.RED + "Stopped due to server closing"));
		reset();
	}

	private void reset() {
		afk = false;
	}
}