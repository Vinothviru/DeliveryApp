import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.delivery.request.MealRequest;

//$Id$

public class PojoConvertorUtil {
	public static List<String> readFileInList(String fileName)
	  {
	 
	    List<String> lines = Collections.emptyList();
	    try
	    {
	      lines =
	       Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
	    }
	 
	    catch (IOException e)
	    {
	 
	      // do something
	      e.printStackTrace();
	    }
	    return lines;
	  }
	
	public static List<MealRequest> jsonToPojoConverter(String fileName){
		List l = readFileInList("input.txt");
		 
	    Iterator<String> itr = l.iterator();
	    String str;
	    List<MealRequest> mealRequestList = new LinkedList<MealRequest>(); 
	    while (itr.hasNext()) {
	    	MealRequest mealRequest = new MealRequest();
	    	str = itr.next();
	    	String[] a  =str.replace("[{", "").replace("}]", "").replace("{", "").replace("}", "").split(" ");
	    	mealRequest.setOrderId(Integer.valueOf(a[1].replace(",", "")));
	    	String mealList = a[3].replace("\"", "").replace("[", "").replace("]", "").replace(",", "");
	    	List<String> meal = new LinkedList<String>();
	    	for(int k=0; k<mealList.length(); k++) {
	    		meal.add(String.valueOf(mealList.charAt(k)));
	    	}
	    	mealRequest.setMeals(meal);
	    	mealRequest.setDistance(Float.valueOf(a[5].replace(",", "")));
	    	mealRequestList.add(mealRequest);
	    }
	    return mealRequestList;
	}
}
