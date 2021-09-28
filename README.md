# SPRING

Banuprakash C

Full Stack Architect,

Co-founder Lucida Technologies Pvt Ltd.,

Corporate Trainer,

Email: banuprakashc@yahoo.co.in

https://www.linkedin.com/in/banu-prakash-50416019/


https://github.com/BanuPrakash/SPRING

===================================

Softwares Required:
1) Java 8+
	https://www.oracle.com/in/java/technologies/javase/javase-jdk8-downloads.html

2) Eclipse for JEE  
	https://www.eclipse.org/downloads/packages/release/2021-09/m1/eclipse-ide-enterprise-java-and-web-developers

3) MySQL  [ Prefer on Docker]

Install Docker Desktop

Docker steps:

a) docker pull mysql

b) docker run --name local-mysql –p 3306:3306 -e MYSQL_ROOT_PASSWORD=Welcome123 -d mysql

container name given here is "local-mysql"

For Mac:
docker run -p 3306:3306 -d --name local-mysql -e MYSQL_ROOT_PASSWORD=Welcome123 mysql


c) CONNECT TO A MYSQL RUNNING CONTAINER:

$ docker exec -t -i <container_name> /bin/bash

d) Run MySQL client:

bash terminal> mysql -u "root" -p

mysql> exit

=============================================================================

Day 1

-------

Spring Framework:

Lightweight Container for building enterprise applications.


containers are layers on JVM.

Spring provids Dependency Injection and Life Cycle Management with its Core container.

What is DI?

Spring Core Module provides Dependency Injection.

Modules provided by Spring:
1) Core Module
2) JDBC ==> Simpilify using JDBC
3) ORM ==> using Spring with ORM frameworks like Hibernate, TopLink, OpenJPA, EclipseLink
4) Web ==> simplifies building web application development [ Traditional or RESTful or GraphQL]
5) Transaction ==> simplifies transaction managment
6) AOP ==> Aspect Oriented Programming
....

Spring uses metadata to manage lifecycle of beans and wiring of beans

// entity class / Domain / Model
public class Employee {
	fields / getters / setters
}

public interface EmployeeDao {
	public void addEmployee(Employee e);
}

public class EmployeeDaoFileImpl implements EmployeeDao {
	public void addEmployee(Employee e) {
		...
	}	
}

public class EmployeeDaoJdbcImpl implements EmployeeDao {
	public void addEmployee(Employee e) {
		...
	}
}


public class AppService {
	private EmployeeDao empDao; // dependency

	public void setEmpDao(EmployeeDao dao) {
		this.empDao = dao;
	}

	public void insert(Employee e) {
		empDao.addEmployee(e);
	}
}


XML as Metadata:

<bean id="jdbcImpl" class="pkg.EmployeeDaoJdbcImpl" />

<bean id="fileImpl" class="pkg.EmployeeDaoFileImpl" />

<bean id="appService" class="pkg.AppService">
	<property name="empDao" ref="fileImpl" />
</bean>



=======================

Java 1.5 version introduced Annotations

Spring Framework creates beans which has one of these annotations at classlevel.

1) @Component
2) @Repository
3) @Service
4) @Configuration
5) @Controller
6) @RestController

https://github.com/spring-projects/spring-framework/blob/main/spring-jdbc/src/main/resources/org/springframework/jdbc/support/sql-error-codes.xml

public interface EmployeeDao {
	public void addEmployee(Employee e);
}

@Repository
public class EmployeeDaoFileImpl implements EmployeeDao {
	public void addEmployee(Employee e) {
		...
	}	
}

@Service
public class AppService {
	@Autowired
	private EmployeeDao empDao; // dependency

 	public void insert(Employee e) {
		empDao.addEmployee(e);
	}
}


==

try {
	.. code ...
} catch(SQLException ex) {
	if(ex.getErrorCode() == 1) {
		thrown new DataAcessException("Duplicate key")
	} 
}


@Repository
public class EmployeeDaoJdbcImpl implements EmployeeDao {
	public void addEmployee(Employee e) {
		...
	}	
}

=============

Creating a Spring Container:

1) ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");

2) ApplicationContext ctx = new FileSystemXmlApplicationContext("/users/nameofuser/beans.xml");

Spring Container using Annotations:

3) AnnotationConfigApplicationContext = AnnotationConfigApplicationContext();

=========
pom.xml

1) com.sg.prj.entity;
 public class Employee

2) com.sg.prj.dao;
EmployeeDao.java
EmployeeDaoJdbcImpl.java

3) com.sg.prj.service;
AppService.java

4) com.sg.prj.client
FirstExample.java


======
 
When more than one bean implements the same interface and when @Autowired we get:
NoUniqueBeanDefinitionException: No qualifying bean of type 'com.sg.prj.dao.EmployeeDao' available: expected single matching bean but found 2: employeeDaoFileImpl,employeeDaoJdbcImpl

1) Solution 1:
Mark one of the implmentation with @Primary

@Repository
@Primary
public class EmployeeDaoFileImpl implements EmployeeDao {

...

@Repository
public class EmployeeDaoJdbcImpl implements EmployeeDao {


2) Solution 2: 
Don't use @Primary instead use @Qualifier while @Autowired


@Service
public class AppService {
	@Autowired
	@Qualifier("employeeDaoJdbcImpl")
	private EmployeeDao empDao;
	
---

3) Solution 3: using profile ; Most recommended solution


@Repository
@Profile("prod")
public class EmployeeDaoJdbcImpl implements EmployeeDao {


@Repository
@Profile("dev")
public class EmployeeDaoFileImpl implements EmployeeDao {


@Service
public class AppService {
	@Autowired
	private EmployeeDao empDao;

FirstExample ==> Run As ==> Run Configurations

Arguments : VM Arguments
-Dspring.profiles.active=dev

======================
When to use @Bean

* Spring uses default constructor for creating instances of bean.
* to create instances of classes which are provided by 3rd party libraries

==============

@Repository
@Service
@Autowired
@Qualifier
@Primary
@Profile
@Configuration
@Bean


================

MySQL as RDBMS

ORM ==> Object Relational Mapping

ORM frameworks: Hibernate, TopLink, KODO, OpenJPA, EclipseLink, JDO

JPA ==> Java Persistence API is a specification for ORM frameworks


=================

<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <version>1.4.200</version>
 </dependency>



====

props.setProperty("hibernate.hbm2ddl.auto", "update");

hbm ==> Hibernate Mapping
DDL ==> Data Definition Language ==> CREATE , ALTER and DROP

1) update
* create table if doesn't exist
* use existing table if present
* alter table if required

props.setProperty("hibernate.hbm2ddl.auto", "create");
2) create
* drop tables and re-create every run of application

3) validate
* use tables as present in backend if it matches; if it doesn't match throw error

==========================

props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");

em.persist(p); ==>   ORM should generate SQL for MySQL8


===

Local-Mysql client:

terminal> docker exec -it local-mysql bash

# mysql -u "root" -p
Enter Password: Welcome123

=====

Transaction Management:

Programatic Transaction:

* JDBC

public void transferFunds(Account fromAcc, Account toAcc, double amt) {
	Connection con = null;
	try {
		con = DriverManger.getConnection(URL, USER, PWD);
		con.setAutoCommit(false); // transaction begins
			PreparedStatement ps1 == > update "fromAcc"
			PreparedSteament ps2 ==> update "toAcc"
			PreparedStatement ps3 ==> insert into daily_trnsactions table

			set IN parameters for ps1, ps2, ps3;
			exceuteUPdate() on ps1, ps2 and ps3
		con.commit();
	} catch(SQLExceptione ex) {
		con.rollback();
	} finally {
		con.close();
	}

}

* JPA

public void transferFunds(Account fromAcc, Account toAcc, double amt) {
	try {
	Transaction tx = em.beginTransaction();
		em.merge(fromAcc);
		em.merge(toAcc);
		em.persist(transaction);
	tx.commit();
  }catch(Exception ex) {
  	tx.rollback();
  }

 ====

 With Declarative Transaction just add @Transactional on method irrespective of JPA / JDBC / JTA / ...

@Transactional
public void transferFunds(Account fromAcc, Account toAcc, double amt) {
}

If any exception occurs in this method and not caught the rollback else commit

===========================================


CustomerDao and Impl class
 ==> add customer
 ==> get Customer by last name ==> JPQL ==> from Customer where lastName = 'Raj'

===


* @JoinColumn(name = "customer_fk")

=> with @ManyToOne() will introduce foreign key in owning table [ orders]
=> with OneToMany will introduce foreign key in child table

=========


@Entity
@Table(name="orders")
public class Order {
	
	@OneToMany()
	@JoinColumn(name="order_fk") 
	private List<Item> items = new ArrayList<>();

Suppose if 1 order has 5 items ==> 6 CALLS TO database
orderDao.persist(order); // saves into orders table

for(Item item: order.getItems()) {
	itemDao.persist(item); // 5 calls to items table
}


=========
With Cascade


@Entity
@Table(name="orders")
public class Order {

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="order_fk") 
	private List<Item> items = new ArrayList<>();


Suppose if 1 order has 50 items ==>  1 call

orderDao.persist(order); // saves into orders table
saving order saves all its items;

--
Similary 

orderDao.deleteOrder(order); // deleting order deletes all its items also

======================

http://databaseanswers.org/data_models/
http://databaseanswers.org/data_models/agile_retail_sales/index.htm


Task:

http://databaseanswers.org/data_models/car_hire/index.htm

Entites
Booking.java
Customer.java
Vehicle.java

===================

 

Bug Report / Tickets

Projects and Employee:

@Entity
@Table(name="employees") 
public class Employee {
	@Id
	private String email;

	private String name;

}



@Entity
@Table(name="clients") 
public class Client {
	@Id
	@GeneratedValue(..)
	private int id;

	private String name;

}



@Entity
@Table(name="projects") 
public class Project {
	@Id
	@GeneratedValue(..)
	private int id;

	private String name;

	@Column(name="start_date")
	private Date startDate = new Date();

	@Column(name="end_date")
	private Date endDate = new Date();

	@ManyToOne
	@JoinColumn(name="client_fk")
	private Client client;
}	


==


@Entity
@Table(name="projects_employees") 
public class ProjectEmployee {
	@Id
	@GeneratedValue(..)
	private int id;

	@ManyToOne
	@JoinColumn(name="pid_fk")
	private Project project;

	@ManyToOne
	@JoinColumn(name="emp_fk")
	private Employee employee;

	@Column(name="start_date")
	private Date startDate = new Date();

	@Column(name="end_date")
	private Date endDate = new Date();

	private String role;

}

==========

Day 2

no need for ItemDao ==> Items are managed by Order using Cascade

Client places an order:
Rquirement: Customer and Products should exist

Order
	Customer ==> email
	Items
		1) Product [ id ], qty
		2) Product [ id ], qty
		3) Product [ id ], qty

In the backend:
* compute amount for item [ product price * Qty + tax - discount]
* compute total of order [ sum(items amount) + GST ]
* Once product is purchased; should reduce the quantity of product in inventory


===

H2 Inmemory
1) Products should be added
2) Customer should be added
2) execute OrderClient

============================

By default OneToMany Mapping is LAZY loading
and ManyToOne is EAGER loading

================================================================

* Core Module
* Transaction Module helps declartive trasnaction @Transactional
* Spring ORM module to integrate with ORM frameworks like Hibernate
* Spring Web MVC module


Traditional Web application development using Spring MVC

Model View Controller


@Controller
public class ProductController {
	@Autowired 
	private OrderService service;

	@RequestMapping("getProducts.do") 
	public ModelAndView getProducts() {
		ModelAndView mav = new ..
		mav.addObject("products", service.getProducts());
		mav.setViewName("print.jsp");
	}	
}

===================

Run As ==> Maven Build ==> Goals ==>
jetty:run

==========================

mvn clean install

mvn jetty:run

==================



index.jsp
form.jsp
print.jsp

ProductController.java


======================================

Spring Boot

Eclipse ==> Help ==> Eclipse Market Place ==> Search:  STS ==> GO
and select Spring Tools 3 or 4
Spring tools 3.9.14.RELEASE

=============================================


Spring Boot
a framework as a layer on Spring Framework

Why Spring Boot?
* Many configurations comes out of the box
* Application development is very easy
* Makes the application containerizable

--

* Spring boot is highly opiniated framework
1) If we build ORM based application; Spring boot out of box gives you HikariCP database connection pool; 
Configures Hibernate as the ORM framework; enables declarative Transactions
* simply put AppConfig.java is not required

2) For web application development; it configures DispatcherServlet to handle "*" url-pattern
* Provides JSON HttpMessageHandlers ==> ContentNegotiationHandlers Java <--> 
* Configures Embedded Tomcat Server
==> Not Required AppInitializer.java and "jetty plugin in pom.xml"

==> Run applciation with main() method ==> it starts Tomcat server


====================

Spring Boot application:
1) new Spring-starter-project
2) add "mysql" , "Spring data jpa", and "spring web"

-----------------------



@SpringBootApplication
public class OrderappApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderappApplication.class, args);
	}

}



@SpringBootApplication is 3 in one:
1) @ComponentScan
will scan all classes from "com.sg" and sub-packages for @component, @Repository, @Service, @Controller,@RestController,@Configuration and creates instances of these classes

2) @EnableAutoConfiguration
this creates beans which are a part of "jar" files like auto configure of HikariCP, EntityManager, ...

3) @Configuration


SpringApplication.run() creates Spring Container

===

Spring Data JPA has built-in interfaces like JpaRepository

If we create interfaces extends JpaRepositroy; spring data jpa creates @Repository classes with std. methods


H2 Console:

http://localhost:8080/h2console/