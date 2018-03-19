/* 
 * Copyright (c) Sombraro Software (2012).
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.tintuna.sudoku.board;

import java.util.ArrayList;

import com.tintuna.sudoku.data.Board;
import com.tintuna.sudoku.data.Cell;
import com.tintuna.sudoku.data.Col;
import com.tintuna.sudoku.data.Row;
import com.tintuna.sudoku.data.Square;


public class InitialBoard {
	public static void setupBoard(final Board board) {
		final int numRows = board.getSides() * Board.getNumberColsRowsPerSquare();
		final int numCols = numRows;
		final int numSquares = board.getSides() * board.getSides();
		final int numCells = numRows * numCols;
		board.setCells(new ArrayList<Cell>(numCells));
		board.setRows(new ArrayList<Row>(numRows));
		board.setCols(new ArrayList<Col>(numCols));
		board.setSquares(new ArrayList<Square>(numSquares));

		for (int cellNo = 0; cellNo < numCells; cellNo++) {
			board.getCells().add(cellNo, new Cell(cellNo));

		}
		for (int rowcol = 0; rowcol < numRows; rowcol++) {
			board.getRows().add(rowcol, new Row(rowcol));
			board.getCols().add(rowcol, new Col(rowcol));
		}

		for (int square = 0; square < numSquares; square++) {
			board.getSquares().add(square, new Square(square));
		}
		joinEverything(board);
	}

	/**
	 * Given a row and col (0-indexed), return the square number that the intersection lives in
	 * 
	 * @param row (0-indexed)
	 * @param col (0-indexed)
	 * @return square number that is the intersection of the row and col
	 */
	public static int getSquareFromRowCol(final Board board, final int row, final int col) {
		final int squareNoA = (row / board.getSides());
		final int squareNoB = (col / board.getSides());
		final int square = (squareNoA * board.getSides()) + squareNoB; // not same as rowNo + squareNoB
		return square;
	}

	// Cells are numbered 0..n, left to right, top to bottom
	// Work out the square, row and col join everything up
	private static void joinEverything(final Board board) {
		final int numRows = board.getSides() * Board.getNumberColsRowsPerSquare();
		final int numCols = numRows;
		final int numCells = numRows * numCols;
		for (int cellNo = 0; cellNo < numCells; cellNo++) {
			final int colNo = (cellNo % numCols);
			final int rowNo = (cellNo / numRows);
			final int square = getSquareFromRowCol(board, rowNo, colNo);

			board.getSquares().get(square).addCol(board.getCols().get(colNo));
			board.getSquares().get(square).addRow(board.getRows().get(rowNo));
			board.getSquares().get(square).addCell(board.getCells().get(cellNo));

			board.getRows().get(rowNo).addSquare(board.getSquares().get(square));
			board.getRows().get(rowNo).addCell(board.getCells().get(cellNo));

			board.getCols().get(colNo).addSquare(board.getSquares().get(square));
			board.getCols().get(colNo).addCell(board.getCells().get(cellNo));

			board.getCells().get(cellNo).setRow(board.getRows().get(rowNo));
			board.getCells().get(cellNo).setCol(board.getCols().get(colNo));
			board.getCells().get(cellNo).setSquare(board.getSquares().get(square));
		}
	}
}
