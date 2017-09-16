package drmeepster.drcorester.common.block;

import drmeepster.drcorester.common.util.interfaces.IBasicObject;
import net.minecraft.block.Block;

public interface IBasicBlock extends IBasicObject<Block> {
	public BasicItemBlock getItemBlock();
}
