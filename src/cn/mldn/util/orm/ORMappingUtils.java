package cn.mldn.util.orm;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.mldn.util.StringUtils;

public class ORMappingUtils {
	/**
	 * 处理集合数据的结果集操作
	 * @param rsmd
	 * @param rs
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> preparedResultSetList(ResultSetMetaData rsmd , ResultSet rs,Class<?> cls) throws Exception  {
		List<T> all = new ArrayList<T>() ;
		while (rs.next()) {	// 现在有内容
			Object obj = cls.newInstance() ;	// 实例化指定类的对象
			for (int x = 1 ; x <= rsmd.getColumnCount() ; x ++) {
				try {
					BeanOperation.setValue(rsmd.getColumnName(x).toLowerCase(), obj,
							getResultValue(rsmd.getColumnName(x).toLowerCase(), obj,
									rs, x));
				}catch(Exception e) {}
			}
			all.add((T) obj) ;
		}
		return all ; 
	} 
	
	@SuppressWarnings("unchecked")
	public static <T> T preparedResultSet(ResultSetMetaData rsmd , ResultSet rs,Class<?> cls) throws Exception  {
		if (rs.next()) {	// 现在有内容
			Object obj = cls.newInstance() ;	// 实例化指定类的对象
			for (int x = 1 ; x <= rsmd.getColumnCount() ; x ++) {
				BeanOperation.setValue(rsmd.getColumnName(x).toLowerCase(), obj,
						getResultValue(rsmd.getColumnName(x).toLowerCase(), obj,
								rs, x)); 
			}
			return (T) obj ;
		}
		return null ;
	} 
	// 统计查询的处理操作
	@SuppressWarnings("unchecked")
	public static <T> T preparedResultSetObject(ResultSet rs,Class<?> cls) throws Exception  {
		if (rs.next()) {	// 现在有内容
			if ("Integer".equals(cls.getSimpleName())) {
				Object obj = rs.getInt(1) ;
				return (T) obj ;
			}
		} 
		return null ;
	} 
	
	private static Object getResultValue(String attribute,Object obj,ResultSet rs,int index) throws Exception {
		Field field = obj.getClass().getDeclaredField(attribute) ;
		if ("String".equals(field.getType().getSimpleName())) {	// 按照字符串设置
			return rs.getString(index) ;
		} else if ("int".equals(field.getType().getSimpleName()) || "Integer".equals(field.getType().getSimpleName())) {
			return rs.getInt(index) ;
		} else if ("double".equalsIgnoreCase(field.getType().getSimpleName())) {
			return rs.getDouble(index) ;
		} else if ("Date".equals(field.getType().getSimpleName())) {
			return rs.getDate(index) ;
		}
		return null ;
	}
	
	public static void preparedHandle(String mql,Map<String,Object> param,PreparedStatement pstmt) {
		Map<Integer,String> paramName = StringUtils.handleMQL(mql) ; 	// 所有参数名称
		Iterator<Map.Entry<Integer,String>> iter = paramName.entrySet().iterator() ;
		while (iter.hasNext()) {
			Map.Entry<Integer, String> me = iter.next() ;
			try {// 随后应该根据反射的信息取得要设置的内容，这个内容设置到PreapredStatement对象之中
				setPreparedStatementContent(me.getKey(), param.get(me.getValue()), pstmt);
			} catch (SQLException e) {
//				e.printStackTrace();
			}
		}
	}
	
	public static void preparedHandle(String mql,Object vo,PreparedStatement pstmt) {
		Map<Integer,String> paramName = StringUtils.handleMQL(mql) ; 	// 所有参数名称
		// 下面需要针对于Map集合进行循环的操作处理
		Iterator<Map.Entry<Integer,String>> iter = paramName.entrySet().iterator() ;
		while (iter.hasNext()) {
			Map.Entry<Integer, String> me = iter.next() ;
			try {// 随后应该根据反射的信息取得要设置的内容，这个内容设置到PreapredStatement对象之中
				setPreparedStatementContent(me.getKey(), BeanOperation.handleField(me.getValue(), vo), pstmt);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 负责设置指定索引内容的PreparedStatement对象的数据
	 * @param index 操作索引
	 * @param ftav 包含的数据
	 */
	private static void setPreparedStatementContent(int index,FieldTypeAndValue ftav,PreparedStatement pstmt) throws SQLException {
		if ("String".equals(ftav.getType())) {	// 按照字符串设置
			pstmt.setString(index, ftav.getValue().toString());
		} else if ("int".equals(ftav.getType()) || "Integer".equals(ftav.getType())) {
			pstmt.setInt(index, (Integer)ftav.getValue());
		} else if ("double".equalsIgnoreCase(ftav.getType())) {
			pstmt.setDouble(index, (Double)ftav.getValue());
		} else if ("Date".equals(ftav.getType())) {
			java.util.Date date = (java.util.Date) ftav.getValue() ;
			pstmt.setDate(index, new java.sql.Date(date.getTime()));
		}
	} 
	private static void setPreparedStatementContent(int index,Object data,PreparedStatement pstmt) throws SQLException {
		if ("String".equals(data.getClass().getSimpleName())) {	// 按照字符串设置
			pstmt.setString(index, data.toString());
		} else if ("int".equals(data.getClass().getSimpleName()) || "Integer".equals(data.getClass().getSimpleName())) {
			pstmt.setInt(index, (Integer)data);
		} else if ("double".equalsIgnoreCase(data.getClass().getSimpleName())) {
			pstmt.setDouble(index, (Double)data);
		} else if ("Date".equals(data.getClass().getSimpleName())) {
			java.util.Date date = (java.util.Date) data ;
			pstmt.setDate(index, new java.sql.Date(date.getTime()));
		}
	}
}
