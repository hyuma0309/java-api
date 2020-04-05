
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
    │   │   └── com.teamlab.engineering.restfulapi
    │   │           ├── config
    │   │           │   └── MessageSourceConfiguration
    │   │           ├── controller
    │   │           │   └── ProductController.java
    │   │           ├── dto
    │   │           │   └── ProductDto.java
    │   │           ├── entity
    │   │           │   └── Product.java
    │   │           ├── exception
    │   │           │   ├── AlreadyExistTitleException.java
    │   │           │   ├── ProductExceptionHandler.java
    │   │           │   ├── ProductImageNotDeletedException.java
    │   │           │   ├── ProductNotFoundException.java
    │   │           │   └── ProductNotImageException.java
    │   │           │   └── UnsupportedMediaTypeException.java
    │   │           ├── form
    │   │           │   └── ProductForm.java
    │   │           ├── repository
    │   │           │   └── ProductRepository.java
    │   │           └── service
    │   │               └── ProductService.java
    │   │               └── ProductService.java
    │   └── resources
    │       └── static
    │       |    ├── image
    |       |__ application.yml
    |       |__ application-local.yml
    |       |__ messages.properties
    |       |__ schema.sql
    └── test
        └── java
            └── com
                └── teamlab
                    └── engineering
                        └── restfulapi
                            └── PRestfulapiApplicationTests.java


                        
```
  
## DB設計

Database名：restful_api

table:product

| Field       | Type                | Null | Key | Default           | Extra                       |
+-------------+---------------------+------+-----+-------------------+-----------------------------+
| id          | bigint(20) unsigned | NO   | PRI | NULL              | auto_increment              |
| title       | varchar(100)        | NO   | UNI | NULL              |                             |
| description | varchar(500)        | NO   |     | NULL              |                             |
| price       | int(10) unsigned    | NO   |     | NULL              |                             |
| image_path  | text                | YES  |     | NULL              |                             |
| create_time | datetime            | NO   |     | CURRENT_TIMESTAMP |                             |
| update_time | datetime            | NO   |     | CURRENT_TIMESTAMP | on update CURRENT_TIMESTAMP |
+-------------+---------------------+------+-----+-------------------+-----------------------------+




### 環境構築手順


**DBの作成**

```
mysql> CREATE DATABASE restful_api; 
```

**git clone**

`
git clone git@bitbucket.org:teamlabengineering/asada-restapi.git
`

**SpringBoot起動**

`$ gradle bootRun`

**ブラウザで開く**

`http://localhost:8080/api/products`