package me.puyodead1.spongeserverbuilderv;

import me.puyodead1.spongeserverbuilderv.utils.Utils;

public class SpongeServerBuilderV {

	public static void Log(String log) {
		Main.console.append(log + "\n");
	}

	public SpongeServerBuilderV(String URL) {
		Utils.CheckForDirectory(Main.outputPath);
		Utils.CheckForDirectory(Main.outputPath + "\\SpongeServer");
		Utils.DownloadSponge(URL);
	}

}
