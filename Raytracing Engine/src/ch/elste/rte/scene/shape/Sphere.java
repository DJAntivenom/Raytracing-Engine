package ch.elste.rte.scene.shape;

import ch.elste.math.Vector;
import ch.elste.rte.Ray;
import ch.elste.rte.Ray.IntersectionInfo;
import ch.elste.rte.image.Pixel;
import ch.elste.rte.scene.Scene;

/**
 * A simple sphere.
 * 
 * @author Dillon Elste
 */
public class Sphere extends Primitive {
	private Vector center;
	private double radius;
	private double radiusSquared;

	/**
	 * Creates a new sphere object with given values.
	 * 
	 * @param center
	 *            the center of the sphere
	 * @param radius
	 *            the radius of the sphere
	 */
	public Sphere(Vector center, double radius) {
		super();

		this.center = center;
		this.radius = radius;
		radiusSquared = radius * radius;
	}

	@Override
	public IntersectionInfo intersects(Ray ray) {
		// This web site has a good explanation of what's happening here.
		// https://www.scratchapixel.com/lessons/3d-basic-rendering/minimal-ray-tracer-rendering-simple-shapes/ray-sphere-intersection

		// The vector from the origin of the ray to the center of the sphere.
		Vector rOtoC = center.minus(ray.origin);

		// The distance between the origin of the ray and the point nearest to the
		// center of the sphere, but still on the ray.
		double dco = rOtoC.dot(ray.direction);

		// The distance squared between the center of the sphere and the ray. It is
		// calculated using the pythagorean theorem.
		double dSquared = rOtoC.dot(rOtoC) - dco * dco;

		// if the distance between the center of the sphere and the ray is greater than
		// the radius of the sphere, there is no intersection.
		if (dSquared > radiusSquared)
			return new IntersectionInfo(false, Double.POSITIVE_INFINITY, null, null);

		// the distance between the surface of the sphere and the nearest point to the
		// center of the sphere, but still on the ray.
		double dcs = Math.sqrt(radiusSquared - dSquared);

		double firstIntersection = dco - dcs;
		double secondIntersection = dco + dcs;

		if (firstIntersection < 0) {
			if (secondIntersection < 0)
				return new IntersectionInfo(false, Double.POSITIVE_INFINITY, null, null);
			else
				return getIntersectionInfo(ray, secondIntersection);
		} else {
			return getIntersectionInfo(ray, firstIntersection);
		}
	}

	private IntersectionInfo getIntersectionInfo(final Ray ray, final double distance) {
		Vector intersectionPoint = ray.origin.plus(ray.direction.scale(distance));
		Ray toLight = new Ray(intersectionPoint, Scene.getLight(0).position.minus(intersectionPoint).normalize());
		IntersectionInfo lightRayInfo = toLight.intersects(Scene.getLight(0), this);
		if (lightRayInfo.intersects) {
			return new IntersectionInfo(true, distance, this,
					new Pixel(Math.max(0, toLight.direction.dot(getNormal(intersectionPoint)))));
		} else {
			return new IntersectionInfo(true, distance, this, new Pixel(0));
		}
	}

	/**
	 * Returns the normal vector at the specified point on the surface.
	 * 
	 * @param pointOnSurface
	 *            the point on the surface
	 * 
	 * @return the normal vector
	 */
	public Vector getNormal(final Vector pointOnSurface) {
		return pointOnSurface.minus(center).scale(1d / radius);
	}
}
