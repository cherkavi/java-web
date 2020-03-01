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
import at.kugel.zodiac.house.HousePlacidus;
import at.kugel.zodiac.planet.PlanetAA0;
/**
 * Demo method for the AstroLib.  
 * @author Kugel, <i>Theossos Comp Group</i>
 * @version 1.12
 */
public class Demo {

   /** Ctor. */
   public Demo() {
      // do nothing
   }

   /** Main for Testing. 
    * @param args unused. */
   public static void main(String[] args) {
      // get a horoscop instance
      final TextHoroscop horoscop = new TextHoroscop();

      // set your desired planet position calculation algorithm
      horoscop.setPlanet(new PlanetAA0());

      // set your desired house system calculation algorithm
      // may be anything from the at.kugel.zodiac.house package.
      horoscop.setHouse(new HousePlacidus());

      // set your user data time value
      horoscop.setTime(1, 12, 1953, 21 - 1.0);

      // set your user data location value
      horoscop.setLocationDegree(-16 - 17.0 / 60, 48 + 4.0 / 60);

      // calculate the values
      horoscop.calcValues();

      // do something with the data or output raw data
      System.out.println(horoscop.toString());
   }
}
