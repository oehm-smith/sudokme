/* 
 * Copyright (c) Sombraro Software (2012).
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.tintuna.sudoku.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.tintuna.sudoku.board.FromSquaresForumlaTest;
import com.tintuna.sudoku.board.InitialBoardTest;
import com.tintuna.sudoku.board.LoadBoardTest;
import com.tintuna.sudoku.board.ValidatedBoardTest;


@RunWith(Suite.class)
@SuiteClasses({ FromSquaresForumlaTest.class, InitialBoardTest.class, LoadBoardTest.class, ValidatedBoardTest.class })
public class AllTests {
}
