package org.gua.app;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.gua.app.*;
import org.gua.factory.ILoveMyJob;
import org.gua.factory.ILoveMyJobValidator;
import org.gua.factory.Panalo;
import org.gua.factory.UtilityValidator;
import org.rmj.appdriver.agent.GRiderX;
import org.rmj.replication.utility.LogWrapper;

public class sendRaffleNotif {
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
        String lsSQL;
        ResultSet loRS;
        lsSQL = "SELECT * FROM ILMJ_Master" +
                        " WHERE cTranStat = '1' ORDER BY dRaffleDt DESC LIMIT 1;";
        
        loRS = instance.executeQuery(lsSQL);
        
        
        try {
            while (loRS.next()){
                ILoveMyJobValidator utility;
        
                instance.beginTrans();

                utility = ILoveMyJob.make(ILoveMyJob.Type.RAFFLE_DATE);
                utility.setGRider(instance);
                utility.setWithParent(true);
                System.out.println("sTransNox = " + loRS.getString("sTransNox"));
                System.out.println("dRaffleDt = " + loRS.getString("dRaffleDt"));
                System.out.println("sRemarksx = " + loRS.getString("sRemarksx"));
                
                if (!utility.Run(loRS.getString("sTransNox"),loRS.getString("dRaffleDt"), loRS.getString("sRemarksx"))){
                    instance.rollbackTrans();
                    System.err.println("err = " + utility.getMessage());
                    logwrapr.info("Error!!!");
                    System.exit(1);
                }

                instance.commitTrans();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            logwrapr.info(ex.getMessage());
            System.exit(1);
        }
        
        logwrapr.info("End of Process!");
    }
}
