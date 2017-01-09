package com.cyfonly.nettice.core.convertor;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseSimpleTypeConverter implements ITypeConverter {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 实现了ITypeConverter中的相同方法
	 */
	public Object convertValue(Object value, Class<?> toType, Object... parmas) {
		
		/**
		 * 如果对象本身已经是所指定的类型则不进行转换直接返回
		 * 如果对象能够被复制，则返回复制后的对象
		 */
		if (value != null && toType.isInstance(value)) {
			if (value instanceof Cloneable) {
				if(value.getClass().isArray() && value.getClass().getComponentType() == String.class){
					return value; // 字符串数组虽然是Cloneable的子类，但并没有clone方法
				}
				try {
					Method m = value.getClass().getDeclaredMethod("clone", new Class[0]);
					m.setAccessible(true);
					return m.invoke(value, new Object[0]);
				} catch (Exception e) {
					logger.debug("Can not clone object " + value, e);
				}
			}

			return value;
		}

		/**
		 * 如果需要转换，且value为String类型并且长度为0，则按照null值进行处理
		 */
		if (value != null && value instanceof String && ((String)value).length() == 0) {
			value = null;
		}

		/**
		 * 不对Annotation, Interface,
		 * Enummeration类型进行转换。
		 */
		if (toType == null || (value == null && !toType.isPrimitive())
				|| toType.isInterface() || toType.isAnnotation()
				|| toType.isEnum()) {
			return null;
		}

		return doConvertValue(value, toType);
	}

	/**
	 * 需要被子类所实现的转换方法
	 * @param value 需要进行类型转换的对象
	 * @param toType　需要被转换成的类型
	 * @param params　转值时需要提供的可选参数
	 * @return　转换后所生成的对象，如果不能够进行转换则返回null
	 */
	protected abstract Object doConvertValue(Object value, Class<?> toType, Object... params);

}
