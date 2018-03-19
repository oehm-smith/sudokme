/* 
 * Copyright (c) Sombraro Software (2012).
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.tintuna.sudoku.data;

import java.util.List;
import java.util.Set;

import com.tintuna.sudoku.collections.SArrayList;
import com.tintuna.sudoku.collections.SHashMap;
import com.tintuna.sudoku.collections.SList;
import com.tintuna.sudoku.collections.SMap;
import com.tintuna.sudoku.exception.SudokmeStateException;


public class Square implements SList {
	private final int squareNumber;
	private List<Cell> cells;
	private List<Row> rows;
	private List<Col> cols;
	/**
	 * Keep a data structure to know what values are set on the square so can easily tell you that or the inverse
	 */
	private final SMap<Integer, Boolean> values;

	public Square(final int squareNumber) {
		this.squareNumber = squareNumber;
		this.cells = new SArrayList<Cell>();
		this.rows = new SArrayList<Row>();
		this.cols = new SArrayList<Col>();
		this.values = new SHashMap<Integer, Boolean>();
	}

	public int getSquareNumber() {
		return this.squareNumber;
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

	public void addCol(final Col col) {
		this.cols.add(col);
	}

	public void addRow(final Row row) {
		this.rows.add(row);
	}

	public void addCell(final Cell cell) {
		this.cells.add(cell);
	}

	public void addValueUsed(final int value) throws SudokmeStateException {
		if (this.values.put(value, true) != null) {
			// the previous value was returned. This is an exception as means trying to load same value twice in square
			throw new SudokmeStateException(String.format("Adding duplicate value:%d to same square:%d%n", value, getSquareNumber()));
		}
	}

	public void removeValueUsed(final int value) throws SudokmeStateException {
		if (this.values.containsKey(value)) {
			this.values.remove(value);
		} else {
			// the previous value was returned. This is an exception as means trying to load same value twice in row
			throw new SudokmeStateException(String.format("Trying to remove non-existant key with value:%d from square:%d%n", value,
					getSquareNumber()));
		}
	}

	// TODO - this could go in a super class of row, col and square
	public Set<Integer> getAvailableValuesSet() {
		final Set<Integer> valueSet = this.values.keySet();
		final Set<Integer> remaining = Board.getFullSet();
		remaining.removeAll(valueSet);
		return remaining;
	}

	@Override
	public String toString() {
		return Integer.toString(getSquareNumber());
	}

	@Override
	public boolean equals(final Object o) {
		if (o instanceof Square && ((Square) o).getSquareNumber() == getSquareNumber()) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getSquareNumber();
	}

}
