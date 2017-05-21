package drmeepster.drcorester.proxy;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public interface IProxy{
	
	public default void registerItemRenderer(Item item, int meta, ResourceLocation resloc){}
	
	public void preInit();

	public void init();

	public void postInit();
}
