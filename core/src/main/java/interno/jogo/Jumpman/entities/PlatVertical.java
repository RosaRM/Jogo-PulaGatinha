package interno.jogo.Jumpman.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class PlatVertical extends Plataforma {
    private Vector2 direction;
    private float speed;
    private float distance;
    private float originalY;
    private float fall;

    // Construtor da classe PlatVertical
    public PlatVertical(Texture texture, float x, float y) {
        super(texture, x, y);  // Chama o construtor da classe base Plataforma
        this.direction = new Vector2(0, 1);  // Direção inicial (para cima)
        this.speed = 175f;        // Velocidade padrão
        this.distance = 50;       // Distância máxima de movimento
        this.originalY = y;
        this.fall = 0;            // Inicializa a velocidade de queda
    }

    // Atualiza a posição da plataforma vertical a cada frame
    @Override
    public void update(float deltaTime) {
        float distanceL = distance;
        float distanceR = distanceL * -1;
        
        if (movimentoAtivo) {
            // Atualiza a posição da plataforma com base na direção, velocidade e queda
            position.add(0, direction.y * deltaTime * speed + fall * deltaTime);
            this.originalY += fall * deltaTime;
        } else {
            // Se o movimento não estiver ativo, move apenas conforme a direção inicial
            position.add(0, direction.y * deltaTime * speed);
        }

        // Verifica se a plataforma ultrapassou a distância máxima e inverte a direção
        if (Math.abs(position.y - originalY) >= distanceL && direction.y == 1) {
            direction.scl(-1);  // Inverte a direção (movimento para baixo)
        } else if (Math.abs(position.y - originalY) * -1 <= distanceR && direction.y == -1) {
            direction.scl(-1);  // Inverte a direção (movimento para cima)
        }

        // Atualiza a posição do sprite
        sprite.setPosition(position.x, position.y);
    }

    // Define a velocidade de queda da plataforma
    public void setVel(Vector2 velocity) {
        this.fall = -velocity.y;  // Altera a direção da queda no eixo Y
    }

    // Retorna a direção de movimento da plataforma
    public Vector2 getVel() {
        return direction;
    }
}
