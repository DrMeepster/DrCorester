package drmeepster.drcorester;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import drmeepster.drcorester.block.BasicInfectionBlock;
import drmeepster.drcorester.item.BasicItem;
import drmeepster.drcorester.proxy.IProxy;
import drmeepster.drcorester.recipes.NBTShapedRecipe;
import drmeepster.drcorester.recipes.NBTShapelessRecipe;
import drmeepster.drcorester.testing.TestMain;
import drmeepster.drcorester.util.Util;
import net.minecraft.block.Block;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
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
	
	public static boolean badJava = false;
	
	public static boolean devStuff;
	public static boolean careAboutJava;
	public static boolean crashOnVeryBad;

	@SidedProxy(clientSide = "drmeepster.drcorester.proxy.ClientProxy", serverSide = "drmeepster.drcorester.proxy.ServerProxy")
	public static IProxy proxy;
	
	public static final DamageSource DAMAGE_WRATH = new DamageSource("wrath").setDamageAllowedInCreativeMode().setDamageIsAbsolute();
	
	public static BasicItem placeholder;
	
	public static Logger log;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		log = LogManager.getLogger(event.getModMetadata().name);
		if(!Loader.instance().java8){
			log.error(String.format("WARNING! DrCorester requires Java 8, not %s! Things will propably not work!", System.getProperty("java.version")));
			badJava = true;
		}
		
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		ConfigCategory dev = config.getCategory("dev");
		config.addCustomCategoryComment(dev.getName(), "A few developer configs for DrCorester.");
		
		devStuff = config.getBoolean("allowDevItems", dev.getName(), false, "Adds in items used to test DrCorester.");
		careAboutJava = config.getBoolean("careAboutJVMVersion", Configuration.CATEGORY_GENERAL, true, "Care whether or not DrCorester has Java 8.");
		crashOnVeryBad = config.getBoolean("crashOnVeryBadItem", dev.getName(), true, "Causes a crash when \"item.drcorester_very_bad\" is spawned");
		config.save();
		
		if(badJava && careAboutJava){
			throw new UnknownError("DrCorester requires Java 8 to function normally! If you want to run the game still, disable \"careAboutJVMVersion\" in the config");
		}
		
		RecipeSorter.register("NBTShapelessRecipe", NBTShapelessRecipe.class, Category.SHAPELESS, "");
		RecipeSorter.register("NBTShapedRecipe", NBTShapedRecipe.class, Category.SHAPED, "");
		if(devStuff){
			TestMain.preInit();
		}
		
		placeholder = Util.register(new BasicItem("placeholder", MODID));
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void init(FMLInitializationEvent event){
		if(devStuff){
			TestMain.init();
		}
		BasicInfectionBlock.evaluateAll();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
	
	}
}
