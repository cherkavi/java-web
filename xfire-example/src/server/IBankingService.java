package server;

public interface IBankingService {
    public String transferFunds(String fromAccount, 
    						    String toAccount, 
    						    double amount, 
    						    String currency);
    
    /** Демонстрация возвращения объекта - должен полностью сохраняться package (то есть server.Account, а не client.Account, или не org.server.Account)<br>
     * в случае кроссплатформенности - нужно использовать JSON 
     * */
    public Account getAccountByKod(Integer kod);
}

