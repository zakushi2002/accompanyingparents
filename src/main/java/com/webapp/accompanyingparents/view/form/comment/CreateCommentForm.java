package com.webapp.accompanyingparents.view.form.comment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class CreateCommentForm {
    @NotEmpty(message = "contentComment can not be empty")
    @NotNull(message = "contentComment can not be null")
    @ApiModelProperty(name = "contentComment", required = true)
    private String contentComment;
    @NotNull(message = "postId can not be null")
    @ApiModelProperty(name = "postId", required = true)
    private Long postId;
}