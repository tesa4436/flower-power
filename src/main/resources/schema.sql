create table basket (
    basket_id bigint not null,
    primary key (basket_id)
);
create table basket_items (
    basket_basket_id bigint not null,
    items_id bigint not null
);
create table flower_order (
    order_id bigint not null,
    creation_date timestamp,
    greeting_message varchar(255),
    payment_method varchar(255),
    shipping_address varchar(255),
    status integer, basket_basket_id bigint,
    primary key (order_id)
);
create table item (
    name varchar(255),
    id bigint not null,
    amount bigint,
    creation_date timestamp,
    description varchar(255),
    item_type integer,
    photo_id bigint,
    price decimal(19,2),
    primary key (id)
);
create table item_photo (
    id bigint not null,
    photo binary(255),
    primary key (id)
);

alter table basket_items add constraint FK4q9gnl10yamqxew6rs2wdk0qq foreign key (items_id) references item;
alter table basket_items add constraint FKh8ugq8wukffh0bpp01ct2p49t foreign key (basket_basket_id) references basket;
alter table flower_order add constraint FK9ls57yct2e4ue9aip6uwlwc6b foreign key (basket_basket_id) references basket;