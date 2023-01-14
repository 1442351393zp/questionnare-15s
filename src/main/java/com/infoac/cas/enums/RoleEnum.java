package com.infoac.cas.enums;

public enum RoleEnum {
	
	ROLE_ZREO("0","普通用户"),
	ROLE_ONE("1","安全审计员"),
	ROLE_TWO("2","安全管理员"),
	ROLE_FOUR("4","系统管理员");
	
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
	private RoleEnum(String key, String value) {
		this.key = key;
		this.value = value;
	}
    //通过key获取value
	public static String getValueByKey(String key){
		for (RoleEnum unit : RoleEnum.values()) {
			if(unit.getKey().equals(key)){
				return unit.getValue();
			}
		}
		return "";
	}
	//通过value获取key
	public static String getKeyByValue(String value){
		for (RoleEnum unit : RoleEnum.values()) {
			if(unit.getValue().equals(value)){
				return unit.getKey();
			}
		}
		return "";
	}

}
