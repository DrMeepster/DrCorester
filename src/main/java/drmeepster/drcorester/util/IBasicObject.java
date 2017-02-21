package drmeepster.drcorester.util;

import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

public interface IBasicObject<V> extends IForgeRegistryEntry<V>{
	
	public String getName();
	public String getId();
}
