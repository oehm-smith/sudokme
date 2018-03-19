/* 
 * Copyright (c) Sombraro Software (2012).
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.tintuna.sudoku.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tintuna.sudoku.data.Board;
import com.tintuna.sudoku.data.Cell;
import com.tintuna.sudoku.data.Col;
import com.tintuna.sudoku.data.Row;
import com.tintuna.sudoku.data.Square;


/**
 * Validate the board after its loaded (as a whole), each square, row and col as they are added to
 */
public class ValidatedBoard {
	public enum SquareRowCellStatus {
		COMPLETE(0, "Complete - Square is full (all positions used) and Correct"),
		CORRECT(1, "Correct - Only 0 or 1 of each valid value is used and is Not Complete"),
		NOT_CORRECT(2, "Not Correct - It is not true that only 0 or 1 of each valid value is used");

		private int code;
		private String description;

		private SquareRowCellStatus(final int code, final String description) {
			this.code = code;
			this.description = description;
		}

		public int getCode() {
			return this.code;
		}

		public void setCode(final int code) {
			this.code = code;
		}

		public String getDescription() {
			return this.description;
		}

		public void setDescription(final String description) {
			this.description = description;
		}
	}

	/**
	 * Validate a square for a) correctness - max of one of each valid number used, b) completeness - if 'full'. The methods 'on the way
	 * end' validated the values going in were correct but they didn't look for repeated numbers.
	 * 
	 * @param baord
	 * @return SquareStatus enum
	 */
	public static SquareRowCellStatus validateSquare(final Square square) {
		return validateGiven(square.getCells());
	}

	/**
	 * Validate a row for a) correctness - max of one of each valid number used, b) completeness - if 'full'. The methods 'on the way end'
	 * validated the values going in were correct but they didn't look for repeated numbers.
	 * 
	 * @param baord
	 * @return SquareStatus enum
	 */
	public static SquareRowCellStatus validateRow(final Row row) {
		return validateGiven(row.getCells());
	}

	/**
	 * Validate a col(umn) for a) correctness - max of one of each valid number used, b) completeness - if 'full'. The methods 'on the way
	 * end' validated the values going in were correct but they didn't look for repeated numbers.
	 * 
	 * @param baord
	 * @return SquareStatus enum
	 */
	public static SquareRowCellStatus validateCol(final Col col) {
		return validateGiven(col.getCells());
	}

	/**
	 * Given a row and col locate the square that holds that and validate the square.
	 * 
	 * @param board
	 * @param row
	 * @param col
	 * @return
	 */
	public static SquareRowCellStatus validateSquareFromRowCol(final Board board, final int row, final int col) {
		final int squareAtRowCol = InitialBoard.getSquareFromRowCol(board, row, col);
		System.out.println(String.format("-> validateSquareFromRowCol (%d, %d), square is %d", row, col, squareAtRowCol));
		final Square square = board.getSquares().get(squareAtRowCol);
		return validateSquare(square);
	}

	/**
	 * Validate the list of cells where the list is either from a row, col or square.
	 * 
	 * @param cells
	 * @return
	 */
	private static SquareRowCellStatus validateGiven(final List<Cell> cells) {
		final int numberOfCellsInSquare = Board.getNumberColsRowsPerSquare() * Board.getNumberColsRowsPerSquare();
		final Map<Integer, Boolean> valuesUsed = new HashMap<Integer, Boolean>();
		for (final Cell cell : cells) {
			if (cell.getValue() > 0) {
				if (valuesUsed.containsKey(cell.getValue())) {
					return SquareRowCellStatus.NOT_CORRECT;
				}
				valuesUsed.put(cell.getValue(), true);
			}
		}
		if (valuesUsed.size() == numberOfCellsInSquare) {
			return SquareRowCellStatus.COMPLETE;
		} else {
			return SquareRowCellStatus.CORRECT;
		}
	}
}
