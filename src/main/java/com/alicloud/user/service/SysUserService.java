package com.alicloud.user.service;

import com.alicloud.user.domain.User;
import com.alicloud.user.vo.UserSearchVo;

import java.util.List;

public interface SysUserService {

    /**
     * 添加用户
     * @param sysUser
     * @return
     */
    User add(User sysUser);

    /**
     * 查询用户信息
     * @return
     */
    List<User> getUsersInfo(UserSearchVo userSearchVo);

    /**
     * 获取查询用户信息总记录数
     * @param userSearchVo
     * @return
     */
    Integer getUsersInfoCount(UserSearchVo userSearchVo);

    /**
     * 删除用户
     * @param id
     * @return
     */
    int deleteUser(Long id);

    /**
     * 修改用户
     * @param sysUser
     */
    int updateUser(User sysUser);
}
