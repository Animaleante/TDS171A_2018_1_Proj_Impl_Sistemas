package persistence.user.permission;

import java.util.Set;

import vo.permission.Permission;
import vo.user.User;
import vo.user.permition.UserPermissionConstraints;

/**
 * Interface to define the operations against to the database to the
 * UserPermission entity.
 * 
 * @author Baracho
 * 
 * @since January 31, 2017
 * 
 * @version 1.0
 *
 */
interface IUserPermissionDAO {

	public Boolean save(User user);

	public Boolean save(UserPermissionConstraints userPermissionConstraints);

	public Boolean delete(User user);

	public Boolean delete(UserPermissionConstraints userPermissionConstraints);

	public Set<Permission> search(User user);
}
