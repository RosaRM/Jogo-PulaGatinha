package interno.jogo.Jumpman;

import com.badlogic.gdx.Game;
import interno.jogo.Jumpman.screens.MainMenu;

public class Main extends Game {

	public static final String TITULO = "PULA GATINHA", VERSION = "0.75";
	
	@Override
	public void create () {
		setScreen(new MainMenu());
		//setScreen(new Pontuacao());
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