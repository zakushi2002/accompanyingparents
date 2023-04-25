package com.webapp.accompanyingparents.model.criteria;

import com.webapp.accompanyingparents.model.Comment;
import com.webapp.accompanyingparents.model.Post;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class CommentCriteria implements Serializable {
    private Long id;
    private Long postId;
    private Integer status;

    public Specification<Comment> getSpecification() {
        return new Specification<Comment>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Comment> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                if (getId() != null) {
                    predicates.add(cb.equal(root.get("id"), getId()));
                }
                if (getPostId() != null) {
                    Join<Post, Comment> join = root.join("post", JoinType.INNER);
                    predicates.add(cb.equal(join.get("id"), getPostId()));
                }
                if (getStatus() != null) {
                    predicates.add(cb.equal(root.get("status"), getStatus()));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
