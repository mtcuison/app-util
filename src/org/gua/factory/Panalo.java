package org.gua.factory;

import org.gua.connect.panalo.NewAccount;

public class Panalo {
    public enum Type{
        NEW_ACCOUNT,
        JOB_ORDER
    }
    
    public static UtilityValidator make(Type foType){
        switch (foType) {
            case NEW_ACCOUNT:
                return new NewAccount();
            case JOB_ORDER:
                break;
        }
        
        return null;
    }
}
