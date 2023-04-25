package com.webapp.accompanyingparents.view.form.post;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class UpdatePostForm {
    @NotNull(message = "id can not be null")
    @ApiModelProperty(name = "id", required = true)
    private Long id;
    @NotEmpty(message = "titlePost can not be empty")
    @NotNull(message = "titlePost can not be null")
    @ApiModelProperty(name = "titlePost", required = true)
    private String titlePost;
    @NotEmpty(message = "contentPost can not be empty")
    @NotNull(message = "contentPost can not be null")
    @ApiModelProperty(name = "contentPost", required = true)
    private String contentPost;
}