
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
    │   │           │   └── FilterConfiguration
    │   │           │   └── MessageSourceConfiguration
    │   │           │   └── WebMvcConfig
    │   │           ├── controller
    │   │           │   └── OAuthController.java
    │   │           │   └── ProductController.java
    │   │           ├── dto
    │   │           │   └── GithubDto.java
    │   │           │   └── ProductDto.java
    │   │           ├── entity
    │   │           │   └── AccessToken.java
    │   │           │   └── Product.java
    │   │           ├── exception
    │   │           │   ├── AlreadyExistTitleException.java
    │   │           │   ├── ErrorResponse.java
    │   │           │   ├── OAuthExceptionHandler.java
    │   │           │   ├── ProductExceptionHandler.java
    │   │           │   ├── UnAuthorizedException.java
    │   │           │   ├── ProductImageNotDeletedException.java
    │   │           │   ├── ProductNotFoundException.java
    │   │           │   └── ProductNotImageException.java
    │   │           │   └── UnsupportedMediaTypeException.java
    │   │           ├── filter
    │   │           │   └── AccessTokenFilter.java
    │   │           ├── form
    │   │           │   └── ProductForm.java
    │   │           ├── setting
    │   │           │   └── AccessTokenSetting.java
    │   │           ├── repository
    │   │           │   └── AccessTokenRepository.java
    │   │           │   └── ProductRepository.java
    │   │           └── service
    │   │               └── AccessTokenService.java
    │   │               └── ImageService.java
    │   │               └── OAuthService.java
    │   │               └── ProductService.java
    │   └── resources
    │      └── static
    │      |    ├── image
    │      └── templates
    │      |    ├── error
    │      |    |    ├── 401.html
    │      |    |    ├── 404.html
    │      |    |    ├── 500.html
    │      |    ├── header.html
    │      |    ├── index.html
    │      |    ├── layout.html
    │      |    ├── profile.html
    |      |__ application.yml
    |      |__ application-local.yml
    |      |__ messages.properties
    |      |__ schema.sql
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

| Field | Type | Null | Key | Defalt | Extra |
|:-----------|:------------|:------------|:------------|:------------|:------------|
| id         |  bigint(20) unsigned  | NO   | PRI | NULL    | auto_increment |
| title       | varchar(100)          | NO  | UNI    | NULL    |                |
| price   | int                 | NO  |     | NULL    |                |
| description      |  varchar(500)        | NO   |     | NULL    |                |
| image_path |  text            | YES  |     | NULL    |                |
| create_time |  datetime            | NO  |     | CURRENT_TIMESTAMP    |                |
| update_time |  datetime            | NO  |     | CURRENT_TIMESTAMP    | on update CURRENT_TIMESTAMP                 |

table:access_tokens

| Field | Type | Null | Key | Defalt | Extra |
|:-----------|:------------|:------------|:------------|:------------|:------------|
| id         |  bigint(20) unsigned  | NO   | PRI | NULL    | auto_increment |
| access_token       | varchar(100)          | NO  | UNI    | NULL    |                |
| create_time |  datetime            | NO  |     | CURRENT_TIMESTAMP    |                |
| update_time |  datetime            | NO  |     | CURRENT_TIMESTAMP    | on update CURRENT_TIMESTAMP   




### 環境構築手順


**DBの作成**

```
mysql> CREATE DATABASE restful_api; 
```

**git clone**

```
git clone git@bitbucket.org:teamlabengineering/asada-restapi.git
```

**アクセストークン認証**

 * トップ画面のログインボタンからログイン
 
 * ログイン後のプロフィール画面から[アクセストークン]をコピー
 
 * リクエストの際Headerに以下の設定を加える
   + KEYに「Authorization」
   + VALUEに「Bearer [アクセストークン]」

 

**Github認証設定**
 
 * GithubでOAuth用アプリケーションの作成 `https://github.com/settings/developers`
 
 * 各項目を登録 App name : asada-restful-api
 
 * HomePage URL : http://localhost:8080
 
 * Authorization callback URL : http://localhost:8080/github/callback
 
 * Client ID・Client Secretを控える
 
 **アプリ起動**
 
 * .bash_profileに環境変数としてClient ID・Client Secretを記述し起動
 `$ open ~/.bash_profile`
 * .bash_profileに以下を追加し、上書き保存
 
```
export GITHUB_CLIENTID=Client ID
```

```
export GITHUB_CLIENTSECRET=Client Secret
```

```
$ $source ~/.bash_profile
```

 * `$ gradle bootRun`