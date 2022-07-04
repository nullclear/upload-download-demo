package dev.yxy.app.global;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @author yuanxy
 * @create 2022/7/4 13:48
 * @update 2022/7/4 13:48
 * @origin upload-download-demo
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Throwable.class)
    public ResponseData handleThrowable(Throwable e) {
        logger.error("[handleThrowable]", e);
        return ResponseData.failMsg(e.getMessage());
    }
}
