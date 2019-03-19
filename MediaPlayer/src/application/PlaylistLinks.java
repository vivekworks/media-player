package application;

import java.io.File;

import javafx.scene.control.Hyperlink;

public class PlaylistLinks extends Hyperlink {
	String playListName;
	Player player;
	PlaylistLinks(String playListName,int sNo,Player player){
		super(sNo+". "+playListName);
		this.playListName=playListName;
		this.player = player;
	}
	
	public void fire() {
		File chosenFile = new File(player.getCurrentPath()+"\\"
				+ playListName);
		player.mediaBar.playFile(chosenFile);
	}
}
