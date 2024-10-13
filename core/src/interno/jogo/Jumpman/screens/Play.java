package interno.jogo.Jumpman.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.RandomXS128;
import interno.jogo.Jumpman.Plataforma;
import interno.jogo.Jumpman.Player;

public class Play implements Screen {
    private OrthographicCamera camera;
    private Player player;
    private Array<Plataforma> plataformas;
    private Texture playerTexture;
    private Texture plataformaTexture;
	private SpriteBatch batch;
    RandomXS128 random = new RandomXS128();
    
    public int PosX = 300, PosY = 250;

    public Play() {


	    playerTexture = new Texture("img/gatinhaDoPula.png");
	    plataformaTexture = new Texture("img/plataforma.png");

        
	    player = new Player(playerTexture, PosX, PosY);  
	    
        plataformas = new Array<>();
        plataformas.add(new Plataforma(plataformaTexture, PosX, PosY - 50));
    }

    private void createPlatforms() {
        float baseY = 50; // A altura mínima para a primeira plataforma
        float maxOffset = 200; // Deslocamento máximo entre as plataformas
        float minOffset = 50; // Deslocamento mínimo entre as plataformas
        float lastY = baseY; // Começa a partir de baseY

        // Gera plataformas em posições aleatórias
        for (int i = 0; i < 10; i++) { 
            float x = random.nextFloat() * Gdx.graphics.getWidth(); // Ajuste para a largura total da tela
            
            // Calcula o novo Y com um deslocamento aleatório
            float offsetY = random.nextFloat() * (maxOffset - minOffset) + minOffset;
            lastY += offsetY; // Aumenta a altura com o deslocamento

            // Verifica se a plataforma está dentro do limite superior da tela
            if (lastY <= Gdx.graphics.getHeight()) { // Verifica se está dentro da altura total da tela
                Plataforma plataforma = new Plataforma(plataformaTexture, x, lastY); // Não dividir por 20
                plataformas.add(plataforma);
            }
        }
    }

    
    @Override
    public void render(float delta) {
    	
        
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        camera.update();

        // Atualizar jogador e plataformas
        player.update(delta, plataformas);  // Passar as plataformas para o jogador verificar as colisões
        for (Plataforma platform : plataformas) {
            platform.update(delta);
        }

        // Desenhar jogador e plataformas
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        player.getSprite().draw(batch);
        for (Plataforma platform : plataformas) {
            platform.getSprite().draw(batch);
        }
        batch.end();
    }


    // Implementação de outros métodos exigidos pela interface Screen (show, resize, etc.)


	// No método show(), ajuste a câmera
	@Override
	public void show() {
	    Gdx.input.setInputProcessor(new InputAdapter() {
	        @Override
	        public boolean keyDown(int keycode) {
	            return player.keyDown(keycode);  // Delegar para o jogador
	        }

	        @Override
	        public boolean keyUp(int keycode) {
	            return player.keyUp(keycode);  // Delegar para o jogador
	        }
	    });
	    batch = new SpriteBatch();
	
	    // Configura a câmera
	    camera = new OrthographicCamera();
	    camera.setToOrtho(false, 800, 480);  // Defina as dimensões da tela (largura e altura)
	
	    // Carrega as texturas
	    playerTexture = new Texture("img/gatinhaDoPula.png");
	    plataformaTexture = new Texture("img/plataforma.png");
	
	    // Cria o jogador e as plataformas
	    player = new Player(playerTexture, PosX, PosY);
	    plataformas = new Array<>();
	    createPlatforms();
        plataformas.add(new Plataforma(plataformaTexture, PosX, PosY - 50));
	}
	
		

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width , height );
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
    }
}
