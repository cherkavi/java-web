package server;

public interface IBankingService {
    public String transferFunds(String fromAccount, 
    						    String toAccount, 
    						    double amount, 
    						    String currency);
    
    /** ������������ ����������� ������� - ������ ��������� ����������� package (�� ���� server.Account, � �� client.Account, ��� �� org.server.Account)<br>
     * � ������ �������������������� - ����� ������������ JSON 
     * */
    public Account getAccountByKod(Integer kod);
}

