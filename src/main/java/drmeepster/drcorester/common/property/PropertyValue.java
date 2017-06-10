package drmeepster.drcorester.common.property;

import java.util.Set;

public class PropertyValue<T> {

	public final String name;
	public final Property<T> property;
	private T value;
	
	public PropertyValue(Property<T> property, T value){
		this.name = property.name;
		this.property = property;
		if(property.allowedValues != null && !property.allowedValues.contains(value)){
			throw new IllegalArgumentException("Value is not allowed!");
		}
		this.value = value;
	}
	
	public T getValue(){
		return value;
	}

	public void setValue(T value){
		this.value = value;
	}

	public static class Property<T>{
		public final String name;
		public final boolean allowNull;
		public final Set<T> allowedValues;
		
		public Property(String name, Set<T> allowedValues, boolean allowNull){
			this.name = name;
			this.allowNull = allowNull;
			this.allowedValues = allowedValues;
		}
	}
	
	public ImmutablePropertyValue<T> immutableize(){
		return new ImmutablePropertyValue<T>(this);
	}
	
	public static <T> ImmutablePropertyValue<T> immutableize(PropertyValue<T> property){
		return new ImmutablePropertyValue<T>(property);
	}
	
	public static class ImmutablePropertyValue<T> extends PropertyValue<T>{

		public ImmutablePropertyValue(PropertyValue<T> value){
			super(value.property, value.value);
		}
		
		@Override
		public void setValue(T value){
			throw new UnsupportedOperationException("Cannot set the value!");
		}
	}
}
