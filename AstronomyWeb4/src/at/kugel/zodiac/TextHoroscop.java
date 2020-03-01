package at.kugel.zodiac;
/*
AstroLib - astro charting library - Copyright (C) 2004 - Peter Kofler

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
*/
import at.kugel.zodiac.house.HouseInt;
import at.kugel.zodiac.planet.PlanetInt;
import at.kugel.zodiac.util.CalcUtil;
import at.kugel.zodiac.util.HMS;
/**
   Basic zodiac class for calculating. This is a stripped version of the Zodiac
   watch's <code>Horoscop</code>. It does no drawing, just calculation and possibly
   dumps to <code>System.out</code>
   @author Kugel, <i>Theossos Comp Group</i>
   @version 1.13 - fixed bug in timezone usage (thanks to Rastislav Szabo)
*/
public final class TextHoroscop extends Object {

   /** Own copy from <code>Parameters</code>, number Julian days, <code>jD</code>. */
   private int julianDay;
   /** Own copy from <code>Parameters</code>, Julian centuries. */
   private double julianCenturies;
   /** Own copy from <code>Parameters</code>, GMT time, <code>F</code>. */
   private double worldTime;
   /** Own copy from <code>Parameters</code>, Longitude, -east to +west in radiant. */
   private double lonR;
   /** Own copy from <code>Parameters</code>, Latitude, -south to +north in radiant. */
   private double latR;
   /** Rechte Erh&ouml;hung at worldTime. In Radiant. (Right ascension at worldTime.) <code>real RA</code>. */
   private double rightAscension;
   /** Schiefe der Ekliptik in Radiant. (Obliquity of ecliptic.)  <code>real OB</code>. */
   private double eclipticObliquity;
   /** ? (Sidereal offset) in Radiant, verschiebt Location. <code>real rSid</code>. */
   private double siderealOffset;

   /** Used house system calculator for this Horoscope. */
   private HouseInt usedHouse;
   /** Used planet position calculator for this Horoscope. */
   private PlanetInt usedPlanet;

   // ---------------------------------------------------------------------
   // -------------------- Methods ----------------------------------------
   // ---------------------------------------------------------------------

   /** Constructor. */
   public TextHoroscop() {
      // do nothing
   }

   /** Getter.
       @return Erh&ouml;hung. */
   public double getRrightAscension() {
      return rightAscension;
   }
   /** Getter.
       @return lokale Sternzeit. */
   public HMS getLocalSideralTime() {
      return CalcUtil.HMSFromD(new HMS(), CalcUtil.DFromR(rightAscension) / 15.0);
   }
   /** Getter.
       @return Schiefe (Obliquity). */
   public double getEclipticObliquity() {
      return eclipticObliquity;
   }
   /** Getter.
       @return Sidereal Offset. */
   public double getSiderealOffset() {
      return siderealOffset;
   }
   /** Getter.
       @return Haus System. */
   public HouseInt getHouse() {
      return usedHouse;
   }
   /** Getter.
       @return Planeten System. */
   public PlanetInt getPlanet() {
      return usedPlanet;
   }

   /** Setter.
       @param house das Haussystem. */
   public synchronized void setHouse(HouseInt house) {
      usedHouse = house;
   }
   /** Setzt ein Planetensystem.
       @param planet das Planetensystem */
   public synchronized void setPlanet(PlanetInt planet) {
      usedPlanet = planet;
   }

   /** Set time data for horoscop.
       @param jD Anzahl Julianische Tage, berechnet aus TTMMJJJJ.
       @param F GMT Zeit in Stunden. Minuten und Sekunden.
   */
   public void setTime(int jD, double F) {
      this.julianDay = jD;
      this.worldTime = F;
   }

   /** Set time data for horoscop.
      @param day Tag f&uuml;r neues Horoskop.
      @param month Monat f&uuml;r neues Horoskop.
      @param year Jahr f&uuml;r neues Horoskop.
      @param F GMT Zeit in Stunden.
   */
   public void setTime(int day, int month, int year, double F) {
      setTime(CalcUtil.julianDay(day, month, year), F);
   }

   /** Set time data for horoscop.
      @param day Tag f&uuml;r neues Horoskop.
      @param month Monat f&uuml;r neues Horoskop.
      @param year Jahr f&uuml;r neues Horoskop.
      @param h Stunde Ortszeit f&uuml;r neues Horoskop.
      @param m Minute Ortszeit f&uuml;r neues Horoskop.
      @param s Sekunde Ortszeit f&uuml;r neues Horoskop.
      @param timeZone Zeitzone in Stunden. Positiv wenn &ouml;stlich,
              negativ wenn westlich. (ZZ, ciCore.zon)
   */
   public void setTime(int day, int month, int year, int h, int m, int s, double timeZone) {
      setTime(day, month, year, CalcUtil.DFromHMS(h, m, s) - timeZone); // - Dst
   }

   /** Setzt location data for horoscope.
       @param lon Longitude, d.h. geografische L&auml;nge der Position in Radiant.
       @param lat Latitude, d.h. geografische Breite der Position in Radiant.
   */
   public void setLocation(double lon, double lat) {
      this.lonR = lon;
      this.latR = lat;
   }

   /**
      Set location data.
      @param lon Longitude in degree.
      @param lat Latitude in degree.
   */
   public void setLocationDegree(double lon, double lat) {
      setLocation(CalcUtil.RFromD(lon), CalcUtil.RFromD(lat));
   }

   /**
      Set location data.
      @param lond Longitude in degree.
      @param lonm Longitude in minutes.
      @param lons Longitude in seconds.
      @param latd Latitude in degree.
      @param latm Latitude in minutes.
      @param lats Latitude in seconds.
   */
   public void setLocationDegree(int lond, int lonm, int lons, int latd, int latm, int lats) {
      setLocation(CalcUtil.RFromHMS(lond, lonm, lons), CalcUtil.RFromHMS(latd, latm, lats));
   }

   /**
      Setzt Daten eines neuen Horoskops. Parameterliste mit Zeit und Ortsangabe zusammengefasst.
      @param day Tag f&uuml;r neues Horoskop.
      @param month Monat f&uuml;r neues Horoskop.
      @param year Jahr f&uuml;r neues Horoskop.
      @param F GMT Zeit in Stunden.
      @param lon Longitude in degree.
      @param lat Latitude in degree.
   */
   public void setUserData(int day, int month, int year, double F, double lon, double lat) {
      setTime(day, month, year, F);
      setLocationDegree(lon, lat);
   }

   /** Berechnet Basiswerte und setzt diese in H&auml;ser und Planeten ein. */
   public void calcValues() {
      double julianTime = julianDay - 0.5 + worldTime / 24.0;

      /* Berechnet die Anzahl Julianischer Jahrhunderte seit 0.1.1900 aus dem angegebenen
         Julianischen Tag seit der angegebenen Uhrzeit in Stunden. Notwendig für mehrere
         Formeln, die ab 0.1.1900 rechnen. (Century Increment T, [2], 41).
         @param worldTime (F) Zeit in Stunden, alle Zeitverschiebungen einberechnet, d.h. in GMT.
         @return Anzahl der Julianischen Jahrhunderte seit 0.1.1900 F Uhr. */
      //    public static double julianCenturies(int julianDay, double worldTime) {
      julianCenturies = (julianTime - 2415020) / 36525.0;
      // -0.5. damit 0.1.1900 Mitternacht habe
      // rechnet mit Julianischen Jahren. Sternenjahr wäre 366.2422 Sternentage.

      /* Berechnet Erh&ouml;hung aus Juliantime, worldTime, Longitude.
         Das ist die Sternenzeit in 360 Grad Notation, aber nicht klar, was das
         ist. Hat mit Sternenzeit nicht umbedingt was zu tun, soll aber Local Sidereal
         Time sein. (Right Ascension of the Mideaven RA, [2], 45)
         @param T julianCenturies seit 0.1.1900. 0:00.
         @param F (worldTime) GMT Zeit in Stunden.
         @param lon Longitude in Grad.
         @return Sternenzeit in Radiant. */
      //    public static double rightAscension(double julianCenturies, double worldTime, double lon) {
      rightAscension =
         CalcUtil.RFromD(
            CalcUtil.Mod360(
               (6.6460656 + (2400.0513 + 2.58E-5 * julianCenturies) * julianCenturies + worldTime) * 15.0
                  - CalcUtil.DFromR(lonR)));

      /* Berechnet die Schiefe der Ekliptik. Diese ist ca. 23 Grad 26' = 23.43333 Grad
         ([1], 63). (Obliquity of the Ecliptic for Date OB, [2], 41)
         @param T julianCenturies seit 0.1.1900. 0:00, für den wohl der Wert 23.452294
                  berechnet ist.
         @return Die Schiefe der Ekliptik in Radiant. */
      //    public static double eclipticObliquity(double julianCenturies) {
      // Compute angle that the ecliptic is inclined to the Celestial Equator
      eclipticObliquity = CalcUtil.RFromD(23.452294 - 0.0130125 * julianCenturies);

      /* Berechnet Offset für Sternen Horoskop (Sidereal Offset SD, [1], 42) */
      //    public static double siderealOffset(double T) {
      /*
            if (Parameters.calculateUseSidereal) {
               // bool fSidereal - Compute a sidereal instead of the normal tropical chart.
               double Ln = CalcUtil.Mod360((933060+(-6962911+7.5*julianCenturies)*julianCenturies)/3600.0); // Mean lunar node
               double Off = (259205536.0*julianCenturies+2013816.0)/3600.0; // Mean Sun
               Off = 17.23*Math.sin(CalcUtil.RFromD(Ln))+1.27*Math.sin(CalcUtil.RFromD(Off))-
                     (5025.64+1.11*julianCenturies)*julianCenturies;
               siderealOffset = CalcUtil.RFromD((Off-84038.27)/3600.0); // + us.rZodiacOffset;
               // real rZodiacOffset - Position shifting value passed to -s switch.
               // if (test.D.bug) test.D.log("siderealOffset="+CalcUtil.DFromR(siderealOffset)+"°("+siderealOffset+")");
            } else */
      siderealOffset = 0.0;

      if (usedHouse != null) {
         usedHouse.setHouses(latR, rightAscension, eclipticObliquity, siderealOffset);
      }
      if (usedPlanet != null) {
         usedPlanet.setPlanets(julianCenturies, siderealOffset);
      }
   }

   // ---------------------------------------------------------------------
   // ---------------- Methods for painting/writing -----------------------
   // ---------------------------------------------------------------------

   /** Convert whole horocsop to String for debug purpose. */
   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.setLength(0);
      buf.append("Horoscop [JD: ");
      buf.append(julianDay);
      buf.append("; F: ");
      buf.append(worldTime);
      buf.append("; SZ: ");
      buf.append(getLocalSideralTime().toString());
      buf.append(";\n");
      if (usedHouse != null) {
         buf.append(usedHouse.toString());
         buf.append(";\n");
      }
      if (usedPlanet != null) {
         buf.append(usedPlanet.toString());
      }
      buf.append(";]");
      return buf.toString();
   }

   /** Convert whole horocsop to String for debug purpose. */
   public void toConsole() {
      System.out.println("Horoscop [JD: ");
      System.out.println(julianDay);
      System.out.println("; F: ");
      System.out.println(worldTime);
      System.out.println("; SZ: ");
      System.out.println(getLocalSideralTime().toString());
      System.out.println(";\n");
      if (usedHouse != null) {
         System.out.println(usedHouse.toString());
         System.out.println(";\n");
      }
      if (usedPlanet != null) {
         System.out.println(usedPlanet.toString());
      }
      System.out.println(";]");
   }

}
