package shadow.web.dao;

import org.apache.ibatis.annotations.Param;

import shadow.web.domain.ShadowUser;

public interface UserDao {

	int checkPhoneExist(@Param("phone")String phone);

	ShadowUser getUserByName(@Param("username")String username);

	int addUser(@Param("username")String username, @Param("password")String password, @Param("phone")String phone);

}
