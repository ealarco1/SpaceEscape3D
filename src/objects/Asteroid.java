
package objects;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;
import com.jme3.util.TangentBinormalGenerator;

public class Asteroid extends Node {
    
    private Spatial model;
    private Vector3f direction;
    private int speed;
    private Node trail;
    private Vector3f rotation;
    private RigidBodyControl rbc;
    
    public Asteroid(String name, Spatial model, Material material) {
        super(name);
        this.model = model;
        this.model.setMaterial(material);
        direction = Vector3f.ZERO;
        rotation = Vector3f.ZERO;
        TangentBinormalGenerator.generate(model);
        attachChild(this.model);
    }
    
    public void registerPhysics(PhysicsSpace ps) {
        CollisionShape collisionShape = CollisionShapeFactory.createDynamicMeshShape(model);
        rbc = new RigidBodyControl(collisionShape, 80);
        rbc.setLinearVelocity(direction.mult(speed));
        rbc.setAngularVelocity(rotation);
        addControl(rbc);
        ps.add(rbc);
    }
    
    public void addTrail(Material material, Texture texture) {
        trail = new Node("CometTrail");
        
        ParticleEmitter fire = new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 30);
        material.setTexture("Texture", texture);
        fire.setMaterial(material);
        fire.setImagesX(2);
        fire.setImagesY(2);
        fire.setEndColor(new ColorRGBA((float)Math.random(), (float)Math.random(),
                (float)Math.random(), 1f));
        fire.setStartColor(new ColorRGBA((float)Math.random(), (float)Math.random(),
                (float)Math.random(), 0.5f));
        fire.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 0, 2));
        System.out.println(model.getWorldScale().length());
        fire.setStartSize(10 * model.getWorldScale().length());
        fire.setEndSize(0);
        fire.setGravity(0, 0, 0);
        fire.setLowLife(0.5f);
        fire.setHighLife(2f);
        fire.getParticleInfluencer().setVelocityVariation(0.1f);
        
        trail.attachChild(fire);
        attachChild(trail);
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Vector3f getDirection() {
        return direction;
    }

    public void setDirection(Vector3f direction) {
        this.direction = direction;
    }
    
    public Spatial getModel() {
        return model;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }
    
}
