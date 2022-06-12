create table if not exists t_order
(
    id           int auto_increment
    primary key,
    user_id      int            not null,
    order_time   int            not null,
    total_amount decimal(32, 2) not null
    );

create table if not exists t_order_detail
(
    id             int auto_increment
    primary key,
    order_id       int            not null,
    sku_id         int            not null,
    price          decimal(32, 2) not null,
    discount_price decimal(32, 2) null,
    count          int            not null,
    title          varchar(256)   not null,
    cover          varchar(512)   not null
    );

create table if not exists t_sku
(
    id             int auto_increment
    primary key,
    spu_id         int            not null,
    spec_id        int            not null,
    price          decimal(32, 2) not null,
    discount_price decimal(32, 2) null
    );

create table if not exists t_spu
(
    id             int auto_increment
    primary key,
    title          varchar(256)   not null,
    cover          varchar(512)   not null,
    price          decimal(32, 2) not null,
    discount_price decimal(32, 2) null
    );

create table if not exists t_spu_detail
(
    spu_id  int  not null
    primary key,
    covers  json not null,
    details json not null
);

create table if not exists t_users
(
    id    int auto_increment
    primary key,
    name  varchar(64) not null comment '用户姓名',
    phone varchar(20) not null comment '手机号码'
    );

