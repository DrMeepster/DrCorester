package drmeepster.drcorester;

import java.util.HashMap;

import drmeepster.drcorester.proxy.IProxy;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ModDrCorester.MODID, useMetadata = true)
public class ModDrCorester {
	
	public static final String MODID = "drcorester";
	@SidedProxy(clientSide = "drmeepster.drcorester.proxy.ClientProxy", serverSide = "drmeepster.drcorester.proxy.ServerProxy")
	public static IProxy proxy;
	public static final DamageSource DAMAGE_WRATH = new DamageSource("wrath").setDamageAllowedInCreativeMode().setDamageIsAbsolute();
	private static HashMap<String, HashMap<String, Object>> modidList = new HashMap<>();

	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event){

	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
	
	}
	
	public static HashMap getModProperties(String modid){
		if(modidList.containsKey(modid)){
			
		}
		return null;
	}
	
	private static void initMap(String modid){
		
	}
}
