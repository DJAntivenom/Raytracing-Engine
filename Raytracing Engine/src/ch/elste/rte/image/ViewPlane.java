package ch.elste.rte.image;

import java.awt.image.BufferedImage;

import ch.elste.math.Vector;
import ch.elste.rte.Ray;

/**
 * Each camera has its view plane. On it the world is being projected to create
 * an image.
 * 
 * @author Dillon Elste
 */
public class ViewPlane {
	private static int ambientLighting;

	private Camera camera;

	private double field_of_view;
	private double field_of_view_factor;

	private int resolution_width;
	private int resolution_height;
	private int resolution;

	private Pixel[] pixels;

	/**
	 * Creates a new view plane with given values.
	 * 
	 * @param camera
	 *            the corresponding camera
	 * @param resolution_width
	 *            the width of the image
	 * @param resolution_height
	 *            the height of the image
	 * @param field_of_view
	 *            the field of view in degree
	 */
	public ViewPlane(Camera camera, int resolution_width, int resolution_height, double field_of_view) {
		this.camera = camera;

		this.field_of_view = Math.toRadians(field_of_view);
		field_of_view_factor = Math.tan(this.field_of_view / 2);

		this.resolution_width = resolution_width;
		this.resolution_height = resolution_height;
		resolution = resolution_width * resolution_height;

		pixels = new Pixel[resolution];

		ambientLighting = 1;
	}

	/**
	 * Creates a new view plane with given values.
	 * 
	 * @param camera
	 *            the corresponding camera
	 * @param field_of_view
	 *            the field of view in degree
	 */
	public ViewPlane(Camera camera, double fieldOfView) {
		this(camera, 400, 400, fieldOfView);
	}

	/**
	 * Creates a new view plane with given values.
	 * 
	 * @param camera
	 *            the corresponding camera
	 */
	public ViewPlane(Camera camera) {
		this(camera, 90);
	}

	/**
	 * Renders an image and saves it to memory.
	 * <p>
	 * If possible {@link #getImage()} should be used, because it just uses the
	 * saved image instead of calculating a new one.
	 * </p>
	 * 
	 * @return returns the rendered image
	 * 
	 * @see #getImage()
	 */
	public BufferedImage render() {
		Ray.IntersectionInfo temp;
		double u, v;
		int x, y;

		for (int i = 0; i < resolution; i++) {
			x = i - resolution_width * (i / resolution_width);
			y = i / resolution_width;

			u = (2d * x / resolution_width - 1) * resolution_width / resolution_height;
			v = 2d * y / resolution_height - 1;

			temp = getCameraRay(u, v).intersects(null);
			if (temp.intersects) {
				pixels[i] = temp.pixel.addBrightness(ambientLighting);
			} else {
				pixels[i] = new Pixel(0);
			}
		}

		return getImage();
	}

	/**
	 * Returns the last rendered image. If there was no image rendered the result is
	 * a black image.
	 * 
	 * @return the last rendered image. If there was no image rendered the result is
	 *         a black image
	 */
	public BufferedImage getImage() {
		BufferedImage image = new BufferedImage(resolution_width, resolution_height, BufferedImage.TYPE_4BYTE_ABGR);

		int x, y;
		for (int i = 0; i < resolution; i++) {
			x = i - resolution_width * (i / resolution_width);
			y = i / resolution_width;

			image.setRGB(x, y, pixels[i].abgr());
		}

		return image;
	}

	/**
	 * Returns the vector/ray pointing towards the point {@code (u, v)} on the view
	 * plane.
	 * 
	 * @param u
	 *            the x coordinate on the view plane in the range of
	 *            {@code [-imageRatio;+imageRatio]}
	 * @param v
	 *            the y coordinate on the view plane in the range of {@code [-1;+1]}
	 * 
	 * @return a normalized vector than points from {@code (0,0)} towards this point
	 */
	public Ray getCameraRay(double u, double v) {
		Vector rayDirection = camera.getTransformationMatrix()
				.mult(new Vector(u * field_of_view_factor, v * field_of_view_factor, 1d).normalize());
		Ray ray = new Ray(camera.position, rayDirection);
		// System.out.println(ray);
		return ray;
	}

	/**
	 * Returns the width of this viewPlane.
	 * 
	 * @return the width of this viewPlane
	 */
	public int getResolutionWidth() {
		return resolution_width;
	}

	/**
	 * Returns the height of this viewPlane.
	 * 
	 * @return the height of this viewPlane
	 */
	public int getResolutionHeight() {
		return resolution_height;
	}

	/**
	 * Returns the ratio of width to height.
	 * 
	 * @return the ratio of width to height
	 */
	public double getImageRatio() {
		return 1d * resolution_width / resolution_height;
	}
}
