package drmeepster.drcorester.common.property;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import drmeepster.drcorester.common.property.PropertyValue.Property;

/**
 * Abstract class for handling the property system.
 * This works by having a <code>Map</code> with modids as keys and <code>Set</codes>s of <code>PropertyValues</code> as values.
 * 
 * @author DrMeepster
 */
public abstract class AbstractPropertyHandler{

	public final Set<Property<?>> allowedProperties;
	
	AbstractPropertyHandler(Set<Property<?>> allowedProperties){
		this.allowedProperties = allowedProperties;
	}
	
	public final Set<PropertyValue<?>> getProperties(String modid){
		return getPropertyMap().get(modid);
	}
	
	protected abstract PropertyValue<?> getProperty(String modid, Property<?> property, int dummy);
	
	public final PropertyValue<?> getProperty(String modid, Property<?> property){
		return getProperty(modid, property, 1) == null ? null : getProperty(modid, property, 1).immutableize();
	}
	
	public abstract <T> void setProperty(String modid, PropertyValue<T> value);
	
	public PropertyValue<?> getProperty(String modid, String name){
		for(Property<?> prop : allowedProperties){
			if(prop.name == name){
				return getProperty(modid, prop);
			}
		}
		return null;
	}
	
	protected abstract Map<String, Set<PropertyValue<?>>> getPropertyMap(int dummy);
	
	public final Map<String, Set<PropertyValue<?>>> getPropertyMap(){
		Map<String, Set<PropertyValue<?>>> out = new HashMap<>(getPropertyMap(1));
		for(Entry<String, Set<PropertyValue<?>>> entry : out.entrySet()){
			for(int i = 0; i < entry.getValue().size(); i++){
				Iterator<PropertyValue<?>> iterator = entry.getValue().iterator();
				entry.getValue().add(iterator.next().immutableize());
				iterator.remove();
			}
			entry.setValue(Collections.unmodifiableSet(entry.getValue()));
		}
		return Collections.unmodifiableMap(out);
	}
}
