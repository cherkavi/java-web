package algorithms.utils;

/** ������� � �������� */
public enum PlanetName {
	Sun(0,"������"),
	Moon(1,"����"),
	Mercury(2, "��������"),
	Venus(3, "������"),
	Mars(4, "����"),
	Jupiter(5, "������"),
	Saturn(6, "������"),
	Uranus(7, "����"),
	Neptune(8, "������"),
	Pluto(9, "������");

	private int kod;
	private String name;
	/** ������� � �������� */
	private PlanetName(int kod, String name){
		this.kod=kod;
		this.name=name;
	}
	
	public int getKod(){
		return this.kod;
	}
	
	public String getName(){
		return this.name;
	}

	/** �������� ������� �� ����  */
	public static PlanetName getPlanet(int x) {
		switch(x){
			case 0: return PlanetName.Sun;
			case 1: return PlanetName.Moon;
			case 2: return PlanetName.Mercury;
			case 3: return PlanetName.Venus;
			case 4: return PlanetName.Mars;
			case 5: return PlanetName.Jupiter;
			case 6: return PlanetName.Saturn;
			case 7: return PlanetName.Uranus;
			case 8: return PlanetName.Neptune;
			case 9: return PlanetName.Pluto;
			default: return null;
		}
	}
}
