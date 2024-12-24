package com.adm.test.specification;

import com.adm.test.entity.Employee;
import org.springframework.data.jpa.domain.Specification;

public final class EmployeeSpecification {

    private EmployeeSpecification() {
    }

    public static Specification<Employee> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                name != null ? criteriaBuilder.like(root.get("fullName"), "%" + name + "%") : null;
    }
}
