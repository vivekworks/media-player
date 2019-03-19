package application;

import java.io.File;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;

public class Main extends Application {
	private Player player;
	private FileChooser fileChooser;
	private Stage pStage;
	private Scene scene;
	private MenuBar menuBar;
	public static Stage staticStage;
	public static Scene staticScene;
	public static MenuBar staticMenuBar;

	public void start(Stage primaryStage) {
		pStage = primaryStage;
		menuBar = new MenuBar();
		Menu fileMenu = new Menu("File");
		MenuItem openItem = new MenuItem("Open");
		fileMenu.getItems().add(openItem);
		menuBar.getMenus().add(fileMenu);
		fileChooser = new FileChooser();
		EventHandler<ActionEvent> openAction = this::chooseFile;
		openItem.setOnAction(openAction);
		player = new Player();
		player.setTop(menuBar);
		scene = new Scene(player, 760, 540, Color.BLACK);
		primaryStage.setScene(scene);
		setStaticValues();
		primaryStage.show();
	}

	public void chooseFile(ActionEvent event) {
		File file = fileChooser.showOpenDialog(pStage);
		playFile(file);
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void playFile(File file) {
		if (file != null) {
			try {
				String playingFile = file.getCanonicalPath().toString();
				String playingFile1 = file.toURI().toURL().toExternalForm();
				player = new Player(playingFile1);
				player.setCurrentPath(playingFile.substring(0, playingFile.lastIndexOf('\\')));
				player.setPlayingFile(playingFile.substring(playingFile.lastIndexOf('\\')+1));
				player.setTop(menuBar);
				player.mediaBar.getFilesList();
				player.setPlaylists();
				scene = new Scene(player, 760, 540, Color.BLACK);
				pStage.setScene(scene);
				setStaticValues();
				pStage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void setStaticValues() {
		staticStage = pStage;
		staticMenuBar = menuBar;
		staticScene = scene;
	}
}