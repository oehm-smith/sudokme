/* 
 * Copyright (c) Sombraro Software (2012).
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.tintuna.sudoku.data;

import java.util.ArrayList;
import java.util.List;

import com.tintuna.sudoku.exception.SudokmeStateException;


/**
 * Data Struct Object to assist in the loading of the baord as has cell reference coordinates and value
 */
public class CellValueBlock {
	private Board board;
	private int row;
	private int col;
	private int value;
	public static final int MIN_VALUE = 0;
	public static final int MAX_VALUE = Board.getNumberColsRowsPerSquare() * Board.getNumberColsRowsPerSquare();

	public Board getBoard() {
		return this.board;
	}

	public void setBoard(final Board board) {
		this.board = board;
	}

	public int getRow() {
		return this.row;
	}

	public void setRow(final int row) {
		this.row = row;
	}

	public int getCol() {
		return this.col;
	}

	public void setCol(final int col) {
		this.col = col;
	}

	public int getValue() {
		return this.value;
	}

	public void setValue(final int value) {
		this.value = value;
	}

	/**
	 * Constructor
	 * 
	 * @param board
	 * @param row
	 * @param col
	 * @param value
	 * @throws SudokmeStateException if value passed is outside valid range
	 */
	public CellValueBlock(final Board board, final int row, final int col, final int value) throws SudokmeStateException {
		if (value < MIN_VALUE || value > MAX_VALUE) {
			throw new SudokmeStateException("New CellValueBlock - value is:" + value + ", but can't be less than '" + MIN_VALUE
					+ "' or greater than '" + MAX_VALUE + "'");
		}
		this.board = board;
		this.row = row;
		this.col = col;
		this.value = value;
	}

	@Override
	public String toString() {
		return "[CVB - Row: " + getRow() + ", Col: " + getCol() + ", Value: " + getValue() + "]";
	}

	/* ***** Convenience static methods ***** */
	/**
	 * Convenience static method that takes an array or arrays and returns a list of CellValueBlock that can be loaded into a board. There
	 * doesn't need to be an array for every row and each row array doesn't have to have every cell. They populate from top to bottom, left
	 * to right.
	 * 
	 * @param rowsArray where each internal array represents a row - the cells 0..n, where n is the number of array entries
	 * @param board that the rows will be loaded into outside of this
	 * @return a List of CellValueBlocks that can be Loaded into a board
	 * @throws SudokmeStateException if value passed is outside valid range
	 */
	public static List<CellValueBlock> fromRows(final Board board, final Integer[][] rowsArray) throws SudokmeStateException {
		final List<CellValueBlock> cvbRows = new ArrayList<CellValueBlock>();
		int row = 0;
		for (final Integer[] rowArray : rowsArray) {
			int col = 0;
			for (final Integer cellValue : rowArray) {
				cvbRows.add(new CellValueBlock(board, row, col, cellValue));
				col++;
			}
			row++;
		}
		return cvbRows;
	}

	/**
	 * Convenience static method that takes an array or arrays and returns a list of CellValueBlock that can be loaded into a board. There
	 * doesn't need to be an array for every col and each col array doesn't have to have every cell. They populate from left to right, top
	 * to bottom.
	 * 
	 * @param colsArray where each internal array represents a col - the cells 0..n, where n is the number of array entries
	 * @param board that the rows will be loaded into outside of this
	 * @return a List of CellValueBlocks that can be Loaded into a board
	 * @throws SudokmeStateException if value passed is outside valid range
	 */
	public static List<CellValueBlock> fromCols(final Board board, final Integer[][] colsArray) throws SudokmeStateException {
		final List<CellValueBlock> cvbCols = new ArrayList<CellValueBlock>();
		int col = 0;
		for (final Integer[] colArray : colsArray) {
			int row = 0;
			for (final Integer cellValue : colArray) {
				cvbCols.add(new CellValueBlock(board, row, col, cellValue));
				row++;
			}
			col++;
		}
		return cvbCols;
	}

	/**
	 * Convenience static method that takes an array or arrays and returns a list of CellValueBlock that can be loaded into a board. There
	 * doesn't need to be an array for every square and each square array doesn't have to have every cell. They populate from left to right,
	 * top to bottom.
	 * 
	 * @param squaresArray where each internal array represents a square (left to right, top to bottom)
	 * @param board that the square will be loaded into
	 * @return a List of CellValueBlocks that can be Loaded into a board
	 * @throws SudokmeStateException if value passed is outside valid range
	 */
	public static List<CellValueBlock> fromSquares(final Board board, final Integer[][] squaresArray) throws SudokmeStateException {
		final List<CellValueBlock> cvbSquares = new ArrayList<CellValueBlock>();
		int square = 0;
		for (final Integer[] squareArray : squaresArray) {
			int cell = 0;
			for (final Integer cellValue : squareArray) {
				final int a = (square % board.getSides()) * Board.NUMBER_COLS_ROWS_PER_SQUARE; // correct 0,0 col in the given square
				final int b = (cell % Board.NUMBER_COLS_ROWS_PER_SQUARE); // correct offset to col in square
				final int c = (square / board.getSides()) * Board.NUMBER_COLS_ROWS_PER_SQUARE;// correct the 0,0 col in the given square
				final int d = (cell / Board.NUMBER_COLS_ROWS_PER_SQUARE); // correct offset to row in square
				final int row = c + d;
				final int col = a + b;
				cvbSquares.add(new CellValueBlock(board, row, col, cellValue));
				cell++;
			}
			square++;
		}
		return cvbSquares;

	}
}
