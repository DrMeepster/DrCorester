package drmeepster.drcorester.proxy;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy implements IProxy{
	
	@Override
	public void registerItemRenderer(Item item, int meta, ResourceLocation resloc) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(resloc.toString(), "inventory"));
	}

	@Override
	public void preInit() {
		
	}

	@Override
	public void init() {
		
	}

	@Override
	public void postInit() {
		
	}
}