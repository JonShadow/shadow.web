package shadow.web.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shadow.web.dao.UserDao;
import shadow.web.domain.ShadowUser;

@Service("userService")
public class UserService {

	private static final Log logger = LogFactory.getLog(UserService.class);
	
	@Autowired
	UserDao userDao;

	public boolean checkPhone(String phone) {
		int result = userDao.checkPhoneExist(phone);
		logger.info("...");
		if (result > 0){
			return false;
		}
		return true;
	}

	public String getPassword(String username) {
		ShadowUser user = userDao.getUserByName(username);
		if (user != null) {
			return user.getPassword();
		}
		return null;
	}

	public int addUser(String username, String password, String phone) {
		int result = userDao.addUser(username, password, phone);
		return result;
	}

}
