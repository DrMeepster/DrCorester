package drmeepster.drcorester.proxy;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

public interface IProxy{
	
	public default void registerItemRenderer(Item item, int meta, ResourceLocation resloc){}
	
	public void preInit();

	public void init();

	public void postInit();
}
