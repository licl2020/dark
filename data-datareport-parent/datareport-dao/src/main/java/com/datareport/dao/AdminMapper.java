package com.datareport.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.datareport.bean.Admin;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author licl123
 * @since 2020-02-14
 */
public interface AdminMapper extends BaseMapper<Admin> {

	
	public List<Admin> Login(@Param("uName")String uName);
	
}
