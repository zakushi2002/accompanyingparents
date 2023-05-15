package com.webapp.accompanyingparents.model.criteria;

import com.webapp.accompanyingparents.model.Bookmark;
import com.webapp.accompanyingparents.model.Post;
import com.webapp.accompanyingparents.model.UserProfile;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class BookmarkCriteria implements Serializable {
    private Long id;
    @NotNull(message = "accountId can be not null")
    private Long accountId;
    private Long postId;

    public Specification<Bookmark> getSpecification() {
        return new Specification<Bookmark>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Bookmark> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                if (getId() != null) {
                    predicates.add(cb.equal(root.get("id"), getId()));
                }
                if (getAccountId() != null) {
                    Join<UserProfile, Bookmark> join = root.join("account", JoinType.INNER);
                    predicates.add(cb.equal(join.get("id"), getAccountId()));
                }
                if (getPostId() != null) {
                    Join<Post, Bookmark> join = root.join("post", JoinType.INNER);
                    predicates.add(cb.equal(join.get("id"), getPostId()));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}