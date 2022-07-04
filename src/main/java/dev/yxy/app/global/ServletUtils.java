package dev.yxy.app.global;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Servlet工具类
 *
 * @author yuanxy
 * @create 2022/7/4 15:14
 * @update 2022/7/4 15:14
 * @origin upload-download-demo
 */
public class ServletUtils {

    /**
     * 获取 Servlet 请求属性
     */
    public static ServletRequestAttributes getRequestAttributes() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }

    /**
     * 获取 Request
     */
    public static HttpServletRequest getRequest() {
        return Optional.ofNullable(getRequestAttributes())
                .map(ServletRequestAttributes::getRequest)
                .orElse(null);
    }

    /**
     * 获取 Response
     */
    public static HttpServletResponse getResponse() {
        return Optional.ofNullable(getRequestAttributes())
                .map(ServletRequestAttributes::getResponse)
                .orElse(null);
    }

    /**
     * 输出字符串（输出流不关闭）
     *
     * @param response 响应
     * @param string   字符串
     */
    public static void renderString(HttpServletResponse response, String string) {
        renderString(response, string, Boolean.FALSE);
    }

    /**
     * 输出字符串
     *
     * @param response 响应
     * @param string   字符串
     * @param close    是否关闭输出流
     */
    public static void renderString(HttpServletResponse response, String string, boolean close) {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.print(string);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (close && writer != null) {
                writer.close();
            }
        }
    }

    /**
     * 下载本地文件
     *
     * @param response 响应
     * @param filePath 文件路径
     */
    public static void downloadLocalFile(HttpServletResponse response, Path filePath) {
        try {
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setContentLengthLong(Files.size(filePath));
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + URLEncoder.encode(filePath.getFileName().toString(), StandardCharsets.UTF_8.name()));
            FileCopyUtils.copy(Files.newInputStream(filePath), response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
