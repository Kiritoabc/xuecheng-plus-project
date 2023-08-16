package com.boluo.media;

import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import org.junit.jupiter.api.Test;

/**
 * <MinIo>测试
 *
 * @author spongzi
 * @date 2023/07/19
 */
public class MinIOTest {

    MinioClient minioClient = MinioClient.builder()
            .endpoint("http://127.0.0.1:9000/")
            .credentials("minio", "minio@123456")
            .build();

    @Test
    void testUpload() {
        try {
            UploadObjectArgs testbucket = UploadObjectArgs.builder()
                    .bucket("testbucket") // 桶
                    .filename("E:\\Study\\1、前端V7.6主课程阶段\\001- HTML+CSS基础到高级\\01第一章HTML基础知识\\001今日课程介绍.mp4")
                    .object("001今日课程介绍.mp4") // 对象名
                    .build();
            minioClient.uploadObject(testbucket);
            System.out.println("上传成功");
        } catch (Exception e) {
            System.out.println("上传失败");
        }
    }
}
