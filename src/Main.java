

import com.jme3.app.SimpleApplication;
import com.jme3.input.ChaseCamera;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl;
import com.jme3.scene.control.CameraControl.ControlDirection;
import com.jme3.util.SkyFactory;
import objects.Planet;

/**
 * @author Esteban Alarcon Ceballos y Enrique Arango Lyons
 */
public class Main extends SimpleApplication {
    
    private Planet sun, mercury, venus, earth, mars, jupiter, saturn, uranus, neptune, pluto;
    private Planet[] planets;
    private Spatial spaceship;
    private Node spaceshipNode;
    private FilterPostProcessor fpp;
    private BloomFilter bloom;
    private CameraNode camNode;

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        
        planets = new Planet[9];
        
        Material mat10 = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");        
        mat10.setTexture("DiffuseMap", assetManager.loadTexture("Textures/Sun.jpg"));
        mat10.setTexture("GlowMap", assetManager.loadTexture("Textures/Sun.jpg"));
        mat10.setColor("Specular", ColorRGBA.White);
        mat10.setBoolean("UseAlpha", true);
        sun = new Planet("Sun", 3f, null, mat10, null, 0, 0);
        rootNode.attachChild(sun.getGeom());
        
        fpp=new FilterPostProcessor(assetManager);
        bloom= new BloomFilter(BloomFilter.GlowMode.Objects); 
        bloom.setExposurePower(40f);
        bloom.setBloomIntensity(10f);
        fpp.addFilter(bloom);
        viewPort.addProcessor(fpp);
        
        Material mat1 = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat1.setTexture("DiffuseMap", assetManager.loadTexture("Textures/Mercury.jpg"));
        mat1.setColor("Specular", ColorRGBA.White);
        Node p1 = new Node();
        rootNode.attachChild(p1);
        mercury = new Planet("Mercury", 1f, new Vector3f(7.0f, 0f, -6.0f), mat1, p1, (float) Math.random(), (float) Math.random());
        planets[0] = mercury;
        
        Material mat2 = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat2.setTexture("DiffuseMap", assetManager.loadTexture("Textures/Venus.jpg"));
        mat2.setColor("Specular", ColorRGBA.White);
        Node p2 = new Node();
        rootNode.attachChild(p2);
        venus = new Planet("Venus", 1.5f, new Vector3f(12.0f, 0f, -6.0f), mat2, p2, (float) Math.random(), (float) Math.random());
        planets[1] = venus;
        
        Material mat3 = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat3.setTexture("DiffuseMap", assetManager.loadTexture("Textures/Earth/Color.jpg"));
        mat3.setTexture("ParallaxMap", assetManager.loadTexture("Textures/Earth/Bump.jpg"));
        mat3.setTexture("SpecularMap", assetManager.loadTexture("Textures/Earth/Specular.jpg"));
        Node p3 = new Node();
        rootNode.attachChild(p3);
        earth = new Planet("Earth", 1.6f, new Vector3f(16.0f, 0f, -6.0f), mat3, p3, (float) Math.random(), (float) Math.random());
        planets[2] = earth;
        
        Material mat4 = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat4.setTexture("DiffuseMap", assetManager.loadTexture("Textures/Mars.jpg"));
        mat4.setColor("Specular", ColorRGBA.White);
        Node p4 = new Node();
        rootNode.attachChild(p4);
        mars = new Planet("Mars", 1.4f, new Vector3f(20.0f, 0f, -6.0f), mat4, p4, (float) Math.random(), (float) Math.random());
        planets[3] = mars;
        
        Material mat5 = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat5.setTexture("DiffuseMap", assetManager.loadTexture("Textures/Jupiter.jpg"));
        mat5.setColor("Specular", ColorRGBA.White);        
        Node p5 = new Node();
        rootNode.attachChild(p5);
        jupiter = new Planet("Jupiter", 2.1f, new Vector3f(27.0f, 0f, -6.0f), mat5, p5, (float) Math.random(), (float) Math.random());
        planets[4] = jupiter;
        
        Material mat6 = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat6.setTexture("DiffuseMap", assetManager.loadTexture("Textures/Saturn.jpg"));
        mat6.setColor("Specular", ColorRGBA.White);
        Node p6 = new Node();
        rootNode.attachChild(p6);
        saturn = new Planet("Saturn", 1.9f, new Vector3f(35.0f, 0f, -6.0f), mat6, p6, (float) Math.random(), (float) Math.random());
        planets[5] = saturn;
        
        Material mat7 = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat7.setTexture("DiffuseMap", assetManager.loadTexture("Textures/Uranus.jpg"));
        mat7.setColor("Specular", ColorRGBA.White);
        Node p7 = new Node();
        rootNode.attachChild(p7);
        uranus = new Planet("Uranus", 1.7f, new Vector3f(40.0f, 0f, -6.0f), mat7, p7, (float) Math.random(), (float) Math.random());
        planets[6] = uranus;
        
        Material mat8 = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat8.setTexture("DiffuseMap", assetManager.loadTexture("Textures/Neptune.jpg"));
        mat8.setColor("Specular", ColorRGBA.White);
        Node p8 = new Node();
        rootNode.attachChild(p8);
        neptune = new Planet("Neptune", 1.65f, new Vector3f(47.0f, 0f, -6.0f), mat8, p8, (float) Math.random(), (float) Math.random());
        planets[7] = neptune;
        
        Material mat9 = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat9.setTexture("DiffuseMap", assetManager.loadTexture("Textures/Pluto.jpg"));
        mat9.setColor("Specular", ColorRGBA.White);
        Node p9 = new Node();
        rootNode.attachChild(p9);
        pluto = new Planet("Pluto", 1.65f, new Vector3f(53.0f, 0f, -6.0f), mat9, p9, (float) Math.random(), (float) Math.random());
        planets[8] = pluto;
        
        spaceship = assetManager.loadModel("Models/X-WING.j3o");
        spaceshipNode = new Node("SpaceshipNode");
        spaceship.scale(0.1f);
        spaceship.rotate(0, FastMath.PI, 0);
        spaceshipNode.setLocalTranslation(0, 0, 6f);
        spaceshipNode.attachChild(spaceship);
        rootNode.attachChild(spaceshipNode);        
        
        
        //cam.setLocation(new Vector3f(0, 2f, 8f));
        flyCam.setEnabled(false);
        
        camNode = new CameraNode("CameraNode", cam);
        camNode.setControlDir(ControlDirection.SpatialToCamera);
        spaceshipNode.attachChild(camNode);
        camNode.setLocalTranslation(new Vector3f(0, 1, 4));
        camNode.lookAt(spaceship.getLocalTranslation(), Vector3f.UNIT_Y);
        
        /*ChaseCamera chaseCam = new ChaseCamera(cam, spaceship, inputManager);
        chaseCam.setMaxDistance(4f);
        chaseCam.setDefaultDistance(4f);
        chaseCam.setDefaultVerticalRotation(FastMath.PI / 8);*/
        
        PointLight sunLight = new PointLight();
        sunLight.setColor(ColorRGBA.White);
        sunLight.setPosition(new Vector3f(0f, 0f, 0f));
        sunLight.setRadius(100f);
        rootNode.addLight(sunLight);
        
        rootNode.attachChild(SkyFactory.createSky(assetManager, "Textures/Stars.png", true));
        
        initKeys();
    }
    private void initKeys() {
        inputManager.addMapping("Left",  new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right",   new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Up",  new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Down",  new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("LeftSide",  new KeyTrigger(KeyInput.KEY_Q));
        inputManager.addMapping("RightSide",  new KeyTrigger(KeyInput.KEY_E));
        inputManager.addMapping("Accelerate",   new KeyTrigger(KeyInput.KEY_SPACE));
    
        inputManager.addListener(analogListener, new String[]{"Left", "Right", "Up", "Down", "LeftSide", "RightSide", "Accelerate"});
    }
    
    private AnalogListener analogListener = new AnalogListener() {

        public void onAnalog(String name, float value, float tpf) {
            if (name.equals("Left")) {
                spaceshipNode.rotate(0, tpf, 0);
            }
            if (name.equals("Right")) {
                spaceshipNode.rotate(0, -tpf, 0);
            }
            if (name.equals("Up")) {
                spaceshipNode.rotate(tpf, 0, 0);
            }
            if (name.equals("Down")) {
                spaceshipNode.rotate(-tpf, 0, 0);
            }
            if (name.equals("LeftSide")) {
                spaceshipNode.rotate(0, 0, tpf);
            }
            if (name.equals("RightSide")) {
                spaceshipNode.rotate(0, 0, -tpf);
            }
            if (name.equals("Accelerate")) {
                Vector3f movement = new Vector3f(0, 0, 0);
                spaceshipNode.getLocalRotation().mult(new Vector3f(0, 0, -2*tpf), movement);
                spaceshipNode.move(movement);                
            }
        }
        
    };

    @Override
    public void simpleUpdate(float tpf) {
        for(Planet planet : planets){
            planet.getGeom().rotate(0, 0, planet.getRotationVel()*tpf);
            planet.getPivot().rotate(0, planet.getTraslationVel()*tpf, 0);
            
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
