package ie.gmit.sw;

public class HammingDistance implements StringCompare{
	public String distance(String s, String t) {
		if (s.length() != t.length()) return "-1"; //Similar length strings only
		int counter = 0;
		
		for (int i = 0; i < s.length(); ++i){
			if (s.charAt(i) != t.charAt(i)) counter++;
		}
		
		String result = Integer.toString(counter);
		
		return result;
	}
	

}
