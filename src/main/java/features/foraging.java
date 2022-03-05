package features;

import java.util.Random;
import java.lang.reflect.Method;

import org.lwjgl.input.Keyboard;
import honesty.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import utils.location;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class foraging {
    public static boolean IslandForaging = false;
    public static boolean foragingcheck = false;
    private boolean direction = false;

    public static int tick = 20;
    public static int updater = 20;

    KeyBinding attack = Minecraft.getMinecraft().gameSettings.keyBindAttack;
    KeyBinding place = Minecraft.getMinecraft().gameSettings.keyBindUseItem;
    KeyBinding forward = Minecraft.getMinecraft().gameSettings.keyBindForward;
    KeyBinding left = Minecraft.getMinecraft().gameSettings.keyBindLeft;
    KeyBinding right = Minecraft.getMinecraft().gameSettings.keyBindRight;

    public static int findItemInHotbar(String name) {
        InventoryPlayer inv = Minecraft.getMinecraft().thePlayer.inventory;
        for (int i = 0; i < 9; i++) {
            ItemStack curStack = inv.getStackInSlot(i);
            if (curStack != null) {
                if (curStack.getDisplayName().toLowerCase().contains(name.toLowerCase())) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static void rightClick() {
        try {
            Method rightClickMouse = null;
            try {
                rightClickMouse = Minecraft.class.getDeclaredMethod("rightClickMouse");
            } catch (NoSuchMethodException e) {
                rightClickMouse = Minecraft.class.getDeclaredMethod("func_147121_ag");
            }
            rightClickMouse.setAccessible(true);
            rightClickMouse.invoke(Minecraft.getMinecraft());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void KeyDown(KeyBinding key) {
        if (!key.isKeyDown())
            KeyBinding.setKeyBindState(key.getKeyCode(), true);
    }

    private static void KeyUp(KeyBinding key) {
        if (key.isKeyDown())
            KeyBinding.setKeyBindState(key.getKeyCode(), false);
    }

    private static final KeyBinding SWAP_KEY = new KeyBinding("Turn off foraging macro", Keyboard.KEY_PERIOD,
            Main.MODID);
    static {
        ClientRegistry.registerKeyBinding(SWAP_KEY);
    }
    
    @SubscribeEvent
	public void onKeyEvent(KeyInputEvent e) {
		if (SWAP_KEY.isPressed()) {
			IslandForaging = false;
			KeyUp(attack);
			KeyUp(place);
			KeyUp(forward);
			KeyUp(left);
			KeyUp(right);
			Minecraft.getMinecraft().thePlayer
					.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Turned off all foraging macro"));
		}
	}

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent e) {
        if (e.phase != TickEvent.Phase.START || !foragingcheck)
            return;
        tick++;
        if (tick > 20)
            tick = 1;
        if (tick == 18) {
            if (IslandForaging) {
                placing();
                foragingcheck = false;
            }
        }
        if (tick == 19) {
            foragingcheck = false;
        }
    }

    public void placing() {
        if (IslandForaging) {
            int plant = findItemInHotbar("sapling");
            if (plant == -1) {
                Minecraft.getMinecraft().thePlayer.addChatMessage(
                        new ChatComponentText(EnumChatFormatting.RED + "No sapplings in hotbar"));
                return;
            }
            if (plant != -1) {
                if (location.onIsland) {
                    new Thread(() -> {
                        try {
                            Minecraft.getMinecraft().thePlayer.inventory.currentItem = plant;
                            KeyDown(place);
                            Thread.sleep(new Random().nextInt(50) + 100);
                            walking();
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }).start();
                }
            }
        }
    }

    public void walking() {
        if (IslandForaging) {
            if (location.onIsland) {
                if (direction) {
                    new Thread(() -> {
                        try {
                            KeyDown(right);
                            Thread.sleep(new Random().nextInt(100) + 50);
                            KeyUp(place);
                            KeyUp(right);
                            growing();
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }).start();
                }

                else if (!direction) {
                    new Thread(() -> {
                        try {
                            KeyDown(left);
                            Thread.sleep(new Random().nextInt(100) + 50);
                            KeyUp(place);
                            KeyUp(left);
                            growing();
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }).start();
                }
            }
        }
    }

    public void growing() {
        if (IslandForaging) {
            int meal = findItemInHotbar("Bone");
            if (meal == -1) {
                Minecraft.getMinecraft().thePlayer.addChatMessage(
                        new ChatComponentText(EnumChatFormatting.RED + "No Bonemeal in hotbar"));
            }
            if (meal != -1) {
                if (location.onIsland) {
                    new Thread(() -> {
                        try {
                            Thread.sleep(new Random().nextInt(25) + 25);
                            Minecraft.getMinecraft().thePlayer.inventory.currentItem = meal;
                            rightClick();
                            Thread.sleep(new Random().nextInt(25) + 10);
                            swapping();
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }).start();
                }
            }
        }
    }

    public void swapping() {
        if (IslandForaging) {
            int rod = findItemInHotbar("Rod");
            if (rod == -1) {
                Minecraft.getMinecraft().thePlayer.addChatMessage(
                        new ChatComponentText(EnumChatFormatting.RED + "No fishing rod in hotbar"));
                return;
            }
            if (rod != -1) {
                if (location.onIsland) {
                    new Thread(() -> {
                        try {
                            Thread.sleep(new Random().nextInt(50) + 50);
                            Minecraft.getMinecraft().thePlayer.inventory.currentItem = rod;
                            Thread.sleep(new Random().nextInt(75) + 100);
                            rightClick();
                            Thread.sleep(new Random().nextInt(75) + 50);
                            breaking();
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }).start();
                }
            }
        }
    }

    public void breaking() {
        if (IslandForaging) {
            int tools = findItemInHotbar("Treecapitator");
            if (tools == -1) {
                Minecraft.getMinecraft().thePlayer.addChatMessage(
                        new ChatComponentText(EnumChatFormatting.RED + "No Treecap in hotbar"));
                return;
            }
            if (tools != -1) {
                if (location.onIsland) {
                    new Thread(() -> {
                        try {
                            Thread.sleep(new Random().nextInt(25) + 50);
                            Minecraft.getMinecraft().thePlayer.inventory.currentItem = tools;
                            Thread.sleep(new Random().nextInt(75) + 50);
                            KeyDown(attack);
                            Thread.sleep(new Random().nextInt(100) + 75);
                            KeyUp(attack);
                            placing();
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }).start();
                }
            }
        }
    }

    public void onWorldChange(WorldEvent.Unload e) {
        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(
                EnumChatFormatting.AQUA + "Foraging stopped:" + EnumChatFormatting.DARK_RED + "Due to server close"));
        IslandForaging = false;
    }

}
