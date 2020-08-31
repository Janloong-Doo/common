/*::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
 : Copyright (c) 2019  All Rights Reserved.
 : ProjectName: SpringCloud
 : FileName: ExceptionHandle.java
 : Author: janloongdoo@gmail.com
 : Date: 19-5-20 上午11:37
 : LastModify: 18-11-1 下午5:17
 :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/

package com.janloong.common.exception;

import com.janloong.common.entity.ErrorInfo;
import com.janloong.common.enums.ResultEnum;
import com.janloong.common.utils.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


/**
 * 异常处理类
 * <p>
 * controller层异常外抛处理类
 *
 * @author <a href ="https://blog.janloong.com">Janloong Doo</a>
 * @version 1.0.0
 * @since 2018/5/15 10:39
 **/
@ControllerAdvice
@Slf4j
public class ExceptionHandle {

    @ExceptionHandler(value = {RuntimeException.class, Exception.class})
    @ResponseBody
    public ResponseResult handle(Exception e) {
        if (e instanceof BusinessException) {
            BusinessException bussinesException = (BusinessException) e;
            ErrorInfo errorInfo = bussinesException.getErrorInfo();
            if (errorInfo.isSuccess()) {
                log.info("[业务正常] [{} - {}]", errorInfo.getCode(), errorInfo.getMsg());
                return ResponseResult.success(errorInfo.getCode(), errorInfo.getMsg(), null);
            } else {
                log.error("[业务异常] [{} - {}]", errorInfo.getCode(), errorInfo.getMsg());
                return ResponseResult.error(errorInfo.getCode(), errorInfo.getMsg());
            }
        } else {
            log.error("[系统异常]: {}", e);
            return ResponseResult.error(ResultEnum.ERROR.getCode(), e.getMessage());
        }
    }

    @Autowired
    @Qualifier(value = "localDateTimeConverter")
    private Converter<String, LocalDateTime> localDateTimeConverter;

    @Autowired
    @Qualifier(value = "localDateConverter")
    private Converter<String, LocalDate> localDateConverter;

    @Autowired
    @Qualifier(value = "localTimeConverter")
    private Converter<String, LocalTime> localTimeConverter;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // 方法1，注册converter
        GenericConversionService genericConversionService = (GenericConversionService) binder.getConversionService();
        if (genericConversionService != null) {
            genericConversionService.addConverter(localDateTimeConverter);
            genericConversionService.addConverter(localDateConverter);
            genericConversionService.addConverter(localTimeConverter);
        }

        // 方法2，定义单格式的日期转换，可以通过替换格式，定义多个dateEditor，代码不够简洁
        //DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //CustomDateEditor dateEditor = new CustomDateEditor(df, true);
        //binder.registerCustomEditor(Date.class, dateEditor);
        //binder.registerCustomEditor(Date.class, dateEditor);
        //
        //
        //// 方法3，同样注册converter
        //binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
        //    @Override
        //    public void setAsText(String text) throws IllegalArgumentException {
        //        setValue(new DateConverter().convert(text));
        //    }
        //});
    }
}
