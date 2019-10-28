/*create by xuan on 2019/9/10

 */
package com.zhcdata.jc.xml_model;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class MatchList {
    List<JSONObject> list;
}