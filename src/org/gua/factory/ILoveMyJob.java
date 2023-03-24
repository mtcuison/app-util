package org.gua.factory;

import org.gua.circle.panalo.LRPayment;
import org.gua.circle.panalo.LRPaymentPR;
import org.gua.circle.panalo.MCSales;
import org.gua.circle.panalo.MPSales;
import org.gua.circle.panalo.MPSalesAcc;
import org.gua.circle.panalo.OfficialReceipt;
import org.gua.circle.panalo.ProvisionaryReceipt;
import org.gua.circle.panalo.SPSales;

public class ILoveMyJob {
    public enum Type{
        LR_PAYMENT,
        LR_PAYMENT_PR,
        OFFICIAL_RECEIPT,
        PROVISIONARY_RECEIPT,
        MC_SALES,
        SP_SALES,
        MP_SALES,
        MP_SALES_ACC
    }
    
    public static ILoveMyJobValidator make(Type foType){
        switch (foType) {
            case LR_PAYMENT:
                return new LRPayment();
            case LR_PAYMENT_PR:
                return new LRPaymentPR();
            case OFFICIAL_RECEIPT:
                return new OfficialReceipt();
            case PROVISIONARY_RECEIPT:
                return new ProvisionaryReceipt();
            case MC_SALES:
                return new MCSales();
            case SP_SALES:
                return new SPSales();
            case MP_SALES:
                return new MPSales();
            case MP_SALES_ACC:
                return new MPSalesAcc();
            default:
                return null;
        }
    }
}
