package com.thundersphun.foggingup.util;

import com.google.gson.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class JsonUtil {
	public static JsonObject load(File configFilePath) {
		try (FileReader reader = new FileReader(configFilePath)) {
			return new Gson().fromJson(reader, JsonObject.class);
		} catch (IOException e) {
			return null;
		}
	}

	public static JsonElement get(JsonObject json, String key) {
		return get(json, key, JsonNull.INSTANCE);
	}

	public static int getInt(JsonObject json, String key) {
		return getInt(json, key, 0);
	}

	public static String getString(JsonObject json, String key) {
		return getString(json, key, "");
	}

	public static double getDouble(JsonObject json, String key) {
		return getDouble(json, key, 0);
	}

	public static float getFloat(JsonObject json, String key) {
		return getFloat(json, key, 0f);
	}

	public static boolean getBoolean(JsonObject json, String key) {
		return getBoolean(json, key, false);
	}

	public static JsonArray getArray(JsonObject json, String key) {
		return getArray(json, key, new JsonArray()).getAsJsonArray();
	}

	public static JsonElement get(JsonObject json, String key, JsonElement ifAbsent) {
		if (json.has(key)) {
			return json.get(key);
		}
		return ifAbsent;
	}

	public static int getInt(JsonObject json, String key, int ifAbsent) {
		return get(json, key, new JsonPrimitive(ifAbsent)).getAsInt();
	}

	public static String getString(JsonObject json, String key, String ifAbsent) {
		return get(json, key, new JsonPrimitive(ifAbsent)).getAsString();
	}

	public static double getDouble(JsonObject json, String key, double ifAbsent) {
		return get(json, key, new JsonPrimitive(ifAbsent)).getAsDouble();
	}

	public static float getFloat(JsonObject json, String key, float ifAbsent) {
		return get(json, key, new JsonPrimitive(ifAbsent)).getAsFloat();
	}

	public static boolean getBoolean(JsonObject json, String key, boolean ifAbsent) {
		return get(json, key, new JsonPrimitive(ifAbsent)).getAsBoolean();
	}

	public static JsonElement getArray(JsonObject json, String key, JsonArray ifAbsent) {
		return get(json, key, ifAbsent).getAsJsonArray();
	}
}
