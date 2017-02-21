package drmeepster.drcorester.block;

import drmeepster.drcorester.util.IBasicObject;
import net.minecraft.block.Block;

public interface IBasicBlock extends IBasicObject<Block> {
	public BasicItemBlock getItemBlock();
}
