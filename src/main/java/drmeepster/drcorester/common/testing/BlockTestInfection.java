package drmeepster.drcorester.common.testing;

import java.util.Random;

import drmeepster.drcorester.common.block.BasicInfectionBlock;
import drmeepster.drcorester.common.util.BlockStateWrapper;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockTestInfection extends BasicInfectionBlock {

	public static final String NAME = "test_infection";
	public static final BlockStateWrapper[] INFECTABLES = {new BlockStateWrapper(Blocks.STONE.getDefaultState()), new BlockStateWrapper(Blocks.DIRT.getDefaultState()), new BlockStateWrapper(Blocks.GRAVEL.getDefaultState()), new BlockStateWrapper(Blocks.GRASS.getDefaultState())};
	public static final BlockStateWrapper[] INFECT_TO = {new BlockStateWrapper(new ResourceLocation("drcorester", NAME)), new BlockStateWrapper(new ResourceLocation("drcorester", NAME)), new BlockStateWrapper(new ResourceLocation("drcorester", NAME)), new BlockStateWrapper(new ResourceLocation("drcorester", NAME))};
	
	public BlockTestInfection(){
		super(Material.CLAY, NAME, TestMain.tabTesting, INFECTABLES, INFECT_TO, null);
	}
}
