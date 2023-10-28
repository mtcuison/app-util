
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.gua.factory.ILoveMyJob;
import org.gua.factory.ILoveMyJobValidator;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.rmj.appdriver.agent.GRiderX;
import org.rmj.lib.net.MiscReplUtil;
import org.rmj.replication.utility.LogWrapper;

public class testGetCebuana {
    public static void main (String [] args){
        LogWrapper logwrapr = new LogWrapper("testCreateRaffle", "app-util.log");
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
        
        String lsSQL = "SELECT * FROM XAPITrans" +
                        " WHERE dReceived LIKE '2023-04-21%'";
        
        ResultSet loRS = instance.executeQuery(lsSQL);
        
        try {
            JSONObject loJSON;
            JSONParser loParser = new JSONParser();
            while (loRS.next()){
                loJSON = (JSONObject) loParser.parse(loRS.getString("sPayloadx"));
            
                MiscReplUtil.fileWrite("d:/cebuana_04112023.txt", 
                        (String) loJSON.get("branch") + "\t" + 
                        (String) loJSON.get("referno") + "\t" +
                        (String) loJSON.get("datetime") + "\t" +
                        (String) loJSON.get("account") + "\t" +
                        (String) loJSON.get("name") + "\t" +
                        (String) loJSON.get("address") + "\t" +
                        (String) loJSON.get("mobile") + "\t" +
                        (String) loJSON.get("amount") + "\n", 
                        true);
                System.out.println((String) loJSON.get("account"));
            }
        } catch (SQLException | ParseException ex) {
            ex.printStackTrace();
            logwrapr.info(ex.getMessage());
            System.exit(1);
        }
        
        logwrapr.info("End of Process!");
    }
}
