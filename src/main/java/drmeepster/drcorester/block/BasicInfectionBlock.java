package drmeepster.drcorester.block;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.function.Function;

import drmeepster.drcorester.util.BlockArea;
import drmeepster.drcorester.util.BlockArea.BlockAreaApplied;
import drmeepster.drcorester.util.TriPredicate;
import drmeepster.drcorester.util.Util;
import drmeepster.drcorester.util.Util.BlockStateWrapper;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

public class BasicInfectionBlock extends BasicBlock {

	public static final BlockArea DEFAULT_RANGE = new BlockArea((byte)1, (byte)1, (byte)3, (byte)1, (byte)1, (byte)1);
	
	public final Map<BlockStateWrapper, BlockStateWrapper> infectMap;
	
	protected TriPredicate<World, BlockPos, BlockPos> condition = Util.INFECTION_ALWAYS;
	protected int infections = 4;
	protected Function<Biome, Biome> infectionBiome = null;
	protected BlockArea range = DEFAULT_RANGE;
	protected Aura[] auraWeaknesses = new Aura[0];
	protected int strength = 0;
	
	public BasicInfectionBlock(Material material, String name, CreativeTabs tab, String modid, BlockStateWrapper[] infectables, BlockStateWrapper[] infectTo){
		this(material.getMaterialMapColor(), material, name, tab, modid, infectables, infectTo);
	}
	
	public BasicInfectionBlock(Material material, String name, CreativeTabs tab, String modid, Map<BlockStateWrapper, BlockStateWrapper> infectMap){
		this(material.getMaterialMapColor(), material, name, tab, modid, infectMap);
	}
	
	public BasicInfectionBlock(MapColor color, Material material, String name, CreativeTabs tab, String modid, BlockStateWrapper[] infectables, BlockStateWrapper[] infectTo){
		this(color, material, name, tab, modid, Util.listsToHashMap(infectables, infectTo));
	}
	
	public BasicInfectionBlock(MapColor color, Material material, String name, CreativeTabs tab, String modid, Map<BlockStateWrapper, BlockStateWrapper> infectMap){
		super(color, material, name, tab, modid);
		this.infectMap = infectMap;
	}
	
	//Util.listsToHashMap(Util.arrayToList(infectables), Util.arrayToList(infectTo));
	//condition != null ? condition : Util.INFECTION_ALWAYS;
	//this.setTickRandomly(true);	
	
	/**
	 * @return the infections
	 */
	public int getInfections() {
		return infections;
	}

	/**
	 * @param infections the infections to set
	 */
	public BasicInfectionBlock setInfections(int infections) {
		if(infections < 0){
			throw new IllegalArgumentException("You cannot have a negative number of infections per random tick!");
		}
		this.infections = infections;
		return this;
	}

	/**
	 * @return the infectionBiome
	 */
	public Function<Biome, Biome> getInfectionBiome() {
		return infectionBiome;
	}

	/**
	 * @param infectionBiome the infectionBiome to set
	 */
	public BasicInfectionBlock setInfectionBiome(Function<Biome, Biome> infectionBiome) {
		this.infectionBiome = infectionBiome;
		return this;
	}

	/**
	 * @return the condition
	 */
	public TriPredicate<World, BlockPos, BlockPos> getCondition() {
		return condition;
	}

	/**
	 * @param condition the condition to set
	 */
	public BasicInfectionBlock setCondition(TriPredicate<World, BlockPos, BlockPos> condition) {
		if(condition == null){
			throw new NullPointerException("\"condition\" cannot be null!");
		}
		this.condition = condition;
		return this;
	}

	/**
	 * @return the range
	 */
	public BlockArea getRange() {
		return range;
	}

	/**
	 * @param range the range to set
	 * @return 
	 */
	public BasicInfectionBlock setRange(BlockArea range) {
		this.range = range;
		return this;
	}

	/**
	 * @return generates a new Aura
	 */
	public Aura getAura() {
		return new Aura(this);
	}

	/**
	 * @return the auraWeaknesses
	 */
	public Aura[] getAuraWeaknesses() {
		return auraWeaknesses;
	}

	/**
	 * @param auraWeaknesses the auraWeaknesses to set
	 * @return 
	 */
	public BasicInfectionBlock setAuraWeaknesses(Aura[] auraWeaknesses){
		if(auraWeaknesses == null){
			auraWeaknesses = new Aura[0];
		}
		this.auraWeaknesses = auraWeaknesses;
		return this;
	}

	/**
	 * @return the strength
	 */
	public int getStrength() {
		return strength;
	}

	/**
	 * @param strength the strength to set
	 * @return 
	 */
	public BasicInfectionBlock setStrength(int strength) {
		this.strength = strength;
		return this;
	}
	
	/**
	 * Called by DrCorester in Init. DO NOT CALL! THIS <b>WILL</b> CAUSE BUGS!
	 * 
	 * @deprecated INTERNAL USE ONLY
	 */
	@Deprecated
	public static final void evaluateAll(){
		for(Block block : Block.REGISTRY){
			if(!(block instanceof BasicInfectionBlock)){
				continue;
			}
			BasicInfectionBlock infblock = (BasicInfectionBlock)block;
			
			for(Entry<BlockStateWrapper, BlockStateWrapper> entry : infblock.infectMap.entrySet()){
				if(!entry.getKey().isEvaluated()){
					entry.getKey().evaluate();
				}
				if(!entry.getValue().isEvaluated()){
					entry.getValue().evaluate();
				}
			}
		}
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random random){
		if(!worldIn.isRemote){
			BlockAreaApplied range = new BlockAreaApplied(this.range, pos);
			for(int i = 0; i < infections; ++i){
				BlockPos blockpos = Util.randomBlockInArea(range, random);
				IBlockState iblockstate = worldIn.getBlockState(blockpos);
				//IBlockState iblockstate1 = worldIn.getBlockState(blockpos.up());
    		
				for(BlockStateWrapper stateWrapper : infectMap.keySet()){
					if(iblockstate ==  stateWrapper.getState() && condition.test(worldIn, pos, blockpos)){
						worldIn.setBlockState(blockpos, stateWrapper.getState());
						break;
					}
				}
    		
				if(condition.test(worldIn, pos, blockpos) && infectionBiome != null){
					Chunk c = worldIn.getChunkFromBlockCoords(pos);
					Chunk cc = worldIn.getChunkFromBlockCoords(blockpos);
    			
					byte[] biomeArray1 = c.getBiomeArray();
					Biome biome1 = Biome.getBiome(biomeArray1[(pos.getZ() & 15) << 4 | (pos.getX() & 15)]);
					biomeArray1[(pos.getZ() & 15) << 4 | (pos.getX() & 15)] = (byte)Biome.getIdForBiome(infectionBiome.apply(biome1));
					c.setBiomeArray(biomeArray1);
    			
					byte[] biomeArray2 = cc.getBiomeArray();
					Biome biome2 = Biome.getBiome(biomeArray2[(blockpos.getZ() & 15) << 4 | (blockpos.getX() & 15)]);
					biomeArray2[(blockpos.getZ() & 15) << 4 | (blockpos.getX() & 15)] = (byte)Biome.getIdForBiome(infectionBiome.apply(biome2));
					cc.setBiomeArray(biomeArray2);
				}
			}
        }
	}
	
	public static final HashSet<BlockPos> findInfectionBlocksInRange(BlockPos orgin, World world){
		BlockAreaApplied area = new BlockAreaApplied(BlockArea.MAX_AREA, orgin);
		final BlockPos POSITION = new BlockPos(area.bound(EnumFacing.WEST), area.bound(EnumFacing.DOWN), area.bound(EnumFacing.NORTH));
		BlockPos posZ = POSITION;
		BlockPos posY = POSITION;
		BlockPos posX = POSITION;
		
		HashSet<BlockPos> out = new HashSet<>();
		for(int z = 0; z < area.getArea().length(Axis.Z); z++){
			for(int y = 0; y < area.getArea().length(Axis.Y) ; y++){
				for(int x = 0; x < area.getArea().length(Axis.X); x++){
					if(world.getBlockState(posX).getBlock() instanceof BasicInfectionBlock && !posX.equals(orgin)){
						out.add(posX);
					}
					posX = posX.east();
				}
				posY = posY.up();
				posX = posY;
			}
			posZ = posZ.south();
			posY = posZ;
			posX = posZ;
		}
		return out;
	}
	
	public final static class Aura{
		
		public Aura(BasicInfectionBlock infBlock){
			this(infBlock, false);
		}
		
		public Aura(BasicInfectionBlock infBlock, boolean hijack){
			this(infBlock.infectMap, infBlock.infectionBiome, infBlock.getId(), hijack);
		}
		
		public Aura(Map<BlockStateWrapper, BlockStateWrapper> infectMap, Function<Biome, Biome> infectionBiome, String name){
			this(infectMap, infectionBiome, name, false);
		}
		
		public Aura(Map<BlockStateWrapper, BlockStateWrapper> infectMap, Function<Biome, Biome> infectionBiome, String name, boolean hijack){
			this.infectMap = infectMap;
			this.infectionBiome = infectionBiome;
			this.name = name;
			this.hijack = hijack;
		}
		
		public final Map<BlockStateWrapper, BlockStateWrapper> infectMap;
		public final Function<Biome, Biome> infectionBiome;
		
		/**
		 * IS NOT USED IN {@link #equals(Object)} OR {@link #hashCode()}
		 */
		public final String name;
		
		/**
		 * IS NOT USED IN {@link #equals(Object)} OR {@link #hashCode()}
		 */
		public final boolean hijack;	
		
		@Override
		public String toString() {
			return "Aura [infectMap=" + infectMap + ", infectionBiome=" + infectionBiome + ", name=" + name + "]";
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((infectMap == null) ? 0 : infectMap.hashCode());
			result = prime * result + ((infectionBiome == null) ? 0 : infectionBiome.hashCode());
			return result;
		}
		
		@Override
		public boolean equals(Object obj){
			if(this == obj)
				return true;
			if(obj == null)
				return false;
			if(getClass() != obj.getClass())
				return false;
			Aura other = (Aura)obj;
			if(infectMap == null){
				if(other.infectMap != null)
					return false;
			}else if(!infectMap.equals(other.infectMap))
				return false;
			if(infectionBiome == null){
				if(other.infectionBiome != null)
					return false;
			}else if(infectionBiome.equals(other.infectionBiome))
				return false;
			return true;
		}
		
		public static enum AuraIntersection{
			NONE,
			REDUNDANCY,
			CONFLICTION;
		}
	}
}
