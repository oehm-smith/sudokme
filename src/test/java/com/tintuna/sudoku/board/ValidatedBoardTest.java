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
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.tintuna.sudoku.board.InitialBoard;
import com.tintuna.sudoku.board.LoadedBoard;
import com.tintuna.sudoku.board.PrintedBoard;
import com.tintuna.sudoku.board.ValidatedBoard;
import com.tintuna.sudoku.data.Board;
import com.tintuna.sudoku.data.CellValueBlock;
import com.tintuna.sudoku.exception.SudokmeException;
import com.tintuna.sudoku.exception.SudokmeStateException;
import com.tintuna.sudoku.exception.TableException;

public class ValidatedBoardTest {
	Board board = null;

	@Before
	public void setup() {
		this.board = new Board(3, 3);
		InitialBoard.setupBoard(this.board);
	}

	private void loadBoardWithCorrectSquare() throws SudokmeStateException, SudokmeException, TableException {
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
	}

	@Test
	public void testValidateSquare() throws SudokmeStateException, SudokmeException, TableException {
		loadBoardWithCorrectSquare();

		PrintedBoard.printBoard(this.board);
		assertEquals(ValidatedBoard.SquareRowCellStatus.CORRECT, ValidatedBoard.validateSquare(this.board.getSquares().get(0)));
	}

	private void loadBoardWithCompleteSquare() throws SudokmeStateException, SudokmeException, TableException {
		final CellValueBlock cvb1 = new CellValueBlock(this.board, 0, 0, 1);
		final CellValueBlock cvb2 = new CellValueBlock(this.board, 0, 1, 2);
		final CellValueBlock cvb3 = new CellValueBlock(this.board, 0, 2, 3);
		final CellValueBlock cvb4 = new CellValueBlock(this.board, 1, 0, 4);
		final CellValueBlock cvb5 = new CellValueBlock(this.board, 1, 1, 5);
		final CellValueBlock cvb6 = new CellValueBlock(this.board, 1, 2, 6);
		final CellValueBlock cvb7 = new CellValueBlock(this.board, 2, 0, 7);
		final CellValueBlock cvb8 = new CellValueBlock(this.board, 2, 1, 8);
		final CellValueBlock cvb9 = new CellValueBlock(this.board, 2, 2, 9);
		final List<CellValueBlock> cvbs = new ArrayList<CellValueBlock>();
		cvbs.add(cvb1);
		cvbs.add(cvb2);
		cvbs.add(cvb3);
		cvbs.add(cvb4);
		cvbs.add(cvb5);
		cvbs.add(cvb6);
		cvbs.add(cvb7);
		cvbs.add(cvb8);
		cvbs.add(cvb9);
		LoadedBoard.load(cvbs);
	}

	@Test
	public void testValidateSquareFullComplete() throws SudokmeStateException, SudokmeException, TableException {
		loadBoardWithCompleteSquare();

		PrintedBoard.printBoard(this.board);
		assertEquals(ValidatedBoard.SquareRowCellStatus.COMPLETE, ValidatedBoard.validateSquare(this.board.getSquares().get(0)));
	}

	private void loadBoardWithNotCorrectSquare() throws SudokmeStateException, SudokmeException, TableException {
		final CellValueBlock cvb1 = new CellValueBlock(this.board, 0, 0, 1);
		final CellValueBlock cvb2 = new CellValueBlock(this.board, 0, 1, 2);
		final CellValueBlock cvb3 = new CellValueBlock(this.board, 0, 2, 3);
		final CellValueBlock cvb4 = new CellValueBlock(this.board, 1, 0, 4);
		final CellValueBlock cvb5 = new CellValueBlock(this.board, 1, 1, 4);
		final CellValueBlock cvb6 = new CellValueBlock(this.board, 1, 2, 6);
		final CellValueBlock cvb7 = new CellValueBlock(this.board, 2, 0, 7);
		final CellValueBlock cvb8 = new CellValueBlock(this.board, 2, 1, 8);
		final CellValueBlock cvb9 = new CellValueBlock(this.board, 2, 2, 9);
		final List<CellValueBlock> cvbs = new ArrayList<CellValueBlock>();
		cvbs.add(cvb1);
		cvbs.add(cvb2);
		cvbs.add(cvb3);
		cvbs.add(cvb4);
		cvbs.add(cvb5);
		cvbs.add(cvb6);
		cvbs.add(cvb7);
		cvbs.add(cvb8);
		cvbs.add(cvb9);
		LoadedBoard.load(cvbs);
	}

	@Test(expected = SudokmeStateException.class)
	public void testValidateSquareFullNOT_CORRECT() throws SudokmeStateException, SudokmeException, TableException {
		loadBoardWithNotCorrectSquare();

		PrintedBoard.printBoard(this.board);
		assertEquals(ValidatedBoard.SquareRowCellStatus.NOT_CORRECT, ValidatedBoard.validateSquare(this.board.getSquares().get(0)));
	}

	private void loadBoardWithCompleteRow() throws SudokmeStateException, SudokmeException, TableException {
		final CellValueBlock cvb1 = new CellValueBlock(this.board, 0, 0, 1);
		final CellValueBlock cvb2 = new CellValueBlock(this.board, 0, 1, 2);
		final CellValueBlock cvb3 = new CellValueBlock(this.board, 0, 2, 3);
		final CellValueBlock cvb4 = new CellValueBlock(this.board, 0, 3, 4);
		final CellValueBlock cvb5 = new CellValueBlock(this.board, 0, 4, 5);
		final CellValueBlock cvb6 = new CellValueBlock(this.board, 0, 5, 6);
		final CellValueBlock cvb7 = new CellValueBlock(this.board, 0, 6, 7);
		final CellValueBlock cvb8 = new CellValueBlock(this.board, 0, 7, 8);
		final CellValueBlock cvb9 = new CellValueBlock(this.board, 0, 8, 9);
		final List<CellValueBlock> cvbs = new ArrayList<CellValueBlock>();
		cvbs.add(cvb1);
		cvbs.add(cvb2);
		cvbs.add(cvb3);
		cvbs.add(cvb4);
		cvbs.add(cvb5);
		cvbs.add(cvb6);
		cvbs.add(cvb7);
		cvbs.add(cvb8);
		cvbs.add(cvb9);
		LoadedBoard.load(cvbs);
	}

	@Test
	public void testValidateRowFullComplete() throws SudokmeStateException, SudokmeException, TableException {
		loadBoardWithCompleteRow();

		PrintedBoard.printBoard(this.board);
		assertEquals(ValidatedBoard.SquareRowCellStatus.COMPLETE, ValidatedBoard.validateRow(this.board.getRows().get(0)));
	}

	private void loadBoardWithNotCorrectRow() throws SudokmeStateException, SudokmeException, TableException {
		final CellValueBlock cvb1 = new CellValueBlock(this.board, 0, 0, 1);
		final CellValueBlock cvb2 = new CellValueBlock(this.board, 0, 1, 2);
		final CellValueBlock cvb3 = new CellValueBlock(this.board, 0, 2, 3);
		final CellValueBlock cvb4 = new CellValueBlock(this.board, 0, 3, 4);
		final CellValueBlock cvb5 = new CellValueBlock(this.board, 0, 4, 4);
		final CellValueBlock cvb6 = new CellValueBlock(this.board, 0, 5, 6);
		final CellValueBlock cvb7 = new CellValueBlock(this.board, 0, 6, 7);
		final CellValueBlock cvb8 = new CellValueBlock(this.board, 0, 7, 8);
		final CellValueBlock cvb9 = new CellValueBlock(this.board, 0, 8, 9);
		final List<CellValueBlock> cvbs = new ArrayList<CellValueBlock>();
		cvbs.add(cvb1);
		cvbs.add(cvb2);
		cvbs.add(cvb3);
		cvbs.add(cvb4);
		cvbs.add(cvb5);
		cvbs.add(cvb6);
		cvbs.add(cvb7);
		cvbs.add(cvb8);
		cvbs.add(cvb9);
		LoadedBoard.load(cvbs);
	}

	@Test(expected = SudokmeStateException.class)
	public void testValidateRowFullNOT_CORRECT() throws SudokmeStateException, SudokmeException, TableException {
		loadBoardWithNotCorrectRow();

		PrintedBoard.printBoard(this.board);
		assertEquals(ValidatedBoard.SquareRowCellStatus.NOT_CORRECT, ValidatedBoard.validateRow(this.board.getRows().get(0)));
	}

	private void loadBoardWithCorrectCol() throws SudokmeStateException, SudokmeException, TableException {
		final CellValueBlock cvb1 = new CellValueBlock(this.board, 0, 0, 1);
		final CellValueBlock cvb2 = new CellValueBlock(this.board, 1, 0, 2);
		final CellValueBlock cvb3 = new CellValueBlock(this.board, 2, 0, 3);
		final CellValueBlock cvb4 = new CellValueBlock(this.board, 3, 0, 4);
		final CellValueBlock cvb5 = new CellValueBlock(this.board, 4, 0, 5);
		final CellValueBlock cvb6 = new CellValueBlock(this.board, 5, 0, 6);
		final CellValueBlock cvb7 = new CellValueBlock(this.board, 6, 0, 7);
		final CellValueBlock cvb8 = new CellValueBlock(this.board, 7, 0, 8);
		final CellValueBlock cvb9 = new CellValueBlock(this.board, 8, 0, 9);
		final List<CellValueBlock> cvbs = new ArrayList<CellValueBlock>();
		cvbs.add(cvb1);
		cvbs.add(cvb2);
		cvbs.add(cvb3);
		cvbs.add(cvb4);
		cvbs.add(cvb5);
		cvbs.add(cvb6);
		cvbs.add(cvb7);
		cvbs.add(cvb8);
		cvbs.add(cvb9);
		LoadedBoard.load(cvbs);
	}

	@Test
	public void testValidateColFullComplete() throws SudokmeStateException, SudokmeException, TableException {
		loadBoardWithCorrectCol();

		PrintedBoard.printBoard(this.board);
		assertEquals(ValidatedBoard.SquareRowCellStatus.COMPLETE, ValidatedBoard.validateCol(this.board.getCols().get(0)));
	}

	private void loadBoardWithNotCorrectCol() throws SudokmeStateException, SudokmeException, TableException {
		final CellValueBlock cvb1 = new CellValueBlock(this.board, 0, 0, 1);
		final CellValueBlock cvb2 = new CellValueBlock(this.board, 1, 0, 2);
		final CellValueBlock cvb3 = new CellValueBlock(this.board, 2, 0, 3);
		final CellValueBlock cvb4 = new CellValueBlock(this.board, 3, 0, 4);
		final CellValueBlock cvb5 = new CellValueBlock(this.board, 4, 0, 4);
		final CellValueBlock cvb6 = new CellValueBlock(this.board, 5, 0, 6);
		final CellValueBlock cvb7 = new CellValueBlock(this.board, 6, 0, 7);
		final CellValueBlock cvb8 = new CellValueBlock(this.board, 7, 0, 8);
		final CellValueBlock cvb9 = new CellValueBlock(this.board, 8, 0, 9);
		final List<CellValueBlock> cvbs = new ArrayList<CellValueBlock>();
		cvbs.add(cvb1);
		cvbs.add(cvb2);
		cvbs.add(cvb3);
		cvbs.add(cvb4);
		cvbs.add(cvb5);
		cvbs.add(cvb6);
		cvbs.add(cvb7);
		cvbs.add(cvb8);
		cvbs.add(cvb9);
		LoadedBoard.load(cvbs);
	}

	@Test(expected = SudokmeStateException.class)
	public void testValidateColFullNOT_CORRECT() throws SudokmeStateException, SudokmeException, TableException {
		loadBoardWithNotCorrectCol();

		PrintedBoard.printBoard(this.board);
		assertEquals(ValidatedBoard.SquareRowCellStatus.NOT_CORRECT, ValidatedBoard.validateCol(this.board.getCols().get(0)));
	}

	@Test
	public void testValidateSquareGivenRowColComplete() throws SudokmeStateException, SudokmeException, TableException {
		loadBoardWithCompleteSquare();

		assertEquals(ValidatedBoard.SquareRowCellStatus.COMPLETE, ValidatedBoard.validateSquareFromRowCol(this.board, 0, 0));
	}

	@Test
	public void testValidateSquareGivenRowColCorect() throws SudokmeStateException, SudokmeException, TableException {
		loadBoardWithCorrectSquare();

		assertEquals(ValidatedBoard.SquareRowCellStatus.CORRECT, ValidatedBoard.validateSquareFromRowCol(this.board, 0, 0));
	}

	@Test(expected = SudokmeStateException.class)
	public void testValidateSquareGivenRowColNOT_CORRECT() throws SudokmeStateException, SudokmeException, TableException {
		loadBoardWithNotCorrectSquare();

		assertEquals(ValidatedBoard.SquareRowCellStatus.NOT_CORRECT, ValidatedBoard.validateSquareFromRowCol(this.board, 0, 0));
	}

}
