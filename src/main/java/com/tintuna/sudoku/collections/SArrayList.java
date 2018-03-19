/* 
 * Copyright (c) Sombraro Software (2012).
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.tintuna.sudoku.collections;

import java.util.ArrayList;

/**
 * Extend the Java ArrayList so objects only added if not already in the List. Like a Set but want the List functionality also.
 */
public class SArrayList<E> extends ArrayList<E> {
	private static final long serialVersionUID = -5990330017638473075L;

	public SArrayList() {
		super();
	}

	public SArrayList(final int initialCapacity) {
		super(initialCapacity);
	}

	@Override
	public void add(final int index, final E element) {
		if (!contains(element)) {
			super.add(index, element);
		}
	}

	@Override
	public boolean add(final E element) {
		if (!contains(element)) {
			super.add(element);
			return false;
		}
		return true;
	}
}
