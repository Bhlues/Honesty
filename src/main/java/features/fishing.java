package features;

import java.lang.reflect.Method;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import utils.location;

public class fishing {
    /*
    private boolean ReelIn = false;
    private boolean oldBobberIsInWater = false;
    private double oldBobberPosY = 0;
    private long lastBobberEnteredWater = Long.MAX_VALUE;
    private long lastFishingAlert = 0;
    private long ReelRod = 0;

    public static void click() {
        try {
            Method clickMouse;
            try {
                clickMouse = Minecraft.class.getDeclaredMethod("func_147116_af");
            } catch (NoSuchMethodException e) {
                clickMouse = Minecraft.class.getDeclaredMethod("clickMouse");
            }
            clickMouse.setAccessible(true);
            clickMouse.invoke(Minecraft.getMinecraft());
        } catch (Exception e) {
            e.printStackTrace();
        }
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

   
   //  * Checks if the fishing indicator sound should be played. To play the sound, these conditions have to be met:
    // * <p>1. Fishing sound indicator feature is enabled</p>
   //  * <p>2. The player is on skyblock (checked in {@link #onTick(TickEvent.ClientTickEvent)})</p>
    // * <p>3. The player is holding a fishing rod</p>
    // * <p>4. The fishing rod is in the water</p>
    // * <p>5. The bobber suddenly moves downwards, indicating a fish has been caught</p>
   //  *
     // * @return {@code true} if the fishing alert sound should be played, {@code false} otherwise
    // * @see Feature#FISHING_SOUND_INDICATOR
   
    private boolean shouldTriggerFishingIndicator() {
        Minecraft mc = Minecraft.getMinecraft();

        if (main.getConfigValues().isEnabled(ReelIn) && mc.thePlayer.fishEntity != null) {
            // Highly consistent detection by checking when the hook has been in the water for a while and
            // suddenly moves downward. The client may rarely bug out with the idle bobbing and trigger a false positive.
            EntityFishHook bobber = Minecraft.getMinecraft().thePlayer.fishEntity;
            long currentTime = System.currentTimeMillis();
            if (bobber.isInWater() && !oldBobberIsInWater) lastBobberEnteredWater = currentTime;
            oldBobberIsInWater = bobber.isInWater();
            if (bobber.isInWater() && Math.abs(bobber.motionX) < 0.01 && Math.abs(bobber.motionZ) < 0.01
                    && currentTime - lastFishingAlert > 1000 && currentTime - lastBobberEnteredWater > 1500) {
                double movement = bobber.posY - oldBobberPosY; // The Entity#motionY field is inaccurate for this purpose
                oldBobberPosY = bobber.posY;
                if (movement < -0.04d) {
                    lastFishingAlert = currentTime;
                    return true;
                }
            }
        }
        return false;
    }

    public void OnInteract(PlayerInteractEvent e) {
        ItemStack heldItem = e.entityPlayer.getHeldItem();

        if (location.inSkyblock && heldItem != null) {
        } else if (heldItem.getItem() == Items.fishing_rod
                && (e.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK
                        || e.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR)) {
            // Update fishing status if the player is fishing and reels in their rod.
            if (main.getConfigValues().isEnabled(ReelIn)) {
                oldBobberIsInWater = false;
                lastBobberEnteredWater = Long.MAX_VALUE;
                oldBobberPosY = 0;
            }
        }
    } */
}
