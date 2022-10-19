package com.balasamajam.specifications;

import com.balasamajam.entities.Admin;
import com.balasamajam.entities.Member;
import com.balasamajam.entities.Payment;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;

public class PaymentSpecification
{
    public static Specification<Payment> paymentCollectedBy(Admin admin)
    {
        return new Specification<Payment>() {
            @Override
            public Predicate toPredicate(Root<Payment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("collectedBy"), admin);
            }
        };
    }

    public static Specification<Payment> paymentCollectedFrom(Member member)
    {
        return new Specification<Payment>() {
            @Override
            public Predicate toPredicate(Root<Payment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("collectedFrom"), member);
            }
        };
    }

    public static Specification<Payment> paymentStartDate(Date date)
    {
        return new Specification<Payment>() {
            @Override
            public Predicate toPredicate(Root<Payment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("paymentDate"), date);
            }
        };
    }

    public static Specification<Payment> paymentEndDate(Date date)
    {
        return new Specification<Payment>() {
            @Override
            public Predicate toPredicate(Root<Payment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("paymentDate"), date);
            }
        };
    }
}
