package at.kugel.zodiac.house;

import at.kugel.zodiac.util.CalcUtil;
/**
   Porphyry Hausberechnung (1500, [1], 58).
   @see at.kugel.zodiac.house.HouseBasic
   @author Kugel, <i>Theossos Comp Group</i>
   @version 1.00 - 17062000 finished
                 - 22062000 values compared ok.
   @version 1.01 - 24062000 problems with latitude fixed.
   @version 1.10 - 02022002 ok for JDK 1.0 and 1.1; no changes
   @since JDK 1.0
*/
public final class HousePorphyry extends HouseBasic {

   /** Validity range of the house system. */
   private final double[] subRange = {-CalcUtil.RFromD(90.0-rAxis),CalcUtil.PIH};

   /** Berechnet H&auml;user in Radiant. Verwendet <code>ascendant</code> und
       <code>midHeaven</code>.*/
   protected void calcHouses() {
      // if (test.D.bug) test.D.log("HousePorphyry ("+this.getClass()+") - calcHouses called");
      if ((latR<subRange[0])||(latR>subRange[1])) return; // do nothing

      housesR[0]=CalcUtil.Mod2PI(ascendant+siderealOffset);

      double Y = CalcUtil.Mod2PI(Math.PI+midHeaven-ascendant)/3.0;
      housesR[1] = CalcUtil.Mod2PI(ascendant+Y+siderealOffset);
      housesR[2] = CalcUtil.Mod2PI(ascendant+2*Y+siderealOffset);
      housesR[3] = CalcUtil.Mod2PI(ascendant+3*Y+siderealOffset);

      Y = CalcUtil.Mod2PI(ascendant-midHeaven)/3.0;
      housesR[4] = CalcUtil.Mod2PI(Math.PI+midHeaven+Y+siderealOffset);
      housesR[5] = CalcUtil.Mod2PI(Math.PI+midHeaven+2*Y+siderealOffset);

      for (int i = 0; i < 6; i++)
         housesR[i+6] = CalcUtil.Mod2PI(housesR[i]+Math.PI);
   }

   /** Namen des Hausberechnungs Algorithmus.
       @return Name des Systems. */
   public String getHouseName() { return "Porphyry"; }

   /** G&uuml;ltigkeit des Hausberechnungs Algorithmus.
       @return Radiant des Ranges, d.h. range(1) &lt; r &lt; range(1). */
   public double[] getValidityRange() {
      return subRange;
   }
}

