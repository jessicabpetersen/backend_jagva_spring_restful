package br.com.jessicabpetersen.modelVO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Tipo {
	PRODUTO(0),
	SERVICO(1);
	
	public final Integer code;
	 
	Tipo(Integer code){
		 this.code=code;
	 }
	 
	 @JsonValue
	 public String toString() {
	    return this.code.toString();
	 }

    @JsonCreator
    public static <T extends Tipo> T fromString(Integer value) {
        if (value == null) {
            return null;
        }
        for (T item : (T[]) Tipo.values()) {
            if (item.code == value) {
                return item;
            }
        }
        return null;
    }
}
