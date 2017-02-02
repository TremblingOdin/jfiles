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

package edu.wright.cs.jfiles.gui.client;

import edu.wright.cs.jfiles.client.JFilesClient;
import edu.wright.cs.jfiles.core.FileStruct;
import edu.wright.cs.jfiles.core.SocketClient;
import edu.wright.cs.jfiles.core.XmlHandler;
import edu.wright.cs.jfiles.gui.common.FileIconViewController;
import edu.wright.cs.jfiles.gui.common.FileIconViewController.Size;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

/**
 * Controller for main application view.
 *
 * @author Matt
 *
 */
public class ClientAppViewController implements Initializable {

	private SocketClient client;

	private FileStruct selectedFile;
	private Map<FileStruct, Parent> contents;

	private ContextMenu fileContextMenu;
	// private ContextMenu folderContextMenu;
	private ContextMenu viewContextMenu;

	@FXML
	BorderPane root;

	@FXML
	TreeView<String> treeView;

	@FXML
	FlowPane flowPane;

	/**
	 * Loads the given directory into the view.
	 *
	 * @param path
	 *            the path to load
	 */
	private void loadDirectory(String path) {
		client.send("ls " + path);
		XmlHandler handler = new XmlHandler(path);
		for (FileStruct file : handler.readXmlString(client.read())) {
			FXMLLoader loader =
					new FXMLLoader(FileIconViewController.class.getResource("FileIconView.fxml"));
			try {
				final Parent view = loader.load();
				FileIconViewController controller = loader.getController();

				controller.setFileStruct(file);
				controller.setSize(Size.MEDIUM);
				controller.registerAppController(this);

				flowPane.getChildren().add(view);
				contents.put(file, view);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Sets the current selected file.
	 *
	 * @param fs
	 *            fileStruct to set
	 */
	public void setSelectedFile(FileStruct fs) {
		selectedFile = fs;
		System.out.println(selectedFile.getValue("path"));
		for (Entry<FileStruct, Parent> entry : contents.entrySet()) {
			if (entry.getKey().equals(fs)) {
				entry.getValue().getStyleClass().add("selected");
			} else {
				entry.getValue().getStyleClass().remove("selected");
			}
		}
	}

	/**
	 * Builds context menu for files.
	 *
	 * @return context menu
	 */
	private ContextMenu buildFileContextMenu() {
		ContextMenu menu = new ContextMenu();

		MenuItem cut = new MenuItem("Cut");
		MenuItem copy = new MenuItem("Copy");
		MenuItem paste = new MenuItem("Paste");
		MenuItem delete = new MenuItem("Delete");
		menu.getItems().addAll(cut, copy, paste, delete);

		cut.setOnAction(event -> cut());
		copy.setOnAction(event -> copy());
		paste.setOnAction(event -> paste());
		delete.setOnAction(event -> delete());

		return menu;
	}

	/**
	 * Builds context menu for main view.
	 *
	 * @return context menu
	 */
	private ContextMenu buildViewContextMenu() {
		ContextMenu menu = new ContextMenu();
		Menu view = new Menu("View");
		Menu sort = new Menu("Sort");

		MenuItem largeIcons = new MenuItem("Large Icons");
		MenuItem mediumIcons = new MenuItem("Medium Icons");
		MenuItem smallIcons = new MenuItem("Small Icons");
		MenuItem details = new MenuItem("Details");
		view.getItems().addAll(largeIcons, mediumIcons, smallIcons, details);

		MenuItem sortName = new MenuItem("Name");
		MenuItem sortDate = new MenuItem("Date");
		MenuItem sortType = new MenuItem("Type");
		sort.getItems().addAll(sortName, sortDate, sortType);

		MenuItem newFile = new MenuItem("New");

		menu.getItems().addAll(view, sort, newFile);

		largeIcons.setOnAction(event -> System.out.println("Large Icons"));
		mediumIcons.setOnAction(event -> System.out.println("Medium Icons"));
		smallIcons.setOnAction(event -> System.out.println("Small Icons"));
		details.setOnAction(event -> System.out.println("Details"));

		sortName.setOnAction(event -> System.out.println("Sort Name"));
		sortDate.setOnAction(event -> System.out.println("Sort Date"));
		sortType.setOnAction(event -> System.out.println("Sort Type"));

		newFile.setOnAction(event -> System.out.println("New File"));

		return menu;
	}

	/**
	 * Cut action.
	 */
	@FXML
	public void cut() {
		// if (clipboard.hasfiles()) {
		// client.send(new Command(Type.COPY, source, dest));
		// } else {
		// clipboard.add(file);
		// client.send(new Command(Type.REMOVE, target));
		// }
	}

	/**
	 * Copy action.
	 */
	@FXML
	public void copy() {
		// clipboard.add(selectefile);
	}

	/**
	 * Paste action.
	 */
	@FXML
	public void paste() {
		// client.send(new Command(Type.COPY, source, dest));
	}

	/**
	 * Delete action.
	 */
	@FXML
	public void delete() {
		// client.send(new Command(Type.REMOVE, target));
	}

	/**
	 * Mouse clicked in flowPane action.
	 */
	@FXML
	public void handleMouseClicked() {
		fileContextMenu.hide();
		// folderContextMenu.hide();
		viewContextMenu.hide();
	}

	/**
	 * Handles dragging files over the view.
	 *
	 * @param event
	 *            drag event
	 */
	@FXML
	public void onDragOver(DragEvent event) {
		event.acceptTransferModes(TransferMode.COPY);
	}

	/**
	 * Handles dropping files on the view.
	 *
	 * @param event
	 *            drag event
	 */
	@FXML
	public void onDragDrop(DragEvent event) {
		root.getScene().getWindow().requestFocus();

		Dragboard dragboard = event.getDragboard();
		if (dragboard.hasFiles()) {
			for (File file : dragboard.getFiles()) {
				try {
					FileStruct fileStruct = new FileStruct(file.toPath());
					System.out.println(fileStruct.getValue("path") + " added");
					FXMLLoader loader = new FXMLLoader(
							FileIconViewController.class.getResource("FileIconView.fxml"));
					final Parent view = loader.load();
					FileIconViewController controller = loader.getController();

					controller.setFileStruct(fileStruct);
					controller.setSize(Size.MEDIUM);
					controller.registerAppController(this);

					flowPane.getChildren().add(view);
					contents.put(fileStruct, view);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	/**
	 * Context menu requested action.
	 *
	 * @param event
	 *            the event
	 */
	@FXML
	public void contextMenuRequested(ContextMenuEvent event) {
		Node node = event.getPickResult().getIntersectedNode();
		if (node == flowPane) {
			viewContextMenu.show(node, event.getScreenX(), event.getScreenY());
		} else if (node instanceof BorderPane) {
			fileContextMenu.show(node, event.getScreenX(), event.getScreenY());
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		contents = new HashMap<>();
		client = new SocketClient();
		loadDirectory("./src/edu/wright/cs/jfiles");
		fileContextMenu = buildFileContextMenu();
		viewContextMenu = buildViewContextMenu();
	}
}