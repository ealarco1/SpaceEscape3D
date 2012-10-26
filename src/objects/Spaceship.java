
package objects;

import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;

public class Spaceship extends Node {
    
    private Spatial model;
    private Node front;
    private Node rear;
    private Node turbines;
    
    public Spaceship(String name, Spatial model) {
        super(name);
        this.model = model;
        attachChild(model);
        front = new Node("SpaceshipFront");
        rear = new Node("SpaceshipRear");
        attachChild(front);
        attachChild(rear);
        front.setLocalTranslation(0, 0, -2);
        rear.setLocalTranslation(0, 0, 2);
    }
    
    public void addTurbines(Material material, Texture texture) {
        turbines = new Node("Turbines");
        turbines.setLocalTranslation(0, 0, 0.4f);
        
        ParticleEmitter fire1 = new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 30);
        material.setTexture("Texture", texture);
        fire1.setMaterial(material);
        fire1.setImagesX(2);
        fire1.setImagesY(2);
        fire1.setEndColor(new ColorRGBA(1f, 1f, 0f, 1f)); // blue
        fire1.setStartColor(new ColorRGBA(0f, 0f, 1f, 0.5f)); // yellow
        fire1.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 0, 2));
        fire1.setStartSize(0.1f);
        fire1.setEndSize(0.05f);
        fire1.setGravity(0, 0, 0);
        fire1.setLowLife(0.1f);
        fire1.setHighLife(0.2f);
        fire1.getParticleInfluencer().setVelocityVariation(0.2f);
        fire1.setLocalTranslation(0.1f, 0, 0);
        turbines.attachChild(fire1);
        
        ParticleEmitter fire2 = fire1.clone();
        fire2.setLocalTranslation(-0.1f, 0, 0);
        turbines.attachChild(fire2);
        
        attachChild(turbines);
    }
    
    public Node getTurbines() {
        return turbines;
    }
    
    public Spatial getModel() {
        return model;
    }

    public void setModel(Spatial model) {
        this.model = model;
    }

    public Node getFront() {
        return front;
    }

    public Node getRear() {
        return rear;
    }
    
}
