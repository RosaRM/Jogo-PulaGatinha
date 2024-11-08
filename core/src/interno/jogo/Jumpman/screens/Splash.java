package interno.jogo.Jumpman.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.TweenAccessor;
import aurelienribon.tweenengine.TweenCallback;
import interno.jogo.Jumpman.tween.SpriteAccessor;

public class Splash implements Screen {

    // Atributos principais
    private SpriteBatch batch;  // Para desenhar os gr�ficos
    private Sprite splash;  // Imagem da splash screen
    private TweenManager tweenManager;  // Gerenciador de anima��es

    @Override
    public void show() {
        // Inicializa o SpriteBatch e o TweenManager
        batch = new SpriteBatch();
        tweenManager = new TweenManager();

        // Registra o TweenAccessor para sprites
        Tween.registerAccessor(Sprite.class, (TweenAccessor<?>) new SpriteAccessor());

        // Carrega a textura da imagem de splash
        Texture splashTexture = new Texture(Gdx.files.internal("img/fundoss.jpeg"));
        splash = new Sprite(splashTexture);
        splash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());  // Ajusta o tamanho da imagem de splash para a tela

        // Define a anima��o: come�a com alpha (transpar�ncia) zero
        Tween.set(splash, SpriteAccessor.ALPHA).target(0).start(tweenManager);

        // Anima��o de fade in e fade out da imagem
        Tween.to(splash, SpriteAccessor.ALPHA, 2).target(1).repeatYoyo(1, 0.1f)  // Faz a transi��o de fade in, depois fade out
            .setCallback(new TweenCallback() {  // Ap�s a anima��o, troca para a tela principal
                @Override
                public void onEvent(int type, BaseTween<?> source) {
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());  // Troca para a tela de MainMenu
                }
            }).start(tweenManager);  // Inicia o gerenciador de anima��es
    }

    @Override
    public void render(float delta) {
        // Limpa a tela e define a cor preta de fundo
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        // Atualiza o tween manager para as anima��es
        tweenManager.update(delta);

        // Desenha a imagem de splash na tela
        batch.begin();
        splash.draw(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // M�todo chamado ao redimensionar a tela (pode ficar vazio se n�o houver ajustes a fazer)
    }

    @Override
    public void pause() {
        // M�todo chamado quando o jogo � pausado (n�o implementado)
    }

    @Override
    public void resume() {
        // M�todo chamado quando o jogo � retomado (n�o implementado)
    }

    @Override
    public void hide() {
        // M�todo chamado ao ocultar a tela (n�o implementado)
    }

    @Override
    public void dispose() {
        // Libera os recursos
        batch.dispose();
        splash.getTexture().dispose();
    }
}
