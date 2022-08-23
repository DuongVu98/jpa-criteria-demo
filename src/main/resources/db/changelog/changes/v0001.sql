CREATE TABLE "shop"
(
    "id"   bigint PRIMARY KEY NOT NULL,
    "name" varchar            NOT NULL
);

CREATE TABLE "category"
(
    "id"   bigint PRIMARY KEY NOT NULL,
    "name" varchar            NOT NULL
);

CREATE TABLE "product"
(
    "id"          bigint PRIMARY KEY NOT NULL,
    "name"        varchar            NOT NULL,
    "price"       bigint,
    "shop_id"     bigint             NOT NULL,
    "category_id" bigint             NOT NULL
);

ALTER TABLE "product"
    ADD FOREIGN KEY ("shop_id") REFERENCES "shop" ("id");

ALTER TABLE "product"
    ADD FOREIGN KEY ("category_id") REFERENCES "category" ("id");
