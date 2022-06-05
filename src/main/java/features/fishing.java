package features;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import utils.location;

public class fishing {
	private static final fishing INSTANCE = new fishing();

	public static fishing getInstance() {
		return INSTANCE;
	}
	
	
	public static boolean incomingFishWarning = false;
	public static boolean incomingFishWarningR = false;
	public static boolean incomingFishIncSounds = false;
	public static boolean incomingFishHookedSounds = false;
	
	public static boolean Recast = false;
	
	public float incomingFishIncSoundsVol = 0;
	public float incomingFishHookedSoundsVol = 0;

	
	public static int tick = 0;
	public static int updater = 20;
	
	public static class WakeChain {
		public int particleNum = 0;
		public long lastUpdate;
		public double currentAngle;
		public double currentX;
		public double currentZ;

		public final HashMap<Integer, Double> distances = new HashMap<>();

		public WakeChain(long lastUpdate, double currentAngle, double currentX, double currentZ) {
			this.lastUpdate = lastUpdate;
			this.currentAngle = currentAngle;
			this.currentX = currentX;
			this.currentZ = currentZ;
		}
	}
	
	 public static void rightClick() {
	        try {
	            Method rightClickMouse;
	            try {
	                rightClickMouse = Minecraft.class.getDeclaredMethod("func_147121_ag");
	            } catch (NoSuchMethodException e) {
	                rightClickMouse = Minecraft.class.getDeclaredMethod("rightClickMouse");
	            }
	            rightClickMouse.setAccessible(true);
	            rightClickMouse.invoke(Minecraft.getMinecraft());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	 
	public void OnTick (TickEvent.ClientTickEvent e) {
		tick++;
		if (tick > updater) {
			if (Recast) {
				rightClick();
			}
			tick = 1;
		}
	}

	public enum PlayerWarningState {
		NOTHING,
		FISH_INCOMING,
		FISH_HOOKED
	}

	public PlayerWarningState warningState = PlayerWarningState.NOTHING;
	private int hookedWarningStateTicks = 0;

	public final HashMap<Integer, EntityFishHook> hookEntities = new HashMap<>();
	public final HashMap<WakeChain, List<Integer>> chains = new HashMap<>();

	private long lastCastRodMillis = 0;
	private int pingDelayTicks = 0;
	private final List<Integer> pingDelayList = new ArrayList<>();
	private int buildupSoundDelay = 0;

	public static void drawTexturedRect(float x, float y, float width, float height) {
		drawTexturedRect(x, y, width, height, 0, 1, 0, 1);
	}

	public static void drawTexturedRect(float x, float y, float width, float height, int filter) {
		drawTexturedRect(x, y, width, height, 0, 1, 0, 1, filter);
	}

	public static void drawTexturedRect(
		float x,
		float y,
		float width,
		float height,
		float uMin,
		float uMax,
		float vMin,
		float vMax
	) {
		drawTexturedRect(x, y, width, height, uMin, uMax, vMin, vMax, GL11.GL_LINEAR);
	}
	public static void drawTexturedRect(
			float x,
			float y,
			float width,
			float height,
			float uMin,
			float uMax,
			float vMin,
			float vMax,
			int filter
		) {
			GlStateManager.enableTexture2D();
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(
				GL11.GL_SRC_ALPHA,
				GL11.GL_ONE_MINUS_SRC_ALPHA,
				GL11.GL_ONE,
				GL11.GL_ONE_MINUS_SRC_ALPHA
			);
			GL14.glBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);

			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, filter);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, filter);

			Tessellator tessellator = Tessellator.getInstance();
			WorldRenderer worldrenderer = tessellator.getWorldRenderer();
			worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
			worldrenderer
				.pos(x, y + height, 0.0D)
				.tex(uMin, vMax).endVertex();
			worldrenderer
				.pos(x + width, y + height, 0.0D)
				.tex(uMax, vMax).endVertex();
			worldrenderer
				.pos(x + width, y, 0.0D)
				.tex(uMax, vMin).endVertex();
			worldrenderer
				.pos(x, y, 0.0D)
				.tex(uMin, vMin).endVertex();
			tessellator.draw();

			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

			GlStateManager.disableBlend();
		}

		public static void drawTexturedRectNoBlend(
			float x,
			float y,
			float width,
			float height,
			float uMin,
			float uMax,
			float vMin,
			float vMax,
			int filter
		) {
			GlStateManager.enableTexture2D();

			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, filter);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, filter);

			Tessellator tessellator = Tessellator.getInstance();
			WorldRenderer worldrenderer = tessellator.getWorldRenderer();
			worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
			worldrenderer
				.pos(x, y + height, 0.0D)
				.tex(uMin, vMax).endVertex();
			worldrenderer
				.pos(x + width, y + height, 0.0D)
				.tex(uMax, vMax).endVertex();
			worldrenderer
				.pos(x + width, y, 0.0D)
				.tex(uMax, vMin).endVertex();
			worldrenderer
				.pos(x, y, 0.0D)
				.tex(uMin, vMin).endVertex();
			tessellator.draw();

			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		}
	
	private static final ResourceLocation FISHING_WARNING_EXCLAM = new ResourceLocation(
		"notenoughupdates:fishing_warning_exclam.png");

	public void onRenderBobber(EntityFishHook hook) {
		if (Minecraft.getMinecraft().thePlayer.fishEntity == hook && warningState != PlayerWarningState.NOTHING) {

			if (incomingFishWarning &&
				warningState == PlayerWarningState.FISH_INCOMING)
				return;
			if (incomingFishWarningR &&
				warningState == PlayerWarningState.FISH_HOOKED)
				return;

			GlStateManager.disableCull();
			GlStateManager.disableLighting();
			GL11.glDepthFunc(GL11.GL_ALWAYS);
			GlStateManager.scale(1, -1, 1);

			float offset = warningState == PlayerWarningState.FISH_HOOKED ? 0.5f : 0f;

			float centerOffset = 0.5f / 8f;
			Minecraft.getMinecraft().getTextureManager().bindTexture(FISHING_WARNING_EXCLAM);
			drawTexturedRect(
				centerOffset - 4f / 8f,
				-20 / 8f,
				1f,
				2f,
				0 + offset,
				0.5f + offset,
				0,
				1,
				GL11.GL_NEAREST
			);

			GlStateManager.scale(1, -1, 1);
			GL11.glDepthFunc(GL11.GL_LEQUAL);
			GlStateManager.enableLighting();
			GlStateManager.enableCull();
		}
	}

	public void addEntity(int entityId, Entity entity) {
		if (entity instanceof EntityFishHook) {
			hookEntities.put(entityId, (EntityFishHook) entity);

			if (((EntityFishHook) entity).angler == Minecraft.getMinecraft().thePlayer) {
				long currentTime = System.currentTimeMillis();
				long delay = currentTime - lastCastRodMillis;
				if (delay > 0 && delay < 500) {
					if (delay > 300) delay = 300;
					pingDelayList.add(0, (int) delay);
				}
			}
		}
	}

	public void removeEntity(int entityId) {
		hookEntities.remove(entityId);
	}

	@SubscribeEvent
	public void onWorldUnload(WorldEvent.Unload event) {
		hookEntities.clear();
		chains.clear();
	}

	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR &&
			event.entityPlayer == Minecraft.getMinecraft().thePlayer) {

			ItemStack heldItem = event.entityPlayer.getHeldItem();

			if (heldItem != null && heldItem.getItem() == Items.fishing_rod) {
				long currentTime = System.currentTimeMillis();
				if (currentTime - lastCastRodMillis > 500) {
					lastCastRodMillis = currentTime;
				}
			}
		}
	}

	private int tickCounter = 0;

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event) {
		if (Minecraft.getMinecraft().thePlayer != null && event.phase == TickEvent.Phase.END) {
			if (buildupSoundDelay > 0) buildupSoundDelay--;

			if (incomingFishWarning ||
				incomingFishWarningR) {
				if (Minecraft.getMinecraft().thePlayer.fishEntity != null) {
					if (!pingDelayList.isEmpty()) {
						while (pingDelayList.size() > 5) pingDelayList.remove(pingDelayList.size() - 1);

						int totalMS = 0;
						for (int delay : pingDelayList) {
							totalMS += delay;
						}

						int averageMS = totalMS / pingDelayList.size();
						pingDelayTicks = (int) Math.floor(averageMS / 50f);
					}
				}

				if (hookedWarningStateTicks > 0) {
					hookedWarningStateTicks--;
					warningState = PlayerWarningState.FISH_HOOKED;
				} else {
					warningState = PlayerWarningState.NOTHING;
					if (Minecraft.getMinecraft().thePlayer.fishEntity != null) {
						int fishEntityId = Minecraft.getMinecraft().thePlayer.fishEntity.getEntityId();
						for (Map.Entry<WakeChain, List<Integer>> entry : chains.entrySet()) {
							if (entry.getKey().particleNum >= 3 && entry.getValue().contains(fishEntityId)) {
								warningState = PlayerWarningState.FISH_INCOMING;
								break;
							}
						}
					}
				}
			}

			if (tickCounter++ >= 20) {
				long currentTime = System.currentTimeMillis();
				tickCounter = 0;

				Set<Integer> toRemoveEnt = new HashSet<>();
				for (Map.Entry<Integer, EntityFishHook> entry : hookEntities.entrySet()) {
					if (entry.getValue().isDead) {
						toRemoveEnt.add(entry.getKey());
					}
				}
				hookEntities.keySet().removeAll(toRemoveEnt);

				Set<WakeChain> toRemoveChain = new HashSet<>();
				for (Map.Entry<WakeChain, List<Integer>> entry : chains.entrySet()) {
					if (currentTime - entry.getKey().lastUpdate > 200 ||
						entry.getValue().isEmpty() ||
						Collections.disjoint(entry.getValue(), hookEntities.keySet())) {
						toRemoveChain.add(entry.getKey());
					}
				}
				chains.keySet().removeAll(toRemoveChain);
			}
		}
	}

	private double calculateAngleFromOffsets(double xOffset, double zOffset) {
		double angleX = Math.toDegrees(Math.acos(xOffset / 0.04f));
		double angleZ = Math.toDegrees(Math.asin(zOffset / 0.04f));

		if (xOffset < 0) {
			angleZ = 180 - angleZ;
		}
		if (zOffset < 0) {
			angleX = 360 - angleX;
		}

		angleX %= 360;
		angleZ %= 360;
		if (angleX < 0) angleX += 360;
		if (angleZ < 0) angleZ += 360;

		double dist = angleX - angleZ;
		if (dist < -180) dist += 360;
		if (dist > 180) dist -= 360;

		return angleZ + dist / 2;
	}

	private boolean checkAngleWithinRange(double angle1, double angle2, double range) {
		double dist = Math.abs(angle1 - angle2);
		if (dist > 180) dist = 360 - dist;

		return dist <= range;
	}

	private enum HookPossibleRet {
		NOT_POSSIBLE,
		EITHER,
		ANGLE1,
		ANGLE2
	}

	private HookPossibleRet isHookPossible(
		EntityFishHook hook,
		double particleX,
		double particleY,
		double particleZ,
		double angle1,
		double angle2
	) {
		double dY = particleY - hook.posY;
		if (Math.abs(dY) > 0.5f) {
			return HookPossibleRet.NOT_POSSIBLE;
		}

		double dX = particleX - hook.posX;
		double dZ = particleZ - hook.posZ;
		double dist = Math.sqrt(dX * dX + dZ * dZ);

		if (dist < 0.2) {
			return HookPossibleRet.EITHER;
		} else {
			float angleAllowance = (float) Math.toDegrees(Math.atan2(0.03125f, dist)) * 1.5f;
			float angleHook = (float) Math.toDegrees(Math.atan2(dX, dZ));
			angleHook %= 360;
			if (angleHook < 0) angleHook += 360;

			if (checkAngleWithinRange(angle1, angleHook, angleAllowance)) {
				return HookPossibleRet.ANGLE1;
			} else if (checkAngleWithinRange(angle2, angleHook, angleAllowance)) {
				return HookPossibleRet.ANGLE2;
			}
		}
		return HookPossibleRet.NOT_POSSIBLE;
	}

	public static EnumParticleTypes type = EnumParticleTypes.BARRIER;

	private static final float ZERO_PITCH = 1.0f;
	private static final float MAX_PITCH = 0.1f;
	private static final float MAX_DISTANCE = 5f;

	private float calculatePitchFromDistance(float d) {
		if (d < 0.1f) d = 0.1f;
		if (d > MAX_DISTANCE) d = MAX_DISTANCE;

		return 1 / (d + (1 / (ZERO_PITCH - MAX_PITCH))) * (1 - d / MAX_DISTANCE) + MAX_PITCH;
	}

	public boolean onSpawnParticle(
		EnumParticleTypes particleType,
		double x,
		double y,
		double z,
		double xOffset,
		double yOffset,
		double zOffset
	) {

		
		if (hookEntities.isEmpty()) {
			return false;
		}

		if ((particleType == EnumParticleTypes.WATER_WAKE || particleType == EnumParticleTypes.SMOKE_NORMAL) && Math.abs(
			yOffset - 0.01f) < 0.001f) {
			double angle1 = calculateAngleFromOffsets(xOffset, -zOffset);
			double angle2 = calculateAngleFromOffsets(-xOffset, zOffset);

			final List<Integer> possibleHooks1 = new ArrayList<>();
			final List<Integer> possibleHooks2 = new ArrayList<>();

			for (EntityFishHook hook : hookEntities.values()) {
				if (hook.isDead) continue;
				if (possibleHooks1.contains(hook.getEntityId())) continue;
				if (possibleHooks2.contains(hook.getEntityId())) continue;

				HookPossibleRet ret = isHookPossible(hook, x, y, z, angle1, angle2);
				if (ret == HookPossibleRet.ANGLE1) {
					possibleHooks1.add(hook.getEntityId());
				} else if (ret == HookPossibleRet.ANGLE2) {
					possibleHooks2.add(hook.getEntityId());
				} else if (ret == HookPossibleRet.EITHER) {
					possibleHooks1.add(hook.getEntityId());
					possibleHooks2.add(hook.getEntityId());
				}
			}

			if (!possibleHooks1.isEmpty() || !possibleHooks2.isEmpty()) {
				long currentTime = System.currentTimeMillis();

				boolean isMainPlayer = false;

				boolean foundChain = false;
				for (Map.Entry<WakeChain, List<Integer>> entry : chains.entrySet()) {
					WakeChain chain = entry.getKey();

					if (currentTime - chain.lastUpdate > 200) continue;

					double updateAngle;
					List<Integer> possibleHooks;
					if (checkAngleWithinRange(chain.currentAngle, angle1, 16)) {
						possibleHooks = possibleHooks1;
						updateAngle = angle1;
					} else if (checkAngleWithinRange(chain.currentAngle, angle2, 16)) {
						possibleHooks = possibleHooks2;
						updateAngle = angle2;
					} else {
						continue;
					}

					if (!Collections.disjoint(entry.getValue(), possibleHooks)) {
						HashSet<Integer> newHooks = new HashSet<>();

						for (int hookEntityId : possibleHooks) {
							if (entry.getValue().contains(hookEntityId) && chain.distances.containsKey(hookEntityId)) {
								EntityFishHook entity = hookEntities.get(hookEntityId);

								if (entity != null && !entity.isDead) {
									double oldDistance = chain.distances.get(hookEntityId);

									double dX = entity.posX - x;
									double dZ = entity.posZ - z;
									double newDistance = Math.sqrt(dX * dX + dZ * dZ);

									double delta = oldDistance - newDistance;

									if (newDistance < 0.2 || (delta > -0.1 && delta < 0.3)) {
										if ((incomingFishWarning ||
											incomingFishWarningR) &&
											Minecraft.getMinecraft().thePlayer.fishEntity != null &&
											Minecraft.getMinecraft().thePlayer.fishEntity.getEntityId() == hookEntityId &&
											chain.particleNum > 3) {
											float lavaOffset = 0.1f;
											if (particleType == EnumParticleTypes.SMOKE_NORMAL) {
												lavaOffset = 0.03f;
											} else if (particleType == EnumParticleTypes.WATER_WAKE) {
												lavaOffset = 0.1f;
											}
											if (newDistance <= 0.2f + lavaOffset * pingDelayTicks &&
												incomingFishWarningR) {
												if (incomingFishHookedSounds &&
													hookedWarningStateTicks <= 0) {
													rightClick();
													Recast = true;
													float vol = incomingFishHookedSoundsVol / 100f;
													if (vol > 0) {
														if (vol > 1) vol = 1;
														final float volF = vol;

														ISound sound = new PositionedSound(new ResourceLocation("note.pling")) {{
															volume = volF;
															pitch = 2f;
															repeat = false;
															repeatDelay = 0;
															attenuationType = ISound.AttenuationType.NONE;
														}};

														float oldLevel = Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.RECORDS);
														Minecraft.getMinecraft().gameSettings.setSoundLevel(SoundCategory.RECORDS, 1);
														Minecraft.getMinecraft().getSoundHandler().playSound(sound);
														Minecraft.getMinecraft().gameSettings.setSoundLevel(SoundCategory.RECORDS, oldLevel);
													}
												}

												hookedWarningStateTicks = 12;
											} else if (newDistance >= 0.4f + 0.1f * pingDelayTicks &&
												incomingFishWarning) {
												if (incomingFishIncSounds &&
													buildupSoundDelay <= 0) {
													float vol = incomingFishIncSoundsVol / 100f;
													if (vol > 0) {
														if (vol > 1) vol = 1;
														final float volF = vol;

														ISound sound = new PositionedSound(new ResourceLocation("note.pling")) {{
															volume = volF;
															pitch = calculatePitchFromDistance((float) newDistance - (0.3f + 0.1f * pingDelayTicks));
															repeat = false;
															repeatDelay = 0;
															attenuationType = ISound.AttenuationType.NONE;
														}};

														float oldLevel = Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.RECORDS);
														Minecraft.getMinecraft().gameSettings.setSoundLevel(SoundCategory.RECORDS, 1);
														Minecraft.getMinecraft().getSoundHandler().playSound(sound);
														Minecraft.getMinecraft().gameSettings.setSoundLevel(SoundCategory.RECORDS, oldLevel);
														buildupSoundDelay = 4;
													}
												}
											}
										}

										chain.distances.put(hookEntityId, newDistance);
										newHooks.add(hookEntityId);
									}
								}

							}
						}
						if (newHooks.isEmpty()) {
							continue;
						}

						entry.getValue().retainAll(newHooks);
						chain.distances.keySet().retainAll(newHooks);

						for (int i : entry.getValue()) {
							EntityFishHook hook = hookEntities.get(i);
							if (hook != null && hook.angler == Minecraft.getMinecraft().thePlayer) {
								isMainPlayer = true;
								break;
							}
						}

						chain.lastUpdate = currentTime;
						chain.particleNum++;
						chain.currentAngle = updateAngle;

						foundChain = true;
					}
				}

				if (!foundChain) {
					possibleHooks1.removeAll(possibleHooks2);
					if (!possibleHooks1.isEmpty()) {
						for (int i : possibleHooks1) {
							EntityFishHook hook = hookEntities.get(i);
							if (hook != null && hook.angler == Minecraft.getMinecraft().thePlayer) {
								isMainPlayer = true;
								break;
							}
						}

						WakeChain chain = new WakeChain(currentTime, angle1, x, z);
						for (int hookEntityId : possibleHooks1) {
							EntityFishHook entity = hookEntities.get(hookEntityId);

							if (entity != null && !entity.isDead) {
								double dX = entity.posX - x;
								double dZ = entity.posZ - z;
								double newDistance = Math.sqrt(dX * dX + dZ * dZ);
								chain.distances.put(hookEntityId, newDistance);
							}
						}
						chains.put(chain, possibleHooks1);
					} else if (!possibleHooks2.isEmpty()) {
						for (int i : possibleHooks2) {
							EntityFishHook hook = hookEntities.get(i);
							if (hook != null && hook.angler == Minecraft.getMinecraft().thePlayer) {
								isMainPlayer = true;
								break;
							}
						}

						WakeChain chain = new WakeChain(currentTime, angle2, x, z);
						for (int hookEntityId : possibleHooks2) {
							EntityFishHook entity = hookEntities.get(hookEntityId);

							if (entity != null && !entity.isDead) {
								double dX = entity.posX - x;
								double dZ = entity.posZ - z;
								double newDistance = Math.sqrt(dX * dX + dZ * dZ);
								chain.distances.put(hookEntityId, newDistance);
							}
						}
						chains.put(chain, possibleHooks2);
					}
				}

				

				

				return true;
			}
		}

		return false;
	}
}
