package me.puyodead1.spongeserverbuilderf.utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import me.puyodead1.spongeserverbuilderf.Download;
import me.puyodead1.spongeserverbuilderf.Main;
import me.puyodead1.spongeserverbuilderf.SpongeServerBuilderF;

public class Utils extends Thread {
	public static String OS;
	public static Download download;

	public static void Complete() {
		SpongeServerBuilderF.Log(
				">>>>>>> ----------------------------------------------------------------------------------------------------- <<<<<<<");
		SpongeServerBuilderF
				.Log(">>>>>>> Complete! Please use the Launcher.bat file to start your new server! Enjoy! <<<<<<<");
		SpongeServerBuilderF.Log(
				">>>>>>> ----------------------------------------------------------------------------------------------------- <<<<<<<");

		Main.btnGo.setEnabled(true);
		Main.mcVersionsCombo.setEnabled(true);
		Main.spongeVersionsCombo.setEnabled(true);
		Main.text.setEnabled(true);
		Main.btnBrowse.setEnabled(true);
	}

	public static boolean CreateEULA() {
		if (!Utils.CheckForFile(Main.outputPath + "\\SpongeServer\\eula.txt"))
			// EULA file doesn't exist, we create it here
			try {
				PrintWriter pw = new PrintWriter(Main.outputPath + "\\SpongeServer\\eula.txt");
				pw.write("eula=true");
				pw.close();
				SpongeServerBuilderF.Log("EULA Re-Write complete...");
				// EULA Created successfully!
				return true;
			} catch (IOException e) {
				// Error creating that EULA file 0_0
				SpongeServerBuilderF.Log("Error creating the EULA.txt file!" + e.getMessage());
				StringWriter writer = new StringWriter();
				e.printStackTrace(new PrintWriter(writer));
				SpongeServerBuilderF.Log(writer.toString());
				return false;
			}
		else {
			// EULA file does exist, we can skip to launching the server! YAY!
			SpongeServerBuilderF.Log("Found EULA, Deleting and Re-Writing.");
			File eula = new File(Main.outputPath + "\\SpongeServer\\eula.txt");
			eula.delete();
			try {
				eula.createNewFile();
				PrintWriter pw = new PrintWriter(Main.outputPath + "\\SpongeServer\\eula.txt");
				pw.write("eula=true");
				pw.close();
				SpongeServerBuilderF.Log("EULA Re-Write Complete!");
				return true;
			} catch (IOException e) {
				StringWriter writer = new StringWriter();
				e.printStackTrace(new PrintWriter(writer));
				SpongeServerBuilderF.Log(writer.toString());
				return false;
			}
		}
	}

	public static boolean CheckForFile(String path) {
		File in = new File(path);
		if (!in.exists())
			// return false If we don't find file
			return false;
		else
			// return true If we find file
			return true;
	}

	public static void LauncherHandlerWindows() {
		if (CheckForFile(Main.outputPath + "\\SpongeServer\\Launcher.bat")) {
			// true if exists
			SpongeServerBuilderF.Log("Launcher exists.. Skipping...");
			Complete();
		} else {
			// false if no
			File launcher = new File(Main.outputPath + "\\SpongeServer\\Launcher.bat");
			try {
				launcher.createNewFile();
				PrintWriter pw = new PrintWriter(Main.outputPath + "\\SpongeServer\\Launcher.bat");
				pw.write(String.format("java -jar forge-%s.jar", Main.forgeVersions
						.get(Main.spongeVersionsCombo.getItemAt(Main.spongeVersionsCombo.getSelectedIndex()))));
				pw.close();
				SpongeServerBuilderF.Log("Launcher write complete.");
				Complete();
			} catch (IOException e) {
				StringWriter writer = new StringWriter();
				e.printStackTrace(new PrintWriter(writer));
				SpongeServerBuilderF.Log(writer.toString());
			}
		}
	}

	public static void CheckForDirectory(String path) {
		File in = new File(path);
		if (!in.exists())
			// false not exists
			try {
				CreateDirectory(path);
				SpongeServerBuilderF.Log("Created Directory at: " + path);
			} catch (Exception e) {
				StringWriter writer = new StringWriter();
				e.printStackTrace(new PrintWriter(writer));
				SpongeServerBuilderF.Log(writer.toString());
			}
	}

	public static boolean CreateDirectory(String path) {
		try {
			Files.createDirectory(Paths.get(path));
			return true;
		} catch (IOException e) {
			if (e.toString().contains("FileAlreadyExists")) { // TODO this might be a bad way of checking...
				SpongeServerBuilderF.Log("Directory already exists! Skipping...");
				return false;
			} else {
				SpongeServerBuilderF.Log("Oops, an error occured creating directory...");
				StringWriter writer = new StringWriter();
				e.printStackTrace(new PrintWriter(writer));
				SpongeServerBuilderF.Log(writer.toString());
				return false;
			}
		}
	}

	public static void DownloadSponge(String URL) {
		try {
			URL url = new URL(URL);

			download = new Download(url, Main.outputPath + "\\SpongeServer\\mods\\Sponge-"
					+ Main.spongeVersionsCombo.getItemAt(Main.spongeVersionsCombo.getSelectedIndex()) + ".jar");

		} catch (MalformedURLException e) {
			SpongeServerBuilderF.Log("\nCought a Malformed URL Exception!");
			StringWriter writer = new StringWriter();
			e.printStackTrace(new PrintWriter(writer));
			SpongeServerBuilderF.Log(writer.toString());
		}
	}

	public static boolean isWindows() {

		return OS.indexOf("win") >= 0;

	}

	public static boolean isMac() {

		return OS.indexOf("mac") >= 0;

	}

	public static boolean isUnix() {

		return OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0;

	}

	public static boolean isSolaris() {

		return OS.indexOf("sunos") >= 0;

	}

	public static void GetOS() {
		OS = System.getProperty("os.name").toLowerCase();
	}
}
