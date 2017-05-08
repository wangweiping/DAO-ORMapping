package cn.mldn.service.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import cn.mldn.util.dbc.DatabaseConnection;
/**
 * 业务层优化--AOP的设计雏形
 * 动态代理设计模式
 * 更新操作需要事物的控制
 */
public class ServiceProxy implements InvocationHandler {
	private Object target; // 保存真实主题对象
	public Object bind(Object target) {
		this.target = target ;
		return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this) ;
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object retData = null ;	// 返回结果
		String methodName = method.getName() ;	// 接收方法名称
		boolean flag = methodName.startsWith("add") || methodName.startsWith("edit")
				|| methodName.startsWith("delete") ;
		try {	// 此处负责代理操作，其核心的本质在于进行数据库的关闭处理
			if (flag) {	// 更新处理，需要事务控制
				DatabaseConnection.getConnection().setAutoCommit(false);	// 关闭自动事务提交
			}
			retData = method.invoke(this.target, args) ;
			if (flag) {	// 更新处理，需要事务控制
				DatabaseConnection.getConnection().commit(); // 事务提交
			}
		} catch (Exception e) {
			if (flag) {	// 更新处理，需要事务控制
				DatabaseConnection.getConnection().rollback(); // 事务回滚
			}
			throw e;
		} finally {
			DatabaseConnection.close();
		}
		return retData; 
	}
}
