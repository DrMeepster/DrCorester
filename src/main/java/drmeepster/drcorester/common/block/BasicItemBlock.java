package drmeepster.drcorester.common.block;

import drmeepster.drcorester.common.util.interfaces.IBasicObject;
import drmeepster.drcorester.common.util.interfaces.InvalidInterfaceTypeException;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class BasicItemBlock extends ItemBlock implements IBasicObject<Item> {
	
	public final IBasicBlock block;
	
	/**
	 * @param block Must be block!
	 */
	protected BasicItemBlock(IBasicBlock block) {
		super((Block)block);
		this.block = block;
		this.setRegistryName(block.getRegistryName());
		this.setUnlocalizedName(((Block)block).getUnlocalizedName());
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
	public static BasicItemBlock getBasicItemBlock(IBasicBlock block){
		if(!(block instanceof Block)){
			throw new InvalidInterfaceTypeException("An IBasicBlock should extend Block!");
		}
		return new BasicItemBlock(block);
	}

}
