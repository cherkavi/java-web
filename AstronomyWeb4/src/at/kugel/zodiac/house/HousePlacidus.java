package at.kugel.zodiac.house;

import at.kugel.zodiac.util.CalcUtil;
/**
   Placidus (klassische) Hausberechnung (1735, [1], 60).
   @see at.kugel.zodiac.house.HouseBasic
   @author Kugel, <i>Theossos Comp Group</i>
   @version 1.00 - 17062000 finished
                 - 22062000 values compared ok.
   @version 1.01 - 24062000 problems with latitude fixed.
   @version 1.10 - 02022002 ok for JDK 1.0 and 1.1; no changes
   @since JDK 1.0
*/
public final class HousePlacidus extends HouseBasic {

   /** Validity range of the house system. */
   private final double[] subRange = {-CalcUtil.RFromD(90.0-rAxis),CalcUtil.RFromD(90.0-rAxis)};

   /** Interne Placidus Routine aus Astrolog, bzw. (1770, [1], 60).
       @param deg Basiswinkel in Degree.
       @param FF
       @param fNeg */
   private double CuspPlacidus(double deg, double FF, boolean fNeg) {
     double R1, XS, X;

     R1 = CalcUtil.Mod2PI(rightAscension+CalcUtil.RFromD(deg)); // CalcUtil.RFromD(deg);
     if (fNeg) X = 1.0;
     else X = -1;
     // Looping 10 times is arbitrary, but it's what other programs do.
     for (int i = 1; i <= 10; i++) {
       // This formula works except at 0 latitude (AA == 0.0).
       XS = X*Math.sin(R1)*Math.tan(eclipticObliquity)*Math.tan( (latR == 0.0) ? 0.000001 : latR);
       XS = Math.acos(XS);
       if (XS < 0.0) XS += Math.PI;
       if (fNeg) R1 = rightAscension + Math.PI - XS/FF;
       else R1 = rightAscension + XS/FF;
     }
     // return CalcUtil.Angle(Math.cos(eclipticObliquity),Math.tan(R1));
     // could be done by angle()
     double LO = Math.atan(Math.tan(R1)/Math.cos(eclipticObliquity));
     if (LO < 0.0) LO += Math.PI;
     if (Math.sin(R1) < 0.0) LO += Math.PI;
     return LO;
   }

   /** Berechnet H&auml;user in Radiant. Verwendet <code>ascendant</code> und
       <code>midHeaven</code>.*/
   protected void calcHouses() {
     // if (test.D.bug) test.D.log("HousePlacidus ("+this.getClass()+") - calcHouses called");
     if ((latR<subRange[0])||(latR>subRange[1])) return; // do nothing
     /*
     if ( Math.abs(latR) > CalcUtil.RFromD(90.0-rAxis) ) {
         // "The system of houses is not defined at extreme latitudes."
         latR = CalcUtil.RSgn(latR)*CalcUtil.RFromD(90.0-rAxis);
     }
     */

     housesR[0] = ascendant-siderealOffset;
     housesR[1] = CuspPlacidus(120.0, 1.5, true);
     housesR[2] = CuspPlacidus(150.0, 3.0, true);
     housesR[3] = midHeaven+Math.PI-siderealOffset;
     housesR[4] = CuspPlacidus(30.0, 3.0, false) + Math.PI;
     housesR[5] = CuspPlacidus(60.0, 1.5, false) + Math.PI;
     for (int i = 0; i < 6; i++) {
       housesR[i] = CalcUtil.Mod2PI(housesR[i]+siderealOffset);
       housesR[i+6] = CalcUtil.Mod2PI(housesR[i]+Math.PI);
     }
   }

   /** Namen des Hausberechnungs Algorithmus.
       @return Name des Systems. */
   public String getHouseName() { return "Placidus"; }

   /** G&uuml;ltigkeit des Hausberechnungs Algorithmus.
       @return Radiant des Ranges, d.h. range(1) &lt; r &lt; range(1). */
   public double[] getValidityRange() {
      return subRange;
   }
}

