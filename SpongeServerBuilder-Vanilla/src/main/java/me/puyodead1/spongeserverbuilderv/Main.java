package me.puyodead1.spongeserverbuilderv;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public static JTextField text;
	public static JComboBox<String> mcVersionsCombo, spongeVersionsCombo;
	public static JLabel downloadProgress;
	public static JTextArea console;
	public static JCheckBox btnAcceptMojangEula;
	public static JButton btnGo, btnBrowse;

	public static boolean acceptedEULA = false;
	public static String outputPath, version, ProgramVersion = "1.0.0";
	public static List<String> mcVersions = new ArrayList<String>();
	public static HashMap<String, String> spongeVersions = new HashMap<String, String>();
	public static List<String> blacklistedVersions = new ArrayList<String>(
			Arrays.asList("1.8", "1.8.1", "1.8.2", "1.8.3", "1.8.4", "1.8.5", "1.8.6", "1.8.7", "1.8.8", "1.9", "1.9.1",
					"1.9.2", "1.9.3", "1.9.4", "1.10", "1.10.1", "1.11", "1.11.1", "1.12", "1.12.1"));
	private JScrollPane scrollPane;
	private final JLabel lblSpongeVersion = new JLabel("Sponge Version:");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// updateSpongeVersions();
		updateMinecraftVersions();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		initGUI();
	}

	private void initGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(String.format("Sponge Server Builder (Vanilla) %s by Puyodead1", ProgramVersion));
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setSize(1220, 492);
		contentPane.setLayout(null);
		contentPane.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(430, 36, 759, 409);
		contentPane.add(scrollPane);

		console = new JTextArea();
		console.setWrapStyleWord(true);
		console.setEditable(false);
		scrollPane.setViewportView(console);

		JLabel lblSelectAVersion = new JLabel("Minecraft Version:");
		lblSelectAVersion.setBounds(19, 10, 93, 15);
		getContentPane().add(lblSelectAVersion);

		JLabel lblChooseOutputPath = new JLabel("Output Directory:");
		lblChooseOutputPath.setBounds(190, 10, 100, 15);
		getContentPane().add(lblChooseOutputPath);

		JLabel lblCopyright = new JLabel("Copyright 2018-2019 Puyodead1");
		lblCopyright.setBounds(3, 435, 222, 15);
		getContentPane().add(lblCopyright);

		JLabel label = new JLabel();
		label.setBounds(418, 0, 2, 464);
		getContentPane().add(label);

		JLabel lblOutput = new JLabel("Output:");
		lblOutput.setBounds(799, 10, 45, 15);
		getContentPane().add(lblOutput);

		btnBrowse = new JButton("Browse");
		btnBrowse.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setName("Select where the server should be created.");
				fileChooser.setCurrentDirectory(new java.io.File("."));
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fileChooser.showOpenDialog(fileChooser);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					if (file != null && file.isDirectory()) {
						text.setText(file.getAbsolutePath());
						outputPath = file.getAbsolutePath();
					}
				}

			}
		});
		btnBrowse.setBounds(337, 28, 75, 25);
		getContentPane().add(btnBrowse);

		btnGo = new JButton();
		btnGo.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if (acceptedEULA) {
					if (!Objects.isNull(outputPath) && !outputPath.isEmpty()) {
						console.setText("");
						console.append("Starting...");
						console.append("\nUsing Minecraft Versions: "
								+ mcVersionsCombo.getItemAt(mcVersionsCombo.getSelectedIndex()));
						console.append("\nUsing Sponge Versions: "
								+ spongeVersionsCombo.getItemAt(spongeVersionsCombo.getSelectedIndex()) + "\n");
						new SpongeServerBuilderV(spongeVersions.get(spongeVersionsCombo.getItemAt(spongeVersionsCombo.getSelectedIndex())));
						btnGo.setEnabled(false);
						mcVersionsCombo.setEnabled(false);
						spongeVersionsCombo.setEnabled(false);
						text.setEnabled(false);
						btnBrowse.setEnabled(false);
					} else
						console.setText("Please choose the output directory!");
				} else
					console.setText("You need to accept the Mojang EULA to continue!");
				
			}
		});
		btnGo.setBounds(175, 76, 238, 74);
		btnGo.setText("Create");
		getContentPane().add(btnGo);

		text = new JTextField();
		text.setBounds(131, 31, 200, 21);
		getContentPane().add(text);

		JLabel lblSpongeDownloadProgress = new JLabel("Sponge Download Progress:");
		lblSpongeDownloadProgress.setBounds(10, 171, 180, 15);
		getContentPane().add(lblSpongeDownloadProgress);

		downloadProgress = new JLabel("0%");
		downloadProgress.setBounds(190, 171, 35, 15);
		getContentPane().add(downloadProgress);

		btnAcceptMojangEula = new JCheckBox();
		btnAcceptMojangEula.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acceptedEULA = !acceptedEULA;
				btnAcceptMojangEula.setEnabled(false);
			}
		});
		btnAcceptMojangEula.setBounds(4, 138, 108, 16);
		btnAcceptMojangEula.setText("Accept EULA?");
		getContentPane().add(btnAcceptMojangEula);

		JSeparator separator = new JSeparator();
		separator.setLocation(0, 59);
		separator.setSize(419, 2);
		contentPane.add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(125, 0, 2, 60);
		contentPane.add(separator_1);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(0, 163, 420, 2);
		contentPane.add(separator_2);

		JSeparator separator_3 = new JSeparator();
		separator_3.setOrientation(SwingConstants.VERTICAL);
		separator_3.setBounds(418, 0, 2, 464);
		contentPane.add(separator_3);

		mcVersionsCombo = new JComboBox<String>();
		mcVersionsCombo.setBounds(15, 29, 105, 23);
		mcVersionsCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateSpongeVersions(mcVersionsCombo.getItemAt(mcVersionsCombo.getSelectedIndex()));
			}
		});
		getContentPane().add(mcVersionsCombo);

		spongeVersionsCombo = new JComboBox<String>();
		spongeVersionsCombo.setBounds(3, 95, 157, 23);
		contentPane.add(spongeVersionsCombo);

		for (String v : mcVersions) {
			if (v.startsWith("a") || v.startsWith("b") || v.startsWith("c") || v.startsWith("inf") || v.startsWith("rd")
					|| blacklistedVersions.contains(v))
				continue;
			mcVersionsCombo.addItem(v);
		}
		lblSpongeVersion.setBounds(32, 72, 93, 15);

		contentPane.add(lblSpongeVersion);

		JSeparator separator_4 = new JSeparator();
		separator_4.setOrientation(SwingConstants.VERTICAL);
		separator_4.setBounds(170, 60, 2, 105);
		contentPane.add(separator_4);

		JSeparator separator_5 = new JSeparator();
		separator_5.setBounds(0, 129, 171, 2);
		contentPane.add(separator_5);
	}

	/**
	 * Fetch sponge versions for mc version
	 *
	 * @author Puyodead1
	 */
	public static void updateSpongeVersions(String v) {
		Main.spongeVersionsCombo.removeAllItems();
		try {
			URL url = new URL(
					"https://dl-api.spongepowered.org/v1/org.spongepowered/spongevanilla/downloads?type=stable&&minecraft="
							+ v + "&limit=1000");

			URLConnection request = url.openConnection();
			request.connect();
			JsonParser jp = new JsonParser();
			JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
			JsonArray rootObj = root.getAsJsonArray();

			for (JsonElement je : rootObj) {
				JsonObject jo = je.getAsJsonObject();
				if (!spongeVersions.containsKey(jo.get("version").getAsString())) {
					JsonObject artifactJO = jo.get("artifacts").getAsJsonObject();
					spongeVersions.put(jo.get("version").getAsString(),
							artifactJO.get("").getAsJsonObject().get("url").getAsString());
				}
				Main.spongeVersionsCombo.addItem(jo.get("version").getAsString());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Fetch all minecraft versions
	 *
	 * @author Puyodead1
	 */
	public static void updateMinecraftVersions() {
		List<String> versions = new ArrayList<String>();
		JsonArray rawMCVersions = new JsonArray();
		try {
			URL url = new URL("https://launchermeta.mojang.com/mc/game/version_manifest.json");

			URLConnection request = url.openConnection();
			request.connect();
			JsonParser jp = new JsonParser();
			JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
			JsonObject rootObj = root.getAsJsonObject();
			JsonArray mcVersionsArray = rootObj.get("versions").getAsJsonArray();

			for (JsonElement mcVersionJE : mcVersionsArray)
				rawMCVersions.add(mcVersionJE);

			for (JsonElement versionElem : rawMCVersions) {
				JsonObject versionObj = versionElem.getAsJsonObject();
				if (versionObj.get("type").getAsString().equals("snapshot"))
					continue;
				versions.add(versionObj.get("id").getAsString());
			}

			Main.mcVersions = versions.subList(0, versions.indexOf("1.8") + 1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
