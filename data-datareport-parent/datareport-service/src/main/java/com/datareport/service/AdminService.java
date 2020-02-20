package com.datareport.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.extension.service.IService;
import com.datareport.bean.Admin;
import com.datareport.common.msg.ResultTools;

public interface AdminService extends IService<Admin>{

	public List<Admin> Login(@Param("uName")String uName);
	
	
	/**
	 *   新增用户
	 * @param commonRole
	 * @return
	 */
	public ResultTools addAdmin(Admin bean);
	
	
	/**
	 *   编辑用户
	 * @param commonRole
	 * @return
	 */
	public ResultTools updateAdmin(Admin bean);
	
	
	
	/**
	 *   删除用户
	 * @param commonRole
	 * @return
	 */
	public ResultTools delAdmin(Admin bean);
	
	
	
	/**
	 *   更新用户状态
	 * @param commonRole
	 * @return
	 */
	public ResultTools updateAdminState(Admin bean);
	
	
	/**
	 *   重置密码
	 * @param commonRole
	 * @return
	 */
	public ResultTools resetpassword(Admin bean);
	
}
