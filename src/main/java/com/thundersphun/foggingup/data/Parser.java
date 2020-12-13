package com.thundersphun.foggingup.data;

import com.google.gson.JsonObject;

public interface Parser {
	FogList parse(JsonObject json);

	JsonObject toJson(FogList fogList);

	int getVersion();
}
