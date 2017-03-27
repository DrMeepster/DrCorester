package drmeepster.drcorester.block;

import drmeepster.drcorester.property.PropertyHandler;
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
	protected BasicBlockFalling(Material material, String name, CreativeTabs tab, String modid){
		super(material);
		setRegistryName(name);
		itemBlock = new BasicItemBlock(this, modid);
		setUnlocalizedName(PropertyHandler.getName(modid, name));
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
