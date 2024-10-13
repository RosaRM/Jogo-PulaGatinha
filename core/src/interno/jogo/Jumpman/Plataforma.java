package interno.jogo.Jumpman;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Plataforma {
    private Sprite sprite;
    private Vector2 position;

    public Plataforma(Texture texture, float x, float y) {
        this.sprite = new Sprite(texture);
        this.position = new Vector2(x, y);
        sprite.setPosition(position.x, position.y);
    }

    public void update(float deltaTime) {
        // Lógica para mover a plataforma (se necessário)

        // Se a plataforma sair da parte inferior da tela (y < 0)
        if (position.y + sprite.getHeight() < 0) {
            // Reposiciona a plataforma no topo da tela em uma nova posição x aleatória
            position.y = 480;  // Ou a altura da sua tela
            position.x = MathUtils.random(0, 800 - sprite.getWidth());  // Coloca em uma posição x aleatória

            // Atualiza a posição do sprite para corresponder ao novo posicionamento
            sprite.setPosition(position.x, position.y);
        }
    }


    public Sprite getSprite() {
        return sprite;
    }

    public Vector2 getPosition() {
        return position;
    }
}
