package interno.jogo.Jumpman.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import interno.jogo.Jumpman.JumpMain;


public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = JumpMain.TITULO + " v " + JumpMain.VERSION ;
        config.vSyncEnabled = true;
        config.useGL30 = true;
        config.width = 720;
        config.height = 1280;
		new LwjglApplication(new JumpMain(), config);
		
		//System.out.println(Gdx.files.internal("font/Branca.fnt").exists());
	}
}
