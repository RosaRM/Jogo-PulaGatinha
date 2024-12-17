
package interno.jogo.Jumpman.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
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
    private SpriteBatch batch;  // Para desenhar os gráficos
    private Sprite splash;  // Imagem da splash screen
    private TweenManager tweenManager;  // Gerenciador de animações
    private Sound ps2;  
    
    @Override
    public void show() {
    	// Inicializa o SpriteBatch e o TweenManager
    	batch = new SpriteBatch();
    	tweenManager = new TweenManager();
    	ps2 = Gdx.audio.newSound(Gdx.files.internal("sounds/abertura.wav"));
    	
    	ps2.play();
    	
    	// Registra o TweenAccessor para sprites
    	Tween.registerAccessor(Sprite.class, (TweenAccessor<?>) new SpriteAccessor());
    	
    	// Carrega a textura da imagem de splash
    	Texture splashTexture = new Texture(Gdx.files.internal("img/fundoss.jpeg"));
    	splash = new Sprite(splashTexture);
    	splash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());  // Ajusta o tamanho da imagem de splash para a tela
    	
    	// Define a animação inicial: começa com alpha (transparência) zero (invisível)
    	splash.setAlpha(0); // Tornar a imagem inicialmente invisível
    	Tween.set(splash, SpriteAccessor.ALPHA).target(0).start(tweenManager); // Garantir que começa invisível
    	
    	// Aplica o fade-in (a imagem aparece gradualmente)
    	Tween.to(splash, SpriteAccessor.ALPHA, 2).target(1).start(tweenManager); // Fade-in: a imagem vai aparecer durante 2 segundos
    	
    	// Atraso de 2 segundos após o fade-in, e então aplica o fade-out (imagem desaparecendo)
    	Tween.to(splash, SpriteAccessor.ALPHA, 2).target(0).delay(2f)  // Atraso de 2 segundos com a imagem visível
    	.setCallback(new TweenCallback() {  // Após o fade-out, troca para a tela principal
    		@Override
    		public void onEvent(int type, BaseTween<?> source) {
    			((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());  // Troca para o menu principal
    		}
    	}).start(tweenManager);  // Inicia a animação
    }
    
    

    @Override	
    public void render(float delta) {
        // Limpa a tela e define a cor preta de fundo
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        // Atualiza o tween manager para as animações
        tweenManager.update(delta);

        // Desenha a imagem de splash na tela
        batch.begin();
        splash.draw(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // Método chamado ao redimensionar a tela (pode ficar vazio se não houver ajustes a fazer)
    }

    @Override
    public void pause() {
        // Método chamado quando o jogo é pausado (não implementado)
    }

    @Override
    public void resume() {
        // Método chamado quando o jogo é retomado (não implementado)
    }

    @Override
    public void hide() {
        ps2.dispose();
    }

    @Override
    public void dispose() {
        // Libera os recursos
        batch.dispose();
        splash.getTexture().dispose();
        ps2.dispose();
    }
}
