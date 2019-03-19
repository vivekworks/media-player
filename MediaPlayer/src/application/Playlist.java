package application;

import java.io.File;
import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Playlist extends VBox{
	private File[] playlist;

	public Playlist(File[] lists, Player player) {
		this.playlist = lists;
		setAlignment(Pos.TOP_LEFT);
		setStyle("-fx-background-color : white");
		setMinWidth(120);
		setMinHeight(120);
		setMaxHeight(500);
		setPadding(new Insets(10, 10, 10, 10));
		ArrayList<Node> playlists = new ArrayList<>();
		playlists.add(new Label("  PLAYLISTS  "));
		playlists.add(new Label("----------------"));
		if (playlist != null) {
			for (int i=0,plen=playlist.length;i<plen;i++) {
				playlists.add(new PlaylistLinks(playlist[i].getName(),(i+1),player));
			}
			getChildren().addAll(playlists);
		}
	}
}
