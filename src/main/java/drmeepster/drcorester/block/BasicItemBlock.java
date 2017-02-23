package drmeepster.drcorester.block;

import drmeepster.drcorester.property.PropertyHandler;
import drmeepster.drcorester.util.IBasicObject;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class BasicItemBlock extends ItemBlock implements IBasicObject<Item> {
	
	public final IBasicBlock block;
	
	/**
	 * @param block Must be block!
	 */
	protected BasicItemBlock(IBasicBlock block, String modid) {
		super((Block)block);
		this.block = block;
		this.setRegistryName(block.getRegistryName());
		this.setUnlocalizedName(PropertyHandler.getName(modid, block.getId()));
	}

	@Override
	public String getName() {
		return this.getUnlocalizedName();
	}

	@Override
	public String getId() {
		return block.getId();
	}
	
	/**
	 * For error handling reasons.
	 * @param block Must be a block!
	 */
	public static BasicItemBlock getBasicItemBlock(IBasicBlock block, String modid){
		if(!(block instanceof Block)){
			throw new IllegalArgumentException("Argument \"block\" must be a block!");
		}
		return new BasicItemBlock(block, modid);
	}

}
