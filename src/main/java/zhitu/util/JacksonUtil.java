package zhitu.util;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Jackson工具类
 *
 */
public class JacksonUtil {
	private static final JacksonUtil jacksonUtil = new JacksonUtil();
	private ObjectMapper objectMapper = new ObjectMapper();

	private JacksonUtil() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		objectMapper.setDateFormat(dateFormat);
	}

	public static final JacksonUtil Builder() {
		return jacksonUtil;
	}

	public String obj2Json(Object obj) throws Exception {
		return obj == null ? null : objectMapper.writeValueAsString(obj);
	}

	public <T> T json2Model(String jsonStr, Class<T> clazz) throws Exception {
		return jsonStr == null ? null : objectMapper.readValue(jsonStr, clazz);
	}
	
	public <T> T json2Model(InputStream jsonStream, Class<T> clazz) throws Exception {
		return jsonStream == null  ? null : objectMapper.readValue(jsonStream, clazz);
	}

	public String map2Json(Map<String, Object> map) throws Exception {
		return map == null ? null : objectMapper.writeValueAsString(map);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> json2Map(String jsonStr) throws Exception {
		return  jsonStr == null ? null : objectMapper.readValue(jsonStr, Map.class);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> json2Map(InputStream jsonStream) throws Exception {
		return  jsonStream == null ? null : objectMapper.readValue(jsonStream, Map.class);
	}

	public <T> Map<String, T> json2Map(String jsonStr, Class<T> clazz) throws Exception {
		return objectMapper.readValue(jsonStr, new TypeReference<Map<String, T>>() {});
	}

	public <T> Map<String, T> json2Map(InputStream jsonStream, Class<T> clazz) throws Exception {
		return objectMapper.readValue(jsonStream, new TypeReference<Map<String, T>>() {});
	}

	public <T> List<T> json2List(String jsonStr, Class<T> clazz) throws Exception {
		List<T> result = new ArrayList<T>();
		List<Map<String, Object>> list = objectMapper.readValue(jsonStr, new TypeReference<List<T>>() {});
		for (Map<String, Object> map : list) {
			result.add(map2Model(map, clazz));
		}
		
		return result;
	}

	public <T> List<T> json2List(InputStream jsonStream, Class<T> clazz) throws Exception {
		List<T> result = new ArrayList<T>();
		List<Map<String, Object>> list = objectMapper.readValue(jsonStream, new TypeReference<List<T>>() {});
		for (Map<String, Object> map : list) {
			result.add(map2Model(map, clazz));
		}
		
		return result;
	}
	
	public <T> T map2Model(Map<?, ?> map, Class<T> clazz) {
		return objectMapper.convertValue(map, clazz);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> model2Map(Object obj) {
		return objectMapper.convertValue(obj, Map.class);
	}
}
