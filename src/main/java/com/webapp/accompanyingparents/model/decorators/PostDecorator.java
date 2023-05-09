package com.webapp.accompanyingparents.model.decorators;

import com.webapp.accompanyingparents.model.IPost;

public abstract class PostDecorator implements IPost {
    private final IPost corePost;

    protected PostDecorator(IPost corePost) {
        this.corePost = corePost;
    }

    @Override
    public Integer getClassify() {
        return corePost.getClassify();
    }
}