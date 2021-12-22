import java.io.IOException;
import java.util.List;

import com.delivery.request.MealRequest;
import com.delivery.service.DeliveryService;

public class DeliveryMainClass {
	
	
	public static void main(String[] args) throws IOException {
			List<MealRequest> mealRequestList = PojoConvertorUtil.jsonToPojoConverter("input.txt");
            DeliveryService deliveryService = new DeliveryService(); 
            List<Float> estimatedTimeOfDeliveryForOrders = deliveryService.getEstimatedTimeForOrders(mealRequestList);
            for(int i =0; i<estimatedTimeOfDeliveryForOrders.size();i++) {
            	if(estimatedTimeOfDeliveryForOrders.get(i)==0) {
            		System.out.println("Order " + mealRequestList.get(i).getOrderId() + " is denied because the restaurant cannot accommodate it.");
            	}
            	else {
            		System.out.println("Order " + mealRequestList.get(i).getOrderId() + " will get delivered in " + estimatedTimeOfDeliveryForOrders.get(i) + " minutes");
            	}
            }
	}
	
}
