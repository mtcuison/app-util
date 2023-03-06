package org.gua.connect;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.gua.factory.UtilityValidator;
import org.rmj.appdriver.MiscUtil;
import org.rmj.appdriver.SQLUtil;
import org.rmj.appdriver.agent.GRiderX;

public class NewAccount implements UtilityValidator{
    private final String _code = "0001";
    private final String _item = "M02907000106";
    private final int _days = 60;
    
    private GRiderX _instance;
    private String _message;
    
    @Override
    public void setGRider(GRiderX foValue) {
        _instance = foValue;
    }

    @Override
    public boolean Run() {
        if (_instance == null){
            _message = "Application driver is not set.";
            return false;
        }
        
        //get the accounts
        String lsSQL = getSQ_Master();
        ResultSet loRS = _instance.executeQuery(lsSQL);
        
        if (MiscUtil.RecordCount(loRS) == 0) return true;
        
        try {
            _instance.beginTrans();
            while (loRS.next()){                
                lsSQL = "INSERT INTO Guanzon_Apps_Panalo SET" +
                        "  sTransNox = " + SQLUtil.toSQL(MiscUtil.getNextCode("Guanzon_Apps_Panalo", "sTransNox", true, _instance.getConnection(), _instance.getBranchCode())) +
                        ", dTransact = " + SQLUtil.toSQL(_instance.getServerDate()) +
                        ", sPanaloCD = " + SQLUtil.toSQL(_code) +
                        ", sAcctNmbr = " + SQLUtil.toSQL(loRS.getString("sAcctNmbr")) +
                        ", sSourceCD = " + SQLUtil.toSQL("MCSO") +
                        ", sSourceNo = " + SQLUtil.toSQL(loRS.getString("xTransNox")) +
                        ", nAmountxx = 0.00" +
                        ", sItemCode = " + SQLUtil.toSQL(_item) +
                        ", nItemQtyx = 1" +
                        ", nRedeemxx = 0" +
                        ", dExpiryDt = NULL" +
                        ", sUserIDxx = " + SQLUtil.toSQL(loRS.getString("sUserIDxx")) +
                        ", dRedeemxx = NULL" +
                        ", sDeviceID = " + SQLUtil.toSQL(loRS.getString("sIMEINoxx")) +
                        ", sReleased = NULL" +
                        ", cTranStat = 0" +
                        ", sModified = " + SQLUtil.toSQL(_instance.getUserID()) +
                        ", dModified = " + SQLUtil.toSQL(_instance.getServerDate());
                
                if (_instance.executeQuery(lsSQL, "Guanzon_Apps_Panalo", _instance.getBranchCode(), "") <= 0){
                    _message = _instance.getErrMsg();
                    _instance.rollbackTrans();
                    return false;
                }
            }
            _instance.commitTrans();
        } catch (SQLException e) {
            _message = e.getMessage();
            return false;
        }
        
        return true;
    }

    @Override
    public String getMessage() {
        return _message;
    }
    
    private String getSQ_Master(){
        return "SELECT" +
                    "  a.sAcctNmbr" +
                    ", a.sBranchCd" +
                    ", a.sClientID" +
                    ", e.sCompnyNm" +
                    ", e.sMobileNo" +
                    ", a.sSerialID" +
                    ", b.sGCardNox" +
                    ", b.cDigitalx" +
                    ", c.sUserIDxx" +
                    ", g.sTransNox xTransNox" +
                    ", f.sTransNox" +
                    ", d.cCardStat" +
                    ", c.sIMEINoxx" +
                " FROM MC_AR_Master a" +
                        " LEFT JOIN MC_SO_Master g" +
                        " ON a.sApplicNo = g.sApplicNo" +
                            " AND a.sClientID = g.sClientID" +
                            " AND g.cTranStat NOT IN ('3', '7')" +
                    ", MC_Serial_Service b" +
                        " LEFT JOIN G_Card_App_User_Device c" + 
                            " LEFT JOIN G_Card_Master d" + 
                            " ON c.sGCardNox = d.sGCardNox" +
                            " LEFT JOIN Guanzon_Apps_Panalo f" + 
                            " ON c.sUserIDxx = f.sUserIDxx" +
				" AND f.sPanaloCD = " + SQLUtil.toSQL(_code) +
				" AND f.sSourceCd = 'MCSO'" +
				" AND f.cTranStat NOT IN ('3', '4')" +
                        " ON b.sGCardNox = c.sGCardNox" +
                    ", Client_Master e" +
                " WHERE a.sClientID = e.sClientID" +
                    " AND a.sSerialID = b.sSerialID" +
                    " AND a.sBranchCd LIKE 'M%'" +
                    " AND a.dPurchase >= '2022-12-01'" +
                    " AND a.cAcctstat = '0'" +
                    " AND a.cMotorNew = '1'" +
                " GROUP BY a.sAcctNmbr" +
                " HAVING b.cDigitalx = '1'" + 
                    " AND c.sUserIDxx IS NOT NULL" +
                    " AND f.sTransNox IS NULL";
    }
}
