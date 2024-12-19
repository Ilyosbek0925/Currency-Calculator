package org.example;

import java.util.List;

public class UserService {
	public static Users findUser(long chatId, List<Users>list){
		Users user = null;
		for (Users users : list) {
			if (users.getId() == chatId) {
				user = users;
			}
		}
return user;

	}

}
