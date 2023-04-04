package org.gua.factory;

import org.gua.circle.panalo.LRPayment;
import org.gua.circle.panalo.LRPaymentPR;
import org.gua.circle.panalo.MCSales;
import org.gua.circle.panalo.MPSales;
import org.gua.circle.panalo.MPSalesAcc;
import org.gua.circle.panalo.OfficialReceipt;
import org.gua.circle.panalo.ProvisionaryReceipt;
import org.gua.circle.panalo.SPSales;
import org.gua.circle.panalo.PanaloRaffle;
import org.gua.circle.panalo.PanaloWinners;
import org.gua.circle.panalo.RaffleNotificationEnd;
import org.gua.circle.panalo.RaffleNotificationStart;

public class ILoveMyJob {
    public enum Type{
        LR_PAYMENT,
        LR_PAYMENT_PR,
        OFFICIAL_RECEIPT,
        PROVISIONARY_RECEIPT,
        MC_SALES,
        SP_SALES,
        MP_SALES,
        MP_SALES_ACC,
        RAFFLE_DATE,
        RAFFLE_WINNER,
        RAFFLE_START,
        RAFFLE_END,
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
            case RAFFLE_DATE:
                return new PanaloRaffle();
            case RAFFLE_WINNER:
                return new PanaloWinners();
            case RAFFLE_START:
                return new RaffleNotificationStart();
            case RAFFLE_END:
                return new RaffleNotificationEnd();
            default:
                return null;
        }
    }
}
