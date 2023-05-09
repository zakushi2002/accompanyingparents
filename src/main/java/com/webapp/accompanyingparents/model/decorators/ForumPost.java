package com.webapp.accompanyingparents.model.decorators;

import com.webapp.accompanyingparents.model.IPost;

public class ForumPost extends PostDecorator {
    public ForumPost(IPost corePost) {
        super(corePost);
    }

    @Override
    public Integer getClassify() {
        return super.getClassify() + 1;
    }
}