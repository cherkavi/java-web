/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.actions;

import javax.faces.event.ActionEvent;

/** ������� ��� ����������� ������ �������� �������� �� ��������  */
public class ActionElement {
    private int id;
    private String startPage;
    private int sessionId;
    private String parseBegin;
    private String result;

    /** ������� ��� ����������� ������ �������� �������� �� ��������
     * @param id - ���������� ������������� ������� ��������
     * @param startPage - ��������� �������� �������
     * @param sessionId - ���������� ���������� �����
     * @param parseBegin - ���� ������ ��������
     * @param result - ��������� �������� 
     */
    public ActionElement(int id, String startPage, int sessionId, String parseBegin, String result){
        this.id=id;
        this.startPage=startPage;
        this.sessionId=sessionId;
        this.parseBegin=parseBegin;
        this.result=result;
    }

    public int getId() {
        return id;
    }

    public String getStartPage() {
        return startPage;
    }

    public int getSessionId() {
        return sessionId;
    }

    public String getParseBegin() {
        return parseBegin;
    }

    public String getResult() {
        return result;
    }

    public String gotoDetail(ActionEvent event){
    	System.out.println("goto Detail");
    	return "/actions/ParserSession";
    }
}
