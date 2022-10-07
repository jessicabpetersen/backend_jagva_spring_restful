package br.com.jessicabpetersen.modelVO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {

	INATIVO(0),
	ATIVADO(1);
	
	public final Integer code;
	 
	Status(Integer code){
		 this.code=code;
	 }
	 
	 @JsonValue
	 public String toString() {
	    return this.code.toString();
	 }

    @JsonCreator
    public static <T extends Status> T fromString(Integer value) {
        if (value == null) {
            return null;
        }
        for (T item : (T[]) Status.values()) {
            if (item.code == value) {
                return item;
            }
        }
        return null;
    }
}
