package com.alicloud.user.contorller;

import com.alicloud.user.annotation.UserLoginToken;
import com.alicloud.user.domain.User;
import com.alicloud.user.service.SysUserService;
import com.alicloud.user.util.JsonUtil;
import com.alicloud.user.vo.UserSearchResponse;
import com.alicloud.user.vo.UserSearchVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class ApiContorller {
    private final static Logger logger = LoggerFactory.getLogger(ApiContorller.class);
    @Autowired
    private RestTemplate restTemplate;

    @Value("${user2_service.url}")
    private String user2Usrl;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 验证用户名密码
     * @param sysUser
     * @return
     */
    @RequestMapping("/user/verification")
    @UserLoginToken
    public boolean verification(@RequestBody User sysUser){
        logger.info("verification receive message : {}",JsonUtil.object2Json(sysUser));
         if(sysUserService.add(sysUser) != null){
            return true;
        }
        return false;
    }

    /**
     * 添加用户，会调用user2服务做实际的添加操作，以验证阿里云链路分析
     * @param sysUser
     * @return
     */
    @RequestMapping("/user/add")
    @UserLoginToken
    public boolean addUser(@RequestBody User sysUser){
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> requestEntity = new HttpEntity<>(sysUser, requestHeaders);
        return restTemplate.postForObject(user2Usrl+"/user/add", requestEntity, Boolean.class);
    }

    /**
     * 用户删除
     * @param id
     * @return
     */
    @RequestMapping("/user/delete")
    @UserLoginToken
    public int deleteUser(@RequestParam("id") Long id){
        return sysUserService.deleteUser(id);
    }

    /**
     * 用户更新
     * @param sysUser
     * @return
     */
    @RequestMapping("/user/update")
    @UserLoginToken
    public int updateUser(@RequestBody User sysUser){
        return sysUserService.updateUser(sysUser);
    }

    /**
     * 分页获取用户信息
     * @param userSearchVo
     * @return
     */
    @RequestMapping("/users/get")
    @UserLoginToken
    public String getUsersInfo(@RequestBody UserSearchVo userSearchVo){
        UserSearchResponse userSearchResponse = new UserSearchResponse();
        userSearchResponse.setSysUsers(sysUserService.getUsersInfo(userSearchVo));
        userSearchResponse.setTotalCount(sysUserService.getUsersInfoCount(userSearchVo));
        return JsonUtil.object2Json(userSearchResponse);
    }
}
