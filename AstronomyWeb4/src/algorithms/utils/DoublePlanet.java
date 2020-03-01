package algorithms.utils;

import java.io.Serializable;

/** две планеты, которые образуют аспект  */
public class DoublePlanet implements Serializable{
	private final static long serialVersionUID=1L;
	
	private PlanetName planet1;
	private PlanetName planet2;
	/** угол между планетами  */
	private Float angle;
	private Float kpd;
	
	/** две планеты, которые образуют аспект  */
	public DoublePlanet(PlanetName planet1, PlanetName planet2){
		this.planet1=planet1;
		this.planet2=planet2;
	}
	
	/** две планеты, которые образуют аспект  */
	public DoublePlanet(PlanetName planet1, PlanetName planet2, Float angle, Float kpd){
		this.planet1=planet1;
		this.planet2=planet2;
		this.angle=angle;
		this.kpd=kpd;
	}
	
	/** получить первую планету  */
	public PlanetName getPlanet1(){
		return this.planet1;
	}
	
	/** получить вторую планету  */
	public PlanetName getPlanet2(){
		return this.planet2;
	}
	
	/** получить угол между планетами 
	 * @return null - если угол не задан
	 * */
	public Float getAngle(){
		return this.angle;
	}

	/** получить угол между планетами 
	 * @return null - если угол не задан
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
