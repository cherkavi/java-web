package at.kugel.zodiac.house;

import at.kugel.zodiac.util.CalcUtil;
/**
   Whole Hausberechnung: The "Whole" house system is like the Equal system
   with 30 degree houses, where the 1st house starts at zero degrees of the
   sign of the Ascendant.
   @see at.kugel.zodiac.house.HouseBasic
   @author Kugel, <i>Theossos Comp Group</i>
   @version 1.00 - 17062000 finished.
   @version 1.01 - 24062000 no problems with latitude.
   @version 1.10 - 02022002 ok for JDK 1.0 and 1.1; no changes
   @since JDK 1.0
*/
public final class HouseWhole extends HouseBasic {

   /** Berechnet H&auml;user in Radiant. Verwendet <code>ascendant</code>. */
   protected void calcHouses() {
     // if (test.D.bug) test.D.log("HouseWhole ("+this.getClass()+") - calcHouses called");
     if ((latR<range[0])||(latR>range[1])) return; // do nothing

     // Hack: horoscopemode = 3 hardcoded!
     int sign = CalcUtil.ZFromR(ascendant,3)-1;

     for (int i = 0; i < NUMBER_HOUSE; i++)
       housesR[i] = CalcUtil.Mod2PI( (sign+i)*ANGLE_HOUSE_R + siderealOffset);
   }

   /** Namen des Hausberechnungs Algorithmus.
       @return Name des Systems. */
   public String getHouseName() { return "Whole"; }

   /** G&uuml;ltigkeit des Hausberechnungs Algorithmus.
       @return Radiant des Ranges, d.h. range(1) &lt; r &lt; range(1). */
   public double[] getValidityRange() {
      return range;
   }
}

