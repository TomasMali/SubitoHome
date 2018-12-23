package Utils;

import java.util.ArrayList;
import java.util.List;

public class UserList {
	
	
	private static List<User> user_List = new ArrayList<User>();
	
	
	public static UserList getInstanceUser() {
		return new UserList();
	}
	
	public static List<User> getUserList() {
		return user_List;
	}
	
	
	public static void addUser(User x) {
		if(!user_List.contains(x))
			user_List.add(x);
	}

}
