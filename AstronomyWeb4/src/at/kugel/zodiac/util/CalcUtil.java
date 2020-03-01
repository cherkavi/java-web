package at.kugel.zodiac.util;

import at.kugel.zodiac.Constants;
/**
   Sammlung von statischen Hilfsroutinen zur Berechnung. Das sind
   mathematische Konstanten, Umrechenroutinen von Radiant und Grad,
   Grad dezimal und HMS, Modulofunktionen, etc.
   @see at.kugel.zodiac.util.HMS
   @see at.kugel.zodiac.util.XY
   @see at.kugel.zodiac.Constants
   @author Kugel, <i>Theossos Comp Group</i>
   @version 1.00 - 01032000 finished.
   @version 1.10 - 02022002 ok for JDK 1.0 and 1.1; removed unused code.
   @version 1.12 - removed warnings and unused code in AstroLib
   @since JDK 1.0
*/
public final class CalcUtil {

// ---------------------------------------------------------------------
// -------------------- mathematische Konstanten -----------------------
// ---------------------------------------------------------------------

   /** Hilfswert 180/Pi. */
   private static final double PI180 = 180.0/Math.PI;
   /** Hilfswert 2*Pi. */
   public static final double PI2 = 2.0*Math.PI;
   /** Hilfswert Pi/2. */
   public static final double PIH = Math.PI/2.0;

   /** Angles of one basic sign in degree. */
   public static final int SignAngleD = 360/Constants.NUMBER_MAX;
   /** Angles of one basic sign in radiant. */
   static final double SignAngleR = PI2/Constants.NUMBER_MAX;

// ---------------------------------------------------------------------
// -------------------- Winkelkonversion Rad-Deg -----------------------
// ---------------------------------------------------------------------

   /** Liefert Grad (Degree) aus Radiant.
       @param r Radiant.
       @return Grad (Degree) aus Radiant. */
   public static final double DFromR(double r) { return r*PI180; }

   /** Liefert Radiant aus Grad (Degree).
       @param d Grad.
       @return Radiant aus Grad (Degree). */
   public static final double RFromD(double d) { return d/PI180; }

   /** Liefert Grad aus Grad/Stunden, Minuten und Sekunden. */
   public static final double DFromHMS(int h, int m, int s) {
      return (s/60.0 + m)/60.0 + h;
   }

   /** Liefert Radiant aus Grad/Stunden, Minuten und Sekunden. */
   public static final double RFromHMS(int h, int m, int s) {
      // return RFromD(DFromHMS(h,m,s));
      return ((s / 60.0 + m) / 60.0 + h) / PI180;
   }

   /** Liefert Grad/Stunden, Minuten und Sekunden aus Grad. ([1], 45)
       @param hms das HMS, das benutzt wird, oder <code>new HMS()</code>.
       @param d dezimaler Gradwert.
       @return Grad/Stunden, Minuten und Sekunden aus Grad. */
   public static final HMS HMSFromD(HMS hms, double d) {
/*
      hms.h = (int)Math.floor(d); // Y
      hms.m = (int)Math.floor( (d-hms.h)*60 ); // Z
      hms.s = (int)Math.round( ((d-hms.h)*60-hms.m)*60 );
      if (hms.s==60) { // Sonderfall Rundungsfehler
         hms.s = 0;
         hms.m++;
         if (hms.m==60) {
           hms.m = 0;
           hms.h++;
         }
      }
*/
      hms.h = (int)Math.floor(d); // Y
      final double t = (d-hms.h)*60;
      hms.m = (int)Math.floor(t); // Z
      hms.s = (int)Math.floor((t-hms.m)*60);
      return hms;
   }

   /* DEBUG/unused
   /** Liefert Grad/Stunden, Zeichen, Minuten und Sekunden in Abh&auml;nggigkeit vom
       Modus aus Grad. ([1], 44) Alle Werte m&uuml;ssen positiv sein. Zeichen ist 0 bis
       <var>n-1</var>.
       @param into das HMS, das benutzt wird, oder <code>new HMS()</code>.
       @param d dezimaler Gradwert.
       @param hososcopMode Index in die Arrays von Anzahl Zeichen. *
   public static final HMS HZMSFromD(HMS hms, double d, int horoscopMode) {
      hms = HMSFromD(hms,d);
      hms.z = (hms.h / SignAngleD) % Constants.NUMBER_SIGN[horoscopMode];
      hms.h = (hms.h % SignAngleD) * Constants.ANGLE_SIGN_D[horoscopMode] / SignAngleD;
      return hms;
   }
   */

   /** Liefert Grad/Stunden, Minuten und Sekunden aus Radiant. ([1], 45)
       @param hms das HMS, das benutzt wird, oder <code>new HMS()</code>.
       @param r Radiantwert.
       @return Grad/Stunden, Minuten und Sekunden aus Radiant. */
   public static final HMS HMSFromR(HMS hms, double r) {
      return HMSFromD(hms,DFromR(r));
   }

   /* DEBUG/unused
   /** Liefert Grad/Stunden, Zeichen, Minuten und Sekunden in
       Abh&auml;nggigkeit vom Modus aus Radiant ([1], 44). Alle Werte
       m&uuml;ssen positiv sein.
       @param hms das HMS, das benutzt wird, oder <code>new HMS()</code>.
       @param r Radiantwert.
       @param hososcopMode Index in die Arrays von Anzahl Zeichen. *
   public static final HMS HZMSFromR(HMS hms, double r, int horoscopMode) {
      hms = HMSFromR(hms,r);
      hms.z = (hms.h / SignAngleD) % Constants.NUMBER_SIGN[horoscopMode];
      hms.h = (hms.h % SignAngleD) * Constants.ANGLE_SIGN_D[horoscopMode] / SignAngleD;
      return hms;
   }
   */

   /** Liefert Grad/Stunden, Minuten und Sekunden aus Radiant in StringBuffer.
       @param r Radiantwert. */
   public static final void HMStringFromR(StringBuffer buf, double r) {
      int h,m,s;
      final double d = DFromR(r);
      h = (int)Math.floor(d); // Y
      final double t = (d-h)*60;
      m = (int)Math.floor(t); // Z
      s = (int)Math.floor((t-m)*60);
      if (h<10) buf.append('0');
      buf.append(h);
      buf.append('°');
      if (m<10) buf.append('0');
      buf.append(m);
      buf.append('\'');
      if (s<10) buf.append('0');
      buf.append(s);
      buf.append('\"');
   }

   /* DEBUG/unused
   /** Liefert Grad/Stunden, Minuten und Sekunden aus Radiant in StringBuffer.
       Dabei sind die Winkel innerhalb eines Zeichens.
       @param r Radiantwert.
       @param hososcopMode Index in die Arrays von Anzahl Zeichen. *
   public static final void HZMStringFromR(StringBuffer buf, double r, int horoscopMode) {
      int h,m,s;
      final double d = DFromR(r);
      h = (int)Math.floor(d); // Y
      final double t = (d-h)*60;
      m = (int)Math.floor(t); // Z
      s = (int)Math.floor((t-m)*60);
      h = (h % SignAngleD)/SignAngleD*Constants.ANGLE_SIGN_D[horoscopMode];
      if (h<10) buf.append('0');
      buf.append(h);
      buf.append('°');
      if (m<10) buf.append('0');
      buf.append(m);
      buf.append('\'');
      if (s<10) buf.append('0');
      buf.append(s);
      buf.append('\"');
   }
   */

   /* DEBUG/unused
   /** Liefert Zeichen in Abh&auml;nggigkeit vom Modus aus Grad. *
   public static final int ZFromD(double d, int horoscopMode) {
      return ( (int) Math.floor(d/SignAngleD) ) % Constants.NUMBER_SIGN[horoscopMode];
   }
   */

   /** Liefert Zeichen in Abh&auml;nggigkeit vom Modus aus Radiant. */
   public static final int ZFromR(double r, int horoscopMode) {
      return ( (int) Math.floor(r/SignAngleR) ) % Constants.NUMBER_SIGN[horoscopMode];
   }

   /** Liefert Winkel in Radiant zum Zeichnen in Abh&auml;nggigkeit vom
       Modus aus Radiant.
       @param r Radiant.
       @param horoscopMode index of horoscope mode/Zeichen.
       @return Winkel in Radiant zu Zeichnung relativ zu 2Pi. */
   public static final double DrawFromR(double r, int horoscopMode) {
      final double t = r/SignAngleR;
      final int sign = ( (int) Math.floor(t) ) % Constants.NUMBER_SIGN[horoscopMode];
      return ( sign + t-Math.floor(t) ) * Constants.ANGLE_SIGN_R[horoscopMode];
   }

// ---------------------------------------------------------------------
// -------------------- Spzialfunktionen -------------------------------
// ---------------------------------------------------------------------

   /** Liefert Sinus von Argument in Grad.
       @param d Gradargument f&uuml;r Sinus.
       @return Sinus von Argument in Grad. */
   public static final double RSinD(double d) {
      return Math.sin(d/PI180);
   }

   /** Liefert Signum von Argument.
       @param d Argument f&uuml;r Sgn.
       @return Signum von Argument. */
   public static final double RSgn(double d) {
      if (d>0.0) return 1.0;
      else if (d<0.0) return -1.0;
      else return 0.0;
   }

// ---------------------------------------------------------------------
// -------------------- Koordinaten Transformationen -------------------
// ---------------------------------------------------------------------

   /* Convert polar to rectangular coordinates (1280 [1], 47).
      @param R distance factor.
      @param A polar angle in Radians.
      @return new x-y coordinates. */
   public static final XY PolToRec(double R, double A) {
     // if (A == 0.0) A = Double.MIN_VALUE; // warum?
     return new XY(R*Math.cos(A),R*Math.sin(A));
   }

   /* DEBUG/unused
   /* Convert polar to rectangular coordinates (1280 [1], 47).
      @param ra distance factor and polar angle in Radians.
      @return new x-y coordinates. *
   public static final XY PolToRec(XY ra) {
     return new XY(ra.x*Math.cos(ra.y),ra.x*Math.sin(ra.y));
   }
   */

   /* Convert rectangular to polar coordinates. (1290 [1], 48)
      @param X x-Koordinate
      @param Y y-Koordinate
      @return new r-a coordinates. */
   public static final XY RecToPol(double X, double Y) {
     // if (Y == 0.0) Y = Double.MIN_VALUE; // warum ?
     return new XY(Math.sqrt(X*X + Y*Y),Angle(X, Y));
   }

   /* DEBUG/unused
   /* Convert rectangular to polar coordinates. (1290 [1], 48)
      @param xy Coordinate
      @return new r-a coordinates. *
   public static final XY RecToPol(XY xy) {
     // if (Y == 0.0) Y = Double.MIN_VALUE; // warum ?
     return new XY(Math.sqrt(xy.x*xy.x + xy.y*xy.y),Angle(xy.x, xy.y));
   }
   */

   /* Convert spherical coordinates to ecliptic coordinates. (Coordinate Conversion
      1315, [1], 49). Dabei wird zuerst auf x,y,z Koordinaten konvertiert.
      @param L Longitude (Right Ascension) in radians, also die L&auml;nge, also lambda.
      @param B Latitude (Declination) in radians, also Breite, also phi.
      @param O is the Obliquity (OB), use -O when converting star or planet positions
             from the Equator to the Ecliptic, also die Schiefe zwischen den
             Koordinatensystemen.

      Die Formeln sind:<BR> r=sqrt(x^2+y^2+z^2); tan(lambda)=y/x; sin(phi)=z/r;<BR>
      bzw.: x = r cos(phi) sin(lambda); y= r cos(phi) sin(lambda); z = r sin(phi)<BR>
      mit -pi/2 <= phi <= pi/2; -pi < lambda <= pi; r >= 0. <BR>
      Dabei ist phi die Breite oder Latitude, lambda die L&auml;nge oder Longitude */
   public static final double SphToEcl(double L, double B, double O) {
     double G; // Longitude value in Radians, also die Länge und das ist genau der Winkel auf der Ekliptik
     double Q; // Latidude value in Radians, das ist genau der Winkel der Ebene...
     XY xy = new XY(), ra = new XY();

     xy = PolToRec(1.0,B); // liefert (r cos(phi), z)
     Q = xy.y; // z-Koordinate merken, also die Höhe der Kugel (nach Formeln Seite 264).
     xy = PolToRec(xy.x,L); // liefert (x,y)
     G = xy.x; // x-Koordinate merken, also die Tiefe der Kugel

     ra = RecToPol(xy.y, Q); // (y,z) wieder konvertieren auf (..,phi)
     xy = PolToRec(ra.x,ra.y+O); // um die Schiefe erhöhten Winken wieder zurückrechnen

     // Q = Math.asin(xy.y);
     ra = RecToPol(G, xy.x); // (x,y) wieder auf Polar, Höhe ist wurscht, weil jetzt in Ekliptik bin
     if ( ra.y< 0.0) ra.y += PI2; // Winkel positiv machen
     // G = ra.y; // und Winkel merken, das ist der lambda
     return ra.y; // We only ever care about and return one of the coordinates.
     // xy.x = G; // Longitude value in Radians, also die Länge und das ist genau der Winkel auf der Ekliptik
     // xy.y = Q; // Latidude value in Radians, das ist genau der Winkel aus der Ebene heraus.
     // return xy;
   }

   /** Generelle Koordinatentransformation. (1315 [1], 51, Koordinaten [2], 182)
       Do a coordinate transformation: Given a longitude and latitude value,
       return the new longitude and latitude values that the same location
       would have, were the equator tilted by a specified number of degrees.
       In other words, do a pole shift! This is used to convert among ecliptic,
       equatorial, and local coordinates, each of which have zero declination
       in different planes. In other words, take into account the Earth's axis. Liefert das
       gleiche, wie <code>SphToEcl</code>, nur direkt und beide Koordinaten.
       @param azi (L) Azimuth bzw. Longitude  bzw. RA in Radians, also die L&auml;nge, also lambda.
       @param alt (B) Altitude bzw. Latitude bzw. Declination in Radians, also Breite, also phi.
       @param tilt (OB) Winkel zwsichen den Koordinatensystemen.
       @return Transformierte Koordinaten. */
   public static final XY CoorXform(double azi, double alt, double tilt) {
     double x, y, a1, l1; // a1=Q, l1=G

     final double sinalt = Math.sin(alt);
     final double cosalt = Math.cos(alt);
     final double sinazi = Math.sin(azi);
     final double sintilt = Math.sin(tilt);
     final double costilt = Math.cos(tilt);

     x = cosalt * sinazi * costilt;
     y = sinalt * sintilt;
     x -= y;
     y = cosalt * Math.cos(azi);
     l1 = Angle(y, x);
     a1 = Math.asin(cosalt * sinazi * sintilt + sinalt * costilt);
     return new XY(l1,a1); // azi = l1; alt = a1;
   }

// ---------------------------------------------------------------------
// -------------------- mathematische Hilfsroutinen --------------------
// ---------------------------------------------------------------------

   /** Modulus function for floating point values, where we bring the given
       parameter to within the range of 0 to 360.
       @param d degree to mod
       @return Modulus function for Degree. */
   public static final double Mod360(double d) {
     if (d >= 360.0) d -= 360.0;   // In most cases, our value is only slightly
     else if (d < 0.0) d += 360.0; // out of range, so we can test for it and
                                   // avoid the more complicated arithmetic.
     if ((d >= 0) && (d < 360.0)) return d;
     return d - Math.floor(d / 360.0) * 360.0;
   }

   /* DEBUG/unused
   /** Modulus function for floating point values, where we bring the given
       parameter to within the range of 0 to 360, where we do not regard the sign. *
   public static final double Mod360Abs(double d) {
     // return CalcUtil.RSgn(d)*Mod360(Math.abs(d));
     if (d >= 360.0) d -= 360.0;
     if ((d >= 0) && (d < 360.0)) return d;
     else return CalcUtil.RSgn(d)*(Math.abs(d) - Math.floor(Math.abs(d)/360.0)*360.0);
   }
   */

   /** Modulus function for floating point values, where we bring the given
       parameter to within the range of 0 to 2Pi.
       @param d randiant to mod
       @return Modulus function for Radiant. */
   public static final double Mod2PI(double d) {
     if (d >= PI2) d -= PI2;   // In most cases, our value is only slightly
     else if (d < 0.0) d += PI2; // out of range, so we can test for it and
                                   // avoid the more complicated arithmetic.
     if ((d >= 0) && (d < PI2)) return d;
     return d - Math.floor(d / PI2) * PI2;
   }

   /* DEBUG/unused
   /** Modulus function for floating point values, where we bring the given
       parameter to within the range of 0 to 2Pi. where we do not regard the sign.  *
   public static final double Mod2PIAbs(double d) {
     // return CalcUtil.RSgn(d)*Mod2PI(Math.abs(d));
     if (d >= PI2) d -= PI2;
     if ((d >= 0) && (d < PI2)) return d;
     else return CalcUtil.RSgn(d)*(Math.abs(d) - Math.floor(Math.abs(d)/PI2)*PI2);
   }
   */

   /** Berechnet Winkel zu Punkt (x,y), d.h. den ATAN(y/x), wobei Sonderfälle,
       wie ATAN(+/-infinity) ber&uuml;cksichtigt werden.
     Given an x and y coordinate, return the angle formed by a line from the
     origin to this coordinate. This is just converting from rectangular to
     polar coordinates; however, we don't determine the radius here. */
   public static final double Angle(double x, double y) {
     double a;

     if (x != 0.0) {
       if (y != 0.0) a = Math.atan(y/x);
       else a = (x < 0.0) ? Math.PI : 0.0;
     } else a = (y < 0.0) ? -Math.PI/2 : Math.PI/2;
     if (a < 0.0) a += Math.PI;
     if (y < 0.0) a += Math.PI;
     return a;
   }

   /** Return the shortest distance between two degrees in the zodiac. This is
       normally their difference, but we have to check if near the Aries point. */
   public static final double MinDistance(double deg1, double deg2) {
      double i = Math.abs(deg2-deg1);
      if (i<Math.PI) return i;
      return PI2-i;
   }

   /** This is just like <code>MinDistance</code>, except the min distance value
       returned will either be positive or negative based on whether the second
       value is ahead or behind the first one in a circular zodiac. */
   public static final double MinDifference(double deg1, double deg2) {
      double i = deg2-deg1;
      if (Math.abs(i) < Math.PI) return i;
      return RSgn(i)*(Math.abs(i) - PI2);
   }

// ---------------------------------------------------------------------
// -------------------- Julianische Datumsroutinen ---------------------
// ---------------------------------------------------------------------

   /** Berechnet den Julianischen Tag aus dem angegebenen Tag, Monat, Jahr.
       Julianisches Datum ([1], 161) Datum in durchnummerierten Tagen seit dem 1.1. 4713BC
       (also -4712) um 12:00. Diese Routine ist gut für alle Datumsangaben seit Tag 0.
       Dazu muss man die Kalender ([1], 166) kennen, Julianischer Kalender (365.25d pro Jahr,
       ab 753BC) bis 1582, ab dann Gregorianischen Kalender (365.2425d pro Jahr).
       Daneben gibt es noch das modifizierte JD ([1], 166).
       2415020 ist 0.1.1900 Mittags in Greenich. (Julian Day JD, [2], 39).
       @param day Tag
       @param month Monat
       @param year Jahr ab -4712
       @return Die Anzahl der Tage seit 1.1.-4712 (12:00)
   */
   public static int julianDay(int day, int month, int year) {
        int im, jD;

        // im = 12*(year+4800)+month-3; // original formula
        im = 12*year+month+57597;
        // jD = (2*(im - (im/12)*12) + 7 + 365*im)/12; // original formula
        jD = (2*(im%12) + 7 + 365*im)/12; // Julian Day number routine
        jD = jD + day + im/48 - 32083;
        if (jD > 2299171)               // take care of dates in Gregorian calendar.
          jD = jD + im/4800 - im/1200 + 38;
        return jD;
   }

   /* DEBUG/unused
   /** Berechnet den Julianischen Tag aus dem angegebenen Tag, Monat, Jahr.
       Diese Routine ist gut für Datumsangaben von 1.3.1800 bis 28.2.2100.
       @param day Tag
       @param month Monat
       @param year Jahr ab 1800
       @return Die Anzahl der Tage seit 1.1.-4712 (12:00) *
   public static int julianDay1800(int day, int month, int year) {
        int jD;

        jD = 367*year - 7*(year+ (month+9)/12)/4;
        jD = (int)((double)jD + 275*(double)month/9 + day + 1721013.5);
        if (100*year+month-190002.5 < 0) jD ++;
        return jD;
   }
   */

   /* DEBUG
   /** Main zum Testen.
   public static void main(String[] args) {

      // test RFromD und DFromR
      double d = 147.3424;
      System.out.println("d="+d);
      System.out.println("RFromD "+RFromD(d)+"("+RFromD(d)/Math.PI+")");
      System.out.println("DFromR "+DFromR(RFromD(d)));

      d = 263.9982;
      System.out.println("d="+d);
      System.out.println("RFromD "+RFromD(d)+"("+RFromD(d)/Math.PI+")");
      System.out.println("DFromR "+DFromR(RFromD(d)));

      int h = 37, m = 10, s = 35;
      System.out.println("h="+h+" m="+m+" s="+s);
      System.out.println("DFromHMS "+DFromHMS(h,m,s));
      HMS hms = HMSFromD(DFromHMS(h,m,s));
      System.out.println("HMSFromD h="+hms.h+" m="+hms.m+" s="+hms.s);
      hms = HZMSFromD(DFromHMS(h,m,s));
      System.out.println("HZMSFromD h="+hms.h+" z="+hms.z+" m="+hms.m+" s="+hms.s);
      h = 97; m = 59; s = 36;
      System.out.println("h="+h+" m="+m+" s="+s);
      System.out.println("RFromHMS "+RFromHMS(h,m,s));
      hms = HMSFromR(RFromHMS(h,m,s));
      System.out.println("HMSFromR h="+hms.h+" m="+hms.m+" s="+hms.s);
      hms = HZMSFromR(RFromHMS(h,m,s));
      System.out.println("HZMSFromR h="+hms.h+" z="+hms.z+" m="+hms.m+" s="+hms.s);

      h = -37;
      m = 10;
      s = 35;
      System.out.println("h="+h+" m="+m+" s="+s);
      System.out.println("DFromHMS "+DFromHMS(h,m,s));
      hms = HMSFromD(DFromHMS(h,m,s));
      System.out.println("HMSFromD h="+hms.h+" m="+hms.m+" s="+hms.s);
      hms = HZMSFromD(DFromHMS(h,m,s));

      double x=0.3, y=0.7;
      System.out.println("x="+x+" y="+y);
      XY xy = RecToPol(x,y);
      System.out.println("RecToPol r="+xy.x+" a="+xy.y);
      xy = PolToRec(xy.x, xy.y);
      System.out.println("PolToRec r="+xy.x+" a="+xy.y);

      // test Julian Day
      System.out.println("Julian Day 0.1.1900 = 2415020: "+julianDay(0,1,1900));
      System.out.println("Julian Day 15.12.1971 = 2441301: "+julianDay(15,12,1971));
      System.out.println("Julian Day 1.1.1990 = 2447893: "+julianDay(1,1,1990));
      System.out.println("Julian Day 1.1.2000 = 2451545: "+julianDay(1,1,2000));

      System.out.println("Julian Day 1800 0.1.1900 = 2415020: "+julianDay1800(0,1,1900));
      System.out.println("Julian Day 1800 15.12.1971 = 2441301: "+julianDay1800(15,12,1971));
      System.out.println("Julian Day 1800 1.1.1990 = 2447893: "+julianDay1800(1,1,1990));
      System.out.println("Julian Day 1800 1.1.2000 = 2451545: "+julianDay1800(1,1,2000));
   }
   */
}

