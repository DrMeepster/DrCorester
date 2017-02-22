package drmeepster.drcorester.property;

import java.util.ArrayList;
import java.util.HashMap;
import static drmeepster.drcorester.property.ValueTypes.*;
import drmeepster.drcorester.util.Util;

public class PropertyHandler {
	
	private static HashMap<String, ArrayList<ValueWrapper>> modidList = new HashMap<>();
	
	private static ValueWrapper[] w = {new ValueWrapper("alterName", "true", BOOLEAN), new ValueWrapper("name", "%1_%2", STRING)};
	public static final ArrayList<ValueWrapper> PROP_WRAPPERS = Util.arrayToList(w);
	
	public static ArrayList<ValueWrapper> getModProperties(String modid){
		if(modidList.containsKey(modid)){
			return modidList.get(modid);
		}else{
			return initProperties(modid);
		}
	}
	
	public static Object getModProperty(String modid, String property){
		Object obj = null;
		for(ValueWrapper vw : getModProperties(modid)){
			if(vw.name == property){
				obj = vw.getValue();
			}
		}
		return obj;
	}
	
	public static ValueWrapper getModPropertyWrapper(String modid, String property){
		ValueWrapper w = null;
		for(ValueWrapper vw : getModProperties(modid)){
			if(vw.name == property){
				w = vw;
			}
		}
		return w;
	}
	
	public static void setProperty(String modid, String propertyName, String property){
		ValueWrapper prop = getModPropertyWrapper(modid, propertyName);
		if(prop == null){
			throw new UnsupportedOperationException("setProperty cannot add properties!");
		}
		getModProperties(modid).remove(prop);
		getModProperties(modid).add(new ValueWrapper(propertyName, property, prop.type));
	}
	
	private static ArrayList<ValueWrapper> initProperties(String modid){
		ArrayList<ValueWrapper> list = PROP_WRAPPERS;
		modidList.put(modid, list);
		return list;
	}
	
	public static String getName(String modid, String name){
		if(!(boolean)getModProperty(modid, "alterName")){
			return name;
		}
		String pre = (String)getModProperty(modid, "name");
		return Util.insert(pre, modid, name);
	}
}
