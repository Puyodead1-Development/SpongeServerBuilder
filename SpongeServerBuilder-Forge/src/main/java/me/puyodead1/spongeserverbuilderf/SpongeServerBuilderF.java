package me.puyodead1.spongeserverbuilderf;

import me.puyodead1.spongeserverbuilderf.utils.Utils;

public class SpongeServerBuilderF {

	public static void Log(String log) {
		Main.console.append(log + "\n");
	}

	public SpongeServerBuilderF(String URL) {
		Utils.CheckForDirectory(Main.outputPath);
		Utils.CheckForDirectory(Main.outputPath + "\\SpongeServer");
		Utils.CheckForDirectory(Main.outputPath + "\\SpongeServer\\mods");
		Utils.DownloadSponge(URL);
	}
}
