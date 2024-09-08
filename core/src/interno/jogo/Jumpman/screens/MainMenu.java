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

import aurelienribon.tweenengine.TweenManager;
import interno.jogo.Jumpman.JumpMain;

public class MainMenu implements Screen {

	
	private SpriteBatch batch;
	private Sprite splash;
    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private Table table;
    private TextButton buttonPlay, buttonExit;
    private BitmapFont white, black;
    private Label heading;
    private Viewport viewport;
    private Sprite bg;

    @Override
    public void show() {
    	
        batch = new SpriteBatch();
        Texture bgTexture = new Texture(Gdx.files.internal("img/Rafundo.jpeg"));
        bg = new Sprite(bgTexture);
        bg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Obtém a largura e altura da tela
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        // Cria o Viewport com a largura e altura
        viewport = new StretchViewport(width, height);

        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        atlas = new TextureAtlas("ui/button.pack");
        skin = new Skin(atlas);

        table = new Table();
        table.setFillParent(true); // Faz a tabela preencher o Stage

        // Fontes carregadas em bitmap
        white = new BitmapFont(Gdx.files.internal("font/Branca.fnt"), false);
        black = new BitmapFont(Gdx.files.internal("font/Preta.fnt"), false);

        // Criando botões
        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("btn-up");
        textButtonStyle.down = skin.getDrawable("btn-down");
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = black;

        buttonExit = new TextButton("EXIT", textButtonStyle);
        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        buttonExit.pad(29);

        buttonPlay = new TextButton("PLAY", textButtonStyle);
        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Levels());
            }
        });
        buttonPlay.pad(25);

        // Criando heading
        heading = new Label(JumpMain.TITULO, new LabelStyle(white, Color.WHITE));
        heading.setFontScale(2f);    

        // Colocando tudo junto
        table.add(heading).align(0);
        table.getCell(heading).spaceBottom(300);
        table.row();
        table.add(buttonPlay).uniform().spaceBottom(15); // Usa tamanho uniforme
        table.row();
        table.add(buttonExit).uniform();

        // Adiciona a tabela ao stage
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        

        // Começa a desenhar a imagem de fundo
        batch.begin();
        bg.draw(batch);
        batch.end();

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
    	batch.dispose();
        bg.getTexture().dispose(); 
        stage.dispose();
        atlas.dispose();
        skin.dispose();
        white.dispose();
        black.dispose();
    }
}
