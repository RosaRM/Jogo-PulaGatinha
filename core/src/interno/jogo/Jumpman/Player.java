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
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import interno.jogo.Jumpman.screens.MainMenu;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactListener;


public class Player extends InputAdapter {
    private Body body;
    private Fixture fixture;
    public final float width, height;
    private Vector2 velocity = new Vector2();
    private float movementSpeed = 100f; // Velocidade do movimento
    private float jumpForce = 1000f; // Força de pulo ajustada
    public boolean isOnGround = false; // Flag para saber se está no chão
    private Sprite sprite;

    public Player(World world, float x, float y, float width) {
        this.width = width;
        height = width * 2;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        bodyDef.fixedRotation = true;

        // Criando a forma do corpo
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2); // Ajustar largura e altura

        // Configurando o fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.restitution = 0.1f; // Pequeno bounce
        fixtureDef.friction = 0.5f;
        fixtureDef.density = 1;

        body = world.createBody(bodyDef);
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData("player"); // Adiciona um identificador ao fixture

        sprite = new Sprite(new Texture("img/gatinhaDoPula.png"));
        sprite.setSize(width, height); // Mantém o tamanho do sprite
        sprite.setOrigin(width / 2, height / 2); // Define a origem para o centro do sprite
        body.setUserData(sprite);

        shape.dispose();

     // Adicionando o ContactListener
     // ContactListener dentro da classe Player
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                if ((contact.getFixtureA().getBody().getUserData() != null && contact.getFixtureA().getBody().getUserData().equals("platform")) ||
                    (contact.getFixtureB().getBody().getUserData() != null && contact.getFixtureB().getBody().getUserData().equals("platform"))) {
                    
                    if (body.getLinearVelocity().y < 0) { // Certifica que o jogador está caindo
                        isOnGround = true;  // Apenas permite pular quando estiver caindo
                    }
                }
            }

            @Override
            public void endContact(Contact contact) {
                if ((contact.getFixtureA().getBody().getUserData() != null && contact.getFixtureA().getBody().getUserData().equals("platform")) ||
                    (contact.getFixtureB().getBody().getUserData() != null && contact.getFixtureB().getBody().getUserData().equals("platform"))) {
                    isOnGround = false;  // Sai do chão ao deixar a plataforma
                }
            }

            @Override
            public void preSolve(Contact contact, com.badlogic.gdx.physics.box2d.Manifold oldManifold) {}
            @Override
            public void postSolve(Contact contact, com.badlogic.gdx.physics.box2d.ContactImpulse impulse) {}
        });


    }

    public void update(float deltaTime) {
        // Atualiza a posição do sprite com a posição do corpo
        sprite.setPosition(body.getPosition().x - width / 2, body.getPosition().y - height / 2); // Centraliza o sprite

        // Mantém a velocidade horizontal constante (ou o que for desejado)
        Vector2 currentVelocity = body.getLinearVelocity();
        body.setLinearVelocity(velocity.x, currentVelocity.y); // Só altera a componente horizontal

        // Pulo automático: verifica se o jogador está no chão e está caindo
        if (isOnGround && currentVelocity.y <= 0) { // Verifica se a velocidade é negativa (caindo)
            jump();  // Executa o pulo automático
        }
    }

 // Na classe Player
    public void jump() {
        if (isOnGround) {
            body.setLinearVelocity(body.getLinearVelocity().x, 0); // Zera a velocidade vertical antes do pulo
            body.applyLinearImpulse(new Vector2(0, jumpForce), body.getWorldCenter(), true);
            isOnGround = false;  // Define como falso logo após pular
        }
    }


    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Keys.ESCAPE:
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
                break;
            case Keys.D:
                velocity.x = movementSpeed;  // Movimentação para a direita
                break;
            case Keys.A:
                velocity.x = -movementSpeed;  // Movimentação para a esquerda
                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Keys.A || keycode == Keys.D) {
            velocity.x = 0;
        } else {
            return false;
        }
        return true;
    }

    public Body getBody() {
        return body;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Fixture getFixture() {
        return fixture;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
