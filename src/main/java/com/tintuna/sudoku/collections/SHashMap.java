/* 
 * Copyright (c) Sombraro Software (2012).
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.tintuna.sudoku.collections;

import java.util.HashMap;

/** Like a Map (key, value) and Set (only add unique entries) **/
public class SHashMap<K, V> extends HashMap<K, V> implements SMap<K, V> {

	private static final long serialVersionUID = 8507354068857574131L;

	@Override
	public V put(final K key, final V value) {
		// only add key if it doesn't exist already
		if (!super.containsKey(key)) {
			super.put(key, value);
			return null;
		} else {
			return get(key);
		}
	}
}
