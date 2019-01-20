package ch.elste.rte;

import ch.elste.math.Vector;
import ch.elste.rte.image.Pixel;
import ch.elste.rte.scene.Actor;
import ch.elste.rte.scene.Scene;
import ch.elste.rte.scene.light.Light;
import ch.elste.rte.scene.shape.Primitive;

/**
 * A ray is a beam of light with an origin and a direction.
 * 
 * @author Dillon Elste
 */
public class Ray {
	public Vector origin;
	public Vector direction;

	/**
	 * Creates a new ray with given values.
	 * 
	 * @param origin
	 *            the origin of the ray
	 * @param direction
	 *            the direction of the ray
	 */
	public Ray(Vector origin, Vector direction) {
		this.origin = new Vector(origin);
		this.direction = new Vector(direction);
	}

	/**
	 * Checks if this ray intersects with an actor on its way through space and
	 * returns {@link IntersectionInfo information} about the intersection.
	 * 
	 * @return an {@link IntersectionInfo} object.
	 * 
	 * @see IntersectionInfo
	 */
	public IntersectionInfo intersects(Primitive skip) {
		IntersectionInfo temp = null;
		IntersectionInfo nearestInfo = new IntersectionInfo(false, Double.POSITIVE_INFINITY, null, null);

		for (int i = 0; i < Scene.numberOfPrimitives(); i++) {
			if (Scene.getPrimitive(i) != skip) {
				temp = Scene.getPrimitive(i).intersects(this);
				if (temp.intersects && temp.distance < nearestInfo.distance) {
					nearestInfo = temp;
				}
			}
		}

		return nearestInfo;
	}

	public IntersectionInfo intersects(Light l, Primitive skip) {
		if (intersects(skip).intersects)
			return new IntersectionInfo(false, Double.POSITIVE_INFINITY, null, null);
		else
			return new IntersectionInfo(true, l.position.minus(origin).dot(direction), l, null);
	}

	/**
	 * An structure holding information about intersections.
	 * 
	 * @author Dillon Elste
	 * 
	 * @see #intersects
	 * @see #distance
	 * @see #intersectionObject
	 * @see #pixel
	 */
	public static class IntersectionInfo {
		public IntersectionInfo(boolean intersects, double distance, Actor intersectionObject, Pixel pixel) {
			this.intersects = intersects;
			this.distance = distance;
			this.intersectionObject = intersectionObject;
			this.pixel = pixel;
		}

		/**
		 * <li>true, if the ray intersects</li>
		 * <li>false, if the ray doesn't intersect</li>
		 */
		public boolean intersects;

		/**
		 * The distance between the origin and the intersection point.
		 */
		public double distance;

		/**
		 * <li>if {@link #intersects} is true -> the actor the ray intersects with</li>
		 * <li>if {@link #intersects} is false -> null</li>
		 */
		public Actor intersectionObject;

		/**
		 * The pixel holding the color information of the intersection point or null if
		 * there is no intersection.
		 */
		public Pixel pixel;
	}

	@Override
	public String toString() {
		return String.format("This Ray %s is defined as %n origin: %s%n direction: %s", super.toString(),
				origin.toString(), direction.toString());
	}
}
