package honesty;

import features.corleone;
import features.farming;
import features.soulwhip;
import features.teleport;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = Main.MODID, version = Main.VERSION)
public class Main
{
    public static final String MODID = "Apec Beta";
    public static final String VERSION = "1.01";
    
	// EVENTS:        MinecraftForge.EVENT_BUS.register(new Chat());
	//	COMMANDS:         ClientCommandHandler.instance.registerCommand(new SECommand());	
	
    
    @EventHandler
	public void init(FMLInitializationEvent event) {
    	MinecraftForge.EVENT_BUS.register(new corleone());
    	MinecraftForge.EVENT_BUS.register(new soulwhip());
    	MinecraftForge.EVENT_BUS.register(new teleport());
    	MinecraftForge.EVENT_BUS.register(new farming());
    }
}
