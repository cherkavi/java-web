package at.kugel.zodiac.house;

import at.kugel.zodiac.util.CalcUtil;
/**
   Koch Hausberechnung (1580, [1], 58).
   @see at.kugel.zodiac.house.HouseBasic
   @author Kugel, <i>Theossos Comp Group</i>
   @version 0.99 - 17062000 finished, leider der Fehler, dass 11. und 12.
                   Haus falsch sind.
   @version 1.00 - 22062000 fixed, values compared ok.
   @version 1.01 - 24062000 problems with latitude fixed.
   @version 1.10 - 02022002 ok for JDK 1.0 and 1.1; no changes
   @since JDK 1.0
*/
public final class HouseKoch extends HouseBasic {

   /** Validity range of the house system. */
   private final double[] subRange = {-CalcUtil.RFromD(90.0-rAxis),CalcUtil.RFromD(90.0-rAxis)};

   /** Berechnet H&auml;user in Radiant. Verwendet nur Erh&ouml;hung.*/
   protected void calcHouses() {
      // if (test.D.bug) test.D.log("HouseKoch ("+this.getClass()+") - calcHouses called");
      if ((latR<subRange[0])||(latR>subRange[1])) return; // do nothing

      double A2, A3, KN, D, X;
      final double A1 = Math.asin(Math.sin(rightAscension)*Math.tan(latR)*Math.tan(eclipticObliquity));
      final double Z = Math.cos(eclipticObliquity);
      final double Z2 = Math.sin(eclipticObliquity)*Math.tan(latR);
      for (int i = 0; i < NUMBER_HOUSE; i++) {
        D = CalcUtil.Mod2PI(ANGLE_HOUSE_R*i + CalcUtil.PIH);
        if (D >= Math.PI) {
          KN = -1.0;
          A2 = D/CalcUtil.PIH-3;
        } else {
          KN = 1.0;
          A2 = D/CalcUtil.PIH-1;
        }
        A3 = CalcUtil.Mod2PI(rightAscension+D+A2*A1);
        X = CalcUtil.Angle(Math.cos(A3)*Z-KN*Z2, Math.sin(A3));
        if (siderealOffset!=0.0) housesR[i] = CalcUtil.Mod2PI(X+siderealOffset);
        else housesR[i] = X; // Angle returns right values
     }
   }

   /** Namen des Hausberechnungs Algorithmus.
       @return Name des Systems. */
   public String getHouseName() { return "Koch"; }

   /** G&uuml;ltigkeit des Hausberechnungs Algorithmus.
       @return Radiant des Ranges, d.h. range(1) &lt; r &lt; range(1). */
   public double[] getValidityRange() {
      return subRange;
   }

   /* DEBUG
   /** Main for testing house calculation routines. *
   public static void main(String[] args) {
      double rightAscension=3.0669907891147563;
      double latR=0.6987128954386459;
      double eclipticObliquity=0.40922539378432593;
      HouseKoch usedHouse = new HouseKoch();
      usedHouse.setHouses(latR,rightAscension,eclipticObliquity,0.0);
      // System.out.println(a.toString());

      // notwendiges initialisieren von globalen Daten START
      StringBuffer htText = new StringBuffer();
      String leerZeile = "                                        ";
      int horoscopMode = 3;
      String[] names2;
      double[] values2;
      at.kugel.zodiac.util.HMS hms= new at.kugel.zodiac.util.HMS();
      // notwendiges initialisieren von globalen Daten ENDE

      // write houses
      if (usedHouse != null) {
        htText.append(usedHouse.getHouseName());
        htText.append('\n');
        names2 = usedHouse.getHousesText();
        values2 = usedHouse.getHousesR();
        for (int i=0;i<values2.length;i++) {
           // Space required: 11+12+1+9+1+13 = 43, place 5 gaps
           htText.append(' '); // Space possible
           htText.append(names2[i]);
           htText.append(leerZeile.substring(names2[i].length(),11));
           hms = CalcUtil.HMSFromR(hms,values2[i]);
           htText.append(" ("); // Space possible
           if (hms.h<10) htText.append("00");
           else if (hms.h<100) htText.append('0');
           htText.append(hms.h);
           htText.append('°');
           if (hms.m<10) htText.append('0');
           htText.append(hms.m);
           htText.append('\'');
           if (hms.s<10) htText.append('0');
           htText.append(hms.s);
           htText.append("\") "); // Space possible
           hms.z = (hms.h / CalcUtil.SignAngleD) % Constants.NUMBER_SIGN[horoscopMode];
           hms.h = (hms.h % CalcUtil.SignAngleD) * Constants.ANGLE_SIGN_D[horoscopMode] / CalcUtil.SignAngleD;
           if (hms.h<100) htText.append(' ');
           if (hms.h<10) htText.append('0');
           htText.append(hms.h);
           htText.append('°');
           if (hms.m<10) htText.append('0');
           htText.append(hms.m);
           htText.append('\'');
           if (hms.s<10) htText.append('0');
           htText.append(hms.s);
           htText.append("\"  "); // Space possible
           htText.append(hms.z);
           // Space possible
           htText.append('\n');
        }
      } // houses
      System.out.println(htText.toString());
   }
   */
}

