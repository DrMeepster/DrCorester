package drmeepster.drcorester;

import java.util.HashMap;

import drmeepster.drcorester.proxy.IProxy;
import drmeepster.drcorester.recipes.NBTShapedRecipe;
import drmeepster.drcorester.recipes.NBTShapelessRecipe;
import drmeepster.drcorester.util.Util;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;

@Mod(modid = ModDrCorester.MODID, useMetadata = true)
public class ModDrCorester{
	
	public static final String MODID = "drcorester";
	
	@SidedProxy(clientSide = "drmeepster.drcorester.proxy.ClientProxy", serverSide = "drmeepster.drcorester.proxy.ServerProxy")
	public static IProxy proxy;
	
	public static final DamageSource DAMAGE_WRATH = new DamageSource("wrath").setDamageAllowedInCreativeMode().setDamageIsAbsolute();

	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		RecipeSorter.register("NBTShapelessRecipe", NBTShapelessRecipe.class, Category.SHAPELESS, "");
		RecipeSorter.register("NBTShapedRecipe", NBTShapedRecipe.class, Category.SHAPED, "");
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event){

	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
	
	}
}
