package at.kugel.zodiac.house;

import at.kugel.zodiac.util.CalcUtil;
/**
   Campanus Hausberechnung (1690, [1], 60).
   @see at.kugel.zodiac.house.HouseBasic
   @author Kugel, <i>Theossos Comp Group</i>
   @version 1.00 - 17062000 finished
                 - 22062000 values compared ok.
   @version 1.01 - 24062000 problems with latitude fixed.
   @version 1.10 - 02022002 ok for JDK 1.0 and 1.1; no changes
   @since JDK 1.0
*/
public final class HouseCampan extends HouseBasic {

   /** Validity range of the house system. */
   private final double[] subRange = {-CalcUtil.RFromD(90.0-rAxis),CalcUtil.PIH};

   /** Berechnet H&auml;user in Radiant. Verwendet nur Erh&ouml;hung.*/
   protected void calcHouses() {
     // if (test.D.bug) test.D.log("HouseCampan ("+this.getClass()+") - calcHouses called");
     if ((latR<subRange[0])||(latR>subRange[1])) return; // do nothing

     double KO, DN, X;
     final double Z = Math.cos(eclipticObliquity);
     final double Z2 = Math.sin(eclipticObliquity)*Math.tan(latR);
     final double Z3 = Math.cos(latR);

      for (int i = 0; i < NUMBER_HOUSE; i++) {
        // KO = RFromD(60.000001+30.0*(real)i);
        KO = CalcUtil.Mod2PI(ANGLE_HOUSE_R*i + CalcUtil.PIH + 1.7E-8);
        DN = Math.atan(Math.tan(KO)*Z3);
        if (DN < 0.0) DN += Math.PI;
        if (Math.sin(KO) < 0.0) DN += Math.PI;
        X = CalcUtil.Angle(Math.cos(rightAscension+DN)*Z-Math.sin(DN)*Z2,Math.sin(rightAscension+DN));
        if (siderealOffset!=0.0) housesR[i] = CalcUtil.Mod2PI(X+siderealOffset);
        else housesR[i] = X; // Angle returns right values
     }
   }

   /** Namen des Hausberechnungs Algorithmus.
       @return Name des Systems. */
   public String getHouseName() { return "Campanus"; }

   /** G&uuml;ltigkeit des Hausberechnungs Algorithmus.
       @return Radiant des Ranges, d.h. range(1) &lt; r &lt; range(1). */
   public double[] getValidityRange() {
      return subRange;
   }
}

