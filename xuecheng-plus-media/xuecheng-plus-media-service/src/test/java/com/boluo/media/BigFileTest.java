package com.boluo.media;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BigFileTest {

    @Test
    void testChunk() {
        // 源文件
        File sourceFile = new File("C:\\Users\\spongzi\\Downloads\\Video\\test.ts");
        // 分块文件存储路径
        String chunkPath = "E:\\chunk\\";
        // 分块文件大小
        int chunkSize = 1024 * 1024 * 5;
        // 分块个数
        int chunkNum = (int) Math.ceil(sourceFile.length() * 1.0 / chunkSize);
        // 使用流从源文件中读取数据,向分块文件写数据
        try (RandomAccessFile rafR = new RandomAccessFile(sourceFile, "r")) {
            byte[] buf = new byte[1024];
            for (int i = 0; i < chunkNum; i++) {
                File chunkFile = new File(chunkPath + i);
                try (RandomAccessFile rw = new RandomAccessFile(chunkFile, "rw")) {
                    int len = -1;
                    while ((len = rafR.read(buf)) != -1) {
                        rw.write(buf, 0, len);
                        if (chunkFile.length() > chunkSize) {
                            break;
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void merge() {
        // 找到分块模块的路径
        File chunkFolder = new File("E:/chunk/");
        // 源文件
        File sourceFile = new File("C:\\Users\\spongzi\\Downloads\\Video\\test.ts");
        // 合并后的文件
        File mergeFile = new File("E:/test2.ts");
        // 取出所有分块文件
        File[] files = chunkFolder.listFiles();
        // 将数组转成list
        List<File> fileList = Arrays.asList(files);
        Collections.sort(fileList, Comparator.comparingInt(o -> Integer.parseInt(o.getName())));
        // 遍历分块文件, 向合并的文件写
        try (RandomAccessFile rw = new RandomAccessFile(mergeFile, "rw")) {
            byte[] buf = new byte[1024];
            fileList.forEach(file -> {
                try (RandomAccessFile r = new RandomAccessFile(file, "r")) {
                    int len = -1;
                    while ((len = r.read(buf)) != -1) {
                        rw.write(buf, 0, len);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 合并文件完整性校验
        try (FileInputStream fileInputStreamMerge = new FileInputStream(mergeFile);
             FileInputStream fileInputStreamSource = new FileInputStream(sourceFile);
        ){
            String md5Merge = DigestUtils.md5Hex(fileInputStreamMerge);
            String md5Source = DigestUtils.md5Hex(fileInputStreamSource);
            if (md5Source.equals(md5Merge)) {
                System.out.println("文件合并成功");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
