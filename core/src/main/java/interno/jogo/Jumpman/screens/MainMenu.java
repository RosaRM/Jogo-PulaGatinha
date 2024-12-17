package interno.jogo.Jumpman.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenu implements Screen {

    // Atributos da tela de menu
    private SpriteBatch batch;
    private Stage stage;
    private Table table;
    private ImageButton buttonPlay, buttonExit;
    private Viewport viewport;
    private Sprite bg;
    private Music menuTema;
    private Texture playTexture, playPressedTexture, exitTexture, exitPressedTexture;
    public static Sound click = Gdx.audio.newSound(Gdx.files.internal("sounds/Click.wav"));

    @Override
    public void show() {
        // Inicializa os elementos da tela
        batch = new SpriteBatch();
        Texture bgTexture = new Texture(Gdx.files.internal("img/Capa.png"));
        bg = new Sprite(bgTexture);
        bg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
        // Música de fundo
        menuTema = Gdx.audio.newMusic(Gdx.files.internal("music/Menu.wav"));
        menuTema.setLooping(true);
        menuTema.play();
        menuTema.setVolume(0.45f);

        // Configura o viewport
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        viewport = new StretchViewport(width, height);

        // Inicializa o stage e define como processador de entrada
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        // Carrega as texturas dos botões
        playTexture = new Texture("img/Bplay.png");
        playPressedTexture = new Texture("img/BHplay.png");
        exitTexture = new Texture("img/Bexit.png");
        exitPressedTexture = new Texture("img/BHexit.png");

        // Estilos para os botões
        ImageButtonStyle playButtonStyle = new ImageButtonStyle();
        playButtonStyle.up = new TextureRegionDrawable(new TextureRegion(playTexture));
        playButtonStyle.down = new TextureRegionDrawable(new TextureRegion(playPressedTexture));

        ImageButtonStyle exitButtonStyle = new ImageButtonStyle();
        exitButtonStyle.up = new TextureRegionDrawable(new TextureRegion(exitTexture));
        exitButtonStyle.down = new TextureRegionDrawable(new TextureRegion(exitPressedTexture));

        // Criação dos botões
        buttonPlay = new ImageButton(playButtonStyle);
        buttonExit = new ImageButton(exitButtonStyle);

        // Adiciona listeners nos botões
        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                click.play();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Play()); // Troca para a tela de jogo
            }
        });

        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                click.play();
                Gdx.app.exit(); // Encerra o jogo
            }
        });

        // Organiza os botões na tela usando uma tabela
        table = new Table();
        table.bottom().right();
        table.setFillParent(true);

        // Adiciona os botões à tabela
        table.row().padBottom(10);
        table.add(buttonPlay).uniform().spaceBottom(10);
        table.row().padBottom(25);
        table.add(buttonExit).uniform().spaceBottom(10);

        // Adiciona a tabela ao stage
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        // Limpa a tela e desenha o fundo
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        batch.begin();
        bg.draw(batch); // Desenha o fundo
        batch.end();

        // Atualiza o stage e desenha os elementos
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // Atualiza o viewport se a tela for redimensionada
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {}
    
    @Override
    public void resume() {}

    @Override
    public void hide() {
        menuTema.dispose(); // Libera a música quando a tela é escondida
    }

    @Override
    public void dispose() {
        // Libera os recursos
        batch.dispose();
        bg.getTexture().dispose();
        stage.dispose();
        playTexture.dispose();
        playPressedTexture.dispose();
        exitTexture.dispose();
        exitPressedTexture.dispose();
        menuTema.dispose();
    }
}
