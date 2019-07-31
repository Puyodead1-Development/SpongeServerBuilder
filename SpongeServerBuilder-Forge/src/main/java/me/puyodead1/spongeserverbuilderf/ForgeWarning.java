package me.puyodead1.spongeserverbuilderf;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

public class ForgeWarning {

	private JPanel contentPane;
	private final JTextArea txtrA = new JTextArea("You WILL need to download Minecraft Forge Version: "
			// +
			// Main.forgeVersions.get(Main.spongeVersionsCombo.getItemAt(Main.spongeVersionsCombo.getSelectedIndex()))
			+ " and place it along side the Launcher.jar!");
	private final JLabel lblWarning = new JLabel("Warning!");
	private BufferedImage image;
	private BufferedImage longLogo;
	private final JLabel forgeLogo = new JLabel();
	private final JLabel lblForgeAndThe = new JLabel("Forge and the Forge logo are property of ForgeDevelopment LLC.");
	private final JTextPane txtpnHttps = new JTextPane();
	private final JLabel lblDownloadAt = new JLabel("Download at:");
	private final JPanel panel = new JPanel();

	/**
	 * Create the frame.
	 */
	public ForgeWarning() {
		try {
			image = ImageIO.read(new URL("https://i.imgur.com/9g90944.png"));
			longLogo = ImageIO.read(new URL("https://i.imgur.com/oaViUv5.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ImageIcon icon = new ImageIcon(longLogo);

		JFrame frame = new JFrame();
		frame.setIconImage(image);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 450, 300);
		frame.setBackground(Color.BLACK);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		lblWarning.setForeground(Color.WHITE);
		lblWarning.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblWarning.setBounds(165, 84, 122, 38);

		contentPane.add(lblWarning);
		txtrA.setFont(new Font("Monospaced", Font.PLAIN, 12));
		txtrA.setLineWrap(true);
		txtrA.setBounds(5, 137, 425, 38);

		contentPane.add(txtrA);
		forgeLogo.setBounds(58, 11, 316, 67);
		forgeLogo.setIcon(icon);
		contentPane.add(forgeLogo);
		lblForgeAndThe.setBounds(42, 247, 365, 14);

		contentPane.add(lblForgeAndThe);
		panel.setBackground(Color.BLACK);
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(0, 174, 434, 62);

		contentPane.add(panel);
		panel.setLayout(null);
		lblDownloadAt.setForeground(new Color(255, 255, 255));
		lblDownloadAt.setBounds(118, 19, 80, 21);
		panel.add(lblDownloadAt);
		txtpnHttps.setBounds(206, 19, 169, 20);
		panel.add(txtpnHttps);
		txtpnHttps.setEditable(false);
		txtpnHttps.setText("https://files.minecraftforge.net/");
	}
}
