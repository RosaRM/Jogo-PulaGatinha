package interno.jogo.Jumpman;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Screen;

import interno.jogo.Jumpman.screens.MainMenu;
import interno.jogo.Jumpman.screens.Splash;

public class JumpMain extends Game {

	public static final String TITULO = "PULA GATINHA", VERSION = "0.0.0.1.0";
	
	@Override
	public void create () {
		setScreen(new MainMenu());
	}

	@Override
	public void dispose () {
		super.dispose(); 
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void resize (int width, int height) {
		super.resize(width, height);
	}
	
	@Override
	public void pause () {
		super.pause();
	}
	
	@Override
	public void resume () {
		super.resume();
	}
}
