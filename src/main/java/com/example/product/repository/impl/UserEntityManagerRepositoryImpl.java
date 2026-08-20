package com.example.product.repository.impl;

import com.example.product.entity.User;
import com.example.product.repository.UserEntityManagerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class UserEntityManagerRepositoryImpl implements UserEntityManagerRepository {

    private static final Set<String> SORTABLE_FIELDS = Set.of("id", "username", "role", "enabled");

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> findEnabledUsers(Pageable pageable) {

        String jpql = "SELECT u FROM User u WHERE u.enabled = true" + buildOrderBy(pageable.getSort());

        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        return query.getResultList();
    }

    @Override
    public long countEnabledUsers() {

        String jpql = "SELECT COUNT(u) FROM User u WHERE u.enabled = true";

        return entityManager
                .createQuery(jpql, Long.class)
                .getSingleResult();
    }

    private String buildOrderBy(Sort sort) {
        if (sort == null || sort.isUnsorted()) {
            return " ORDER BY u.id DESC";
        }

        StringBuilder sb = new StringBuilder(" ORDER BY ");

        sort.forEach(order -> {
            String property = SORTABLE_FIELDS.contains(order.getProperty())
                    ? order.getProperty()
                    : "id";

            sb.append("u.")
                    .append(property)
                    .append(order.isAscending() ? " ASC" : " DESC")
                    .append(", ");
        });

        sb.setLength(sb.length() - 2);

        return sb.toString();
    }
}