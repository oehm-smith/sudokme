/* 
 * Copyright (c) Sombraro Software (2012).
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.tintuna.sudoku.game;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.tintuna.sudoku.board.InitialBoard;
import com.tintuna.sudoku.data.Board;
import com.tintuna.sudoku.data.BoardItemType;
import com.tintuna.sudoku.exception.SudokmeException;
import com.tintuna.sudoku.exception.SudokmeStateException;
import com.tintuna.sudoku.exception.TableException;
import com.tintuna.sudoku.game.SolvedRow;
import com.tintuna.sudoku.test.BoardHelper;
import com.tintuna.sudoku.ui.table.SudokMeTable;

public class PlayGamesTest {
	SudokMeTable sudokMeTable = null;
	Board board = null;
	BoardHelper boardHelper = null;

	@Before
	public void before() {
		System.out.println("GamesTest - have 'USE_SWT'?:"+System.getenv().get("USE_SWT"));
		if (System.getenv().containsKey("USE_SWT")
				&& (System.getenv().get("USE_SWT").equals("TRUE") || System
						.getenv().get("USE_SWT").equals("true"))) {
			this.sudokMeTable = new SudokMeTable(3, 3);
		} else {
			this.sudokMeTable = null;
		}
		this.board = new Board(3, 3, this.sudokMeTable);
		this.boardHelper = new BoardHelper(this.board);
		InitialBoard.setupBoard(this.board);
	}

	@After
	public void after() {
		if (this.sudokMeTable != null) {
			this.sudokMeTable.end();
		}
	}

	// @Test
	public void test() throws SudokmeStateException, SudokmeException,
			TableException {
		final Integer[][] rows = { { 3 }, { 4, 5, 6, 7, 8, 9, 1, 2, 3 },
				{ 7, 8, 9, 1, 2, 3, 4, 5, 6 } };
		this.boardHelper.setupBoard(BoardItemType.ROW, rows);
		// final List<Row> solvedRow = SolvedRow.solveRowEntry(this.board, 0);
		// assertEquals(Board.getFullSet(), solvedRow);
		SolvedRow.fillBoard(this.board);
	}

	@Test
	public void testCompletelyEmptyBoard() throws SudokmeStateException,
			SudokmeException, TableException {
		final Integer[][] rows = {};
		this.boardHelper.setupBoard(BoardItemType.ROW, rows);
		// final List<Row> solvedRow = SolvedRow.solveRowEntry(this.board, 0);
		// assertEquals(Board.getFullSet(), solvedRow);
		// Insert delay so can set up screenshot movie
//		try {
//			Thread.sleep(30000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		SolvedRow.fillBoard(this.board);
		// TODO - actually test getting correct result
		Assert.assertTrue(true);
	}

	@Test
	public void testOneSquareCorrectlyFilledBoard00()
			throws SudokmeStateException, SudokmeException, TableException {
		// That square shouldn't be changed as it tries alternatives
		final Integer[][] rows = { {}, { 3 }, {} };
		this.boardHelper.setupBoard(BoardItemType.ROW, rows);
		// final List<Row> solvedRow = SolvedRow.solveRowEntry(this.board, 0);
		// assertEquals(Board.getFullSet(), solvedRow);
		SolvedRow.fillBoard(this.board);
		// TODO - actually test getting correct result
		Assert.assertTrue(true);
	}

	@Test
	public void testOneSquareInCorrectlyFilledBoard00()
			throws SudokmeStateException, SudokmeException, TableException {
		// That square shouldn't be changed as it tries alternatives. The game
		// should fail with 'no solution'
		final Integer[][] rows = { {}, { 9 }, {} };
		this.boardHelper.setupBoard(BoardItemType.ROW, rows);
		// final List<Row> solvedRow = SolvedRow.solveRowEntry(this.board, 0);
		// assertEquals(Board.getFullSet(), solvedRow);
		SolvedRow.fillBoard(this.board);
		// TODO - actually test getting correct result
		Assert.assertTrue(true);
	}
}
