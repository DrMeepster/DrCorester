package drmeepster.drcorester.block;

import drmeepster.drcorester.property.PropertyHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BasicBlock extends Block implements IBasicBlock {
	
	//copy these
	//public static final String NAME = "";
		
	protected final String id;
	private BasicItemBlock itemBlock;
	
	public BasicBlock(MapColor color, Material material, String name, CreativeTabs tab, String modid){
		super(material, color);
		setRegistryName(name);
		itemBlock = new BasicItemBlock(this, modid);
		id = name;
		setUnlocalizedName(PropertyHandler.getName(modid, name));
		if(tab != null){
			setCreativeTab(tab);
		}
	}
	
	public BasicBlock(Material material, String name, CreativeTabs tab, String modid){
		this(material.getMaterialMapColor(), material, name, tab, modid);
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
