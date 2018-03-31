/*
 * This class originates from
 * http://www.uncwil.edu/courses/mat111hb/calculators/numcalc.html
 */

package com.linuxnet.jpcsc_wrap.util.eval;

public class Calc {
	public static double num(String s) throws Exception {
		ParseFunction parsefunction = new ParseFunction(s);
		parsefunction.parse();
		if (parsefunction.getVariables()[0] || parsefunction.getVariables()[1]
				|| parsefunction.getVariables()[2])
			throw new Exception("Don't use variables.");
		else
			return parsefunction.getResult();
	}
}