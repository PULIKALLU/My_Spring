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

===
entities are copied from previous project

ProductDao.java

OrderService.java

OrderappApplication.java

==================================

Try this:

CustomerDao.java interface

OrderService
@Autowired
CustomerDao customerDao;

* add Customer
* get Customers

=======================
 
 public interface CustomerDao extends JpaRepository<Customer,String> {

 }

 @Service
 public class OrderService {
 	@Autowired
 	private CustomerDao customerDao;

 	public Customer addCustomer(Customer c) {
 		return customerDao.save(c);
 	}

 	public List<Customer> getCustomers() {
 		return customerDao.findAll();
 	}
 }

==========

Product p = productDao.getById(2); ==> returns a Proxy object ; not actual content; no hit to DB


System.out.println(p.getName()); // hits the DB and populates the Product; But connection to DB is required

=============

JDBC
exeuteQuery("select")
exeuteUpdate("insert / delete / update")



===============


Day 3

CRUD operations are performed on Customer, Product and Order
CustomerDao
ProductDao
OrderDao

Item is completeley managed by Order itself ==> Cascade

ItemDao is not required


===============

Spring Starter Project
added dependecnies:
mysql/h2, jpa, web

Using Spring Data JPA dependencies:
* Spring boot creates datasource [ pool of database connections ] using info present in "application.properties" file
* Creates EntityManagerFactory using Hibernate and HikariCP
* Enables transaction management


Using Web dependecies:
* configures DispatcherServlet to accept all requests "*"
* configures Jackson library for Java <---> JSON; HttpMessageConverter
* bridges Servlet Container with Spring container

java/ JSON conversion following libraries are available:
1) jackson
2) jettison
3) gson
4) moxy

=================

Spring Data JPA
* we just create interfaces extends JpaRepository
* this repositroy has most of the common methods for CRUD operations
* Spring DATA JPA creates @Repository class using this interface
* Allows to create custom queries using @Query()

==================================================================

RESTful Web Services using Spring Boot

REST ==> REpresentational State Transfer

* Resource resides on server [ database / files / images/ printers]
* State of Resource at any given point is representation in various forms like XML / JSON / CSV / RSS / HTML

Representation is served to the client in various formats based on HTTP headers sent by client.

Accept: application/json

Accept: text/xml


Alternatives to RESTful Web services:
1) SOAP
2) GraphQL
3) Protobuf
4) Apache Thrift
5) IIOP

======================

Characterstics of RESTful Web Services
1) Client - Server
seperate the concerns of client and server; make it scalable; heterogenous
Traditional web application contains client and server techonlogies deployed on a single server;==> not scalable

2) Stateless
	* JWT

3) Cacheable
  * Cache on client or middle tier [ Redis ]

4) Layered


=================

RESTful Web services uses 
 "plural nouns" to identify resources
 "HTTP verbs" for actions [ GET, POST, PUT, DELETE]

GET needs HTTP header
Accept: application/json

Examples:
1) GET
  http://localhost:8080/api/products

  get all products 

2) GET
  http://localhost:8080/api/products/3

  get a product whose id is "3"

  use "pathparameter [ / ]" for fetching by PRIMARY KEY or to get child data

  GET
  http://localhost:8080/api/customers/banu@gmail.com/orders

  get all orders of "banu@gmail.com"

3) GET

	 http://localhost:8080/api/products?low=1000&high=5000

	 get products whose price is between 1000 and 5000

	 http://localhost:8080/api/products?page=1&size=20

	 get 20 records from page "1"

	 use "query parameter [ ? ]" for getting sub-set
=====

Http Header
Content-type: application/json

4) POST
	http://localhost:8080/api/products

	payload contains new "product data" to be added to "products" resources


5) PUT
		http://localhost:8080/api/products/3

		payload contains new "product info for product with id 3" to be updated in "products" resources

========

6) DELETE
		http://localhost:8080/api/products/3

		delete product whose id is 3

CRUD and HTTP

CREATE ==> POST
READ ==> GET
UPDATE ==> PUT / PATCH
DELETE ==> DELETE

==

GET and DELETE has no payload; these 2 are IDEMPOTENT methods ==> Safe methods

PUT, PATCH and POST contains payload; not IDEMPOTENT methods ==> Not safe

Assume PUT is banking transaction

{
	"type": credit,
	"amount": 5000,
	"account" : "SB123"

}

=================


Status Code:

200 ==> OK
201 ==> CREATED
302 ==> REDIRECTION
304 ==> Not Modified
400 ==> BAD REQUEST
401 ==> Unauthorized
404 ==> Resource not Found
500 ==> Internal server error

===========
Tasks:
1) Delete Product
2) CustomerController

==================

http://localhost:8080/api/products?low=1000&high=50000

 @GetMapping()
	public @ResponseBody List<Product> getProducts(@RequestParam(name="low", defaultValue = "0.0") double low,
			@RequestParam(name="high", defaultValue = "0.0") double high) {
		if(low != 0.0 && high != 0.0) {
			return service.byRange(low, high);
		}
		return service.getProducts();
		
	}

========


Order JSON:
Placiong ORder:

POST:  http://localhost:8080/api/orders
body:
{
	"customer" : {
		"email" : "sam@sg.com"
	},
	"items": [
		{
			"product" : {"id": 6}, "qty": 1
		},
		{
			"product" : {"id": 2}, "qty": 3
		}
	]
}

==========


AOP
Aspect Oriented Programing

Cross-cutting concerns which leads to code tangling and code scattering


public void transferFunds(...) {
	try {
	if(securityContext.getAuthorities(). equals("CUSTOMER")) {
		logger.debug("begin transaction");
		Tranasaction tx = em.beginTransaction();
		em.merge(acc1);
		logger.debug("account 1 updated!!");
		em.merge(acc2);
		logger.debug("account 2 updated!!");
		..
		tx.commit();
	}
	}catch(Exception ex) {
		tx.rollback();
	}
}

AOP Terminology:

Aspect:  Cross - cutting concern; bit of code which is not a part of main logic; but can be used along with main logic ==> Logging, Security, Profile, Transaction

JoinPoint: is a point in application where AOP aspect can be weaved. [Methods and Exception]

Pointcut: selected JoinPoint

Advice: before, after, after-returning, after-throwing, around

=========================


@Transactional is a Around Adice

@Aspect
public TransactionalAspect {
	@Around("execution(* com.sg.prj.service.*.*(..))")
	public Object doProfile(ProceedingJoinPoint pjp) throws Throwable {
		try {
			Transaction tx = em.beginTransaction();
			Object ret = pjp.proceed();
			tx.commit();
		} catch(Exception ex) {
			tx.rollback();
			return ex;
		}
		return ret;
	}
}

======================

AOP:
https://docs.spring.io/spring-framework/docs/2.0.x/reference/aop.html
Limitiation: No Dynamic PointCuts

================

@ControllerAdvice
	catchs Exceptions thrown from @Controller or @RestController
	==> handle it and write custom messages to client

===

NotFoundException.java
GlobalExceptionHandler.java
ProductController.java
LogAspect.java

=========

	<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-validation</artifactId>
	</dependency>
=========================

MethodArgumentNotValidException: 

Validation failed for argument [0] in public org.springframework.http.ResponseEntity<com.sg.prj.entity.Product> com.sg.prj.api.ProductController.addProduct(com.sg.prj.entity.Product) with 3 errors: 
	[Field error in object 'product' on field 'name': rejected value []; codes [NotBlank.product.name,NotBlank.name,NotBlank.java.lang.String,NotBlank]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [product.name,name]; arguments []; default message [name]]; default message [Name is required]] 

	[Field error in object 'product' on field 'quantity': rejected value [-12]; codes [Min.product.quantity,Min.quantity,Min.int,Min]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [product.quantity,quantity]; arguments []; default message [quantity],0]; 
	default message [Quantity -12 should be more than 0]] 

	[Field error in object 'product' on field 'price': rejected value [0.0]; codes [Min.product.price,Min.price,Min.double,Min]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [product.price,price]; arguments []; default message [price],10]; 
	default message [Price 0.0 should be more than 10]] ]

========
1) ReportDTO.java

2) OrderDao.java

public interface OrderDao extends JpaRepository<Order, Integer>{
	@Query("select new com.sg.prj.dto.ReportDTO(o.orderDate, o.total, c.email, c.firstName, c.lastName) "
			+ " from Order o inner join o.customer c")
	List<ReportDTO> getReport();
}


3) OrderService.java
public List<ReportDTO> getReports() {
		return orderDao.getReport();
	}

4) OrderController.java
	@GetMapping("/report")
	public @ResponseBody List<ReportDTO> getReports() {
			return service.getReports();
	}
http://localhost:8080/api/orders/report

======

RestTemplate is to consume REST API

========================================

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>

Unit testing RestControllers

AAA ==> Assemble Action and Assert

* JUnit is used Unit testing framework [ TestNG]
* Mockito as mocking framework [ EasyMock / JMock ]
* Hamcrest ==> good matchers for assertion
* jsonpath ==> validating JSON [https://jsonpath.com/]

==

* Spring Container should not load all the beans only components required for testing

@WebMvcTest(ProductController.class)

loads  DispatcherTestServlet with ProductController only

 MockMvc mockMvc; is used to make API calls GET / POST / PUT / DELETE

 ==============
	 <dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-redis</artifactId>
		</dependency>
=========

Day 3 Recap

1) RestController, @RequestMapping, @Responsebody, @RequestBody, 
@GetMapping(), @PostMapping(), @PutMapping(), @DeleteMapping()
@PathVariable, @RequestParam

2) RestTemplate
	to consume REST api;
	getForObject(), getForEntity(), exchange(), postForEntity(),
	String result = template.getForObject("http://localhost:8080/api/products", String.class);

	ResponseEntity<List<Product>> response = template.exchange("http://localhost:8080/api/products",
				HttpMethod.GET, null , new ParameterizedTypeReference<List<Product>>() {
				});

3) Testing with Spring Boot 
	spring-boot-starter-test dependency is included by default
	* JUnit
	* Mockito 
	* Hamcrest provides matchers like : hasSize() hasItem()
	* JsonPath to extract JSON information from payload
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$[0].id", is(1)))
	* MockMvc bean to simulate API calls like get(), post() , ...
	==> load only one controller which is under test; mock the beans which needs to be injected into Controller [ @Autowired]

4) AOP
5) Exception Handling using @ControllerAdvice and @Valid with input validation [ javax.validation.constraints ==> @Min, @Max, @NotBlank, @NotNull, @Pattern, @Past, @Future, ..]


Day 4
properites file
spring.datasource.url=jdbc:mysql://localhost:3306/SG_SPRING?createDatabaseIfNotExist=true
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver

YAML files
spring.datasource
	url=jdbc:mysql://localhost:3306/SG_SPRING?createDatabaseIfNotExist=true
	driverClassName=com.mysql.cj.jdbc.Driver

RESTful API Documentation
1) RAML
	RESTful API Modeling Language is a YAML-based language for describing RESTful APIs
/songs:
  description: Collection of available songs in Jukebox
  get:
    description: Get a list of songs based on the song title.
    queryParameters:
      songTitle:
        description: "The title of the song to search (it is case insensitive and doesn't need to match the whole title)"
        required: true
        minLength: 3
        type: string
        example: "Get L"
    responses:
      200:
        body:
          application/json:
            example: |
              {
                "songs": [
                  {
                    "songId": "550e8400-e29b-41d4-a716-446655440000",
                    "songTitle": "Get Lucky"
                  },
                  {
                    "songId": "550e8400-e29b-41d4-a716-446655440111",
                    "songTitle": "Loose yourself to dance"
                  },
                  {
                    "songId": "550e8400-e29b-41d4-a716-446655440222",
                    "songTitle": "Gio sorgio by Moroder"
                  }
                ]
              }
2) OpenAPI / Swagger
	Programmatic way of generating RESTful documents
	The OpenAPI Specification, previously known as the Swagger Specification, is a specification for machine-readable interface files for describing, producing, consuming, and visualizing RESTful web services.

		<dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger2</artifactId>
			<version>2.7.0</version>
		</dependency>

		<dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger-ui</artifactId>
			<version>2.7.0</version>
		</dependency>

@ApiOpertation
@ApiParameter
@Api

======================================

Caching

1) Client side Caching
	HttpHeaders 
	a) Cache-Control

	@GetMapping("/{pid}/cachecontrol")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Product> getProductCache(@PathVariable("pid") int id) throws NotFoundException {
		 return ResponseEntity.ok().cacheControl(CacheControl.maxAge(60, TimeUnit.MINUTES)).body(service.getProduct(id));
	}

	b) ETag

	The ETag HTTP response header is an identifier for a specific version of a resource

	Server sends ETag "numeric value of response data" to the Client

	Next requests coming from client should have the In-None-Match header

	Accept: application/json
	In-None-Match: previous etag

	REstful server fetches data from database, generates Etag, matches with Etag sent by client
	if any changes then it sends the new data else SC 304 [ Not modified]


	@Version
	public int version;

	introducese "version" column in database;
	JPA automatically increments this column whenever data changes

	can also be useful in avoiding data corruption

	products
	id   qty  version
	12   299  1

	User 1
	productDao.findById(12);
		purchases 3 products and needs to set qty to 297

	User 2
	productDao.findById(12);	
		purchases 1products and needs to set qty to 299


	User 2 sets it first
		update products set qty = 299, version = version + 1 	where id = 12 and version 0


	User 1 is setting
		update products set qty = 297, version = version + 1 	where id = 12 and version 0
		==> StateStateException



===

ETag "-1394764243"


=============

PostMan:

1) GET http://localhost:8080/api/products/3/etag
Accept: application/json

Response gives ETag in Header

2) GET http://localhost:8080/api/products/3/etag
Accept: application/json
If-None-Match : value of previous ETag

Response will be 304 Not Modifed

===

Caching on MiddleTier


docker run --name my-redis –p 6379:6379  -d redis

====================================

To use Caching

1) 
	<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-cache</artifactId>
	</dependency>

2) @EnableCaching in any @Configuration class
Enables Springs annotation driven cache management cabilities

By default Spring boot provides ConcurrentHashMap CacheImplmentation

3)
	@Cacheable(value="productCache", key ="#id")
	@GetMapping("/{pid}")
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody Product getProduct(@PathVariable("pid") int id) throws NotFoundException {
		try {
			return service.getProduct(id);
		} catch (NoSuchElementException e) {
			throw new NotFoundException("Product with " + id + " doesn't exist!!!");
		}
	}

==
	@Cacheable(value="productCache", key ="#p.id")
	@PostMapping()
	public  @ResponseBody Product addProduct(@RequestBody @Valid Product p) {
	
==

Input conditions
	@Cacheable(value="productCache", key ="#p.id", condition="#p.price < 50000")
	@PostMapping()
	public  @ResponseBody Product addProduct(@RequestBody @Valid Product p) {

Output /result condition:

	@Cacheable(value="productCache", key ="#id", unless"#result == null")
	@GetMapping("/{pid}")
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody Product getProduct(@PathVariable("pid") int id) throws NotFoundException {

===
	update the cache
	@CachePut(value="productCache",  key ="#id" )
	@PutMapping("/{pid}")
	public @ResponseBody Product updateProduct(@PathVariable("pid") int id, @RequestBody Product p) {
		service.updateProduct(p.getPrice(), id);
		return service.getProduct(id);
	}

==

REmove from cache
@CacheEvict(value="productCache",   key ="#id")
@DeleteMapping("/{id}")
public String deleteProduct(@PathVariable("id") int id) {
	...
}

===
http://localhost:8080/api/products/clear

Remove all items from Cache
@CacheEvict(value="productCache",   allEntries = true)
@GetMapping("/clear")
public void clearAll() {
}

==

@EnableScheduling
@Scheduled(fixedRate = 1000)
@CacheEvict(value="productCache",   allEntries = true)
public void doTask() {
		System.out.println("task done!!!");
}

===

@Scheduled(cron = "0 0/30 * * * *") every half-hours

@Scheduled(cron = "@hourly")
public void doTask() {
		System.out.println("task done!!!");
}

================

@Autowired
private CacheManager cacheManger;


@Scheduled(fixedRate = 1000)
public void doTask() {
		for(String name: cacheManager.getCacheNames()) {
			cacheManger.getCache(name).clear();
		}
}

=====

@EnableCaching
@Cacheable
@CachePut
@CacheEvict

==
Using Redis as Cache Manager
<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>

package com.sg.prj.cfg;

import java.time.Duration;

import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

@Configuration
public class RedisCustomConfig {
	
	@Bean
	public RedisCacheConfiguration cacheConfiguration() {
		return RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(Duration.ofMinutes(60))
				.disableCachingNullValues()
				.serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
	}

	@Bean
	public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
		return (builder) -> builder
				.withCacheConfiguration("productCache",
						RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10)))
				.withCacheConfiguration("customerCache",
						RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(5)));
	}

}

====



Serializable is a mechansim used to write the state of object to a stream [ outside of JVM ] 
* Redis should be running [ Docker]

* If Nodejs is installed:
To start redis client:
npx redis-commander


======================

Spring Security

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>
		
Most of the configurations comes out of the box

1) creates user by name "user" with
Using generated security password: 2bee6472-57d6-4277-95c6-cc67873611d9

2) comes with login and logout pages

3) many filters are enabled for security

http://localhost:8080/login
http://localhost:8080/logout

===

Security is about:
1) Authentication
2) Authorization
3) ExceptionHandling

===============

jdbcAuth.zip
methodAuth.zip

=================

1) All requests are intercepted by DelegatingProxyFilter
2) UsernamePasswordAuthenticationFilter ==> attemptAuthenctication(request, response)
3) this filter extracts username/password from request
4) creates Authentication Object with principle/username, credentials, authorities[null], authenticated=false
5) Authentication objects is passed on to AuthenticationManager
6) AuthenticationManager connects AuthenticationProvider [ JdbcAuthentication, InMemory, Ldap, or custom]
7) each of these providers has UserDetailsService implementation
	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
8) If User exists successfulAuthentication() of UsernamePasswordAuthenticationFilter is called
9) UsernamePasswordAuthenticationFilter will now remove credentials from Authentication object adds authorities from UserDetails, set authenticated = true
10) UsernamePasswordAuthenticationFilter stores Authentication Object in SecurityContext.
11) subsequent requests made by clients uses SecurityContext to identity user / roles to allow access to resources

======================

Spring data jpa executes "schema.sql" and "data.sql" when application starts
JDBC uses User Schema:
https://docs.spring.io/spring-security/site/docs/4.2.x/reference/html/appendix-schema.html

=================

Security using JSESSIONID and SecurityContext ==> Stateful

Cookies are for conversational state of client

============

RESTful should be Stateless 

Recommended: use token based authentication
JWT ==> Json Web Token

==============================

Token:
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c

Part 1 Header:
{
  "alg": "HS256",
  "typ": "JWT"
}

Part2 Payload:
{
  "sub": "who i am",
  "name": "John Doe",
  "iat": 1516239022, // issued at time,
  "exp": 3434342, // expires
  "iss" : "SG",
  "authorities" : "ADMIN, USER"
}

Part 3 VERIFY SIGNATURE to validate token
HMACSHA256(
  base64UrlEncode(header) + "." +
  base64UrlEncode(payload),
  "TopSecret124XyZVerySecure"
) 

=====

extract jwtExample.zip

Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGhvcml0aWVzIjpbeyJhdXRob3JpdHkiOiJST0xFX0FETUlOIn0seyJhdXRob3JpdHkiOiJST0xFX1VTRVIifV0sImlhdCI6MTYzMjk5NTkyOSwiZXhwIjoxNjMzODA0MjAwfQ.bRJlFfmeh3moXNzAzG18fDwyAxQ3E8ATey0awSIaMdJalMiVsMeZF1DbOUUY_J93fW0ZL2yhi_7L-is5sd8t3A


Refresh Token

==========

Postman:

POST http://localhost:8080/login

Headers
Accept: application/json
Content-type: application/json

body:
{
	"username" : "admin",
	"password" : "secret"
}

this sends Response Headers
Authorization:Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGhvcml0aWVzIjpbeyJhdXRob3JpdHkiOiJST0xFX0FETUlOIn0seyJhdXRob3JpdHkiOiJST0xFX1VTRVIifV0sImlhdCI6MTYzMjk5NTkyOSwiZXhwIjoxNjMzODA0MjAwfQ.bRJlFfmeh3moXNzAzG18fDwyAxQ3E8ATey0awSIaMdJalMiVsMeZF1DbOUUY_J93fW0ZL2yhi_7L-is5sd8t3A

---
GET http://localhost:8080/admin

Headers:
Authorization:Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGhvcml0aWVzIjpbeyJhdXRob3JpdHkiOiJST0xFX0FETUlOIn0seyJhdXRob3JpdHkiOiJST0xFX1VTRVIifV0sImlhdCI6MTYzMjk5NTkyOSwiZXhwIjoxNjMzODA0MjAwfQ.bRJlFfmeh3moXNzAzG18fDwyAxQ3E8ATey0awSIaMdJalMiVsMeZF1DbOUUY_J93fW0ZL2yhi_7L-is5sd8t3A

==========================================

