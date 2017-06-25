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
	
	/**
	 * Please extend.
	 */
	protected BasicBlockFalling(Material material, String name, CreativeTabs tab){
		super(material);
		setRegistryName(name);
		itemBlock = new BasicItemBlock(this);
		setUnlocalizedName(PropertyHandler.INSTANCE.getName(name));
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