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
	public static final String[] PROP_NAMES = {"moddidInName"};
	public static final Object[] PROP_DEF_VALUES = {Boolean.TRUE};

	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event){

	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
	
	}
	
	public static HashMap<String, Object> getModProperties(String modid){
		if(modidList.containsKey(modid)){
			return modidList.get(modid);
		}else{
			return initMap(modid);
		}
	}
	
	public static Object getModProperty(String modid, String property){
		HashMap<String, Object> map = getModProperties(modid);
		if(map.containsKey(property)){
			return map.get(property);
		}else{
			return null;
		}
	}
	
	public static void setProperty(String modid, String propertyName, Object property){
		HashMap<String, Object> map = getModProperties(modid);
		map.put(propertyName, property);
		modidList.put(modid, map);
	}
	
	private static HashMap<String, Object> initMap(String modid){
		HashMap<String, Object> map = new HashMap<>();
		for(int i = 0; i < PROP_NAMES.length; i++){
			map.put(PROP_NAMES[i], PROP_DEF_VALUES[i]);
		}
		modidList.put(modid, map);
		return map;
	}
}
