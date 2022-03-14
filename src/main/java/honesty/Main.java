package honesty;

import org.lwjgl.input.Keyboard;

import features.corleone;
import features.experiments;
import features.farming;
import features.foraging;
import features.mining;
import features.perspective;
import features.soulwhip;
import features.itemswithotheritems;
import features.soulwhipwithanything;
import gui.honestygui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemStack;
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
	public static final String MODID = "Honesty";
	public static final String VERSION = "1.4";

	// ik kan alles - spongetalisman

	// EVENTS: MinecraftForge.EVENT_BUS.register(new Chat());
	// COMMANDS: ClientCommandHandler.instance.registerCommand(new SECommand());

	@EventHandler
	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new corleone());
		MinecraftForge.EVENT_BUS.register(new soulwhip());
		MinecraftForge.EVENT_BUS.register(new itemswithotheritems());
		MinecraftForge.EVENT_BUS.register(new farming());
		MinecraftForge.EVENT_BUS.register(new rotation());
		MinecraftForge.EVENT_BUS.register(new mining());
		MinecraftForge.EVENT_BUS.register(new perspective());
		MinecraftForge.EVENT_BUS.register(new foraging());
		MinecraftForge.EVENT_BUS.register(new soulwhipwithanything());
		MinecraftForge.EVENT_BUS.register(new experiments());
		MinecraftForge.EVENT_BUS.register(this);
	}

	public static final KeyBinding Gui = new KeyBinding("GUI", Keyboard.KEY_J, MODID);
	static {
		ClientRegistry.registerKeyBinding(Gui);
	}

	@SubscribeEvent
	public void onKeyEvent(KeyInputEvent e) {
		if (Gui.isPressed()) {
			Minecraft mc = Minecraft.getMinecraft();
			ItemStack test = mc.thePlayer.inventory.getCurrentItem();
			if (mc.currentScreen != null) {
				System.out.print(test.getDisplayName());
				return;
			}
			mc.displayGuiScreen(new honestygui(1));
		}
	}
}
