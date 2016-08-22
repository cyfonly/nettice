package com.router;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.router.annotation.Get;
import com.router.annotation.Post;
import com.router.annotation.Read;
import com.router.convertor.PriTypeConverter;
import com.router.convertor.PrimitiveType;
import com.router.exception.ActionInvocationException;
import com.router.utils.GenericsUtils;
import com.router.utils.ValidateUtil;

/**
 * Action基础类，所有Action必须继承自此类。BaseAction封装了参数填充、转发、方法调用等功能，并向子类提供render等渲染方法。
 * @author yunfeng.cheng
 * @create 2016-08-03
 */
public class BaseAction {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	public Return processRequest(Method method, String methodName) throws Exception {
		logger.debug("will invoke method : " + methodName);
		if (method == null) {
			throw new NoSuchMethodException("Can NOT find specified method: " + methodName);
		}
        return exec(method);
	}
	
	/**
	 * 供内部使用的调用所指定方法的方法
	 * 
	 * @param method
	 *            所指定的需要被调用的方法
	 * @return 调用方法后所返回的结果
	 * @throws Exception
	 */
	private Return exec(Method method) throws Exception {

		Get getAnnotation = method.getAnnotation(Get.class);
		Post postAnnotation = method.getAnnotation(Post.class);
		if(getAnnotation != null && postAnnotation != null){
			throw new IllegalStateException("both get annotation and post annotation are used, only one can be used at an action");
		}
		
		//获得所调用方法的参数类型和所使用的Annotation数组
		Class<?>[] type = method.getParameterTypes();
		Annotation[][] annotationArray = method.getParameterAnnotations();

		//用于存放调用参数的对象数组
		Object[] paramTarget = new Object[type.length];
		
		//获取参数列表
		Map<String, List<String>> paramMap = getParamMap();
		
		//构造调用所需要的参数数组
		for (int i = 0; i < type.length; i++) {
			Class<?> typeClasz = (Class<?>) type[i];
			Annotation[] annotation = annotationArray[i];
			if (annotation == null || annotation.length == 0) {
				throw new Exception("Must specify a @Read annotation for every parameter in method: " + method.getName());
			}
			Read read = (Read) annotation[0];
			
			//生成当前的调用参数
			try{
				Object paramValue = getParamValue(paramMap, typeClasz, read, method, i);
                if(read.checkNull()){
                    ValidateUtil.checkNull(read.key(), paramValue);
                }
                paramTarget[i] = paramValue;
			}catch(Throwable e){
				throw getInvokeException(method,paramTarget,new IllegalArgumentException("参数不合法:" + read.key(), e));
			}
		}
		
		Return result = null;
		try {
			//调用，并得到调用结果
			result = (Return)method.invoke(this, paramTarget);
			
		} catch(InvocationTargetException e){
			throw getInvokeException(method,paramTarget,e.getCause());
		} catch(IllegalArgumentException e){
            throw getInvokeException(method,paramTarget,e);
		}
		return result;
	}
	
	/**
	 * GET 参数解析
	 * @param paramMap
	 * @param type
	 * @param read
	 * @param method
	 * @param index
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private Object getParamValue(Map<String, List<String>> paramMap, Class<?> type, Read read, Method method, int index) throws InstantiationException, IllegalAccessException{
		Object value = null;
		String key = read.key();
		if(key != null && key.length() > 0){
			List<String> params = paramMap.get(key);
			if(PrimitiveType.isPriType(type)){
				value = PriTypeConverter.getInstance().convertValue(params.get(0), type);
				
			}else if(type.isArray()){
				Object[] objArray = params.toArray();
				String[] strArray = objArray2StrArray(objArray);
				value = PriTypeConverter.getInstance().convertValue(strArray, type);
				
			}else if(List.class.isAssignableFrom(type)){
				List<Object> list = null;
				List<Class> types = GenericsUtils.getMethodGenericParameterTypes(method, index);
				Class<?> listType = types.size() == 1?types.get(0):String.class;
                if(List.class == type){
                    list = new ArrayList<Object>();
                }else{
                    list = (List<Object>) type.newInstance();
                }
                for(int i = 0; i < params.size(); i++){
                	if(params.get(i).length() > 0){
                		list.add(PriTypeConverter.getInstance().convertValue(params.get(i), listType));
                	}
                }
                value = list;
			}
		}
		return value;
	}
	
	private String[] objArray2StrArray(Object[] objArray){
		int length = objArray.length;
		String[] strArray = new String[length];
		for(int i=0; i<length; i++){
			strArray[i] = String.valueOf(objArray[i]);
		}
		return strArray;
	}
	
	/**
	 * 获取请求参数 Map
	 */
	private Map<String, List<String>> getParamMap(){
		Map<String, List<String>> paramMap = new HashMap<String, List<String>>();
		
		Object msg = DataHolder.getRequest();
		HttpRequest request = (HttpRequest) msg;
		HttpMethod method = request.method();
		if(method.equals(HttpMethod.GET)){
			String uri = request.uri();
			QueryStringDecoder queryDecoder = new QueryStringDecoder(uri, Charsets.toCharset(CharEncoding.UTF_8));
			paramMap = queryDecoder.parameters();
			
		}else if(method.equals(HttpMethod.POST)){
			FullHttpRequest fullRequest = (FullHttpRequest) msg;
			paramMap = getPostParamMap(fullRequest);
		}
		
		return paramMap;
	}
	
	private Map<String, List<String>> getPostParamMap(FullHttpRequest fullRequest) {
		Map<String, List<String>> paramMap = new HashMap<String, List<String>>();
		HttpHeaders headers = fullRequest.headers();
		String contentType = getContentType(headers);
		if(contentType.equals("application/json")){
			String jsonStr = fullRequest.content().toString(Charsets.toCharset(CharEncoding.UTF_8));
			JSONObject obj = JSON.parseObject(jsonStr);
			for(Entry<String, Object> item : obj.entrySet()){
				List<String> valueList = null;
				String key = item.getKey();
				Object value = item.getValue();
				Class<?> valueType = value.getClass();
				
				if(paramMap.containsKey(key)){
					valueList = paramMap.get(key);
				}else{
					valueList = new ArrayList<String>();
				}
				
				if(PrimitiveType.isPriType(valueType)){
					valueList.add(value.toString());
					paramMap.put(key, valueList);
					
				}else if(valueType.isArray()){
					int length = Array.getLength(value);
					for(int i=0; i<length; i++){
						String arrayItem = String.valueOf(Array.get(value, i));
						valueList.add(arrayItem);
					}
					paramMap.put(key, valueList);
					
				}else if(List.class.isAssignableFrom(valueType)){
					if(valueType.equals(JSONArray.class)){
						JSONArray jArray = JSONArray.parseArray(value.toString());
						for(int i=0; i<jArray.size(); i++){
							valueList.add(jArray.getString(i));
						}
					}else{
						valueList = (ArrayList<String>) value;
					}
					paramMap.put(key, valueList);
				}
			}
			
		}else if(contentType.equals("application/x-www-form-urlencoded")){
			String jsonStr = fullRequest.content().toString(Charsets.toCharset(CharEncoding.UTF_8));
			QueryStringDecoder queryDecoder = new QueryStringDecoder(jsonStr, false);
			paramMap = queryDecoder.parameters();
		}
		
		return paramMap;
	}
	
	private String getContentType(HttpHeaders headers){
		String typeStr = headers.get("Content-Type").toString();
		String[] list = typeStr.split(";");
		return list[0];
	}
	
	private ActionInvocationException getInvokeException(Method method,Object[] paramTarget,Throwable ex){
		String msg = "action method=" + method.getName() + ", params=" + Arrays.toString(paramTarget);
		return new ActionInvocationException(method.getName(),paramTarget,msg,ex);
	}
	
}
