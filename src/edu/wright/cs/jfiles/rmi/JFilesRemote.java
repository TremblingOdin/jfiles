/*
 * Copyright (C) 2016 - WSU CEG3120 Students
 * 
 * Roberto C. Sánchez <roberto.sanchez@wright.edu>
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

package edu.wright.cs.jfiles.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * A remote interface to be implemented by the JFiles client and server.
 * @author Samuel Willoughby
 *
 */
public interface JFilesRemote extends Remote {
	/**
	 * Abstract method that should return "HELLO WORLD!!!" when implemented
	 * in server and client.
	 * @return the string "HELLO WORLD!!!"
	 * @throws RemoteException required by all remote methods
	 */
	String sayHello() throws RemoteException;
}
