package persistence.permission;

import java.util.Set;

import vo.permission.PermitionConstraints;
import vo.permission.Permission;

/**
 * Interface to define the operations against to the database to the Permission
 * entity.
 * 
 * @author Baracho
 * 
 * @since January 31, 2017
 * 
 * @version 1.0
 *
 */
interface IPermissionDAO {

	public Permission save(Permission permission);

	public Set<Permission> list();

	public Permission update(Permission permission);

	public Boolean delete(Permission permission);

	public PermitionConstraints search();

	public Permission search(PermitionConstraints permitionConstraints);

	public PermitionConstraints permissionConstraints();
}
