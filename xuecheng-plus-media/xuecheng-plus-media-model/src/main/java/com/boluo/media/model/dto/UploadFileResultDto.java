package com.boluo.media.model.dto;

import com.boluo.media.model.po.MediaFiles;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 上传文件结果dto
 *
 * @author spongzi
 * @date 2023/07/19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UploadFileResultDto extends MediaFiles {
}
