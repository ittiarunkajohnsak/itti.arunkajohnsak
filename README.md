### Design
1. Create project with spring-boot
2. Integrate jwt with spring-security
3. Use h2 database file keep it data
4. Use Jpa with spring-data to kepp and store data
5. Use JUnit for add testcase and code coverage

### Installation
``` Run main class : Application.java ```

### Usage
- Get Jwt Token
```  
   POST : http://localhost:8080/authenticate  
        header : Content-Type : application/json  
        body : {  
                 "username": "itti.test",  
                 "password": "password"  
               }
```

- Register user  
```
   POST : http://localhost:8080/user  
        header : Content-Type : application/json  
               : Authorization: Bearer <jwt_token>  
        body   : {  
                   "username": "",  
                   "password": "",  
                   "firstName": "",  
                   "lastName": "",  
                   "gender": "",  
                   "address": "",  
                   "phone": "",  
                   "salary": ""  
                 }
```

- Get all users
```
   GET : http://localhost:8080/users  
       header : Authorization: Bearer <jwt_token>
```  