package main.jsf_bean;

import java.text.SimpleDateFormat;

import javax.faces.bean.CustomScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;

import main.spring_bean.TempBean;


@ManagedBean(name="page_bean")
@RequestScoped
public class PageBean {
	
	@ManagedProperty(value = "#{spring_bean}")
	private TempBean bean;

	public void setBean(TempBean bean){
		this.bean=bean;
	}
	
	public TempBean getBean(){
		return this.bean;
	}
	
	/**
	 * получить временной штамп
	 * @return вернуть время в виде: <b>HH:mm:ss</b>
	 */
	public String getTimeStamp(){
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
		return sdf.format(new java.util.Date());
	}
	
	public String getBeanString(){
		if(this.bean!=null){
			return this.bean.toString();
		}else{
			return null;
		}
	}
	
}
