package interno.jogo.Jumpman.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.utils.Array;

import interno.jogo.Jumpman.PlatHorizontal;
import interno.jogo.Jumpman.Plataforma;
import interno.jogo.Jumpman.Player;

public class Play implements Screen {

	private BitmapFont white;  // Fonte para o texto da pontuação
	public static int score = 0;     // Pontuação do jogador
    private OrthographicCamera camera; // Câmera para visualizar o jogo
    private Player player; // Instância do jogador
    private Array<Plataforma> plataformas; // Array para armazenar as plataformas
    private Texture playerTexture; // Textura do jogador
    private Texture plataformaTexture; // Textura das plataformas
    private Texture plathorizonTexture; // Textura das plataformas
    private SpriteBatch batch; // Usado para desenhar sprites
    RandomXS128 random = new RandomXS128(); // Gerador de números aleatórios
    private Sprite bg;  // Imagem de fundo

    public int PosX = 300, PosY = 200; // Posições iniciais do jogador

    public Play() {
        // Carregando as texturas do jogador e da plataforma
        playerTexture = new Texture("img/gatinhaDoPula.png");
        plataformaTexture = new Texture("img/plataforma.png");
        plathorizonTexture = new Texture("img/plataformaH.png");

        // Inicializando o jogador com a textura e posição definidas
        player = new Player(playerTexture, PosX, PosY);  
        
        // Inicializando o array de plataformas e adicionando uma plataforma inicial
        plataformas = new Array<>();
        plataformas.add(new Plataforma(plataformaTexture, PosX, PosY - 50));
    }

    private void createPlatforms() {
        float baseY = 60; // Altura mínima para a primeira plataforma
        float lastY = baseY; // Começando a partir de baseY

        while (lastY <= Gdx.graphics.getHeight() * 1.8) {
            float x = random.nextFloat() * (Gdx.graphics.getWidth() - plataformaTexture.getWidth());

            // Definindo o deslocamento máximo e mínimo com base na pontuação
            float maxOffset = Math.max(120 - score * 0.5f, 50); // Reduz com a pontuação até um mínimo
            float minOffset = Math.max(30 - score * 0.1f, 10);   // Reduz até um valor mínimo

            // Calculando o novo Y com um deslocamento aleatório
            float offsetY = random.nextFloat() * (maxOffset - minOffset) + minOffset;
            lastY += offsetY;        
            
            if (random.nextFloat() < 0.05f) { // 10% de chance
                Plataforma plataforma = new PlatHorizontal(plathorizonTexture, x, lastY);
                plataformas.add(plataforma);
            } else {
                Plataforma plataforma = new Plataforma(plataformaTexture, x, lastY);
                plataformas.add(plataforma);
                }
        }
    }

	@Override
	public void show() {
		score = 0;
	    white = new BitmapFont(Gdx.files.internal("font/Preta.fnt"), false);  // Fonte branca
        Texture bgTexture = new Texture(Gdx.files.internal("img/background.png"));  // Carrega a imagem de fundo
        bg = new Sprite(bgTexture);  // Cria o sprite para a imagem de fundo
        bg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());  // Ajusta o tamanho do fundo para preencher a tela

	    // Configurando o processador de entrada
	    Gdx.input.setInputProcessor(new InputAdapter() {
	        @Override
	        public boolean keyDown(int keycode) {
	            return player.keyDown(keycode);  // Delegando o evento de tecla para o jogador
	        }
	
	        @Override
	        public boolean keyUp(int keycode) {
	            return player.keyUp(keycode);  // Delegando o evento de tecla solta para o jogador
	        }
	    });
	    
	    batch = new SpriteBatch(); // Inicializando o SpriteBatch
	
	    // Configurando a câmera
	    camera = new OrthographicCamera();
	    camera.setToOrtho(false, 800, 480);  // Definindo as dimensões da tela
	
	    // Criando o jogador e as plataformas
	    plataformas = new Array<>();
	    createPlatforms(); // Chamando o método para criar plataformas
        plataformas.add(new Plataforma(plataformaTexture, PosX, PosY - 50)); // Adicionando uma plataforma inicial
	    player = new Player(playerTexture, PosX, PosY); // Criando o jogador
	}@Override
	public void render(float delta) {
	    Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
	    camera.update();

	    if (player.isRising()) {
	        score++;
	    }

	    player.update(delta, plataformas);

	    // Variável para controlar a altura mínima para criação da próxima plataforma
	    float lastPlatformY = plataformas.get(plataformas.size - 1).getPosition().y;

	    for (int i = plataformas.size - 1; i >= 0; i--) {
	        Plataforma plataforma = plataformas.get(i);
	        plataforma.update(delta);

	        if (plataforma.getPosition().y + plataforma.getSprite().getHeight() < 0) {
	            plataformas.removeIndex(i);
	            System.out.println("Plataforma destruída. Tamanho atual do array: " + plataformas.size);
	        }
	    }

	    // Verificar se deve adicionar uma nova plataforma com base no espaçamento
	    if (lastPlatformY < Gdx.graphics.getHeight() * 1.3) { 
	        float maxOffset = 120 + score * 0.01f;  
	        float minOffset = 60 + score * 0.05f;
	        float offsetY = random.nextFloat() * (maxOffset - minOffset) + minOffset;
	        System.out.println("OFFSET" + offsetY);

	        float x = MathUtils.random(10, Gdx.graphics.getWidth() - plataformaTexture.getWidth());
	        float newY = lastPlatformY + offsetY; // Calcula a nova altura com o offset

	        if (random.nextFloat() < 0.075f) { // 5% de chance
	            plataformas.add(new PlatHorizontal(plathorizonTexture, x, newY));
	        } else {
	            plataformas.add(new Plataforma(plataformaTexture, x, newY));
	        }
	        System.out.println("Nova plataforma criada. Tamanho atual do array: " + plataformas.size);
	    }

	    batch.setProjectionMatrix(camera.combined);
	    batch.begin();
        bg.draw(batch);  // Desenha o fundo

	    for (Plataforma platform : plataformas) {
	        platform.getSprite().draw(batch);
	    }
	    player.getSprite().draw(batch);
	    white.draw(batch, "Score: " + score, 20, Gdx.graphics.getHeight() - 30);
	    batch.end();
	}

	@Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width , height ); // Ajustando a câmera ao redimensionar a janela
    }

    @Override
    public void pause() {} // Método de pausa (não implementado)

    @Override
    public void resume() {} // Método de retomar (não implementado)

    @Override
    public void hide() {} // Método para ocultar a tela (não implementado)

    @Override
    public void dispose() {
        batch.dispose();
        playerTexture.dispose();
        plataformaTexture.dispose();
        white.dispose();
    }
}
