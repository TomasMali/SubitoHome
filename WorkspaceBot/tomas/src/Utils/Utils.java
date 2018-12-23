package Utils;

import java.util.Arrays;
import java.util.List;

public class Utils {

	public static boolean isNumeric(List<String> list) {

		for (String str : list) {
			if (!str.matches("[+-]?\\d*(\\.\\d+)?")) {
				return false;
			}
		}

		// return (str.matches("[+-]?\\d*(\\.\\d+)?"));

		return true;
	}

	public static float[] convertToFloat(String number) {

		List<String> list = Arrays.asList(number.split(","));
		float[] myFloatArray = new float[list.size()];

		for (int i = 0; i < list.size(); i++)
			myFloatArray[i] = Float.parseFloat(list.get(i));

		return myFloatArray;
	}

	public static boolean isInCache(List<Annuncio> annunci, Annuncio a) {

		for (Annuncio an : annunci) {
			if (an.getTitolo().equals(a.getTitolo()) && an.getPrezzo().equals(a.getPrezzo()))
				return true;
		}

		return false;
	}

	public static boolean checkUser(User user, List<User> list) {

		for (User userAux : list) {
			if (userAux.getUserId() == user.getUserId())
				return true;
		}

		return false;
	}

	public static User getUser(long userId, List<User> list) {

		for (User userAux : list) {
			if (userAux.getUserId() == userId)
				return userAux;
		}
		return null;
	}
}
