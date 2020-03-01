package algorithms.utils;

import java.io.Serializable;

/** ��� �������, ������� �������� ������  */
public class DoublePlanet implements Serializable{
	private final static long serialVersionUID=1L;
	
	private PlanetName planet1;
	private PlanetName planet2;
	/** ���� ����� ���������  */
	private Float angle;
	private Float kpd;
	
	/** ��� �������, ������� �������� ������  */
	public DoublePlanet(PlanetName planet1, PlanetName planet2){
		this.planet1=planet1;
		this.planet2=planet2;
	}
	
	/** ��� �������, ������� �������� ������  */
	public DoublePlanet(PlanetName planet1, PlanetName planet2, Float angle, Float kpd){
		this.planet1=planet1;
		this.planet2=planet2;
		this.angle=angle;
		this.kpd=kpd;
	}
	
	/** �������� ������ �������  */
	public PlanetName getPlanet1(){
		return this.planet1;
	}
	
	/** �������� ������ �������  */
	public PlanetName getPlanet2(){
		return this.planet2;
	}
	
	/** �������� ���� ����� ��������� 
	 * @return null - ���� ���� �� �����
	 * */
	public Float getAngle(){
		return this.angle;
	}

	/** �������� ���� ����� ��������� 
	 * @return null - ���� ���� �� �����
	 * */
	public Float getKpd(){
		return this.kpd;
	}

	
	@Override
	public boolean equals(Object anotherObject) {
		boolean returnValue=false;
		if(anotherObject==null){
			return returnValue;
		};
		if(anotherObject instanceof DoublePlanet){
			DoublePlanet destination=(DoublePlanet)anotherObject;
			if (  ((destination.planet1.equals(this.planet1)) && (destination.planet2.equals(this.planet2)))
				||((destination.planet1.equals(this.planet2)) && (destination.planet2.equals(this.planet1)))){
				returnValue=true;
			}else{
				returnValue=false;
			}
		}
		return returnValue;
	}
	
	@Override
	public String toString(){
		return "1: "+planet1.getName()+"     2: "+planet2.getName()+"   Angle: "+this.angle+"   KPD: "+this.kpd;
	}
}
