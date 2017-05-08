package cn.mldn.test.service;
/*
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import cn.mldn.factory.Factory;
import cn.mldn.service.IMemberService;
import cn.mldn.vo.Member;
import junit.framework.TestCase;
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class IMemberServiceTest {
	private Date birthday = null;
	{ // 构造块可以编写代码
		try {
			birthday = new SimpleDateFormat("yyyy-MM-dd").parse("2000-01-01");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	IMemberService service = Factory.getServiceInstance("member.service") ;
	@Test
	public void testAdd() {
		System.out.println("--add--");
		Member vo = new Member();
		vo.setMid("101");
		vo.setName("b-add");
		vo.setAge(17);
		vo.setBirthday(birthday);
		vo.setNote("www.mldn.cn");
		vo.setSex("男");
		try {
			TestCase.assertTrue(service.add(vo)); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testEdit() {
		System.out.println("--edit--");
		Member vo = new Member();
		vo.setMid("101");
		vo.setName("修改ORM-add");
		vo.setAge(19);
		vo.setBirthday(birthday);
		vo.setNote("www.java.cn");
		vo.setSex("女");
		try {
			
			TestCase.assertTrue(service.edit(vo));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDelete() {
		System.out.println("--deletet--");
		try {
			
			TestCase.assertTrue(service.delete("101"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testList() {
		fail("Not yet implemented");
	}

	@Test
	public void testGet() {
		System.out.println("--get--");
		try {
			
			TestCase.assertNotNull(service.get("101"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testListIntIntStringString() {
		fail("Not yet implemented");
	}
}
*/


















