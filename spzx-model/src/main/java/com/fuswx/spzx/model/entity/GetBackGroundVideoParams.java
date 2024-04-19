package com.fuswx.spzx.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class GetBackGroundVideoParams {
    @Schema(description = "标志任务唯一")
    private Integer taskId;
    @Schema(description = "需要更换背景的原始视频的公网地址")
    private String oriVideoPath;
    @Schema(description = "背景视频的公网地址")
    private String backVideoPath;
    @Schema(description = "更换语音的公网地址")
    private String backAudioPath;
    @Schema(description = "可选背景的替换,参数必填项 【0，1，2，3】")
    private String changeSort;
}
