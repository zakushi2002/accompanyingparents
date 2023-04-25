package com.webapp.accompanyingparents.view.form.comment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class UpdateCommentForm {
    @NotNull(message = "id can not be null")
    @ApiModelProperty(name = "id", required = true)
    private Long id;
    @NotEmpty(message = "contentComment can not be empty")
    @NotNull(message = "contentComment can not be null")
    @ApiModelProperty(name = "contentComment", required = true)
    private String contentComment;
}