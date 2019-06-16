package gui.place_order;

import java.io.Serializable;

import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

import utility.WicketUtility;

/** Панель, которая отображает контактные данные по клиентам */
public class PanelContakt extends Panel{
	private final static long serialVersionUID=1L;
	private TextField field_surname;
	private TextField field_name;
	private TextField field_address;
	private TextField field_phone;
	private TextField field_email;
	
	private Label field_error_surname;
	private Label field_error_name;
	private Label field_error_address;
	private Label field_error_phone;
	private Label field_error_email;
	
	private ContaktData field_model=new ContaktData();
	
	public PanelContakt(String id){
		super(id);
		initComponents();
	}
	
	/** получить введенные пользователем данные в форму "Контакты"*/
	public ContaktData getContaktData(){
		return this.field_model;
	}
	/** возвращает True, если хотя бы один из параметров содержит неверный ввод данных */
	public boolean isValuesError(){
		boolean return_value=false;
		
		String error_surname=this.getErrorSurname();
		String error_name=this.getErrorName();
		String error_address=this.getErrorAddress();
		String error_phone=this.getErrorPhone();
		String error_email=this.getErrorEmail();

		if(error_surname==null){
			setValidLabel(this.field_error_surname);
		}else{
			setErrorLabel(this.field_error_surname,error_surname);
			return_value=true;
		};
		
		if(error_name==null){
			setValidLabel(this.field_error_name);
		}else{
			setErrorLabel(this.field_error_name,error_name);
			return_value=true;
		};

		if(error_address==null){
			setValidLabel(this.field_error_address);
		}else{
			setErrorLabel(this.field_error_address,error_address);
			return_value=true;
		};
		
		if(error_phone==null){
			setValidLabel(this.field_error_phone);
		}else{
			setErrorLabel(this.field_error_phone,error_phone);
			return_value=true;
		};

		if(error_email==null){
			setValidLabel(this.field_error_email);
		}else{
			setErrorLabel(this.field_error_email,error_email);
			return_value=true;
		};

		return return_value;
	}
	
	/** установить указанный Label сигнализирующий о неправильном вводе данных */
	private void setErrorLabel(Label label, String error_text){
		String style_string=WicketUtility.getStyleString(label);
		style_string=WicketUtility.addStyleElement(style_string, "color", "red");
		style_string=WicketUtility.addStyleElement(style_string, "font-weight", "bold");
		label.add(new SimpleAttributeModifier("style",style_string));
		label.setModelObject(error_text);
	}
	/** очистить указанный Label, сигнализирующий о неправильном вводе данных */
	private void setValidLabel(Label label){
		label.setModelObject("");
	}
	
	/** инициализация данных*/
	private void initComponents(){
		field_surname=new TextField("surname",new PropertyModel(field_model,"surname"));
		field_name=new TextField("name",new PropertyModel(field_model,"name"));
		field_address=new TextField("address",new PropertyModel(field_model,"address"));
		field_phone=new TextField("phone",new PropertyModel(field_model,"phone"));
		field_email=new TextField("email",new PropertyModel(field_model,"email"));
		
		field_error_surname=new Label("error_surname","");
		field_error_name=new Label("error_name","");
		field_error_address=new Label("error_address","");
		field_error_phone=new Label("error_phone","");
		field_error_email=new Label("error_email","");
		
		field_surname.setRequired(false);
		field_name.setRequired(false);		
		field_address.setRequired(false);
		field_phone.setRequired(false);
		field_email.setRequired(false);
		
		this.add(field_surname);
		this.add(field_name);
		this.add(field_address);
		this.add(field_phone);
		this.add(field_email);
		this.add(field_error_surname);
		this.add(field_error_name);
		this.add(field_error_address);
		this.add(field_error_phone);
		this.add(field_error_email);
	}
	
	/** get Surname*/
	private String getSurname(){
		return getStringFromTextFieldModel(this.field_surname);
	}
	/** get Name*/
	private String getName(){
		return getStringFromTextFieldModel(this.field_name);
	}
	/** get Address*/
	private String getAddress(){
		return getStringFromTextFieldModel(this.field_address);
	}
	/** get Phone*/
	private String getPhone(){
		return getStringFromTextFieldModel(this.field_phone);
	}
	/** get E-mail*/
	private String getEmail(){
		return getStringFromTextFieldModel(this.field_email);
	}
	
	/** получить ошибку ввода данных для поля Surname
	 * @return null если данные введены правильно, либо же возвращает текст ошибки 
	 * */
	private String getErrorSurname(){
		String surname=this.getSurname();
		if(surname!=null){
			if(surname.length()<2){
				return "Поле фамилия менее 2 символов";
			}
			if(surname.length()>20){
				return "Поле фамилия более 20 символов";
			}
			return null;
		}else{
			return null;
		}
	}

	/**
	 * получить ошибку ввода данных в поле Name
	 * @return null если ввод правильный, либо же текст ошибки   
	 */
	private String getErrorName(){
		String name=this.getName();
		if(name!=null){
			if(name.length()<2){
				return "Поле имя менее 2 символов";
			}
			if(name.length()>20){
				return "Поле имя более 20 символов";
			}
			return null;
		}else{
			return "Введите Имя";
		}
	}
	
	/** 
	 * получить ошибку ввода данных в поле Address 
	 * @return null если ввод правильный, либо же возвращает текст ошибки 
	 */
	private String getErrorAddress(){
		String address=this.getAddress();
		if(address!=null){
			if(address.length()<10){
				return "Поле адрес менее 10 символов";
			}
			if(address.length()>40){
				return "Поле адрес более 40 символов";
			}
			return null;
		}else{
			return "Введите данные в поле адрес";
		}
	}
	/**
	 * получить ошибку ввода данных в поле Phone
	 * @return null если ошибок нет, либо же вернуть текст ошибки 
	 */
	private String getErrorPhone(){
		String phone=this.getPhone();
		if(phone!=null){
			if(phone.length()<7){
				return "Введите корректное значение в поле Phone";
			}
			if(phone.length()>30){
				return "Введите корректное значение в поле Phone";
			}
			return null;
		}else{
			return "Введите контактный телефон";
		}
	}
	
	/** получить ошибку ввода данных в поле E-mail 
	 * @return null если данные введены правильно, либо же вернуть текст ошибки
	 */
	private String getErrorEmail(){
		String email=this.getEmail();
		if(email!=null){
			return null;
		}else{
			return null;
		}
	}
	
	/** получить строку с текстом из текстового поля*/
	private String getStringFromTextFieldModel(TextField textfield){
		if(textfield.getModelObject()!=null){
			return (String)textfield.getModel().getObject();
/*			System.out.println("Model Object: "+textfield.getModel().getObject());
			ContaktData contakt_data=(ContaktData)textfield.getModel().getObject();
			if(textfield==this.field_surname){
				return contakt_data.getSurname();
			};
			if(textfield==this.field_name){
				return contakt_data.getName();
			};
			if(textfield==this.field_address){
				return contakt_data.getAddress();
			};
			if(textfield==this.field_phone){
				return contakt_data.getPhone();
			};
			if(textfield==this.field_email){
				return contakt_data.getEmail();
			};
			return "";
*/			
		}else{
			//System.out.println("get Model Object is null");
			return null;
		}
	}
}

class ContaktData implements Serializable{
	private final static long serialVersionUID=1L;
	
	private String surname;
	private String name;
	private String address;
	private String phone;
	private String email;
	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}
	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
}
