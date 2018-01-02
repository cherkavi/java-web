/*
 * This class originates from
 * http://www.uncwil.edu/courses/mat111hb/calculators/numcalc.html
 */

package com.linuxnet.jpcsc_wrap.util.eval;

public class ParseFunction extends ScanString {

	public ParseFunction() {
		root = null;
		independent_x = false;
		independent_y = false;
		independent_z = false;
		debug = false;
		x = 0.0D;
		y = 0.0D;
		z = 0.0D;
		addKeyWord(",", 26);
		addKeyWord("(", 1);
		addKeyWord(")", 2);
		addKeyWord("+", 3);
		addKeyWord("-", 4);
		addKeyWord("/", 5);
		addKeyWord("*", 6);
		addKeyWord("ln", 7);
		addKeyWord("^", 8);
		addKeyWord("pi", 9);
		addKeyWord("e", 10);
		addKeyWord("sin", 11);
		addKeyWord("cos", 12);
		addKeyWord("tan", 13);
		addKeyWord("x", 14);
		addKeyWord("y", 15);
		addKeyWord("z", 16);
		addKeyWord("asin", 17);
		addKeyWord("acos", 18);
		addKeyWord("atan", 19);
		addKeyWord("rad", 20);
		addKeyWord("sqrt", 21);
		addKeyWord("rand", 22);
		addKeyWord("log", 23);
		addKeyWord("exp", 24);
		addKeyWord("rem", 25);
		addKeyWord("atan2", 27);
		addKeyWord("j0", 28);
		addKeyWord("j1", 29);
		addKeyWord("jn", 30);
		addKeyWord("sinh", 31);
		addKeyWord("cosh", 32);
		addKeyWord("tanh", 33);
		addKeyWord("asinh", 34);
		addKeyWord("acosh", 35);
		addKeyWord("atanh", 36);
		addKeyWord("fac", 40);
		addKeyWord("gamma", 41);
		addKeyWord("erf", 42);
		addKeyWord("erfc", 43);
		addKeyWord("normal", 44);
		addKeyWord("poissonc", 45);
		addKeyWord("poisson", 46);
		addKeyWord("chisq", 48);
		addKeyWord("chisqc", 47);
		addKeyWord("igam", 49);
		addKeyWord("igamc", 50);
		addKeyWord("abs", 51);
		addKeyWord("f", 52);
		addKeyWord("g", 53);
		addKeyWord("h", 54);
		addKeyWord("a", 55);
		addKeyWord("b", 56);
		addKeyWord("L", 57);
		addKeyWord("LE", 58);
	}

	public ParseFunction(String s) {
		this();
		setString(s);
	}

	public boolean parse(String s) {
		setString(s);
		return parse();
	}

	public boolean parse() {
		root = new Node();
		if (parseString(root) != -259)
			return false;
		if (debug) {
			System.out.println("Before Reordering:");
			root.print(5);
		}
		reOrderNodes(root);
		if (debug) {
			System.out.println("After Reordering:");
			root.print(5);
		}
		return true;
	}

	public double getResult(double d, double d1, double d2) throws Exception {
		x = d;
		y = d1;
		z = d2;
		return evaluate(root);
	}

	public double getResult(double d, double d1) throws Exception {
		x = d;
		y = d1;
		return evaluate(root);
	}

	public double getResult(double d) throws Exception {
		x = d;
		return evaluate(root);
	}

	public double getResult() throws Exception {
		return evaluate(root);
	}

	public double[] getResults(int i, double ad[]) throws Exception {
		if (i <= 0)
			throw new Exception("Array index error");
		if (ad == null)
			throw new Exception("X Array error");
		double ad1[] = new double[i];
		for (int j = 0; j < i; j++) {
			x = ad[j];
			ad1[j] = evaluate(root);
		}

		return ad1;
	}

	public double[] getResults(int i, double ad[], double ad1[])
			throws Exception {
		if (i <= 0)
			throw new Exception("Array index error");
		if (ad == null)
			throw new Exception("X Array error");
		if (ad1 == null)
			throw new Exception("Y Array error");
		double ad2[] = new double[i];
		for (int j = 0; j < i; j++) {
			x = ad[j];
			y = ad1[j];
			ad2[j] = evaluate(root);
		}

		return ad2;
	}

	public double[] getResults(int i, double ad[], double ad1[], double ad2[])
			throws Exception {
		if (i <= 0)
			throw new Exception("Array index error");
		if (ad == null)
			throw new Exception("X Array error");
		if (ad1 == null)
			throw new Exception("Y Array error");
		if (ad2 == null)
			throw new Exception("Z Array error");
		double ad3[] = new double[i];
		for (int j = 0; j < i; j++) {
			x = ad[j];
			y = ad1[j];
			z = ad2[j];
			ad3[j] = evaluate(root);
		}
		return ad3;
	}

	public boolean[] getVariables() {
		boolean aflag[] = new boolean[3];
		aflag[0] = independent_x;
		aflag[1] = independent_y;
		aflag[2] = independent_z;
		return aflag;
	}

	public void setX(double d) {
		x = d;
	}

	public void setY(double d) {
		y = d;
	}

	public void setZ(double d) {
		z = d;
	}

	private int parseString(Node node) {
		int i = nextWord();
		if (i == -257) {
			System.out.println("Error parsing \"" + super.sval + "\"");
			return -257;
		}
		if (i != -259 && debug)
			System.out.println("Parse: " + super.sval + "\t Token: " + i);
		else if (i == -259 && debug)
			System.out.println("Parse: EOS");
		switch (i) {
		case -259:
		case 2: // '\002'
		case 26: // '\032'
		default:
			break;

		case -258:
			node.type = 1;
			node.value = super.nval;
			return parseString(node);

		case 9: // '\t'
			node.type = 1;
			node.value = 3.1415926535897931D;
			return parseString(node);

		case 10: // '\n'
			node.type = 1;
			node.value = 2.7182818284590451D;
			return parseString(node);

		case 20: // '\024'
			node.type = 1;
			node.value = 0.017453292519943295D;
			return parseString(node);

		case 55: // '7'
			node.type = 1;
			node.value = LocalFuncs.a;
			return parseString(node);

		case 56: // '8'
			node.type = 1;
			node.value = LocalFuncs.b;
			return parseString(node);

		case 22: // '\026'
			node.type = 1;
			node.value = Math.random();
			return parseString(node);

		case 14: // '\016'
		case 15: // '\017'
		case 16: // '\020'
			node.op = i;
			node.type = 4;
			if (i == 14)
				independent_x = true;
			else if (i == 15)
				independent_y = true;
			else if (i == 16)
				independent_z = true;
			return parseString(node);

		case 1: // '\001'
			Node node1 = new Node();
			if (parseString(node1) == 2) {
				node.left = node1;
				node.type = 5;
				node.precedence = 5;
				i = parseString(node);
			} else {
				System.out.println("Parse Failed: missing parentheses");
				i = -257;
			}
			break;

		case 3: // '\003'
		case 4: // '\004'
		case 5: // '\005'
		case 6: // '\006'
		case 8: // '\b'
		case 57: // '9'
		case 58: // ':'
			Node node4 = new Node();
			int j = parseString(node4);
			if (j != -257)
				if ((i == 4 || i == 3) && node.type == 3) {
					node.right = node4;
					node.precedence = 3;
					node.op = i;
					node.type = 0;
				} else {
					Node node2 = new Node(node);
					node.left = node2;
					node.right = node4;
					node.op = i;
					node.type = 0;
					switch (i) {
					case 3: // '\003'
					case 4: // '\004'
					case 57: // '9'
					case 58: // ':'
						node.precedence = 1;
						break;

					case 5: // '\005'
					case 6: // '\006'
						node.precedence = 2;
						break;

					case 8: // '\b'
						node.precedence = 4;
						break;
					}
				}
			i = j;
			break;

		case 7: // '\007'
		case 11: // '\013'
		case 12: // '\f'
		case 13: // '\r'
		case 17: // '\021'
		case 18: // '\022'
		case 19: // '\023'
		case 21: // '\025'
		case 23: // '\027'
		case 24: // '\030'
		case 25: // '\031'
		case 28: // '\034'
		case 29: // '\035'
		case 31: // '\037'
		case 32: // ' '
		case 33: // '!'
		case 34: // '"'
		case 35: // '#'
		case 36: // '$'
		case 40: // '('
		case 41: // ')'
		case 42: // '*'
		case 43: // '+'
		case 44: // ','
		case 51: // '3'
		case 52: // '4'
		case 53: // '5'
		case 54: // '6'
			node.op = i;
			node.type = 2;
			node.precedence = 0;
			i = nextWord();
			if (i != 1) {
				System.out
						.println("Parse Failed: intrinsic function is missing \"(\"");
				i = -257;
				break;
			}
			Node node3 = new Node();
			if (parseString(node3) == 2) {
				node.left = node3;
				i = parseString(node);
			} else {
				System.out
						.println("Parse Failed: intrinsic function is missing \")\"");
				i = -257;
			}
			break;

		case 27: // '\033'
		case 30: // '\036'
		case 45: // '-'
		case 46: // '.'
		case 47: // '/'
		case 48: // '0'
		case 49: // '1'
		case 50: // '2'
			node.op = i;
			node.type = 2;
			node.precedence = 0;
			i = nextWord();
			if (debug)
				System.out.println("Parse: " + super.sval);
			if (i != 1) {
				System.out
						.println("Parse Failed: intrinsic function is missing \"(\"");
				i = -257;
				break;
			}
			Node node5 = new Node();
			if (parseString(node5) == 26) {
				Node node6 = new Node();
				if (parseString(node6) == 2) {
					node.right = node6;
					node.left = node5;
					i = parseString(node);
				} else {
					System.out
							.println("Parse Failed: intrinsic function is missing \")\"");
					i = -257;
				}
			} else {
				System.out
						.println("Parse Failed: intrinsic function is missing \",\"");
				i = -257;
			}
			break;
		}
		return i;
	}

	private double evaluate(Node node) throws Exception {
		double d = 0.0D;
		if (node == null)
			throw new Exception("evaluate: Failed because of null node!");
		switch (node.type) {
		case 5: // '\005'
			d = evaluate(node.left);
			break;

		case 0: // '\0'
			d = evaluateOp(node);
			break;

		case 2: // '\002'
			d = evaluateIntrinsic(node);
			break;

		case 1: // '\001'
			d = node.value;
			break;

		case 4: // '\004'
			if (node.op == 14) {
				d = x;
				break;
			}
			if (node.op == 15) {
				d = y;
				break;
			}
			if (node.op == 16)
				d = z;
			break;

		case 3: // '\003'
		default:
			throw new Exception("evaluate: Unknown type!");
		}
		return d;
	}

	private double evaluateOp(Node node) throws Exception {
		double d = 0.0D;
		switch (node.op) {
		case 3: // '\003'
			if (node.left != null)
				d = evaluate(node.left);
			d += evaluate(node.right);
			break;

		case 4: // '\004'
			if (node.left != null)
				d = evaluate(node.left);
			d -= evaluate(node.right);
			break;

		case 5: // '\005'
			d = evaluate(node.left);
			d /= evaluate(node.right);
			break;

		case 6: // '\006'
			d = evaluate(node.left);
			d *= evaluate(node.right);
			break;

		case 8: // '\b'
			double d1 = evaluate(node.right);
			if (d1 % 1.0D == 0.0D) {
				d = Math.pow(evaluate(node.left), evaluate(node.right));
				break;
			}
			double d2 = evaluate(node.left);
			if (d2 >= 0.0D) {
				d = Math.pow(evaluate(node.left), evaluate(node.right));
				break;
			}
			if (node.right.left.value == 1.0D
					&& node.right.left.right.value % 2D == 1.0D)
				d = -Math.pow(-1D * d2, evaluate(node.right));
			else
				throw new Exception("Negative base.");
			break;

		case 57: // '9'
			if (evaluate(node.left) < evaluate(node.right))
				d = 1.0D;
			else
				d = 0.0D;
			break;

		case 58: // ':'
			if (evaluate(node.left) <= evaluate(node.right))
				d = 1.0D;
			else
				d = 0.0D;
			break;

		default:
			throw new Exception("evaluate: Failed because of Unknown operator!");
		}
		return d;
	}

	private double evaluateIntrinsic(Node node) throws Exception {
		double d = 0.0D;
		switch (node.op) {
		case 11: // '\013'
			d = Math.sin(evaluate(node.left));
			break;

		case 12: // '\f'
			d = Math.cos(evaluate(node.left));
			break;

		case 13: // '\r'
			d = Math.tan(evaluate(node.left));
			break;

		case 17: // '\021'
			d = Math.asin(evaluate(node.left));
			break;

		case 18: // '\022'
			d = Math.acos(evaluate(node.left));
			break;

		case 19: // '\023'
			d = Math.atan(evaluate(node.left));
			break;

		case 7: // '\007'
			d = Math.log(evaluate(node.left));
			break;

		case 21: // '\025'
			d = Math.sqrt(evaluate(node.left));
			break;

		case 23: // '\027'
			d = Math.log(evaluate(node.left)) / Math.log(10D);
			break;

		case 24: // '\030'
			d = Math.exp(evaluate(node.left));
			break;

		case 27: // '\033'
			d = Math.atan2(evaluate(node.left), evaluate(node.right));
			break;

		case 51: // '3'
			d = Math.abs(evaluate(node.left));
			break;

		case 52: // '4'
			d = LocalFuncs.f.getResult(evaluate(node.left));
			break;

		case 53: // '5'
			d = LocalFuncs.g.getResult(evaluate(node.left));
			break;

		case 54: // '6'
			d = LocalFuncs.h.getResult(evaluate(node.left));
			break;

		default:
			throw new Exception(
					"evaluate: Failed because of an unknown intrinsic!");
		}
		return d;
	}

	private void reOrderNodes(Node node) {
		Node node1 = null;
		Node node2 = null;
		if (node == null)
			return;
		node1 = node.right;
		node2 = node.left;
		if (node1 != null && node1.type == 5)
			reOrderNodes(node1);
		if (node2 != null && node2.type == 5)
			reOrderNodes(node2);
		if (node1 != null && (node1.type == 0 || node1.type == 2))
			reOrderNodes(node1);
		if (node2 != null && (node2.type == 0 || node2.type == 2))
			reOrderNodes(node2);
		if (node.type == 5) {
			reOrderNodes(node2);
			return;
		}
		if (node.type == 0 && node1.type == 0
				&& node.precedence >= node1.precedence) {
			Node node3 = new Node(node);
			if (debug)
				node3.print(5);
			node3.right = node1.left;
			if (debug)
				node3.print(5);
			node.replace(node1);
			if (debug)
				node.print(5);
			node.left = node3;
			if (debug)
				node.print(5);
			node1 = null;
			reOrderNodes(node);
			if (debug)
				node.print(5);
		}
	}

	static final int GROUP = 1;

	static final int ENDGROUP = 2;

	static final int ADD = 3;

	static final int SUBTRACT = 4;

	static final int DIVIDE = 5;

	static final int MULTIPLY = 6;

	static final int LN = 7;

	static final int POWER = 8;

	static final int PI = 9;

	static final int E = 10;

	static final int SIN = 11;

	static final int COS = 12;

	static final int TAN = 13;

	static final int X = 14;

	static final int Y = 15;

	static final int Z = 16;

	static final int ASIN = 17;

	static final int ACOS = 18;

	static final int ATAN = 19;

	static final int RAD = 20;

	static final int SQRT = 21;

	static final int RANDOM = 22;

	static final int LOG = 23;

	static final int EXP = 24;

	static final int REMAINDER = 25;

	static final int COMMA = 26;

	static final int ATAN2 = 27;

	static final int J0 = 28;

	static final int J1 = 29;

	static final int JN = 30;

	static final int SINH = 31;

	static final int COSH = 32;

	static final int TANH = 33;

	static final int ASINH = 34;

	static final int ACOSH = 35;

	static final int ATANH = 36;

	static final int FAC = 40;

	static final int GAMMA = 41;

	static final int ERF = 42;

	static final int ERFC = 43;

	static final int NORMAL = 44;

	static final int POISSONC = 45;

	static final int POISSON = 46;

	static final int CHISQC = 47;

	static final int CHISQ = 48;

	static final int IGAM = 49;

	static final int IGAMC = 50;

	static final int ABS = 51;

	static final int F = 52;

	static final int G = 53;

	static final int H = 54;

	static final int A = 55;

	static final int B = 56;

	static final int L = 57;

	static final int LE = 58;

	private Node root;

	private boolean independent_x;

	private boolean independent_y;

	private boolean independent_z;

	private double x;

	private double y;

	private double z;

	public boolean debug;
}