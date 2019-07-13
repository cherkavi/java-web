package gui.place_order;

import gui.order_product.PanelTrolley;
import gui.thanks.Thanks;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;

import database.Utility;

import utility.ShopSession;

public class PlaceOrder extends WebPage{
	private PanelContakt field_panel_contakt;
	
	public PlaceOrder(){
		initComponents();
	}
	
	/** create and init Components */
	private void initComponents(){
		field_panel_contakt=new PanelContakt("panel_contakt");
		/** �����, ������� �������� ���������� ������ � ������ */
		Form form_main=new Form("form_main"){
			private final static long serialVersionUID=1L;
			@Override
			public void onError(){
				onFormError();
			}
		};
		
		form_main.add(new Button("button_send"){
			private static final long serialVersionUID = 1L;
			public void onSubmit(){
				// ��������� �� ���������� ��������� ������ � ������ ���������
				if(isValidValues()==true){
					// ������ �������
					// ���������� ��������� ������ � ������� �������
					if(((ShopSession)getSession()).getTrolley().fixedOrder(getClientKod())){
						((ShopSession)getSession()).getTrolley().clear();
						setResponsePage(new Thanks());
					}else{
						setResponsePage(new Thanks("Error in save Order"));
					}
				}else{
					// ������ �� ������� - ������� ������ ��� ��� 
					setResponsePage(PlaceOrder.this);
				}
			}
		});
		form_main.add(new Button("button_pay"){
			private static final long serialVersionUID=1L;
			public void onSubmit(){
				System.out.println("ButtonPay:");
				// TODO reactin on button_pay.click;
			}
		});
		form_main.add(new Button("button_cancel"){
			private static final long serialVersionUID = 1L;
			@Override
			public void onSubmit(){
				setResponsePage(((ShopSession)getSession()).popWebPage());
			}
		});
		form_main.add(field_panel_contakt);
		form_main.add(new PanelTrolley("panel_trolley"));
		this.add(form_main);
	}
	
	
	/** reaction on Form Error (one of validators is not Valid)*/
	private void onFormError(){
		/*if(isValidValues()){
			
		}else{
			setResponsePage(this);
		}*/
	}
	
	/** ��������� ��������� ���� �� ���������� ������ */
	private boolean isValidValues(){
		boolean return_value=true;
		if(field_panel_contakt.isValuesError()){
			return_value=false;
		}else{
			return_value=true;
		}
		return return_value;
	}
	
	/** �������� ��� �������, �� �������� ����� �������� ������ ����� 
	 *  @return 0, ���� ��������� �����-���� ������ �� ����� ������� ����������-�������� �������
	 */
	private int getClientKod(){
		// ������� ������ ������� � ������� ��� ���������� ID
		ContaktData contakt_data=this.field_panel_contakt.getContaktData();
		return Utility.saveClients(null, 
								   contakt_data.getEmail(), 
								   contakt_data.getPhone(), 
								   contakt_data.getAddress(), 
								   contakt_data.getName(), 
								   contakt_data.getSurname(), 
								   null);
	}
}
