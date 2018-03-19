/* 
 * Copyright (c) Sombraro Software (2012).
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.tintuna.sudoku.collections;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.tintuna.sudoku.collections.Utils;

public class UtilsTest {

	@Test
	public void testSetIntersection00() {
		final Set<Integer> a = new HashSet<Integer>();
		final Set<Integer> b = new HashSet<Integer>();
		final Set<Integer> c = new HashSet<Integer>();

		a.add(1);
		a.add(2);
		a.add(3);
		b.add(2);
		b.add(4);
		c.add(2);
		c.add(4);
		c.add(5);

		final Set<Integer> exp = new HashSet<Integer>();
		exp.add(2);

		final Set<Integer> res = Utils.setIntersection(a, b, c);

		assertEquals(exp, res);
	}

	@Test
	public void testSetIntersection01() {
		final Set<Integer> a = new HashSet<Integer>();

		a.add(1);
		a.add(2);
		a.add(3);

		final Set<Integer> exp = new HashSet<Integer>();
		exp.add(1);
		exp.add(2);
		exp.add(3);

		final Set<Integer> res = Utils.setIntersection(a);

		assertEquals(exp, res);
	}

	@Test
	public void testSetIntersection02() {
		final Set<Integer> a = new HashSet<Integer>();
		final Set<Integer> b = new HashSet<Integer>();
		final Set<Integer> c = new HashSet<Integer>();

		a.add(1);
		a.add(2);
		a.add(3);
		b.add(1);
		b.add(2);
		b.add(3);
		c.add(1);
		c.add(2);
		c.add(3);

		final Set<Integer> exp = new HashSet<Integer>();
		exp.add(1);
		exp.add(2);
		exp.add(3);

		final Set<Integer> res = Utils.setIntersection(a, b, c);

		assertEquals(exp, res);
	}

	@Test
	public void testSetIntersection03() {
		final Set<Integer> a = new HashSet<Integer>();

		final Set<Integer> exp = new HashSet<Integer>();

		final Set<Integer> res = Utils.setIntersection(a);

		assertEquals(exp, res);
	}

	@Test
	public void testSetIntersectionList00() {
		final Set<Integer> a = new HashSet<Integer>();
		final Set<Integer> b = new HashSet<Integer>();
		final Set<Integer> c = new HashSet<Integer>();

		a.add(1);
		a.add(2);
		a.add(3);
		b.add(1);
		b.add(2);
		b.add(3);
		c.add(1);
		c.add(2);
		c.add(3);

		final List<Integer> exp = new ArrayList<Integer>();
		exp.add(1);
		exp.add(2);
		exp.add(3);

		final List<Integer> res = Utils.setAsList(Utils.setIntersection(a, b, c));

		assertEquals(exp, res);
	}

	@Test
	public void testSetIntersectionList04() {
		final Set<Integer> a = new HashSet<Integer>();
		final Set<Integer> b = new HashSet<Integer>();
		final Set<Integer> c = new HashSet<Integer>();

		a.add(1);
		a.add(4);
		a.add(3);
		a.add(2);
		a.add(6);
		b.add(2);
		c.add(2);

		final List<Integer> exp = new ArrayList<Integer>();
		exp.add(2);

		final List<Integer> res = Utils.setAsList(Utils.setIntersection(a, b, c));

		assertEquals(exp, res);
	}

}
