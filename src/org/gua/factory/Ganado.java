package org.gua.factory;

import org.gua.connect.panalo.NewAccount;

public class Ganado {
    public enum Type{
        CONNECT,
        CIRCLE
    }
    
    public static UtilityValidator make(Type foType){
        switch (foType) {
            case CIRCLE:
                return new NewAccount();
        }
        
        return null;
    }
}
