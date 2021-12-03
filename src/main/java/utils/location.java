package utils;

import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.util.EnumChatFormatting;

public class location {
    public static boolean inSkyblock = false;
	public static boolean onIsland = false;
	public static boolean divan = false;

    public static boolean isOnHypixel() {
		Minecraft mc = Minecraft.getMinecraft();
		if (mc != null && mc.theWorld != null && !mc.isSingleplayer()) {
			return mc.getCurrentServerData().serverIP.toLowerCase().contains("hypixel");
		}
		return false;
	}

    public static void checkForSkyblock() {
		Minecraft mc = Minecraft.getMinecraft();
		if (mc != null && mc.theWorld != null && !mc.isSingleplayer()) {
			ScoreObjective scoreboardObj = mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(1);
			if (scoreboardObj != null) {
				String scObjName = scoreboard.cleanSB(scoreboardObj.getDisplayName());
				if (scObjName.contains("SKYBLOCK")) {
					inSkyblock = true;
					return;
				}
			}
		}
		inSkyblock = false;
	}

	public static void checkForIsland() {
		Minecraft mc = Minecraft.getMinecraft();
		if (mc != null && mc.theWorld != null && !mc.isSingleplayer()) {
			ScoreObjective scoreboardObj = mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(1);
			if (scoreboardObj != null) {
				String scObjName = scoreboard.cleanSB(scoreboardObj.getDisplayName().toLowerCase());
				if (scObjName.contains("Your Island")) {
					onIsland = true;
					return;
				}
			}
		}
		onIsland = false;
	}

    public static void checkDivan() {
		Minecraft mc = Minecraft.getMinecraft();
		if (mc != null && mc.theWorld != null && !mc.isSingleplayer()) {
			ScoreObjective scoreboardObj = mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(1);
			if (scoreboardObj != null) {
				String scObjName = scoreboard.cleanSB(scoreboardObj.getDisplayName().toLowerCase());
				if (scObjName.contains("Divan's")) {
					divan = true;
					return;
				}
			}
		}
		divan = false;
	}
}