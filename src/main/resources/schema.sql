create table if not exists product
(
    id    bigint unsigned not null auto_increment comment '商品ID',
    title  varchar(100)    not null comment '商品タイトル',
    description  varchar(500)    not null comment '商品説明文',
    price   int  unsigned not null comment '商品価格',
    image_path   text     comment '商品画像パス',
    create_time datetime        not null default current_timestamp comment '作成日時',
    update_time  datetime    not null default current_timestamp on update current_timestamp comment '更新日時',
    primary key (id),
    unique key (title)
) engine = innodb
  charset = utf8mb4 comment = '商品テーブル';

  create  table  if not exists access_tokens
  (
  id bigint unsigned not null auto_increment comment 'アクセストークンID',
  access_token varchar(100)    not null  comment 'APIアクセストークン',
  create_time datetime        not null default current_timestamp comment '作成日時',
  update_time  datetime    not null default current_timestamp on update current_timestamp comment '更新日時',
  primary key (id),
  unique key (access_token)
) engine = innodb
  charset = utf8mb4 comment 'アクセストークンテーブル';

  create  table  if not exists logs
  (
  id bigint unsigned not null auto_increment comment 'アクセストークンID',
  api_name varchar(100)    not null  comment 'API名',
  http_method varchar(5000)        not null comment 'HTTPメソッド',
  http_status_code char(3)    not null  comment 'HTTPステータスコード',
  access_times    bigint unsigned not null comment 'アクセス回数',
  average_execution_time double    not null comment '平均実行時間',
  aggregate_date    date    not null comment '集計日',
  primary key (id)
) engine = innodb
  charset = utf8mb4 comment 'ログ集計テーブル';

