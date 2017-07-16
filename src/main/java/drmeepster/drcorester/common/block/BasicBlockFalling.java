package drmeepster.drcorester.common.block;

import drmeepster.drcorester.common.property.PropertyHandler;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BasicBlockFalling extends BlockFalling implements IBasicBlock {
	
	//copy these
	//public static final String NAME = "";
		
	private String id;
	private BasicItemBlock itemBlock;
	
	public BasicBlockFalling(Material material, String name, CreativeTabs tab){
		super(material);
		setRegistryName(name);
		setUnlocalizedName(PropertyHandler.INSTANCE.getName(name));
		itemBlock = BasicItemBlock.getBasicItemBlock(this);
		if(tab != null){
			setCreativeTab(tab);
		}
	}

	@Override
	public String getName(){
		return super.getUnlocalizedName();
	}
	
	@Override
	public String getId(){
		return id;
	}

	@Override
	public BasicItemBlock getItemBlock() {
		return itemBlock;
	}
}
