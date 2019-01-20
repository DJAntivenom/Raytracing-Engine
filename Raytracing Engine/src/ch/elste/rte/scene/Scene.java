package ch.elste.rte.scene;

import java.util.ArrayList;

import ch.elste.rte.scene.light.Light;
import ch.elste.rte.scene.shape.Primitive;

/**
 * The scene holds all actors, lights and cameras.
 * 
 * @author Dillon Elste
 */
public class Scene {
	/**
	 * A list holding all actors in the scene.
	 */
	private static ArrayList<Actor> actors = new ArrayList<>();

	private static ArrayList<Light> lights = new ArrayList<>();
	private static ArrayList<Primitive> objects = new ArrayList<>();

	/**
	 * Add a new actor to the scene.
	 * 
	 * @param p
	 *            the actor to add
	 */
	private static void addActor(Actor p) {
		actors.add(p);
	}

	/**
	 * Add a new primitive to the scene
	 * 
	 * @param p
	 *            the primitive to add
	 */
	public static void addPrimitive(Primitive p) {
		objects.add(p);
		addActor(p);
	}

	/**
	 * Add a new light to the scene
	 * 
	 * @param l
	 *            the light to add
	 */
	public static void addLight(Light l) {
		lights.add(l);
		addActor(l);
	}

	/**
	 * Get the amount of primitives in the scene.
	 * 
	 * @return the amount of primitives in the scene
	 */
	public static int numberOfPrimitives() {
		return objects.size();
	}
	
	/**
	 * Get the amount of actors in the scene.
	 * 
	 * @return the amount of actors in the scene
	 */
	public static int numberOfActors() {
		return actors.size();
	}

	/**
	 * Get the primitive at {@code index}.
	 * 
	 * @param index
	 *            index of the element to return
	 * 
	 * @return the primitive at given index
	 */
	public static Primitive getPrimitive(int index) {
		return objects.get(index);
	}
	
	/**
	 * Get the light at {@code index}.
	 * 
	 * @param index
	 *            index of the element to return
	 * 
	 * @return the light at given index
	 */
	public static Light getLight(int index) {
		return lights.get(index);
	}
}
