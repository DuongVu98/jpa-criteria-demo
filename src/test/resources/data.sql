INSERT into shop (id, name)
VALUES (1, 'Logitech'),
       (2, 'MSI'),
       (3, 'Asus'),
       (4, 'Lenovo'),
       (5, 'Razer'),
       (6, 'Keychron');

INSERT into category (id, name)
VALUES (1, 'Chair'),
       (2, 'Keyboard'),
       (3, 'PC'),
       (4, 'Laptop'),
       (5, 'Monitor'),
       (6, 'Phone'),
       (7, 'Headphone'),
       (8, 'Mouse');

INSERT into product (id, name, price, category_id, shop_id)
VALUES (1, 'MX MASTER 3S', 3299000, 8, 1),
       (2, 'MX ANYWHERE 3', 2159000, 8, 1),
       (3, 'MX MECHANICAL', 3599000, 2, 1),
       (4, 'MX KEYS MINI', 2994000, 2, 1),
       (5, 'G335 Black', 1290000, 7, 1),
       (6, 'G733 Lightspeed', 2990000, 7, 1),
       (7, 'GS66 Stealth', 40000000, 4, 2),
       (8, 'GE76 Raider', 100000000, 4, 2),
       (9, 'MAG CH130', 6190000, 1, 2),
       (10, 'Zenbook S', 26000000, 4, 3),
       (11, 'TUF Dash', 23000000, 4, 3),
       (12, 'Zephyrus G15', 70000000, 4, 3),
       (13, 'ROG Phone 6 Pro', 26500000, 6, 3),
       (14, 'ProArt PA278CV', 10400000, 5, 3),
       (15, 'ThinkPad X1', 48000000, 4, 4),
       (16, 'Yoga Slim', 22000000, 4, 4),
       (17, 'ThinkStation', 22000000, 3, 4),
       (18, 'Razer Blade', 60000000, 4, 5),
       (19, 'Razer Book', 31000000, 4, 5),
       (20, 'Kraken V3', 2450000, 7, 5),
       (21, 'Huntsman V2', 4900000, 2, 5),
       (22, 'K8 Pro', 2590000, 2, 6),
       (23, 'K3 V2', 1980000, 2, 6)

