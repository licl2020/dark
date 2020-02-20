package com.datareport.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.datareport.bean.CommonRole;
import com.datareport.common.msg.ResultTools;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author licl123
 * @since 2020-02-14
 */
public interface CommonRoleService extends IService<CommonRole> {
	
	
	/**
	 *   新增角色
	 * @param commonRole
	 * @return
	 */
	public ResultTools addRole(CommonRole bean);
	
	
	/**
	 *   编辑角色
	 * @param commonRole
	 * @return
	 */
	public ResultTools updateRole(CommonRole bean);
	
	
	
	/**
	 *   删除角色
	 * @param commonRole
	 * @return
	 */
	public ResultTools delRole(CommonRole bean);
	
	
	
	/**
	 *   保存角色权限
	 * @param commonRole
	 * @return
	 */
	public ResultTools saveRoleAuth(String item,int roleId);
	

}
