package ch.elste.rte.image;

import java.awt.image.BufferedImage;

import ch.elste.math.Matrix3x3;
import ch.elste.math.Vector;

/**
 * The is an infintesimally small point, from which the world is seen and
 * projected onto a view plane.
 * 
 * @author Dillon Elste
 */
public class Camera {
	public Vector position, direction;
	public ViewPlane viewplane;

	private Matrix3x3 transformationMatrix;

	/**
	 * Creates a new camera with given values.
	 * 
	 * @param position
	 *            the position of the camera
	 * @param width
	 *            the width of the image this camera creates
	 * @param height
	 *            the height of the image this camera creates
	 * @param fieldOfView
	 *            the field of view in degrees
	 */
	public Camera(Vector position, int width, int height, double fieldOfView) {
		this.position = position;
		direction = new Vector(Vector.Z);
		viewplane = new ViewPlane(this, width, height, fieldOfView);

		transformationMatrix = calculateTransformationMatrix();
	}

	/**
	 * Creates a new camera with given values.
	 * 
	 * @param position
	 *            the position of the camera
	 */
	public Camera(Vector position) {
		this.position = position;
		viewplane = new ViewPlane(this);
	}

	/**
	 * Creates a new camera at this position.
	 * 
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 * @param z
	 *            the z coordinate
	 */
	public Camera(double x, double y, double z) {
		this(new Vector(x, y, z));
	}

	/**
	 * Calculates the transformation matrix.
	 * 
	 * @return the transformation matrix
	 */
	private Matrix3x3 calculateTransformationMatrix() {
		Vector directionFlat = new Vector(direction.x, 0, direction.z);
		Vector newX = Vector.Y.cross(directionFlat).normalize();
		Vector newY = direction.cross(newX).normalize();

		// newY is negative because in computer science the origin of the coordinate
		// system is top left, but real space coordinates have an origin at bottom left.
		return Matrix3x3.fromVerticalVectors(newX, newY.negative(), direction);
	}

	/**
	 * Returns the transformation matrix from camera-space to world-space.
	 * 
	 * @return the transformation matrix
	 */
	public Matrix3x3 getTransformationMatrix() {
		return transformationMatrix;
	}

	/**
	 * Set the direction of this camera.
	 * 
	 * @param direction
	 *            the direction of the camera
	 */
	public void setDirection(Vector direction) {
		this.direction = direction.normalize();
		transformationMatrix = calculateTransformationMatrix();
	}

	/**
	 * Get the rendered image of the view plane.
	 * 
	 * @return the rendered image
	 */
	public BufferedImage getImage() {
		return viewplane.render();
	}
}
