package application;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class Player extends BorderPane {
	private String currentPath;
	private String playingFile;
	public MediaBar mediaBar;
	Pane mediaPane;
	public String getCurrentPath() {
		return this.currentPath;
	}

	public void setCurrentPath(String currentPath) {
		this.currentPath = currentPath;
	}
	
	public String getPlayingFile() {
		return this.playingFile;
	}

	public void setPlayingFile(String playingFile) {
		this.playingFile = playingFile;
	}

	public Player() {
	}

	public Player(String file) {
		Media media = new Media(file);
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		MediaView mediaView = new MediaView(mediaPlayer);
		mediaPane = new Pane();
		mediaPane.getChildren().add(mediaView);
		setCenter(mediaPane);
		mediaBar = new MediaBar(mediaPlayer,this);
		setBottom(mediaBar);
		setStyle("-fx-background-color : #bfc2c7");
		mediaPlayer.play();
	}
	
	public void setPlaylists() {
		Playlist playlists = new Playlist(mediaBar.files,this);
		setRight(playlists);
	}
}