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
import drmeepster.drcorester.block.BasicInfectionBlock;
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
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Various utility methods and constants for DrCorester
 * 
 * @author DrMeepster
 */
public final class Util{
	
	/**
	 * The Unicode symbol (U+00A7 {@value}) that Minecraft uses to format text.
	 */
	public static final char SECTION_SIGN = '\u00A7';
	
	/**
	 * The <code>DamageSource</code> used by <code>wrath(EntityLivingBase)<code/> and <code>wrath(EntityLivingBase, Achievement)<code/>.
	 * 
	 * @see Util#wrath(EntityLivingBase)
	 * @see Util#wrath(EntityLivingBase, Achievement)
	 */
	public static final DamageSource DAMAGE_WRATH = new DamageSource("wrath").setDamageAllowedInCreativeMode().setDamageIsAbsolute();
	
	/*LAMBADA EXPRESSIONS START*/
	
	/**
	 * Evaluates whether <code>player</code> is a spectator based on a few rules:
	 * If <code>specRule</code> is 0, <code>player</code> must not be a spectator.
	 * If <code>specRule</code> is 1, the spectator status of <code>player</code>.
	 * If <code>specRule</code> is 2, <code>player</code> must be a spectator.
	 * 
	 * @param player The <code>Player</code> to evaluate on the rules above.
	 * @param specRule The rule to evaluate <code>player</code> on.
	 * @return True if <code>player</code> follows the above rules.
	 */
	public static final BiPredicate<EntityPlayer, Integer> SPECTATOR = (player, specRule) -> {
		if(specRule == 2){
			return player.isSpectator();
		}else if(specRule == 1){
			return true;
		}else if(specRule == 0){
			return !player.isSpectator();
		}
		throw new IllegalArgumentException("\"specRule\" must be 0, 1 or 2!");
	};
	
	/**
	 * Performs a boolean XOR (Exclusive Or) operation on <code>a</code> and <code>b</code>.
	 * 
	 * @param a One input of the logic operation.
	 * @param b One input of the logic operation.
	 */
	public static final BiPredicate<Boolean, Boolean> XOR = (a, b) -> !((a && b) || (!a && !b));
	
	/**
	 * Performs a boolean XNOR (Exclusive Not Or) operation on <code>a</code> and <code>b</code>.
	 * 
	 * @param a One input of the logic operation.
	 * @param b One input of the logic operation.
	 */
	public static final BiPredicate<Boolean, Boolean> XNOR = (a, b) -> ((a && b) || (!a && !b));
	
	/**
	 * A TriPredicate that matches the required type of a <code>BasicInfectionBlock</code>'s <code>condition</code> field but always returns <code>true</code>.
	 * 
	 * @param world Does nothing
	 * @param pos Does nothing
	 * @param randPos Does nothing
	 * @see BasicInfectionBlock
	 */
	public static final TriPredicate<World, BlockPos, BlockPos> INFECTION_ALWAYS = (World world, BlockPos pos, BlockPos randPos) -> true;
	
	/*LAMBADA EXPRESSIONS END*/
	
	/**
	 * Nope.
	 */
	private Util(){}
	
	/**
	 * Strikes <code>player</code> with one-hit-kill lightning and then if <code>player</code> is a <code>Player</code>, grants them <code>onWrath</code>.
	 * 
	 * @param player The entity to strike and kill
	 * @param onWrath The achievement to grant
	 * @see
	 */
	public static void wrath(EntityLivingBase player, Achievement onWrath){
		if(player instanceof EntityPlayer && onWrath != null){
			((EntityPlayer)player).addStat(onWrath, 1);
		}
		wrath(player);
	}
	
	/**
	 * Strikes <code>player</code> with one-hit-kill lightning.
	 * 
	 * @param player
	 */
	public static void wrath(EntityLivingBase player){
		World world = player.getEntityWorld();
		BlockPos playerPos = player.getPosition();
		
		EntityLightningBolt lightning;
		for(int i = 0; i < 5; i++){
			lightning = new EntityLightningBolt(world, playerPos.getX(), playerPos.getY(), playerPos.getZ(), true);
			world.spawnEntityInWorld(lightning);
		}

		player.attackEntityFrom(DAMAGE_WRATH, Float.MAX_VALUE);
	}
	
	/**
	 * Registers an <code>IBasicObject</code>.
	 * 
	 * @param object The <code>IBasicObject</code> to register.
	 * @param <T> The type of <code>object</code>.
	 * @return <code>object</code>
	 */
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
	
	/**
	 * Registers an <code>IBasicObject</code> and adds it to <code>list</code>.
	 * 
	 * @param object The <code>IBasicObject</code> to register.
	 * @param list The <code>List</code> to add <code>object</code>.
	 * @param <T> The type of <code>object</code> to.
	 * @return <code>object</code>
	 */
	public static <T extends IBasicObject<?>> T register(T object, List<? super T> list){
		if(object instanceof Item){
			list.add(object);
		}
		return register(object);
	}
	
	/**
	 * Registers <code>item</code>'s model.
	 * 
	 * @param item The <code>Item</code> to register the model of.
	 */
	public static void registerItemModel(Item item){
		ModDrCorester.proxy.registerItemRenderer(item, 0, item.getRegistryName());
	}

	/**
	 * Removes the first section of <code>id</code>.
	 * 
	 * @param id The <code>String</code> to remove the first section of.
	 * @param seperator The <code>char</code> to separate each section of <code>id</code> with.
	 * @return <code>id</code> but without its first section.
	 */
	public static String removePrefix(String id, char seperator){
		char[] idChar = id.toCharArray();
		for(int i = 0; i < id.length(); i++){
			if(idChar[i] == seperator){
				return id.substring(i+1);
			}
		}
		return id;
	}
	
	/**
	 * Removes the first section of <code>id</code> with <b>.</b> as the separator.
	 * 
	 * @param id
	 * @return <code>id</code> but without its first section.
	 */
	public static String removePrefix(String id){
		return removePrefix(id, '.');
	}
	
	
	
	/**
	 * Returns all the players at <code>pos</code>.
	 * 
	 * @param world The <code>World</code> that contains the players to detect.
	 * @param pos The position where players are being detected.
	 * @param condition This <code>BiPredicate</code>  must return true when given a player and <code>input</code> for that player to be added to the output list.
	 * @param input A value to be given to <code>condition</code>.
	 * @return An <code>ArrayList</code> that contains all players at <code>pos</code>.
	 */
	public static <T> ArrayList<EntityPlayer> getPlayersAtPos(World world, BlockPos pos, BiPredicate<EntityPlayer, T> condition, T input){
		List<EntityPlayer> players = world.playerEntities;
		ArrayList<EntityPlayer> playersOut = new ArrayList<>(players.size());
		
		for(EntityPlayer playerX : players){
			if((condition.test(playerX, input)) && (playerX.getPosition().compareTo(pos) == 0)){
				playersOut.add(playerX);
			}
		}
		return playersOut;
	}
	
	/**
	 * Destroys all blocks in a column up from <code>pos</code> until either the top of the world is reached or a block that cannot be destroyed is found.
	 * 
	 * @param world The current world.
	 * @param pos Where to start destroying blocks.
	 * @param tooHardThreshold If the hardness of a block exceeds or equals this, it is not broken.
	 * @param breakTileEnts If false, it will not break TileEntities
	 * @return The position of the last block broken
	 */
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
		return pos.down();
	}
	
	/**
	 * Adds an <code>NBTTagCompound</code> to an <code>ItemStack</code>.
	 * 
	 * @param stack The <code>ItemStack</code> to add <code>tag</code> to.
	 * @param tag A <code>NBTTagCompound</code> to add to <code>stack</code>.
	 * @return <code>stack</code> with <code>tag</code> added to it.
	 */
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
	
	/**
	 * Turns an array into an <code>ArrayList</code>.
	 * 
	 * @param array The array to convert.
	 * @return An <code>ArrayList</code> that contains the content of <code>array</code>.
	 */
	public static <T> ArrayList<T> arrayToList(T[] array){
		ArrayList<T> list = new ArrayList<>();
		for(T t : array){
			list.add(t);
		}
		return list;
	}
	
	/**
	 * Turns a non-array <code>Iterable</code> into an <code>ArrayList</code>.
	 * 
	 * @param iterable The <code>Iterable</code> to convert.
	 * @return An <code>ArrayList</code> that contains the content of <code>iterable</code>.
	 */
	public static <T> ArrayList<T> iterableToList(Iterable<T> iterable){
		ArrayList<T> list = new ArrayList<>();
		for(T t : iterable){
			list.add(t);
		}
		return list;
	}
	
	/**
	 * Uses the same code as <code>GameRegistry</code> to transform an array of <code>Object</code>s into an array of <code>ItemStack</code>s. used by <code>ShapedRecipes</code> constructors<br><br>
	 * The proper format is:<br>
	 * {<br>
	 * "ABC",<br>
	 * "ABA",<br>
	 * "CAC",<br>
	 * 'A', &lt;SOME ITEM&gt;,<br>
	 * 'B', &lt;SOME ITEM&gt;,<br>
	 * 'C', &lt;SOME ITEM&gt;<br>
	 * }
	 * 
	 * @param recipeComponents The components of the recipe
	 * @return An <code>ItemStack</code> array usable by <code>ShapedRecipes</code> constructors.
	 * @see GameRegistry#addShapedRecipe(ItemStack, Object...)
	 * @see GameRegistry#addRecipe(ItemStack, Object...)
	 */
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
	
	/**
	 * Changes a <code>float</code> into an <code>EnumFacing</code>: either <code>EnumFacing.DOWN</code> or <code>EnumFacing.UP</code>.
	 * 
	 * @param pitch The pitch to be converted.
	 * @return <code>EnumFacing.DOWN</code> or <code>EnumFacing.UP</code>, depending on <code>pitch</code>.
	 */
	public static EnumFacing pitchToDir(float pitch){
		if(pitch < 0){
			return EnumFacing.DOWN;
		}
		return EnumFacing.UP;
	}
	
	/**
	 * Gets the next available index in the <code>MapColor</code> array.
	 * 
	 * @return The next available index in the <code>MapColor</code> array or -1 if it is full.
	 */
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
	
	/**
	 * Creates a new <code>MapColor</code> at the next allowed index.
	 * 
	 * @param color The color value of the <code>MapColor</code> to be created.
	 * @return A new <code>MapColor</code>.
	 * @see MapColor#MapColor(int, int)
	 */
	public static MapColor newMapColor(int color){
		return newMapColor(getNextMapColor(), color);
	}
	
	/**
	 * Creates a new <code>MapColor</code> at the given index.
	 * 
	 * @param index The position in the <code>MapColor</code> array of this <code>MapColor</code>
	 * @param color The color value of the <code>MapColor</code> to be created.
	 * @return A new <code>MapColor</code>.
	 * @see MapColor#MapColor(int, int)
	 */
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
	
	/**
	 * Creates a new <code>HashMap</code> out of <code>keys</code> and <code>values</code>.
	 * 
	 * @param keys The keys of the <code>HashMap</code>.
	 * @param values The values of the <code>HashMap</code>.
	 * @return A new <code>HashMap</code> out of <code>keys</code> and <code>values</code>.
	 */
	public static <K, V> HashMap<K, V> listsToHashMap(List<K> keys, List<V> values){
		HashMap<K, V> map = new HashMap<>();
		for(int i = 0; i < (keys.size() > values.size() ? keys.size() : values.size()); i++){
			map.put(keys.get(i), values.get(i));
		}
		return map;
	}
	
	/**
	 * Creates a new <code>HashMap</code> out of <code>keys</code> and <code>values</code>.
	 * 
	 * @param keys The keys of the <code>HashMap</code>.
	 * @param values The values of the <code>HashMap</code>.
	 * @return A new <code>HashMap</code> out of <code>keys</code> and <code>values</code>.
	 */
	public static <K, V> HashMap<K, V> arraysToHashMap(K[] keys, V[] values){
		return listsToHashMap(arrayToList(keys), arrayToList(values));
	}
	
	/**
	 * Generates a random <code>BlockPos</code> inside of <code>area</code>.
	 * 
	 * @param area The area to find the random block in.
	 * @param r The <code>Random</code> to use for the operation.
	 * @return A random <code>BlockPos</code> inside of <code>area</code>.
	 */
	public static BlockPos randomBlockInArea(BlockAreaApplied area, Random r){
		int x = randomInt(area.bound(EnumFacing.EAST), area.bound(EnumFacing.WEST), r);
		int y = randomInt(area.bound(EnumFacing.UP), area.bound(EnumFacing.DOWN), r);
		int z = randomInt(area.bound(EnumFacing.SOUTH), area.bound(EnumFacing.NORTH), r);
		
		return new BlockPos(x, y, z);
	}
	
	/**
	 * Generates a random <code>BlockPos</code> inside of <code>chunk</code>.
	 * 
	 * @param chunk The chunk to find the random block in.
	 * @param r The <code>Random</code> to use for the operation.
	 * @return A random <code>BlockPos</code> inside of <code>chunk</code>.
	 */
	public static BlockPos randomBlockInChunk(Chunk chunk, Random r){
		int x = randomInt(chunkBound(EnumFacing.EAST, chunk), chunkBound(EnumFacing.WEST, chunk), r);
		int y = randomInt(chunkBound(EnumFacing.UP, chunk), chunkBound(EnumFacing.DOWN, chunk), r);
		int z = randomInt(chunkBound(EnumFacing.SOUTH, chunk), chunkBound(EnumFacing.NORTH, chunk), r);
		
		return new BlockPos(x, y, z);
	}
	
	/**
	 * Returns the x/y bound of the <code>Chunk</code> at the given coordinates.
	 * 
	 * @param dir Which direction to get the bound of.
	 * @param x The X coordinate of the <code>Chunk</code>.
	 * @param z The Z coordinate of the <code>Chunk</code>.
	 * @return The bound of a <code>Chunk</code> at the given coordinates.
	 */
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
			if(FMLCommonHandler.instance().getSide() == Side.SERVER){
				//There is NO other way.
				return 256;
			}
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
	
	/**
	 * Returns the x/y bound of <code>chunk</code>.
	 * 
	 * @param dir Which direction to get the bound of.
	 * @param chunk The chunk to get the bounds of.
	 * @return The bound of <code>chunk</code>.
	 */
	public static int chunkBound(EnumFacing dir, Chunk chunk){
		return chunkBound(dir, chunk.xPosition, chunk.zPosition);
	}
	
	/**
	 * A version of <code>Random.nextInt()</code> and <code>Random.nextInt(int)</code> which allows upper <b>and</b> lower bounds (inclusive).
	 * These bounds can be in any order.
	 * 
	 * @param a One bound of the random <code>int</code>.
	 * @param b One bound of the random <code>int</code>.
	 * @param r The Random for the operation.
	 * @return
	 * @see Random#nextInt()
	 * @see Random#nextInt(int)
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
	 * A version of <code>Random.nextInt()</code> and <code>Random.nextInt(int)</code> which allows upper <b>and</b> lower bounds (inclusive).
	 * These bounds can be in any order.
	 * 
	 * @param a One bound of the random <code>int</code>.
	 * @param b One bound of the random <code>int</code>.
	 * @return
	 * @see Random#nextInt()
	 * @see Random#nextInt(int)
	 */
	public static int randomInt(int a, int b){
		return randomInt(a, b, new Random());
	}
}