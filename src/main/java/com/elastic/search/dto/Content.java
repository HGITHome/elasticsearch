package com.elastic.search.dto;

import lombok.*;

import java.io.Serializable;

/**
 * @author
 * @Title:
 * @Package
 * @Description:
 * @Company
 * @date 2020/11/7 18:23
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Content implements Serializable {

    private String img;

    private String price;

    private String title;
}
