package utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.util.Vec3;

public class rotation {
	
	static boolean working = false;
			
	public static void facePos(Vec3 vector) {
        if(Minecraft.getMinecraft().currentScreen != null) {
            if (Minecraft.getMinecraft().currentScreen instanceof GuiIngameMenu || Minecraft.getMinecraft().currentScreen instanceof GuiChat) {}
            else {
                return;
            }
        }
        if(working) return;
        new Thread(() -> {
            try {
                working = true;
                double diffX = vector.xCoord - (Minecraft.getMinecraft()).thePlayer.posX;
                double diffY = vector.yCoord - (Minecraft.getMinecraft()).thePlayer.posY;
                double diffZ = vector.zCoord - (Minecraft.getMinecraft()).thePlayer.posZ;
                double dist = Math.sqrt(diffX * diffX + diffZ * diffZ);

                float pitch = (float) -Math.atan2(dist, diffY);
                float yaw = (float) Math.atan2(diffZ, diffX);
                pitch = (float) wrapAngleTo180((pitch * 180F / Math.PI + 90)*-1 - Minecraft.getMinecraft().thePlayer.rotationPitch);
                yaw = (float) wrapAngleTo180((yaw * 180 / Math.PI) - 90 - Minecraft.getMinecraft().thePlayer.rotationYaw);

                for(int i = 0; i < 500; i++) {
                    Minecraft.getMinecraft().thePlayer.rotationYaw += yaw/500;
                    Minecraft.getMinecraft().thePlayer.rotationPitch += pitch/500;
                    Thread.sleep(1);
                }
                working = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
	private static double wrapAngleTo180(double angle) {
        angle %= 360;
        while (angle >= 180) {
            angle -= 360;
        }
        while (angle < -180) {
            angle += 360;
        }
        return angle;
    }
}
