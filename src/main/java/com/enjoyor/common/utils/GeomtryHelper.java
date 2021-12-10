package com.enjoyor.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import oracle.spatial.geometry.JGeometry;

 
public final class GeomtryHelper {
	/**
	 * 转换JGeomertry类型到arcgisJsApi标准绘制json字符串
	 * @param jgeom
	 * @return
	 */
	@SuppressWarnings("unused")
	public static String getGeom2JsonString(JGeometry jgeom,boolean hasZ) {
		int jtype = jgeom.getType();
		int wkid = jgeom.getSRID();
		double[] coords;
		StringBuffer tempStr = new StringBuffer("");
		switch (jtype) {
		case 1:
			coords = jgeom.getPoint();
			tempStr.append("{");
			tempStr.append("x:");
			tempStr.append(String.valueOf(coords[0]) + ",");
			tempStr.append("y:");
			tempStr.append(String.valueOf(coords[1]) + ",");
			tempStr.append("\"spatialReference\":{\"wkid\":4326}}");
			break;
		case 2:
			coords = jgeom.getOrdinatesArray();
			tempStr.append("{\"paths\":[[");
			if(hasZ){
				for (int j = 0; j < coords.length - 1; j++) {
					if (j % 3 == 0) {
						tempStr.append("[");
						tempStr.append(String.valueOf(coords[j]) + ",");
					}else if(j%3==1){
						tempStr.append(String.valueOf(coords[j]) + ",");
					} else {
						tempStr.append(String.valueOf(coords[j]) + "],");
					}
				}
			}else{
				for (int j = 0; j < coords.length - 1; j++) {
					if (j % 2 == 0) {
						tempStr.append("[");
						tempStr.append(String.valueOf(coords[j]) + ",");
					} else {
						tempStr.append(String.valueOf(coords[j]) + "],");
					}
				}
			}
			tempStr.append(String.valueOf(coords[coords.length - 1]) + "]]]");
			tempStr.append(",\"spatialReference\":{\"wkid\":4326}}");
			break;
		case 3:
			coords = jgeom.getOrdinatesArray();
			tempStr.append("{\"rings\":[[");
			for (int j = 0; j < coords.length - 1; j++) {
				if (j % 2 == 0) {
					tempStr.append("[");
					tempStr.append(String.valueOf(coords[j]) + ",");
				} else {
					tempStr.append(String.valueOf(coords[j]) + "],");
				}
			}
			tempStr.append(String.valueOf(coords[coords.length - 1]) + "]]]");
			tempStr.append(",\"spatialReference\":{\"wkid\":4326}}");
			break;
		default:
			break;
		}
		return tempStr.toString();
	}

	/**
	 * ArcgisWebApi标准json字符换转成JGeometry
	 * @param jsonText
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static JGeometry getJTring2Geom(String jsonText) {
		if(jsonText==null||jsonText.length()==0)return null;
		JGeometry jgeom = null;
		int dim = 2;
		int srid = 4326;
		JSONObject geomjson = JSONObject.parseObject(jsonText);
		JSONObject spatialReference = (JSONObject) geomjson.get("spatialReference");
		srid = spatialReference.getIntValue("wkid");
		double[] coords;
		if (geomjson.get("rings") != null) {
			JSONArray rings = (JSONArray) geomjson.get("rings");
			List ring = (List) rings.get(0);
			coords = new double[ring.size() * 2];
			int xxx = 0;
			for (int j = 0; j < ring.size(); j++) {
				List r = (List) ring.get(j);
				String x = r.get(0).toString();
				String y = r.get(1).toString();
				coords[xxx] = Double.parseDouble(x);
				xxx++;
				coords[xxx] = Double.parseDouble(y);
				xxx++;
			}
			jgeom = JGeometry.createLinearPolygon(coords, dim, srid);
		} else if (geomjson.get("x") != null) {
			String x = geomjson.get("x").toString();
			String y = geomjson.get("y").toString();
			coords = new double[] { Double.parseDouble(x), Double.parseDouble(y) };
			jgeom = JGeometry.createPoint(coords, dim, srid);
		} else if (geomjson.get("paths") != null) {
			JSONArray paths = (JSONArray) geomjson.get("paths");
			List path = (List) paths.get(0);
			coords = new double[path.size() * 3];
			int xxx = 0;
			for (int j = 0; j < path.size(); j++) {
				List p = (List) path.get(j);
				String x = p.get(0).toString();
				String y = p.get(1).toString();
				coords[xxx] = Double.parseDouble(x);
				xxx++;
				coords[xxx] = Double.parseDouble(y);
				xxx++;
				coords[xxx] = 0;
				xxx++;
			}
			//dim=3;
			jgeom = JGeometry.createLinearLineString(coords, dim, srid);
			
			/*coords = new double[path.size() * 2];
			int xxx = 0;
			for (int j = 0; j < path.size(); j++) {
				List p = (List) path.get(j);
				String x = p.get(0).toString();
				String y = p.get(1).toString();
				coords[xxx] = Double.parseDouble(x);
				xxx++;
				coords[xxx] = Double.parseDouble(y);
				xxx++;
			}
			jgeom = JGeometry.createLinearLineString(coords, dim, srid);*/
		}
		return jgeom;
	}

	/**
	 * Map转GeoJSON格式图元
	 * @param map 对象
	 * @param propertyJGeometryName 图元属性字段
	 * @return
	 */
	public static Map<String, Object> mapToGeoJsonGeometry(Map<String, Object> map, String propertyJGeometryName) {
		Map<String, Object> result = new LinkedHashMap<>();
		try {
			JGeometry geometry = (JGeometry) map.get(propertyJGeometryName);
			int type = geometry.getType();
			switch (type) {
			case 1:
				result.put("type", "Point");
				result.put("coordinates", geometry.getPoint());
				break;
			case 2:
				result.put("type", "LineString");
				double[] ordinates = geometry.getOrdinatesArray();
				double[][] temp = {};
				for (int i = 0; i < ordinates.length; i = i + 2) {
					double[] xy = { ordinates[i], ordinates[i + 1] };
					temp = ArrayUtils.add(temp, xy);
				}
				result.put("coordinates", temp);
				break;
			case 3:
				result.put("type", "Point");
				result.put("coordinates", new double[] {});
				break;
			default:
				break;
			}
		} catch (Exception e) {
			System.err.println(e);
			result = null;
		}
		return result;
	}

	/**
	 * Map转GeoJSON格式图元
	 * @param map 对象
	 * @param propertyXName 经度属性字段
	 * @param propertyYName 纬度属性字段
	 * @return
	 */
	public static Map<String, Object> mapToGeoJsonGeometry(Map<String, Object> map, String propertyXName, String propertyYName) {
		Map<String, Object> result = new LinkedHashMap<>();
		result.put("type", "Point");
		result.put("coordinates", new double[] {});
		try {
			result.put("coordinates", new double[] { (double) map.get(propertyXName), (double) map.get(propertyYName) });
		} catch (Exception e) {
			System.err.println(e);
			return null;
		}
		return result;
	}

	/**
	 * Map转GeoJSON格式要素
	 * @param map 对象
	 * @param propertyKeyName 主键属性字段，可空
	 * @param propertyJGeometryName 图元属性字段
	 * @return
	 */
	public static Map<String, Object> mapToGeoJsonFeature(Map<String, Object> map, String propertyKeyName, String propertyJGeometryName) {
		try {
			Map<String, Object> result = new LinkedHashMap<>();
			result.put("type", "Feature");
			result.put("id", "Feature." + UUID.randomUUID().toString().replace("-", ""));
			if (propertyKeyName != null) {
				result.put("id", map.get(propertyKeyName));
			}
			result.put("geometry_name", "GEOMETRY");
			/* 为防止属性过长浅复制移除JGeometry字段Begin */
			Map<String, Object> temp = new LinkedHashMap<>();
			temp.putAll(map);
			temp.remove(propertyJGeometryName);
			result.put("properties", temp);
			/* 为防止属性过长浅复制移除JGeometry字段End */
			result.put("geometry", mapToGeoJsonGeometry(map, propertyJGeometryName));
			return result;
		} catch (Exception e) {
			System.err.println(e);
			return null;
		}
	}

	/**
	 * Map转GeoJSON格式要素
	 * @param map 对象
	 * @param propertyKeyName 主键属性字段，可空
	 * @param propertyXName 经度属性字段
	 * @param propertyYName 纬度属性字段
	 * @return
	 */
	public static Map<String, Object> mapToGeoJsonFeature(Map<String, Object> map, String propertyKeyName, String propertyXName, String propertyYName) {
		try {
			Map<String, Object> result = new LinkedHashMap<>();
			result.put("type", "Feature");
			result.put("id", "Feature." + UUID.randomUUID().toString().replace("-", ""));
			if (propertyKeyName != null) {
				result.put("id", map.get(propertyKeyName));
			}
			result.put("geometry_name", "GEOMETRY");
			result.put("properties", map);
			result.put("geometry", mapToGeoJsonGeometry(map, propertyXName, propertyYName));
			return result;
		} catch (Exception e) {
			System.err.println(e);
			return null;
		}
	}

	/**
	 * 对象转GeoJSON格式图元
	 * @param bean 对象
	 * @param propertyJGeometryName 图元属性字段
	 * @return
	 */
	public static Map<String, Object> beanToGeoJsonGeometry(Object bean, String propertyJGeometryName) {
		try {
			return mapToGeoJsonGeometry(beanToMap(bean), propertyJGeometryName);
		} catch (Exception e) {
			System.err.println(e);
			return null;
		}
	}

	/**
	 * 对象转GeoJSON格式图元
	 * @param bean 对象
	 * @param propertyXName 经度字段名称
	 * @param propertyYName 纬度字段名称
	 * @return
	 */
	public static Map<String, Object> beanToGeoJsonGeometry(Object bean, String propertyXName, String propertyYName) {
		try {
			return mapToGeoJsonGeometry(beanToMap(bean), propertyXName, propertyYName);
		} catch (Exception e) {
			System.err.println(e);
			return null;
		}
	}

	/**
	 * 对象转GeoJSON格式要素
	 * @param bean
	 * @param propertyKeyName
	 * @param propertyJGeometryName
	 * @return
	 */
	public static Map<String, Object> beanToGeoJsonFeature(Object bean, String propertyKeyName, String propertyJGeometryName) {
		try {
			return mapToGeoJsonFeature(beanToMap(bean), propertyKeyName, propertyJGeometryName);
		} catch (Exception e) {
			System.err.println(e);
			return null;
		}
	}

	/**
	 * 对象转GeoJSON格式要素
	 * @param bean
	 * @param propertyKeyName
	 * @param propertyXName
	 * @param propertyYName
	 * @return
	 */
	public static Map<String, Object> beanToGeoJsonFeature(Object bean, String propertyKeyName, String propertyXName, String propertyYName) {
		try {
			return mapToGeoJsonFeature(beanToMap(bean), propertyKeyName, propertyXName, propertyYName);
		} catch (Exception e) {
			System.err.println(e);
			return null;
		}
	}

	/**
	 * List转GeoJSON格式FeatureCollection
	 * @param source
	 * @param propertyKeyName
	 * @param propertyJGeometryName
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> listToGeoJsonFeatureCollection(List source, String propertyKeyName, String propertyJGeometryName) {
		Map<String, Object> result = new LinkedHashMap<>();
		try {
			result.put("type", "FeatureCollection");
			List<Map<String, Object>> features = new ArrayList<>();
			for (Object item : source) {
				features.add(beanToGeoJsonFeature(item, propertyKeyName, propertyJGeometryName));
			}
			result.put("totalFeatures", features.size());
			result.put("features", features);
			result.put("crs", JSONObject.parse("{type:'name',properties:{name:'urn:ogc:def:crs:EPSG::4326'}}"));
		} catch (Exception e) {
			System.err.println(e);
			result = null;
		}
		return result;
	}

	/**
	 * List转GeoJSON格式FeatureCollection
	 * @param source 对象
	 * @param propertyKeyName 主键属性字段、可空
	 * @param propertyXName 经度属性字段
	 * @param propertyYName 纬度属性字段
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> listToGeoJsonFeatureCollection(List source, String propertyKeyName, String propertyXName, String propertyYName) {
		Map<String, Object> result = new LinkedHashMap<>();
		try {
			result.put("type", "FeatureCollection");
			List<Map<String, Object>> features = new ArrayList<>();
			for (Object item : source) {
				features.add(beanToGeoJsonFeature(item, propertyKeyName, propertyXName, propertyYName));
			}
			result.put("totalFeatures", features.size());
			result.put("features", features);
			result.put("crs", JSONObject.parse("{type:'name',properties:{name:'urn:ogc:def:crs:EPSG::4326'}}"));
		} catch (Exception e) {
			System.err.println(e);
			result = null;
		}
		return result;
	}

	/**
	 * 对象转Map
	 * @param bean
	 * @return
	 */
	private static Map<String, Object> beanToMap(Object bean) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Class<?> beanClass = bean.getClass();
			for (Field field : beanClass.getDeclaredFields()) {
				String fieldName = field.getName();
				for (Method method : beanClass.getMethods()) {
					try {
						if ((Pattern.matches("get(\\w+)", method.getName()) || Pattern.matches("is(\\w+)", method.getName())) && method.getParameterTypes().length == 0 && method.getDeclaringClass() != Object.class) {
							Pattern pattern = Pattern.matches("get(\\w+)", method.getName()) ? Pattern.compile("get(\\w+)") : Pattern.compile("is(\\w+)");
							String param = pattern.matcher(method.getName()).replaceAll("$1");
							if (fieldName.equalsIgnoreCase(param)) {
								Object ret = method.invoke(bean);
								if (ret != null) {
									map.put(fieldName, ret);
								}
								break;
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
