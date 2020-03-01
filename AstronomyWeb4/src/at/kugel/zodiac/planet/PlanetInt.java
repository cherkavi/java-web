package at.kugel.zodiac.planet;
/**
   Interface f&uuml;r Planetenberechnungen. Minimales Interface, um andere
   Basistypen zu erm&ouml;glichen. Geht davon aus, dass das Planetensystem
   sich selber zeichnet. Auch die Anzahl der betrachteten K&ouml;rper ist
   variabel und jeweils in der Basisimplemetierung festgelegt.
   @author Kugel, <i>Theossos Comp Group</i>
   @version 1.12 - removed unused code in AstroLib
*/
public interface PlanetInt {

   /** Liefert Koordinaten der Planeten in Radiant.
       @return ein Array der Planetenpositionen in Radiant. */
   public double[] getPlanetsR();

   /** Namen der Planetennamen als Text.
       @return ein Array der Planetennamen. */
   public String[] getPlanetsText();

   /** Namen des Planetenberechnungs-Algorithmus.
       @return Name des Systems. */
   public String getPlanetName();

   /** Setzt Werte f&uuml;r das PlanetenSystem.
       @param T Anzahl Julianischer Jahrhunderte seit 1900.
       @param SO Sideral Offset f&uuml;r Sternenhoroskope, nur f&uuml;r
              Mond notwendig. */
   public void setPlanets(double T, double SO);

}
