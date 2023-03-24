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

public class createilmj {
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
        
        String lsSQL = "SELECT * FROM Branch" +
                        " WHERE (sBranchCd LIKE 'C%' OR sBranchCd LIKE 'M%')" +
                            " AND cRecdStat = '1'" +
                        " ORDER BY sBranchCd";
        
        ResultSet loRS = instance.executeQuery(lsSQL);
        
        
        try {
            while (loRS.next()){
                ILoveMyJobValidator utility;
        
                instance.beginTrans();

                utility = ILoveMyJob.make(ILoveMyJob.Type.LR_PAYMENT);
                utility.setGRider(instance);
                utility.setWithParent(true);

                if (!utility.Run(loRS.getString("sBranchCd"), "2023-01-01", "2023-01-31")){
                    instance.rollbackTrans();
                    System.err.println(utility.getMessage());
                    logwrapr.info("Error!!!");
                    System.exit(1);
                }

                utility = ILoveMyJob.make(ILoveMyJob.Type.LR_PAYMENT_PR);
                utility.setGRider(instance);
                utility.setWithParent(true);

                if (!utility.Run(loRS.getString("sBranchCd"), "2023-01-01", "2023-01-31")){
                    instance.rollbackTrans();
                    System.err.println(utility.getMessage());
                    logwrapr.info("Error!!!");
                    System.exit(1);
                }

                utility = ILoveMyJob.make(ILoveMyJob.Type.OFFICIAL_RECEIPT);
                utility.setGRider(instance);
                utility.setWithParent(true);

                if (!utility.Run(loRS.getString("sBranchCd"), "2023-01-01", "2023-01-31")){
                    instance.rollbackTrans();
                    System.err.println(utility.getMessage());
                    logwrapr.info("Error!!!");
                    System.exit(1);
                }

                utility = ILoveMyJob.make(ILoveMyJob.Type.PROVISIONARY_RECEIPT);
                utility.setGRider(instance);
                utility.setWithParent(true);

                if (!utility.Run(loRS.getString("sBranchCd"), "2023-01-01", "2023-01-31")){
                    instance.rollbackTrans();
                    System.err.println(utility.getMessage());
                    logwrapr.info("Error!!!");
                    System.exit(1);
                }

                utility = ILoveMyJob.make(ILoveMyJob.Type.MC_SALES);
                utility.setGRider(instance);
                utility.setWithParent(true);

                if (!utility.Run(loRS.getString("sBranchCd"), "2023-01-01", "2023-01-31")){
                    instance.rollbackTrans();
                    System.err.println(utility.getMessage());
                    logwrapr.info("Error!!!");
                    System.exit(1);
                }

                utility = ILoveMyJob.make(ILoveMyJob.Type.SP_SALES);
                utility.setGRider(instance);
                utility.setWithParent(true);

                if (!utility.Run(loRS.getString("sBranchCd"), "2023-01-01", "2023-01-31")){
                    instance.rollbackTrans();
                    System.err.println(utility.getMessage());
                    logwrapr.info("Error!!!");
                    System.exit(1);
                }


                //mac - always run MP_SALES first before MP_SALES_ACC
                utility = ILoveMyJob.make(ILoveMyJob.Type.MP_SALES);
                utility.setGRider(instance);
                utility.setWithParent(true);

                if (!utility.Run(loRS.getString("sBranchCd"), "2023-01-01", "2023-01-31")){
                    instance.rollbackTrans();
                    System.err.println(utility.getMessage());
                    logwrapr.info("Error!!!");
                    System.exit(1);
                }

                //mac - always run MP_SALES first before MP_SALES_ACC
                utility = ILoveMyJob.make(ILoveMyJob.Type.MP_SALES_ACC);
                utility.setGRider(instance);
                utility.setWithParent(true);

                if (!utility.Run(loRS.getString("sBranchCd"), "2023-01-01", "2023-01-31")){
                    instance.rollbackTrans();
                    System.err.println(utility.getMessage());
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
