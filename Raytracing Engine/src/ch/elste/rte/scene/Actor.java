package ch.elste.rte.scene;

import ch.elste.rte.Ray;

/**
 * Every object in the scene is an actor.
 * 
 * @author Dillon Elste
 */
public abstract class Actor {
	/**
	 * Checks whether this primitive intersects the ray and returns an
	 * {@link Ray.IntersectionInfo} object.
	 * 
	 * @param ray
	 *            the ray to be checked
	 * 
	 * @return an {@link IntersectionInfo} object
	 */
	public abstract Ray.IntersectionInfo intersects(Ray ray);
}
