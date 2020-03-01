package at.kugel.zodiac.util;
/**
   Kontainer f&uuml;r die &Uuml;bergabe von H:M:S aus den diversen
   Umrechenroutinem von <code>CalcUtil</code>.
   @see at.kugel.zodiac.util.CalcUtil CalcUtil (mainly used there)
   @author Kugel, <i>Theossos Comp Group</i>
   @version 1.00 - 01032000 finished.
   @version 1.10 - 02022002 ok for JDK 1.0 and 1.1; removed unused code.
   @version 1.12 - removed warnings
   @since JDK 1.0
*/
public final class HMS {
   /** Stunden. */
   public int h;
   /** Minuten. */
   public int m;
   /** Sekunden. */
   public int s;
   /** Zeichen falls Ausgabe f&uuml;r Horoskop verwendet wird, 0 ist kein
       Zeichen. */
   public int z;

   /** Leerer Konstruktor. Werte k&ouml;nnen sp&auml;ter gesetzt werden,
       direkt da <code>public<code> sind.*/
   public HMS() { 
      // do nothing
   }

   /* DEBUG/unused
   /** Standart Konstruktor.
       @param h Stunden.
       @param m Minuten.
       @param s Sekunden. *
   public HMS(int h, int m, int s) {
      this.h = h;
      this.m = m;
      this.s = s;
      this.z = 0; // no sign
   }
   */

   /* DEBUG/unused
   /** Standart Konstruktor.
       @param h Stunden.
       @param m Minuten.
       @param s Sekunden.
       @param z Zeichen in dem Winkel zu liegen kommt. *
   public HMS(int h, int m, int s, int z) {
      this.h = h;
      this.m = m;
      this.s = s;
      this.z = z;
   }
   */

   /** HMS im Format d°m's\quot; auf String ausgeben. Ist z gesetzt, so
       wird der Winkel modulo Anzahl der Zeichen reduziert. */
   public String toString() {
      return h+"°"+m+'\''+s+'\"';
   }
}

