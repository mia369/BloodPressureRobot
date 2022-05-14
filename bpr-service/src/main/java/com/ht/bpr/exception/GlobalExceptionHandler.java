package com.ht.bpr.exception;

import com.ht.bpr.common.BprResultStatus;
import com.ht.bpr.common.Result;
import com.ht.bpr.common.ResultStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/14 15:59
 * @description
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler({BprException.class})
    public Result bizExceptionHandler(HttpServletRequest request, BprException e) {
        logger.error("exception occurs when execute method. url is {}", request.getRequestURI(), e);
        return Result.failure(e.getCode(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler({Throwable.class})
    public Result bizExceptionHandler(HttpServletRequest request, Throwable e) {
        logger.error("exception occurs when execute method. url is {}", request.getRequestURI(), e);
        return Result.failure(BprResultStatus.FAILED, e.getMessage());
    }
}
