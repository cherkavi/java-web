/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import javax.faces.event.ActionEvent;

/**
 *
 * @author Администратор
 */
public class CheckINN {
    private String fieldValue=null;
    private String checkError=null;
    private boolean inBlackList=false;
    private boolean showResult=false;

    public CheckINN(){
    	System.out.println("constructor create");
    }


    public String getFieldValue(){
        return this.fieldValue;
    }

    public void setFieldValue(String value){
        this.fieldValue=value;
    }



    public void setCheckError(String value){
        this.checkError=value;
    }

    public String getCheckError(){
        return this.checkError;
    }


    public boolean isInBlackList(){
        return this.inBlackList;
    }

    public void setInBlackList(boolean value){
        this.inBlackList=value;
    }

    public String goToBlack(){
    	System.out.println("go to Black ");
        return "goto_black";
    }

    public String goToClear(){
    	System.out.println("go to Clear");
        return "goto_clear";
    }
    
    public String checkINN(){
    	System.out.println("checkINN");
    	this.showResult=true;
        if(this.fieldValue.startsWith("0")){
        	this.showResult=true;
            this.inBlackList=true;
            return null; // direct page "page_black";
        }

        if(this.fieldValue.startsWith("9")){
        	this.showResult=true;
            this.inBlackList=false;
            return null;// direct page "page_clear";
        }

        this.setCheckError("error in con");
        return null;
    }


	public boolean isShowResult() {
		return showResult;
	}


	public void setShowResult(boolean showResult) {
		this.showResult = showResult;
	}
    
	public void actionBlack(ActionEvent event){
		System.out.println("ActionListener Black:");
	}
	
	public void actionClear(ActionEvent event){
		System.out.println("ActionListener Clear:");
	}
	
	public String goBack(){
		return "back";
	}
}
