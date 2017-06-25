package drmeepster.drcorester.common.block;

import static drmeepster.drcorester.common.block.BasicInfectionBlock.Aura.AuraIntersection.CONFLICTION;
import static drmeepster.drcorester.common.block.BasicInfectionBlock.Aura.AuraIntersection.NONE;
import static drmeepster.drcorester.common.block.BasicInfectionBlock.Aura.AuraIntersection.REDUNDANCY;
import static drmeepster.drcorester.common.block.BasicInfectionBlock.InfUtil.IntersectionEdge.DEFAULT;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import drmeepster.drcorester.ModDrCorester;
import drmeepster.drcorester.common.block.BasicInfectionBlock.Aura.AuraIntersection;
import drmeepster.drcorester.common.block.BasicInfectionBlock.InfUtil.IntersectionEdge;
import drmeepster.drcorester.common.block.BasicInfectionBlock.InfUtil.IntersectionEdge.Type;
import drmeepster.drcorester.common.util.BlockArea;
import drmeepster.drcorester.common.util.BlockArea.BlockAreaApplied;
import drmeepster.drcorester.common.util.BlockStateWrapper;
import drmeepster.drcorester.common.util.TriPredicate;
import drmeepster.drcorester.common.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState;

public class BasicInfectionBlock extends BasicBlock{//BlockGrass

	public static final BlockArea DEFAULT_RANGE = new BlockArea((byte)1, (byte)1, (byte)3, (byte)1, (byte)1, (byte)1);
	
	protected TriPredicate<World, BlockPos, BlockPos> condition = Util.INFECTION_ALWAYS;
	protected int infections;
	protected Aura aura;
	protected BlockArea range;
	protected Aura[] auraWeaknesses;
	protected int strength;
	
	//useless currently
	protected IntersectionEdge edge;
	protected boolean hijacker = false;
	protected boolean hijackable = false;

	public BasicInfectionBlock(MapColor color, Material material, String name, CreativeTabs tab, TriPredicate<World, BlockPos, BlockPos> condition, int infections, Map<BlockStateWrapper, BlockStateWrapper> infectMap, Map<ResourceLocation, ResourceLocation> infectionBiome, BlockArea range, Aura[] auraWeaknesses, int strength, IntersectionEdge edge, boolean hijacker, boolean hijackable){
		super(color, material, name, tab);
		this.condition = condition;
		this.infections = infections;
		this.range = range;
		this.auraWeaknesses = auraWeaknesses;
		this.strength = strength;
		this.edge = edge;
		this.hijacker = hijacker;
		this.hijackable = hijackable;
		aura = new Aura(infectMap, null, name + (hasMultipleAuras() ? ":default" : ""));
		
		this.setTickRandomly(true);
	}
	
	public BasicInfectionBlock(MapColor color, Material material, String name, CreativeTabs tab, TriPredicate<World, BlockPos, BlockPos> condition, int infections, Map<BlockStateWrapper, BlockStateWrapper> infectMap, Map<ResourceLocation, ResourceLocation> infectionBiome, BlockArea range, Aura[] auraWeaknesses, int strength){
		this(color, material, name, tab, condition, infections, infectMap, infectionBiome, range, auraWeaknesses, strength, DEFAULT, false, false);
	}
	
	public BasicInfectionBlock(MapColor color, Material material, String name, CreativeTabs tab, Map<BlockStateWrapper, BlockStateWrapper> infectMap, Map<ResourceLocation, ResourceLocation> infectionBiome){
		this(color, material, name, tab, Util.INFECTION_ALWAYS, 4, infectMap, infectionBiome, DEFAULT_RANGE, new Aura[0], 0);
	}
	
	public BasicInfectionBlock(Material material, String name, CreativeTabs tab, TriPredicate<World, BlockPos, BlockPos> condition, int infections, Map<BlockStateWrapper, BlockStateWrapper> infectMap, Map<ResourceLocation, ResourceLocation> infectionBiome, BlockArea range, Aura[] auraWeaknesses, int strength, IntersectionEdge edge, boolean hijacker, boolean hijackable){
		this(material.getMaterialMapColor(), material, name, tab, condition, infections, infectMap, infectionBiome, range, auraWeaknesses, strength, edge, hijacker, hijackable);
	}
	
	public BasicInfectionBlock(Material material, String name, CreativeTabs tab, TriPredicate<World, BlockPos, BlockPos> condition, int infections, Map<BlockStateWrapper, BlockStateWrapper> infectMap, Map<ResourceLocation, ResourceLocation> infectionBiome, BlockArea range, Aura[] auraWeaknesses, int strength){
		this(material, name, tab, condition, infections, infectMap, infectionBiome, range, auraWeaknesses, strength, DEFAULT, false, false);
	}
	
	public BasicInfectionBlock(Material material, String name, CreativeTabs tab, Map<BlockStateWrapper, BlockStateWrapper> infectMap, Map<ResourceLocation, ResourceLocation> infectionBiome){
		this(material, name, tab, Util.INFECTION_ALWAYS, 4, infectMap, infectionBiome, DEFAULT_RANGE, new Aura[0], 0);
	}
	
	public BasicInfectionBlock(MapColor color, Material material, String name, CreativeTabs tab, TriPredicate<World, BlockPos, BlockPos> condition, int infections, BlockStateWrapper[] infectables, BlockStateWrapper[] infectTo, Map<ResourceLocation, ResourceLocation> infectionBiome, BlockArea range, Aura[] auraWeaknesses, int strength, IntersectionEdge edge, boolean hijacker, boolean hijackable){
		this(material.getMaterialMapColor(), material, name, tab, condition, infections, Util.arraysToHashMap(infectables, infectTo), infectionBiome, range, auraWeaknesses, strength, edge, hijacker, hijackable);
	}
	
	public BasicInfectionBlock(MapColor color, Material material, String name, CreativeTabs tab, TriPredicate<World, BlockPos, BlockPos> condition, int infections, BlockStateWrapper[] infectables, BlockStateWrapper[] infectTo, Map<ResourceLocation, ResourceLocation> infectionBiome, BlockArea range, Aura[] auraWeaknesses, int strength){
		this(color, material, name, tab, condition, infections, Util.arraysToHashMap(infectables, infectTo), infectionBiome, range, auraWeaknesses, strength, DEFAULT, false, false);
	}
	
	public BasicInfectionBlock(MapColor color, Material material, String name, CreativeTabs tab, BlockStateWrapper[] infectables, BlockStateWrapper[] infectTo, Map<ResourceLocation, ResourceLocation> infectionBiome){
		this(color, material, name, tab, Util.INFECTION_ALWAYS, 4, Util.arraysToHashMap(infectables, infectTo), infectionBiome, DEFAULT_RANGE, new Aura[0], 0);
	}
	
	public BasicInfectionBlock(Material material, String name, CreativeTabs tab, TriPredicate<World, BlockPos, BlockPos> condition, int infections, BlockStateWrapper[] infectables, BlockStateWrapper[] infectTo, Map<ResourceLocation, ResourceLocation> infectionBiome, BlockArea range, Aura[] auraWeaknesses, int strength, IntersectionEdge edge, boolean hijacker, boolean hijackable){
		this(material.getMaterialMapColor(), material, name, tab, condition, infections, Util.arraysToHashMap(infectables, infectTo), infectionBiome, range, auraWeaknesses, strength, edge, hijacker, hijackable);
	}
	
	public BasicInfectionBlock(Material material, String name, CreativeTabs tab, TriPredicate<World, BlockPos, BlockPos> condition, int infections, BlockStateWrapper[] infectables, BlockStateWrapper[] infectTo, Map<ResourceLocation, ResourceLocation> infectionBiome, BlockArea range, Aura[] auraWeaknesses, int strength){
		this(material, name, tab, condition, infections, Util.arraysToHashMap(infectables, infectTo), infectionBiome, range, auraWeaknesses, strength, DEFAULT, false, false);
	}
	
	public BasicInfectionBlock(Material material, String name, CreativeTabs tab, BlockStateWrapper[] infectables, BlockStateWrapper[] infectTo, Map<ResourceLocation, ResourceLocation> infectionBiome){
		this(material, name, tab, Util.INFECTION_ALWAYS, 4, Util.arraysToHashMap(infectables, infectTo), infectionBiome, DEFAULT_RANGE, new Aura[0], 0);
	}
	
	/**
	 * Override if the <code>BasicInfectionBlock</code> has multiple auras.
	 */
	public boolean hasMultipleAuras(){
		return false;
	}
	
	/**
	 * @return the infections.
	 */
	public int getInfections() {
		return infections;
	}

	/**
	 * @param infections the infections to set.
	 */
	public BasicInfectionBlock setInfections(int infections) {
		if(infections < 0){
			throw new IllegalArgumentException("You cannot have a negative number of infections per random tick!");
		}
		this.infections = infections;
		return this;
	}

	/**
	 * @return the infectionBiome.
	 */
	public Map<ResourceLocation, ResourceLocation> getInfectionBiome() {
		return aura.infectionBiome;
	}

	/**
	 * @param infectionBiome the infectionBiome to set.
	 */
	public BasicInfectionBlock setInfectionBiome(Map<ResourceLocation, ResourceLocation> infectionBiome) {
		aura = new Aura(aura.infectMap, infectionBiome, id);
		return this;
	}

	/**
	 * @return the condition.
	 */
	public TriPredicate<World, BlockPos, BlockPos> getCondition() {
		return condition;
	}

	/**
	 * @param condition the condition to set.
	 */
	public BasicInfectionBlock setCondition(TriPredicate<World, BlockPos, BlockPos> condition) {
		if(condition == null){
			throw new NullPointerException("\"condition\" cannot be null!");
		}
		this.condition = condition;
		return this;
	}

	/**
	 * @return the range.
	 */
	public BlockArea getRange(){
		return range;
	}

	/**
	 * @param range the range to set.
	 */
	public BasicInfectionBlock setRange(BlockArea range){
		this.range = range;
		return this;
	}

	/**
	 * @return the Aura.
	 */
	public Aura getAura(){
		return aura;
	}
	
	public BasicInfectionBlock setAura(Aura aura){
		this.aura = aura;
		return this;
	}
	
	/**
	 * @return The ACTUAL aura.
	 */
	public Aura getActualAura(IBlockAccess world, BlockPos pos){
		return getActualAura(world.getBlockState(pos), world, pos);
	}
	
	/**
	 * @return The ACTUAL aura.
	 */
	@SuppressWarnings({ "deprecation", "unused" })
	public Aura getActualAura(IBlockState state, IBlockAccess world, BlockPos pos){
		BasicInfectionBlock block = this;
		Aura aura = this.getAuraFromState(this.getActualState(state, world, pos));
		double distance = 0;
		
		if(/*hijackable*/false){
			for(Entry<BlockPos, IBlockState> entry : InfUtil.findInfectionBlocksInRange(pos, world).entrySet()){
				BasicInfectionBlock infBlock = (BasicInfectionBlock)entry.getValue().getBlock();
				if((infBlock.strength > block.strength) || (infBlock.strength == block.strength && Util.getDistance(pos, entry.getKey()) < distance)){
					block = infBlock;
					BlockPos pos2 = entry.getKey();
					distance = Util.getDistance(pos, pos2);
					
					Aura a = block.getActualAura(world, pos2);
					aura = a.hijack ? getAura() : a;
				}
			}
		}
		return aura;
	}
	
	/**
	 * @returns An <code>Aura</code> based on <code>state</code>.
	 */
	@Deprecated
	public Aura getAuraFromState(IBlockState state){
		return getAura();
	}
	
	@SuppressWarnings("deprecation")
	public Aura getAuraFromMeta(int meta){
		return getAuraFromState(this.getStateFromMeta(meta));
	}

	/**
	 * @return the auraWeaknesses.
	 */
	public Aura[] getAuraWeaknesses(){
		return auraWeaknesses;
	}

	/**
	 * @param auraWeaknesses the auraWeaknesses to set.
	 */
	public BasicInfectionBlock setAuraWeaknesses(Aura[] auraWeaknesses){
		if(auraWeaknesses == null){
			auraWeaknesses = new Aura[0];
		}
		this.auraWeaknesses = auraWeaknesses;
		return this;
	}

	/**
	 * @return the strength.
	 */
	public int getStrength(){
		return strength;
	}

	/**
	 * @param strength the strength to set.
	 */
	public BasicInfectionBlock setStrength(int strength){
		this.strength = strength;
		return this;
	}
	
	/**
	 * @return The <code>IntersectionEdge</code>.
	 */
	public IntersectionEdge getEdge(){
		return edge;
	}

	/**
	 * @param edge the <code>IntersectionEdge</code> to set.
	 */
	public BasicInfectionBlock setEdge(IntersectionEdge edge){
		this.edge = edge;
		return this;
	}

	public Map<BlockStateWrapper, BlockStateWrapper> getInfectMap(){
		return aura.infectMap;
	}

	/**
	 * @param strength the strength to set.
	 */
	public void setInfectMap(Map<BlockStateWrapper, BlockStateWrapper> infectMap){
		aura = new Aura(infectMap, aura.infectionBiome, id);
	}

	public boolean isHijacker(){
		return hijacker;
	}

	/**
	 * @param strength the strength to set.
	 */
	public void setHijacker(boolean hijacker){
		this.hijacker = hijacker;
	}

	public boolean isHijackable(){
		return hijackable;
	}

	/**
	 * @param strength the strength to set.
	 */
	public void setHijackable(boolean hijackable){
		this.hijackable = hijackable;
	}

	/**
	 * Called by DrCorester in Init. DO NOT CALL! THIS <b>WILL</b> CAUSE BUGS!
	 * 
	 * @deprecated INTERNAL USE ONLY
	 */
	@Deprecated
	public static final void evaluateAll(){
		String modid = Util.getModid();
		if(!modid.equals(ModDrCorester.MODID)){
			ModDrCorester.log.error("The call to BasicInfectionBlock.evaluateAll() is likely not made by Dr Corester but by " + modid + "!");
			if(ModDrCorester.overreact){
				throw new BlockStateWrapper.EvaluationException("The call to BasicInfectionBlock.evaluateAll() is likely not made by Dr Corester but by " + modid + "!");
			}
			return;
		}
		
		for(Block block : Block.REGISTRY){
			if(!(block instanceof BasicInfectionBlock)){
				continue;
			}
			BasicInfectionBlock infblock = (BasicInfectionBlock)block;

			for(IBlockState state : infblock.blockState.getValidStates()){
				for(Entry<BlockStateWrapper, BlockStateWrapper> entry : infblock.getAuraFromState(state).infectMap.entrySet()){
					if(!entry.getKey().isEvaluated()){
						entry.getKey().evaluate();
					}
					if(!entry.getValue().isEvaluated()){
						entry.getValue().evaluate();
					}
				}
			}
			infblock.evaluate();
		}
		ModDrCorester.log.info("All BasicInfectionBlocks in registry evaluated!");
	}
	
	/**
	 * Override to evaluate any other <code>BlockStateWrapper</code>s in the block.
	 */
	private void evaluate(){}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random random){
		if(!world.isRemote){
			long time1 = System.nanoTime();
			BlockAreaApplied range = new BlockAreaApplied(this.range, pos);
			Aura aura = getActualAura(world, pos);
			boolean doBlock = true;
			boolean doBiome = true;
			
			for(int inf = 0; inf < infections; ++inf){
				BlockPos blockpos = Util.randomBlockInArea(range, random);
				System.out.println(blockpos);
				IBlockState iblockstate = world.getBlockState(blockpos);
				
				/*for(IBlockState infState : InfUtil.findInfectionBlocksInRange(pos, world).values()){
					BasicInfectionBlock infBlock = (BasicInfectionBlock)infState.getBlock();
					
					if(isBlocked(infBlock)){
						AuraIntersection[] intersect = aura.getIntersection(infBlock.getAura());
						boolean weaker = strength < infBlock.strength;
						boolean sameStr = strength == infBlock.strength;
						
						doBlock = !(intersect[0] == CONFLICTION && weaker) || (edge.block.equals(Type.FUZZY) && sameStr);
						doBiome = !(intersect[1] == CONFLICTION && weaker) || (edge.biome.equals(Type.FUZZY) && sameStr);
					}
				}*/
				
				if(doBlock){
					for(Entry<BlockStateWrapper, BlockStateWrapper> stateEntry : aura.infectMap.entrySet()){
						try{
							if(iblockstate.equals(stateEntry.getKey().getState()) && condition.test(world, pos, blockpos)){
								world.setBlockState(blockpos, stateEntry.getValue().getState());
								break;
							}
						}catch(IllegalStateException e){
							ModDrCorester.log.error("The infectMap of the aura, \"" + aura.name + "\", of the BasicInfectionBlock, \"" + this.getRegistryName() + "\", is not evaluated!", e);
							if(ModDrCorester.overreact){
								throw new IllegalStateException("The infectMap of the aura, \"" + aura.name.split(":")[1] + "\", of the BasicInfectionBlock, \"" + this.getRegistryName() + "\", is not evaluated!", e);
							}
							break;
						}
					}
				}
    		
				if(condition.test(world, pos, blockpos) && aura.infectionBiome != null && doBiome){				
					BlockPos changePos = pos;
					
					//Iterates twice. Once with changePos = pos and once with changePos = blockpos.
					for(int i = 0; i < 0; i++){
						Chunk c = world.getChunkFromBlockCoords(changePos);
						byte[] biomeArray = c.getBiomeArray();
						Biome biome = Biome.getBiome(biomeArray[(changePos.getZ() & 15) << 4 | (changePos.getX() & 15)]);
						byte biomeId = (byte)Biome.getIdForBiome(Biome.REGISTRY.getObject(aura.infectionBiome.get(biome)));
						biomeArray[(changePos.getZ() & 15) << 4 | (changePos.getX() & 15)] = biomeId == -1 ? (aura.infectionBiome.containsKey(null) ? (byte)Biome.getIdForBiome(Biome.REGISTRY.getObject(aura.infectionBiome.get(null))) : biomeArray[(changePos.getZ() & 15) << 4 | (changePos.getX() & 15)]) : biomeId;
						c.setBiomeArray(biomeArray);
						
						changePos = blockpos;
					}
				}
			}
			//InfUtil.findInfectionBlocksInRange(pos, world);
			long time2 = System.nanoTime();
			System.out.println(time2 - time1);
        }
	}
	
	public final AuraIntersection[] getAuraIntersection(BlockPos pos, World world){
		Aura aura = getAura();
		Collection<IBlockState> states = InfUtil.findInfectionBlocksInRange(pos, world).values();
		
		AuraIntersection block = AuraIntersection.NONE;
		AuraIntersection biome = AuraIntersection.NONE;
		for(IBlockState state : states){
			Aura contest = ((BasicInfectionBlock)state.getBlock()).getAura();
			
			AuraIntersection intersect = aura.getIntersection(contest)[0];
			block = intersect.ordinal() > block.ordinal() ? intersect : block;
			
			intersect = aura.getIntersection(contest)[1];
			biome = intersect.ordinal() > biome.ordinal() ? intersect : biome;
		}
		return new AuraIntersection[] {block, biome};
	}
	
	public final boolean isBlocked(BasicInfectionBlock infBlock){
		for(Aura aura : auraWeaknesses){
			if(aura.equals(infBlock.getAura())){
				return true;
			}
		}
		return infBlock.strength >= strength;
	}
	
	public static final class InfUtil{		
		
		public static HashMap<BlockPos, IBlockState> findInfectionBlocksInRange(BlockPos orgin, IBlockAccess world){
			BlockAreaApplied area = BlockArea.MAX_AREA.apply(orgin);
			final BlockPos POSITION = new BlockPos(area.bound(EnumFacing.WEST), area.bound(EnumFacing.DOWN), area.bound(EnumFacing.NORTH));
			BlockPos posZ = POSITION;
			BlockPos posY = POSITION;
			BlockPos posX = POSITION;
			
			HashMap<BlockPos, IBlockState> out = new HashMap<>();
			for(int z = 0; z < 255; z++){
				for(int y = 0; y < 255 ; y++){
					for(int x = 0; x < 255; x++){
						if(world.getBlockState(posX).getBlock() instanceof BasicInfectionBlock && !posX.equals(area.pos) && (Util.findAllBlocks(((BasicInfectionBlock)world.getBlockState(posX).getBlock()).getRange().apply(posX), world).contains(orgin))){
							out.put(posX, world.getBlockState(posX).getActualState(world, posX));
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
		
		public static enum IntersectionEdge{
			DEFAULT(Type.DEFAULT),
			DEFAULT_FUZZY(Type.DEFAULT, Type.FUZZY),
			//DEFAULT_HARD(Type.DEFAULT, Type.HARD),
			FUZZY_DEFAULT(Type.FUZZY, Type.DEFAULT),
			FUZZY(Type.FUZZY);
			//FUZZY_HARD(Type.FUZZY, Type.HARD),
			//HARD_DEFAULT(Type.HARD, Type.FUZZY),
			//HARD_FUZZY(Type.HARD, Type.FUZZY),
			//HARD(Type.HARD);
			
			public final Type block;
			public final Type biome;
			
			IntersectionEdge(Type type){
				this(type, type);
			}
			
			IntersectionEdge(Type block, Type biome){
				this.block = block;
				this.biome = biome;
			}
			
			public static enum Type{
				DEFAULT,//Blocked when strength is less than or equal to the other one.
				FUZZY;//No blocking when strengths are equal.
				//HARD;//I forgot
			}
		}
	}
	
	public static final class Aura{	
		public Aura(Map<BlockStateWrapper, BlockStateWrapper> infectMap, Map<ResourceLocation, ResourceLocation> infectionBiome, String name){
			this(infectMap, infectionBiome, name, false);
		}
		
		public Aura(Map<BlockStateWrapper, BlockStateWrapper> infectMap, Map<ResourceLocation, ResourceLocation> infectionBiome, String name, boolean hijack){
			this.infectMap = Collections.unmodifiableMap(infectMap);
			this.infectionBiome = infectionBiome == null ? null : Collections.unmodifiableMap(infectionBiome);
			this.name = name;
			this.hijack = hijack;
		}
		
		public final Map<BlockStateWrapper, BlockStateWrapper> infectMap;
		public final Map<ResourceLocation, ResourceLocation> infectionBiome;
		
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
			return "Aura:" + name + " [infectMap=" + infectMap + ", infectionBiome=" + infectionBiome + ", name=" + name + "]";
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
		
		public AuraIntersection[] getIntersection(Aura aura){
			AuraIntersection[] out = {NONE, NONE};
			block:for(int i = 0; i < infectMap.size(); i++){
				for(int ii = 0; ii < aura.infectMap.size(); ii++){
					Entry<BlockStateWrapper, BlockStateWrapper> entry1 = Util.iterableToList(infectMap.entrySet()).get(i);
					Entry<BlockStateWrapper, BlockStateWrapper> entry2 = Util.iterableToList(aura.infectMap.entrySet()).get(ii);
					
					if(entry1.getKey().equals(entry2.getKey())){
						if(entry1.getValue().equals(entry2.getValue())){
							out[0] = REDUNDANCY;
						}else{
							out[0] = CONFLICTION;
							break block;
						}
					}
				}
			}
			for(int i = 0; i < infectionBiome.size(); i++){
				for(int ii = 0; ii < aura.infectionBiome.size(); ii++){
					Entry<ResourceLocation, ResourceLocation> entry1 = Util.iterableToList(infectionBiome.entrySet()).get(i);
					Entry<ResourceLocation, ResourceLocation> entry2 = Util.iterableToList(aura.infectionBiome.entrySet()).get(ii);
					
					if(entry1.getKey().equals(entry2.getKey())){
						if(entry1.getValue().equals(entry2.getValue())){
							out[1] = AuraIntersection.REDUNDANCY;
						}else{
							out[1] = AuraIntersection.CONFLICTION;
							return out;
						}
					}
				}
			}
			return out;
		}
		
		public static enum AuraIntersection{
			NONE,
			REDUNDANCY,
			CONFLICTION;
		}
	}
}
