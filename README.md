BUILD & RUN:
1. open terminal
2. go to project directory
3. run 'mvn clean install' command to BUILD the project
4. run 'java -jar target/transaction-service-0.0.1.jar' to RUN the project
------------------------------------
REQUEST:
TRANSACTION:
POST request url:
http://localhost/transactionservice/transactions
POST request body:
{
	"amount": 1,
	"timestamp": 1530301576706
}

STATISTICS:
GET request url:
http://localhost/transactionservice/statistics
response:
{
    "sum": 0,
    "avg": 0,
    "max": 0,
    "min": 0,
    "count": 0
}
------------------------------------
TEST:
1. Unit Test
2. Integration Test
------------------------------------
MULTITHREADING:
1. ConcurrentHashMap collection
2. compute method of ConcurrentHashMap is used for atomic operation
------------------------------------
ALGORITHM COMPLEXITY:
1. Post request done in constant time
2. Get request done in constant time
------------------------------------
DESIGN PATTERN:
1. Builder Pattern
2. Static Factory Method