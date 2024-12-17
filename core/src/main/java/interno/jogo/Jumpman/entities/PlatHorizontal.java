package interno.jogo.Jumpman.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class PlatHorizontal extends Plataforma {
	// Inicializando variaveis
    private Vector2 direction;
    private float speed;
    private float distance;
    private float originalX;

    // Construtor da classe PlatHorizontal
    public PlatHorizontal(Texture texture, float x, float y) {
        super(texture, x, y);
        direction = new Vector2(1, 0);
        speed = 150f;
        distance = 100f;
        originalX = x;
    }

    // Atualiza a posição da plataforma horizontal a cada frame
    @Override
    public void update(float deltaTime) {
        if (movimentoAtivo) {
            position.add(direction.x * speed * deltaTime, direction.y * deltaTime);
        } else {
            position.add(direction.x * speed * deltaTime, 0);
        }

        // Verifica se a plataforma ultrapassou a distância máxima e inverte a direção
        if (Math.abs(position.x - originalX) >= distance && direction.x == 1) {
            direction.scl(-1);
        } else if (Math.abs(position.x - originalX) * -1 <= -distance && direction.x == -1) {
            direction.scl(-1);
        }

        sprite.setPosition(position.x, position.y);
    }

    // Define a direção da plataforma com base na velocidade
    public void setVel(Vector2 velocity) {
        this.direction.y = -velocity.y;
    }

    // Retorna a direção da plataforma
    public Vector2 getVel() {
        return direction;
    }
}
