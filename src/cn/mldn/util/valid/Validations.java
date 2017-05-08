package cn.mldn.util.valid;
import java.util.Date;
/**
 * 实现具体的数据验证的处理操作类
 * @author mldn
 */
public class Validations {
	/**
	 * 验证距今天为止生日计算是否与年龄相符
	 * @param age 验证的年龄
	 * @param birthday 传入的生日信息
	 * @return 如果年龄和生日相符，那么返回true，否则返回false
	 */
	public static boolean validateAge(int age,Date birthday) {
		if (validateAge(age)) {	// 表示现在年龄正常
			Date currentDate = new Date() ;	// 验证今天的日期
			if (currentDate.after(birthday)) {	// 生日合法
				int calAge = (int) ((currentDate.getTime() - birthday.getTime()) /1000 / 60 / 60 / 24 / 366) ;
				if ((age - 1) <= calAge && calAge <= (age + 1) ) {
					return true ;
				}
			}
		}
		return false ;
	}
	/**
	 * 验证年龄是否合法，合法的年龄指的是大于0
	 * @param age 验证的年龄
	 * @return 合法返回true、非法返回的是false
	 */
	public static boolean validateAge(int age) {
		return age >= 0 ;
	}
}
