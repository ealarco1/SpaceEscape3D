
package objects;

import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Cylinder;

public class Laser extends Node {
    
    private int speed;
    private Vector3f direction;
    
    public Laser(String name, Material material) {
        super(name);
        
        Geometry laser1 = new Geometry("LaserShape", new Cylinder(32, 32, 0.01f, 1));
        laser1.setMaterial(material);
        Geometry laser2 = laser1.clone();
        
        attachChild(laser1);
        laser1.setLocalTranslation(new Vector3f(-0.4f, 0, 0));
        
        attachChild(laser2);
        laser2.setLocalTranslation(new Vector3f(0.4f, 0, 0));
        
        direction = Vector3f.ZERO;
        speed = 1;
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
