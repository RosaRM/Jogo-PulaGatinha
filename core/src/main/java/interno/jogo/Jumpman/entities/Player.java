package interno.jogo.Jumpman.entities;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import interno.jogo.Jumpman.screens.MainMenu;
import interno.jogo.Jumpman.screens.Pontuacao;

public class Player {
    // Atributos do jogador
    protected Sprite sprite;
    protected Vector2 position;
    protected Vector2 velocity;
    private float gravity;
    protected boolean IsFalling = false;
    protected boolean IsDead = false;
    private boolean IsRising = false;
    protected boolean IsFliped = false;
    private float jumpVelocity = 650f, horizontaVelocity = 275f;
    public int width = 64, height = 132;
    private Sound jump = Gdx.audio.newSound(Gdx.files.internal("sounds/pulo.wav"));

    // Construtor do jogador
    public Player(Texture texture, float x, float y) {
        this.sprite = new Sprite(texture);
        this.position = new Vector2(x, y);
        this.velocity = new Vector2(0, 0);
        sprite.setSize(width, height);
        sprite.setPosition(position.x, position.y);
    }

    // Atualiza o jogador e verifica colisões
    public void update(float deltaTime, Array<Plataforma> plataformas, Array<Monster> monstros) {
        for (Plataforma plataforma : plataformas) {
            if (plataforma.isOnPlatform(this)) {
                break;
            }
        }

        for (Monster monstro : monstros) {
            if (monstro.isOnEnemy(this)) {
                break;
            }
        }

        // Reposiciona o jogador se sair da tela
        float playerWidth = sprite.getWidth();
        if (position.x > (Gdx.graphics.getWidth() - playerWidth / 2)) {
            position.x = -playerWidth / 2;
        } else if (position.x < (-playerWidth / 2)) {
            position.x = Gdx.graphics.getWidth() - playerWidth / 2;
        }

        // Aplica gravidade no jogador
        if (position.y > 0 || velocity.y > 0) {
            velocity.y += gravity * deltaTime;
        }
        if (velocity.y < -900) {
            velocity.y = -900;
        }

        if (velocity.y < 0) {
            IsFalling = true;
            gravity = -750 * 1.6f;
        } else {
            IsFalling = false;
            gravity = -750;
        }

        // Atualiza a posição com base na velocidade
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);

        // Impede o jogador de cair abaixo do chão
        if (position.y <= 0) {
            IsDead = true;
        }

        // Impede o jogador de sair de uma posição superior na tela
        if (position.y >= Gdx.graphics.getHeight() / 1.75f) {
            position.y = Gdx.graphics.getHeight() / 1.75f;
            IsRising = true;

            for (Monster monstro : monstros) {
                monstro.ativarMovimento(true);
                monstro.setVel(velocity);
            }

            // Ativa movimento das plataformas
            for (Plataforma plataforma : plataformas) {
                plataforma.ativarMovimento(true);
                plataforma.setVel(velocity);
            }
        } else {
            IsRising = false;
            for (Monster monstro : monstros) {
                monstro.ativarMovimento(false);
                monstro.setVel(velocity);
            }
            for (Plataforma plataforma : plataformas) {
                plataforma.ativarMovimento(false);
            }
        }

        // Se o jogador morreu, troca para a tela de pontuação
        if (IsDead) {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new Pontuacao());
            jump.dispose();
        }

        // Atualiza a posição do sprite
        sprite.setPosition(position.x, position.y);
    }

    // Faz o jogador pular
    public void jump() {
        velocity.y = jumpVelocity;
        jump.play();
    }

    public boolean isRising() {
        return IsRising;
    }

    // Getters para sprite e posição
    public Sprite getSprite() {
        return sprite;
    }

    public Vector2 getPosition() {
        return position;
    }

    // Lida com eventos de tecla pressionada
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Keys.ESCAPE:
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu()); // Vai para o menu principal
                break;
            case Keys.D:
            case Keys.RIGHT:
                if (IsFliped) {
                    sprite.flip(true, false);
                    IsFliped = false;
                }
                velocity.x = horizontaVelocity; // Move o jogador para a direita
                break;
            case Keys.A:
            case Keys.LEFT:
                if (!IsFliped) {
                    sprite.flip(true, false);
                    IsFliped = true;
                }
                velocity.x = -horizontaVelocity; // Move o jogador para a esquerda
                break;
/*            case Keys.W: // Faz o jogador pular
                jump();
                break; */
            default:
                return false;
        }
        return true;
    }

    // Lida com eventos de tecla solta
    public boolean keyUp(int keycode) {
        if (keycode != 0) {
            if (keycode == Keys.A || keycode == Keys.D || keycode == Keys.LEFT || keycode == Keys.RIGHT) {
                velocity.x = 0; // Para a movimentação horizontal
            } else {
                return false;
            }
        }
        return true;
    }
}
