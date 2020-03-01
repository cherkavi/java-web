package algorithms.utils;

/** планеты в расчетах */
public enum PlanetName {
	Sun(0,"Солнце"),
	Moon(1,"Луна"),
	Mercury(2, "Меркурий"),
	Venus(3, "Венера"),
	Mars(4, "Марс"),
	Jupiter(5, "Юпитер"),
	Saturn(6, "Сатурн"),
	Uranus(7, "Уран"),
	Neptune(8, "Нептун"),
	Pluto(9, "Плутон");

	private int kod;
	private String name;
	/** планеты в расчетах */
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

	/** получить планету по коду  */
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
