package com.webapp.accompanyingparents.model.criteria;


import com.webapp.accompanyingparents.model.Account;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class AccountCriteria implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String email;
    private String fullName;

    public Specification<Account> getSpecification() {
        return new Specification<Account>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                if (getId() != null) {
                    predicates.add(cb.equal(root.get("id"), getId()));
                }
                if (!StringUtils.isEmpty(getEmail())) {
                    predicates.add(cb.like(cb.lower(root.get("email")), "%" + getEmail().toLowerCase() + "%"));
                }
                if (!StringUtils.isEmpty(getFullName())) {
                    predicates.add(cb.like(cb.lower(root.get("fullName")), "%" + getFullName().toLowerCase() + "%"));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }

}
