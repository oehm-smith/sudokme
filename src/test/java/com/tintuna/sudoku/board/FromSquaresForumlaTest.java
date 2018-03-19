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

/**
 * Using to develop CellValueBlock.fromSquares()
 */
public class FromSquaresForumlaTest {
	private final int num = 3;
	private int sides = 4;

	@Test
	public void forumla00() {

		final int square = 5; // the square
		final int cell = 2; // within the square

		final int expRow = 3;
		final int expCol = 5;
		helper(square, cell, expRow, expCol);
	}

	@Test
	public void forumla01() {

		final int square = 6; // the square
		final int cell = 6; // within the square

		final int expRow = 5;
		final int expCol = 6;
		helper(square, cell, expRow, expCol);
	}

	@Test
	public void forumla02() {

		final int square = 7; // the square
		final int cell = 4; // within the square

		final int expRow = 4;
		final int expCol = 10;
		helper(square, cell, expRow, expCol);
	}

	@Test
	public void forumla03() {
		this.sides = 3;
		final int square = 4; // the square
		final int cell = 4; // within the square

		final int expRow = 4;
		final int expCol = 4;
		helper(square, cell, expRow, expCol);
	}

	@Test
	public void forumla04() {
		this.sides = 3;
		final int square = 5; // the square
		final int cell = 6; // within the square

		final int expRow = 5;
		final int expCol = 6;
		helper(square, cell, expRow, expCol);
	}

	@Test
	public void formul05() {
		this.sides = 3;

		for (int square = 0; square < 9; square++) {
			for (int cell = 0; cell < 9; cell++) {
				helper(square, cell, -1, -1);
			}
		}
	}

	private void helper(final int square, final int cell, final int expRow, final int expCol) {
		final int a = (square % this.sides) * this.num; // correct the 0,0 row NO COL in the given square
		final int b = (cell % this.num); // correct offset to col in square
		final int c = (square / this.sides) * this.num;// correct the 0,0 col NO ROW in the given square
		final int d = (cell / this.num); // correct offset to row in square??
		final int row = c + d;
		final int col = a + b;
		if (expRow >= 0) {
			System.out.println(String.format("Formula - row:%d, expRow:%d, col:%d, ExpCol:%d, a:%d, b%d, c:%d, d:%d, c+b:%d", row, expRow,
					col,
					expCol,
					a, b, c, d, c + b));

			assertEquals(expRow, row);
			assertEquals(expCol, col);
		} else {
			System.out.println(String.format("Formula - square: %d, cell: %d -> row:%d, col:%d", square, cell, row, col));

		}
	}

}
