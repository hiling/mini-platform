/*
DROP TABLE IF EXISTS `oauth_client`;
DROP TABLE IF EXISTS `oauth_access_token`;
DROP TABLE IF EXISTS `oauth_refresh_token`;
*/

CREATE TABLE `oauth_client` (
	`client_id` CHAR(16) NOT NULL COLLATE 'utf8_bin',
	`client_name` VARCHAR(16) NOT NULL COLLATE 'utf8_bin',
	`client_secret` CHAR(32) NOT NULL COLLATE 'utf8_bin',
	`grant_types` VARCHAR(64) NOT NULL COLLATE 'utf8_bin',
	`ip_whitelist` VARCHAR(128) NOT NULL DEFAULT '' COMMENT '该客户端能够获取授权的IP白名单' COLLATE 'utf8_bin',
	`scope` VARCHAR(255) NOT NULL COMMENT '该客户端的授权范围，用逗号分隔' COLLATE 'utf8_bin',
	`status` TINYINT(4) NOT NULL DEFAULT '1' COMMENT '1:启用、0:禁用',
	`create_user` VARCHAR(16) NOT NULL COLLATE 'utf8_bin',
	`create_time` DATETIME NOT NULL DEFAULT NOW(),
	`update_user` VARCHAR(16) NOT NULL COLLATE 'utf8_bin',
	`update_time` DATETIME NOT NULL DEFAULT NOW(),
	`remark` VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8_bin',
	PRIMARY KEY (`client_id`)
)
COLLATE='utf8_bin'
ENGINE=InnoDB;

CREATE TABLE `oauth_access_token` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`client_id` VARCHAR(16) NOT NULL COLLATE 'utf8_bin',
	`user_id` BIGINT(20) NOT NULL,
	`access_token` CHAR(32) NOT NULL COLLATE 'utf8_bin',
	`jwt_token` VARCHAR(500) NOT NULL DEFAULT '' COLLATE 'utf8_bin',
	`refresh_token` VARCHAR(32) NOT NULL DEFAULT '' COLLATE 'utf8_bin',
	`expires_in` INT(11) NOT NULL DEFAULT '0',
	`create_time` DATETIME NOT NULL DEFAULT NOW(),
	PRIMARY KEY (`id`),
	INDEX `ix_client_id` (`client_id`),
	INDEX `ix_access_token` (`access_token`)
)
COLLATE='utf8_bin'
ENGINE=InnoDB;

CREATE TABLE `oauth_refresh_token` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`client_id` VARCHAR(16) NOT NULL COLLATE 'utf8_bin',
	`user_id` BIGINT(20) NOT NULL,
	`refresh_token` CHAR(32) NOT NULL COLLATE 'utf8_bin',
	`expires_in` INT(11) NOT NULL DEFAULT '0',
	`create_time` DATETIME NOT NULL DEFAULT NOW(),
	`last_used_time` DATETIME NOT NULL DEFAULT NOW(),
	PRIMARY KEY (`id`),
	INDEX `ix_refresh_token` (`refresh_token`)
)
COLLATE='utf8_bin'
ENGINE=InnoDB;
