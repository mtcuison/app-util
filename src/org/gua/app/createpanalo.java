package org.gua.app;

import org.gua.factory.Panalo;
import org.gua.factory.UtilityValidator;
import org.rmj.appdriver.agent.GRiderX;
import org.rmj.replication.utility.LogWrapper;

public class createpanalo {
    public static void main (String [] args){
        LogWrapper logwrapr = new LogWrapper("newaccounts", "app-util.log");
        
        String path;
        if(System.getProperty("os.name").toLowerCase().contains("win")){
            path = "D:/GGC_Java_Systems";
        }
        else{
            path = "/srv/GGC_Java_Systems";
        }
        System.setProperty("sys.default.path.config", path);
        
        GRiderX instance = new GRiderX("gRider");
        
        if (!instance.logUser("gRider", "M001111122")){
            logwrapr.severe(instance.getMessage() + instance.getErrMsg());
            System.exit(1);
        }
        
        UtilityValidator utility;
        
        utility = Panalo.make(Panalo.Type.NEW_ACCOUNT);
        utility.setGRider(instance);
        if (!utility.Run()){
            logwrapr.severe(utility.getMessage());
            System.exit(1);
        }
        
        System.exit(0);
    }
}
