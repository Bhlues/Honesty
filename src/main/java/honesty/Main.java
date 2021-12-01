package honesty;

import org.lwjgl.input.Keyboard;

import features.corleone;
import features.farming;
import features.soulwhip;
import features.teleport;
import gui.honestygui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import utils.rotation;

@Mod(modid = Main.MODID, version = Main.VERSION)
public class Main {
	public static final String MODID = "Apec Beta";
	public static final String VERSION = "1.01";

	// EVENTS: MinecraftForge.EVENT_BUS.register(new Chat());
	// COMMANDS: ClientCommandHandler.instance.registerCommand(new SECommand());

	@EventHandler
	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new corleone());
		MinecraftForge.EVENT_BUS.register(new soulwhip());
		MinecraftForge.EVENT_BUS.register(new teleport());
		MinecraftForge.EVENT_BUS.register(new farming());
		MinecraftForge.EVENT_BUS.register(new rotation());
		MinecraftForge.EVENT_BUS.register(this);
	}

	public static final KeyBinding SWAP_KEY = new KeyBinding("GUI", Keyboard.KEY_L, Main.MODID);
	static {
		ClientRegistry.registerKeyBinding(SWAP_KEY);
	}

	@SubscribeEvent
	public void onKeyEvent(KeyInputEvent e) {
		if (SWAP_KEY.isPressed()) {
			Minecraft.getMinecraft().displayGuiScreen(new honestygui(1));
		}
	}

}
