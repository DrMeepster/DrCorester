package drmeepster.drcorester.property;

public enum ValueTypes{
	BYTE(Byte.class),
	SHORT(Short.class),
	INTEGER(Integer.class),
	LONG(Long.class),
	FLOAT(Float.class),
	DOUBLE(Double.class),
	BOOLEAN(Boolean.class),
	CHARACTER(Character.class),
	STRING(Character.class);
	
	public final Class<?> type;
	
	ValueTypes(Class<?> type){
		this.type = type;
	}
	
	@Override
	public String toString(){
		return type.getSimpleName();
	}
}