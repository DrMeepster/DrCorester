package drmeepster.drcorester.testing;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class TabTesting extends CreativeTabs {

	public static final String NAME = "drcorester";
	
	public TabTesting() {
		super(NAME);
	}

	@Override
	public Item getTabIconItem() {
		return TestMain.itemNbtTest;
	}

}
