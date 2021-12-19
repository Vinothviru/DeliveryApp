import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import com.delivery.request.MealRequest;
import com.delivery.service.DeliveryService;
import com.google.gson.Gson;

//$Id$

public class DeliveryMainClass {
	public static void main(String[] args) throws IOException {
		try (Reader reader = new FileReader("input.txt")) {
            Gson gson = new Gson();
            MealRequest[] features = gson.fromJson(reader, MealRequest[].class);
            DeliveryService deliveryService = new DeliveryService(); 
            List<Float> estimatedTimeOfDeliveryForOrders = deliveryService.getEstimatedTimeForOrders(features);
            for(int i =0; i<estimatedTimeOfDeliveryForOrders.size();i++) {
            	if(estimatedTimeOfDeliveryForOrders.get(i)==0) {
            		System.out.println("Order " + features[i].getOrderId() + " is denied because the restaurant cannot accommodate it.");
            	}
            	else {
            		System.out.println("Order " + features[i].getOrderId() + " will get delivered in " + estimatedTimeOfDeliveryForOrders.get(i) + " minutes");
            	}
            }
        }
	}
	
}
