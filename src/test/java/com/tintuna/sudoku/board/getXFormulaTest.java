/* 
 * Copyright (c) Sombraro Software (2012).
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.tintuna.sudoku.board;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.tintuna.sudoku.data.Board;

public class getXFormulaTest {

	@Test
	public void rowFromCell() {
		final int[] cells = { 0, 1, 8, 9, 17, 18, 19 };
		final int[] expected = { 0, 0, 0, 1, 1, 2, 2 };
		for (int i = 0; i < cells.length; i++) {
			assertEquals(expected[i], rowNoFromCellNo(cells[i]));
		}
	}

	private int rowNoFromCellNo(final int cellNo) {
		return cellNo / (Board.getNumberColsRowsPerSquare() * Board.getNumberColsRowsPerSquare());
	}

	@Test
	public void colFromCell() {
		final int[] cells = { 0, 1, 8, 9, 17, 18, 19 };
		final int[] expected = { 0, 1, 8, 0, 8, 0, 1 };
		for (int i = 0; i < cells.length; i++) {
			assertEquals(expected[i], colNoFromCellNo(cells[i]));
		}
	}

	private int colNoFromCellNo(final int cellNo) {
		return cellNo % (Board.getNumberColsRowsPerSquare() * Board.getNumberColsRowsPerSquare());
	}

}
