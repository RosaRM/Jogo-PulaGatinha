package interno.jogo.Jumpman.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.utils.Array;
import interno.jogo.Jumpman.entities.Monster;
import interno.jogo.Jumpman.entities.PlatHorizontal;
import interno.jogo.Jumpman.entities.PlatVertical;
import interno.jogo.Jumpman.entities.Plataforma;
import interno.jogo.Jumpman.entities.Player;

public class Play implements Screen {
    private BitmapFont white;
    public static int score = 0;
    private OrthographicCamera camera;
    private Player player;
    private Array<Monster> monstros; // Array para os monstros
    private Array<Plataforma> plataformas; // Array para as plataformas
    private Texture playerTexture, plataformaTexture, plathorizonTexture, monsterTexture;
    private SpriteBatch batch;
    RandomXS128 random = new RandomXS128(); // Gerador de números aleatórios para posições e espaçamento
    private Sprite bg;
    private float likelihood = 0.075f; // Probabilidade de criar plataformas horizontais ou verticais
    public int PosX = 300, PosY = 200;
    private Music tema; // Música de fundo

    public Play() {
        // Carregamento de texturas e música
        playerTexture = new Texture("img/gatinhaDoPula.png");
        monsterTexture = new Texture("img/Enemy.png");
        plataformaTexture = new Texture("img/plataforma.png");
        plathorizonTexture = new Texture("img/plataformaH.png");
        tema = Gdx.audio.newMusic(Gdx.files.internal("music/Trilha.wav"));

        tema.setLooping(true); // A música vai tocar em loop
        tema.play();
        tema.setVolume(0.5f); // Ajuste de volume da música

        player = new Player(playerTexture, PosX, PosY); // Instância do jogador

        monstros = new Array<>(); // Inicializa o array de monstros
        monstros.add(new Monster(playerTexture, PosX + 100, PosY + 150)); // Adiciona um monstro na cena inicial

        plataformas = new Array<>(); // Inicializa o array de plataformas
        plataformas.add(new Plataforma(plataformaTexture, PosX, PosY - 50)); // Adiciona uma plataforma inicial
    }

    // Método que cria novas plataformas conforme a pontuação e a probabilidade
    private void createPlatforms() {
        float baseY = 60;
        float lastY = baseY;

        while (lastY <= Gdx.graphics.getHeight() * 1.8) {
            float x = random.nextFloat() * (Gdx.graphics.getWidth() - plataformaTexture.getWidth());

            // Ajuste da distância entre as plataformas com base na pontuação
            float maxOffset = Math.max(120 - score * 0.5f, 50);
            float minOffset = Math.max(30 - score * 0.1f, 10);
            float offsetY = random.nextFloat() * (maxOffset - minOffset) + minOffset;
            lastY += offsetY;

            // Criação de diferentes tipos de plataformas
            if (random.nextFloat() < likelihood - 0.025) {
                Plataforma plataforma = new PlatHorizontal(plathorizonTexture, x, lastY);
                plataformas.add(plataforma);
            } else if (random.nextFloat() < (likelihood - 0.025) * 2) {
                Plataforma plataforma = new PlatVertical(plathorizonTexture, x, lastY);
                plataformas.add(plataforma);
            } else {
                Plataforma plataforma = new Plataforma(plataformaTexture, x, lastY);
                plataformas.add(plataforma);
            }
        }
    }
    // Atualiza as plataformas com base no tempo
    private void NewPlataforms(float delta) {
    	float lastPlatformY = plataformas.get(plataformas.size - 1).getPosition().y;
    	
    	// Atualiza as plataformas existentes
    	for (int i = plataformas.size - 1; i >= 0; i--) {
    		Plataforma plataforma = plataformas.get(i);
    		plataforma.update(delta);
    		
    		// Remove plataformas que saíram da tela
    		if (plataforma.getPosition().y + plataforma.getSprite().getHeight() < -100) {
    			plataformas.removeIndex(i);
    		}
    	}
    	
    	// Cria novas plataformas se necessário
    	if (lastPlatformY < Gdx.graphics.getHeight() * 1.3) {
    		float maxOffset = 120 + score * 0.01f;
    		float minOffset = 60 + score * 0.05f;
    		float offsetY = random.nextFloat() * (maxOffset - minOffset) + minOffset;
    		
    		float x = MathUtils.random(10, Gdx.graphics.getWidth() - plataformaTexture.getWidth());
    		float newY = lastPlatformY + offsetY;
    		
    		if (random.nextFloat() < likelihood) {
    			plataformas.add(new PlatVertical(plathorizonTexture, x, newY));
    		} else if (random.nextFloat() < likelihood * 2) {
    			plataformas.add(new PlatHorizontal(plathorizonTexture, x, newY));
    		} else {
    			plataformas.add(new Plataforma(plataformaTexture, x, newY));
    		}
    	}
    }

    // Método que cria novos monstros com espaçamento variável
    private void createMonster() {
        float baseY = 60;
        float lastY = baseY;

        // Aumenta o espaçamento inicial entre os monstros
        float initialMaxOffset = 1200; // Espaçamento maior no início
        float initialMinOffset = 1000;

        while (lastY <= Gdx.graphics.getHeight() * 1.8) {
            float x = random.nextFloat() * (Gdx.graphics.getWidth() - monsterTexture.getWidth());

            // Ajuste do espaçamento com base na pontuação
            float maxOffset = Math.max(initialMaxOffset - score * 0.5f, 900);  
            float minOffset = Math.max(initialMinOffset - score * 0.2f, 800); 

            float offsetY = random.nextFloat() * (maxOffset - minOffset) + minOffset;
            lastY += offsetY;

            Monster monstro = new Monster(monsterTexture, x, lastY);
            monstros.add(monstro); // Adiciona o monstro ao array
        }
    }

    // Atualiza os monstros e verifica se eles saíram da tela ou morreram
    private void NewMonsters(float delta) {
        float lastMonsterY = monstros.get(monstros.size - 1).getPosition().y;

        // Atualiza os monstros existentes
        for (int i = monstros.size - 1; i >= 0; i--) {
            Monster monstro = monstros.get(i);
            monstro.update(delta);

            // Remove monstros que saíram da tela ou foram mortos
            if (monstro.getPosition().y + monstro.getSprite().getHeight() < -100 || monstro.IsDead) {
                monstros.removeIndex(i);
            }
        }
        // Cria novos monstros se necessário
        if (lastMonsterY < Gdx.graphics.getHeight() * 1.3) {
            float maxOffset = 1000 - score * 0.002f;
            float minOffset = 700 - score * 0.005f;
            float offsetY = random.nextFloat() * (maxOffset - minOffset) + minOffset;

            float x = MathUtils.random(10, Gdx.graphics.getWidth() - monsterTexture.getWidth());
            float newY = lastMonsterY + offsetY;

            monstros.add(new Monster(monsterTexture, x, newY)); // Criação de um novo monstro
        }
    }


    @Override
    public void show() {
        score = 0; // Reinicia a pontuação
        white = new BitmapFont(Gdx.files.internal("font/Preta.fnt"), false);
        Texture bgTexture = new Texture(Gdx.files.internal("img/background.png"));
        bg = new Sprite(bgTexture);
        bg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Configuração do input
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                return player.keyDown(keycode);
            }

            @Override
            public boolean keyUp(int keycode) {
                return player.keyUp(keycode);
            }
        });

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        monstros = new Array<>(); // Reinicializa os monstros
        plataformas = new Array<>(); // Reinicializa as plataformas
        createPlatforms(); // Cria as plataformas
        createMonster(); // Cria os monstros
        plataformas.add(new Plataforma(plataformaTexture, PosX, PosY - 50)); // Adiciona uma plataforma inicial
        player = new Player(playerTexture, PosX, PosY); // Reinicializa o jogador
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        camera.update();

        // Incrementa a pontuação quando o jogador sobe
        if (player.isRising()) {
            score++;
        }

        player.update(delta, plataformas, monstros); // Atualiza o jogador
        NewMonsters(delta); // Atualiza os monstros
        NewPlataforms(delta); // Atualiza as plataformas

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        bg.draw(batch); // Desenha o fundo

        // Desenha as plataformas
        for (Plataforma plataforma : plataformas) {
            plataforma.getSprite().draw(batch);
        }

        // Desenha os monstros
        for (Monster monstro : monstros) {
            monstro.getSprite().draw(batch);
        }

        // Desenha o jogador
        player.getSprite().draw(batch);

        // Exibe a pontuação
        white.draw(batch, "Score: " + score, 20, Gdx.graphics.getHeight() - 30);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        tema.dispose(); // Libera recursos da música
    }

    @Override
    public void dispose() {
        batch.dispose(); // Libera o SpriteBatch
        playerTexture.dispose(); // Libera a textura do jogador
        plataformaTexture.dispose(); // Libera a textura das plataformas
        white.dispose(); // Libera o BitmapFont
        tema.dispose(); // Libera a música
    }
}
