package features;

import java.util.Random;

import org.lwjgl.input.Keyboard;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import honesty.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import utils.location;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class foraging {
    public static boolean IslandForaging
    private boolean direction = false;

    public static int tick = 20;
    public static int updater = 20;


    private static final KeyBinding SWAP_KEY = new KeyBinding("Turn off all macro's", Keyboard.KEY_PERIOD,
			Main.MODID);
	static {
		ClientRegistry.registerKeyBinding(SWAP_KEY);
	}
    
    public void placing
    {
        if (location.onIsland) {

        }
    }

    public void walking
    {
        if (location.onIsland) {
            if (direction) {

            }

            else if (!direction) {

            }
        }
    }

    public void growing
    {
        if (location.onIsland) {

        }
    }

    public void swapping
    {
        if (location.onIsland) {

        }
    }

    public void breaking
    {
        if (location.onIsland) {

        }
    }

}