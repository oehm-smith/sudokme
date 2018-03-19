/* 
 * Copyright (c) Sombraro Software (2012).
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.tintuna.sudoku.log4j;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.ErrorCode;

/**
 * This is a customized log4j appender, which will create a new file for every run of the application.
 * 
 * @author veera | http://veerasundar.com
 * 
 *         Found at:
 *         http://webcache.googleusercontent.com/search?q=cache:uTk_-XULZwcJ:veerasundar.com/blog/2009/08/how-to-create-a-new-log-file
 *         -for-each-time-the-application-runs/+&cd=6&hl=en&ct=clnk&gl=au&client=firefox-a
 * 
 */
public class NewLogForEachRunFileAppender extends FileAppender {

	public NewLogForEachRunFileAppender() {
	}

	public NewLogForEachRunFileAppender(final Layout layout, final String filename,
			final boolean append, final boolean bufferedIO, final int bufferSize)
			throws IOException {
		super(layout, filename, append, bufferedIO, bufferSize);
	}

	public NewLogForEachRunFileAppender(final Layout layout, final String filename,
			final boolean append) throws IOException {
		super(layout, filename, append);
	}

	public NewLogForEachRunFileAppender(final Layout layout, final String filename)
			throws IOException {
		super(layout, filename);
	}

	@Override
	public void activateOptions() {
		if (this.fileName != null) {
			try {
				this.fileName = getNewLogFileName();
				setFile(this.fileName, this.fileAppend, this.bufferedIO, this.bufferSize);
			} catch (final Exception e) {
				this.errorHandler.error("Error while activating log options", e,
						ErrorCode.FILE_OPEN_FAILURE);
			}
		}
	}

	private String getNewLogFileName() {
		if (this.fileName != null) {
			final String DOT = ".";
			final String HIPHEN = "-";
			final File logFile = new File(this.fileName);
			final String fileName = logFile.getName();
			String newFileName = "";

			final int dotIndex = fileName.indexOf(DOT);
			if (dotIndex != -1) {
				// the file name has an extension. so, insert the time stamp
				// between the file name and the extension
				newFileName = fileName.substring(0, dotIndex) + HIPHEN
						+ +System.currentTimeMillis() + DOT
						+ fileName.substring(dotIndex + 1);
			} else {
				// the file name has no extension. So, just append the timestamp
				// at the end.
				newFileName = fileName + HIPHEN + System.currentTimeMillis();
			}
			return logFile.getParent() + File.separator + newFileName;
		}
		return null;
	}
}
