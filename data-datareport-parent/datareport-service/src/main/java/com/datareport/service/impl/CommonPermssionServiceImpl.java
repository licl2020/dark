package com.datareport.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.datareport.bean.CommonPermssion;
import com.datareport.bean.CommonRolePermssion;
import com.datareport.bean.ModularDto;
import com.datareport.common.msg.ResultTools;
import com.datareport.common.msg.ResultTools.Light;
import com.datareport.dao.CommonPermssionMapper;
import com.datareport.dao.CommonRolePermssionMapper;
import com.datareport.service.CommonPermssionService;

/**
 * <p>
 * 该表用来存储资源权限信息 服务实现类
 * </p>
 *
 * @author licl123
 * @since 2020-02-14
 */
@Service
public class CommonPermssionServiceImpl extends ServiceImpl<CommonPermssionMapper, CommonPermssion> implements CommonPermssionService {
	
	@Autowired
	private CommonPermssionMapper commonPermssionMapper;
	
	@Autowired
	private CommonRolePermssionMapper commonRolePermssionMapper;

	@Override
	public int commonPermssionAdd(CommonPermssion t) {
		// TODO Auto-generated method stub
		return commonPermssionMapper.commonPermssionAdd(t);
	}

	@Override
	public List<CommonPermssion> mysqlchilds(int id) {
		// TODO Auto-generated method stub
		return commonPermssionMapper.mysqlchilds(id);
	}

	@Override
	public List<ModularDto> findMenuAll(ModularDto t) {
		// TODO Auto-generated method stub
		return commonPermssionMapper.findMenuAll(t);
	}

	@Override
	public List<CommonPermssion> selectAuthList(int id) {
		// TODO Auto-generated method stub
		return commonPermssionMapper.selectAuthList(id);
	}

	@Override
	public ResultTools addCommonPermssion(CommonPermssion bean) {
		try {
			
    	  	//查询权限名称是否存在
            QueryWrapper<CommonPermssion> wrapper = new QueryWrapper<>();
            wrapper.eq("NAME",bean.getName());
            int count=commonPermssionMapper.selectCount(wrapper);
            if(count==0) {
            	//查询权限标识是否存在
                QueryWrapper<CommonPermssion> wrapper2 = new QueryWrapper<>();
                wrapper2.eq("PERMISSION",bean.getPermission());
                int count2=commonPermssionMapper.selectCount(wrapper2);
                if(count2==0) {
                	
                	 //保存权限信息
                	commonPermssionMapper.commonPermssionAdd(bean);
                	 //进行上级的是否有下级的逻辑判断
                	 updateParent(null, bean.getParentId(), 1);
                	 //将保存的权限信息绑定到超级管理员
                	 CommonRolePermssion crp = new CommonRolePermssion();
                	 crp.setResourceId(bean.getId());
                	 crp.setRoleId(2);
                	 commonRolePermssionMapper.insert(crp);
                	 return ResultTools.getInstance(Light.SUCCESS);
                }else {
                	//权限标识存在
                	return ResultTools.getInstance(-100,"权限标识已存在","error");
                }
            	
            }else {
	            	//权限名称存在
            	return ResultTools.getInstance(-100,"权限名称已存在","error");
            }
		} catch (Exception e) {
			return ResultTools.getInstance(Light.ERROR_201);
		}
  
	}

	@Override
	public ResultTools updateCommonPermssion(CommonPermssion bean) {
		try {
        	  List<CommonPermssion> list = commonPermssionMapper.mysqlchilds(bean.getId());
            boolean f = true;
            //遍历比较，如果当前选择的上级id等于自己查询书的下级id，则返回错误信息
            for (int i = 0; i < list.size(); i++) {
                if (bean.getParentId() == list.get(i).getId()) {
                    f = false;
                    break;
                }
            }
            
            if(f) {
          	  
            	//查询权限名称是否存在
                QueryWrapper<CommonPermssion> wrapper = new QueryWrapper<>();
                wrapper.eq("NAME",bean.getName());
                List<CommonPermssion> count=commonPermssionMapper.selectList(wrapper);
                if(count.size()==0 || (count.size()==1 && count.get(0).getId()==bean.getId())) {
                	//查询权限标识是否存在
                    QueryWrapper<CommonPermssion> wrapper2 = new QueryWrapper<>();
                    wrapper2.eq("PERMISSION",bean.getPermission());
                    List<CommonPermssion> count2=commonPermssionMapper.selectList(wrapper2);
                    if(count2.size()==0 || (count2.size()==1 && count2.get(0).getId()==bean.getId())) {
                  	  //更新权限信息
                    	commonPermssionMapper.updateById(bean);
                    	 //进行上级是否有下级的逻辑判断
                       updateParent(bean.getYldyo(), bean.getParentId(), 2);
                       return ResultTools.getInstance(Light.SUCCESS);
                    }else {
                    	//权限标识存在
                    	return ResultTools.getInstance(-100,"权限标识已存在","error");
                    }
                	
                }else {
    	            	//权限名称存在
                	return ResultTools.getInstance(-100,"权限名称已存在","error");
                }
          	  
            }else {
          	  //不能选择自己的下级为上级菜单
            	return ResultTools.getInstance(-100,"不能选择自己的下级为上级菜单","error");
            }
			} catch (Exception e) {
				return ResultTools.getInstance(Light.ERROR_201);
			}
	}

	@Override
	public ResultTools delCommonPermssion(CommonPermssion bean) {
    	try {
  		  //删除权限数据
  		  List<CommonPermssion> list = commonPermssionMapper.mysqlchilds(bean.getId());
  		  list.stream().forEach(a->{
  			 commonPermssionMapper.deleteById(a.getId());
      		  //删除关系表数据
      		  QueryWrapper<CommonRolePermssion> wrapper = new QueryWrapper<>();
                wrapper.eq("RESOURCE_ID",a.getId());
                commonRolePermssionMapper.delete(wrapper);
      		  //进行上级的是否有下级的逻辑判断
      		  updateParent(null, a.getParentId(), 3);
  		  });
  		return ResultTools.getInstance(Light.SUCCESS);
		} catch (Exception e) {
			 return ResultTools.getInstance(Light.ERROR_201);
		}
	}
	
	  /**
     * 查询是否有下级
     *
     * @param star_id
     * @param new_id
     */
    public void updateParent(Integer star_id, Integer new_id, Integer index) {

        
        if (index == 1) {

            //查询现在的上级id是否有下级
        	  QueryWrapper<CommonPermssion> wrapper = new QueryWrapper<>();
              wrapper.eq("PARENT_ID",new_id);
              int count = commonPermssionMapper.selectCount(wrapper);

            if (count > 0) {
            	CommonPermssion commonPermssion = new CommonPermssion();
            	commonPermssion.setmParentc(1);
            	commonPermssion.setId(new_id);
            	commonPermssionMapper.updateById(commonPermssion);
            }

        }

        if (index == 2) {
            if (star_id != new_id) {

            	 QueryWrapper<CommonPermssion> wrapper = new QueryWrapper<>();
                 wrapper.eq("PARENT_ID",star_id);
                 int count = commonPermssionMapper.selectCount(wrapper);
                if (count == 0) {
                	CommonPermssion commonPermssion = new CommonPermssion();
                	commonPermssion.setmParentc(2);
                	commonPermssion.setId(star_id);
                	commonPermssionMapper.updateById(commonPermssion);
                }


                //查询现在的上级id是否有下级
                QueryWrapper<CommonPermssion> wrapper2 = new QueryWrapper<>();
                wrapper2.eq("PARENT_ID",new_id);
                int count2 = commonPermssionMapper.selectCount(wrapper2);

                if (count2 > 0) {
                	CommonPermssion commonPermssion = new CommonPermssion();
                	commonPermssion.setmParentc(1);
                	commonPermssion.setId(new_id);
                	commonPermssionMapper.updateById(commonPermssion);
                }

            }
        }

        if (index == 3) {

            //查询现在的上级id是否有下级
        	  QueryWrapper<CommonPermssion> wrapper = new QueryWrapper<>();
              wrapper.eq("PARENT_ID",new_id);
              int count = commonPermssionMapper.selectCount(wrapper);
            if (count == 0) {
            	CommonPermssion commonPermssion = new CommonPermssion();
            	commonPermssion.setmParentc(2);
            	commonPermssion.setId(new_id);
            	commonPermssionMapper.updateById(commonPermssion);
            }

        }

    }

}
