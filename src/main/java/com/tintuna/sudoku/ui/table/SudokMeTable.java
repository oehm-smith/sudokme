/* 
 * Copyright (c) Sombraro Software (2012).
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.tintuna.sudoku.ui.table;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.tintuna.sudoku.exception.TableException;


public class SudokMeTable {
	int sides = 3;
	int rowColPerSide = 3;
	final int WIDTH = 40;

	Display display = null;
	Shell shell = null;
	Table table = null;
	String[] itemStrings;

	public SudokMeTable(final int sides, final int rowColPerSide) {
		this.sides = sides;
		setupUI();
		setupTable();
	}

	private void setupUI() {
		this.display = new Display();
		this.shell = new Shell(this.display);
		this.shell.setMinimumSize(500, 500);
	}

	public void end() {
		while (!this.shell.isDisposed()) {
			if (!this.display.readAndDispatch()) {
				this.display.sleep();
			}
		}
		this.display.dispose();
	}

	private void setupTable() {
		this.shell.setLayout(new GridLayout());
		final int sideCells = this.sides * this.rowColPerSide;
		this.itemStrings = new String[sideCells * sideCells];
		this.table = new Table(this.shell, SWT.VIRTUAL | SWT.MULTI
				| SWT.BORDER);
		final GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 40;
		data.widthHint = 10;
		this.table.setLayoutData(data);
		this.table.setLinesVisible(true);
		this.table.setHeaderVisible(false);
		for (int col = 0; col < this.sides * this.rowColPerSide; col++) {
			final TableColumn column = new TableColumn(this.table, SWT.NONE | SWT.BORDER);
			column.setWidth(this.WIDTH);
		}

		// init model data that sits behind table
		final int cellsPerRow = SudokMeTable.this.sides * SudokMeTable.this.rowColPerSide;
		for (int rowNo = 0; rowNo < cellsPerRow; rowNo++) {
			for (int colNo = 0; colNo < SudokMeTable.this.sides * SudokMeTable.this.rowColPerSide; colNo++) {
				this.itemStrings[(rowNo * cellsPerRow) + colNo] = ".";
			}
		}
		this.table.addListener(SWT.SetData, new Listener() {

			public void handleEvent(Event event) {
					final TableItem item = (TableItem) event.item;
					final int rowNo = event.index;
					final int cellsPerRow = SudokMeTable.this.sides * SudokMeTable.this.rowColPerSide;
					for (int colNo = 0; colNo < cellsPerRow; colNo++) {
						final int cellNo = (rowNo * cellsPerRow) + colNo;
						item.setText(colNo, SudokMeTable.this.itemStrings[cellNo]);
					}
			}
			
		});

		this.table.setItemCount(this.sides * this.rowColPerSide);
		this.shell.pack();
		this.shell.open();
	}

	public void updateCell(final int rowNo, final int colNo, final int value) throws TableException {
		final int maxPerBoardSide = this.rowColPerSide * this.sides;
		if (rowNo >= maxPerBoardSide) {
			throw new TableException("row number (index) must be less than the max per board side: " + maxPerBoardSide + ", its: " + rowNo);
		}
		if (colNo >= maxPerBoardSide) {
			throw new TableException("col number (index) must be less than the max per board side: " + maxPerBoardSide + ", its: " + colNo);
		}
		final int cellNo = rowNo * maxPerBoardSide + colNo;
		if (value != 0) {
			this.itemStrings[cellNo] = Integer.toString(value);
		} else {
			this.itemStrings[cellNo] = ".";
		}
		this.table.clear(rowNo); // makes row dirty so gets redrawn
		this.display.readAndDispatch(); // forces the SWT.SetData event
	}
}
