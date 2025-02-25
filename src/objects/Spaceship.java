
package objects;

import com.jme3.audio.AudioNode;
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

public class Spaceship extends Node {
    
    public static final float MAX_SPEED = 4;
    
    private Spatial model;
    private Node front;
    private Node rear;
    private Node leftGun;
    private Node rightGun;
    private Node turbines;
    private Node sounds;
    private Material laserMaterial;
    private RigidBodyControl rbc;
    
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
        
        leftGun = new Node("LeftGun");
        rightGun = new Node("RightGun");
        attachChild(leftGun);
        attachChild(rightGun);
        leftGun.setLocalTranslation(-0.5f, 0, 0);
        rightGun.setLocalTranslation(0.5f, 0, 0);
        
        sounds = new Node("Sounds");        
        attachChild(sounds);
    }
    
    public void registerPhysics(PhysicsSpace ps) {
        CollisionShape collisionShape = CollisionShapeFactory.createDynamicMeshShape(model);
        rbc = new RigidBodyControl(collisionShape, 1);
        addControl(rbc);
        ps.add(rbc);
    }
    
    public void initAudio(String name, AudioNode audio) {
        audio.setName(name);
        sounds.attachChild(audio);
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
        fire1.setLocalTranslation(0.1f, 0, 0.2f);
        turbines.attachChild(fire1);
        
        ParticleEmitter fire2 = fire1.clone();
        fire2.setLocalTranslation(-0.1f, 0, 0.2f);
        turbines.attachChild(fire2);
        
        attachChild(turbines);
    }
    
    public Laser[] shoot() {
        Laser laser1 = new Laser("Laser", laserMaterial);
        Vector3f direction = new Vector3f(0, 0, 0);
        getLocalRotation().mult(new Vector3f(0, 0, -1), direction);
        laser1.setDirection(direction);
        laser1.setLocalTranslation(leftGun.getWorldTranslation());
        laser1.setLocalRotation(getWorldRotation());
        
        Laser laser2 = (Laser)laser1.clone();
        laser2.setLocalTranslation(rightGun.getWorldTranslation());
        
        Laser[] lasers = {laser1, laser2};
        return lasers;
    }  

    public AudioNode getSound(String name) {
        return (AudioNode)sounds.getChild(name);
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

    public Material getLaserMaterial() {
        return laserMaterial;
    }

    public void setLaserMaterial(Material laserMaterial) {
        this.laserMaterial = laserMaterial;
    }
    
    public RigidBodyControl getControl() {
        return rbc;
    }
    
}
