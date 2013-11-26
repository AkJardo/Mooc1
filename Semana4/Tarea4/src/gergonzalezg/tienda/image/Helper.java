package gergonzalezg.tienda.image;

public class Helper {
	public final static String INSTAGRAM_API_KEY = "981d99451ee7461aa177fc9caf778c66";
	public final static String BASE_API_URL = "https://api.instagram.com/v1/";
	
	public static String getRecentUrl(String tag){
		return BASE_API_URL + "tags/" + tag + "/media/recent?client_id=" + INSTAGRAM_API_KEY; 
	}
}
