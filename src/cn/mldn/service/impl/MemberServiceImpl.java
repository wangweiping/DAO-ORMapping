package cn.mldn.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.mldn.dao.IMemberDAO;
import cn.mldn.factory.Factory;
import cn.mldn.service.IMemberService;
import cn.mldn.util.valid.Validations;
import cn.mldn.vo.Member;
/**
 * 一旦实例化了业务层的接口子类，就意味着需要进行数据库的连接处理
 * @author mldn
 */
public class MemberServiceImpl implements IMemberService {
	IMemberDAO memberDAO = Factory.getDAOInstance("member.dao") ;
	@Override
	public boolean add(Member vo) throws Exception {
		if (vo == null) { // 没有数据要处理
			return false;
		}
		if (!Validations.validateAge(vo.getAge(), vo.getBirthday())) {
			return false;
		}
		if (memberDAO.findById(vo.getMid()) == null) { // 现在要追加的ID数据不存在
			return memberDAO.doCreate(vo); // 追加数据
		}
		return false;
	}
	@Override
	public Member get(String id) throws Exception {
		if (id == null) { // 没有数据要处理
			return null;
		}
		return memberDAO.findById(id);
	}
	@Override
	public boolean edit(Member vo) throws Exception {
		if (vo == null) { // 没有数据要处理
			return false;
		}
		if (!Validations.validateAge(vo.getAge(), vo.getBirthday())) {
			return false;
		}
		return memberDAO.doUpdate(vo);
	}
	@Override
	public boolean delete(String id) throws Exception {
		if (id == null || "".equals(id)) {
			return false;
		}
		return memberDAO.doRemove(id);
	}
	@Override
	public List<Member> list() throws Exception {
		return memberDAO.findAll();
	}
	@Override
	public Map<String, Object> list(int currentPage, int lineSize, String column, String keyWord) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if (column == null || keyWord == null || "".equals(column) || "".equals(keyWord)) {
			map.put("allMembers", memberDAO.findAllSplit(currentPage, lineSize));
			map.put("memberCount", memberDAO.getAllCount());
		} else {
			map.put("allMembers", memberDAO.findAllSplit(currentPage, lineSize, column, keyWord));
			map.put("memberCount", memberDAO.getAllCount(column, keyWord));
		}
		return map;
	}

}
