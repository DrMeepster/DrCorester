package test;

import drmeepster.drcorester.item.BasicItem;
import drmeepster.drcorester.util.Util;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "test")
public class ModTest {
	
	@EventHandler
	public static void preInit(FMLPreInitializationEvent event){
		BasicItem item = new BasicItem("test", CreativeTabs.BUILDING_BLOCKS, "test");
		Util.register(item);
	}
}
