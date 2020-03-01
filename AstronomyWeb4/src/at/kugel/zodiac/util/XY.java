package at.kugel.zodiac.util;
/**
   Kontainer f&uuml;r die &Uuml;bergabe von von 2D-Koordinaten aus den
   diversen Umrechenroutinem von <code>CalcUtil</code>.
   @see at.kugel.zodiac.util.CalcUtil CalcUtil (mainly used there)
   @author Kugel, <i>Theossos Comp Group</i>
   @version 1.00 - 01032000 finished.
   @version 1.10 - 02022002 ok for JDK 1.0 and 1.1; no changes
   @since JDK 1.0
*/
public final class XY {

   /** X-Koordinaten bei planaeren Koordinaten, Radius bei Polarkoordinaten,
       Longitude bzw. L&auml;nge bzw. lambda bei Kugelkoordinaten. */
   public double x;

   /** Y-Koordinaten bei planaeren Koordinaten, Winkel bei Polarkoordinaten,
       Latitude bzw. Breite bzw. phi bei Kugelkoordinaten. */
   public double y;

   /** Leerer Konstruktor. Werte k&ouml;nnen sp&auml;ter gesetzt werden,
       direkt da <code>public<code> sind.*/
   public XY () {
      // do nothing
   }

   /** Standart Konstruktor.
       @param x Koordinate.
       @param y Koordinate. */
   public XY (double x, double y) {
      this.x = x;
      this.y = y;
   }
}
