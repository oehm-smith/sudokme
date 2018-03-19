/* 
 * Copyright (c) Sombraro Software (2012).
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.tintuna.sudoku.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tintuna.sudoku.data.Board;
import com.tintuna.sudoku.data.Square;
import com.tintuna.sudoku.exception.SudokmeException;

public class BoardTest {
	Board board = null;

	@Before
	public void setUp() throws Exception {
		this.board = new Board(3, 3);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test001() {
		assertEquals(9, this.board.getRows().size());
		assertEquals(9, this.board.getCols().size());
		assertEquals(9, this.board.getSquares().size());
	}

	@Test
	public void testCols() {
		final Square square = this.board.getSquares().get(0);
		assertEquals(3, square.getCols().size());
		assertNotNull(square.getCols().get(0));
		assertEquals(0, square.getCols().get(0).getColNumber());
		assertNotNull(square.getCols().get(1));
		assertEquals(1, square.getCols().get(1).getColNumber());
		assertNotNull(square.getCols().get(2));
		assertEquals(2, square.getCols().get(2).getColNumber());
	}

	@Test
	public void testRows() {
		final Square square = this.board.getSquares().get(5);
		assertEquals(3, square.getRows().size());
		assertNotNull(square.getRows().get(0));
		assertEquals(3, square.getRows().get(0).getRowNumber());
		assertNotNull(square.getRows().get(1));
		assertEquals(4, square.getRows().get(1).getRowNumber());
		assertNotNull(square.getRows().get(2));
		assertEquals(5, square.getRows().get(2).getRowNumber());
	}

	private void cellTest(final Square square, final int index, final int expectedValue) {
		assertNotNull(square.getCells().get(index));
		assertEquals(expectedValue, square.getCells().get(index).getCellNumber());
	}

	@Test
	public void testCells() {
		final Square square = this.board.getSquares().get(7);
		assertEquals(9, square.getCells().size());
		cellTest(square, 0, 57);
		cellTest(square, 1, 58);
		cellTest(square, 2, 59);
		cellTest(square, 3, 66);
		cellTest(square, 4, 67);
		cellTest(square, 5, 68);
		cellTest(square, 6, 75);
		cellTest(square, 7, 76);
		cellTest(square, 8, 77);
	}

	@Test
	public void testRowCol() throws SudokmeException {
		assertEquals(0, this.board.getRowColCell(0, 0).getCellNumber());
		assertEquals(1, this.board.getRowColCell(0, 1).getCellNumber());
		assertEquals(9, this.board.getRowColCell(1, 0).getCellNumber());
		assertEquals(10, this.board.getRowColCell(1, 1).getCellNumber());

		assertEquals(50, this.board.getRowColCell(5, 5).getCellNumber());

		assertEquals(79, this.board.getRowColCell(8, 7).getCellNumber());
		assertEquals(71, this.board.getRowColCell(7, 8).getCellNumber());
		assertEquals(80, this.board.getRowColCell(8, 8).getCellNumber());
	}

	@Test
	public void testColRow() throws SudokmeException {
		assertEquals(0, this.board.getColRowCell(0, 0).getCellNumber());
		assertEquals(1, this.board.getColRowCell(1, 0).getCellNumber());
		assertEquals(9, this.board.getColRowCell(0, 1).getCellNumber());
		assertEquals(10, this.board.getColRowCell(1, 1).getCellNumber());

		assertEquals(50, this.board.getColRowCell(5, 5).getCellNumber());

		assertEquals(79, this.board.getColRowCell(7, 8).getCellNumber());
		assertEquals(71, this.board.getColRowCell(8, 7).getCellNumber());
		assertEquals(80, this.board.getColRowCell(8, 8).getCellNumber());
	}

}
