package drmeepster.drcorester.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

import drmeepster.drcorester.ModDrCorester;
import drmeepster.drcorester.block.IBasicBlock;
import drmeepster.drcorester.proxy.ClientProxy;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

public final class Util{
	
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
			registerItemModel((Item)object, ((Item)object).getRegistryName().getResourceDomain());
		}
		if(object instanceof IBasicBlock){
			register(((IBasicBlock)object).getItemBlock());
		}
		if(object instanceof ItemBlock){
			System.out.println("The ItemBlock with block \"" + object.getName() + "\", has been registered");
			return object;
		}
		System.out.println("The object, \"" + object.getName() + "\", has been registered");
		return object;
	}
	
	public static <T extends IBasicObject> T register(T object, List<Item> itemList){
		if(object instanceof Item){
			itemList.add((Item)object);
		}
		return register(object);
	}
	
	public static void registerItemModel(Item item, String modid){
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT){
			((ClientProxy)ModDrCorester.proxy).registerItemRenderer(item, 0, removePrefix(item.getUnlocalizedName()), modid);
		}
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
		NBTTagCompound out = stack.getTagCompound();
		if(out == null){
			out = tag;
		}else{
			out.merge(tag);
		}
		stack.setTagCompound(out);
		return stack;
	}
	
	public static ItemStack setNbtData(ItemStack stack, NBTTagCompound tag){
		stack.setTagCompound(tag);
		return stack;
	}
	
	/**
	 * Crashes game.
	 */
	@Deprecated
	public static String insert(String in, String... replace){
		for(int i = 0; i < replace.length; i++){
			in = in.replaceAll("%" + (i + 1), replace[i]);
		}
		return in;
	}
	
	/**
	 * Crashes game.
	 */
	@Deprecated
	public static String insert(String in, Object... replace){
		String[] replaceStr = new String[replace.length];
		for(int i = 0; i < replace.length; i++){
			replaceStr[i] = replace[i].toString();
		}
		return insert(in, replaceStr);
	}
	
	public static <T> ArrayList<T> arrayToList(T[] array){
		ArrayList<T> list = new ArrayList<>();
		for(T t : array){
			list.add(t);
		}
		return list;
	}
}