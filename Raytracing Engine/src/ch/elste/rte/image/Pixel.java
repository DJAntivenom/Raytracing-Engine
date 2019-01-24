package ch.elste.rte.image;

/**
 * This class represent a non translusive pixel with a rgb value.
 * 
 * @author Dillon Elste
 */
public class Pixel {
	public int r, g, b;

	/**
	 * Creates a new pixel with given rgb value
	 * 
	 * @param r
	 *          the red value of the pixel
	 * @param g
	 *          the green value of the pixel
	 * @param b
	 *          the blue value of the pixel
	 * 
	 * @throws IllegalArgumentException
	 *                                  if one of the arguments is greater than 255
	 *                                  or smaller than 0
	 */
	public Pixel(int r, int g, int b) {
		/*
		 * if (r > 255 || r < 0 || g > 255 || g < 0 || b > 255 || b < 0) throw new
		 * IllegalArgumentException("rgb values have to be between 0 and 255");
		 */

		this.r = r;
		this.g = g;
		this.b = b;
	}

	/**
	 * Creates a new pixel with given rgb value
	 * 
	 * @param r
	 *          the red value of the pixel
	 * @param g
	 *          the green value of the pixel
	 * @param b
	 *          the blue value of the pixel
	 * 
	 * @throws IllegalArgumentException
	 *                                  if one of the arguments is greater than 1 or
	 *                                  smaller than 0
	 */
	public Pixel(double r, double g, double b) {
		this((int) Math.round(r * 255), (int) Math.round(g * 255), (int) Math.round(b * 255));
	}

	/**
	 * Creates a new pixel with given gray scale value.
	 * 
	 * @param grayScale
	 *                  the value of this pixel
	 */
	public Pixel(double grayScale) {
		this(grayScale, grayScale, grayScale);
	}

	/**
	 * Returns this Pixel as a 4 byte ABGR value.
	 * 
	 * @return this Pixel as a 4 byte ABGR value
	 */
	public int abgr() {
		return (255 << 24) | (Math.max(0, b) << 16) | (Math.max(0, g) << 8) | Math.max(0, r);
	}

	/**
	 * Adds the specified brightness to this pixel.
	 * 
	 * @param brightness
	 *                   the brightness to be added
	 * 
	 * @return a new pixel with the resulting values
	 * 
	 * @throws IllegalArgumentException
	 *                                  if {@code brightness} is less than 0
	 */
	public Pixel addBrightness(final int brightness) {
		if (brightness < 0)
			throw new IllegalArgumentException("brightness must be greater than 0");

		r = Math.min(255, r + brightness);
		g = Math.min(255, g + brightness);
		b = Math.min(255, b + brightness);

		return new Pixel(Math.min(255, r + brightness), Math.min(255, g + brightness), Math.min(255, b + brightness));
	}

	/**
	 * Adds the specified brightness to this pixel.
	 * 
	 * @param brightness
	 *                   the brightness to be added
	 * 
	 * @return a new pixel with the resulting values
	 * 
	 * @throws IllegalArgumentException
	 *                                  if {@code brightness} is less than 0
	 */
	public Pixel addBrightness(final double brightness) {
		return addBrightness((int) Math.round(brightness * 255));
	}

	public Pixel scale(double d) {
		return new Pixel(d * r, d * g, d * b);
	}

	public Pixel add(double d) {
		return new Pixel(r + d, g + d, b + d);
	}

	public Pixel add(Pixel p) {
		return new Pixel(r + p.r, g + p.g, b + p.b);
	}

	public java.awt.Color getColor() {
		return new java.awt.Color(r, g, b);
	}
}
