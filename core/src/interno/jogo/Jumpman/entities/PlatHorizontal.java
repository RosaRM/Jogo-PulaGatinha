package interno.jogo.Jumpman.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class PlatHorizontal extends Plataforma {
    private Vector2 direction;  // Dire��o do movimento
    private float speed;        // Velocidade do movimento
    private float distance;     // Dist�ncia m�xima que a plataforma deve percorrer
    private float originalX;    // Posi��o original Y da plataforma

    // Construtor da classe PlataformaMovel
    public PlatHorizontal(Texture texture, float x, float y) {
        super(texture, x, y);  // Chama o construtor da classe base Plataforma
        this.direction = new Vector2(1, 0);  // Dire��o padr�o (para cima)
        this.speed = 150f;        // Velocidade padr�o
        this.distance = 100f;     // Dist�ncia padr�o
        this.originalX = x;      // Armazena a posi��o Y original para refer�ncia
    }

    // M�todo update: atualiza a l�gica da plataforma m�vel
    @Override
    public void update(float deltaTime) {
    	if (movimentoAtivo) {
            // Atualiza a posi��o da plataforma com base na dire��o e na velocidade
            position.add(direction.x * speed * deltaTime, direction.y * deltaTime);
    	}
    	else {
            // Atualiza a posi��o da plataforma com base na dire��o e na velocidade
            position.add(direction.x * speed * deltaTime, 0);
    	}
            // Verifica se a plataforma ultrapassou a dist�ncia m�xima
            if (Math.abs(position.x - originalX) >= distance -1) {
                // Inverte a dire��o do movimento
                direction.scl(-1);
            }
        

        // Atualiza a posi��o do sprite
        sprite.setPosition(position.x, position.y);
    }
	public void setVel(Vector2 velocity) {
		this.direction.y = -velocity.y;
	}
    public Vector2 getVel() {
        return direction;
    }
}
