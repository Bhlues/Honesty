package features;

import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.reflect.Method;

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
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public class foraging {
    public static boolean IslandForaging;
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
                if (curStack.getDisplayName().contains(name)) {
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

    private static final KeyBinding SWAP_KEY = new KeyBinding("Turn off all macro's", Keyboard.KEY_PERIOD,
            Main.MODID);
    static {
        ClientRegistry.registerKeyBinding(SWAP_KEY);
    }

    public void placing() {
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
                        Thread.sleep(new Random().nextInt(150) + 100);
                        walking();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }).start();
            }
        }
    }

    public void walking() {
        if (location.onIsland) {
            if (direction) {
                new Thread(() -> {
                    try {
                        Thread.sleep(new Random().nextInt(50) + 50);
                        KeyUp(place);
                        growing();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }).start();
            }

            else if (!direction) {
                new Thread(() -> {
                    try {
                        Thread.sleep(new Random().nextInt(50) + 50);
                        KeyUp(place);
                        growing();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }).start();
            }
        }
    }

    public void growing() {
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

    public void swapping() {
        int rod = findItemInHotbar("rod");
       if (rod == -1) {

        return;
       }
       if (rod != -1) {
        if (location.onIsland) {

        }
       }
    }

    public void breaking() {
        if (location.onIsland) {

        }
    }

}