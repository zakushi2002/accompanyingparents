package com.webapp.accompanyingparents.model.repository;

import com.webapp.accompanyingparents.model.Account;
import com.webapp.accompanyingparents.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    List<Post> findAllByAccount(Account account);
}
