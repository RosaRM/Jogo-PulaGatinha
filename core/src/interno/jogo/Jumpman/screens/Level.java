package interno.jogo.Jumpman.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class Level implements Screen {
	
	private World world;
	private Box2DDebugRenderer debugRender;
	private OrthographicCamera camera;

	@Override
	public void show() {

		world = new World(new Vector2(0 ,-9.81f), true);
		debugRender = new Box2DDebugRenderer();
		
		camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(100, 0, 200, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		
		debugRender.render(world, camera.combined);
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {
		dispose();

	}

	@Override
	public void dispose() {
		world.dispose();
		debugRender.dispose();
	}

}
