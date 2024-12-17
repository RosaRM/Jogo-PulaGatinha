package interno.jogo.Jumpman.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import interno.jogo.Jumpman.tween.SpriteAccessor;

public class Historia implements Screen {

    private SpriteBatch batch;
    private Sprite sprite;
    private TweenManager tweenManager;
    private Texture[] images; // Array de texturas para as imagens
    private int currentImageIndex; // Índice da imagem atual
    private Music history;
    public Historia() {
        history = Gdx.audio.newMusic(Gdx.files.internal("music/NOCTIS.mp3"));
        
        history.setLooping(true); 
        history.play();    
        
        batch = new SpriteBatch();
        tweenManager = new TweenManager();

        // Registrando o Tween para Sprite
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());

        // Carregar todas as imagens da história
        images = new Texture[] {
            new Texture(Gdx.files.internal("img/fundoss.jpeg")),
            new Texture(Gdx.files.internal("img/painel1.jpg")),
            new Texture(Gdx.files.internal("img/painel2.jpg")),
            new Texture(Gdx.files.internal("img/Capa.png")),
            new Texture(Gdx.files.internal("img/gatinhaDoPula.png"))
            // Adicione mais imagens conforme necessário
        };

        currentImageIndex = 0;

        // Criar o sprite com a primeira imagem
        sprite = new Sprite(images[currentImageIndex]);
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Inicializar a primeira transição
        iniciarTransicao();
    }

    private void iniciarTransicao() {
        // Definir a opacidade inicial como 0 (invisível)
        Tween.set(sprite, SpriteAccessor.ALPHA).target(0).start(tweenManager);

        // Transição para fazer a imagem aparecer
        Tween.to(sprite, SpriteAccessor.ALPHA, 2) // 2 segundos para aparecer
            .target(1)
            .repeatYoyo(1, 1f) // Faz a imagem desaparecer depois de aparecer
            .setCallback(new TweenCallback() {
                @Override
                public void onEvent(int type, BaseTween<?> source) {
                    // Quando a imagem desaparecer, mudar para a próxima
                    currentImageIndex++;

                    if (currentImageIndex < images.length) {
                        sprite.setTexture(images[currentImageIndex]); // Mudar a textura para a próxima imagem
                        iniciarTransicao(); // Iniciar a próxima transição
                    } else {
                        // Todas as imagens foram exibidas, ir para a próxima tela (menu, jogo, etc.)
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
                    }
                }
            }).start(tweenManager);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        // Atualizar o tween
        tweenManager.update(delta);

        // Desenhar a imagem atual
        batch.begin();
        sprite.draw(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
    	history.dispose();
    }

    @Override
    public void dispose() {
    	history.dispose();
        batch.dispose();
        for (Texture texture : images) {
            texture.dispose();
        }
    }
}