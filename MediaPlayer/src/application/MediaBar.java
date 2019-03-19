package application;

import java.io.File;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import application.Main;

public class MediaBar extends HBox {

	Button playButton = new Button("||");
	Button previousButton = new Button("|<<");
	Button nextButton = new Button(">>|");
	Slider timeSlider = new Slider();
	Slider volumeSlider = new Slider();
	MediaPlayer mediaPlayer;
	Player player;
	Label volumeLabel = new Label("Volume ");
	Label playLabel = new Label("  ");
	File[] files;

	public MediaBar(MediaPlayer mediaPlayer, Player player) {
		this.mediaPlayer = mediaPlayer;
		this.player = player;
		volumeSlider.setPrefWidth(70);
		volumeSlider.setMinWidth(50);
		volumeSlider.setValue(100);
		setAlignment(Pos.CENTER);
		setPadding(new Insets(5, 10, 5, 10));
		HBox.setHgrow(timeSlider, Priority.ALWAYS);
		playButton.setPrefWidth(30);
		EventHandler<ActionEvent> playAction = this::handlePlayAndPause;
		InvalidationListener timeNavigation = this::navigateAcrossTime;
		InvalidationListener volumeAdjustment = this::adjustVolume;
		InvalidationListener timeSliderMovement = this::timerThread;
		EventHandler<ActionEvent> nextButtonAction = this::playNextMedia;
		EventHandler<ActionEvent> previousButtonAction = this::playPreviousMedia;
		playButton.setOnAction(playAction);
		mediaPlayer.currentTimeProperty().addListener(timeSliderMovement);
		timeSlider.valueProperty().addListener(timeNavigation);
		volumeSlider.valueProperty().addListener(volumeAdjustment);
		nextButton.setOnAction(nextButtonAction);
		previousButton.setOnAction(previousButtonAction);
		getChildren().add(previousButton);
		getChildren().add(playButton);
		getChildren().add(nextButton);
		getChildren().add(playLabel);
		getChildren().add(timeSlider);
		getChildren().add(volumeLabel);
		getChildren().add(volumeSlider);
	}

	public void handlePlayAndPause(ActionEvent event) {
		Status status = mediaPlayer.getStatus();
		if (status == Status.PLAYING) {
			if (mediaPlayer.getCurrentTime().greaterThanOrEqualTo(mediaPlayer.getTotalDuration())) {
				mediaPlayer.seek(mediaPlayer.getStartTime());
				mediaPlayer.play();
				playButton.setText("||");
			} else {
				mediaPlayer.pause();
				playButton.setText(">");
			}
		}
		if (status == Status.HALTED || status == Status.PAUSED || status == Status.STOPPED) {
			mediaPlayer.play();
			playButton.setText("||");
		}
	}

	public void navigateAcrossTime(Observable observable) {
		if (timeSlider.isPressed()) {
			mediaPlayer.seek(mediaPlayer.getMedia().getDuration().multiply(timeSlider.getValue() / 100));
		}
	}

	public void adjustVolume(Observable observable) {
		if (volumeSlider.isPressed()) {
			mediaPlayer.setVolume(volumeSlider.getValue() / 100);
		}
	}

	public void timerThread(Observable observable) {
		Platform.runLater(this::moveTimeSlide);
	}

	public void moveTimeSlide() {
		timeSlider.setValue(mediaPlayer.getCurrentTime().toMillis() / mediaPlayer.getTotalDuration().toMillis() * 100);
	}

	public void playPreviousMedia(ActionEvent event) {
		if (files == null || (files != null && files.length < 0)) {
			getFilesList();
		}
		if (files != null) {
			String playingFile = player.getPlayingFile();
			for (int i = 0, flen = files.length; i < flen; i++) {
				if (files[i].getName().equalsIgnoreCase(playingFile)) {
					File previousFile = new File(player.getCurrentPath()+"\\"
							+ files[i==0 ? files.length-1 : i-1].getName());
					playFile(previousFile);
				}
			}
		}
	}

	public void playNextMedia(ActionEvent event) {
		if (files == null || (files != null && files.length < 0)) {
			getFilesList();
		}
		if (files != null) {
			String playingFile = player.getPlayingFile();
			for (int i = 0, flen = files.length; i < flen; i++) {
				if (files[i].getName().equalsIgnoreCase(playingFile)) {
					File nextFile = new File(player.getCurrentPath()+"\\"
							+ files[(files.length + ((i + 1) % files.length)) % files.length].getName());
					playFile(nextFile);
				}
			}
		}
	}

	public void getFilesList() {
		if (files == null || (files != null && files.length < 0)) {
			String currentPath = player.getCurrentPath();
			if (currentPath != null) {
				files = new File(currentPath).listFiles();
			}
		}
	}

	public void playFile(File file) {
		if (file != null) {
			try {
				String playingFile = file.getCanonicalPath().toString();
				String playingFile1 = file.toURI().toURL().toExternalForm();
				if(mediaPlayer != null) {
					mediaPlayer.stop();
				}
				player = new Player(playingFile1);
				player.setCurrentPath(playingFile.substring(0, playingFile.lastIndexOf('\\')));
				player.setPlayingFile(playingFile.substring(playingFile.lastIndexOf('\\')+1));
				player.setTop(Main.staticMenuBar);
				player.mediaBar.getFilesList();
				player.setPlaylists();
				Main.staticScene = new Scene(player, 760, 540, Color.BLACK);
				Main.staticStage.setScene(Main.staticScene);
				Main.staticStage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}