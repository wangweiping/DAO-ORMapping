package cn.mldn.factory;

import cn.mldn.service.proxy.ServiceProxy;
import cn.mldn.util.message.MessageUtils;

public class Factory {
	private static final String DAO_BASENAME = "cn.mldn.config.dao" ;
	private static final String SERVICE_BASENAME = "cn.mldn.config.service" ;
	// 实例化MessageUtils类对象
	private static MessageUtils daoMU = new MessageUtils(DAO_BASENAME);
	private static MessageUtils serviceMU = new MessageUtils(SERVICE_BASENAME) ;

	private Factory() {} ;
	@SuppressWarnings("unchecked")
	public static <T> T getServiceInstance(String className) {
		try {
			return (T) new ServiceProxy().bind( 
					Class.forName(serviceMU.getValue(className)).newInstance());  
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null ;
	}
	/**
	 * 提供有DAO接口子类的对象实例化 
	 * @param className 标记的名称，与dao.properties中定义的内容相符
	 * @return 返回接口对象IXxxDAO
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getDAOInstance(String className) {
		try {
			return (T) Class.forName(daoMU.getValue(className)).newInstance() ;  
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null ;
	}
}
