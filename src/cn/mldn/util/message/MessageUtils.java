package cn.mldn.util.message;

import java.util.ResourceBundle;
/**
 * 负责资源文件信息的读取处理操作
 * @author mldn
 */
public class MessageUtils {
	private ResourceBundle resource ;
	/**
	 * 在实例化对象时传入要读取资源文件的名称
	 * @param baseName 资源文件的名称
	 */
	public MessageUtils(String baseName) {
		this.resource = ResourceBundle.getBundle(baseName) ;
	}
	/**
	 * 根据key读取指定的信息
	 * @param key 要读取的key的信息
	 * @return 如果该key有内容则返回内容，如果没有内容则返回null
	 */
	public String getValue(String key) {
		return this.resource.getString(key) ;
	} 
}
