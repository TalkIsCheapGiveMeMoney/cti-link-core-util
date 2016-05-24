package com.tinet.ctilink.json;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

/**
 * 使用Jackson构造的json-lib适配器，以为了在不修改原有实现的基础上替换掉json-lib的JSONObject
 * 
 * @author Jiangsl
 *
 */
@SuppressWarnings("serial")
public class JSONObject extends LinkedHashMap<String, Object> {

	private static ObjectMapper mapper;

	static {
		mapper = new ObjectMapper();
	}

	public static JSONObject fromObject(String json) {
		try {
			mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
			mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
			mapper.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true);
			mapper.configure(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS, true);
			return mapper.readValue(json, JSONObject.class);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static JSONObject fromObject(Object object) {
		if (object instanceof String) {
			return fromObject(object.toString());
		}
		try {
			return fromObject(mapper.writeValueAsString(object));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getJSONString(Object object) {
		if (object instanceof String) {
			return object.toString();
		}
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static <T> T getBean(String json, Class<T> c) {
		if (json == null) {
			return null;
		}
		T t = null;
		try {
			t = mapper.readValue(json, c);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	public JSONObject getJSONObject(String key) {
		return mapper.convertValue(super.get(key), JSONObject.class);
	}

	public <T> T getBean(Class<T> c) {
		return mapper.convertValue(this, c);
	}

	public JSONArray getJSONArray(String key) {
		JSONArray ja = new JSONArray();
		List<Object> list = (List<Object>)super.get(key);
		for(Object o : list){
			ja.add(o);
		}
		return ja;
	}


	public boolean isNull(String key) {
		return !super.containsKey(key);
	}

	public boolean isNotNull(String key) {
		return !isNull(key);
	}

	public String getString(String key) {
		Object object = super.get(key);
		if (object == null) {
			return null;
		}
		return object.toString();
	}

	public int getInt(String key) {
		Object object = super.get(key);
		if (object == null || !StringUtils.isNumeric(object.toString())) {
			return -1;
		}
		return Integer.parseInt(object.toString());
	}

	public boolean getBoolean(String key) {
		Object object = super.get(key);
		if (object == null) {
			return false;
		}
		return Boolean.parseBoolean(object.toString());
	}

	@Override
	public String toString() {
		String json = null;

		try {
			json = mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return json;
	}

}
