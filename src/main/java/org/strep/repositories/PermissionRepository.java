package org.strep.repositories;

import org.springframework.data.repository.CrudRepository;

import org.strep.domain.Permission;


/**
 * Interface that extends CrudRepository implementation of basic CRUD operations with added queries for Permission objects
 * @author Ismael Vázquez
 */

public interface PermissionRepository extends CrudRepository<Permission, Long> {

}