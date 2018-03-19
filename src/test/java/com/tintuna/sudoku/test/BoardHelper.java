/* 
 * Copyright (c) Sombraro Software (2012).
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.tintuna.sudoku.test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tintuna.sudoku.board.LoadedBoard;
import com.tintuna.sudoku.board.PrintedBoard;
import com.tintuna.sudoku.data.Board;
import com.tintuna.sudoku.data.BoardItemType;
import com.tintuna.sudoku.data.CellValueBlock;
import com.tintuna.sudoku.exception.SudokmeException;
import com.tintuna.sudoku.exception.SudokmeStateException;
import com.tintuna.sudoku.exception.TableException;


public class BoardHelper {
	Board board;

	public BoardHelper(final Board board) {
		this.board = board;
	}

	public void setupBoard(final BoardItemType rowColSquareTypeToLoad,
			final Integer[][] rowColSquare)
			throws SudokmeStateException, SudokmeException, TableException {
		List<CellValueBlock> cvbrcs = null;
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
		PrintedBoard.printBoard(this.board);
	}

	public void assertSituation(
			final BoardItemType rowColSquareTypeToInspect,
			final int rowColSquareToInspect, final Integer[] expected)
			throws SudokmeStateException, SudokmeException {
		Set<Integer> actual = null;
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

		final Set<Integer> expectedSet = new HashSet<Integer>(Arrays.asList(expected));
		assertEquals(expectedSet, actual);
	}
}
