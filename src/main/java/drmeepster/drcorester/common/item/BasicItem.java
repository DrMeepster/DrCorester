package drmeepster.drcorester.common.item;

import drmeepster.drcorester.common.property.PropertyHandler;
import drmeepster.drcorester.common.util.interfaces.IBasicObject;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class BasicItem extends Item implements IBasicObject<Item>{
	 
	//copy these
	//public static final String NAME = "";
	
	private String id;
 
	public BasicItem(String name, CreativeTabs tab) {
		id = name;
		setUnlocalizedName(PropertyHandler.INSTANCE.getName(name));
		setRegistryName(name);
		if(tab != null){
			setCreativeTab(tab);
		}
	}
	public BasicItem(String name, String modid){
		this(name, (CreativeTabs)null);
	}

	@Override
	public String getName(){
		return super.getUnlocalizedName();
	}
	
	@Override
	public String getId(){
		return id;
	}
}