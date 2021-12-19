Problem Statement : Someone can make a future order(similar to prior booking) and all orders will be processed according to their new times. 
					Orders can also be recurring and people can subscribe to meals for the future. People can cancel and edit their future orders. 
					People can mention the exact recipe which they want to. How do I approach this?

Tech Stack to solve this problem:
Java, SpringBoot FW, MySQL RDBMS, Application Server, Scheduler server(Uses Cron tab)

Database name: Delivery_Platform 
Tables used/ Table design: 
Note : CUD operation in MySQL will be Cascaded entirely.

1) Table name: -> User
					- user_id(PK) - One to many 
					- created_at
					- modified_at
					 
2) Table name: -> Order
					- order_id(PK) - One to many
					- user_id(FK)
					- order_type(Instant, Schedule)(NN) - VARCHAR(10)
					- delivery_scheduled_time
					- distance(NN)
					- created_at(NN)
					- modified_at

3) Table name: -> Meals
					- order_id(FK)
					- user_id(FK)
					- meal_type
					
4) Table name: -> Scheduled_Jobs
					- job_id(PK)
					- order_id(FK)
					- job_type(OneTime, Recurring)(NN)
					- job_status(NN)
					- created_at(NN)

Explanation:

Note: Both Application Server, Scheduler server points to same DB.

Order CRUD - Create, read, update, delete an order.
Create API - When the user is trying to schedule their order for the future purpose, "Order" table will make a entry with order_id which is PK
			 for every order and order_type is mandatory which should be filled based upon the user's request. Once the table is updated there
			 will be a check in the application where it verifies whether the order_type is "Schedule". If yes, then "Scheduled_Jobs" table will
			 be made an entry with that order_id and job will be created and concurrently the job_id will be made an entry in a List based upon
			 the delivery_scheduled_time fo each order_id in Scheduled_Jobs. Cron is used to run the API which provides the estimated time of 
			 the order by fetching number of meal_type from Meals table with respect to order_id.

Read API   - Using this API user can be able to view the list of orders made previously, if there is no order before then it returns empty.

Update API - If the user is updating the order, the same has to be modified in the tables. If the user is updating a order_type from Schedule to 
		     Instant then that order_id row in Scheduled_Jobs has to be deleted. If the user is updating a order_type from Instant to Schedule 
		     then that new row in Scheduled_Jobs has to be added with that order_id.
		     
Delete API - If the user is deleting the order, with respect to that order_id, all the rows has to be deleted on cascade across tables.

 		        

	 
	

			