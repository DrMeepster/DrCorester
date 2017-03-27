package drmeepster.drcorester.potion;

import drmeepster.drcorester.property.PropertyHandler;
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
	
	private ResourceLocation icon;
	private String id;

	protected BasicPotion(boolean isBad, int color, String name, String modid) {
		super(true, color);
		this.setPotionName("effect." + PropertyHandler.getName(modid, name));
		this.setRegistryName(modid, name);
		this.setIconIndex(0, 0);
		icon = new ResourceLocation(modid, "textures/potion/" + name + ".png");
		id = name;
	}
	
	@Override
	public boolean isReady(int duration, int amplifier){
		return true;
	}
	
	@Override
	public boolean hasStatusIcon(){
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
		if(mc.currentScreen != null) {
			mc.getTextureManager().bindTexture(icon);
			Gui.drawModalRectWithCustomSizedTexture(x + 6, y + 7, 0, 0, 18, 18, 18, 18);
		}
	}
	
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
	
	/* Copy this:
	 * public void performEffect(EntityLivingBase entity, int amplifier){
	 */
}