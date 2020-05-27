package com.alicloud.user.vo;

import com.alicloud.user.domain.User;

import java.io.Serializable;
import java.util.List;

public class UserSearchResponse implements Serializable {
    Integer totalCount;
    List<User> sysUsers;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<User> getSysUsers() {
        return sysUsers;
    }

    public void setSysUsers(List<User> sysUsers) {
        this.sysUsers = sysUsers;
    }
}
