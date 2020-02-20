package com.datareport.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.datareport.bean.CommonPermssion;
import com.datareport.bean.ModularDto;

/**
 * <p>
 * 该表用来存储资源权限信息 Mapper 接口
 * </p>
 *
 * @author licl123
 * @since 2020-02-14
 */
public interface CommonPermssionMapper extends BaseMapper<CommonPermssion> {

	
	/**
	 *单条数据增加 
	 * 
	 * @author：LICL
	 */
	public int commonPermssionAdd(CommonPermssion t);
	
	/**
	 * 根据角色id 查询出所有的权限信息
	 * @param id
	 * @return
	 */
	public List<CommonPermssion> selectAuthList(@Param("id")int id);
	
	/**
	 * mysql根据条件对象找返回平行结构
	 * 
	 * @Title: findByObject
	 * @Description: TODO
	 * @param t
	 * @return
	 * @return: T
	 */
	public List<CommonPermssion> mysqlchilds(@Param("id") int id) ;
	
	
	public List<ModularDto> findMenuAll(ModularDto t);
}
