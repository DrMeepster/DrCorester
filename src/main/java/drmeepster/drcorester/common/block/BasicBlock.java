package drmeepster.drcorester.common.block;

import drmeepster.drcorester.common.property.PropertyHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BasicBlock extends Block implements IBasicBlock {
	
	//copy these
	//public static final String NAME = "";
		
	protected final String id;
	private BasicItemBlock itemBlock;
	
	public BasicBlock(MapColor color, Material material, String name, CreativeTabs tab){
		super(material, color);
		setRegistryName(name);
		itemBlock = new BasicItemBlock(this);
		id = name;
		setUnlocalizedName(PropertyHandler.INSTANCE.getName(name));
		if(tab != null){
			setCreativeTab(tab);
		}
	}
	
	public BasicBlock(Material material, String name, CreativeTabs tab){
		this(material.getMaterialMapColor(), material, name, tab);
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
