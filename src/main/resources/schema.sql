create table if not exists product
(
    id           bigint unsigned not null auto_increment comment '商品ID',
    title        varchar(100)    not null comment '商品タイトル',
    description  varchar(500)    not null comment '商品説明文',
    price        int    unsigned not null comment '商品価格',
    image_path   text                     comment '商品画像パス',
    create_time  datetime        not null default current_timestamp comment '作成日時',
    update_time  datetime        not null default current_timestamp on update current_timestamp comment '更新日時',
    primary key (id),
    unique key (title)
) engine = innodb
  charset = utf8mb4 comment = '商品テーブル';