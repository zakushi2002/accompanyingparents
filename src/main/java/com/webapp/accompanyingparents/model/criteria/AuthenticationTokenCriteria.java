package com.webapp.accompanyingparents.model.criteria;
/*
import com.webapp.accompanyingparents.model.AuthenticationToken;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class AuthenticationTokenCriteria implements Serializable {
    private Integer status;
    private String token;
    private String name;

    public Specification<AuthenticationToken> getSpecification() {
        return new Specification<AuthenticationToken>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<AuthenticationToken> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                if (getStatus() != null) {
                    predicates.add(cb.equal(root.get("status"), getStatus()));
                }

                if (getName() != null) {
                    predicates.add(cb.like(cb.lower(root.get("tokenName")), "%" + getName() + "%"));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
*/
