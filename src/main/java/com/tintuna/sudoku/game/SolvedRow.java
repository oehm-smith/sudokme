/* 
 * Copyright (c) Sombraro Software (2012).
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.tintuna.sudoku.game;

import java.util.List;

import org.apache.log4j.Logger;

import com.tintuna.sudoku.board.PrintedBoard;
import com.tintuna.sudoku.collections.Utils;
import com.tintuna.sudoku.data.Board;
import com.tintuna.sudoku.data.Cell;
import com.tintuna.sudoku.data.Row;
import com.tintuna.sudoku.exception.SudokmeException;
import com.tintuna.sudoku.exception.SudokmeStateException;
import com.tintuna.sudoku.exception.TableException;


public class SolvedRow {
	static int depth = 0;
	static Logger logger = Logger.getLogger(SolvedRow.class);

	public static List<Row> solveRowEntry(final Board board, final int row) throws TableException {
		runRow(board);
		PrintedBoard.printBoard(board);
		return null;
	}

	public static void runRow(final Board board) throws TableException {
		for (final Row r : board.getRows()) {
			if (fillInRow(board, r) == false) {
				logger.info("runRow - false");
			} else {
				logger.debug("runRow - true");
			}
			break; // for testing
		}
	}

	public static boolean fillInRow(final Board board, final Row row) throws TableException {
		logger.debug(String.format("-> fillInRow(%s) -> %s%n", row.toString(), row.printRow()));
		logger.debug(String.format("Depth:%d", SolvedRow.depth++));

		boolean filled = false;
		for (final Cell cell : row.emptyCells()) {
			final List<Integer> rcsIntersection = getIntersection(row, cell);
			Utils.setAsList(Utils.setIntersection(row.getAvailableValuesSet(), cell.getCol()
					.getAvailableValuesSet(), cell.getSquare().getAvailableValuesSet()));
			logger.debug(String.format("  Row:%d, Col:%d%n", row.getRowNumber(), cell.getCol().getColNumber()));
			logger.debug(String.format("  intersection: %s%n", rcsIntersection.toString()));
			if (rcsIntersection.size() == 1)
			{
				doSetRowColCell(board, row.getRowNumber(), cell.getCol().getColNumber(), rcsIntersection.get(0));

				logger.debug(String.format("    intersection.size == 1 (setValue in cell:%d)%n", rcsIntersection.get(0)));
				filled = true;
			} else if (rcsIntersection.size() == 0) {
				logger.debug(String.format("    intersection.size == 0 - print each available set%n"));
				logger.debug(String.format("      row:%s%n", row.getAvailableValuesSet().toString()));
				logger.debug(String.format("      col:%s%n", cell.getCol().getAvailableValuesSet().toString()));
				logger.debug(String.format("      square:%s%n", cell.getSquare().getAvailableValuesSet().toString()));
				filled = false;
			} else {
				// logger.debug(String.format("    intersection.size == %d%n", rcsIntersection.size()));

				for (final Integer potentialValue : rcsIntersection) {
					// Retrieving the intersection of row/col/square again as might have changed in the loop
					final List<Integer> newIntersection = getIntersection(row, cell);
					if (newIntersection != null && newIntersection.contains(potentialValue)) {
						logger.debug(String.format("      try potential value: %d%n", potentialValue));
						doSetRowColCell(board, row.getRowNumber(), cell.getCol().getColNumber(), potentialValue);

						if ((filled = fillInRow(board, row)) == true) {
							logger.debug(String.format("        fillInRow returned true (returning true)%n"));
							filled = true;
						} else {
							logger.debug(String.format("        fillInRow returned false (resetting cell to empty (0))%n"));
							doUnSetRowColCell(board, row.getRowNumber(), cell.getCol().getColNumber(), potentialValue);
							filled = false;
						}

					} else {
						logger.debug(String.format("      the potential value: %d has already been used (further down the stack)%n",
								+potentialValue));
						filled = false;
					}
				}
			}
		}
		SolvedRow.depth--;
		return filled;
	}

	public static boolean fillBoard(final Board board) throws TableException {
		final boolean complete = fillCells(board, 0);
		PrintedBoard.printBoard(board);
		return complete;
	}

	public static boolean fillCells(final Board board, final int cellNo) throws TableException {

		if (cellNo >= board.getMaxCells()) {
			// Terminate when have tried all cells
			return true;
		}
		SolvedRow.depth++;
		final Cell cell = board.getCells().get(cellNo);
		final Row row = board.getRowFromCell(cell);
		if (cell.getValue() != 0) {
			return fillCells(board, cellNo + 1);
		}
		logger.debug(String.format("-> [%d] fillBoard - cellNo:%d%n", SolvedRow.depth, cellNo));
		PrintedBoard.printBoard(board);
		if (board.getSudokMeTable() != null) {
			// If you want to see the board being solved, uncomment this code.

//			logger.debug("Sleep 500ms\n");
//			try {
//				Thread.sleep(50);
//			} catch (final InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
		final List<Integer> rcsIntersection = getIntersection(row, board.getCells().get(cellNo));
		if (rcsIntersection.size() == 1) {
			logger.debug(String.format("     [%d] (Cell:%d) Intersection.size == 1 - fill cell with %d.%n", SolvedRow.depth, cellNo,
					rcsIntersection.get(0)));
			doSetRowColCell(board, row.getRowNumber(), cell.getCol().getColNumber(), rcsIntersection.get(0));
			if (fillCells(board, cellNo + 1) == true) {
				logger.debug(String.format("     [%d] Returning from recursive descenT (true) after trying only choice %d.%n",
						SolvedRow.depth, rcsIntersection.get(0)));
				SolvedRow.depth--;
				return true;
			} else {
				logger.debug(String.format("     [%d] Returning from recursive descent (false).  Unset cell value %d%n", SolvedRow.depth,
						rcsIntersection.get(0)));
				doUnSetRowColCell(board, row.getRowNumber(), cell.getCol().getColNumber(), rcsIntersection.get(0));
				SolvedRow.depth--;
				return false;
			}
		} else if (rcsIntersection.size() == 0) {
			logger.debug(PrintedBoard.returnPrintedBoard(board));
			logger.debug(String.format(
					"     [%d] (Cell:%d) Intersection.size == 0 (nothing to fill).  Return false and unwind recursion.%n",
					SolvedRow.depth, cellNo));
			SolvedRow.depth--;
			return false; // couldn't fill cell
		} else {
			for (final int potential : rcsIntersection) {
				logger.debug(String.format(
						"     [%d] (Cell:%d) Intersection.size > 0 - multiple potential values for cell (%s).  Try %d.%n", SolvedRow.depth,
						cellNo,
						rcsIntersection.toString(), potential));
				doSetRowColCell(board, row.getRowNumber(), cell.getCol().getColNumber(), potential);
				if (fillCells(board, cellNo + 1) == true) {
					logger.debug(String.format("     [%d] Returning from recursive descent (true) after trying potential %d.%n",
							SolvedRow.depth, potential));
					SolvedRow.depth--;
					return true;
				} else {
					logger.debug(String
							.format("     [%d] Returning from recursive descent (false) after trying potential %d.  Reset value, return false and unwind recursion.%n",
									SolvedRow.depth, potential));
					doUnSetRowColCell(board, row.getRowNumber(), cell.getCol().getColNumber(), potential);
					// try any other potentials
				}
			}
		}
		// if it drops out to here, none of the potentials worked in the cell, so return false to unwind and try the next previous (cell's)
		// potential
		logger.debug(String.format("     [%d] All potentials must have failed.  Return false and unwind recursion.%n",
				SolvedRow.depth));
		SolvedRow.depth--;
		return false;
	}

	public static List<Integer> getIntersection(final Row row, final Cell cell) {
		logger.debug(String.format("    Intersection:%n"));
		logger.debug(String.format("      cell:%s%n", cell.getValue()));
		logger.debug(String.format("      row:%s%n", row.printRow()));
		logger.debug(String.format("      row available:%s%n", row.getAvailableValuesSet()));
		logger.debug(String.format("      col available:%s%n", cell.getCol().getAvailableValuesSet()));
		logger.debug(String.format("      square available:%s%n", cell.getSquare().getAvailableValuesSet()));

		final List<Integer> inter = Utils.setAsList(Utils.setIntersection(row.getAvailableValuesSet(), cell.getCol()
				.getAvailableValuesSet(), cell.getSquare().getAvailableValuesSet()));

		logger.debug(String.format("     Intersection:%s%n", inter.toString()));
		return inter;
	}

	private static void doSetRowColCell(final Board board, final int row, final int col, final int value) throws TableException {
		try {
			board.setRowColCell(row, col, value);
		} catch (final SudokmeStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final SudokmeException e) {
			e.printStackTrace();
		}
	}

	public static void doUnSetRowColCell(final Board board, final int row, final int col, final int value) throws TableException {
		try {
			board.unSetRowColCell(row, col, value);
		} catch (final SudokmeStateException e) {
			e.printStackTrace();
		}
	}
}