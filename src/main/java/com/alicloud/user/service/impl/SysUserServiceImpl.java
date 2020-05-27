package com.alicloud.user.service.impl;

import com.alicloud.user.domain.User;
import com.alicloud.user.service.SysUserService;
import com.alicloud.user.vo.UserSearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.teasoft.bee.osql.IncludeType;
import org.teasoft.bee.osql.PreparedSql;
import org.teasoft.bee.osql.service.ObjSQLRichService;
import org.teasoft.honey.osql.core.BeeFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private ObjSQLRichService objSQLRichService;

    @Override
    public User add(User sysUser) {
        return objSQLRichService.selectOne(sysUser);
    }

    @Override
    public List<User> getUsersInfo(UserSearchVo userSearchVo) {
        return searcheUserByVariable(userSearchVo);
    }

    @Override
    public Integer getUsersInfoCount(UserSearchVo userSearchVo) {
        String sql = "select count(*) from user where username like #{%name%}";
        PreparedSql preparedSql = BeeFactory.getHoneyFactory().getPreparedSql();
        Map<String, Object> map = new HashMap<>();
        map.put("name",userSearchVo.getName());
        String count =   preparedSql.selectFun(sql,map);
        return Integer.valueOf(count);
    }

    @Override
    public int deleteUser(Long id) {
        return objSQLRichService.deleteById(User.class,id);
    }

    @Override
    public int updateUser(User sysUser) {
        return objSQLRichService.update(sysUser, IncludeType.INCLUDE_EMPTY);
    }
    /**
     * 通过变量的占位语句查询数据
     * @return
     */
    private List<User> searcheUserByVariable(UserSearchVo userSearchVo){
        String sql ="select * from user where username like #{%name%} limit #{pageNo},#{pageSize}"; //可加入到配置文件
        PreparedSql preparedSql = BeeFactory.getHoneyFactory().getPreparedSql();
        Map<String, Object> map = new HashMap<>();
        map.put("name",userSearchVo.getName());
        map.put("pageNo",(userSearchVo.getPageNo()-1)*userSearchVo.getPageSize());
        map.put("pageSize",userSearchVo.getPageSize());
        List<User> sysUserList =  preparedSql.select(sql,new User(),map);
        return sysUserList;
    }


}
