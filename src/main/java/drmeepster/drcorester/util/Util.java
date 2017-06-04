package drmeepster.drcorester.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.BiPredicate;

import com.google.common.collect.Maps;

import drmeepster.drcorester.ModDrCorester;
import drmeepster.drcorester.block.IBasicBlock;
import drmeepster.drcorester.util.BlockArea.BlockAreaApplied;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.Achievement;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.registry.GameRegistry;

public final class Util{
	
	public static final char SECTION_SIGN = '\u00A7';
	
	/**
	 * Nope.
	 */
	private Util(){}
	
	public static void wrath(EntityLivingBase player, Achievement onWrath){
		if(player instanceof EntityPlayer && onWrath != null){
			((EntityPlayer)player).addStat(onWrath, 1);
		}
	
		World world = player.getEntityWorld();
		BlockPos playerPos = player.getPosition();
		
		EntityLightningBolt lightning;
		for(int i = 0; i < 5; i++){
			lightning = new EntityLightningBolt(world, playerPos.getX(), playerPos.getY(), playerPos.getZ(), true);
			world.spawnEntityInWorld(lightning);
		}

		player.attackEntityFrom(ModDrCorester.DAMAGE_WRATH, Float.MAX_VALUE);
	}
		
	public static <T extends IBasicObject<?>> T register(T object){
		GameRegistry.register(object);
		
		if(object instanceof Item){
			registerItemModel((Item)object);
		}
		if(object instanceof IBasicBlock){
			register(((IBasicBlock)object).getItemBlock());
		}
		if(object instanceof ItemBlock){
			ModDrCorester.log.info(String.format("The ItemBlock with block, \"%s\", has been registered", object.getRegistryName().toString()));
			return object;
		}
		/*if(object instanceof BasicBiome){
			
		}*/
		ModDrCorester.log.info(String.format("The object, \"%s\", has been registered", object.getRegistryName().toString()));
		return object;
	}
	
	public static <T extends IBasicObject<?>> T register(T object, List<Item> itemList){
		if(object instanceof Item){
			itemList.add((Item)object);
		}
		return register(object);
	}
	
	public static void registerItemModel(Item item){
		ModDrCorester.proxy.registerItemRenderer(item, 0, item.getRegistryName());
	}

	public static String removePrefix(String id, char seperator){
		char[] idChar = id.toCharArray();
		for(int i = 0; i < id.length(); i++){
			if(Character.valueOf(idChar[i]).equals(seperator)){
				return id.substring(i+1);
			}
		}
		return id;
	}
	
	public static String removePrefix(String id){
		return removePrefix(id, '.');
	}
	
	/**LAMBADA EXPRESSIONS START*/
	
	/*
	 * 0 = "NO!"
	 * 1 = "Ok sure"
	 * 2 = "Sure but, IT MUST BE A SPECTATOR!"
	 */
	public static final BiPredicate<EntityPlayer, Integer> SPECTATOR = (a, b) -> {
		if(b == 2){
			return a.isSpectator();
		}else if(b == 1){
			return true;
		}else{
			return !a.isSpectator();
		}
	};
	
	public static final BiPredicate<Boolean, Boolean> XOR = (a, b) -> ((a && b) || (!a && !b));
	public static final BiPredicate<Boolean, Boolean> XNOR = (a, b) -> !((a && b) || (!a && !b));
	public static final TriPredicate<World, BlockPos, BlockPos> INFECTION_ALWAYS = (World w, BlockPos b, BlockPos bb) -> true;
	/**LAMBADA EXPRESSIONS END*/
	
	public static ArrayList<EntityPlayer> getPlayersAtPos(World world, BlockPos pos, int spectator){
		List<EntityPlayer> players = world.playerEntities;
		ArrayList<EntityPlayer> playersOut = new ArrayList<>(players.size());
		
		for(EntityPlayer playerX : players){
			if((SPECTATOR.test(playerX, spectator)) && (playerX.getPosition().compareTo(pos) == 0)){
				playersOut.add(playerX);
			}
		}
		return playersOut;
	}
	
	public static BlockPos clearAir(World world, BlockPos pos, float tooHardThreshold, boolean breakTileEnts){
		if(!world.canSeeSky(pos)){
			while(pos.getY() < world.getActualHeight()){
				IBlockState block = world.getBlockState(pos);
				
				if(
				 (block.getBlockHardness(world, pos) >= tooHardThreshold ||
				 ((block.getBlock() instanceof ITileEntityProvider) &&
				 (!breakTileEnts)))){
					break;
				}
				
				if(!world.isAirBlock(pos)){
					world.destroyBlock(pos, true);
				}
				pos = pos.up();
			}
		}
		return pos;
	}
	
	public static ItemStack addNbtData(ItemStack stack, NBTTagCompound tag){
		if(tag == null){
			return stack;
		}
		NBTTagCompound out;
		ItemStack s = stack.copy();
	
		if(stack.getTagCompound() == null){
			out = tag;
		}else{
			out = stack.getTagCompound().copy();
		}
		
		out.merge(tag);
		s.setTagCompound(out);
		return s;
	}
	
	public static ItemStack setNbtData(ItemStack stack, NBTTagCompound tag){
		ItemStack s = stack.copy();
		s.setTagCompound(tag);
		return s;
	}
	
	public static <T> ArrayList<T> arrayToList(T[] array){
		ArrayList<T> list = new ArrayList<>();
		for(T t : array){
			list.add(t);
		}
		return list;
	}
	
	/**
	 * @deprecated Heap pollution
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <T> T[] varargsToArray(T... t){
		return t;
	}
	
	public static ItemStack[] stuffToItemstackArray(Object... recipeComponents){
        String s = "";
        int i = 0;
        int j = 0;
        int k = 0;
        
        try{
        	if (recipeComponents[i] instanceof String[]){
        		String[] astring = ((String[])recipeComponents[i++]);

        		for (String s2 : astring){
        			++k;
        			j = s2.length();
        			s = s + s2;
        		}
        	}else{
        		while (recipeComponents[i] instanceof String){
        			String s1 = (String)recipeComponents[i++];
        			++k;
        			j = s1.length();
        			s = s + s1;
        		}
        	}

        	Map<Character, ItemStack> map;

        	for (map = Maps.<Character, ItemStack>newHashMap(); i < recipeComponents.length; i += 2){
        		Character character = (Character)recipeComponents[i];
        		ItemStack itemstack = null;

        		if (recipeComponents[i + 1] instanceof Item){
        			itemstack = new ItemStack((Item)recipeComponents[i + 1]);
        		}else if (recipeComponents[i + 1] instanceof Block){
        			itemstack = new ItemStack((Block)recipeComponents[i + 1], 1, 32767);
        		}else if (recipeComponents[i + 1] instanceof ItemStack){
        			itemstack = (ItemStack)recipeComponents[i + 1];
        		}

        		map.put(character, itemstack);
        	}

        	ItemStack[] aitemstack = new ItemStack[j * k];
        	
        	for (int l = 0; l < j * k; ++l){
        		char c0 = s.charAt(l);

        		if (map.containsKey(Character.valueOf(c0))){
        			aitemstack[l] = map.get(Character.valueOf(c0)).copy();
        		}else{
        			aitemstack[l] = null;
        		}
        	}
        
        	return aitemstack;
        }catch(RuntimeException e){
        	throw new RuntimeException("Caught exception in creating an ItemStack array, " + e.toString(), e);
        }
	}
	
	public static EnumFacing pitchToDir(float pitch){
		if(pitch < 0){
			return EnumFacing.DOWN;
		}
		return EnumFacing.UP;
	}
	
	public static int getNextMapColor(){
		//Will only be -1 if array is full
		int out = -1;
		
		for(int i = 0; i < MapColor.COLORS.length; i++){
			if(MapColor.COLORS[i] != null){
				continue;
			}
			out = i;
			break;
		}
		
		return out;
	}
	
	public static MapColor newMapColor(int color){
		return newMapColor(getNextMapColor(), color);
	}
	
	public static MapColor newMapColor(int index, int color){
		Constructor<MapColor> constr;
		try{
			constr = MapColor.class.getDeclaredConstructor(int.class, int.class);
		}catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
		
		constr.setAccessible(true);
		
		try {
			return constr.newInstance(index, color);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static <K, V> HashMap<K, V> listsToHashMap(List<K> keys, List<V> values){
		HashMap<K, V> map = new HashMap<>();
		for(int i = 0; i < (keys.size() > values.size() ? keys.size() : values.size()); i++){
			map.put(keys.get(i), values.get(i));
		}
		return map;
	}
	
	public static <K, V> HashMap<K, V> listsToHashMap(K[] keys, V[] values){
		return listsToHashMap(arrayToList(keys), arrayToList(values));
	}
	
	public static BlockPos randomBlockInArea(BlockAreaApplied area, Random r){
		int x = randomInt(area.bound(EnumFacing.EAST), area.bound(EnumFacing.WEST), r);
		int y = randomInt(area.bound(EnumFacing.UP), area.bound(EnumFacing.DOWN), r);
		int z = randomInt(area.bound(EnumFacing.SOUTH), area.bound(EnumFacing.NORTH), r);
		
		return new BlockPos(x, y, z);
	}
	
	public static BlockPos randomBlockInChunk(Chunk c, Random r){
		int x = randomInt(chunkBound(EnumFacing.EAST, c), chunkBound(EnumFacing.WEST, c), r);
		int y = randomInt(chunkBound(EnumFacing.UP, c), chunkBound(EnumFacing.DOWN, c), r);
		int z = randomInt(chunkBound(EnumFacing.SOUTH, c), chunkBound(EnumFacing.NORTH, c), r);
		
		return new BlockPos(x, y, z);
	}
	
	public static int chunkBound(EnumFacing dir, int x, int z){
		if(dir == null){
			throw new NullPointerException("\"dir\" cannot be null!");
		}
		
		switch(dir){
		case EAST:
			return x * 16;
		case WEST:
			return (x * 16) + 15;
		case UP:
			//Just in case a mod changes max height limit
			return Minecraft.getMinecraft().theWorld.getHeight();
		case DOWN:
			return 0;
		case SOUTH:
			return z * 16;
		case NORTH:
			return (z * 16) + 15;
		default:
			throw new IllegalArgumentException("Chunks do not have a bound in the \"" + dir + "\" direction.");
		}
	}
	
	public static int chunkBound(EnumFacing dir, Chunk c){
		return chunkBound(dir, c.xPosition, c.zPosition);
	}
	
	/**
	 * NOTE: "a" can be bigger, smaller or equal to "b"
	 * 
	 * @return A random number from "a" to "b" (inclusive).
	 */
	public static int randomInt(int a, int b, Random r){
		if(a == b){
			return a;
		}else if(a < b){
			return r.ints(a, b + 1).findAny().getAsInt();
		}else{
			return r.ints(b, a + 1).findAny().getAsInt();
		}
	}
	
	/**
	 * Allows getting a <code>IBlockState</code> from a <code>Block</code> before it is constructed.
	 * 
	 * @author DrMeepster
	 */
	public static final class BlockStateWrapper{
		private IBlockState state = null;
		private boolean evaluated = false;
		
		private final ResourceLocation block;
		private final int meta;
		
		/**
		 * 
		 * 
		 * @param state
		 */
		public BlockStateWrapper(IBlockState state){
			this(state.getBlock().getRegistryName(), state.getBlock().getMetaFromState(state));
			this.state = state;
			evaluated = true;
		}
		
		public BlockStateWrapper(ResourceLocation block){
			this(block, 0);
		}
		
		/**
		 * Constructs a <code>BlockStateWrapper</code>.
		 * 
		 * @param block
		 * @param meta
		 */
		public BlockStateWrapper(ResourceLocation block, int meta){
			if(block == null){
				throw new NullPointerException("\"block\" cannot be null!");
			}
			if(meta < 0 || meta > 15){
				throw new IllegalArgumentException("\"meta\" must be between 0 and 15 (inclusive), not " + meta + "!");
			}
			this.block = block;
			this.meta = meta;
		}
		
		public IBlockState getState(){
			if(!evaluated){
				throw new IllegalStateException("This BlockStateWrapper must have \"evaluated\" be true to access the IBlockState.");
			}
			return state;
		}

		public boolean isEvaluated(){
			return evaluated;
		}

		public ResourceLocation getBlock(){
			return block;
		}

		public int getMeta(){
			return meta;
		}
		
		@SuppressWarnings("deprecation")
		public void evaluate() throws EvaluationException{
			try{
				if(evaluated){
					throw new IllegalStateException("Cannot evaluate an evaluated BlockStateWrapper!");
				}
				state = Block.REGISTRY.getObject(block).getStateFromMeta(meta);
				evaluated = true;
			}catch(Exception e){
				throw new EvaluationException(e);
			}
		}
		
		public static class EvaluationException extends RuntimeException{

			private static final long serialVersionUID = -3948779907025439331L;

			public EvaluationException() {
		        super();
		    }

		    public EvaluationException(String message){
		        super(message);
		    }

		    public EvaluationException(String message, Throwable cause){
		        super(message, cause);
		    }

		    public EvaluationException(Throwable cause){
		        super(cause);
		    }

		    protected EvaluationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
		        super(message, cause, enableSuppression, writableStackTrace);
		    }
		}
	}
}