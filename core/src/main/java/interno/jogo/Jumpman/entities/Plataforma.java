package interno.jogo.Jumpman.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Plataforma {
    // Atributos da classe Plataforma
    protected Sprite sprite;  // Sprite da plataforma
    protected Vector2 position;  // Posição da plataforma
    private Vector2 velocity;  // Velocidade de movimento da plataforma
    protected boolean movimentoAtivo = false;  // Indica se o movimento está ativado
    public int width = 65, height = 35;  // Tamanho da plataforma

    // Construtor da classe Plataforma
    public Plataforma(Texture texture, float x, float y) {
        this.sprite = new Sprite(texture);  // Inicializa o sprite
        this.position = new Vector2(x, y);  // Define a posição inicial
        this.velocity = new Vector2(0, 0);  // Inicializa a velocidade
        sprite.setPosition(position.x, position.y);  // Define a posição do sprite
        sprite.setSize(width, height);  // Define o tamanho do sprite
    }

    // Verifica se o jogador está sobre a plataforma
    public boolean isOnPlatform(Player player) {
        float playerFootLeft = 0;
        float playerFootRight = 0;
        float playerBottom = player.position.y;  // Posição do pé do jogador
        float playerPrevBottom = player.position.y - player.velocity.y * Gdx.graphics.getDeltaTime(); // Posição do pé na iteração anterior

        if (player.IsFliped) {
            playerFootLeft = player.position.x + player.sprite.getWidth() - 25;
            playerFootRight = player.position.x; 
        } else {
            playerFootLeft = player.position.x + player.sprite.getWidth();
            playerFootRight = player.position.x + 25;    			
        }

        // Verifica se há sobreposição entre o jogador e a plataforma
        if(playerPrevBottom <= getPosition().y + getSprite().getHeight() &&
                playerBottom > getPosition().y && 
                playerFootLeft > getPosition().x &&  // Verifica no eixo X
                playerFootRight < getPosition().x + getSprite().getWidth() && player.velocity.y < 0 ) {
            
            // O jogador pula automaticamente ao pousar na plataforma
            player.jump();
            return true;
        }

        return false;
    }

    // Atualiza a lógica da plataforma a cada frame
    public void update(float deltaTime) {
        if (movimentoAtivo) {
            position.add(velocity.x * deltaTime, velocity.y * deltaTime);  // Atualiza a posição da plataforma
        } else {
            velocity.y = 0;  // Se o movimento não estiver ativo, a velocidade no eixo Y é zerada
        }

        sprite.setPosition(position.x, position.y);  // Atualiza a posição do sprite
    }

    // Ativa ou desativa o movimento da plataforma
    public void ativarMovimento(boolean ativo) {
        this.movimentoAtivo = ativo;
    }

    // Métodos getters para o sprite e a posição
    public Sprite getSprite() {
        return sprite;
    }

    public Vector2 getPosition() {
        return position;
    }

    // Define a velocidade da plataforma
    public void setVel(Vector2 velocity) {
        this.velocity.y = -velocity.y;  // Inverte a direção da velocidade no eixo Y
    }

    public Vector2 getVel() {
        return velocity;
    }
}
