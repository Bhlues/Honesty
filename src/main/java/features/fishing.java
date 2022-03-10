package features;

import java.lang.reflect.Method;

import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;


public class fishing {
    private long ReelRod= 0;

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

    public void OnInteract(PlayerInteractEvent e) {
    
    }

}
