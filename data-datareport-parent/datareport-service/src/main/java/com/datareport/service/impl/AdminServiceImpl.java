package com.datareport.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.datareport.bean.Admin;
import com.datareport.common.enumeration.RedisConstant;
import com.datareport.common.md5.Md5Utils;
import com.datareport.common.msg.ResultTools;
import com.datareport.common.msg.ResultTools.Light;
import com.datareport.dao.AdminMapper;
import com.datareport.service.AdminService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author licl123
 * @since 2020-02-14
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

	@Autowired
    private AdminMapper adminMapper;
	
	@Override
	public List<Admin> Login(String uName) {
		// TODO Auto-generated method stub
		return adminMapper.Login(uName);
	}

	@Override
	public ResultTools addAdmin(Admin bean) {
		try {
			QueryWrapper<Admin> wrapper = new QueryWrapper<>();
			wrapper.eq("u_name", bean.getuName());
			int count = SqlHelper.retCount(adminMapper.selectCount(wrapper));
			if (count == 0) {
				bean.setCreateTime(System.currentTimeMillis());
				bean.setPassword(Md5Utils.getMD5Pwd(bean.getuName(), RedisConstant.PASSWORD));
				adminMapper.insert(bean);
			return ResultTools.getInstance(Light.SUCCESS);
			} else {
			return ResultTools.getInstance(-100, "用户名已经存在", "error");
			}
	} catch (Exception e) {
		return ResultTools.getInstance(Light.ERROR_201);
	}
	}

	@Override
	public ResultTools updateAdmin(Admin bean) {
		try {
			QueryWrapper<Admin> wrapper = new QueryWrapper<>();
			wrapper.eq("u_name", bean.getuName());
			List<Admin> count = adminMapper.selectList(wrapper);
			if (count.size() == 0 || (count.size() == 1 && count.get(0).getId() == bean.getId())) {
				adminMapper.updateById(bean);
				return ResultTools.getInstance(Light.SUCCESS);
			} else {
				return ResultTools.getInstance(-100, "用户名已经存在", "error");
			}
	} catch (Exception e) {
		return ResultTools.getInstance(Light.ERROR_201);
	}
	}

	@Override
	public ResultTools delAdmin(Admin bean) {
		// TODO Auto-generated method stub
		try {
			Admin admin = new Admin();
			admin.setState(3);
			admin.setId(bean.getId());
			adminMapper.updateById(admin);
			return ResultTools.getInstance(Light.SUCCESS);
		} catch (Exception e) {
			return ResultTools.getInstance(Light.ERROR_201);
		}
	}

	@Override
	public ResultTools updateAdminState(Admin bean) {
		try {
			Admin admin = new Admin();
			admin.setId(bean.getId());
			admin.setState(bean.getState());
			adminMapper.updateById(admin);
			return ResultTools.getInstance(Light.SUCCESS);
		} catch (Exception e) {
			return ResultTools.getInstance(Light.ERROR_201);
		}
	}

	@Override
	public ResultTools resetpassword(Admin bean) {
		try {
			Admin admin = adminMapper.selectById(bean.getId());
			admin.setPassword(Md5Utils.getMD5Pwd(admin.getuName(), RedisConstant.PASSWORD));
			adminMapper.updateById(admin);
			return ResultTools.getInstance(Light.SUCCESS);
		} catch (Exception e) {
			return ResultTools.getInstance(Light.ERROR_201);
		}
	}

	

}
