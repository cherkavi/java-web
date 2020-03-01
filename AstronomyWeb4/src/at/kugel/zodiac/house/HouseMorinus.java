package at.kugel.zodiac.house;

import at.kugel.zodiac.util.CalcUtil;
/**
   Morinus Hausberechnung (1550, [1], 58).
   @see at.kugel.zodiac.house.HouseBasic
   @author Kugel, <i>Theossos Comp Group</i>
   @version 1.00 - 17062000 finished
                 - 22062000 values compared ok.
   @version 1.01 - 24062000 no problems with latitude.
   @version 1.10 - 02022002 ok for JDK 1.0 and 1.1; no changes
   @since JDK 1.0
*/
public final class HouseMorinus extends HouseBasic {

   /** Berechnet H&auml;user in Radiant. Verwendet nur Erh&ouml;hung, */
   protected void calcHouses() {
      // if (test.D.bug) test.D.log("HouseMorinus ("+this.getClass()+") - calcHouses called");
      if ((latR<range[0])||(latR>range[1])) return; // do nothing

      double D, X;
      final double Z = Math.cos(eclipticObliquity);
      for (int i = 0; i < NUMBER_HOUSE; i++) {
        D = ANGLE_HOUSE_R*i + CalcUtil.PIH;
        X = CalcUtil.Angle(Math.cos(rightAscension+D),Math.sin(rightAscension+D)*Z);
        if (siderealOffset!=0.0) housesR[i] = CalcUtil.Mod2PI(X+siderealOffset);
        else housesR[i] = X; // Angle returns right values
     }
   }

   /** Namen des Hausberechnungs Algorithmus.
       @return Name des Systems. */
   public String getHouseName() { return "Morinus"; }

   /** G&uuml;ltigkeit des Hausberechnungs Algorithmus.
       @return Radiant des Ranges, d.h. range(1) &lt; r &lt; range(1). */
   public double[] getValidityRange() {
      return range;
   }
}

