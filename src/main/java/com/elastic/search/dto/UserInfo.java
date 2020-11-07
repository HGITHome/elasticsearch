package com.elastic.search.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author
 * @Title:
 * @Package
 * @Description:
 * @Company
 * @date 2020/11/5 15:26
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserInfo implements Serializable {
    /**
     * 昵称
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 薪水
     */
    private Double salary;

    /**
     * 地址
     */
    private String address;

    /**
     * 备注
     */
    private String remark;

    /**
     * 真实名字
     */
    private String realName;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 创建时间
     */
    private Date createTime;
}
