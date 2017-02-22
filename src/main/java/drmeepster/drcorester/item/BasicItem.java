package drmeepster.drcorester.item;

import drmeepster.drcorester.ModDrCorester;
import drmeepster.drcorester.property.PropertyHandler;
import drmeepster.drcorester.util.IBasicObject;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class BasicItem extends Item implements IBasicObject<Item>{
	 
	//copy these
	//public static final String NAME = "";
	
	private String id;
 
	public BasicItem(String name, CreativeTabs tab, String modid) {
		id = name;
		setUnlocalizedName(PropertyHandler.getName(modid, name));
		setRegistryName(name);
		if(tab != null){
			setCreativeTab(tab);
		}
	}
	public BasicItem(String name, String modid){
		this(name, null, modid);
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