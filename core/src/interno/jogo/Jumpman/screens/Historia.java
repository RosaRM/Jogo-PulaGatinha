package interno.jogo.Jumpman.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
    private int currentImageIndex; // �ndice da imagem atual

    public Historia() {
        batch = new SpriteBatch();
        tweenManager = new TweenManager();

        // Registrando o Tween para Sprite
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());

        // Carregar todas as imagens da hist�ria
        images = new Texture[] {
            new Texture(Gdx.files.internal("img/fundoss.jpeg")),
            new Texture(Gdx.files.internal("img/painel1.jpg")),
            new Texture(Gdx.files.internal("img/painel2.jpg")),
            new Texture(Gdx.files.internal("img/Rafundo.jpeg")),
            new Texture(Gdx.files.internal("img/gatinhaDoPula.png"))
            // Adicione mais imagens conforme necess�rio
        };

        currentImageIndex = 0;

        // Criar o sprite com a primeira imagem
        sprite = new Sprite(images[currentImageIndex]);
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Inicializar a primeira transi��o
        iniciarTransicao();
    }

    private void iniciarTransicao() {
        // Definir a opacidade inicial como 0 (invis�vel)
        Tween.set(sprite, SpriteAccessor.ALPHA).target(0).start(tweenManager);

        // Transi��o para fazer a imagem aparecer
        Tween.to(sprite, SpriteAccessor.ALPHA, 2) // 2 segundos para aparecer
            .target(1)
            .repeatYoyo(1, 1f) // Faz a imagem desaparecer depois de aparecer
            .setCallback(new TweenCallback() {
                @Override
                public void onEvent(int type, BaseTween<?> source) {
                    // Quando a imagem desaparecer, mudar para a pr�xima
                    currentImageIndex++;

                    if (currentImageIndex < images.length) {
                        sprite.setTexture(images[currentImageIndex]); // Mudar a textura para a pr�xima imagem
                        iniciarTransicao(); // Iniciar a pr�xima transi��o
                    } else {
                        // Todas as imagens foram exibidas, ir para a pr�xima tela (menu, jogo, etc.)
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
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        for (Texture texture : images) {
            texture.dispose();
        }
    }
}
