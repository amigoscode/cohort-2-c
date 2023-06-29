package com.amigoscode.chohort2.carRental.spcefication;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.SingularAttribute;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

class Util {

    private static final Logger log = LoggerFactory.getLogger(Util.class);


    static <T> Predicate equal(Root<T> root, CriteriaBuilder cb,
                               SingularAttribute<? super T, String> attributeName, String input) {
        if (StringUtils.isNotBlank(input)) {
            return cb.equal(root.get(attributeName), input.trim());
        }
        return null;
    }


    static <T> Predicate notEqual(Root<T> root, CriteriaBuilder cb,
                                  SingularAttribute<? super T, String> attributeName, String input) {
        if (StringUtils.isNotBlank(input)) {
            return cb.notEqual(root.get(attributeName), input.trim());
        }
        return null;
    }

    static <T, F> Predicate equal(Root<T> root, CriteriaBuilder cb,
                                  SingularAttribute<? super T, F> attributeName, F input) {
        if (input != null) {

            if (input instanceof Double asNumber) {
                Expression<Double> trunc = cb.function("trunc", Double.class, root.get(attributeName));

                return cb.equal(trunc, input);
            }

            return cb.equal(root.get(attributeName), input);
        }
        return null;
    }

    static <T, F> Predicate notEqual(Root<T> root, CriteriaBuilder cb,
                                     SingularAttribute<? super T, F> attributeName, F input) {
        if (input != null) {
            return cb.notEqual(root.get(attributeName), input);
        }
        return null;
    }

    static <T> Predicate like(Root<T> root, CriteriaBuilder cb,
                              SingularAttribute<? super T, String> attributeName, String input) {
        if (StringUtils.isNotBlank(input)) {
            return cb.like(root.get(attributeName), "%" + input.trim() + "%");
        }
        return null;
    }

    static <T, F> Predicate in(Root<T> root, SingularAttribute<? super T, F> attributeName, List<F> input) {
        if (input != null) {
            return root.get(attributeName).in(input);
        }
        return null;
    }

    static <T, F> Predicate notIn(Root<T> root, SingularAttribute<? super T, F> attributeName, List<F> input) {
        if (input != null) {
            return root.get(attributeName).in(input).not();
        }
        return null;
    }

    static <T, F extends Comparable<? super F>>
    Predicate lessThanOrEqual(Root<T> root, CriteriaBuilder cb,
                              SingularAttribute<? super T, F> attributeName, F input) {
        if (input != null) {
            return cb.lessThanOrEqualTo(root.get(attributeName), input);
        }
        return null;
    }

    static <T, F extends Comparable<? super F>>
    Predicate greaterThanOrEqual(Root<T> root, CriteriaBuilder cb,
                                 SingularAttribute<? super T, F> attributeName, F input) {
        if (input != null) {
            return cb.greaterThanOrEqualTo(root.get(attributeName), input);
        }
        return null;
    }


    static <T, F extends Comparable<? super F>>
    Predicate between(Root<T> root, CriteriaBuilder cb,
                      SingularAttribute<? super T, F> attributeName, F fromValue, F toValue) {
        if (fromValue == null && toValue == null) {
            return null;
        }

        if (fromValue == null) {
            return lessThanOrEqual(root, cb, attributeName, toValue);
        }

        if (toValue == null) {
            return greaterThanOrEqual(root, cb, attributeName, fromValue);
        }

        return cb.between(root.get(attributeName), fromValue, toValue);


    }


    static <T> Predicate lessThanOrEqualDate(Root<T> root, CriteriaBuilder cb,
                                             SingularAttribute<? super T, LocalDate> attributeName, LocalDate input) {
        if (input != null) {
            return cb.lessThanOrEqualTo(root.get(attributeName), input);
        }
        return null;
    }

    static <T> Predicate greaterThanOrEqualDate(Root<T> root, CriteriaBuilder cb,
                                                SingularAttribute<? super T, LocalDate> attributeName, LocalDate input) {
        if (input != null) {
            return cb.greaterThanOrEqualTo(root.get(attributeName), input);
        }
        return null;
    }


    static <T> Predicate betweenDate(Root<T> root, CriteriaBuilder cb,
                                     SingularAttribute<? super T, LocalDate> attributeName, LocalDate fromValue, LocalDate toValue) {
        if (fromValue == null && toValue == null) {
            return null;
        }

        if (fromValue == null) {
            return lessThanOrEqual(root, cb, attributeName, toValue);
        }

        if (toValue == null) {
            return greaterThanOrEqual(root, cb, attributeName, fromValue);
        }


        return cb.between(root.get(attributeName), fromValue, toValue);


    }


    static <T> Predicate betweenTime(Root<T> root, CriteriaBuilder cb,
                                     SingularAttribute<? super T, LocalDateTime> attributeName, LocalDateTime fromValue, LocalDateTime toValue) {
        if (fromValue == null && toValue == null) {
            return null;
        }

        if (fromValue == null) {
            return lessThanOrEqual(root, cb, attributeName, toValue);
        }

        if (toValue == null) {
            return greaterThanOrEqual(root, cb, attributeName, fromValue);
        }


        return cb.between(root.get(attributeName), fromValue, toValue);


    }


}
