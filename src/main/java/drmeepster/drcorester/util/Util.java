package drmeepster.drcorester.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;

import drmeepster.drcorester.ModDrCorester;
import drmeepster.drcorester.block.IBasicBlock;
import drmeepster.drcorester.proxy.ClientProxy;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.Achievement;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.FMLRelaunchLog;
import net.minecraftforge.fml.relauncher.Side;

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
			lightning = new EntityLightningBolt(world, (double)playerPos.getX(), (double)playerPos.getY(), (double)playerPos.getZ(), true);
			world.spawnEntityInWorld(lightning);
		}

		player.attackEntityFrom(ModDrCorester.DAMAGE_WRATH, Float.MAX_VALUE);
	}
		
	public static <T extends IBasicObject> T register(T object){
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
		ModDrCorester.log.info(String.format("The object, \"%s\", has been registered", object.getRegistryName().toString()));
		return object;
	}
	
	public static <T extends IBasicObject> T register(T object, List<Item> itemList){
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
	
	public static <T> T[] varargsToArray(T... t){
		return t;
	}
	
	public static ItemStack[] stuffToItemstackArray(Object... recipeComponents){
        String s = "";
        int i = 0;
        int j = 0;
        int k = 0;
        
        try{
        	if (recipeComponents[i] instanceof String[])
        	{
        		String[] astring = (String[])((String[])recipeComponents[i++]);

        		for (String s2 : astring)
        		{
        			++k;
        			j = s2.length();
        			s = s + s2;
        		}
        	}
        	else
        	{
        		while (recipeComponents[i] instanceof String)
        		{
        			String s1 = (String)recipeComponents[i++];
        			++k;
        			j = s1.length();
        			s = s + s1;
        		}
        	}

        	Map<Character, ItemStack> map;

        	for (map = Maps.<Character, ItemStack>newHashMap(); i < recipeComponents.length; i += 2)
        	{
        		Character character = (Character)recipeComponents[i];
        		ItemStack itemstack = null;

        		if (recipeComponents[i + 1] instanceof Item)
        		{
        			itemstack = new ItemStack((Item)recipeComponents[i + 1]);
        		}
        		else if (recipeComponents[i + 1] instanceof Block)
        		{
        			itemstack = new ItemStack((Block)recipeComponents[i + 1], 1, 32767);
        		}
        		else if (recipeComponents[i + 1] instanceof ItemStack)
        		{
        			itemstack = (ItemStack)recipeComponents[i + 1];
        		}

        		map.put(character, itemstack);
        	}

        	ItemStack[] aitemstack = new ItemStack[j * k];
        	
        	for (int l = 0; l < j * k; ++l)
        	{
        		char c0 = s.charAt(l);

        		if (map.containsKey(Character.valueOf(c0)))
        		{
        			aitemstack[l] = ((ItemStack)map.get(Character.valueOf(c0))).copy();
        		}
        		else
        		{
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
}