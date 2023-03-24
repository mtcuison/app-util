package org.gua.factory;

import org.rmj.appdriver.agent.GRiderX;

public interface ILoveMyJobValidator {
    public void setGRider(GRiderX foValue);
    public void setWithParent(boolean fbValue);
    public boolean Run(String fsBranchCd, String fsDateFrom, String fsDateThru);
    
    public String getMessage();
}
