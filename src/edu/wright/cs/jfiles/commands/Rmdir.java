/*
 * Copyright (C) 2016 - WSU CEG3120 Students
 *
 * Roberto C. Sanchez <roberto.sanchez@wright.edu>
 *
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package edu.wright.cs.jfiles.commands;

/**
 * The rmdir command removes a directory.
 * Syntax:
 *      RMDIR directory
 *  Flags:
 *      - None.
 *  Example:
 *      RMDIR testfolder
 */

public class Rmdir extends Command {
	/**
	 * Calls super.
	 * @param args Command's args.
	 */
	public Rmdir(String... args) {
		super(args);
	}

	/**
	 *  TODO: Program for RMDIR.
	 *  @return Not much yet
	 */
	@Override
	public String execute() {
		return "";
	}

}
