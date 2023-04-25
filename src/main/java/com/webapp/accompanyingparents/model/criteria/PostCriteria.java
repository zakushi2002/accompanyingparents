package com.webapp.accompanyingparents.model.criteria;


import com.webapp.accompanyingparents.model.Account;
import com.webapp.accompanyingparents.model.Post;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class PostCriteria implements Serializable {
    private Long id;
    private Long accountId;
    private String keyword;
    private Integer status;

    public Specification<Post> getSpecification() {
        return new Specification<Post>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                if (getId() != null) {
                    predicates.add(cb.equal(root.get("id"), getId()));
                }
                if (!StringUtils.isEmpty(getKeyword())) {
                    predicates.add(cb.like(cb.lower(root.get("title")), "%" + getKeyword().toLowerCase() + "%"));
                }
                if (getAccountId() != null) {
                    Join<Account, Post> join = root.join("account", JoinType.INNER);
                    predicates.add(cb.equal(join.get("id"), getAccountId()));
                }
                if (getStatus() != null) {
                    predicates.add(cb.equal(root.get("status"), getStatus()));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
