package objects;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;

/**
 *
 * @author Esteban Alarcon Ceballos y Enrique Arango Lyons
 */
public class Planet extends Node {

    private Geometry geom;
    private Node pivot;
    private float rotationSpeed;
    private float translationSpeed;
    private RigidBodyControl rbc;
    
    public Planet(String name, float radius, Material mat) {
        super(name);
        
        geom = new Geometry(name, new Sphere(32, 32, radius));
        geom.setMaterial(mat);
        geom.rotate(-1.7f, 3.0f, 0);
        
        pivot = new Node("Pivot");
        pivot.setLocalTranslation(Vector3f.ZERO);
        pivot.attachChild(geom);
        
        rotationSpeed = 0;
        translationSpeed = 0;
        
        attachChild(pivot);
        
    }
    
    public void registerPhysics(PhysicsSpace ps) {        
        CollisionShape collisionShape = CollisionShapeFactory.createDynamicMeshShape(geom);
        rbc = new RigidBodyControl(collisionShape, 30);
        rbc.setKinematic(true);
        geom.addControl(rbc);
        ps.add(rbc);
    }
    
    public void setInitLocation(Vector3f t) {
        geom.setLocalTranslation(t);
    }
    
    public Geometry getGeom() {
        return geom;
    }

    public Node getPivot() {
        return pivot;
    }

    public float getRotationSpeed() {
        return rotationSpeed;
    }

    public float getTranslationSpeed() {
        return translationSpeed;
    }

    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public void setTranslationSpeed(float translationSpeed) {
        this.translationSpeed = translationSpeed;
    }
    
}
