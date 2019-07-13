package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Locale;

import org.apache.wicket.IRequestTarget;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.target.resource.ResourceStreamRequestTarget;
import org.apache.wicket.util.resource.FileResourceStream;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;
import org.apache.wicket.util.time.Time;

public class Main extends WebPage{
	private Model<String> modelOne;
	private Model<String> modelTwo;
	
	public Main(){
		initComponents();
	}
	
	private void initComponents(){
		Form<Object> formMain=new Form<Object>("form_main"){
			private final static long serialVersionUID=1L;
		};
		formMain.setOutputMarkupId(true);
		formMain.setMarkupId("form_main");
		
		modelOne=new Model<String>("");
		TextField<String> edit_one=new TextField<String>("edit_one",modelOne);
		edit_one.setRequired(false);
		formMain.add(edit_one);

		modelTwo=new Model<String>("");
		TextField<String> edit_two=new TextField<String>("edit_two",modelTwo);
		edit_two.setRequired(false);
		formMain.add(edit_two);
		
		Link span_one=new Link("span_one"){
			private static final long serialVersionUID=1L;
			@Override
			public void onClick() {
				System.out.println("span_one clicked");
				printModel();
			}
		};
		span_one.add(new SimpleAttributeModifier("onclick", "document.forms['form_main'].submit();"));
		//span_one.add(new SimpleAttributeModifier("href", ""));
		formMain.add(span_one);
		
		Link span_two=new Link("span_two"){
			private static final long serialVersionUID=1L;
			@Override
			public void onClick() {
/*				getRequestCycle().setRequestTarget(
									new ResourceStreamRequestTarget(
											new FileResourceStream(
													new File("d:\\a.wav"))));*/
				getRequestCycle().setRequestTarget(new ResourceStreamRequestTarget(new IResourceStream(){
					@Override
					public void close() throws IOException {
					}

					@Override
					public String getContentType() {
						return "application/pdf";
					}

					@Override
					public InputStream getInputStream()
							throws ResourceStreamNotFoundException {
						try{
							return new FileInputStream(new File("D:\\Book\\CorelDRAW_X4\\coreldraw_x4_s_n.pdf"));
						}catch(Exception ex){
							return null;
						}
					}

					@Override
					public Locale getLocale() {
						return Main.this.getLocale();
					}

					@Override
					public long length() {
						return (new File("D:\\Book\\CorelDRAW_X4\\coreldraw_x4_s_n.pdf")).length();
					}

					@Override
					public void setLocale(Locale arg0) {
					}

					@Override
					public Time lastModifiedTime() {
						return null;
					}
					
				}));
				System.out.println("span_two clicked");
				printModel();
			}
		};
		
		formMain.add(span_two);
		this.add(formMain);
	}
	
	private void printModel(){
		try{
			System.out.println("ModelOne:"+this.modelOne.getObject());
		}catch(NullPointerException ex){
			System.out.println("ModelOne: null");
		}
		try{
			System.out.println("ModelTwo:"+this.modelTwo.getObject());
		}catch(NullPointerException ex){
			System.out.println("ModelTwo: null");
		}
	}
}
