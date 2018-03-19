/* 
 * Copyright (c) Sombraro Software (2012).
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.tintuna.sudoku.data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tintuna.sudoku.board.InitialBoard;
import com.tintuna.sudoku.exception.SudokmeException;
import com.tintuna.sudoku.exception.SudokmeStateException;
import com.tintuna.sudoku.exception.TableException;
import com.tintuna.sudoku.ui.table.SudokMeTable;


public class Board {
	/**
	 * Sides is the number of squares across and the number of squares down.
	 */
	private static int sides;
	private List<Cell> cells;
	private List<Row> rows;
	private List<Col> cols;
	private List<Square> squares;
	public static int NUMBER_COLS_ROWS_PER_SQUARE = 3;
	public static final Set<Integer> FULL_SET = new HashSet<Integer>();
	private SudokMeTable sudokMeTable = null;

	static {
		for (int i = 1; i <= NUMBER_COLS_ROWS_PER_SQUARE * NUMBER_COLS_ROWS_PER_SQUARE; i++) {
			FULL_SET.add(i);
		}
	}

	public static final Set<Integer> getFullSet() {
		return new HashSet<Integer>(FULL_SET);
	}

	public Board(final int sides, final int NUMBER_COLS_ROWS_PER_SQUARE, final SudokMeTable sudokMeTable) {
		super();
		Board.sides = sides;
		Board.NUMBER_COLS_ROWS_PER_SQUARE = NUMBER_COLS_ROWS_PER_SQUARE;
		this.sudokMeTable = sudokMeTable;
		InitialBoard.setupBoard(this);
	}

	public Board(final int sides, final int NUMBER_COLS_ROWS_PER_SQUARE) {
		this(sides, NUMBER_COLS_ROWS_PER_SQUARE, null);
	}

	public SudokMeTable getSudokMeTable() {
		return this.sudokMeTable;
	}

	public int getSides() {
		return Board.sides;
	}

	public static int getNumberColsRowsPerSquare() {
		return NUMBER_COLS_ROWS_PER_SQUARE;
	}

	public int getMaxRowsCols() {
		return Board.sides * NUMBER_COLS_ROWS_PER_SQUARE;
	}

	public int getRowColMinimumIndex() {
		return 0;
	}

	public int getRowColMaximumIndex() {
		return getMaxRowsCols() - 1;
	}

	public List<Cell> getCells() {
		return this.cells;
	}

	public void setCells(final List<Cell> cells) {
		this.cells = cells;
	}

	public List<Row> getRows() {
		return this.rows;
	}

	public void setRows(final List<Row> rows) {
		this.rows = rows;
	}

	public List<Col> getCols() {
		return this.cols;
	}

	public void setCols(final List<Col> cols) {
		this.cols = cols;
	}

	public List<Square> getSquares() {
		return this.squares;
	}

	public void setSquares(final List<Square> squares) {
		this.squares = squares;
	}

	/**
	 * Given the row and col return the cell at the intersection. DON'T use this to set the cell value - use setRowColCell().
	 * 
	 * @param row
	 * @param col
	 * @return cell at the intersection of row and cell
	 */
	public Cell getRowColCell(final int row, final int col) {
		return getRows().get(row).getCells().get(col);
	}

	/**
	 * Given the col and row return the cell at the intersection. DON'T use this to set the cell value - use setRowColCell().
	 * 
	 * @param row
	 * @param col
	 * @return cell at the intersection of cell and row
	 */
	public Cell getColRowCell(final int col, final int row) {
		return getCols().get(col).getCells().get(row);
	}

	/**
	 * The Row and Col need to keep aware of what values are set in them so use this special method for setting the value.
	 * 
	 * @param row
	 * @param col
	 * @param value
	 * @throws SudokmeStateException
	 * @throws SudokmeException
	 * @throws TableException
	 */
	public void setRowColCell(final int row, final int col, final int value) throws SudokmeStateException, SudokmeException, TableException {
		getRowColCell(row, col).setValue(value); // populate the cell itself
		if (this.sudokMeTable != null) {
			this.sudokMeTable.updateCell(row, col, value);
		}
		if (value > 0) {
			getRows().get(row).addValueUsed(value);
			getCols().get(col).addValueUsed(value);
			getSquares().get(InitialBoard.getSquareFromRowCol(this, row, col)).addValueUsed(value);
		} else {
			throw new SudokmeException("Tried to use negative value: " + value);
		}
	}

	/**
	 * Remove the value from the Row and Col.
	 * 
	 * @param row
	 * @param col
	 * @param value to remove
	 * @throws SudokmeStateException
	 * @throws TableException
	 */
	public void unSetRowColCell(final int row, final int col, final int value) throws SudokmeStateException, TableException {
		getRowColCell(row, col).setValue(0); // unset cell value
		if (this.sudokMeTable != null) {
			this.sudokMeTable.updateCell(row, col, 0);
		}
		getRows().get(row).removeValueUsed(value);
		getCols().get(col).removeValueUsed(value);
		getSquares().get(InitialBoard.getSquareFromRowCol(this, row, col)).removeValueUsed(value);
	}

	/**
	 * Given a cell, return the row
	 * 
	 * @param cell
	 * @return the Row that has that cell
	 */
	public Row getRowFromCell(final Cell cell) {
		return getRows().get(cell.getCellNumber() / (Board.NUMBER_COLS_ROWS_PER_SQUARE * Board.NUMBER_COLS_ROWS_PER_SQUARE));
	}

	/**
	 * Given a cell, return the col
	 * 
	 * @param cell
	 * @return the Col that has that cell
	 */
	public Col getColFromCell(final Cell cell) {
		return getCols().get(cell.getCellNumber() % (Board.NUMBER_COLS_ROWS_PER_SQUARE * Board.NUMBER_COLS_ROWS_PER_SQUARE));
	}

	public int getMaxCells() {
		final int cellsOneSide = NUMBER_COLS_ROWS_PER_SQUARE * getSides();
		return cellsOneSide * cellsOneSide;
	}
}
