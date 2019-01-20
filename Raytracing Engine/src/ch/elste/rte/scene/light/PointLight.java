package ch.elste.rte.scene.light;

import ch.elste.math.Vector;
import ch.elste.rte.Ray;
import ch.elste.rte.Ray.IntersectionInfo;

/**
 * A light that shines equally in all directions.
 * 
 * @author Dillon Elste
 */
public class PointLight extends Light {
	public double brightness;

	/**
	 * Creates a new point light with given values.
	 * 
	 * @param position
	 *            the position
	 * @param brightness
	 *            the brightness
	 */
	public PointLight(Vector position, double brightness) {
		this.position = position;
		this.brightness = brightness;
	}

	@Override
	public IntersectionInfo intersects(Ray ray) {
		return new IntersectionInfo(true, Vector.subtract(position, ray.origin).dot(ray.direction), this, null);
	}

}
