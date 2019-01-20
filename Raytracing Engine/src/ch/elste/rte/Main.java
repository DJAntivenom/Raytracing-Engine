package ch.elste.rte;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ch.elste.math.Vector;
import ch.elste.rte.image.Camera;
import ch.elste.rte.scene.Scene;
import ch.elste.rte.scene.light.PointLight;
import ch.elste.rte.scene.shape.Sphere;

public class Main {

	static JPanel panel = new JPanel();
	
	public static void main(String[] args) {
		Scene.addLight(new PointLight(new Vector(5, 3, 0), 100));
		Scene.addPrimitive(new Sphere(new Vector(0, 0, 3), 1));
		Scene.addPrimitive(new Sphere(new Vector(1, 0, 2), .5));
		Camera c = new Camera(new Vector(0, 0, 0), 1600, 900, 90);
		c.setDirection(new Vector(0, 0, 1));
		
		JFrame frame = new JFrame("boi");
		panel.setPreferredSize(new Dimension(c.viewplane.getResolutionWidth(), c.viewplane.getResolutionHeight()));
		frame.setContentPane(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		panel.getGraphics().drawImage(c.getImage(), 0, 0, panel);

		System.out.println(c.getTransformationMatrix());
	}
}
