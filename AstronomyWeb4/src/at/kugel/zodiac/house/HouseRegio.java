package at.kugel.zodiac.house;

import at.kugel.zodiac.util.CalcUtil;
/**
   Regiomontanus Hausberechnung (1470, [1], 57).
   @see at.kugel.zodiac.house.HouseBasic
   @author Kugel, <i>Theossos Comp Group</i>
   @version 1.00 - 17062000 finished
                 - 22062000 values compared ok.
   @version 1.01 - 24062000 problems with latitude fixed.
   @version 1.10 - 02022002 ok for JDK 1.0 and 1.1; no changes
   @since JDK 1.0
*/
public final class HouseRegio extends HouseBasic {

   /** Validity range of the house system. */
   private final  double[] subRange = {-CalcUtil.RFromD(90.0-rAxis),CalcUtil.PIH};

   /** Berechnet H&auml;user in Radiant. Verwendet nur Erh&ouml;hung. */
   protected void calcHouses() {
      // if (test.D.bug) test.D.log("HouseRegio ("+this.getClass()+") - calcHouses called");
      if ((latR<subRange[0])||(latR>subRange[1])) return; // do nothing

      double D, X;
      final double Z = Math.cos(eclipticObliquity);
      final double Z2 = Math.tan(latR)*Math.sin(eclipticObliquity);
      for (int i = 0; i < NUMBER_HOUSE; i++) {
        D = ANGLE_HOUSE_R*i+CalcUtil.PIH;
        X = CalcUtil.Angle(Math.cos(rightAscension+D)*Z-Math.sin(D)*Z2,Math.sin(rightAscension+D));
        if (siderealOffset!=0.0) housesR[i] = CalcUtil.Mod2PI(X+siderealOffset);
        else housesR[i] = X; // Angle returns right values
     }
   }

   /** Namen des Hausberechnungs Algorithmus.
       @return Name des Systems. */
   public String getHouseName() { return "Regiomontanus"; }

   /** G&uuml;ltigkeit des Hausberechnungs Algorithmus.
       @return Radiant des Ranges, d.h. range(1) &lt; r &lt; range(1). */
   public double[] getValidityRange() {
      return subRange;
   }
}

