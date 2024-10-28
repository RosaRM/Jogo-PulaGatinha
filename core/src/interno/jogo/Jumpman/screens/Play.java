package interno.jogo.Jumpman.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.utils.Array;
import interno.jogo.Jumpman.Plataforma;
import interno.jogo.Jumpman.Player;

public class Play implements Screen {

	private BitmapFont white;  // Fonte para o texto da pontuação
	private int score = 0;     // Pontuação do jogador
    private OrthographicCamera camera; // Câmera para visualizar o jogo
    private Player player; // Instância do jogador
    private Array<Plataforma> plataformas; // Array para armazenar as plataformas
    private Texture playerTexture; // Textura do jogador
    private Texture plataformaTexture; // Textura das plataformas
    private SpriteBatch batch; // Usado para desenhar sprites
    RandomXS128 random = new RandomXS128(); // Gerador de números aleatórios
    
    public int PosX = 300, PosY = 200; // Posições iniciais do jogador

    public Play() {
        // Carregando as texturas do jogador e da plataforma
        playerTexture = new Texture("img/gatinhaDoPula.png");
        plataformaTexture = new Texture("img/plataforma.png");

        // Inicializando o jogador com a textura e posição definidas
        player = new Player(playerTexture, PosX, PosY);  
        
        // Inicializando o array de plataformas e adicionando uma plataforma inicial
        plataformas = new Array<>();
        plataformas.add(new Plataforma(plataformaTexture, PosX, PosY - 50));
    }

    private void createPlatforms() {
        float baseY = 50; // Altura mínima para a primeira plataforma
        float maxOffset = 120; // Deslocamento máximo entre as plataformas
        float minOffset = 30; // Deslocamento mínimo entre as plataformas
        float lastY = baseY; // Começando a partir de baseY

        // Gerando plataformas em posições aleatórias
        for (int i = 0; i < 25; i++) { 
            float x = random.nextFloat() *( Gdx.graphics.getWidth() - plataformaTexture.getWidth()); // Posição X aleatória dentro da largura da tela
            
            // Calculando o novo Y com um deslocamento aleatório
            float offsetY = random.nextFloat() * (maxOffset - minOffset) + minOffset;
            lastY += offsetY; // Aumentando a altura com o deslocamento

            // Verificando se a plataforma está dentro do limite superior da tela
            if (lastY <= Gdx.graphics.getHeight() * 1.8) { // Certificando-se que a altura não excede a tela
                Plataforma plataforma = new Plataforma(plataformaTexture, x, lastY); // Criando a plataforma
                plataformas.add(plataforma); // Adicionando a plataforma ao array
            }
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT); // Limpando a tela
        camera.update(); // Atualizando a câmera
        
        // Verifica se o jogador está subindo
        if (player.isRising()) {
            score++; // Aumenta a pontuação enquanto o jogador está subindo
        }

        // Desenhar jogador e plataformas
        batch.setProjectionMatrix(camera.combined); // Configurando a projeção da câmera
        batch.begin(); // Começando a sessão de desenho
        for (Plataforma platform : plataformas) {
            platform.getSprite().draw(batch); // Desenhando cada plataforma
        }
        player.getSprite().draw(batch); // Desenhando o jogador

        // Atualizando jogador e plataformas
        player.update(delta, plataformas);  // Atualizando o jogador e verificando colisões com as plataformas
        for (Plataforma platform : plataformas) {
            platform.update(delta); // Atualizando as plataformas
        }

        white.draw(batch, "Score: " + score, 10, Gdx.graphics.getHeight() - 10);

        batch.end(); // Finalizando a sessão de desenho
    }

    @Override
    public void show() {
    	
    	white = new BitmapFont(Gdx.files.internal("font/Branca.fnt"), false);  // Fonte branca

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
        batch.dispose(); // Liberando os recursos do SpriteBatch
    }
}
