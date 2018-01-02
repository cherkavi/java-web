/*
 * This class originates from
 * http://www.uncwil.edu/courses/mat111hb/calculators/numcalc.html
 */

package com.linuxnet.jpcsc_wrap.util.eval;

import java.util.Hashtable;

public class ScanString {

	public ScanString() {
		kwords = new Hashtable();
		string = null;
		count = 0;
		position = 0;
		kwlength = 0;
		buffer = new char[32];
		sval = null;
		nval = 0.0D;
	}

	public ScanString(String s) {
		kwords = new Hashtable();
		setString(s);
	}

	public void setString(String s) {
		if (s == null)
			return;
		else {
			string = new String(s);
			return;
		}
	}

	public String getString() {
		return string;
	}

	public void addKeyWord(String s, int i) {
		if (s == null)
			return;
		if (kwlength < s.length())
			kwlength = s.length();
		kwords.put(s.toLowerCase(), new Integer(i));
	}

	public int getKeyValue(String s) {
		if (s == null)
			return -256;
		if (!kwords.containsKey(s.toLowerCase()))
			return -256;
		Integer integer = (Integer) kwords.get(s.toLowerCase());
		if (integer == null)
			return -256;
		else
			return integer.intValue();
	}

	public void resetKeyWords() {
		kwords.clear();
		kwlength = 0;
	}

	public int nextWord() {
		int k = 0;
		char ac[] = new char[string.length()];
		boolean flag = false;
		boolean flag1 = false;
		if (position >= string.length())
			return -259;
		char c;
		for (c = string.charAt(position); c == ' ' || c == '\t' || c == '\n'
				|| c == '\013' || c == '\r'; c = string.charAt(position)) {
			position++;
			if (position >= string.length())
				return -259;
		}

		if (c >= '0' && c <= '9' || c == '.') {
			for (int i = position; i < string.length(); i++) {
				char c1 = string.charAt(i);
				if (flag && (c1 < '0' || c1 > '9'))
					break;
				if (c1 == 'E' || c1 == 'e' || c1 == 'D' || c1 == 'd') {
					flag = true;
					ac[k++] = 'e';
					c1 = string.charAt(i + 1);
					if (c1 == '-' || c1 == '+') {
						ac[k++] = c1;
						i++;
					}
					continue;
				}
				if (flag1 && c1 == '.')
					break;
				if (c1 == '.') {
					flag1 = true;
					ac[k++] = c1;
					continue;
				}
				if (c1 < '0' || c1 > '9')
					break;
				ac[k++] = c1;
			}

			try {
				sval = new String(ac, 0, k);
				nval = Double.valueOf(sval).doubleValue();
				position += k;
				return -258;
			} catch (Exception _ex) {
				return -257;
			}
		}
		int l = -256;
		int i1 = 0;
		for (int j1 = position; j1 < string.length();) {
			ac[k++] = string.charAt(j1++);
			int j = getKeyValue(new String(ac, 0, k));
			if (j != -256) {
				l = j;
				i1 = k;
			} else {
				if (i1 == 0 && k >= kwlength)
					return -257;
				if (k >= kwlength) {
					sval = new String(ac, 0, i1);
					position += i1;
					return l;
				}
			}
		}

		if (i1 != 0) {
			sval = new String(ac, 0, i1);
			position += i1;
			return l;
		} else {
			return -257;
		}
	}

	public boolean isSet() {
		return string != null;
	}

	public static final int UNKNOWN = -256;

	public static final int ERROR = -257;

	public static final int NUMBER = -258;

	public static final int EOS = -259;

	private String string;

	private char buffer[];

	private int count;

	private int position;

	private int kwlength;

	public String sval;

	public double nval;

	private Hashtable kwords;
}