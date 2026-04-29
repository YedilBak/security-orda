package kz.main.security_orda.repository;

import kz.main.security_orda.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {

    @Query("SELECT p FROM Permission p WHERE p.role='ROLE_USER'")
    Permission getStandartPermission();
}
