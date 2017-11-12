package drmeepster.drcorester;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import drmeepster.drcorester.common.item.BasicItem;
import drmeepster.drcorester.common.recipes.NBTShapedRecipe;
import drmeepster.drcorester.common.recipes.NBTShapelessRecipe;
import drmeepster.drcorester.common.testing.TestMain;
import drmeepster.drcorester.common.util.Util;
import drmeepster.drcorester.proxy.IProxy;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;

@Mod(modid = ModDrCorester.MODID, useMetadata = true)
public final class ModDrCorester{
	
	public static final String MODID = "drcorester";
	public static final String LOGGER_NAME = "DrCorester";
	
	public static boolean devStuff;
	public static boolean careAboutJava;
	public static boolean overreact;

	@SidedProxy(clientSide = "drmeepster.drcorester.proxy.ClientProxy", serverSide = "drmeepster.drcorester.proxy.ServerProxy")
	public static IProxy proxy;
	
	@Instance
	public static ModDrCorester instance;
	
	public static BasicItem placeholder;
	
	public static Logger log;

	public ModDrCorester(){
		placeholder = new BasicItem("placeholder", MODID);
		log = LogManager.getLogger(LOGGER_NAME);
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		ConfigCategory dev = config.getCategory("dev");
		config.addCustomCategoryComment(dev.getName(), "A few developer configs for DrCorester.");
		
		devStuff = config.getBoolean("allowDevItems", dev.getName(), false, "Adds in items used to test DrCorester.");
		careAboutJava = config.getBoolean("careAboutJVMVersion", Configuration.CATEGORY_GENERAL, true, "Care whether or not DrCorester has Java 8.");
		overreact = config.getBoolean("overreact", Configuration.CATEGORY_GENERAL, false, "Causes DrCorester to overreact to errors.");
		
		config.save();
		
		if(!Loader.instance().java8 && careAboutJava){
			log.fatal("DrCorester requires Java 8 to function normally! If you want to run the game still, disable \"careAboutJVMVersion\" in the config");
			throw new IllegalStateException("DrCorester requires Java 8 to function normally! If you want to run the game still, disable \"careAboutJVMVersion\" in the config");
		}
		
		RecipeSorter.register("NBTShapelessRecipe", NBTShapelessRecipe.class, Category.SHAPELESS, "");
		RecipeSorter.register("NBTShapedRecipe", NBTShapedRecipe.class, Category.SHAPED, "");
		if(devStuff){
			log.info("DrCorester dev items enabled!");
			TestMain.preInit();
		}
		
		Util.setup(placeholder);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event){
		if(devStuff){
			TestMain.init();
		}
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
		
	}
}
