/* 
 * Copyright (c) Sombraro Software (2012).
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.tintuna.sudoku.board;

import java.util.Date;

import com.tintuna.sudoku.data.Board;
import com.tintuna.sudoku.data.Cell;
import com.tintuna.sudoku.data.Row;


public class PrintedBoard {
	public static String returnPrintedBoard(final Board board) {
		final StringBuffer out = new StringBuffer(new Date().toString()).append("\n");
		int rowsPrinted = 0;
		for (final Row row : board.getRows()) {
			int cellsPrinted = 0;
			for (final Cell cell : row.getCells()) {
				if (cell.getValue() == 0) {
					out.append(".");
				} else {
					out.append(cell.getValue());
				}
				if (++cellsPrinted % Board.getNumberColsRowsPerSquare() == 0) {
					out.append("|");
				}
			}
			out.append("\n");
			if (++rowsPrinted % Board.getNumberColsRowsPerSquare() == 0) {
				out.append("------------\n");
			}
		}
		return out.toString();
	}

	public static void printBoard(final Board board) {
		System.out.println(returnPrintedBoard(board));
	}
}
