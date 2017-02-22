package test;

import drmeepster.drcorester.ModDrCorester;
import drmeepster.drcorester.item.BasicItem;
import drmeepster.drcorester.property.PropertyHandler;
import drmeepster.drcorester.util.Util;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "test")
public class ModTest {
	
	@EventHandler
	public static void preInit(FMLPreInitializationEvent event){
		PropertyHandler.setProperty("test", "name", "%1%2%1%2%1%2%1%2%1%2%1%2");
		
		BasicItem item = new BasicItem("test", CreativeTabs.BUILDING_BLOCKS, "test");
		Util.register(item);
	}
}
