package dev.yxy.app.controller;

import dev.yxy.app.global.ResponseData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页
 *
 * @author yuanxy
 * @create 2022/7/4 13:31
 * @update 2022/7/4 13:31
 * @origin upload-download-demo
 */
@RestController
public class HomeController {

    @RequestMapping("/")
    public ResponseData hello() {
        return ResponseData.success("Hello World");
    }

    @RequestMapping("/test-error")
    public ResponseData testError() {
        throw new RuntimeException("test global exception handler");
    }
}
