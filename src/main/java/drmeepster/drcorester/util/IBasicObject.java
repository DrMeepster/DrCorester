package drmeepster.drcorester.util;

import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

/**
 * An interface for creating "Basic" Objects to base other objects on.
 * 
 * @author DrMeepster
 * @param <V>
 */
public interface IBasicObject<V> extends IForgeRegistryEntry<V>{
	
	/**
	 * Gets this Object's name. Usually some kind of unlocalized name. <b>DO NOT HARD CODE!</b>
	 * 
	 * @return This Object's name
	 */
	public String getName();
	
	/**
	 * Gets this Object's "id". 
	 * 
	 * @return
	 */
	public default String getId(){
		return getRegistryName().getResourcePath();
	}
}
