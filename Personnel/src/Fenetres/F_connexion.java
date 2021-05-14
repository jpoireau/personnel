package Fenetres;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class F_connexion extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3644247967299886674L;
	
	public F_connexion() {
		super("page d'accueil");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(500,500);
		this.setLocationRelativeTo(null);
		FlowLayout flow = new FlowLayout();
		
		JPanel contentPane = (JPanel) this.getContentPane();
		contentPane.setLayout(flow);
		JTextField mdp = new JTextField("inserer mot de passe");
		contentPane.add(mdp);
		
		mdp.addKeyListener(new java.awt.event.KeyAdapter() {  
			   public void keyPressed(java.awt.event.KeyEvent e) {
			      
			   }
	});
}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
F_connexion connexion = new F_connexion();

connexion.setVisible(true);
	}

}
