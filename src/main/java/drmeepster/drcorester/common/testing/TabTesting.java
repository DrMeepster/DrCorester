package drmeepster.drcorester.common.testing;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TabTesting extends CreativeTabs {

	public static final String NAME = "drcorester";
	
	public TabTesting() {
		super(NAME);
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(TestMain.itemNbtTest);
	}

}
