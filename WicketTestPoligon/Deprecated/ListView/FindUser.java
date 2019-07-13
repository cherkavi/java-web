package messenger.find_user;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import messenger.recipient_list.RecipientList;
import messenger.session.MessageSession;
import messenger.session.UserList;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import database.Utility;
import database.wrap.Users;

public class FindUser extends WebPage{
	private void out_debug(String information){
		System.out.print("FindUser ");
		System.out.print(" DEBUG: ");
		System.out.println(information);
	}
	
	private void out_error(String information){
		System.out.print("FindUser ");
		System.out.print(" ERROR: ");
		System.out.println(information);
	}
	
	private DataModel field_data_model=new DataModel();
	private List<Users> field_user_list=new ArrayList<Users>();
	
	public List<Users> getUser_list(){
		return this.field_user_list;
	}
	public void setUser_list(List<Users> list){
		this.field_user_list=list;
	}
	
	/** Map, который содержит соответствия CheckBox и User - для получения выделения пользователей */
	//private HashMap<User,?> field_selected_property=new HashMap<User,?>(); 
	
	public FindUser(){
		TextField field_find_nick=new TextField("find_nick");
		TextField field_find_full_name=new TextField("find_full_name");
		TextField field_find_short_name=new TextField("find_short_name");
		/** в данном случае данные будут только получаться методом getUser_list(), и не будут устанавливаться */
		ListView user_list=new ListView("user_list",new PropertyModel(this,"user_list")){
			private static final long serialVersionUID = 1L;
			@Override
			protected void populateItem(ListItem item) {
				Users record=(Users)item.getModelObject();
				item.add(new CheckBox("selected",new PropertyModel(record,"selected")));
				item.add(new Label("nick",record.getNick()));
				item.add(new Label("full_name",record.getName_full()));
				item.add(new Label("short_name",record.getName_short()));
			}
		};
		Button button_find=new Button("button_find"){
			private static final long serialVersionUID=1L;
			public void onSubmit(){
				on_button_find_click();
			}
		};
		Button button_add=new Button("button_add"){
			private static final long serialVersionUID = 1L;
			public void onSubmit(){
				on_button_add_click();
			}
		};
		Button button_cancel=new Button("button_cancel"){
			private static final long serialVersionUID = 1L;
			public void onSubmit(){
				on_button_cancel_click();
			}
		};
		
		
		Form form_find=new Form("form_find",
								new CompoundPropertyModel(field_data_model));
		form_find.add(field_find_nick);
		form_find.add(field_find_full_name);
		form_find.add(field_find_short_name);
		form_find.add(button_find);
		form_find.add(user_list);
		form_find.add(button_add);
		form_find.add(button_cancel);
		this.add(form_find);
	}
	
	/** реакция на нажатие клавиши Add */
	private void on_button_add_click(){
		out_debug("   button add click ");
		try{
			MessageSession session=(MessageSession)this.getSession();
			UserList list=session.getUserList("RECIPIENT");
			if(list==null){
				list=new UserList("RECIPIENT");
				session.addUserList(list);
			}
			try{
				for(int counter=0;counter<this.field_user_list.size();counter++){
					if(this.field_user_list.get(counter).getSelected()){
						list.addUser(this.field_user_list.get(counter));
					}
				}
			}catch(NullPointerException ex){
				out_error("on_button_add_click NullPointerException:"+ex.getMessage());
			}
			
		}catch(Exception ex){
			out_error("on_button_add_click Exception:"+ex.getMessage());
		}
		setResponsePage(RecipientList.class);
	}
	
	/** реакция на нажатие клавиши Cancel */
	private void on_button_cancel_click(){
		setResponsePage(RecipientList.class);
	}
	
	
	/** реакция на получение объектом ответа от формы */
	private void on_button_find_click(){
		out_debug("Find Nick:"+field_data_model.getFind_nick());
		out_debug("Find Full Name:"+field_data_model.getFind_full_name());
		out_debug("Find Short Name:"+field_data_model.getFind_short_name());
		this.field_user_list=Utility.filterUserList( field_data_model.getFind_nick(), 
												    (field_data_model.getFind_full_name()==null)?"":field_data_model.getFind_full_name(), 
												    (field_data_model.getFind_short_name()==null)?"":field_data_model.getFind_short_name());
	}
}


/** модель данных для формы, которая содержит все события */
class DataModel implements Serializable{
	private static final long serialVersionUID = 1L;
	private String find_nick;
	private String find_full_name;
	private String find_short_name;
	private String button_find;
	
	public String getButton_find() {
		return button_find;
	}

	public void setButton_find(String button_find) {
		this.button_find = button_find;
	}

	public String getFind_nick() {
		return find_nick;
	}

	public void setFind_nick(String find_nick) {
		this.find_nick = find_nick;
	}

	public String getFind_full_name() {
		return find_full_name;
	}

	public void setFind_full_name(String field_full_name) {
		this.find_full_name = field_full_name;
	}

	public String getFind_short_name() {
		return find_short_name;
	}

	public void setFind_short_name(String find_short_name) {
		this.find_short_name = find_short_name;
	}
	
}
