package com.project.logistic_management.repository.role;

import com.project.logistic_management.dto.request.RoleDTO;
import com.project.logistic_management.dto.request.UserDTO;
import com.project.logistic_management.entity.*;
import com.project.logistic_management.enums.PermissionKey;
import com.project.logistic_management.repository.BaseRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleRepoImpl extends BaseRepository implements RoleRepoCustom {
    public RoleRepoImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Role findRoleById(Integer id) {
        QRole qRole = QRole.role;
        return query.selectFrom(qRole)
                .where(qRole.id.eq(id))
                .fetchOne();
    }

    public List<Role> getAll(){
        QRole qRole = QRole.role;
        return query.selectFrom(qRole).fetch();
    }

    @Modifying
    @Transactional
    public void deleteRoleById(Integer id){
        QRole qRole = QRole.role;
        query.delete(qRole).where(qRole.id.eq(id));
    }


}
