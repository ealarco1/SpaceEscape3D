
package objects;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Cylinder;

public class Laser extends Geometry {
    
    private int speed;
    private Vector3f direction;
    private GhostControl gc;
    
    public Laser(String name, Material material) {        
        super(name, new Cylinder(32, 32, 0.01f, 1));
        setMaterial(material);
        
        
        direction = Vector3f.ZERO;
        speed = 1;
    }
    
    public void registerPhysics(PhysicsSpace ps) {
        CollisionShape collisionShape = CollisionShapeFactory.createDynamicMeshShape(this);
        gc = new GhostControl(collisionShape);
        addControl(gc);
        ps.add(gc);
    }

    public GhostControl getControl() {
        return gc;
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
    
}
