package ch.elste.rte.scene.shape.material;

import ch.elste.math.Vector;
import ch.elste.rte.image.Pixel;

public abstract class Material {
	public Pixel diffuseColor;
	public double specular;

	/**
	 * The specular color is the tint given to reflections. In metals this is the
	 * same as the diffuse or base color.
	 */
	public Pixel specularColor;
	public double metallic;
	public double roughness;

	/**
	 * The Schlick reflectance is an approximaton for the fresnel equation. It
	 * returns the specular color of a material at a specific angle between the
	 * light ray and the normal of the surface. It is defined as
	 * <p>
	 * {@code F(l,n)=F0+(1-F0)(1-(l*n))^5}
	 * </p>
	 * <p>
	 * With
	 * <li>F0=specularColor</li>
	 * <li>l =direction of incoming light</li>
	 * <li>n =normal of the surface</li>
	 * </p>
	 * 
	 * @param lightDirection
	 *                       the direction of the light hitting the surface
	 * @param normal
	 *                       the normal of the surface being hit by the ray of light
	 * 
	 * @return the resulting color
	 */
	public Pixel getSchlickReflectance(Vector lightDirection, Vector normal) {
		return specularColor.add(specularColor.scale(-1).add(1).scale(Math.pow(1 - normal.dot(lightDirection), 5)));
	}
}
