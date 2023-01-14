package com.infoac.cas.enums;

public enum IsBooleanEnum {
	
	YES_ZREO("0","是"),
	NO_ONE("1","否");
	
	private String key;
    private String value;
    
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	private IsBooleanEnum(String key, String value) {
		this.key = key;
		this.value = value;
	}
    //通过key获取value
	public static String getValueByKey(String key){
		for (IsBooleanEnum unit : IsBooleanEnum.values()) {
			if(unit.getKey().equals(key)){
				return unit.getValue();
			}
		}
		return "";
	}
	//通过value获取key
	public static String getKeyByValue(String value){
		for (IsBooleanEnum unit : IsBooleanEnum.values()) {
			if(unit.getValue().equals(value)){
				return unit.getKey();
			}
		}
		return "";
	}

}
