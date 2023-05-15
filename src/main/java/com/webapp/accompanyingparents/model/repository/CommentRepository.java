package com.webapp.accompanyingparents.model.repository;

import com.webapp.accompanyingparents.model.Account;
import com.webapp.accompanyingparents.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {
    List<Comment> findCommentsByPostId(Long postId);
    List<Comment> findCommentsByParentId(Long parentId);
    List<Comment> findCommentsByAccount(Account account);
}
