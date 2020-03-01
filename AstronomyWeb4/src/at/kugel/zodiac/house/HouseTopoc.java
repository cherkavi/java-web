package at.kugel.zodiac.house;

import at.kugel.zodiac.util.CalcUtil;
/**
   Topocentric Hausberechnung (1625, [1], 59).
   @see at.kugel.zodiac.house.HouseBasic
   @author Kugel, <i>Theossos Comp Group</i>
   @version 1.00 - 17062000 finished
                 - 22062000 values compared ok.
   @version 1.01 - 24062000 problems with latitude fixed.
   @version 1.10 - 02022002 ok for JDK 1.0 and 1.1; no changes
   @since JDK 1.0
*/
public final class HouseTopoc extends HouseBasic {

   /** Validity range of the house system. */
   private final double[] subRange = {-CalcUtil.RFromD(90.0-rAxis),CalcUtil.PIH};

   /** Interne Topocentric Routine aus Astrolog, bzw. (1670, [1], 59).
       @param deg Basiswinkel in Grad, wird zu RA addiert. */
   private double CuspTopocentric(double deg, double AA) {
      final double OA = CalcUtil.Mod2PI(rightAscension+CalcUtil.RFromD(deg));
      final double X = Math.atan(Math.tan(AA)/Math.cos(OA));
      double LO = Math.atan(Math.cos(X)*Math.tan(OA)/Math.cos(X+eclipticObliquity));
      if (LO < 0.0) LO += Math.PI;
      if (Math.sin(OA) < 0.0) LO += Math.PI;
      return LO;
   }

   /** Berechnet H&auml;user in Radiant. Verwendet nur <code>midHeaven</code>. */
   protected void calcHouses() {
     // if (test.D.bug) test.D.log("HouseTopoc ("+this.getClass()+") - calcHouses called");
     if ((latR<subRange[0])||(latR>subRange[1])) return; // do nothing

     double TL = Math.tan(latR);
     double P1 = Math.atan(TL/3.0);
     double P2 = Math.atan(TL/1.5);
     housesR[0] = CuspTopocentric(90.0,latR);
     housesR[1] = CuspTopocentric(120.0,P2);
     housesR[2] = CuspTopocentric(150.0,P1);
     housesR[3] = midHeaven+Math.PI-siderealOffset;
     housesR[4] = CuspTopocentric(30.0,P1) + Math.PI;
     housesR[5] = CuspTopocentric(60.0,P2) + Math.PI;
     for (int i = 0; i < 6; i++) {
       housesR[i] = CalcUtil.Mod2PI(housesR[i]+siderealOffset);
       housesR[i+6] = CalcUtil.Mod2PI(housesR[i]+Math.PI);
     }
   }

   /** Namen des Hausberechnungs Algorithmus.
       @return Name des Systems. */
   public String getHouseName() { return "Topocentric"; }

   /** G&uuml;ltigkeit des Hausberechnungs Algorithmus.
       @return Radiant des Ranges, d.h. range(1) &lt; r &lt; range(1). */
   public double[] getValidityRange() {
      return subRange;
   }
}

