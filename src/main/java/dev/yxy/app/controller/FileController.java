package dev.yxy.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.yxy.app.global.ResponseData;
import dev.yxy.app.global.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 文件上传下载
 *
 * @author yuanxy
 * @create 2022/7/4 13:55
 * @update 2022/7/4 13:55
 * @origin upload-download-demo
 */

@RequestMapping("/file")
@RestController
public class FileController {

    @Value("${file.upload-path:/Temp}")
    private String uploadPath;

    @Value("${file.download-path:/Temp}")
    private String downloadPath;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 上传文件
     */
    @PostMapping("/upload")
    public ResponseData upload(@RequestPart("files") MultipartFile file) throws IOException {
        // 上传目录路径
        Path uploadDirPath = Paths.get(uploadPath);
        // 如果目录不存在则创建
        if (!Files.exists(uploadDirPath)) {
            Files.createDirectory(uploadDirPath);
        }
        // 获取上传文件名
        String filename = Optional.ofNullable(file.getOriginalFilename()).orElseGet(() -> UUID.randomUUID().toString());
        // 获取待写入的目标文件路径
        Path dest = uploadDirPath.resolve(filename);
        // 传输文件
        file.transferTo(dest);
        // 返回响应体
        return ResponseData.success(filename);
    }

    /**
     * 列出文件
     */
    @GetMapping("/list")
    public ResponseData list() throws IOException {
        // 下载目录路径
        Path downloadDirPath = Paths.get(downloadPath);
        // 如果目录不存在则直接返回空列表
        if (!Files.exists(downloadDirPath)) {
            return ResponseData.success(Collections.emptyList());
        }
        // 通过文件流获取文件名列表
        try (Stream<Path> fileStream = Files.list(downloadDirPath)) {
            List<String> fileNameList = fileStream.filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .collect(Collectors.toList());
            return ResponseData.success(fileNameList);
        }
    }

    /**
     * 下载文件
     */
    @GetMapping("/download")
    public void download(@RequestParam("filename") String filename) throws IOException {
        // 下载目录路径
        Path downloadDirPath = Paths.get(downloadPath);
        // 文件路径
        Path filePath = downloadDirPath.resolve(filename);
        // 判断文件是否存在
        if (Files.exists(filePath)) {
            // 下载本地文件
            ServletUtils.downloadLocalFile(ServletUtils.getResponse(), filePath);
        } else {
            // 响应错误信息
            ResponseData responseData = ResponseData.failMsg(String.format("File [%s] not exist.", filename));
            ServletUtils.renderString(ServletUtils.getResponse(), objectMapper.writeValueAsString(responseData), Boolean.TRUE);
        }
    }
}
