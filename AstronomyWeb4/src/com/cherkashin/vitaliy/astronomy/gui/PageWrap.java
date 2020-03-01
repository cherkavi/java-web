package com.cherkashin.vitaliy.astronomy.gui;

import org.apache.wicket.ajax.AjaxRequestTarget;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;

import algorithms.utils.DoublePlanet;

import com.cherkashin.vitaliy.astronomy.gui.aspect.Aspect;
import com.cherkashin.vitaliy.astronomy.gui.aspect_list.AspectList;
import com.cherkashin.vitaliy.astronomy.gui.aspect_text_comment.AspectTextComment;
import com.cherkashin.vitaliy.astronomy.gui.birth_day.BirthDay;
import com.cherkashin.vitaliy.astronomy.gui.menu.Menu;
import com.cherkashin.vitaliy.astronomy.gui.menu.business.Business;
import com.cherkashin.vitaliy.astronomy.gui.menu.business.section_11.Section11;
import com.cherkashin.vitaliy.astronomy.gui.menu.business.section_12.Section12;
import com.cherkashin.vitaliy.astronomy.gui.menu.business.section_13.Section13;
import com.cherkashin.vitaliy.astronomy.gui.menu.business.section_14.Section14;
import com.cherkashin.vitaliy.astronomy.gui.menu.business.section_15.Section15;
import com.cherkashin.vitaliy.astronomy.gui.menu.business.section_16.Section16;
import com.cherkashin.vitaliy.astronomy.gui.menu.partners.Partners;
import com.cherkashin.vitaliy.astronomy.gui.menu.partners.section_17.Section17;
import com.cherkashin.vitaliy.astronomy.gui.menu.partners.section_19.Section19;
import com.cherkashin.vitaliy.astronomy.gui.menu.partners.section_20.Section20;
import com.cherkashin.vitaliy.astronomy.gui.menu.partners.section_22.Section22;
import com.cherkashin.vitaliy.astronomy.gui.menu.partners.section_23.Section23;
import com.cherkashin.vitaliy.astronomy.gui.menu.partners.section_24.Section24;
import com.cherkashin.vitaliy.astronomy.gui.menu.partners.section_25.Section25;


import wicket_utility.ajax.IAjaxActionExecutor;

/** ��������-������� ��� ������� */
public class PageWrap extends WebPage implements IAjaxActionExecutor{
	/** ��������� ��� ������� */
	private WebMarkupContainer mainPanelContainer;
	private final static String idPanelMain="panel_main";
	
	/** ������ ��� �������� */
	public static final String actionBirthDay = "action_birth_day";
	/** ������ �������� */
	public static final String actionBirthDayAspectList = "action_birth_day_aspect_list";
	/** ������ ��� �������� */
	public static final String actionBirthDayAspect = "action_birth_day_aspect";
	/** ����, � ������� �������� ������������ ����� ���������� ���� ��������  */
	public static final String actionMenu = "action_menu";
	/** ��������� ����������� � ���� ������  */
	public static final String actionAspectTextComment="action_aspect_text_comment";
	/** ����������� ������ �������  */
	public static final String actionBusiness = "action_business";
	/** ����������� ������ ����������� */
	public static final String actionPartners= "action_partners";
	/**  ���������� ��������� 17*/
	public static final String actionPartners17="action_acquaintance_17";
	/**  ������� ��������� 19 */
	public static final String actionPartners19="action_acquaintance_19";
	/**  ����������� ���������� 20 */
	public static final String actionPartners20="action_acquaintance_20";
	/**  ���������������� ������� 22 */
	public static final String actionPartners22="action_acquaintance_22";
	/**  ������� ����� 23 */
	public static final String actionPartners23="action_acquaintance_23";
	/**  ������� ����� �������� 24 */
	public static final String actionPartners24="action_acquaintance_24";
	/**  ���� ������ ����� 25 */
	public static final String actionPartners25="action_acquaintance_25";
	
	/**  ������ � �������� 11*/
	public static final String actionPartners11="action_business_11";
	/**  �������/������� ������������ 12*/
	public static final String actionPartners12="action_business_12";
	/**  ������� 13*/
	public static final String actionPartners13="action_business_13";
	/**  ��������������� 14*/
	public static final String actionPartners14="action_business_14";
	/**  ������� �������� ������ 15*/
	public static final String actionPartners15="action_business_15";
	/**  ����� ����� 16*/
	public static final String actionPartners16="action_business_16";
	
	/** ��������-������� ��� ������� */
	public PageWrap(){
		initComponents();
	}
	
	private void initComponents(){
		this.mainPanelContainer=new WebMarkupContainer("main_panel_container");
		this.mainPanelContainer.setOutputMarkupId(true);
		this.add(mainPanelContainer);
		// ����� ����� � ���������
		this.mainPanelContainer.removeAll();
		this.mainPanelContainer.add(new BirthDay(idPanelMain, this));
	}
	
	@Override
	public int action(AjaxRequestTarget target, String name, Object... parameters) {
		if(name==null){
			return 1;
		}
		
		// INFO ����� �������� ����� ����� ����������
		if(name.equals(actionBirthDay)){
			/** ���� */
			this.mainPanelContainer.removeAll();
			this.mainPanelContainer.add(new BirthDay(idPanelMain, this));
			target.addComponent(this.mainPanelContainer);
			return 0;
		}
		
		if(name.equals(actionBirthDayAspectList)){
			/** ������ ���� ��������� �������� */
			this.mainPanelContainer.removeAll();
			this.mainPanelContainer.add(new AspectList(idPanelMain, this));
			target.addComponent(this.mainPanelContainer);
			return 0;
		}
		
		if(name.equals(actionBirthDayAspect)){
			/** ������ ���� ��������� �������� */
			this.mainPanelContainer.removeAll();
			this.mainPanelContainer.add(new Aspect(idPanelMain, this, (DoublePlanet)(parameters[0])));
			target.addComponent(this.mainPanelContainer);
			return 0;
		}
		
		if(name.equals(actionMenu)){
			/** ������ ���� ��������� �������� */
			this.mainPanelContainer.removeAll();
			this.mainPanelContainer.add(new Menu(idPanelMain, this));
			target.addComponent(this.mainPanelContainer);
			return 0;
		}
		
		if(name.equals(actionAspectTextComment)){
			/** ������ ���� ��������� �������� */
			this.mainPanelContainer.removeAll();
			this.mainPanelContainer.add(new AspectTextComment(idPanelMain, this, (DoublePlanet)parameters[0]));
			target.addComponent(this.mainPanelContainer);
			return 0;
		}

		if(name.equals(actionBusiness)){
			/** ������ ���� ��������� �������� */
			this.mainPanelContainer.removeAll();
			this.mainPanelContainer.add(new Business(idPanelMain, this));
			target.addComponent(this.mainPanelContainer);
			return 0;
		}

		if(name.equals(actionPartners)){
			/** ������ ���� ��������� �������� */
			this.mainPanelContainer.removeAll();
			this.mainPanelContainer.add(new Partners(idPanelMain, this));
			target.addComponent(this.mainPanelContainer);
			return 0;
		}

		if(name.equals(actionPartners17)){
			/** ���������� ��������� */
			this.mainPanelContainer.removeAll();
			this.mainPanelContainer.add(new Section17(idPanelMain, this));
			target.addComponent(this.mainPanelContainer);
			return 0;
		}
		if(name.equals(actionPartners19)){
			/** ������� ��������� */
			this.mainPanelContainer.removeAll();
			this.mainPanelContainer.add(new Section19(idPanelMain, this));
			target.addComponent(this.mainPanelContainer);
			return 0;
		}
		if(name.equals(actionPartners20)){
			/** ����������� ���������  */
			this.mainPanelContainer.removeAll();
			this.mainPanelContainer.add(new Section20(idPanelMain, this));
			target.addComponent(this.mainPanelContainer);
			return 0;
		}
		if(name.equals(actionPartners22)){
			/** ���������������� ������� */
			this.mainPanelContainer.removeAll();
			this.mainPanelContainer.add(new Section22(idPanelMain, this));
			target.addComponent(this.mainPanelContainer);
			return 0;
		}
		if(name.equals(actionPartners23)){
			/** ������� ����� */
			this.mainPanelContainer.removeAll();
			this.mainPanelContainer.add(new Section23(idPanelMain, this));
			target.addComponent(this.mainPanelContainer);
			return 0;
		}
		if(name.equals(actionPartners24)){
			/** ������� ����� �������� */
			this.mainPanelContainer.removeAll();
			this.mainPanelContainer.add(new Section24(idPanelMain, this));
			target.addComponent(this.mainPanelContainer);
			return 0;
		}
		if(name.equals(actionPartners25)){
			/** ���� �����  */
			this.mainPanelContainer.removeAll();
			this.mainPanelContainer.add(new Section25(idPanelMain, this));
			target.addComponent(this.mainPanelContainer);
			return 0;
		}
		/*
		public static final String actionAcquaintance23="action_acquaintance_23";
		public static final String actionAcquaintance23="action_acquaintance_24";
		public static final String actionAcquaintance25="action_acquaintance_25";
		*/
		if(name.equals(actionPartners11)){
			/** ������ � �������� */
			this.mainPanelContainer.removeAll();
			this.mainPanelContainer.add(new Section11(idPanelMain, this));
			target.addComponent(this.mainPanelContainer);
			return 0;
		}
		
		if(name.equals(actionPartners12)){
			/** �������, ������� ������������ */
			this.mainPanelContainer.removeAll();
			this.mainPanelContainer.add(new Section12(idPanelMain, this));
			target.addComponent(this.mainPanelContainer);
			return 0;
		}
		if(name.equals(actionPartners13)){
			/** ������� */
			this.mainPanelContainer.removeAll();
			this.mainPanelContainer.add(new Section13(idPanelMain, this));
			target.addComponent(this.mainPanelContainer);
			return 0;
		}
		if(name.equals(actionPartners14)){
			/** ��������������� */
			this.mainPanelContainer.removeAll();
			this.mainPanelContainer.add(new Section14(idPanelMain, this));
			target.addComponent(this.mainPanelContainer);
			return 0;
		}
		if(name.equals(actionPartners15)){
			/** ������� ������� ������ */
			this.mainPanelContainer.removeAll();
			this.mainPanelContainer.add(new Section15(idPanelMain, this));
			target.addComponent(this.mainPanelContainer);
			return 0;
		}
		if(name.equals(actionPartners16)){
			/** ����� ����� */
			this.mainPanelContainer.removeAll();
			this.mainPanelContainer.add(new Section16(idPanelMain, this));
			target.addComponent(this.mainPanelContainer);
			return 0;
		}
		return 1; // ���������� ������ �� ������
	}
}
