
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
    │   │           ├── config
    │   │           │   └── ApiName
    │   │           ├── controller
    │   │           │   └── LogController.java
    │   │           │   └── OAuthController.java
    │   │           │   └── ProductController.java
    │   │           ├── dto
    │   │           │   └── LogDto.java
    │   │           │   └── GithubDto.java
    │   │           │   └── ProductDto.java
    │   │           ├── entity
    │   │           │   └── AccessToken.java
    │   │           │   └── Product.java
    │   │           │   └── Log.java
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
    │   │           │   └── LogFilter.java
    │   │           ├── form
    │   │           │   └── ProductForm.java
    │   │           │   └── LogForm.java
    │   │           ├── setting
    │   │           │   └── AccessTokenSetting.java
    │   │           ├── repository
    │   │           │   └── AccessTokenRepository.java
    │   │           │   └── LogRepository.java
    │   │           │   └── ProductRepository.java
    │   │           └── service
    │   │               └── AccessTokenService.java
    │   │               └── ImageService.java
    │   │               └── LogService.java
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
    │      |    ├── api-log.html
    │      |    ├── header.html
    │      |    ├── index.html
    │      |    ├── layout.html
    │      |    ├── profile.html
    |      |__ application.yml
    |      |__ application-local.yml
    |      |__ logback-spring.xml
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

table:logs

| Field                  | Type                | Null | Key | Default | Extra          |
|:-----------|:------------|:------------|:------------|:------------|:------------|
| id                     | bigint(20) unsigned | NO   | PRI | NULL    | auto_increment |
| api_name               | varchar(100)        | NO   |     | NULL    |                |
| http_method            | varchar(7)          | YES  |     | NULL    |                |
| http_status_code       | char(3)             | NO   |     | NULL    |                |
| access_times           | bigint(20) unsigned | NO   |     | NULL    |                |
| average_execution_time | double              | NO   |     | NULL    |                |
| aggregate_date         | date                | NO   |     | NULL    |                |





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
 
 
**ログ出力項目**
 
 * URL
 
 * HTTPメソッド
 
 * HTTPステータスコード
 
 * 実行にかかった時間
 
 * 集計日
 
1日1回午前1時にバッチ処理が実行され、昨日のログデータをDBに保存します。
 
 
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
 
 
### アプリケーションのデプロイ方法

- EC2サーバにログイン  
`ssh -i アクセスkeyファイル名 ec2-user@パブリックIPアドレス` 
 
- 必要なものをインストールする

  `sdk install gradle 6.5.1` gradleをインストール   
  `sudo yum install git` gitをインストール
  
  `mysql -u root -p`
  
   `sudo yum localinstall https://dev.mysql.com/get/mysql80-community-release-el7-1.noarch.rpm -y` Mysqlをインストール
 
- クローンをする

  `git clone git@github.com:teamlab-engineering/asada-restapi.git`でクローンしてくる
  
-  `cd asada-restapi`で移動する
 
- サービスの起動

有効化
`$ sudo systemctl enable api.service`

起動
`$ sudo systemctl start api.service`

動作しているかの確認
`$  sudo systemctl status api.service`

- ブラウザで表示
`asadahyuma-alb-1711287778.ap-northeast-1.elb.amazonaws.com`