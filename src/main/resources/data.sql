delete from item;
delete from basket;
delete from flower_order;
delete from basket_items;

insert into item_photo (id, photo)
values (1, file_read('..\flower-power\src\main\resources\flower_photos\hiacintas.jpg'));

insert into item (id, amount, item_type, name, price, description, photo_id)
values (1, 1, 0, 'Hiacintas',          3.15,   'Tai yra hiacintas!', 1),
       (2, 0, 0, 'Rožė',               2.5,    'Tai yra rožė!', 1),
       (3, 0, 0, 'Pelargonija',        3.55,   'Tai yra pelargonija!', 1),
       (4, 25, 0, 'Tulpė',              3.1,    'Tai yra tulpė!', 1),
       (5, 25, 0, 'Orchidėja',          4.5,    'Tai yra orchidėja!', 1),
       (6, 25, 1, 'Hiacintų puokštė',   30.0,   'Tai yra hiacintų puokštė!', 1),
       (7, 0, 1, 'Rožių puokštė',      35.25,  'Tai yra rožių puokštė!', 1),
       (8, 25, 1, 'Pelargonijų puokštė',40.0,   'Tai yra pelargonijų puokštė!', 1),
       (9, 255, 1, 'Tulpių puokštė',     50.5,   'Tai tulpių hiacintų puokštė!', 1),
       (10, 0, 1, 'Orchidėjų puokštė', 25.0,   'Tai yra orchidėjų puokštė!', 1);

insert into basket (basket_id) values (1);

insert into basket_items (basket_basket_id, items_id)
values (1, 1),
       (1, 2),
       (1, 3);

insert into flower_order (order_id, shipping_address, status, basket_basket_id)
values (1, 'Z. Sierakausko g. 5-45, Vilnius', 0, 1);