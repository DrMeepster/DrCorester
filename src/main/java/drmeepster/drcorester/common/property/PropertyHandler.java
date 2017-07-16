package drmeepster.drcorester.common.property;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import drmeepster.drcorester.ModDrCorester;
import drmeepster.drcorester.common.property.PropertyValue.Property;
import drmeepster.drcorester.common.util.Util;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState;
import net.minecraftforge.fml.common.ModContainer;

public class PropertyHandler extends AbstractPropertyHandler{
	
	public static final Property<Boolean> ALTER_NAME = new Property<Boolean>("alterName", null, false);
	public static final Property<String> NAME = new Property<String>("name", null, false);
	
	public static final HashSet<Property<?>> ALLOWED_PROPERTIES = Util.iterableToSet(new Property<?>[] {ALTER_NAME, NAME});
	public static final HashSet<PropertyValue<?>> DEFAULT_PROPERTIES = Util.iterableToSet(new PropertyValue<?>[] {new PropertyValue<>(ALTER_NAME, true), new PropertyValue<>(NAME, "%1$s_%2$s")});
	
	public static final Logger LOG = LogManager.getLogger("Dr Corester-PropertyHandler");
	
	public static final PropertyHandler INSTANCE = new PropertyHandler();
	
	private HashMap<String, Set<PropertyValue<?>>> values;
	
	private PropertyHandler(){
		super(ALLOWED_PROPERTIES);
		List<ModContainer> mods = Loader.instance().getActiveModList();
		values = new HashMap<>(mods.size());
		for(ModContainer mod : mods){
			values.put(mod.getModId(), DEFAULT_PROPERTIES);
		}
	}

	public <T> void setProperty(PropertyValue<T> value){
		setProperty(Util.getModid(), value);
	}
	
	@Override
	public <T> void setProperty(String modid, PropertyValue<T> value){
		if(!ALLOWED_PROPERTIES.contains(value.property)){
			throw new IllegalArgumentException("Invalid property!");
		}
		if(Loader.instance().hasReachedState(LoaderState.AVAILABLE)){
			LOG.error("FML has finished loading; PropertyHandler is no longer modifiable.");
			if(ModDrCorester.overreact){
				throw new IllegalStateException("FML has finished loading; PropertyHandler is no longer modifiable!");
			}
			return;
		}
		values.get(modid).add(value);
	}

	@Override
	protected Map<String, Set<PropertyValue<?>>> getPropertyMap(int dummy){
		return values;
	}

	@Override
	protected PropertyValue<?> getProperty(String modid, Property<?> property, int dummy){
		for(PropertyValue<?> value : values.get(modid)){
			if(value.property != null && value.property.equals(property)){
				return value;
			}
		}
		return null;
	}

	public String getName(String modid, String name){
		return (Boolean)getProperty(modid, "alterName").getValue() ? String.format((String)getProperty(modid, "name").getValue(), modid, name) : name;
	}

	public String getName(String name){
		return getName(Util.getModid(), name);
	}
}
