package com.thundersphun.foggingup.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.thundersphun.foggingup.fogTypes.BiomeFogType;
import com.thundersphun.foggingup.fogTypes.DimensionFogType;
import com.thundersphun.foggingup.fogTypes.FogTypeBuilder;
import com.thundersphun.foggingup.util.IdType;
import com.thundersphun.foggingup.util.JsonUtil;
import net.minecraft.util.Identifier;

public class Parser1 implements Parser {
	private static final String ARRAY_PATH = "data";
	private static final String ID_TYPE = "id_type";
	private static final String ENABLED = "enabled";
	private static final String START = "start_modifier";
	private static final String END = "end_modifier";
	private static final String DENSITY = "density_modifier";
	private static final String ID = "id";

	@Override
	public FogList parse(JsonObject json) {
		FogList fogList = new FogList();
		JsonArray array = JsonUtil.getArray(json, ARRAY_PATH);
		for (JsonElement element : array) {
			JsonObject e = (JsonObject) element;
			FogTypeBuilder builder = this.getBuilder(e);

			builder.setEnabled(JsonUtil.getBoolean(e, ENABLED, true));

			builder.setStart(JsonUtil.getFloat(e, START, 1f));
			builder.setEnd(JsonUtil.getFloat(e, END, 1f));
			builder.setDensity(JsonUtil.getFloat(e, DENSITY, 1f));

			builder.setId(new Identifier(JsonUtil.getString(e, ID, "")));

			fogList.add(builder.build());
		}
		return fogList;
	}

	@Override
	public JsonObject toJson(FogList list) {
		JsonArray array = new JsonArray();
		list.getFogTypes().forEach(e -> {
			JsonObject fogType = new JsonObject();

			if (e instanceof DimensionFogType) {
				fogType.addProperty(ID_TYPE, IdType.DIMENSION.toString());
			} else if (e instanceof BiomeFogType) {
				fogType.addProperty(ID_TYPE, IdType.BIOME.toString());
			}

			fogType.addProperty(START, e.getStartModifier());
			fogType.addProperty(END, e.getEndModifier());
			fogType.addProperty(DENSITY, e.getDensityModifier());

			fogType.addProperty(ID, e.getId().toString());

			fogType.addProperty(ENABLED, e.fogEnabled());

			array.add(fogType);
		});

		JsonObject json = new JsonObject();
		json.addProperty(FogIO.CONFIG_VERSION, getVersion());
		json.add(ARRAY_PATH, array);
		return json;
	}

	@Override
	public int getVersion() {
		return 1;
	}

	private FogTypeBuilder getBuilder(JsonObject e) {
		FogTypeBuilder builder = new FogTypeBuilder();

		if (e.has(ID_TYPE)) {
			String identification = JsonUtil.getString(e, ID_TYPE);
			if (identification.equalsIgnoreCase(IdType.BIOME.toString())) {
				builder = new FogTypeBuilder(IdType.BIOME);
			} else if (identification.equalsIgnoreCase(IdType.DIMENSION.toString())) {
				builder = new FogTypeBuilder(IdType.DIMENSION);
			}
		}

		return builder;
	}
}
