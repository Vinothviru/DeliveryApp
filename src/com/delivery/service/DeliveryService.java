//$Id$
package com.delivery.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.delivery.constants.Constants;
import com.delivery.request.MealRequest;

public class DeliveryService {
	public List<Float> getEstimatedTimeForOrders(MealRequest[] features){
		boolean flag = false;
		List<String> restaurantCookingSlots = new LinkedList<String>();
        List<Integer> slotsOccupiedForOrdersList = new LinkedList<Integer>();
        Queue<String> waitingOrders = new LinkedList<String>(); 
        List<Float> estimatedTimeOfDeliveryForOrders = new LinkedList<Float>(); 
        int count = 0;
        Float tempEstimatedTimeOfDelivery = 0.0f;
        for(int i = 0; i<features.length; i++) {
        	Float estimatedTimeOfDelivery = 0.0f;
        	int totalNumberOfAppetizers = 0;
        	int totalNumberOfMainCourse = 0;
        	for(int j = 0; j<features[i].getMeals().size(); j++) {
        		if(features[i].getMeals().get(j).equals(Constants.APPETIZER)) {
        			totalNumberOfAppetizers++;
        		}
        		else if(features[i].getMeals().get(j).equals(Constants.MAIN_COURSE)) {
        			totalNumberOfMainCourse++;
        		}
        	}
        	int totalSlotsNeededForAppetizers = totalNumberOfAppetizers*Constants.APPETIZER_SLOT;
        	int totalSlotsNeededForMainCourse = totalNumberOfMainCourse*Constants.MAIN_COURSE_SLOT;
        	int totalSlotsNeededPerOrder = totalSlotsNeededForAppetizers+totalSlotsNeededForMainCourse;
        	//Checking whether the restaurant is able to cook per user's whole request
        	if(totalSlotsNeededPerOrder>Constants.TOTAL_COOKING_SLOTS_OF_RESTAURANT) {
        		estimatedTimeOfDeliveryForOrders.add(0.0f);
        		slotsOccupiedForOrdersList.add(null);
        		//System.out.println("Order " + features[i].getOrderId() + " is denied because the restaurant cannot accommodate it.");
        	}
        	else {
        		int sizeOfRestaurantSlotsOccupied = restaurantCookingSlots.size();
        		if(sizeOfRestaurantSlotsOccupied<Constants.TOTAL_COOKING_SLOTS_OF_RESTAURANT) {
        			if(sizeOfRestaurantSlotsOccupied+totalSlotsNeededPerOrder<=Constants.TOTAL_COOKING_SLOTS_OF_RESTAURANT) {
        				for(int k = 0; k<totalSlotsNeededForAppetizers; k++) {
        					restaurantCookingSlots.add(Constants.APPETIZER);
        				}
        				for(int l = 0; l<totalSlotsNeededForMainCourse; l++) {
        					restaurantCookingSlots.add(Constants.MAIN_COURSE);
        				}
        				slotsOccupiedForOrdersList.add(totalSlotsNeededPerOrder);
        				if(totalSlotsNeededForMainCourse==0) {
            				estimatedTimeOfDelivery = (Float) ((Constants.APPETIZER_COOKING_TIME)
                    				+features[i].getDistance()*Constants.PER_KM_DELIVERY_TIME);            					
        				}
        				else {
            				estimatedTimeOfDelivery = (Float) ((Constants.MAIN_COURSE_COOKING_TIME)
                    				+features[i].getDistance()*Constants.PER_KM_DELIVERY_TIME);
        					
        				}
            			if(estimatedTimeOfDelivery<=150) {
            				estimatedTimeOfDeliveryForOrders.add(estimatedTimeOfDelivery);
            			}
            			else {
            				estimatedTimeOfDeliveryForOrders.add(0f);
            			}
        				//System.out.println("Order " + features[i].getOrderId() + " will get delivered in " + estimatedTimeOfDelivery + " minutes");
        			}
        		}
        		else {
        			Float minTimeTaken = 150.0f;
        			for(int m = 0; m<slotsOccupiedForOrdersList.size(); m++) {
        				if(tempEstimatedTimeOfDelivery==0&&slotsOccupiedForOrdersList.get(m)!=null&&slotsOccupiedForOrdersList.get(m)>=totalSlotsNeededPerOrder) {
        					if(estimatedTimeOfDeliveryForOrders.get(m)<minTimeTaken&&estimatedTimeOfDeliveryForOrders.get(m)>0) {
        						minTimeTaken = estimatedTimeOfDeliveryForOrders.get(m);
        					}
        					flag = true;
        				}
        			}
        			/* For large numbers */
        			if(!flag) {
        				for(int m = 0; m<slotsOccupiedForOrdersList.size()-1; m++) {
        					for(int n = m+1; n<slotsOccupiedForOrdersList.size(); n++) {
        						if(slotsOccupiedForOrdersList.get(m)!=null&&slotsOccupiedForOrdersList.get(n)!=null&&
        								slotsOccupiedForOrdersList.get(m)+slotsOccupiedForOrdersList.get(n)
        								>=totalSlotsNeededPerOrder) {
                					if(estimatedTimeOfDeliveryForOrders.get(m)
                							+estimatedTimeOfDeliveryForOrders.get(n)<minTimeTaken) {
                						minTimeTaken = estimatedTimeOfDeliveryForOrders.get(m)+
                								estimatedTimeOfDeliveryForOrders.get(n);
                					}
        						}
        					}
        				}
        			}
        			if(totalSlotsNeededForMainCourse==0) {
        				if(count>0) {minTimeTaken = 0f;}
        				estimatedTimeOfDelivery = (Float) tempEstimatedTimeOfDelivery + minTimeTaken + ((Constants.APPETIZER_COOKING_TIME)
                				+features[i].getDistance()*Constants.PER_KM_DELIVERY_TIME); 
        				tempEstimatedTimeOfDelivery+=estimatedTimeOfDelivery;
        				count++;
        			}
        			else {
        				if(count>0) {minTimeTaken = 0f;}
        				estimatedTimeOfDelivery = (Float) tempEstimatedTimeOfDelivery +  minTimeTaken + ((Constants.MAIN_COURSE_COOKING_TIME)
                				+features[i].getDistance()*Constants.PER_KM_DELIVERY_TIME); 
        				tempEstimatedTimeOfDelivery+=estimatedTimeOfDelivery;
        				count++;
        			}
        			if(estimatedTimeOfDelivery<=150) {
        				estimatedTimeOfDeliveryForOrders.add(estimatedTimeOfDelivery);
        			}
        			else {
        				estimatedTimeOfDeliveryForOrders.add(0f);
        			}
        			
        		}
        		
        		
        	}
        }
        return estimatedTimeOfDeliveryForOrders;
	}
}
