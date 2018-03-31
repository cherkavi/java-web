/*
 * This class originates from
 * http://www.uncwil.edu/courses/mat111hb/calculators/numcalc.html
 */

package com.linuxnet.jpcsc_wrap.util.eval;

public class Node {
	public Node() {
		type = 3;
		left = null;
		right = null;
		op = 3;
		value = 0.0D;
		precedence = 0;
	}

	public Node(Node node) {
		replace(node);
	}

	public void replace(Node node) {
		if (node == null)
			return;
		else {
			op = node.op;
			type = node.type;
			left = node.left;
			right = node.right;
			value = node.value;
			precedence = node.precedence;
			return;
		}
	}

	public void indent(int i) {
		for (int j = 0; j < i; j++)
			System.out.print(" ");
	}

	public void print(int i) {
		indent(i);
		System.out.println("NODE type=" + type);
		indent(i);
		System.out.println("     prec=" + precedence);
		indent(i);
		switch (type) {
		case 1: // '\001'
			System.out.println("     value=" + value);
			return;

		case 4: // '\004'
			System.out.println("     variable=" + op);
			return;

		case 2: // '\002'
		case 3: // '\003'
		default:
			System.out.println("     op=" + op);
			break;
		}
		if (left != null)
			left.print(i + 5);
		if (right != null)
			right.print(i + 5);
	}

	public static final int OP = 0;

	public static final int VALUE = 1;

	public static final int INTRINSIC = 2;

	public static final int NULL = 3;

	public static final int INDEPENDENT = 4;

	public static final int GROUP = 5;

	public static final int PARAMETER = 6;

	public static final int P0 = 0;

	public static final int P1 = 1;

	public static final int P2 = 2;

	public static final int P3 = 3;

	public static final int P4 = 4;

	public static final int P5 = 5;

	int type;

	Node left;

	Node right;

	int op;

	double value;

	int precedence;
}