package cn.mldn.util.dbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 为了更加方便的传输关键的引用对象（Connection），所以在本类之中将利用ThreadLocal进行数据库连接的保存
 * 这个类中的ThreadLocal应该作为一个公共的数据，公共的数据使用static声明
 * @author mldn
 */
public class DatabaseConnection  {
	private static final String DBDRIVER = "oracle.jdbc.driver.OracleDriver" ;
	private static final String DBURL = "jdbc:oracle:thin:@localhost:1521:mldn" ;
	private static final String USER = "scott" ;
	private static final String PASSWORD = "tiger" ;
	private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>() ;
	/**
	 * 取得数据库的连接对象，如果在取得的时候没有进行连接则自动创建新的连接
	 * 但是需要防止同一个线程可能重复调用此操作的问题
	 * @return
	 */
	public static Connection getConnection() {
		Connection conn = threadLocal.get() ;	// 首先判断一下在ThreadLocal里面是否保存有当前连接对象
		if (conn == null) {	// 如果此时的连接对象是空，那么就表示没有连接过，则创建一个新的连接
			conn = rebuildConnection() ;	// 创建新的连接
			threadLocal.set(conn);	// 保存到ThreadLocal之中，以便一个线程执行多次数据库的时候使用
		}
		return conn ;	// 返回连接对象
	}
	/**
	 * 重新建立新的数据库连接
	 * @return Connection接口对象
	 */
	private static Connection rebuildConnection() {	// 创建新的连接对象
		try {
			Class.forName(DBDRIVER) ;
			return DriverManager.getConnection(DBURL,USER,PASSWORD); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null ;
	}
	/**
	 * 关闭数据库连接
	 */
	public static void close() {
		System.out.println(Thread.currentThread().getName() + " 关闭数据库连接。");
		Connection conn = threadLocal.get() ;	// 取得数据库连接
		if (conn != null) {
			try {
				conn.close();
				threadLocal.remove(); 	// 从ThreadLocal中删除掉保存的数据
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
