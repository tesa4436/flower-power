delete from item;
delete from basket;
delete from flower_order;
delete from basket_items;

insert into item (id, amount, item_type, description)
values (1, 25, 0, 'Hiacintas'),
       (2, 25, 0, 'Rožė'),
       (3, 25, 0, 'Pelargonija'),
       (4, 25, 0, 'Tulpė'),
       (5, 25, 0, 'Orchidėja'),
       (6, 25, 1, ''),
       (7, 25, 1, ''),
       (8, 25, 1, ''),
       (9, 25, 1, ''),
       (10, 25, 1, '');

insert into basket (basket_id) values (1);

insert into basket_items (basket_basket_id, items_id)
values (1, 1),
       (1, 2),
       (1, 3);

insert into flower_order (order_id, shipping_address, status, basket_basket_id)
values (1, 'Z. Sierakausko g. 5-45, Vilnius', 0, 1);