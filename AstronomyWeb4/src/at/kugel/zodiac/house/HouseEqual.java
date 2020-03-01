package at.kugel.zodiac.house;

import at.kugel.zodiac.util.CalcUtil;
/**
   Equidistante Hausberechnung (1535, [1], 58). Die H&auml;user starten im
   Aszendenten und sind alle gleich gross.
   @see at.kugel.zodiac.house.HouseBasic
   @author Kugel, <i>Theossos Comp Group</i>
   @version 1.00 - 01032000 finished,
                 - 22062000 values compared ok.
   @version 1.01 - 24062000 no problems with latitude.
   @version 1.10 - 02022002 ok for JDK 1.0 and 1.1; no changes
   @since JDK 1.0
*/
public final class HouseEqual extends HouseBasic {

   /** Berechnet H&auml;user in Radiant. Verwendet <code>ascendant</code>. */
   protected void calcHouses() {
      // if (test.D.bug) test.D.log("HouseEqual ("+this.getClass()+") - calcHouses called");
      if ((latR<range[0])||(latR>range[1])) return; // do nothing

      for (int i = 0; i<NUMBER_HOUSE; i++)
        housesR[i] = CalcUtil.Mod2PI(ascendant + ANGLE_HOUSE_R*i + siderealOffset);
   }

   /** Namen des Hausberechnungs Algorithmus.
       @return Name des Systems. */
   public String getHouseName() { return "Equal"; }

   /** G&uuml;ltigkeit des Hausberechnungs Algorithmus.
       @return Radiant des Ranges, d.h. range(1) &lt; r &lt; range(1). */
   public double[] getValidityRange() {
      return range;
   }
}

