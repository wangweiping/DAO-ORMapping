package cn.mldn.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	/** 
	 * 首字母大写处理
	 * @param str
	 * @return
	 */
	public static String initcap(String str) {
		return str.substring(0,1).toUpperCase() + str.substring(1) ;
	}
	/**
	 * 进行指定的字符串操作的处理，目的是通过“#{xx}”内容取的相应的标记信息
	 * @param mql 包含一个标准的结构的语句
	 * @return 返回的Map集合由两部分组成：索引顺序、内容；
	 */
	public static Map<Integer,String> handleMQL(String mql) {
		Map<Integer,String> map = new TreeMap<Integer,String>() ;
		Pattern pat = Pattern.compile("#\\{[a-zA-Z0-9\\.]+\\}") ;
		Matcher mat = pat.matcher(mql) ;
		int count = 1 ;	// 因为PreparedStatement从1设置
		while(mat.find()) {
			map.put(count ++, mat.group().replaceAll("[^a-zA-Z0-9\\.]+", "")) ;
		}
		return map ;
	}
	public static Map<String,String> handleMQL2(String mql) {
		Map<String,String> all = new HashMap<String,String>()  ;
		Pattern pat = Pattern.compile("\\$\\{[a-zA-Z0-9\\.]+\\}") ;
		Matcher mat = pat.matcher(mql) ;
		while(mat.find()) {
			String str = mat.group() ;
			all.put(str, mat.group().replaceAll("[^a-zA-Z0-9\\.]+", "")) ;
		}
		return all ;
	}
	
	public static String getSql(String mq,Map<String,String> all,Map<String,Object> param) {
		String sql = mq ;
		Iterator<Map.Entry<String,String>> iter = all.entrySet().iterator() ;
		while (iter.hasNext()) {
			Map.Entry<String, String> me = iter.next() ;
			System.out.println(me.getKey() + " *********** ");
			sql = sql.replaceFirst("\\$\\{"+me.getValue()+"\\}", param.get(me.getValue()).toString());
		}
		return sql ;
	} 
	
	/** 
	 * 处理MQL的数据，帮助生成“?”
	 * @param mql
	 * @return
	 */
	public static String getSQL(String mql) {
		return mql.replaceAll("\\#\\{[a-zA-Z0-9\\.]+\\}", "?") ;
	}
}
