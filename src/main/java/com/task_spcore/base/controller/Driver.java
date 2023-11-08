package com.task_spcore.base.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.task_spcore.base.config.Config;
import com.task_spcore.base.dao.TaskDao;
import com.task_spcore.base.dao.UserDao;
import com.task_spcore.base.entity.Task;
import com.task_spcore.base.entity.User;

public class Driver {
	static ApplicationContext context= new AnnotationConfigApplicationContext(Config.class);
	static Scanner scanner = new Scanner(System.in);
	static UserDao userDao = context.getBean(UserDao.class);
	static TaskDao taskDao = context.getBean(TaskDao.class);
	
	
	public static void main(String[] args) {
				
		boolean exit = true;
		while (exit) {
			System.out.println("Login to Access Application \n 1.Manager Login \n 2.Employee Login ");
			int ch = scanner.nextInt();
			switch (ch) {
			case 1: {
				System.out.println("Enter Username");
				String username = scanner.next();
				System.out.println("Enter Password");
				String pass = scanner.next();
				boolean result = userDao.verifyManager(username, pass);
				if (result) {
					
					boolean flag1 = true;
					while (flag1) {
						System.out.println(
								"Welcome Manager  \n Enter operation \n 1.Employee Operations \n 2.Task Operations \n 3.Assign Task \n 4.Log Out");

						int a = scanner.nextInt();
						switch (a) {
						case 1: {
							empOp();
						}
							break;
						case 2: {

							taskOp();
						}
							break;
						case 3: {
							assignTask();
						}
							break;
						case 4: {
							flag1 = false;
						}
						}
					}

				} else {
					System.out.println("Login Failed Invalid Credentials!!!!");
				}
			}
			break;
			case 2:{
				System.out.println("Enter Username");
				String username = scanner.next();
				System.out.println("Enter Password");
				String pass = scanner.next();
				User user1=userDao.getEmployee(username, pass);
				boolean result = userDao.verifyEmployee(username, pass);
				if(result)
				{
					boolean fl=true;
					while(fl)
					{
						
					System.out.println("Welcome Employee \n 1.View Tasks \n 2. Update status \n 3.Logout");
					int id=scanner.nextInt();
					switch(id)
					{
					case 1:{
					  User user=user1;
						List<Task> tasks=user.getTasks();
						if (tasks != null && !tasks.isEmpty()) {
							
							System.out.println("==========Tasks===============");
							
								for (Task task : tasks) {
									System.out.println("Task Id       :" + task.getId());
									System.out.println("Task Descr    :" + task.getDescription());
									System.out.println("Task Assign st:" + task.getAssignedStatus());
									System.out.println("Task Complt st:" + task.getCompletedStatus());
									System.out.println("Task Create ti:" + task.getCreatedDT());
									System.out.println("Task Complt ti:" + task.getCompletedDT());
								}
							} else {
								System.out.println("No Task found");
							}
						
					}
					break;
					case 2:{
						List<Task> tasks=user1.getTasks();
						if (tasks != null) {
							
							System.out.println("==========Tasks===============");
							
								for (Task task : tasks) {
									System.out.println("Task Id       :" + task.getId());
									System.out.println("Task Descr    :" + task.getDescription());
									System.out.println("Task Assign st:" + task.getAssignedStatus());
									System.out.println("Task Complt st:" + task.getCompletedStatus());
									System.out.println("Task Create ti:" + task.getCreatedDT());
									System.out.println("Task Complt ti:" + task.getCompletedDT());
								}
								System.out.println("Which task id you want to update");
								int id1=scanner.nextInt();
								Task task=taskDao.getById(id1);
								
									task.setCompletedStatus("Completed");
									taskDao.updateTask(task);
									System.out.println("Task Status Updated successfully");
								
							} else {
								System.out.println("No Task found");
							}
					}
					break;
					case 3:{
						fl=false;
					}
					}
					}
				}else {
					System.out.println("Invalid credentials");
				}
			}
			}
		}
	}
	private static void assignTask() {
		System.out.println("Enter task id");
		int id = scanner.nextInt();
		Task task = taskDao.getById(id);
		if (task != null ) {
			System.out.println("Enter user id to assign task");
			int id1 = scanner.nextInt();
			User user = userDao.getById(id1);
			if (user != null) {
				List<Task> tasks = user.getTasks();
				if (tasks == null) {
					tasks = new ArrayList<Task>();
					
					taskDao.updateTask(task);
					tasks.add(task);
					user.setTasks(tasks);
					userDao.updateUser(user);
					
					System.out.println("Assigned!!");
				} else {
					
					taskDao.updateTask(task);
					tasks.add(task);
					user.setTasks(tasks);
					userDao.updateUser(user);
					System.out.println("Assigned!!");
				}
			} else {
				System.out.println("User Not Found!!!");
			}
		} else {
			System.out.println("Task Not Found");
		}
		
	}
	private static void taskOp() {
		boolean flag2 = true;
		while (flag2) {
			System.out.println("1.Add Task \n 2.View All Tasks \n 3.View Task by id  \n 4.Back");
			int ch1 = scanner.nextInt();
			switch (ch1) {
			case 1: {
				System.out.println("Enter task description");
				String desc = scanner.nextLine();
				desc = scanner.nextLine();
				System.out.println("You want to assign it now\n 1.Yes \n 2.No");
				int d = scanner.nextInt();
				Task task = context.getBean(Task.class);
				if (d == 1) {

					System.out.println("Enter user id to assign task");
					int id = scanner.nextInt();
					User user = userDao.getById(id);
					if (user != null) {
						List<Task> tasks = user.getTasks();

						
						task.setCompletedStatus("No");
						task.setDescription(desc);
						task.setAssignedStatus("Yes");
						if (tasks == null) {
							tasks = new ArrayList<Task>();
							taskDao.saveTask(task);
							System.out.println("Task Registered and assigned");
						} else {
							taskDao.saveTask(task);
							System.out.println("Task Registered!!!!");
						}
						tasks.add(task);
						user.setTasks(tasks);
						
						userDao.updateUser(user);
					} else {
						System.out.println("User Not Found");
					}
				} else if (d == 2) {
					task.setCompletedStatus("No");
					task.setAssignedStatus("No");
					task.setDescription(desc);
					taskDao.saveTask(task);
					System.out.println("Task Registered!!!!");
				}
			}
				break;
			case 2: {
				List<Task> tasks = taskDao.allTasks();
				if (tasks != null && tasks.size() > 0) {
					for (Task task : tasks) {
						System.out.println("Task Id       :" + task.getId());
						System.out.println("Task Descr    :" + task.getDescription());
						System.out.println("Task Assign st:" + task.getAssignedStatus());
						System.out.println("Task Complt st:" + task.getCompletedStatus());
						System.out.println("Task Create ti:" + task.getCreatedDT());
						System.out.println("Task Complt ti:" + task.getCompletedDT());
						System.out.println("========================================");
					}
				} else {
					System.out.println("No Tasks Found");
				}
			}
				break;
			case 3: {
				System.out.println("Enter task id");
				int id = scanner.nextInt();
				Task task = taskDao.getById(id);
				if (task != null) {
					System.out.println("Task Id       :" + task.getId());
					System.out.println("Task Descr    :" + task.getDescription());
					System.out.println("Task Assign st:" + task.getAssignedStatus());
					System.out.println("Task Complt st:" + task.getCompletedStatus());
					System.out.println("Task Create ti:" + task.getCreatedDT());
					System.out.println("Task Complt ti:" + task.getCompletedDT());
				} else {
					System.out.println("Task Not Found!!!");
				}
			}
				break;
			case 4: {
				flag2 = false;
			}
			}
		}
		
	}
	private static void empOp() {
		System.out.println(
				"1.Add Employee \n 2.Update Employee \n 3.View All Emps \n 4.View By id \n 5.Remove Employee \n 6.Back");
		int b = scanner.nextInt();
		switch (b) {
		case 1: {
			System.out.println("Enter user name");
			String name = scanner.next();
			System.out.println("Enter user email");
			String email = scanner.next();
			System.out.println("Enter user password");
			String password = scanner.next();
			User user = context.getBean(User.class);
			user.setEmail(email);
			user.setName(name);
			user.setPassword(password);
			user.setRole("Employee");
			userDao.saveUser(user);
			System.out.println("Employee Added!!!!");
		}
			break;
		case 2: {
			System.out.println("Enter user id for update");
			int id = scanner.nextInt();
			User user = userDao.getById(id);
			if (user == null) {
				System.out.println("===User Not Found====");
				break;
			}
			System.out.println("Enter user password for update");
			String password = scanner.next();
			user.setPassword(password);
			userDao.updateUser(user);
			System.out.println("User Updated Successfully!!!");
		}
			break;
		case 3: {
			List<User> users = userDao.allUsers();
			if (users != null && users.size() > 0) {
				for (User user : users) {
					if (!user.getRole().equals("Manager")) {
						System.out.println("=========Employee=============");
						System.out.println("Emp ID      :" + user.getId());
						System.out.println("Emp Name    :" + user.getName());
						System.out.println("Emp Email   :" + user.getEmail());
						System.out.println("Emp Password:" + user.getPassword());
						List<Task> tasks = user.getTasks();
						System.out.println("==========Tasks===============");
						if (tasks != null && tasks.size() > 0) {
							for (Task task : tasks) {
								System.out.println("Task Id       :" + task.getId());
								System.out.println("Task Descr    :" + task.getDescription());
								System.out.println("Task Assign st:" + task.getAssignedStatus());
								System.out.println("Task Complt st:" + task.getCompletedStatus());
								System.out.println("Task Create ti:" + task.getCreatedDT());
								System.out.println("Task Complt ti:" + task.getCompletedDT());
							}
						} else {
							System.out.println("No Task found");
						}
					}
				}
			} else {
				System.out.println("No Employees Found!!!");
			}
		}
			break;
		case 4: {
			System.out.println("Enter id");
			int i = scanner.nextInt();
			User user = userDao.getById(i);
			if (user != null) {
				System.out.println("=========Employee=============");
				System.out.println("Emp ID      :" + user.getId());
				System.out.println("Emp Name    :" + user.getName());
				System.out.println("Emp Email   :" + user.getEmail());
				System.out.println("Emp Password:" + user.getPassword());
				List<Task> tasks = user.getTasks();
				System.out.println("==========Tasks===============");
				if (tasks != null && tasks.size() > 0) {
					for (Task task : tasks) {
						System.out.println("Task Id       :" + task.getId());
						System.out.println("Task Descr    :" + task.getDescription());
						System.out.println("Task Assign st:" + task.getAssignedStatus());
						System.out.println("Task Complt st:" + task.getCompletedStatus());
						System.out.println("Task Create ti:" + task.getCreatedDT());
						System.out.println("Task Complt ti:" + task.getCompletedDT());
					}
				} else {
					System.out.println("No Task found");
				}
			} else {
				System.out.println("User Not Found!!!");
			}
		}
			break;
		case 5: {
			System.out.println("Enter id");
			int i = scanner.nextInt();
			User user = userDao.getById(i);
			if (user != null) {
				userDao.removeUser(user.getId());
				System.out.println("User Removed!!!");
			} else {
				System.out.println("User Not Found!!!");
			}
		}
			break;
		case 6: {
			return;
		}
			
		default: {
			System.out.println("Invalid choice!!!");
		}
		}

	}

	
}
