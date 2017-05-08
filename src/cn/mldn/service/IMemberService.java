package cn.mldn.service;
import java.util.List;
import java.util.Map;

import cn.mldn.vo.Member;
/**
 * 编写member数据表的业务操作，一个业务需要调用多个数据层
 * @author mldn
 */
public interface IMemberService {
	/**
	 * 执行数据的增加操作，本操作需要按照如下步骤进行：<br>
	 * 1、需要判断给定的数据是否为空，以及数据是否正确；<br>
	 * 2、需要调用IMemberDAO.findById()判断该增加的数据ID是否存在；<br>
	 * 3、如果要追加的数据ID不存在则调用IMemberDAO.doCreate()进行数据保存。
	 * @param vo 要保存的数据
	 * @return 如果数据存在或者无法追加则返回false，否则返回true
	 * @throws Exception 以后可能会写一些自定义的异常（判断异常等），或者是SQL异常。
	 */
	public boolean add(Member vo) throws Exception ;
	/**
	 * 执行数据的修改操作，本操作需要按照如下步骤继续：<br>
	 * 1、需要判断给定的数据是否为空，以及数据是否正确；<br>
	 * 2、调用IMemberDAO.doUpdate()方法更新数据
	 * @param vo 要修改的新数据
	 * @return 如果数据本身验证有问题或者修改失败，那么返回false，否则返回true
	 * @throws Exception 以后可能会写一些自定义的异常（判断异常等），或者是SQL异常。
	 */
	public boolean edit(Member vo) throws Exception ;
	/**
	 * 进行数据的删除处理，调用IMemberDAO.doRemove()方法删除
	 * @param id 要删除的数据id
	 * @return 删除成功返回true，否则返回false（如果数据不存在删除也返回false）
	 * @throws Exception 以后可能会写一些自定义的异常（判断异常等），或者是SQL异常。
	 */
	public boolean delete(String id) throws Exception ;
	/**
	 * 查询表中的全部数据操作，执行IMemberDAO.findAll()方法
	 * @return 数据以集合的形式返回，如果没有数据则为空集合（size() == 0）
	 * @throws Exception 以后可能会写一些自定义的异常（判断异常等），或者是SQL异常。
	 */
	public List<Member> list() throws Exception ;
	/**
	 * 根据用户编号查询用户的完整信息，调用的IMemberDAO.findById()方法
	 * @param id 用户编号
	 * @return 返回用户信息 
	 * @throws Exception 以后可能会写一些自定义的异常（判断异常等），或者是SQL异常。
	 */
	public Member get(String id) throws Exception ;
	/**
	 * 进行数据的模糊分页查询，在查询的同时会返回操作的数据量，但是考虑到性能问题，
	 * 如果此时没有设置查询关键字（keyWorld、column为空、""），则分页查询全部。
	 * 本操作要执行如下的步骤：<br>
	 * 1、调用IMemberDAO.findAllSplit()方法取得数据；<br>
	 * 2、调用IMemberDAO.getAllCount()方法统计数据个数；<br>
	 * @param currentPage 当前所在页
	 * @param lineSize 每页显示的数据行数
	 * @param column 模糊查询列，如果没有内容则查询全部
	 * @param keyWord 模糊查询关键字
	 * @return 本操作需要返回有两类数据，所以使用Map集合，包括如下内容：<br>
	 * key = allMembers、value = IMemberDAO.findAllSplit()结果；<br>
	 * key = memberCount、value = IMemberDAO.getAllCount()结果；
	 * @throws Exception 以后可能会写一些自定义的异常（判断异常等），或者是SQL异常。
	 */
	public Map<String,Object> list(int currentPage,int lineSize,String column,String keyWord) throws Exception ;
}
