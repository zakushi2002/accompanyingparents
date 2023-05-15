package com.webapp.accompanyingparents.view.form.bookmark;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class CreateBookmarkForm {
    @NotNull(message = "postId can not be null")
    @ApiModelProperty(name = "postId", required = true)
    private Long postId;
}