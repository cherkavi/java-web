package server;

import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.util.Date;


/** XFire WebServices sample implementation class. 
 */
public class BankingService implements IBankingService {
    
    //Default constructor.
    public BankingService(){    
    }
    
    /** */
    public Account getAccountByKod(Integer kod){
    	return new Account(kod,"Name:"+kod.toString(),"Owner:"+kod.toString(),new Date());
    }
    
    /** Transfers fund from one account to another.
    */
    public String transferFunds(String fromAccount, 
    							String toAccount, 
    							double amount, 
    							String currency){
        String statusMessage = "";
        //Call business objects and other components to get the job done.
        //Then create a status message and return.
        try {
            NumberFormat formatter = new DecimalFormat("###,###,###,###.00");       
            statusMessage = "COMPLETED: " + currency + " " + formatter.format(amount)+ 
            " was successfully transferred from A/C# " + fromAccount + " to A/C# " + toAccount;
        } catch (Exception e){
            statusMessage = "BankingService.transferFunds(): EXCEPTION: " + e.toString();
        }
        return statusMessage;
    }
    
}
