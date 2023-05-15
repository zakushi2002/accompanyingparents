package com.webapp.accompanyingparents.model.repository;

import com.webapp.accompanyingparents.model.Account;
import com.webapp.accompanyingparents.model.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long>, JpaSpecificationExecutor<Bookmark> {
    Bookmark findFirstByAccountAndPostId(Account account, Long postId);

    List<Bookmark> findAllByPostId(Long postId);

    List<Bookmark> findAllByAccount(Account account);
}