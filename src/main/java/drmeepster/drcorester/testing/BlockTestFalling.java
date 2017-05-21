package drmeepster.drcorester.testing;

import drmeepster.drcorester.block.BasicBlockFalling;
import net.minecraft.block.material.Material;

public class BlockTestFalling extends BasicBlockFalling {
	
	public static final String NAME = "test_falling";
	
	public BlockTestFalling() {
		super(Material.CLOTH, NAME, null, "drcorester");
	}

}
