package drmeepster.drcorester.potion;

import drmeepster.drcorester.util.IBasicObject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class BasicPotion extends Potion implements IBasicObject<Potion>{
	
	//copy these
	//public static final String NAME = "";
	//public static final int COLOR = 0x000000;
	//public static final ResourceLocation RES_LOC = new ResourceLocation("bables:textures/potion/");
	
	private ResourceLocation icon;
	private String id;

	protected BasicPotion(boolean isBad, int color, String name, ResourceLocation icon, String modid) {
		super(true, color);
		this.setPotionName("effect." + name);
		this.setRegistryName(modid, name);
		this.setIconIndex(0, 0);
		this.icon = icon;
		id = name;
	}
	
	@Override
	public void performEffect(EntityLivingBase entity, int amplifier){
		this.effect(entity, amplifier);
	}
	
	protected abstract void effect(EntityLivingBase entity, int amplifier);
	
	@Override
	public boolean isReady(int duration, int amplifier){
		return true;
	}
	
	@Override
	public boolean hasStatusIcon(){
		return false;
	}
	
	/**
	 * Called to draw the this Potion onto the player's inventory when it's active.
	 * This can be used to e.g. render Potion icons from your own texture.
	 *
	 * @param x      the x coordinate
	 * @param y      the y coordinate
	 * @param effect the active PotionEffect
	 * @param mc     the Minecraft instance, for convenience
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
		if (mc.currentScreen != null) {
			mc.getTextureManager().bindTexture(icon);
			Gui.drawModalRectWithCustomSizedTexture(x + 6, y + 7, 0, 0, 18, 18, 18, 18);
		}
	}
	
	/**
	 * Called to draw the this Potion onto the player's ingame HUD when it's active.
	 * This can be used to e.g. render Potion icons from your own texture.
	 *
	 * @param x      the x coordinate
	 * @param y      the y coordinate
	 * @param effect the active PotionEffect
	 * @param mc     the Minecraft instance, for convenience
	 * @param alpha  the alpha value, blinks when the potion is about to run out
	 */
	@SideOnly(Side.CLIENT)
    @Override
    public void renderHUDEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc, float alpha){
    	mc.getTextureManager().bindTexture(icon);
		Gui.drawModalRectWithCustomSizedTexture(x + 3, y + 3, 0, 0, 18, 18, 18, 18);
    }
	
	@Override
	public String getId(){
		return id;
	}
}
