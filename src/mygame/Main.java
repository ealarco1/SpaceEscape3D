package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;

/**
 * @author Enrique Arango Lyons
 */
public class Main extends SimpleApplication {
    
    private Geometry sun, mercury, venus, earth, mars, jupiter, saturn, uranus, neptune, pluto;
    private Geometry[] planets;
    private float time;
    private ColorRGBA prevColor, nextColor;
    private Node pivot;

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        Sphere s = new Sphere(32, 32, 3f);
        Sphere s1 = new Sphere(32, 32, 1.5f);
        Sphere s2 = new Sphere(32, 32, 1f);
        Sphere s3 = new Sphere(32, 32, 1.9f);
        Sphere s4 = new Sphere(32, 32, 1.7f);
        Sphere s5 = new Sphere(32, 32, 0.6f);
        
        planets = new Geometry[9];
        
        pivot = new Node("pivot");
        rootNode.attachChild(pivot);
        
        sun = new Geometry("Sun", s);
        Material mat10 = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat10.setTexture("DiffuseMap", assetManager.loadTexture("Textures/Sun.jpg"));
        mat10.setColor("Specular", ColorRGBA.White);
        mat10.setBoolean("UseAlpha", true);
        mat10.setColor("GlowColor", ColorRGBA.Yellow);
        sun.setMaterial(mat10);
        rootNode.attachChild(sun);
        
        mercury = new Geometry("Mercury", s2);
        Material mat1 = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat1.setTexture("DiffuseMap", assetManager.loadTexture("Textures/Mercury.jpg"));
        mat1.setColor("Specular", ColorRGBA.White);
        mercury.setLocalTranslation(new Vector3f(7.0f, 0f, -6.0f));
        mercury.setMaterial(mat1);
        mercury.rotate(-1.7f, 3.0f, 0);
        pivot.attachChild(mercury);
        planets[0] = mercury;
        
        venus = new Geometry("Venus", s1);
        Material mat2 = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat2.setTexture("DiffuseMap", assetManager.loadTexture("Textures/Venus.jpg"));
        mat2.setColor("Specular", ColorRGBA.White);
        venus.setLocalTranslation(new Vector3f(12.0f, 0f, -6.0f));
        venus.setMaterial(mat2);
        venus.rotate(-1.7f, 3.0f, 0);
        pivot.attachChild(venus);
        planets[1] = venus;
        
        earth = new Geometry("Earth", s1);
        Material mat3 = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat3.setTexture("DiffuseMap", assetManager.loadTexture("Textures/Earth/Color.jpg"));
        mat3.setTexture("ParallaxMap", assetManager.loadTexture("Textures/Earth/Bump.jpg"));
        mat3.setTexture("SpecularMap", assetManager.loadTexture("Textures/Earth/Specular.jpg"));
        earth.setLocalTranslation(new Vector3f(16.0f, 0f, -6.0f));
        earth.setMaterial(mat3);
        earth.rotate(-1.7f, 3.0f, 0);
        pivot.attachChild(earth);
        planets[2] = earth;
        
        mars = new Geometry("Mars", s2);
        Material mat4 = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat4.setTexture("DiffuseMap", assetManager.loadTexture("Textures/Mars.jpg"));
        mat4.setColor("Specular", ColorRGBA.White);
        mars.setLocalTranslation(new Vector3f(20.0f, 0f, -6.0f));
        mars.setMaterial(mat4);
        mars.rotate(-1.7f, 3.0f, 0);
        pivot.attachChild(mars);
        planets[3] = mars;
        
        jupiter = new Geometry("Jupiter", s3);
        Material mat5 = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat5.setTexture("DiffuseMap", assetManager.loadTexture("Textures/Jupiter.jpg"));
        mat5.setColor("Specular", ColorRGBA.White);        
        jupiter.setLocalTranslation(new Vector3f(27.0f, 0f, -6.0f));
        jupiter.setMaterial(mat5);
        jupiter.rotate(-1.7f, 3.0f, 0);
        pivot.attachChild(jupiter);
        planets[4] = jupiter;
        
        saturn = new Geometry("Saturn", s4);
        Material mat6 = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat6.setTexture("DiffuseMap", assetManager.loadTexture("Textures/Saturn.jpg"));
        mat6.setColor("Specular", ColorRGBA.White);
        saturn.setLocalTranslation(new Vector3f(35.0f, 0f, -6.0f));
        saturn.setMaterial(mat6);
        saturn.rotate(-1.7f, 3.0f, 0);
        pivot.attachChild(saturn);
        planets[5] = saturn;
        
        uranus = new Geometry("Uranus", s2);
        Material mat7 = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat7.setTexture("DiffuseMap", assetManager.loadTexture("Textures/Uranus.jpg"));
        mat7.setColor("Specular", ColorRGBA.White);
        uranus.setLocalTranslation(new Vector3f(40.0f, 0f, -6.0f));
        uranus.setMaterial(mat7);
        uranus.rotate(-1.7f, 3.0f, 0);
        pivot.attachChild(uranus);
        planets[6] = uranus;
        
        neptune = new Geometry("Neptune", s1);
        Material mat8 = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat8.setTexture("DiffuseMap", assetManager.loadTexture("Textures/Neptune.jpg"));
        mat8.setColor("Specular", ColorRGBA.White);
        neptune.setLocalTranslation(new Vector3f(47.0f, 0f, -6.0f));
        neptune.setMaterial(mat8);
        neptune.rotate(-1.7f, 3.0f, 0);
        pivot.attachChild(neptune);
        planets[7] = neptune;
        
        pluto = new Geometry("Pluto", s5);
        Material mat9 = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat9.setTexture("DiffuseMap", assetManager.loadTexture("Textures/Pluto.jpg"));
        mat9.setColor("Specular", ColorRGBA.White);
        pluto.setLocalTranslation(new Vector3f(53.0f, 0f, -6.0f));
        pluto.setMaterial(mat9);
        pluto.rotate(-1.7f, 3.0f, 0);
        pivot.attachChild(pluto);
        planets[8] = pluto;
        
        /*
        
        DirectionalLight sunLight = new DirectionalLight();
        sunLight.setDirection(new Vector3f(0f, 0f, -1.0f));
        rootNode.addLight(sunLight);
          */
         
        PointLight sunLight = new PointLight();
        sunLight.setColor(ColorRGBA.White);
        sunLight.setPosition(new Vector3f(0f, 0f, 0f));
        sunLight.setRadius(40f);
        rootNode.addLight(sunLight);
        
        
        prevColor = new ColorRGBA(1,0,0,0);
        nextColor = new ColorRGBA(0.2f,0,0,0);
        
        time = 0;
    }

    @Override
    public void simpleUpdate(float tpf) {
        for(Geometry planet : planets){
            planet.rotate(0, 0, (float) Math.random()*tpf);
        }
        /*
        mercury.rotate(0, 0, (float) Math.random()*tpf);
        venus.rotate(0, 0, (float) Math.random()*tpf);
        earth.rotate(0, 0, (float) Math.random()*tpf);
        mars.rotate(0, 0, (float) Math.random()*tpf);
        jupiter.rotate(0, 0, (float) Math.random()*tpf);
        saturn.rotate(0, 0, (float) Math.random()*tpf);
        uranus.rotate(0, 0, (float) Math.random()*tpf);
        neptune.rotate(0, 0, (float) Math.random()*tpf);
        pluto.rotate(0, 0, (float) Math.random()*tpf);
         * 
         */
        pivot.rotate(0, (float) Math.random()*tpf, 0);

        time += tpf;
 
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
