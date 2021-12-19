//$Id$
package com.delivery.request;

import java.util.List;

public class MealRequest {
	int orderId;
	List<String> meals;
	float distance;
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public List<String> getMeals() {
		return meals;
	}
	public void setMeals(List<String> meals) {
		this.meals = meals;
	}
	public float getDistance() {
		return distance;
	}
	public void setDistance(float distance) {
		this.distance = distance;
	}
}
