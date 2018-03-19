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
import java.util.Set;

import com.tintuna.sudoku.collections.SArrayList;
import com.tintuna.sudoku.collections.SHashMap;
import com.tintuna.sudoku.collections.SList;
import com.tintuna.sudoku.collections.SMap;
import com.tintuna.sudoku.exception.SudokmeStateException;


public class Row implements SList {
	private final int rowNumber;
	private List<Cell> cells;
	private List<Square> squares;
	/**
	 * Keep a data structure to know what values are set on the row so can easily tell you that or the inverse. Helps with validation also.
	 */
	private final SMap<Integer, Boolean> values;

	public Row(final int rowNumber) {
		this.rowNumber = rowNumber;
		this.cells = new SArrayList<Cell>();
		this.squares = new SArrayList<Square>();
		this.values = new SHashMap<Integer, Boolean>();
	}

	public int getRowNumber() {
		return this.rowNumber;
	}

	public List<Cell> getCells() {
		return this.cells;
	}

	public void setCells(final List<Cell> cells) {
		this.cells = cells;
	}

	public List<Square> getSquares() {
		return this.squares;
	}

	public void setSquares(final List<Square> squares) {
		this.squares = squares;
	}

	public void addSquare(final Square square) {
		this.squares.add(square);
	}

	public void addCell(final Cell cell) {
		this.cells.add(cell);
	}

	public void addValueUsed(final int value) throws SudokmeStateException {
		if (this.values.put(value, true) != null) {
			// the previous value was returned. This is an exception as means trying to load same value twice in row
			throw new SudokmeStateException(String.format("Adding duplicate value:%d to same row:%d%n", value, getRowNumber()));
		}
	}

	public void removeValueUsed(final int value) throws SudokmeStateException {
		if (this.values.containsKey(value)) {
			this.values.remove(value);
		} else {
			// the previous value was returned. This is an exception as means trying to load same value twice in row
			throw new SudokmeStateException(String.format("Trying to remove non-existant key with value:%d from row:%d%n", value,
					getRowNumber()));
		}
	}

	public Set<Integer> getAvailableValuesSet() {
		final Set<Integer> valueSet = this.values.keySet();
		final Set<Integer> remaining = Board.getFullSet();
		remaining.removeAll(valueSet);
		return remaining;
	}

	/**
	 * Return a List of the cells in the row with no values in them.
	 * 
	 * @return
	 */
	public List<Cell> emptyCells() {
		final List<Cell> emptyCells = new ArrayList<Cell>();
		for (final Cell cell : getCells()) {
			if (cell.getValue() == 0) {
				emptyCells.add(cell);
			}
		}
		return emptyCells;
	}

	public String printRow() {
		return getCells().toString();
	}

	@Override
	public String toString() {
		return Integer.toString(getRowNumber());
	}

	@Override
	public boolean equals(final Object o) {
		if (o instanceof Row && ((Row) o).getRowNumber() == getRowNumber()) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getRowNumber();
	}

}
