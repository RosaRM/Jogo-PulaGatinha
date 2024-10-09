package interno.jogo.Jumpman.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
    private World world;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Player player;
    private Array<Plataforma> platformas; // Array de plataformas
    private ShapeRenderer shapeRenderer;

    private final float TIMESTEP = 1 / 60F;
    private final int VELOCITYITERATIONS = 6, POSITIONITERATIONS = 2;     
    RandomXS128 random = new RandomXS128();
    
    private final float PLATFORM_WIDTH = 120f;  // Largura padrão das plataformas
    private final float PLATFORM_HEIGHT = 20f; // Altura padrão das plataformas

    private void createPlatforms() {
        float baseY = 50; // A altura mínima para a primeira plataforma
        float maxOffset = 200; // Deslocamento máximo entre as plataformas
        float minOffset = 50; // Deslocamento mínimo entre as plataformas
        float lastY = baseY; // Começa a partir de baseY

        // Gera plataformas em posições aleatórias
        for (int i = 0; i < 10; i++) { // Gera 4 plataformas
            float x = random.nextFloat() * Gdx.graphics.getWidth(); // Ajuste para a largura total da tela
            
            // Calcula o novo Y com um deslocamento aleatório
            float offsetY = random.nextFloat() * (maxOffset - minOffset) + minOffset;
            lastY += offsetY; // Aumenta a altura com o deslocamento

            // Verifica se a plataforma está dentro do limite superior da tela
            if (lastY <= Gdx.graphics.getHeight()) { // Verifica se está dentro da altura total da tela
                Plataforma plataforma = new Plataforma(world, x, lastY, PLATFORM_WIDTH, PLATFORM_HEIGHT); // Não dividir por 20
                platformas.add(plataforma);
            }
            Gdx.app.log("Plataforma", "x: " + x + ", y: " + lastY);
        }
    }

    // No método show(), ajuste a câmera
    @Override
    public void show() {
        world = new World(new Vector2(0, -9.81f), true);
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Ajustando a câmera para as dimensões reais
        player = new Player(world, 300, 400, 100); // Posição centralizada

        platformas = new Array<>();
        createPlatforms(); // Cria plataformas ao iniciar o jogo

        Gdx.input.setInputProcessor(player);
        
    }



    @Override
    public void render(float delta) {
        // Atualiza a lógica do jogo
        world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
        
        // Lógica do loop hole ajustada
        Body playerBody = player.getBody(); // obtem o corpo do jogador
        float playerX = playerBody.getPosition().x; 
        float playerWidth = player.width; 
        float playerHeight = player.height; // Adicione esta linha para obter a altura do jogador
        
        if (playerX > (Gdx.graphics.getWidth() + playerWidth / 2)) {
            playerBody.setTransform(-playerWidth / 2, playerBody.getPosition().y, playerBody.getAngle());
        } else if (playerX < (-playerWidth / 2)) {
            playerBody.setTransform(Gdx.graphics.getWidth() + playerWidth / 2, playerBody.getPosition().y, playerBody.getAngle());
        } 
        
        if (player.isOnGround) {  // Verifica se o jogador está no chão
            player.jump();  // Chama o método de salto
        }

        player.update(delta);

        // Limpa a tela
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // Renderiza as plataformas
        for (Plataforma plataforma : platformas) {
            plataforma.getSprite().setPosition(plataforma.getBody().getPosition().x - plataforma.getWidth() / 2, plataforma.getBody().getPosition().y - plataforma.getHeight() / 2);
            plataforma.getSprite().draw(batch);
        }

        // Renderiza o jogador
        player.getSprite().setPosition(player.getBody().getPosition().x - player.getWidth() / 2, player.getBody().getPosition().y - player.getHeight() / 2);
        player.getSprite().draw(batch);

        batch.end();

        // Desenha os contornos das plataformas
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 0, 0, 1); // Cor vermelha para os contornos

        for (Plataforma plataforma : platformas) {
            float x = plataforma.getBody().getPosition().x - plataforma.getWidth() / 2;
            float y = plataforma.getBody().getPosition().y - plataforma.getHeight() / 2;
            shapeRenderer.rect(x, y, plataforma.getWidth(), plataforma.getHeight());
        }

        // Desenha o contorno do jogador
        float playerXPosition = player.getBody().getPosition().x - playerWidth / 2;
        float playerYPosition = player.getBody().getPosition().y - playerHeight / 2;
        shapeRenderer.rect(playerXPosition, playerYPosition, playerWidth, playerHeight); // Desenha a caixa ao redor do jogador

        shapeRenderer.end();
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
        shapeRenderer.dispose(); 
        world.dispose();
    }
}
