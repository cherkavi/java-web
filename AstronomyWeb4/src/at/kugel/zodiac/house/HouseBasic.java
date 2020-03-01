package at.kugel.zodiac.house;

import at.kugel.zodiac.util.CalcUtil;
/**
   Base class of all house calculations.
   Basisklasse f&uuml;r Hausberechnungen. Implementiert eine Nullimplementierung,
   d.h. die H&auml;user starten in den Zeichen. Liefert aber sonst alle
   Methoden f&uuml;r die H&auml;user. Andere Haussysteme sind als eigene Klassen
   implementiert.
   @author Kugel, <i>Theossos Comp Group</i>
   @version 1.12 - removed unused code in AstroLib, hardcoded house names.
*/
public abstract class HouseBasic extends Object implements HouseInt {

   /** Number of Houses. */
   protected static final int NUMBER_HOUSE = 12;

   /** Die Namen der H&auml;user, die betrachtet werden, k&ouml;nnen
       &uuml;berschrieben werden. */
   protected static final String[] NAME_HOUSE = {
        "AC","2","3","IC","5","6","DC","8","9","MC","11","12" };

// ---------------------------------------------------------------------
// -------------------------- House attributes -------------------------
// ---------------------------------------------------------------------

   /** Angles of one House in degree if equidistant houses are used. */
   protected static final int ANBLE_HOUSE_D = 360/NUMBER_HOUSE;
   /** Angles of one House in radiant if equidistant houses are used. */
   protected static final double ANGLE_HOUSE_R = 2.0*Math.PI/NUMBER_HOUSE;
   /** Konstante an der Ekliptik, wo manche Haussysteme nicht gelten. */
   protected static final double rAxis = 23.44578889;

   /** Validity range of the house system. */
   protected double[] range = {-CalcUtil.PIH,CalcUtil.PIH};

   /** Latitude, d.h. geografische Breite der Position in Radiant. */
   protected double latR;
   /** Erh&ouml;hung in Radiant, <code>real RA</code>. */
   protected double rightAscension;
   /** Schiefe der Ekliptik in Radiant (Obliquity of ecliptic), <code>real OB</code>. */
   protected double eclipticObliquity;
   /** ? (Sidereal offset) in Radiant, verschiebt Location, <code>real rSid</code>. */
   protected double siderealOffset;

   /** Aszendent in Radiant, <code>AS</code>. */
   protected double ascendant;
   /** Medium Coelii in Radiant, <code>MC</code>. */
   protected double midHeaven;
/*
   /** Vertex (was immer das ist) in Radiant, <code>VI</code>. *
   protected double vertex;

   /** East Punkt (was immer das ist) in Radiant. The East Point object is
      technically defined as the Ascendant's position at zero latitude, <code>EP</code>. *
   protected double eastPoint;
*/
   /** Koordinaten der Hausgrenzen in Radiant. */
   protected final double[] housesR = new double[NUMBER_HOUSE];

   /** Leerer Constructor, muss nachher setHouses aufrufen. */
   public HouseBasic() {
      // do nothing
   }

   /** Aszendent in Radiant.
       @return Aszendent in Radiant. */
   public final double getAscendant() { return ascendant; }

   /** Berechnet AC in Radiant. *
   public static double getAscendant(double eclipticObliquity, double rightAscension, double latR) {
      double ascendant = CalcUtil.Angle(-Math.sin(rightAscension)*Math.cos(eclipticObliquity)-
                                 Math.tan(latR)*Math.sin(eclipticObliquity),Math.cos(rightAscension));
      /*
      ascendant = Math.atan(Math.cos(rightAscension)/(-Math.sin(rightAscension)*Math.cos(eclipticObliquity)-
                  Math.tan(latR)*Math.sin(eclipticObliquity)));
      if (ascendant < 0.0) ascendant += Math.PI;
      if (Math.cos(rightAscension) < 0) ascendant += Math.PI;
      *
      // if (siderealOffset!=0.0) ascendant = CalcUtil.Mod2PI(ascendant+siderealOffset);
      return ascendant;
   } */

   /** Medium Coelii in Radiant.
       @return Medium Coelii in Radiant. */
   public final double getMidHeaven() { return midHeaven; }

   /** Berechnet MC in Radiant.
       @return MC in Radiant. * /
   public static double midheaven(double eclipticObliquity, double rightAscension) {
      double midHeaven = CalcUtil.Angle(Math.cos(eclipticObliquity),Math.tan(rightAscension));
      /*
      midHeaven = Math.atan(Math.tan(rightAscension)/Math.cos(eclipticObliquity));
      if (midHeaven < 0.0) midHeaven += Math.PI;
      if (rightAscension > Math.PI) midHeaven += Math.PI;
      // eigentlich if (Math.sin(rightAscension)<0) midHeaven += Math.PI;
      *
      // if (siderealOffset!=0.0) midHeaven = CalcUtil.Mod2PI(midHeaven+siderealOffset);
      return midHeaven;
   } */

/*
   /** Vertex (was immer das ist) in Radiant.
       @return Vertex (was immer das ist) in Radiant. * /
   public double getVertex() { return vertex; }

   /** Berechnet Vertex in Radiant.
       @return Vertex in Radiant. * /
   public static double vertex(double eclipticObliquity, double rightAscension, double latR) {
      double vertex = CalcUtil.Angle(-Math.sin(rightAscension+Math.PI)*Math.cos(eclipticObliquity)-
                              Math.sin(eclipticObliquity)/Math.tan(latR),Math.cos(rightAscension+Math.PI));
      /*
      x = Math.cos(rightAscension+Math.PI);
      y = -Math.sin(rightAscension+Math.PI)*Math.cos(eclipticObliquity)-Math.sin(eclipticObliquity)/Math.tan(latR);
      vt = Math.atan(x/y);
      if (vt < 0.0) vt += Math.PI;
      if (x < 0) vt += Math.PI;
      *
      // if (siderealOffset!=0.0) vertex = CalcUtil.Mod2PI(vertex+siderealOffset);
      return vertex;
   }

   /** East Punkt (was immer das ist) in Radiant. *
   public double getEastPoint() { return eastPoint; }

   /** Berechnet EastPoint in Radiant. *
   public double eastPoint() (double eclipticObliquity, double rightAscension) {
      double eastPoint = CalcUtil.Angle(-Math.sin(rightAscension)*Math.cos(eclipticObliquity),
                                 Math.cos(rightAscension));
      // if (siderealOffset!=0.0) eastPoint = CalcUtil.Mod2PI(eastPoint+siderealOffset);
      return eastPoint;
   } */

   /** Koordinaten der Hausgrenzen in Radiant.
       @return Koordinaten der Hausgrenzen in Radiant. */
   public final double[] getHousesR() { return housesR; }

   /** Namen der Hausgrenzen als Text.
       @return Namen der Hausgrenzen als Text. */
   public final String[] getHousesText() { return NAME_HOUSE; }

   /** Koordinate einer Hausgrenzen in Radiant.
       @param i Nummer der Grenze, 0 bis 11 f&uuml;r Haus Beginn des 1. bis 12. Hauses.
       @return Koordinate einer Hausgrenzen in Radiant. */
   public final double getHousesR(int i) { return housesR[i]; }

   /** Liefert Position eines Winkels im Haussystem aus Grad.
       @param d Position des K&ouml;rpers in Grad.
       @return Wert von 0 bis <var>n-1</var>, also in welchem der
               H&auml;user es steht. */
   public final int HouseFromD(double d) {
      return HouseFromR(CalcUtil.RFromD(d));
   }

   /** Liefert Position eines Winkels im Haussystem aus Radiant.
       @param r Position des K&ouml;rpers in Radiant.
       @return Wert von 0 bis <var>n-1</var>, also in welchem der
               H&auml;user es steht. */
   public final int HouseFromR(double r) {
      boolean limit = (housesR[NUMBER_HOUSE-1]>housesR[0]);
      if (limit) {
         if ((housesR[NUMBER_HOUSE-1] <= r)||(r < housesR[0])) return NUMBER_HOUSE-1;
      } else {
         if ((housesR[NUMBER_HOUSE-1] <= r)&&(r < housesR[0])) return NUMBER_HOUSE-1;
      }
      for (int i=0; i<NUMBER_HOUSE-1; i++) {
         limit = (housesR[i]>housesR[i+1]);
         if (limit) {
            if ( (housesR[i] <= r) || (r < housesR[i+1]) ) return i;
         } else {
            if ((housesR[i] <= r)&&(r < housesR[i+1])) return i;
         }
      }
      throw new RuntimeException("invalid angle in HouseFromR");
   }

   /** Setzt Werte f&uuml;r HouseSystem.
       @param L Latitude, d.h. geografische Breite der Position in Radiant.
       @param RA Erh&ouml;hung in Radiant.
       @param OB Schiefe der Ekliptik in Radiant.
       @param SO Sideral Offset f&uuml;r Sternenhoroskope.
   */
   public final void setHouses(double L, double RA, double OB, double SO) {
      this.latR = L;
      this.rightAscension = RA;
      this.eclipticObliquity = OB;
      this.siderealOffset = SO;
      calcBasicAngles();
      calcHouses();
   }

   /** Berechnet Aszendent und Winkel.
      The following three functions calculate the Midheaven, Ascendant, and
      East Point of the chart in question, based on time and location. The
      first two are also used in some of the house cusp calculation routines
      as a quick way to get the 10th and 1st cusps. (1250, [1], 49) */
   protected final void calcBasicAngles() {

      ascendant = CalcUtil.Angle(-Math.sin(rightAscension)*Math.cos(eclipticObliquity)-
                                 Math.tan(latR)*Math.sin(eclipticObliquity),Math.cos(rightAscension));
      if (siderealOffset!=0.0) ascendant = CalcUtil.Mod2PI(ascendant+siderealOffset);
      // der AC berechnet sich auch als CalcUtil.Sph2Ecl(RA,latR,-OB) +Pi/2

      midHeaven = CalcUtil.Angle(Math.cos(eclipticObliquity),Math.tan(rightAscension));
      if (siderealOffset!=0.0) midHeaven = CalcUtil.Mod2PI(midHeaven+siderealOffset);
      // der MC berechnet sich auch als Pol2Rec(1,RA) und x*COS(-OB), d.h. auf Ekliptik projezieren
/*
      vertex = CalcUtil.Angle(-Math.sin(rightAscension+Math.PI)*Math.cos(eclipticObliquity)-Math.sin(eclipticObliquity)/Math.tan(latR),Math.cos(rightAscension+Math.PI));
      // der VT berechnet sich auch als CalcUtil.Sph2Ecl(RA+PI,Pi/2-abs(latR),-OB) +Pi/2

      eastPoint = CalcUtil.Angle(-Math.sin(rightAscension)*Math.cos(eclipticObliquity),Math.cos(rightAscension));
      if (siderealOffset!=0.0) eastPoint = CalcUtil.Mod2PI(eastPoint+siderealOffset);
*/

/*
//   Original war: Flip the Ascendant if it falls in the wrong half of the zodiac.
     if (CalcUtil.MinDifference(midHeaven, ascendant) < 0.0)
        ascendant = CalcUtil.Mod2PI(ascendant + Math.PI);
*/
     // Aber bei meiner Implementierung springt der MC, also machen wir es für MC
     if (CalcUtil.MinDifference(midHeaven, ascendant) < 0.0)
        midHeaven = CalcUtil.Mod2PI(midHeaven + Math.PI);
   }

   /** Berechnet H&auml;user in Radiant. Diese Methode muss f&uuml;r andere Haussysteme
       &uuml;berschrieben werden.
       HouseNull: In "null" houses, the cusps are always fixed to start at their
       corresponding sign, i.e. the 1st house is always at 0 degrees Aries, etc.
   */
   protected void calcHouses() {
      if ((latR<range[0])||(latR>range[1])) return; // do nothing

      for (int i = 0; i < NUMBER_HOUSE; i++) housesR[i] = i*ANGLE_HOUSE_R;
   }

   /** Namen des Hausberechnungs Algorithmus. Diese Methode muss f&uuml;r andere Haussysteme
       &uuml;berschrieben werden. Namen aller Hausberechnungen: {"Placidus", "Koch", "Equal",
           "Campanus", "Meridian", "Regiomontanus", "Porphyry", "Morinus", "Topocentric",
           "Alcabitius", "EqualMidheaven", "PorphyryNeo", "Whole", "Vedic", "Null"};
       @return Name des Systems. */
   public String getHouseName() { return "Null"; }

   /** G&uuml;ltigkeit des Hausberechnungs Algorithmus.
       @return Radiant des Ranges, d.h. range(1) &lt; r &lt; range(1). */
   public double[] getValidityRange() {
      return range;
   }

// ---------------------------------------------------------------------
// ---------------- Methods for painting/writing -----------------------
// ---------------------------------------------------------------------

   /** Convert Haussystem to String. */
   public final String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("House \"");
      buf.append(getHouseName());
      buf.append("\" [");
         for (int i =0;i<NUMBER_HOUSE; i++) {
            buf.append(NAME_HOUSE[i]);
            buf.append(':');
            CalcUtil.HMStringFromR(buf,housesR[i]);
            buf.append("; ");
            if (i == 5) buf.append('\n');
         }
      buf.append(']');
      return buf.toString();
   }

}

