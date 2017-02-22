package drmeepster.drcorester.property;

import static drmeepster.drcorester.property.ValueTypes.*;

public class ValueWrapper {
	
	public final String name;
	public final String value;
	public final ValueTypes type;
	
	public ValueWrapper(String name, String value, ValueTypes type){
		this.name = name;
		this.value = value;
		this.type = type;
		try{
			getValue();
		}catch(IllegalArgumentException e){
			throw new IllegalArgumentException(e);
		}
	}
	
	public Object getValue(){
		try{
			if(value == null){
				throw new RuntimeException();
			}
			switch(type){
			case BYTE:
				return Byte.parseByte(value);
			case SHORT:
				return Short.parseShort(value);
			case INTEGER:
				return Integer.parseInt(value);
			case LONG:
				return Long.parseLong(value);
			case FLOAT:
				return Float.parseFloat(value);
			case DOUBLE:
				return Double.parseDouble(value);
			case BOOLEAN:
				return Boolean.parseBoolean(value);
			case CHARACTER:
				if(value.length() != 1){
					throw new RuntimeException();
				}
				return value.charAt(0);
			default:
				return value;
			}
		}catch(RuntimeException e){
			throw new IllegalArgumentException("Invalid value for type " + type + ":" + e.getMessage(), e);
		}
	}
}
