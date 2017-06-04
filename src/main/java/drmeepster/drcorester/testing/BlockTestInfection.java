package drmeepster.drcorester.testing;

import drmeepster.drcorester.block.BasicInfectionBlock;
import drmeepster.drcorester.util.Util.BlockStateWrapper;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;

public class BlockTestInfection extends BasicInfectionBlock {

	public static final String NAME = "test_infection";
	public static final BlockStateWrapper[] INFECTABLES = {new BlockStateWrapper(Blocks.STONE.getDefaultState()), new BlockStateWrapper(Blocks.DIRT.getDefaultState()), new BlockStateWrapper(Blocks.GRAVEL.getDefaultState()), new BlockStateWrapper(Blocks.GRASS.getDefaultState())};
	public static final BlockStateWrapper[] INFECT_TO = {new BlockStateWrapper(new ResourceLocation("drcorester", NAME)), new BlockStateWrapper(new ResourceLocation("drcorester", NAME)), new BlockStateWrapper(new ResourceLocation("drcorester", NAME)), new BlockStateWrapper(new ResourceLocation("drcorester", NAME))};
	
	public BlockTestInfection(){
		super(Material.CLAY, NAME, TestMain.TAB_TESTING, "drcorester", INFECTABLES, INFECT_TO);
	}
}
