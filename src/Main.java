

import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioNode;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.ui.Picture;
import com.jme3.util.SkyFactory;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import objects.Asteroid;
import objects.Laser;
import objects.Planet;
import objects.Spaceship;

/**
 * @author Esteban Alarcon Ceballos y Enrique Arango Lyons
 */
public class Main extends SimpleApplication implements PhysicsCollisionListener {
    
    public static final int MAX_ASTEROIDS = 30;
    
    private BulletAppState bap;
    private Planet sun, mercury, venus, earth, mars, jupiter, saturn, uranus, neptune, pluto;
    private Planet[] planets;
    private Spaceship spaceship;
    private FilterPostProcessor fpp;
    private BloomFilter bloom;
    private int bloomDirection;
    private AudioNode bgAudio;
    private AudioNode explosionAudio;
    private Node asteroids;
    private Node lasers;
    private Node explosions;
    private float noComet;
    private int lives;
    private int score;
    private boolean up, down, left, right, leftSide, rightSide;
    private boolean moving;
    private BitmapText showText;
    private BitmapText scoreText;
    private Picture livespic[];

    public static void main(String[] args) {
        Main app = new Main();        
        app.start();        
        //app.toggleToFullscreen();
    }
    
    public void toggleToFullscreen() {
        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        DisplayMode[] modes = device.getDisplayModes();
        int i = 0; // note: there are usually several, let's pick the first
        settings.setResolution(modes[i].getWidth(),modes[i].getHeight());
        settings.setFrequency(modes[i].getRefreshRate());
        settings.setDepthBits(modes[i].getBitDepth());
        settings.setFullscreen(device.isFullScreenSupported());
        restart();
    }

    @Override
    public void simpleInitApp() {
        
        bap = new BulletAppState();
        stateManager.attach(bap);
        bap.getPhysicsSpace().setGravity(Vector3f.ZERO);
        bap.getPhysicsSpace().addCollisionListener(this);
        
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        showText = new BitmapText(guiFont, false);
        showText.setSize(50);
        showText.setColor(ColorRGBA.Red);
        showText.setLocalTranslation((settings.getWidth()/2)-200,settings.getHeight()/2, 0);
        
        score = 0;
        scoreText = new BitmapText(guiFont, false);
        scoreText.setSize(guiFont.getCharSet().getRenderedSize()); 
        scoreText.setColor(ColorRGBA.White);
        scoreText.setText("Score: "+score);
        scoreText.setLocalTranslation(settings.getWidth()-200, settings.getHeight()-30, 0);
        guiNode.attachChild(scoreText);
        
        lives = 3;
        livespic = new Picture[lives];
        for(int i=0; i < lives; i++){ 
            livespic[i] = new Picture("Live "+i);
            livespic[i].setImage(assetManager, "Textures/LifeLogo.png", true);
            livespic[i].setWidth(50);
            livespic[i].setHeight(50);
            livespic[i].setPosition(i*50, settings.getHeight()-50);
            guiNode.attachChild(livespic[i]);
        }
        
        planets = new Planet[9];
        
        Material mat10 = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");        
        mat10.setTexture("DiffuseMap", assetManager.loadTexture("Textures/Sun.jpg"));
        mat10.setTexture("GlowMap", assetManager.loadTexture("Textures/Sun.jpg"));
        mat10.setColor("Specular", ColorRGBA.White);
        mat10.setBoolean("UseAlpha", true);
        sun = new Planet("Sun", 5f, mat10);
        sun.registerPhysics(bap.getPhysicsSpace());
        rootNode.attachChild(sun);
        
        Material mat1 = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat1.setTexture("DiffuseMap", assetManager.loadTexture("Textures/Mercury.jpg"));
        mat1.setColor("Specular", ColorRGBA.White);
        mercury = new Planet("Mercury", 2f, mat1);
        mercury.setInitLocation(new Vector3f(16.0f, 0f, -6.0f));
        mercury.setRotationSpeed((float)Math.random());
        mercury.setTranslationSpeed(0.76f);
        mercury.registerPhysics(bap.getPhysicsSpace());
        planets[0] = mercury;
        rootNode.attachChild(mercury);
        
        Material mat2 = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat2.setTexture("DiffuseMap", assetManager.loadTexture("Textures/Venus.jpg"));
        mat2.setColor("Specular", ColorRGBA.White);
        venus = new Planet("Venus", 2.6f, mat2);
        venus.setInitLocation(new Vector3f(23.0f, 0f, -6.0f));
        venus.setRotationSpeed((float)Math.random());
        venus.setTranslationSpeed(0.65f);
        venus.registerPhysics(bap.getPhysicsSpace());
        planets[1] = venus;
        rootNode.attachChild(venus);
        
        Material mat3 = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat3.setTexture("DiffuseMap", assetManager.loadTexture("Textures/Earth/Color.jpg"));
        mat3.setTexture("ParallaxMap", assetManager.loadTexture("Textures/Earth/Bump.jpg"));
        mat3.setTexture("SpecularMap", assetManager.loadTexture("Textures/Earth/Specular.jpg"));
        earth = new Planet("Earth", 2.7f, mat3);
        earth.setInitLocation(new Vector3f(31.0f, 0f, -6.0f));
        earth.setRotationSpeed((float)Math.random());
        earth.setTranslationSpeed(0.6f);
        earth.registerPhysics(bap.getPhysicsSpace());
        planets[2] = earth;
        rootNode.attachChild(earth);
        
        Material mat4 = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat4.setTexture("DiffuseMap", assetManager.loadTexture("Textures/Mars.jpg"));
        mat4.setColor("Specular", ColorRGBA.White);
        mars = new Planet("Mars", 2.5f, mat4);
        mars.setInitLocation(new Vector3f(40.0f, 0f, -6.0f));
        mars.setRotationSpeed((float)Math.random());
        mars.setTranslationSpeed(0.56f);
        mars.registerPhysics(bap.getPhysicsSpace());
        planets[3] = mars;
        rootNode.attachChild(mars);
        
        Material mat5 = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat5.setTexture("DiffuseMap", assetManager.loadTexture("Textures/Jupiter.jpg"));
        mat5.setColor("Specular", ColorRGBA.White);
        jupiter = new Planet("Jupiter", 3.1f, mat5);
        jupiter.setInitLocation(new Vector3f(49.0f, 0f, -6.0f));
        jupiter.setRotationSpeed((float)Math.random());
        jupiter.setTranslationSpeed(0.5f);
        jupiter.registerPhysics(bap.getPhysicsSpace());
        planets[4] = jupiter;
        rootNode.attachChild(jupiter);
        
        Material mat6 = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat6.setTexture("DiffuseMap", assetManager.loadTexture("Textures/Saturn.jpg"));
        mat6.setColor("Specular", ColorRGBA.White);
        saturn = new Planet("Saturn", 2.9f, mat6);
        saturn.setInitLocation(new Vector3f(57.0f, 0f, -6.0f));
        saturn.setRotationSpeed((float)Math.random());
        saturn.setTranslationSpeed(0.44f);
        saturn.registerPhysics(bap.getPhysicsSpace());
        planets[5] = saturn;
        rootNode.attachChild(saturn);
        
        Material mat7 = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat7.setTexture("DiffuseMap", assetManager.loadTexture("Textures/Uranus.jpg"));
        mat7.setColor("Specular", ColorRGBA.White);
        uranus = new Planet("Uranus", 2.8f, mat7);
        uranus.setInitLocation(new Vector3f(65.0f, 0f, -6.0f));
        uranus.setRotationSpeed((float)Math.random());
        uranus.setTranslationSpeed(0.4f);
        uranus.registerPhysics(bap.getPhysicsSpace());
        planets[6] = uranus;
        rootNode.attachChild(uranus);
        
        Material mat8 = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat8.setTexture("DiffuseMap", assetManager.loadTexture("Textures/Neptune.jpg"));
        mat8.setColor("Specular", ColorRGBA.White);
        neptune = new Planet("Neptune", 2.65f, mat8);
        neptune.setInitLocation(new Vector3f(75.0f, 0f, -6.0f));
        neptune.setRotationSpeed((float)Math.random());
        neptune.setTranslationSpeed(0.34f);
        neptune.registerPhysics(bap.getPhysicsSpace());
        planets[7] = neptune;
        rootNode.attachChild(neptune);
        
        Material mat9 = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat9.setTexture("DiffuseMap", assetManager.loadTexture("Textures/Pluto.jpg"));
        mat9.setColor("Specular", ColorRGBA.White);
        pluto = new Planet("Pluto", 1.5f, mat9);
        pluto.setInitLocation(new Vector3f(82.0f, 0f, -6.0f));
        pluto.setRotationSpeed((float)Math.random());
        pluto.setTranslationSpeed(0.2f);
        pluto.registerPhysics(bap.getPhysicsSpace());
        planets[8] = pluto;
        rootNode.attachChild(pluto);
        
        spaceship = new Spaceship("Spaceship", assetManager.loadModel("Models/X-WING/X-WING.j3o"));
        spaceship.addTurbines(new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md"), 
                    assetManager.loadTexture("Effects/Explosion/flame.png"));
        spaceship.setLocalTranslation(0, 20, 40);
        spaceship.getModel().scale(0.1f);
        spaceship.registerPhysics(bap.getPhysicsSpace());
        
        rootNode.attachChild(spaceship);
        
        Material laserMaterial = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        laserMaterial.setColor("GlowColor", ColorRGBA.Green);
        laserMaterial.setColor("Ambient", ColorRGBA.Green);
        laserMaterial.setColor("Diffuse", ColorRGBA.Green);        
        laserMaterial.setBoolean("UseMaterialColors", true);
        spaceship.setLaserMaterial(laserMaterial);
        
        lasers = new Node("Lasers");
        rootNode.attachChild(lasers);
        
        flyCam.setEnabled(false);
        
        cam.setLocation(spaceship.getRear().getWorldTranslation());
        cam.lookAt(spaceship.getFront().getWorldTranslation(), Vector3f.UNIT_Y);
        
        PointLight sunLight = new PointLight();
        sunLight.setColor(ColorRGBA.White);
        sunLight.setPosition(new Vector3f(0f, 0f, 0f));
        sunLight.setRadius(100f);
        rootNode.addLight(sunLight);
        
        AmbientLight ambient = new AmbientLight();
        rootNode.addLight(ambient);
        
        fpp = new FilterPostProcessor(assetManager);
        bloom = new BloomFilter(BloomFilter.GlowMode.Objects); 
        bloom.setExposurePower(30f);
        bloom.setBloomIntensity(1f);
        fpp.addFilter(bloom);
        viewPort.addProcessor(fpp);
        
        rootNode.attachChild(SkyFactory.createSky(assetManager, "Textures/Stars5.jpeg", true));
        
        bloomDirection = 1;
        noComet = 1;
        moving = up = down = left = right = leftSide = rightSide = false;
        
        asteroids = new Node("Asteroids");
        rootNode.attachChild(asteroids);
        
        explosions = new Node("Explosions");
        rootNode.attachChild(explosions);
        
        initKeys();
        initAudio();      
        
    }
    
    private void initAudio() {    
        bgAudio = new AudioNode(assetManager, "Sound/Background.wav", false);
        bgAudio.setLooping(true);
        bgAudio.setVolume(3);
        rootNode.attachChild(bgAudio);
        bgAudio.play();
        
        explosionAudio = new AudioNode(assetManager, "Sound/Explosion.wav", false);
        explosionAudio.setLooping(false);
        explosionAudio.setVolume(0.5f);
        rootNode.attachChild(explosionAudio);     
        
        AudioNode accelAudio = new AudioNode(assetManager, "Sound/Fire4.wav", false);
        accelAudio.setLooping(true);
        accelAudio.setVolume(10);
        spaceship.initAudio("Accelerate", accelAudio);
        
        AudioNode laserAudio = new AudioNode(assetManager, "Sound/Laser.wav", false);
        laserAudio.setLooping(false);
        laserAudio.setVolume(1);
        spaceship.initAudio("Laser", laserAudio);
    }
    
    private void initKeys() {
        inputManager.addMapping("Left",  new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("LeftSide", new KeyTrigger(KeyInput.KEY_Q));
        inputManager.addMapping("RightSide", new KeyTrigger(KeyInput.KEY_E));
        inputManager.addMapping("Accelerate", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("Shoot", new KeyTrigger(KeyInput.KEY_RSHIFT));
        
        inputManager.addListener(actionListener, new String[]{"Left", "Right", "Up", "Down", "LeftSide", "RightSide", "Accelerate", "Shoot"});
    }
    
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean isPressed, float tpf) {
            if (name.equals("Accelerate")) {
                if (isPressed) {
                    for (Spatial child : spaceship.getTurbines().getChildren()) {
                        ParticleEmitter fire = (ParticleEmitter) child;
                        fire.setStartSize(0.2f);
                        fire.setEndSize(0.1f);
                    }
                    spaceship.getSound("Accelerate").play();
                    moving = true;
                } else {
                    for (Spatial child : spaceship.getTurbines().getChildren()) {
                        ParticleEmitter fire = (ParticleEmitter) child;
                        fire.setStartSize(0.1f);
                        fire.setEndSize(0.05f);
                    }
                    spaceship.getSound("Accelerate").stop();
                    moving = false;
                }
            }
            
            if (name.equals("Shoot") && isPressed) {
                Laser[] lasersShot = spaceship.shoot();
                for (Laser laser : lasersShot) {
                    laser.registerPhysics(bap.getPhysicsSpace());
                    lasers.attachChild(laser);
                }
                spaceship.getSound("Laser").playInstance();
            }
            
            if (name.equals("Left")) {
                left = isPressed;
            }
            if (name.equals("Right")) {
                right = isPressed;
            }
            if (name.equals("Up")) {
                up = isPressed;
            }
            if (name.equals("Down")) {
                down = isPressed;
            }
            if (name.equals("LeftSide")) {
                leftSide = isPressed;
            }
            if (name.equals("RightSide")) {
                rightSide = isPressed;
            }
        }
        
    };
    
    public void generateRandomAsteroid() {
        Material asteroidMaterial = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        asteroidMaterial.setTexture("DiffuseMap", assetManager.loadTexture("Textures/Asteroid/Solid.png"));
        asteroidMaterial.setTexture("NormalMap", assetManager.loadTexture("Textures/Asteroid/Normal.png"));
        
        Asteroid asteroid = new Asteroid("Asteroid", assetManager.loadModel("Models/Asteroid.j3o"), asteroidMaterial);
        
        asteroid.getModel().scale((float)Math.random() / 2);
        
        if (Math.random() * noComet < 0.05) {    
            asteroid.getModel().scale((float)Math.random() / 2);
            asteroid.addTrail(new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md"), 
                    assetManager.loadTexture("Effects/Explosion/flame.png"));
            asteroid.setSpeed((int)(Math.random() * 4) + 10);
            noComet = 1;
        } else {
            asteroid.setSpeed((int)(Math.random() * 4) + 4);  
            noComet -= 0.05;
        }
        
        rootNode.attachChild(asteroid);
        
        float theta = (float) (2 * FastMath.PI * Math.random());
        float phi = (float)(2 * FastMath.PI * Math.random());
        float r = 120;
        
        float x = r * FastMath.cos(theta) * FastMath.sin(phi);
        float y = r * FastMath.sin(theta) * FastMath.cos(phi);
        float z = r * FastMath.cos(phi);
        
        asteroid.setLocalTranslation(x, y, z);
        asteroid.setDirection(planets[2].getGeom().getWorldTranslation().subtract(asteroid.getLocalTranslation()).normalize());
        asteroid.setRotation(new Vector3f((float)Math.random(), (float)Math.random(), (float)Math.random()).normalize());
        asteroid.registerPhysics(bap.getPhysicsSpace());
        asteroids.attachChild(asteroid);
    }
    
    public void generateExplosion(Vector3f position) {        
        ParticleEmitter explosion = new ParticleEmitter("Explosion", ParticleMesh.Type.Triangle, 10);
        Material mat_red = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        mat_red.setTexture("Texture", assetManager.loadTexture("Effects/Explosion/flame.png"));
        explosion.setMaterial(mat_red);
        explosion.setImagesX(2); 
        explosion.setImagesY(2); // 2x2 texture animation
        explosion.setEndColor(  new ColorRGBA(1f, 0f, 0f, 1f));   // red
        explosion.setStartColor(new ColorRGBA(1f, 1f, 0f, 0.5f)); // yellow
        explosion.getParticleInfluencer().setInitialVelocity(new Vector3f(0,0,0));
        explosion.setGravity(0,0,0);
        explosion.setStartSize(2);
        explosion.setEndSize(4);
        explosion.setLowLife(1);
        explosion.setHighLife(2);
        explosion.setParticlesPerSec(0);
        explosion.getParticleInfluencer().setVelocityVariation(4);
        explosion.setLocalTranslation(position);
        explosions.attachChild(explosion);
        explosion.emitAllParticles();
        
        explosionAudio.playInstance();
    }
    
    public void generateDebris(Vector3f position) {
        ParticleEmitter explosion = new ParticleEmitter("Debris", ParticleMesh.Type.Triangle, 6);
        Material mat_red = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        mat_red.setTexture("Texture", assetManager.loadTexture("Effects/Explosion/Debris.png"));
        explosion.setMaterial(mat_red);
        explosion.setImagesX(3);
        explosion.setImagesY(3);
        explosion.setRotateSpeed(4);
        explosion.setSelectRandomImage(true);
        explosion.getParticleInfluencer().setInitialVelocity(new Vector3f(0,0.4f,0));
        explosion.setStartSize(1);
        explosion.setEndSize(1);
        explosion.setStartColor(ColorRGBA.Gray);
        explosion.setEndColor(ColorRGBA.Gray);
        explosion.setGravity(0,0,0);
        explosion.setLowLife(6);
        explosion.setHighLife(8);
        explosion.setParticlesPerSec(0);
        explosion.getParticleInfluencer().setVelocityVariation(10);
        explosion.setLocalTranslation(position);
        explosions.attachChild(explosion);
        explosion.emitAllParticles();
    }

    @Override
    public void simpleUpdate(float tpf) {
        for(Planet planet : planets){
            planet.getGeom().rotate(0, 0, planet.getRotationSpeed()*tpf);
            planet.getPivot().rotate(0, planet.getTranslationSpeed()*tpf, 0);                
        }
        
        for (Spatial spatial : explosions.getChildren()) {
            ParticleEmitter explosion = (ParticleEmitter) spatial;
            if (explosion.getNumVisibleParticles() < 1) {
                explosion.removeFromParent();
            }
        }
            
        Vector3f rotation = new Vector3f(0, 0, 0);
        if (left) {
            rotation.addLocal(0, 1, 0);
        }
        if (right) {
            rotation.addLocal(0, -1, 0);
        }
        if (up) {
            rotation.addLocal(1, 0, 0);
        }
        if (down) {
            rotation.addLocal(-1, 0, 0);
        }
        if (leftSide) {
            rotation.addLocal(0, 0, 1);
        }
        if (rightSide) {
            rotation.addLocal(0, 0, -1);
        }
        
        Vector3f transformed = new Vector3f(0,0,0);
        spaceship.getLocalRotation().mult(rotation, transformed);
        spaceship.getControl().setAngularVelocity(transformed);
        
        Vector3f movement = new Vector3f(0, 0, 0);        
        if (moving) {
            spaceship.getWorldRotation().mult(new Vector3f(0, 0, -6), movement);
        }
        spaceship.getControl().setLinearVelocity(movement);
        
        bloom.setBloomIntensity(bloom.getBloomIntensity() + (bloomDirection * tpf / 8));
        if (bloom.getBloomIntensity() > 4) {
            bloomDirection = -1;
        }
        if (bloom.getBloomIntensity() < 2) {
            bloomDirection = 1;
        }
        
        Vector3f direction = spaceship.getRear().getWorldTranslation().subtract(cam.getLocation());
        float magnitude = direction.length();
        if (magnitude > 0) {
            cam.setLocation(cam.getLocation().add(direction.normalize().mult(tpf * magnitude * magnitude / 2)));
        }
        cam.lookAt(spaceship.getFront().getWorldTranslation(), Vector3f.UNIT_Y);
        
        for (Spatial spatial : asteroids.getChildren()) {
            if (spatial.getWorldTranslation().subtract(Vector3f.ZERO).length() > 200) {
                spatial.removeFromParent();
            }
        }
        
        if (Math.random() < 0.01 && asteroids.getChildren().size() < MAX_ASTEROIDS) {
            generateRandomAsteroid();
        }
        
        for (Spatial spatial : lasers.getChildren()) {
            Laser laser = (Laser) spatial;
            if (laser.getWorldTranslation().subtract(Vector3f.ZERO).length() > 200) {
                bap.getPhysicsSpace().remove(laser.getControl());
                laser.removeFromParent();
                continue;
            }
            laser.move(laser.getDirection().mult(laser.getSpeed()));
        }
        
        scoreText.setText("Score: " + score);
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    public void collision(PhysicsCollisionEvent event) {
        if(event.getNodeA().getName().equals("Spaceship")) {
            if(event.getNodeB().getName().equals("Sun")) {
                lives -= 3;
            } else {
                lives--;                
            }
            if(lives >= 0) {
                guiNode.detachChild(livespic[lives]);
                Vector3f movement = new Vector3f(0, 0, 0);
                spaceship.getWorldRotation().mult(new Vector3f(0, 0, 10), movement);
                spaceship.getControl().setLinearVelocity(movement);
            } else {
                speed = 0.2f;
                generateExplosion(spaceship.getWorldTranslation());
                generateDebris(spaceship.getWorldTranslation());
                bap.getPhysicsSpace().remove(spaceship.getControl());
                spaceship.removeFromParent();
                lives = 0;
                guiNode.detachAllChildren();
                guiNode.detachChild(showText);
                showText.setText("GAME OVER");
                guiNode.attachChild(showText);
            }
        } else if(event.getNodeB().getName().equals("Spaceship")) {
            if(event.getNodeA().getName().equals("Sun")) {
                lives -= 3;
            } else {
                lives--;
            }
            if(lives >= 0) {
                guiNode.detachChild(livespic[lives]);
                Vector3f movement = new Vector3f(0, 0, 0);
                spaceship.getWorldRotation().mult(new Vector3f(0, 0, 10), movement);
                spaceship.getControl().setLinearVelocity(movement);
            } else {
                speed = 0.2f;
                generateExplosion(spaceship.getWorldTranslation());
                generateDebris(spaceship.getWorldTranslation());
                bap.getPhysicsSpace().remove(spaceship.getControl());
                spaceship.removeFromParent();
                lives = 0;
                guiNode.detachAllChildren();
                guiNode.detachChild(showText);
                showText.setText("GAME OVER");
                guiNode.attachChild(showText);
            }
        } else if (event.getNodeA().getName().equals("Laser")) {
            if(event.getNodeB().getName().equals("Asteroid")) {
                final Asteroid asteroid = (Asteroid)event.getNodeB();
                if(asteroid.isComet()) {
                    score += 500;
                } else {
                    score += 100;
                }
                generateDebris(asteroid.getWorldTranslation());
                bap.getPhysicsSpace().remove(asteroid.getControl());
                asteroid.removeFromParent();
            }
            Laser laser = (Laser)event.getNodeA();
            generateExplosion(laser.getWorldTranslation());
            bap.getPhysicsSpace().remove(laser.getControl());
            laser.removeFromParent();
        } else if (event.getNodeB().getName().equals("Laser")) {
            if(event.getNodeA().getName().equals("Asteroid")) {
                final Asteroid asteroid = (Asteroid)event.getNodeA();
                if(asteroid.isComet()) {
                    score += 500;
                } else {
                    score += 100;
                }
                generateDebris(asteroid.getWorldTranslation());
                bap.getPhysicsSpace().remove(asteroid.getControl());
                asteroid.removeFromParent();
            }
            Laser laser = (Laser)event.getNodeB();
            generateExplosion(laser.getWorldTranslation());
            bap.getPhysicsSpace().remove(laser.getControl());
            laser.removeFromParent();
        }
    }
}
