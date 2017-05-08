package cn.mldn.util.orm;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import cn.mldn.util.StringUtils;
import cn.mldn.util.dbc.DatabaseConnection;
import cn.mldn.util.message.MessageUtils;
/**
 * 数据的增加处理里面最为头疼的问题实际上就是PreparedStatement问题了，因为如果此时
 * 数据表中有200个字段，则意味着你可能要使用setXxx()方法调用200次，这不可能做的。
 * 为了整体的改进方便，单独建立一个Session工具类
 * 因为每一个会话的操作都是独立的，所以用Session描述会更好一些
 */
public class Session {
	private static final String SQL_BASENAME = "cn.mldn.config.sql" ;
	private static MessageUtils messageUtils = new MessageUtils(SQL_BASENAME) ;
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> getAll(Class<?> cls,Map<String,Object> params,String methodName) {
		String mql = messageUtils.getValue(methodName) ;	// 取得原始标记
		String sql = StringUtils.getSQL(mql) ;
		if (sql.contains("$")) {
			Map<String,String> map = StringUtils.handleMQL2(mql) ;
			mql = StringUtils.getSql(mql, map, params) ;
			sql = StringUtils.getSQL(mql) ;
		} 
		try {
			PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql) ;
			ORMappingUtils.preparedHandle(mql, params, pstmt);
			ResultSet rs = pstmt.executeQuery() ;
			return (List<T>) ORMappingUtils.preparedResultSetList(pstmt.getMetaData(), rs, cls) ; 	
		} catch (Exception e) {
			e.printStackTrace(); 
		} 
		return null ; 
	}
	
	public static <T> T getStat(Class<?> cls,Map<String,Object> params,String methodName) {
		String mql = messageUtils.getValue(methodName) ;	// 取得原始标记
		String sql = StringUtils.getSQL(mql) ;
		if (sql.contains("$")) {
			Map<String,String> map = StringUtils.handleMQL2(mql) ;
			mql = StringUtils.getSql(mql, map, params) ;
			sql = StringUtils.getSQL(mql) ;
		} 
		try {
			PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql) ;
			ORMappingUtils.preparedHandle(mql, params, pstmt);
			ResultSet rs = pstmt.executeQuery() ;
			return ORMappingUtils.preparedResultSetObject(rs, cls) ; 	
		} catch (Exception e) {
			e.printStackTrace(); 
		} 
		return null ;
	}
	
	/**
	 * 根据id进行数据的查询操作实现
	 * @param cls 表示要操作的VO类型
	 * @param params 表示要进行设置的参数
	 * @return
	 */
	public static <T> T get(Class<?> cls,Map<String,Object> params,String methodName) {
		String mql = messageUtils.getValue(methodName) ;	// 取得原始标记
		String sql = StringUtils.getSQL(mql) ;
		try {
			PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql) ;
			ORMappingUtils.preparedHandle(mql, params, pstmt);
			ResultSet rs = pstmt.executeQuery() ;
			return ORMappingUtils.preparedResultSet(pstmt.getMetaData(), rs, cls) ; 	
		} catch (Exception e) {
			e.printStackTrace(); 
		} 
		return null ;
	}
	
	/**
	 * 进行数据的删除处理
	 * @param param 表示的要传输的数据
	 * @param methodName 要调用的方法
	 * @return
	 */
	public static int delete(Map<String,Object> param,String methodName) {
		String mql = messageUtils.getValue(methodName) ;	// 取得原始标记
		String sql = StringUtils.getSQL(mql) ;
		try {
			PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql) ;
			ORMappingUtils.preparedHandle(mql, param, pstmt);
			return pstmt.executeUpdate() ;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return 0 ;
	}
	/**
	 * 分开两个方法是为了让前面的调用更明确
	 * @param obj
	 * @param methodName
	 * @return
	 */
	public static int update(Object obj,String methodName) {
		return save(obj,methodName) ; 
	}
	/**
	 * 进行数据库的增加操作实现，该实现将根据VO类与SQL资源的访问来进行整体处理
	 * @param obj 要操作的类对象
	 * @param methodName 表示类对象的标记
	 * @return 增加成功影响的数据行数
	 */
	public static int save(Object obj,String methodName) {
		String mql = messageUtils.getValue(methodName) ;	// 取得原始标记
		String sql = StringUtils.getSQL(mql) ;
		try {
			PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql) ;
			ORMappingUtils.preparedHandle(mql, obj, pstmt);
			return pstmt.executeUpdate() ;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return 0 ;
	}
}
