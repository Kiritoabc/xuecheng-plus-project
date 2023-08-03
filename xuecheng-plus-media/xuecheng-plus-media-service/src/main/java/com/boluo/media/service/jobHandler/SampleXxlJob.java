package com.boluo.media.service.jobHandler;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 简单的xxl-job案例
 *
 * @author spongzi
 * @date 2023/07/22
 */
@Slf4j
@Component
public class SampleXxlJob {
    /**
     * 日志记录器
     */
    private static Logger logger = LoggerFactory.getLogger(SampleXxlJob.class);

    @XxlJob("demoHandler")
    public void  demoJobHandler() throws InterruptedException {
        XxlJobHelper.log("XXL-JOB, Hello World");

        for (int i = 0; i < 5; i++) {
            XxlJobHelper.log("beat at: " + i);
            TimeUnit.SECONDS.sleep(2);
        }
    }

    /**
     * 2、分片广播任务
     */
    @XxlJob("shardingJobHandler")
    public void shardingJobHandler() throws Exception {

        // 分片参数
        int shardIndex = XxlJobHelper.getShardIndex(); // 执行器的序号，从0开始
        int shardTotal = XxlJobHelper.getShardTotal(); // 执行器总数

        log.info("分片参数：当前分片序号 = {}, 总分片数 = {}", shardIndex, shardTotal);
        log.info("开始执行第"+shardIndex+"批任务");

    }


}
