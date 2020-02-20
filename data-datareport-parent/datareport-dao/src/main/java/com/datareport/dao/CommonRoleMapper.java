package com.datareport.dao;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.datareport.bean.CommonRole;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author licl123
 * @since 2020-02-14
 */
public interface CommonRoleMapper extends BaseMapper<CommonRole> {

	/**
	 *根据id值查询
	 * 
	 * @author：LICL Date 2017年5月12日 上午10:29:38
	 * @param bean
	 * @return bean
	 * @throws SQLException
	 */
	public CommonRole getId(@Param("id") String id) throws SQLException;
}
