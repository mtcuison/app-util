package org.gua.app;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.gua.app.*;
import org.gua.factory.ILoveMyJob;
import org.gua.factory.ILoveMyJobValidator;
import org.gua.factory.Panalo;
import org.gua.factory.UtilityValidator;
import org.rmj.appdriver.MiscUtil;
import org.rmj.appdriver.SQLUtil;
import org.rmj.appdriver.agent.GRiderX;
import org.rmj.replication.utility.LogWrapper;

public class sendRaffleNotifStart {
    public static void main (String [] args){
        LogWrapper logwrapr = new LogWrapper("ILMJ", "app-util.log");
        logwrapr.info("Start of Process!");
        
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
        
        instance.beginTrans();
        ILoveMyJobValidator utility;
        utility = ILoveMyJob.make(ILoveMyJob.Type.RAFFLE_START);
        utility.setGRider(instance);
        utility.setWithParent(true);
        if (!utility.Run("", "", "")){
            instance.rollbackTrans();
            System.err.println("err = " + utility.getMessage());
            logwrapr.info("Error!!!");
            System.exit(1);
        }
        instance.commitTrans();
        
        logwrapr.info("End of Process!");
    }
    
    
    public static String dateToWord (String dtransact) {
       
        try {
            Date date = new Date();
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            date = (Date)formatter.parse(dtransact);  
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            String todayStr = fmt.format(date);
            
            return todayStr;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
