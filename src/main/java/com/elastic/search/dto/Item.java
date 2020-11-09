package com.elastic.search.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author
 * @Title:
 * @Package
 * @Description:
 * @Company
 * @date 2020/11/9 15:24
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Item {

    //主键

    private Long id;
    //标准产品单位（商品集合）
    private Long spu;
    //库存量单位（最小品类单元）
    private Long sku;
    //商品标题
    private String title;
    //商品价格
    private Double price;
    //商品图片
    private String pic;
    //商品详情地址
    private String url;
    //创建时间
    private Date created;
    //更新时间
    private Date updated;
}
