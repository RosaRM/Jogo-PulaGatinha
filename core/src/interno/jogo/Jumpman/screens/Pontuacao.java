package interno.jogo.Jumpman.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import interno.jogo.Jumpman.tween.SpriteAccessor;

public class Pontuacao implements Screen {

    private SpriteBatch batch;
    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private Table table;
    private TextButton buttonReplay, buttonReturn;
    private BitmapFont white, black;
    private Label heading;
    private Viewport viewport;
    private Sprite bg;
    private TweenManager tweenManager;

    @Override
    public void show() {
        // Inicializa o SpriteBatch, TweenManager e Texture da imagem de fundo
        batch = new SpriteBatch();
        tweenManager = new TweenManager();
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());

        // Carrega a textura de fundo
        Texture bgTexture = new Texture(Gdx.files.internal("img/Score.png"));
        bg = new Sprite(bgTexture);
        bg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        bg.setPosition(0, -Gdx.graphics.getHeight()); // Começa fora da tela

        // Cria o Viewport e o Stage
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        viewport = new StretchViewport(width, height);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage); // Configura o stage para receber entrada

        // Carrega o atlas de texturas e a skin para os botões
        atlas = new TextureAtlas("ui/button.pack");
        skin = new Skin(atlas);

        // Inicializa a tabela para organizar os elementos na tela
        table = new Table();
        table.setFillParent(true); // Faz a tabela preencher o Stage

        // Carrega fontes em bitmap
        white = new BitmapFont(Gdx.files.internal("font/Branca.fnt"), false);
        black = new BitmapFont(Gdx.files.internal("font/Preta.fnt"), false);

        // Configura o estilo dos botões
        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("btn-up");
        textButtonStyle.down = skin.getDrawable("btn-down");
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = black;

        // Criação do botão "MENU"
        buttonReturn = new TextButton("MENU", textButtonStyle);
        buttonReturn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
            }
        });
        buttonReturn.pad(29); // Adiciona padding ao botão

        // Criação do botão "PLAY AGAIN"
        buttonReplay = new TextButton("PLAY AGAIN", textButtonStyle);
        buttonReplay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Play());
            }
        });
        buttonReplay.pad(25); // Adiciona padding ao botão

        // Criação do Label para exibir a pontuação
        int x = 1200; // Exemplo de pontuação
        heading = new Label("Score: " + x, new LabelStyle(white, Color.PURPLE));
        heading.setFontScale(1.5f); // Escala do texto

        // Organiza os elementos na tabela
        table.add(heading).expandX().padTop(height * 0.2f); // Texto um pouco acima do meio
        table.row();
        table.add(buttonReplay).expandX().padBottom(20).bottom(); // Botão "PLAY AGAIN" na parte inferior
        table.row();
        table.add(buttonReturn).expandX().padBottom(10).bottom(); // Botão "MENU" na parte inferior

        // Adiciona a tabela ao stage
        stage.addActor(table);
        table.setVisible(false); // Oculta a tabela inicialmente

        // Iniciar a animação de subida
        Tween.to(bg, SpriteAccessor.Y_POSITION, 2) // Move o sprite para a posição 0 (visível)
            .target(0)
            .setCallbackTriggers(TweenCallback.COMPLETE) // Chama a callback quando a animação termina
            .setCallback(new TweenCallback() {
                @Override
                public void onEvent(int type, BaseTween<?> source) {
                    if (type == TweenCallback.COMPLETE) {
                        table.setVisible(true); // Torna a tabela visível após a animação
                    }
                }
            })
            .start(tweenManager); // Inicia a animação
    }

    @Override
    public void render(float delta) {
        // Limpa a tela e define a cor de fundo
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        // Atualiza o TweenManager
        tweenManager.update(delta);

        // Começa a desenhar a imagem de fundo
        batch.begin();
        bg.draw(batch);
        batch.end();

        // Atualiza e desenha o stage
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // Atualiza o Viewport com as novas dimensões
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        // Libera os recursos
        batch.dispose();
        bg.getTexture().dispose(); 
        stage.dispose();
        atlas.dispose();
        skin.dispose();
        white.dispose();
        black.dispose();
    }
}
