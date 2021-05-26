delete from item;
delete from basket;
delete from flower_order;
delete from basket_items;

insert into item_photo (id, photo, version)
values (1, file_read('..\flower-power\src\main\resources\flower_photos\hiacintas.jpg'), 1),
       (2, file_read('..\flower-power\src\main\resources\flower_photos\roze.jpg'), 1),
       (3, file_read('..\flower-power\src\main\resources\flower_photos\bijunas.jpg'), 1),
       (4, file_read('..\flower-power\src\main\resources\flower_photos\tulpe.jpg'), 1),
       (5, file_read('..\flower-power\src\main\resources\flower_photos\gerbera.jpg'), 1),
       (6, file_read('..\flower-power\src\main\resources\flower_photos\hiacintu_p.jpg'), 1),
       (7, file_read('..\flower-power\src\main\resources\flower_photos\roziu_p.jpg'), 1),
       (8, file_read('..\flower-power\src\main\resources\flower_photos\bijunu_p.jpg'), 1),
       (9, file_read('..\flower-power\src\main\resources\flower_photos\tulpiu_p.jpg'), 1),
       (10, file_read('..\flower-power\src\main\resources\flower_photos\gerberu_p.jpg'), 1);

insert into item (id, amount, item_type, name, price, description, version, photo_id)
values (1, 1, 0, 'Hiacintas',          3.15,   'Tai yra hiacintas!', 1, 1),
       (2, 0, 0, 'Rožė',               2.5,    'Tai yra rožė!', 1, 2),
       (3, 0, 0, 'Bijūnas',        3.55,   'Tai yra bijūnas!', 1, 3),
       (4, 25, 0, 'Tulpė',              3.1,    'Tai yra tulpė!', 1, 4),
       (5, 25, 0, 'Gerbera',          4.5,    'Tai yra gerbera!', 1, 5),
       (6, 25, 1, 'Hiacintų puokštė',   30.0,   'Tai yra hiacintų puokštė!', 1, 6),
       (7, 0, 1, 'Rožių puokštė',      35.25,  'Tai yra rožių puokštė!', 1, 7),
       (8, 25, 1, 'Bijūnų puokštė',40.0,   'Tai yra bijūnų puokštė!', 1, 8),
       (9, 255, 1, 'Tulpių puokštė',     50.5,   'Tai tulpių puokštė!', 1, 9),
       (10, 0, 1, 'Gerberų puokštė', 25.0,   'Tai yra gerberų puokštė!', 1, 10);

insert into basket (basket_id, version) values (1, 1);

insert into basket_items (basket_basket_id, items_id)
values (1, 1),
       (1, 2),
       (1, 3);

insert into flower_order (order_id, shipping_address, status, basket_basket_id, version)
values (1, 'Z. Sierakausko g. 5-45, Vilnius', 0, 1, 1);