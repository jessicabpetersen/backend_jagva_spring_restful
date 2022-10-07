package br.com.jessicabpetersen.modelVO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Situacao {
	
	FECHADO(0),
	ABERTO(1);
	
	 public final Integer code;
	 
	 Situacao(Integer code){
		 this.code=code;
	 }
	 
	 @JsonValue
	 public String toString() {
	    return this.code.toString();
	 }

    @JsonCreator
    public static <T extends Situacao> T fromString(Integer value) {
        if (value == null) {
            return null;
        }
        for (T item : (T[]) Situacao.values()) {
            if (item.code == value) {
                return item;
            }
        }
        return null;
    }
}
