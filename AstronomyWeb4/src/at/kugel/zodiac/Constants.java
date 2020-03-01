package at.kugel.zodiac;
/**
   Sammlung von Konstanten direkt zum Horoscop. Das sind die Anzahl der
   Zeichen, Namen, etc. Soll das ganze um eine M&ouml;glichkeit erweitert
   werden, also z.B. 6 oder 60 Zeichen, so ist &uuml;berall eine Konstante
   in die Arrays hinzuzuf&uuml;gen und die <code>NUMBER_MAX</code> richtig
   zu setzen, falls sie nicht mehr maximal ist. Weiters sind hier alle
   Konstanten der Aspektberechnung gesammelt, da diese von der
   Planatenberechnung unabh&auml;ngig sind.
   This is a static collection of constants which are inlined - not deployed
   @author Kugel, <i>Theossos Comp Group</i>
   @version 1.12 - removed unused constants in AstroLib
*/
public interface Constants {

   /** Alle m&ouml;glichen Anzahlen von Zeichen. */
   static final int[] NUMBER_SIGN = {2, 3, 4, 12 };

   /** Maximale Nummer of Signs. Standart f&uuml;r Horoskope. K&ouml;nnte auch 60 sein. */
   static final int NUMBER_MAX = 12; // NUMBER_PRINCIP;

   /** Alle m&ouml;glichen Winkel von Zeichen in Radiant. */
   static final double[] ANGLE_SIGN_R = {2.0*Math.PI/2, 2.0*Math.PI/3,
                                         2.0*Math.PI/4, 2.0*Math.PI/12 };

}

