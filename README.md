
# RESTfulAPI課題(Java簡易版)

   
   
## 使用した技術要素

| 技術 | バージョン |
|:-----------|------------:|
| Java       |         "11" | 
| Spring boot     |      2.1.5 |   
| Gradle       |         5.4.1 |        
| MySQL       |            5.7 |  
| Swagger       |        3.0.0 |   
 


 * SpringInitiallizer
     + Spring Boot DevTools
     + Spring Web Starter
     + Thymeleaf
     + Spring Data JPA
     + MySQL Driver
  
## 全体の設計・構成
 

```
 
 .
 └── src
    ├── main
    │   ├── java
    │   │   └── com.example
    │   │           ├── controller
    │   │           │   └── ItemController.java
    │   │           ├── entity
    │   │           │   └── Api.java
    │   │           ├── exception
    │   │           │   ├── ItemExceptionHandler.java
    │   │           │   └── ItemNotFoundException.java
    │   │           │   └── ItemImageNotDeletedException.java
    │   │           ├── repository
    │   │           │   └── ItemRepository.java
    │   │           └── service
    │   │               └── ImageService.java
    │   │               └── ItemService.java
    │   └── resources
    │       └── static
    │       |    ├── image
    |       |__ application.yml
    └── test
        └── java
            └── com
                └── example
                    └── Api
                        └── ProjectTodolistApplicationTests.java
                        
```
  
## DB設計

Database名：api

table:items

| Field | Type | Null | Key | Defalt | Extra |
|:-----------|:------------|:------------|:------------|:------------|:------------|
| id         | int(11)  | NO   | PRI | NULL    | auto_increment |
| name       | varchar(100)          | YES  |     | NULL    |                |
| price   | int(11)                | YES  |     | NULL    |                |
| description      |  varchar(255)        | NO   |     | NULL    |                |
| image |  varchar(255)            | YES  |     | NULL    |                |




### 環境構築手順


**DBの作成**

```
$ mysql -u root
mysql> CREATE DATABASE api; 
```

**git clone**

`
git clone git@bitbucket.org:teamlabengineering/asada-restapi.git
`

**SpringBoot起動**

`$ gradle bootRun`

**ブラウザで開く**

`http://localhost:8080/api/items`