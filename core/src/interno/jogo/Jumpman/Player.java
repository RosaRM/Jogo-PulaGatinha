package interno.jogo.Jumpman;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import interno.jogo.Jumpman.screens.MainMenu;
import interno.jogo.Jumpman.screens.Play;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactListener;

public class Player extends InputController{
    private Sprite sprite;
    private Vector2 position;
    private Vector2 velocity;
    private float gravity = -55f;
    private boolean jumping = false;
    private float jumpVelocity = 300f, horizontaVelocity = 300f;
    public int width = 64, height = 132;

    public Player(Texture texture, float x, float y) {
        this.sprite = new Sprite(texture);
        this.position = new Vector2(x, y);
        this.velocity = new Vector2(0, 0);

        // Define o tamanho do sprite
        sprite.setSize(width, height);
        sprite.setPosition(position.x, position.y);
    }

    
    
    public void update(float deltaTime, Array<Plataforma> plataformas) {
    	  // Lógica de movimentação para reposicionar o jogador se sair da tela
        float playerWidth = sprite.getWidth();
        if (position.x > (Gdx.graphics.getWidth() - playerWidth /2   )) {
            position.x = -playerWidth /2 ;  // Reposiciona à esquerda
        } else if (position.x < (-playerWidth /2 )) {
            position.x = Gdx.graphics.getWidth() - playerWidth /2;  // Reposiciona à direita
        }
        
        // Aplica a gravidade sempre, exceto se o jogador estiver no chão ou sobre uma plataforma
        if (position.y > 0 || velocity.y > 0) {
            velocity.y += gravity * deltaTime;
        }

        // Atualiza a posição do jogador com base na gravidade e velocidade
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);

        // Verifica se o jogador está colidindo com alguma plataforma
        for (Plataforma plataforma : plataformas) {
            if (isOnPlatform(plataforma) && velocity.y < 0) {
                // Pular automaticamente ao pousar em uma plataforma
                jump();
                break;
            }
        }

        // Impede o jogador de cair abaixo do "chão"
        if (position.y <= 0) {
            position.y = 0;
            velocity.y = 0;
            jumping = false;
        }

        // Atualiza a posição do sprite
        sprite.setPosition(position.x, position.y);
    }
    
    private boolean isOnPlatform(Plataforma plataforma) {
        // Verifica se o jogador está "em cima" da plataforma
        return position.y > plataforma.getPosition().y &&
               position.y - velocity.y * Gdx.graphics.getDeltaTime() <= plataforma.getPosition().y + plataforma.getSprite().getHeight() &&
               position.x + sprite.getWidth() > plataforma.getPosition().x &&
               position.x < plataforma.getPosition().x + plataforma.getSprite().getWidth();
    }


    public void jump() {
        // Apenas pula se o jogador não estiver no ar
        velocity.y = jumpVelocity;
        jumping = true;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Vector2 getPosition() {
        return position;
    }
    
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Keys.ESCAPE:
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
                break;
            case Keys.D:
            	velocity.x = horizontaVelocity;  // Movimentação para a direita
                break;
            case Keys.A:
            	velocity.x = -horizontaVelocity;  // Movimentação para a esquerda
                break;
            default:
                return false;
        }
        return true;
    }

    
    public boolean keyUp(int keycode) {
        if (keycode == Keys.A || keycode == Keys.D) {
            velocity.x = 0;
        } else {
            return false;
        }
        return true;
    }
}