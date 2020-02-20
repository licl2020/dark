package com.datareport.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.datareport.bean.Admin;
import com.datareport.bean.CommonRole;
import com.datareport.bean.CommonRolePermssion;
import com.datareport.common.String.StringUtil;
import com.datareport.common.msg.ResultTools;
import com.datareport.common.msg.ResultTools.Light;
import com.datareport.dao.AdminMapper;
import com.datareport.dao.CommonRoleMapper;
import com.datareport.dao.CommonRolePermssionMapper;
import com.datareport.service.CommonRoleService;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author licl123
 * @since 2020-02-14
 */
@Service
public class CommonRoleServiceImpl extends ServiceImpl<CommonRoleMapper, CommonRole> implements CommonRoleService {

	@Autowired
    private CommonRoleMapper commonRoleMapper;

	
	@Autowired
	private CommonRolePermssionMapper commonRolePermssionMapper;
	

	@Autowired
    private AdminMapper adminMapper;
	
	@Override
	public ResultTools addRole(CommonRole bean) {
		try {
			QueryWrapper<CommonRole> wrapper = new QueryWrapper<>();
			wrapper.eq("NAME", bean.getName());
			int count = SqlHelper.retCount(commonRoleMapper.selectCount(wrapper));
			if (count == 0) {
				CommonRole role = new CommonRole();
				role.setName(bean.getName());
				commonRoleMapper.insert(role);
				return ResultTools.getInstance(Light.SUCCESS);
			} else {
				return ResultTools.getInstance(-100, "角色名称已经存在", "error");
			}
	} catch (Exception e) {
		return ResultTools.getInstance(Light.ERROR_201);
	}
	}

	@Override
	public ResultTools updateRole(CommonRole bean) {
		try {
			QueryWrapper<CommonRole> wrapper = new QueryWrapper<>();
			wrapper.eq("NAME", bean.getName());
			List<CommonRole> list = commonRoleMapper.selectList(wrapper);
			if (list.size() == 0 || (list.size() == 1 && list.get(0).getId() == bean.getId())) {
				CommonRole role = new CommonRole();
				role.setName(bean.getName());
				role.setId(bean.getId());
				commonRoleMapper.updateById(role);
				return ResultTools.getInstance(Light.SUCCESS);
			} else {
				return ResultTools.getInstance(-100, "角色名称已经存在", "error");
			}
	} catch (Exception e) {
		return ResultTools.getInstance(Light.ERROR_201);
	}
	}

	@Override
	public ResultTools delRole(CommonRole bean) {
		try {
			QueryWrapper<Admin> wrapper = new QueryWrapper<>();
			wrapper.eq("r_id", bean.getId());
			int count = SqlHelper.retCount(adminMapper.selectCount(wrapper));
			if (count == 0) {
				Map<String, Object> columnMap = new HashMap<String, Object>();
				columnMap.put("ROLE_ID", bean.getId());
				commonRolePermssionMapper.deleteByMap(columnMap);
				commonRoleMapper.deleteById(bean.getId());
				return ResultTools.getInstance(Light.SUCCESS);
			} else {
				return ResultTools.getInstance(-100, "角色已经绑定用户,请先移除用户", "error");
			}

		} catch (Exception e) {
			return ResultTools.getInstance(Light.ERROR_201);
		}
	}

	@Override
	public ResultTools saveRoleAuth(String items, int roleId) {
		try {
			// 清除当前角色的所有权限数据
			QueryWrapper<CommonRolePermssion> wrapper = new QueryWrapper<>();
			wrapper.eq("ROLE_ID",roleId);
			commonRolePermssionMapper.delete(wrapper);
			// 分割数据
			String[] item = items.split(",");

			// 判断数组是否为空
			if (!StringUtil.isEmpty(items) && item.length >= 0) {

				// 遍历添加权限数据
				for (int i = 0; i < item.length; i++) {
					CommonRolePermssion somrole = new CommonRolePermssion();
					somrole.setResourceId(Integer.valueOf(item[i]));
					somrole.setRoleId(roleId);
					commonRolePermssionMapper.insert(somrole);
				}
			}
			return ResultTools.getInstance(Light.SUCCESS);

		} catch (Exception e) {
			return ResultTools.getInstance(Light.ERROR_201);
		}
	}

}
