package objects;

import com.jme3.scene.Node;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;

/**
 *
 * @author Esteban Alarcon Ceballos y Enrique Arango Lyons
 */
public class Planet {

    private Geometry geom;
    private float radius;
    private Node pivot;
    private float rotationVel;
    private float traslationVel;
    
    public Planet(String name, float radius, Vector3f initLocation, 
            Material mat, Node pivot, float rotationVel, float traslationVel) {
        this.pivot = pivot;
        this.rotationVel = rotationVel;
        this.traslationVel = traslationVel;
        geom = new Geometry(name, new Sphere(32, 32, radius));
        geom.setMaterial(mat);
        if(initLocation != null) geom.setLocalTranslation(initLocation);
        geom.rotate(-1.7f, 3.0f, 0);
        if(pivot != null) pivot.attachChild(geom); 
    }
    
    public Geometry getGeom() {
        return geom;
    }

    public Node getPivot() {
        return pivot;
    }

    public float getRotationVel() {
        return rotationVel;
    }

    public float getTraslationVel() {
        return traslationVel;
    }
    
}
