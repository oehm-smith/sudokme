/* 
 * Copyright (c) Sombraro Software (2012).
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.tintuna.sudoku.board;

import java.util.List;

import com.tintuna.sudoku.data.CellValueBlock;
import com.tintuna.sudoku.exception.SudokmeException;
import com.tintuna.sudoku.exception.SudokmeStateException;
import com.tintuna.sudoku.exception.TableException;


/**
 * Load a board with values. Duplicates are allowed (subsequent ones rule) and its not until ValidateBoard is used to validate that how full
 * a square, row, col is is checked. CellValueBlock validates that the value to be inserted is in expected range.
 */
public class LoadedBoard {
	/**
	 * Load the board from the given (populated) cellValueBlock.
	 * 
	 * @param cellValueBlock - populated with board, row, col and value
	 * @throws SudokmeStateException if trying to add the same value to the same row, col or square
	 * @throws SudokmeException if validation of the row, col and square fails.
	 * @throws TableException
	 */
	public static void load(final CellValueBlock cellValueBlock) throws SudokmeStateException, SudokmeException, TableException {
		// Validate each affected row, col and square as the cell is added
		// Moved initial loading validation to when the values are added, not in a separate validation step
		cellValueBlock.getBoard().setRowColCell(cellValueBlock.getRow(), cellValueBlock.getCol(), cellValueBlock.getValue());
	}

	public static void load(final List<CellValueBlock> cellValueBlocks) throws SudokmeStateException, SudokmeException, TableException {
		for (final CellValueBlock cvb : cellValueBlocks) {
			LoadedBoard.load(cvb);
		}
	}
}
