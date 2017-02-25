package drmeepster.drcorester.testing;

import drmeepster.drcorester.item.BasicItem;
import net.minecraft.creativetab.CreativeTabs;

import static drmeepster.drcorester.util.Util.register;

import drmeepster.drcorester.ModDrCorester;

public class TestMain{
	
	public static BasicItem itemTest;
	public static ItemNBTTest itemNbtTest;
	
	public static void init(){
		itemTest = register(new BasicItem("test", CreativeTabs.MISC, ModDrCorester.MODID));
		itemNbtTest = register(new ItemNBTTest());
	}
}
