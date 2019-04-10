package com.project.onlinepreprocessor.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;
import java.util.ArrayList;

import javax.transaction.Transactional;

import com.project.onlinepreprocessor.domain.Permission;



// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface PermissionRepository extends CrudRepository<Permission, Long> {

    @Query(value="SELECT perm_id from user_perm where user=?1",nativeQuery=true)
    public ArrayList<BigInteger> getUserPermissions(String username);

    @Modifying
    @Transactional
    @Query(value = "DELETE from user_perm where user=?1", nativeQuery=true)
    public void deletePermissions(String username);

    @Modifying
    @Transactional
    @Query(value = "insert into user_perm(user, perm_id) values(?1, ?2)", nativeQuery=true)
    public void addPermission(String username, int permission);

}