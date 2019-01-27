package com.wegoo.baseresult;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by 二师兄超级帅 on 2018/10/13.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResult {
    private String reCode;
    private String reMsg;
    private Object data;
}
