package at.kugel.zodiac.planet;

import at.kugel.zodiac.util.CalcUtil;
import at.kugel.zodiac.util.XY;
/**
   Planetenpositionen nach Formel AA0 ([1] 73). Gilt von -4711 bis heute
   mit Genauigkeit von ca. 1 Grad.
   @see at.kugel.zodiac.planet.PlanetInt PlanetInt (Basisinterface)
   @see at.kugel.zodiac.planet.PlanetBasic PlanetBasic (Basisimplementierung)
   @author Kugel, <i>Theossos Comp Group</i>
   @version 1.00 - 29042000 finished.
   @version 1.10 - 02022002 ok for JDK 1.0 and 1.1; no changes
   @version 1.12 - removed warnings
   @since JDK 1.0
*/
public class PlanetAA0 extends PlanetBasic {

  /** Daten f&uuml;r Berechnung:
      Anomaly (3), Eccentricity (3), SemiMajor Axis (1), Periphelion (3)
      Ascending Node (3), Inclination (3). D&uuml;rften in Grad angegeben
      sein.*/
  private static double[][] Array = {
      {358.47584,35999.0498,-0.00015,0.016751,-0.41E-4,0,1.00000013,101.22083,1.71918,0.00045,0,0,0,0,0,0},                         // Earth/Sun
      {102.27938,149472.515,0,0.205614,0.2E-4,0,0.387098,28.75375,0.37028,0.00012,47.14594,1.1852,0.00017,7.00288,0.00186,-0.1E-4}, // Mercury
      {212.60322,58517.8039,0.00129,0.00682,-0.4E-4,0,0.723332,54.38419,0.50819,-0.00139,75.77965,0.89985,0.00041,3.39363,0.001,0}, // Venus
      {319.5293,19139.8585,0.18E-3,0.09331,0.9E-4,0,1.52369,285.43176,1.06977,0.13E-3,48.78644,0.77099,0,1.85033,-0.68E-3,0.1E-4},  // Mars
      {225.32833,3034.69202,0.00072,0.04833,0.00016,0,5.202561,273.27754,0.59943,0.0007,99.44338,1.01053,0.00035,1.30874,-0.005696,0}, // Jupiter, - Fehler
      {175.46622,1221.55147,-0.0005,0.05589,-0.00035,0,9.55475,338.30777,1.08522,0.00098,112.79039,0.873195,-0.00015,2.49252,-0.00392,-0.2E-4}, // Saturn
      {72.64882,428.37911,0.8E-4,0.046344,-0.3E-4,0,19.21814,98.07155,0.98577,-0.00107,73.4771,0.49867,0.00131,0.77246,0.00063,0.4E-4}, // Uranus
      {37.73067,218.46134,-0.7E-4,0.008997,0,0,30.10957,276.04597,0.32564,0.00014,130.68136,1.09894,0.000249,1.77924,-0.00954,0},   // Neptune
      {229.94722,144.91306,0,0.24864,0,0,39.51774,113.52139,0,0,108.95444,1.39601,0.00031,17.14678,0,0}                             // Pluto
   };

   /** Ctor. */
   public PlanetAA0() {
      // do nothing      
   }

   /** Namen des Planetenberechnungs Algorithmus.
       @return Name des Systems. */
   public String getPlanetName() { return "AA0"; } //  (-4911/9999)

   /** Berechnet Planetenpositionen in Radiant nach Formel AA0. Benutzt
       julianCenturies (T). */
   protected void calcPlanets() {
      // boolean retrogenic = false;
      double m, // mean anomaly in radians, then Radiant von node
             e, // eccentricity
             au, // semi major axis
             rv, // actual radius vector
          // rv1=0,  // rv of sun
             ea, // eccentric anomaly
             in, // Inclination
             s, // Hilfswert zum Lesen der Werte aus dem Array
             a, // Hilfswert, ein Winkel
             v,b, // Hilfswerte
             c,c1=0,d, // heliozentrische Long und Lat in Radians, Long of sun
             x,x1=0,y,y1=0,z,z1=0; // actual planar coordinates, of sun

      // if (test.D.bug) test.D.log("PlanetAA0 ("+this.getClass()+") - calcPlanets called");
      for (int i = 1; i < NUMBER_PLANET; i ++) { // loop für 9 Planeten

         // Mean anomaly (in radians) einlesen.
         s = Array[i-1][0]+(Array[i-1][1]+Array[i-1][2]*julianCenturies)*julianCenturies;
         m = CalcUtil.RFromD(CalcUtil.Mod360(s));

         // eccentricity einlesen
         e = Array[i-1][3]+(Array[i-1][4]+Array[i-1][5]*julianCenturies)*julianCenturies;

         // solve Kepler's Equation for eccentric anomaly by iteration
         ea = m; // Startwert der Iteration
         for (int j = 1; j<=5; j++) ea = m + e*Math.sin(ea);

         // calculate Radius vector
         au = Array[i-1][6];
         rv = CalcUtil.RFromD(au)*(1.0 - e*Math.cos(ea));

         // calculate true anomaly
         XY xy = CalcUtil.RecToPol(au * (Math.cos(ea) - e),
                                   au * Math.sin(ea) * Math.sqrt(1.0 - e*e));
         s = Array[i-1][7]+(Array[i-1][8]+Array[i-1][9]*julianCenturies)*julianCenturies; // argument of the perihelion
         a = CalcUtil.DFromR(xy.y) + s;
         s = Array[i-1][10]+(Array[i-1][11]+Array[i-1][12]*julianCenturies)*julianCenturies; // ascending node
         v = CalcUtil.Mod360(a+s); // Grad von Pol+ Perhelion + node
         b = CalcUtil.RFromD(v); // Radiant von Pol+ Perhelion + node
         m = CalcUtil.RFromD(s); // Radiant von node

         // reduce to ecliptic
         s = Array[i-1][13]+(Array[i-1][14]+Array[i-1][15]*julianCenturies)*julianCenturies;
         in = CalcUtil.RFromD(s);
         a = Math.atan(Math.cos(in)*Math.tan(b-m));
         if (a<0) a += Math.PI;
         // a = CalcUtil.DFromR(a+m);
         // if (Math.abs(v-a)>10.0 ) a -= 180;
         a = a+m;
         if (Math.abs(b-a)>CalcUtil.RFromD(10.0) ) a -= Math.PI;
         // c = CalcUtil.RFromD(CalcUtil.Mod360(a));
         c = CalcUtil.Mod2PI(a);
         d = Math.atan(Math.sin(c-m)*Math.tan(in));

         // check for retrogade motion (circular orbit assumed)
         if (i==1) {
            //rv1 = rv;
            c1 = c;
         } else {
/* What is that for
            x = ( Math.sqrt(rv)+Math.sqrt(rv1) )* Math.sqrt(rv1) * Math.sqrt(rv) /
                (rv*Math.sqrt(rv) + rv1*Math.sqrt(rv1));
            x = x - Math.cos(c1 - c);
            if (x < 0) retrogenic = true;
            else retrogenic = false;
*/
         }

         // transform helio to recuangular (x,y,z)
         x = rv*Math.cos(d)*Math.cos(c);
         y = rv*Math.cos(d)*Math.sin(c);
         z = rv*Math.sin(d);
         // transform helio to geocentrix x/y/z
         if (i==1) {
            x1 = x;
            y1 = y;
            z1 = z;
         } else {
            x = x - x1;
            y = y - y1;
            z = z - z1;
         }

         // transform geo x/y/z to geo polar (longitude)
         xy = CalcUtil.RecToPol(x,y);
         if (i==1) planetsR[0] = CalcUtil.Mod2PI(c1+Math.PI);
         else planetsR[i] = xy.y;
/*
         if (i==1) c = CalcUtil.Mod2PI(c1+Math.PI);
         else c = xy.y;
         c[i] = c;

         xy = CalcUtil.RecToPol(xy.x,z);
         a = xy.y;
         if (a > 0.35) a -= CalcUtil.PI2;
         d[i] = a;
*/
      }

      // calculate Moon
      calculateMoon();
   }

   /** Calculate Moon Position (4700, [1], 90).
       Calculate the position and declination of the Moon, and the Moon's
       North Node. This has to be done separately from the other planets,
       because they all orbit the Sun, while the Moon orbits the Earth.
       Weiters berechnet es den Mondknoten (Nordknoten). Verwendet SO. */
   protected final void calculateMoon() {
     double D, // Mean elongation of Moo from Sun
            ML; // Compute Moon's perturbations longitide

     final double M = 3600.0; // Divisor
     //     T2; // julian centuries squared
     final double T2 = julianCenturies*julianCenturies;
     //     LL, // Compute mean lunar longitude
     final double LL = 973563.0+1732564379.0*julianCenturies-4.0*T2; // Compute mean lunar longitude
     //     G,  // Sun's mean longitude of perigee
     final double G = 1012395.0+6189.0*julianCenturies;              // Sun's mean longitude of perigee
     //     N,  // Compute mean lunar node
     final double N = 933060.0-6962911.0*julianCenturies+7.5*T2;     // Compute mean lunar node in seconds, convert to degree
     //     G1, // Mean longitude of lunar perigee
     final double G1 = 1203586.0+14648523.0*julianCenturies-37.0*T2; // Mean longitude of lunar perigee
     D = 1262655.0+1602961611.0*julianCenturies-5.0*T2; // Mean elongation of Moon from Sun

     // Some auxiliary angles
     //     L, L1, T1, Y, // Some auxiliary angles
     final double L = (LL-G1)/M;
     final double L1 = ((LL-D)-G)/M;
     final double T1 = (LL-N)/M;           // called F
     D = D/M;
     final double Y = 2.0*D;

     // Compute Moon's perturbations.
     ML = 22639.6*CalcUtil.RSinD(L) -      4586.4*CalcUtil.RSinD(L-Y) + 2369.9*CalcUtil.RSinD(Y) +
            769.0*CalcUtil.RSinD(2.0*L) -   669.0*CalcUtil.RSinD(L1) -   411.6*CalcUtil.RSinD(2.0*T1) -
            212.0*CalcUtil.RSinD(2.0*L-Y) - 206.0*CalcUtil.RSinD(L+L1-Y);
     ML += 192.0*CalcUtil.RSinD(L+Y) -     165.0*CalcUtil.RSinD(L1-Y) + 148.0*CalcUtil.RSinD(L-L1) -
           125.0*CalcUtil.RSinD(D) -       110.0*CalcUtil.RSinD(L+L1) -  55.0*CalcUtil.RSinD(2.0*T1-Y) -
            45.0*CalcUtil.RSinD(L+2.0*T1) + 40.0*CalcUtil.RSinD(L-2.0*T1);

     if (siderealOffset!=0.0) planetsR[1] = CalcUtil.Mod2PI( CalcUtil.RFromD((LL+ML)/M)+siderealOffset); // Lunar longitude in radians
     else planetsR[1] = CalcUtil.Mod2PI( CalcUtil.RFromD((LL+ML)/M)); // Lunar longitude in radians
/*
     // Compute lunar latitude.
     MB = 18461.5*CalcUtil.RSinD(T1) +  1010.0*CalcUtil.RSinD(L+T1) -   999.0*CalcUtil.RSinD(T1-L) -
            624.0*CalcUtil.RSinD(T1-Y) + 199.0*CalcUtil.RSinD(T1+Y-L) - 167.0*CalcUtil.RSinD(L+T1-Y);
     MB +=  117.0*CalcUtil.RSinD(T1+Y) +  62.0*CalcUtil.RSinD(2.0*L+T1) -33.0*CalcUtil.RSinD(T1-Y-L) -
             32.0*CalcUtil.RSinD(T1-2.0*L)-30.0*CalcUtil.RSinD(L1+T1-Y);
     // MB = RSgn(MB)*((Math.abs(MB)/M)/rDegMax-RFloor((RAbs(MB)/M)/rDegMax))*rDegMax;
     MB = CalcUtil.Mod2PIAbs(CalcUtil.RFromD(MB/M));
*/
/*
     // Compute position of the North Lunar Node, either True or Mean.
     N = N+5392.0*CalcUtil.RSinD(2.0*T1-Y)-   541.0*CalcUtil.RSinD(L1)-   442.0*CalcUtil.RSinD(Y)+
            423.0*CalcUtil.RSinD(2.0*T1)-     291.0*CalcUtil.RSinD(2.0*L-2.0*T1);
     planetsR[10] = CalcUtil.Mod2PI(CalcUtil.RFromD(N/M)+siderealOffset);
     // true north node with lat = 0, otherwise take mean north node n (prior)
*/
   }
}
