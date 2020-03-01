package at.kugel.zodiac.planet;

import at.kugel.zodiac.util.CalcUtil;
/**
   Basisklasse f&uuml;r Planetenpositionen. Implementiert eine Nullimplementierung,
   d.h. die Planeten stehen mitten in ihren Zeichen. Liefert aber sonst alle
   Methoden f&uuml;r 10 Planeten sowie deren grafische Darstellung. Werden
   weitere K&ouml;rper implementiert, so m&uuml;ssen nur die Namen und
   anderen Arrays überschrieben werden und eine andere Datei der Bilder
   geladen werden.
   @author Kugel, <i>Theossos Comp Group</i>
   @version 1.12 - removed unused code in AstroLib, hardcoded planet names
*/
public abstract class PlanetBasic implements PlanetInt {
	
   /** Number of Planets. */
   protected final int NUMBER_PLANET = 10;

   /** Die Namen der Objekte, die betrachtet werden. Es sind NUMBER_PLANET Objekte. */
   protected final String[] NAME_PLANET = {
      "Sun","Moon","Mercury","Venus","Mars","Jupiter","Saturn","Uranus","Neptune","Pluto"
   };

// ---------------------------------------------------------------------
// -------------------------- Planet attributes ------------------------
// ---------------------------------------------------------------------

   /** Anzahl Julianischer Jahrhunderte seit 1900, <code>T</code>. */
   protected double julianCenturies;
   /** ? (Sidereal offset) in Radiant, verschiebt Location, nur f&uuml;r
       Mond notwendig, <code>real rSid</code>. */
   protected double siderealOffset;
   /** Koordinaten der Planeten in Radiant. */
   protected final double[] planetsR = new double[NUMBER_PLANET];

   /** Leerer Constructor, muss nachher setPlanets aufrufen. Da Constructoren
       nicht vererbt werden, sind alle anderen Constructoren sinnlos. Nur
       das Bild wird geladen. */
   public PlanetBasic() { 
      // do nothing      
   }

   /** Liefert Koordinaten der Planeten in Radiant.
       @return ein Array der Planetenpositionen in Radiant. */
   public final double[] getPlanetsR() { return planetsR; }

   /** Namen der Hausgrenzen als Text.
       @return ein Array der Planetennamen. */
   public final String[] getPlanetsText() { return NAME_PLANET; }

   /** Namen des Planetenberechnungs Algorithmus. Diese Methode muss
       f&uuml;r andere Planetensysteme &uuml;berschrieben werden.
       @return Name des Systems. */
   public String getPlanetName() { return "Null"; }

   /** Setzt Werte f&uuml;r PlanetenSystem.
       @param T Anzahl Julianischer Jahrhunderte seit 1900.
       @param SO Sideral Offset f&uuml;r Sternenhoroskope, f&uuml;r Mond notwendig.
   */
   public final void setPlanets(double T, double SO) {
      this.julianCenturies = T;
      this.siderealOffset = SO;
      calcPlanets();
      calcAspects();
   }

   /** Berechnet Planetenpositionen in Radiant. Diese Methode muss f&uuml;r
       andere Planetensysteme &uuml;berschrieben werden. */
   protected void calcPlanets() {
      planetsR[0] = CalcUtil.RFromD(15.0+30.0*4);
      planetsR[1] = CalcUtil.RFromD(15.0+30.0*3);
      planetsR[2] = CalcUtil.RFromD(15.0+30.0*2);
      planetsR[3] = CalcUtil.RFromD(15.0+30.0*6);
      planetsR[4] = CalcUtil.RFromD(15.0+30.0*0);
      planetsR[5] = CalcUtil.RFromD(15.0+30.0*8);
      planetsR[6] = CalcUtil.RFromD(15.0+30.0*9);
      planetsR[7] = CalcUtil.RFromD(15.0+30.0*10);
      planetsR[8] = CalcUtil.RFromD(15.0+30.0*11);
      planetsR[9] = CalcUtil.RFromD(15.0+30.0*7);
   }

   /** Berechnet Planetenaspekte, d.h. Verbindungen zwischen Planeten durch
       die Winkel. Berechnung mittels Multiplikation mit dem Aspekt und dann
       nach Konjuunktionen suchen, d.h. Um Square zu suchen machen wir *4
       mod 360 und suchen nach Konjunktionen. */
   protected final void calcAspects() {
      // dropped
   }

// ---------------------------------------------------------------------
// ---------------- Methods for painting/writing -----------------------
// ---------------------------------------------------------------------

   /** Convert Planets-Object to String for debug purpose.
       @return a string representation of the fields. */
   public final String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("Planets \"");
      buf.append(getPlanetName());
      buf.append("\" [");
         for (int i =0;i<NUMBER_PLANET; i++) {
            buf.append(NAME_PLANET[i]);
            buf.append(':');
            CalcUtil.HMStringFromR(buf,planetsR[i]);
            buf.append("; ");
            if ((i == 2)||(i == 6)) buf.append('\n');
         }
      buf.append(']');
      return buf.toString();
   }

}
