package at.kugel.zodiac.house;
/**
   Interface f&uuml;r Hausberechnungen. Minimales Interface, um andere
   Basistypen der Hausberechnung zu erm&ouml;glichen. Geht davon aus, dass
   das Haus sich selber zeichnet. Auch die Anzahl der H&auml;user ist
   variabel und jeweils in der Basis-Implemetierung festgelegt.
   @author Kugel, <i>Theossos Comp Group</i>
   @version 1.12 - removed unused code in AstroLib
*/
public interface HouseInt { 

   /** Koordinaten der Hausgrenzen in Radiant.
       @return Koordinaten der Hausgrenzen in Radiant. */
   public double[] getHousesR();

   /** Namen der Hausgrenzen als Text.
       @return Namen der Hausgrenzen als Text. */
   public String[] getHousesText();

   /** Namen des Hausberechnungs Algorithmus.
       @return Name des Systems. */
   public String getHouseName();

   /** G&uuml;ltigkeit des Hausberechnungs Algorithmus.
       @return Radiant des Ranges, d.h. range(1) &lt; r &lt; range(1). */
   public double[] getValidityRange();

   /** Liefert Position eines Winkels im Haussystem aus Grad.
       @param d Position des K&ouml;rpers in Grad.
       @return Wert von 0 bis <var>n-1</var>, also in welchem der
               H&auml;user es steht. */
   public int HouseFromD(double d);

   /** Liefert Position eines Winkels im Haussystem aus Radiant.
       @param r Position des K&ouml;rpers in Radiant.
       @return Wert von 0 bis <var>n-1</var>, also in welchem der
               H&auml;user es steht. */
   public int HouseFromR(double r);

   /** Setzt Werte f&uuml;r HausSystem.
       @param L Latitude, d.h. geografische Breite der Position in Radiant.
       @param RA Erh&ouml;hung in Radiant.
       @param OB Schiefe der Ekliptik in Radiant.
       @param SO Sideral Offset f&uuml;r Sternenhoroskope.
   */
   public void setHouses(double L, double RA, double OB, double SO);

}

