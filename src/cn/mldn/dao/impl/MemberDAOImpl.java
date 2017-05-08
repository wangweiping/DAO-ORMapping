package cn.mldn.dao.impl;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.mldn.dao.IMemberDAO;
import cn.mldn.util.orm.Session;
import cn.mldn.vo.Member;
/**
 * 数据层主要使用PreparedStatement处理，而要想取得PreparedStatement接口对象就必须依靠Connection一个业
 * 务需要调用多次数据层处理，那么最简单的做法就是让数据层接收一个Connection的引用，该引用由业务层传递进来
 * （每一个要在数据库里面执行的SQL语句都称为一个原子性操作）
 * @author mldn
 */
public class MemberDAOImpl implements IMemberDAO {
	
	@Override
	public boolean doCreate(Member vo) throws SQLException {
		return Session.save(vo, "Member.doCreate.SQL") > 0  ;
	}
	@Override
	public boolean doUpdate(Member vo) throws SQLException {
		return Session.update(vo, "Member.doCreate.SQL") > 0 ;
	}
	@Override
	public boolean doRemove(String id) throws SQLException {
		Map<String,Object> param = new HashMap<String,Object>() ;
		param.put("mid", id) ;
		return Session.delete(param, "Member.doRemove.SQL") > 0  ;
	} 
	@Override
	public Member findById(String id) throws SQLException {
		Map<String,Object> param = new HashMap<String,Object>() ;
		param.put("mid", id) ;
		Member vo = Session.get(Member.class, param, "Member.findById.SQL") ;
		return vo;
	}
	@Override
	public List<Member> findAll() throws SQLException {
		Map<String,Object> param = new HashMap<String,Object>() ;
		return Session.getAll(Member.class, param, "Member.findAll.SQL");
	} 
	@Override
	public List<Member> findAllSplit(Integer currentPage, Integer lineSize) throws SQLException {
		Map<String,Object> param = new HashMap<String,Object>() ;
		param.put("start", (currentPage - 1) * lineSize) ;
		param.put("end", currentPage * lineSize) ;
		return Session.getAll(Member.class, param, "Member.findAllSplit.SQL");
	}
	@Override
	public List<Member> findAllSplit(Integer currentPage, Integer lineSize,
			String column, String keyWord) throws SQLException {
		Map<String,Object> param = new HashMap<String,Object>() ;
		param.put("start", (currentPage - 1) * lineSize) ;
		param.put("end", currentPage * lineSize) ;
		param.put("column", column) ;
		param.put("keyWord", "%"+keyWord+"%") ;
		return Session.getAll(Member.class, param, "Member.findAllSplitLike.SQL");
	} 
	@Override
	public Integer getAllCount() throws SQLException {
		Map<String,Object> param = new HashMap<String,Object>() ;
		Integer count = Session.getStat(Integer.class, param, "Member.getAllCount.SQL") ;
		return count; 
	}
	@Override
	public Integer getAllCount(String column, String keyWord) throws SQLException {
		Map<String,Object> param = new HashMap<String,Object>() ;
		param.put("column", column) ;
		param.put("keyWord", "%"+keyWord+"%") ;
		Integer count = Session.getStat(Integer.class, param, "Member.getAllCountLike.SQL") ;
		return count;
	}
}
