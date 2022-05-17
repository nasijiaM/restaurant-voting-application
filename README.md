A voting system for deciding where to have lunch.
===============================
- 2 types of users: admin and regular users
- Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a menuItem name and price)
- Menu changes each day (admins do the updates)
- Users can vote on which restaurant they want to have lunch at
- Only one vote counted per user
- If user votes again the same day:
  If it is before 11:00 we assume that he changed his mind.
  If it is after 11:00 then it is too late, vote can't be changed.

Each restaurant provides a new menu each day.

-------------------------------------------------------------
- Stack: [JDK 17](http://jdk.java.net/17/), Spring Boot 2.5, Lombok, H2, Caffeine Cache, Swagger/OpenAPI 3.0
- Run: `mvn spring-boot:run` in root directory.
-----------------------------------------------------
[REST API documentation](http://localhost:8080/swagger-ui.html)  
Credentials:
```
User:  user@yandex.ru / password
Admin: admin@gmail.com / admin
```
### curl samples

#### get All Users
`curl -s http://localhost:8080/api/admin/users --user admin@gmail.com:admin`

#### get User 2
`curl -s http://localhost:8080/api/admin/users/2 --user admin@gmail.com:admin`

#### register User
`curl -s -i -X POST -d '{"name":"New User","email":"test@mail.ru","password":"test-password"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/api/profile`

#### get Profile
`curl -s http://localhost:8080/api/profile --user test@mail.ru:test-password`

#### get All MenuItems
`curl -s http://localhost:8080/api/admin/menu-items --user admin@gmail.com:admin`

#### get MenuItem 3
`curl -s http://localhost:8080/api/admin/menu-items/3  --user admin@gmail.com:admin`

#### get All Restaurants with MenuItems
`curl -s http://localhost:8080/api/restaurants/with-menu-items --user user@yandex.ru:password`

#### delete Restaurant
`curl -s -X DELETE http://localhost:8080/api/admin/restaurants/2 --user admin@gmail.com:admin`

#### update Restaurant
`curl -s -X PUT -d '{"name":"Updated restaurant"}' -H 'Content-Type: application/json' http://localhost:8080/api/admin/restaurants/2 --user admin@gmail.com:admin`

#### get Restaurant 2
`curl -s http://localhost:8080/api/admin/restaurants/2  --user admin@gmail.com:admin`

#### vote
`curl -X POST -H 'Content-Type: application/json' -i 'http://localhost:8080/api/votes?id=2' --data '{
"id": 2
}' --user user@yandex.ru:password`

#### get Vote 1
`curl -s http://localhost:8080/api/votes/1  --user user@yandex.ru:password`

#### get All Votes with Restaurants
`curl -s http://localhost:8080/api/votes --user user@yandex.ru:password`