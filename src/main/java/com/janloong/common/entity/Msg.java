package com.janloong.common.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author <a href ="https://blog.janloong.com">Janloong Doo</a>
 * @version 1.0.0
 * @since 2020-10-26 16:14
 **/
@AllArgsConstructor
public class Msg {

    @Getter
    private String msg;

    public static Msg success(String msg) {
        return new Msg(msg);
    }
}
