package drmeepster.drcorester.common.block;

import drmeepster.drcorester.common.util.IBasicObject;
import net.minecraft.block.Block;

public interface IBasicBlock extends IBasicObject<Block> {
	public BasicItemBlock getItemBlock();
}
