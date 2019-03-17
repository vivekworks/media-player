package application;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class Player extends BorderPane {
	public Player(String file) {
		Media media = new Media(file);
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		MediaView mediaView = new MediaView(mediaPlayer);
		Pane mediaPane = new Pane();
		mediaPane.getChildren().add(mediaView);
		setCenter(mediaPane);
		mediaPlayer.play();
	}
}
