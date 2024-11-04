package interno.jogo.Jumpman;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class PlatHorizontal extends Plataforma {
    private Vector2 direction;  // Direção do movimento
    private float speed;        // Velocidade do movimento
    private float distance;     // Distância máxima que a plataforma deve percorrer
    private float originalX;    // Posição original Y da plataforma

    // Construtor da classe PlataformaMovel
    public PlatHorizontal(Texture texture, float x, float y) {
        super(texture, x, y);  // Chama o construtor da classe base Plataforma
        this.direction = new Vector2(1, 0);  // Direção padrão (para cima)
        this.speed = 150f;        // Velocidade padrão
        this.distance = 100f;     // Distância padrão
        this.originalX = x;      // Armazena a posição Y original para referência
    }

    // Método update: atualiza a lógica da plataforma móvel
    @Override
    public void update(float deltaTime) {
    	if (movimentoAtivo) {
            // Atualiza a posição da plataforma com base na direção e na velocidade
            position.add(direction.x * speed * deltaTime, direction.y * deltaTime);
    	}
    	else {
            // Atualiza a posição da plataforma com base na direção e na velocidade
            position.add(direction.x * speed * deltaTime, 0);
    	}
            // Verifica se a plataforma ultrapassou a distância máxima
            if (Math.abs(position.x - originalX) >= distance) {
                // Inverte a direção do movimento
                direction.scl(-1);
            }
        

        // Atualiza a posição do sprite
        sprite.setPosition(position.x, position.y);
    }
	public void setVel(Vector2 velocity) {
		this.direction.y = -velocity.y;
	}
    public Vector2 getVel() {
        return direction;
    }
}
