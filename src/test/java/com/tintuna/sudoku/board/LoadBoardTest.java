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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import com.tintuna.sudoku.board.InitialBoard;
import com.tintuna.sudoku.board.LoadedBoard;
import com.tintuna.sudoku.board.PrintedBoard;
import com.tintuna.sudoku.data.Board;
import com.tintuna.sudoku.data.BoardItemType;
import com.tintuna.sudoku.data.CellValueBlock;
import com.tintuna.sudoku.exception.SudokmeException;
import com.tintuna.sudoku.exception.SudokmeStateException;
import com.tintuna.sudoku.exception.TableException;
import com.tintuna.sudoku.test.BoardHelper;


public class LoadBoardTest {
	@Rule
	public TestName unitTestame = new TestName();
	BoardHelper boardHelper = null;
	Board board = null;
	final Integer[] fullRow = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	final Integer[] emptyRCS = {};

	@Before
	public void setUp() throws Exception {
		System.out.println(String.format("Test: %s", this.unitTestame.getMethodName()));

		this.board = new Board(3, 3);
		this.boardHelper = new BoardHelper(this.board);
		InitialBoard.setupBoard(this.board);
	}

	@SuppressWarnings("unused")
	@Test(expected = SudokmeStateException.class)
	public void testCellValueBlock001() throws SudokmeStateException {
		// Values for this board with squares being 3x3 (ie. max of 9, min of 1 (but accept 0 to clear square)) shouldn't allow 21
		new CellValueBlock(this.board, 0, 0, 21);
	}

	@SuppressWarnings("unused")
	@Test(expected = SudokmeStateException.class)
	public void testCellValueBlock002() throws SudokmeStateException {
		// Values for this board with squares being 3x3 (ie. max of 9, min of 1 (but accept 0 to clear square)) shouldn't allow 21
		new CellValueBlock(this.board, 0, 0, -1);
	}

	@Test
	public void testLoad001() throws SudokmeStateException, SudokmeException, TableException {
		final CellValueBlock cvb1 = new CellValueBlock(this.board, 0, 0, 1);
		LoadedBoard.load(cvb1);

		assertEquals(1, this.board.getColRowCell(0, 0).getValue());
		assertEquals(0, this.board.getColRowCell(0, 1).getValue());
		assertEquals(0, this.board.getColRowCell(0, 2).getValue());
		assertEquals(0, this.board.getColRowCell(1, 0).getValue());
		assertEquals(0, this.board.getColRowCell(1, 1).getValue());
		assertEquals(0, this.board.getColRowCell(1, 2).getValue());
		assertEquals(0, this.board.getColRowCell(2, 0).getValue());
		assertEquals(0, this.board.getColRowCell(2, 1).getValue());
		assertEquals(0, this.board.getColRowCell(2, 2).getValue());
	}

	@Test
	public void testLoad002() throws SudokmeStateException, SudokmeException, TableException {
		final CellValueBlock cvb1 = new CellValueBlock(this.board, 0, 0, 1);
		final CellValueBlock cvb2 = new CellValueBlock(this.board, 0, 2, 2);
		final CellValueBlock cvb3 = new CellValueBlock(this.board, 1, 1, 3);
		final CellValueBlock cvb4 = new CellValueBlock(this.board, 2, 2, 4);
		LoadedBoard.load(cvb1);
		LoadedBoard.load(cvb2);
		LoadedBoard.load(cvb3);
		LoadedBoard.load(cvb4);

		assertEquals(1, this.board.getRowColCell(0, 0).getValue());
		assertEquals(0, this.board.getRowColCell(0, 1).getValue());
		assertEquals(2, this.board.getRowColCell(0, 2).getValue());
		assertEquals(0, this.board.getRowColCell(1, 0).getValue());
		assertEquals(3, this.board.getRowColCell(1, 1).getValue());
		assertEquals(0, this.board.getRowColCell(1, 2).getValue());
		assertEquals(0, this.board.getRowColCell(2, 0).getValue());
		assertEquals(0, this.board.getRowColCell(2, 1).getValue());
		assertEquals(4, this.board.getRowColCell(2, 2).getValue());
	}

	@Test
	public void testLoad003() throws SudokmeStateException, SudokmeException, TableException {
		final CellValueBlock cvb1 = new CellValueBlock(this.board, 0, 0, 1);
		final CellValueBlock cvb2 = new CellValueBlock(this.board, 0, 2, 2);
		final CellValueBlock cvb3 = new CellValueBlock(this.board, 1, 1, 3);
		final CellValueBlock cvb4 = new CellValueBlock(this.board, 2, 2, 4);
		final List<CellValueBlock> cvbs = new ArrayList<CellValueBlock>();
		cvbs.add(cvb1);
		cvbs.add(cvb2);
		cvbs.add(cvb3);
		cvbs.add(cvb4);
		LoadedBoard.load(cvbs);

		assertEquals(1, this.board.getRowColCell(0, 0).getValue());
		assertEquals(0, this.board.getRowColCell(0, 1).getValue());
		assertEquals(2, this.board.getRowColCell(0, 2).getValue());
		assertEquals(0, this.board.getRowColCell(1, 0).getValue());
		assertEquals(3, this.board.getRowColCell(1, 1).getValue());
		assertEquals(0, this.board.getRowColCell(1, 2).getValue());
		assertEquals(0, this.board.getRowColCell(2, 0).getValue());
		assertEquals(0, this.board.getRowColCell(2, 1).getValue());
		assertEquals(4, this.board.getRowColCell(2, 2).getValue());
		PrintedBoard.printBoard(this.board);
	}

	@Test
	public void testLoadCompact() throws SudokmeStateException, SudokmeException, TableException {
		final Integer[][] rows = { { 1, 2, 3, 4, 5, 6, 7, 8, 9 }, { 4, 5, 6, 7, 8, 9, 1, 2, 3 }, { 7, 8, 9, 1, 2, 3, 4, 5, 6 } };
		final List<CellValueBlock> cvbrows = CellValueBlock.fromRows(this.board, rows);
		LoadedBoard.load(cvbrows);
		PrintedBoard.printBoard(this.board);
	}

	/**
	 * Helper method -
	 * 
	 * @param rowColSquare is the pre-built array of rows (array) to create board with rows 0..n where n is the number of row arrays
	 *            contained
	 * @param rowColSquareTypeToLoad is the row, col or square number 0..# that is to be loaded on the board
	 * @param rowColSquareToInspect is the row, col or square number 0..# that are to be inspected (so potentially separate from what is
	 *            loaded)
	 * @param expected is the array of expected values in rcsToInspect remaining to be filled in the row
	 * @throws SudokmeStateException
	 * @throws SudokmeException
	 * @throws TableException
	 */
	private void handleAndAssertAvailableRCSValues(final BoardItemType rowColSquareTypeToLoad,
			final BoardItemType rowColSquareTypeToInspect, final Integer[][] rowColSquare,
			final int rowColSquareToInspect, final Integer[] expected)
			throws SudokmeStateException, SudokmeException, TableException {
		List<CellValueBlock> cvbrcs = null;
		Set<Integer> actual = null;
		switch (rowColSquareTypeToLoad) {
			case ROW:
				cvbrcs = CellValueBlock.fromRows(this.board, rowColSquare);
				LoadedBoard.load(cvbrcs);
				break;
			case COL:
				cvbrcs = CellValueBlock.fromCols(this.board, rowColSquare);
				LoadedBoard.load(cvbrcs);
				break;
			case SQUARE:
				cvbrcs = CellValueBlock.fromSquares(this.board, rowColSquare);
				// System.out.println("Square:\n" + cvbrcs);
				LoadedBoard.load(cvbrcs);
				break;
			case CELL:
				throw new SudokmeException("Cell BoardItemType not relevant here");
		}
		switch (rowColSquareTypeToInspect) {
			case ROW:
				actual = this.board.getRows().get(rowColSquareToInspect).getAvailableValuesSet();
				break;
			case COL:
				actual = this.board.getCols().get(rowColSquareToInspect).getAvailableValuesSet();
				break;
			case SQUARE:
				actual = this.board.getSquares().get(rowColSquareToInspect).getAvailableValuesSet();
				break;
			case CELL:
				throw new SudokmeException("Cell BoardItemType not relevant here");
		}

		PrintedBoard.printBoard(this.board);
		final Set<Integer> expectedSet = new HashSet<Integer>(Arrays.asList(expected));
		assertEquals(expectedSet, actual);
	}

	/**
	 * Same as handleAndAssertAvailableRCSValues() version that has a BoardItemType rowColSquareTypeToInspect argument but doesn't have that
	 * arg, only a rowColSquareTypeToLoad and this is used as the Type to inspect also.
	 * 
	 * @param rowColSquareTypeToLoad
	 * @param rowColSquare
	 * @param rowColSquareToInspect
	 * @param expected
	 * @throws SudokmeStateException
	 * @throws SudokmeException
	 * @throws TableException
	 */
	private void handleAndAssertAvailableRCSValues(final BoardItemType rowColSquareTypeToLoad,
			final Integer[][] rowColSquare,
			final int rowColSquareToInspect, final Integer[] expected)
			throws SudokmeStateException, SudokmeException, TableException {
		handleAndAssertAvailableRCSValues(rowColSquareTypeToLoad, rowColSquareTypeToLoad, rowColSquare, rowColSquareToInspect, expected);
	}

	@Test
	public void testAvailableRowValues00() throws SudokmeStateException, SudokmeException, TableException {
		System.out.println(String.format("Test: %s", this.unitTestame.getMethodName()));
		final Integer[][] rows = { { 1, 2, 3, 4, 5, 6, 7, 8, 9 }, { 4, 5, 6, 7, 8, 9, 1, 2, 3 }, { 7, 8, 9, 1, 2, 3, 4, 5, 6 } };
		final Integer[] expected = {};
		final int rcsToInspect = 0;
		handleAndAssertAvailableRCSValues(BoardItemType.ROW, rows, rcsToInspect, expected);
	}

	@Test
	public void testAvailableRowValues01() throws SudokmeStateException, SudokmeException, TableException {
		final Integer[][] rows = { { 1, 2, 3, 4, 5, 6, 7, 8 }, { 4, 5, 6, 7, 8, 9, 1, 2, 3 }, { 7, 8, 9, 1, 2, 3, 4, 5, 6 } };
		final Integer[] expected = { 9 };
		final int rcsToInspect = 0;
		handleAndAssertAvailableRCSValues(BoardItemType.ROW, rows, rcsToInspect, expected);
	}

	@Test
	public void testAvailableRowValues02() throws SudokmeStateException, SudokmeException, TableException {
		final Integer[][] rows = { { 1, 2, 3, 4, 5, 6, 7 }, { 4, 5, 6, 7, 8, 9, 1, 2, 3 }, { 7, 8, 9, 1, 2, 3, 4, 5, 6 } };
		final Integer[] expected = { 8, 9 };
		final int rcsToInspect = 0;
		handleAndAssertAvailableRCSValues(BoardItemType.ROW, rows, rcsToInspect, expected);
	}

	@Test
	public void testAvailableRowValues03() throws SudokmeStateException, SudokmeException, TableException {
		final Integer[][] rows = { { 1 }, { 4, 5, 6, 7, 8, 9, 1, 2, 3 }, { 7, 8, 9, 1, 2, 3, 4, 5, 6 } };
		final Integer[] expected = { 2, 3, 4, 5, 6, 7, 8, 9 };
		final int rcsToInspect = 0;
		handleAndAssertAvailableRCSValues(BoardItemType.ROW, rows, rcsToInspect, expected);
	}

	@Test
	public void testAvailableRowValues04() throws SudokmeStateException, SudokmeException, TableException {
		final Integer[][] rows = { { 3 }, { 4, 5, 6, 7, 8, 9, 1, 2, 3 }, { 7, 8, 9, 1, 2, 3, 4, 5, 6 } };
		final Integer[] expected = { 1, 2, 4, 5, 6, 7, 8, 9 };
		final int rcsToInspect = 0;
		handleAndAssertAvailableRCSValues(BoardItemType.ROW, rows, rcsToInspect, expected);
	}

	// cols
	@Test
	public void testAvailableColValues00() throws SudokmeStateException, SudokmeException, TableException {
		System.out.println(String.format("Test: %s", this.unitTestame.getMethodName()));
		final Integer[][] cols = { { 1, 2, 3, 4, 5, 6, 7, 8, 9 }, { 4, 5, 6, 7, 8, 9, 1, 2, 3 }, { 7, 8, 9, 1, 2, 3, 4, 5, 6 } };
		final Integer[] expected = {};
		final int rcsToInspect = 0;
		handleAndAssertAvailableRCSValues(BoardItemType.COL, cols, rcsToInspect, expected);
	}

	@Test
	public void testAvailableColValues01() throws SudokmeStateException, SudokmeException, TableException {
		final Integer[][] cols = { { 1, 2, 3, 4, 5, 6, 7, 8 }, { 4, 5, 6, 7, 8, 9, 1, 2, 3 }, { 7, 8, 9, 1, 2, 3, 4, 5, 6 } };
		final Integer[] expected = { 9 };
		final int rcsToInspect = 0;
		handleAndAssertAvailableRCSValues(BoardItemType.COL, cols, rcsToInspect, expected);
	}

	@Test
	public void testAvailableColValues02() throws SudokmeStateException, SudokmeException, TableException {
		final Integer[][] cols = { { 1, 2, 3, 4, 5, 6, 7 }, { 4, 5, 6, 7, 8, 9, 1, 2, 3 }, { 7, 8, 9, 1, 2, 3, 4, 5, 6 } };
		final Integer[] expected = { 8, 9 };
		final int rcsToInspect = 0;
		handleAndAssertAvailableRCSValues(BoardItemType.COL, cols, rcsToInspect, expected);
	}

	@Test
	public void testAvailableColValues03() throws SudokmeStateException, SudokmeException, TableException {
		final Integer[][] cols = { { 1 }, { 4, 5, 6, 7, 8, 9, 1, 2, 3 }, { 7, 8, 9, 1, 2, 3, 4, 5, 6 } };
		final Integer[] expected = { 2, 3, 4, 5, 6, 7, 8, 9 };
		final int rcsToInspect = 0;
		handleAndAssertAvailableRCSValues(BoardItemType.COL, cols, rcsToInspect, expected);
	}

	@Test
	public void testAvailableColValues04() throws SudokmeStateException, SudokmeException, TableException {
		final Integer[][] cols = { { 3 }, { 4, 5, 6, 7, 8, 9, 1, 2, 3 }, { 7, 8, 9, 1, 2, 3, 4, 5, 6 } };
		final Integer[] expected = { 1, 2, 4, 5, 6, 7, 8, 9 };
		final int rcsToInspect = 0;
		handleAndAssertAvailableRCSValues(BoardItemType.COL, cols, rcsToInspect, expected);
	}

	// squares -------------------------------------------------------------------------------------------
	@Test
	public void testAvailableSquareValues000() throws SudokmeStateException, SudokmeException, TableException {
		final Integer[][] squares = { { 1, 2, 3, 4, 5, 6, 7, 8, 9 }, { 4, 5, 6, 7, 8, 9, 1, 2, 3 }, { 7, 8, 9, 1, 2, 3, 4, 5, 6 } };
		final Integer[] expected = {};
		final int rcsToInspect = 0;
		handleAndAssertAvailableRCSValues(BoardItemType.SQUARE, squares, rcsToInspect, expected);
	}

	@Test
	public void testAvailableSquareValues001() throws SudokmeStateException, SudokmeException, TableException {
		final Integer[][] squares = { { 1, 2, 3, 4, 5, 6, 7, 8, 9 }, { 4, 5, 6, 7, 8, 9, 1, 2, 3 }, { 7, 8, 9, 1, 2, 3, 4, 5, 6 } };
		final Integer[] expected = {};
		final int rcsToInspect = 0;
		handleAndAssertAvailableRCSValues(BoardItemType.SQUARE, BoardItemType.ROW, squares, rcsToInspect, expected);
	}

	@Test
	public void testAvailableSquareValues002() throws SudokmeStateException, SudokmeException, TableException {
		final Integer[][] squares = { { 1, 2, 3, 4, 5, 6, 7, 8, 9 }, { 4, 5, 6, 7, 8, 9, 1, 2, 3 }, { 7, 8, 9, 1, 2, 3, 4, 5, 6 } };
		final Integer[] expected = {};
		final int rcsToInspect = 0;
		handleAndAssertAvailableRCSValues(BoardItemType.SQUARE, BoardItemType.ROW, squares, rcsToInspect, expected);
	}

	// ----
	@Test
	public void testAvailableSquareValues010() throws SudokmeStateException, SudokmeException, TableException {
		final Integer[][] squares = { { 1, 2, 3, 4, 5, 6, 7, 8 }, { 4, 5, 6, 7, 8, 9, 1, 2, 3 }, { 7, 8, 9, 1, 2, 3, 4, 5, 6 } };
		this.boardHelper.setupBoard(BoardItemType.SQUARE, squares);
		this.boardHelper.assertSituation(BoardItemType.SQUARE, 0, new Integer[] { 9 });
		this.boardHelper.assertSituation(BoardItemType.ROW, 0, this.emptyRCS);
		this.boardHelper.assertSituation(BoardItemType.ROW, 1, this.emptyRCS);
		this.boardHelper.assertSituation(BoardItemType.ROW, 2, new Integer[] { 9 });
		this.boardHelper.assertSituation(BoardItemType.ROW, 3, this.fullRow);
		this.boardHelper.assertSituation(BoardItemType.ROW, 4, this.fullRow);
		this.boardHelper.assertSituation(BoardItemType.ROW, 5, this.fullRow);
		this.boardHelper.assertSituation(BoardItemType.ROW, 6, this.fullRow);
		this.boardHelper.assertSituation(BoardItemType.ROW, 7, this.fullRow);
		this.boardHelper.assertSituation(BoardItemType.ROW, 8, this.fullRow);
		this.boardHelper.assertSituation(BoardItemType.COL, 0, new Integer[] { 2, 3, 5, 6, 8, 9 });
		this.boardHelper.assertSituation(BoardItemType.COL, 1, new Integer[] { 1, 3, 4, 6, 7, 9 });
		this.boardHelper.assertSituation(BoardItemType.COL, 2, new Integer[] { 1, 2, 4, 5, 7, 8, 9 });
		this.boardHelper.assertSituation(BoardItemType.COL, 3, new Integer[] { 2, 3, 5, 6, 8, 9 });
		this.boardHelper.assertSituation(BoardItemType.COL, 4, new Integer[] { 1, 3, 4, 6, 7, 9 });
		this.boardHelper.assertSituation(BoardItemType.COL, 5, new Integer[] { 1, 2, 4, 5, 7, 8 });
		this.boardHelper.assertSituation(BoardItemType.COL, 6, new Integer[] { 2, 3, 5, 6, 8, 9 });
		this.boardHelper.assertSituation(BoardItemType.COL, 7, new Integer[] { 1, 3, 4, 6, 7, 9 });
		this.boardHelper.assertSituation(BoardItemType.COL, 8, new Integer[] { 1, 2, 4, 5, 7, 8 });
		// handleAndAssertAvailableRCSValues(BoardItemType.SQUARE, squares, rcsToInspect, expected);
		// handleAndAssertAvailableRCSValues(BoardItemType.SQUARE, BoardItemType.ROW, squares, rcsToInspect, expected);
		// handleAndAssertAvailableRCSValues(BoardItemType.SQUARE, BoardItemType.COL, squares, rcsToInspect, expected);
	}

	// ----
	@Test
	public void testAvailableSquareValues02() throws SudokmeStateException, SudokmeException, TableException {
		final Integer[][] squares = { { 1, 2, 3, 4, 5, 6, 7 }, { 4, 5, 6, 7, 8, 9, 1, 2, 3 }, { 7, 8, 9, 1, 2, 3, 4, 5, 6 } };
		final Integer[] expected = { 8, 9 };
		final int rcsToInspect = 0;
		handleAndAssertAvailableRCSValues(BoardItemType.SQUARE, squares, rcsToInspect, expected);
	}

	@Test
	public void testAvailableSquareValues03() throws SudokmeStateException, SudokmeException, TableException {
		final Integer[][] squares = { { 1 }, { 4, 5, 6, 7, 8, 9, 1, 2, 3 }, { 7, 8, 9, 1, 2, 3, 4, 5, 6 } };
		final Integer[] expected = { 2, 3, 4, 5, 6, 7, 8, 9 };
		final int rcsToInspect = 0;
		handleAndAssertAvailableRCSValues(BoardItemType.SQUARE, squares, rcsToInspect, expected);
	}

	@Test
	public void testAvailableSquareValues04() throws SudokmeStateException, SudokmeException, TableException {
		final Integer[][] squares = { { 3 }, { 4, 5, 6, 7, 8, 9, 1, 2, 3 }, { 7, 8, 9, 1, 2, 3, 4, 5, 6 } };
		final Integer[] expected = { 1, 2, 4, 5, 6, 7, 8, 9 };
		final int rcsToInspect = 0;
		handleAndAssertAvailableRCSValues(BoardItemType.SQUARE, squares, rcsToInspect, expected);
	}

	@Test
	public void testGetRowFromCell() throws SudokmeStateException, SudokmeException {
		assertEquals(0, this.board.getRowFromCell(this.board.getCells().get(0)).getRowNumber());
		assertEquals(0, this.board.getRowFromCell(this.board.getCells().get(5)).getRowNumber());
		assertEquals(0, this.board.getRowFromCell(this.board.getCells().get(8)).getRowNumber());
		assertEquals(1, this.board.getRowFromCell(this.board.getCells().get(9)).getRowNumber());
		assertEquals(1, this.board.getRowFromCell(this.board.getCells().get(17)).getRowNumber());
		assertEquals(2, this.board.getRowFromCell(this.board.getCells().get(18)).getRowNumber());
		assertEquals(2, this.board.getRowFromCell(this.board.getCells().get(19)).getRowNumber());
		assertEquals(2, this.board.getRowFromCell(this.board.getCells().get(26)).getRowNumber());
		assertEquals(3, this.board.getRowFromCell(this.board.getCells().get(27)).getRowNumber());
		assertEquals(3, this.board.getRowFromCell(this.board.getCells().get(28)).getRowNumber());
		assertEquals(8, this.board.getRowFromCell(this.board.getCells().get(80)).getRowNumber());
	}
}
