package com.datareport.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.datareport.bean.SystemLog;
import com.datareport.dao.SystemLogMapper;
import com.datareport.service.ISystemLogService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author uncle
 * @since 2019-08-30
 */
@Service
@Transactional(propagation=Propagation.NESTED,isolation=Isolation.DEFAULT,readOnly = false)
public class SystemLogServiceImpl extends ServiceImpl< SystemLogMapper, SystemLog> implements ISystemLogService {

}
