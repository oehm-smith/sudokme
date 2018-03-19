/* 
 * Copyright (c) Sombraro Software (2012).
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.tintuna.sudoku.collections;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Utils {
	public static <E> Set<E> setIntersection(final Set<E> set, final Set<E>... sets) {
		for (final Set<E> eachSet : sets) {
			set.retainAll(eachSet);
		}
		return set;
	}

	public static <E> List<E> setAsList(final Set<E> set) {
		Object[] theArray = new Object[set.size()];
		theArray = set.toArray(theArray);
		@SuppressWarnings("unchecked")
		final List<E> theList = (List<E>) Arrays.asList(theArray);
		return theList;
	}
}
