package cn.mldn.util.orm;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import cn.mldn.util.StringUtils;

public class BeanOperation {
	/**
	 * 设置属性的内容，通过反射进行setter方法调用
	 * @param columnName 属性名称
	 * @param obj 要设置的VO类对象
	 */
	public static void setValue(String columnName,Object obj,Object value) throws Exception {
		Field field = obj.getClass().getDeclaredField(columnName) ;
		Method setterMethod = obj.getClass().getMethod("set" + StringUtils.initcap(columnName.toLowerCase()),field.getType() ) ;
		setterMethod.invoke(obj, value) ;
	}
	
	/**
	 * 根据属性名称以及对象并且利用反射取得相应的属性内容和它的类型
	 * @param fieldName 属性名称
	 * @param obj 操作对象
	 * @return 包含有属性类型、名称、内容的对象信息 
	 */
	public static FieldTypeAndValue handleField(String fieldName, Object obj) {
		try {
			Method getterMethod = obj.getClass()
					.getMethod("get" + StringUtils.initcap(fieldName));
			Field field = obj.getClass().getDeclaredField(fieldName);
			return new FieldTypeAndValue(fieldName,
					field.getType().getSimpleName(), getterMethod.invoke(obj));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null ;
	}
}
