package drmeepster.drcorester.proxy;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public interface IProxy{
	
	public default void registerItemRenderer(Item item, int meta, ResourceLocation resloc){}
	public default <T extends Entity> void registerEntityRenderFactory(Class<T> clazz, IRenderFactory<? super T> factory){}
	
	public void preInit();

	public void init();

	public void postInit();
}
