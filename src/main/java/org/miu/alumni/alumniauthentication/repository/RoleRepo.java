package org.miu.alumni.alumniauthentication.repository;

import org.miu.alumni.alumniauthentication.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {
    @Query("select r from Role r where r.role in (:names)")
    List<Role> findRolesByName(@Param("names") List<String> names);
}
