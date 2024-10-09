package interno.jogo.Jumpman;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Plataforma {
    private Body body;
    private Sprite sprite;
    private float width;  
    private float height;
    
    public Plataforma(World world, float x, float y, float width, float height) {
        this.width = width;   // Inicializa a largura
        this.height = height; // Inicializa a altura

        // Cria o corpo da plataforma
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.StaticBody;
        bodyDef.position.set(new Vector2(x, y));

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0;

        body.createFixture(fixtureDef);
        body.setUserData("platform"); // Define "platform" como UserData para a colisão

        shape.dispose();

        // Cria o sprite para a plataforma
        sprite = new Sprite(new Texture("img/plataforma.png"));
        sprite.setSize(width, height); // Aumentando o tamanho da plataforma
        sprite.setOrigin(width / 2, height / 2); // Ajuste a origem para o centro do sprite

        // ** Não sobrescreva o UserData do corpo após definir o sprite **
    }

    public Sprite getSprite() {
        return sprite;
    }

    public float getWidth() {
        return width; 
    }

    public float getHeight() {
        return height; 
    }

    public Body getBody() {
        return body; // Adicionando o método getBody para obter o corpo da plataforma
    }
}
