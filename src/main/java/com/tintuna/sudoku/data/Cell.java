/* 
 * Copyright (c) Sombraro Software (2012).
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.tintuna.sudoku.data;

public class Cell {
	private final int cellNumber;
	private int value;
	private Row row;
	private Col col;
	private Square square;

	public Cell(final int cellNumber) {
		this.cellNumber = cellNumber;
	}

	public int getCellNumber() {
		return this.cellNumber;
	}

	public int getValue() {
		return this.value;
	}

	public void setValue(final int value) {
		this.value = value;
	}

	public Row getRow() {
		return this.row;
	}

	public Col getCol() {
		return this.col;
	}

	public Square getSquare() {
		return this.square;
	}

	public void setSquare(final Square square) {
		this.square = square;
	}

	public void setRow(final Row row) {
		this.row = row;
	}

	public void setCol(final Col col) {
		this.col = col;
	}

	@Override
	public String toString() {
		return getCellNumber() + ":" + getValue();
	}

	@Override
	public boolean equals(final Object o) {
		if (o instanceof Cell && ((Cell) o).getCellNumber() == getCellNumber()) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getCellNumber();
	}
}
