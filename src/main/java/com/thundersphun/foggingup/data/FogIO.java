package com.thundersphun.foggingup.data;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.thundersphun.foggingup.util.JsonUtil;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class FogIO {
	public static final String CONFIG_VERSION = "config_version";
	private static final String CONFIG_NAME = "Fogging Up.json";
	private static final int LATEST_VERSION = 1;
	private final File configFilePath;
	private final JsonObject json;

	public FogIO() {
		this.configFilePath = FabricLoader.getInstance().getConfigDir().resolve(CONFIG_NAME).toFile();
		this.json = JsonUtil.load(this.configFilePath);
	}

	public FogList toList() {
		if (this.json == null) {
			return FogList.DEFAULT_TYPES;
		}
		int version = JsonUtil.getInt(this.json, CONFIG_VERSION, LATEST_VERSION);
		Parser parser = this.getParserFromVersion(version);
		return parser == null ? new FogList() : parser.parse(this.json);
	}

	private Parser getParserFromVersion(int version) {
		switch (version) {
			case 1:
				return new Parser1();
			default:
				return null;
		}
	}

	public void save(FogList fogList) {
		try (Writer writer = new FileWriter(this.configFilePath)) {
			Parser parser = getParserFromVersion(LATEST_VERSION);
			if (parser == null) {
				new GsonBuilder().setPrettyPrinting().create().toJson(new JsonObject(), writer);
			} else {
				new GsonBuilder().setPrettyPrinting().create().toJson(parser.toJson(fogList), writer);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
