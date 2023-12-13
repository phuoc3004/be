----- ALTER SCHEMA 04 -----
-- Customize Cart --

ALTER TABLE `clothes_store`.`cart_items`
DROP FOREIGN KEY `fk_cart_items_carts1`;
ALTER TABLE carts MODIFY COLUMN id INT AUTO_INCREMENT;
ALTER TABLE `clothes_store`.`cart_items`
ADD CONSTRAINT `fk_cart_items_carts1`
FOREIGN KEY (`carts_id`)
REFERENCES `clothes_store`.`carts` (`id`);

ALTER TABLE `clothes_store`.`carts`
ADD UNIQUE INDEX `users_id_UNIQUE` (`users_id` ASC) VISIBLE;

ALTER TABLE `clothes_store`.`cart_items`
CHANGE COLUMN `id` `id` INT NOT NULL AUTO_INCREMENT ;

ALTER TABLE `clothes_store`.`cart_items`
DROP COLUMN `update_date`,
DROP COLUMN `create_date`;

ALTER TABLE `clothes_store`.`cart_items`
DROP FOREIGN KEY `fk_cart_items_products1`;
ALTER TABLE `clothes_store`.`cart_items`
DROP COLUMN `products_id`,
ADD COLUMN `product_variants_id` INT NOT NULL AFTER `carts_id`,
DROP INDEX `fk_cart_items_products1_idx` ;