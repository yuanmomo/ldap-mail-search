-- phpMyAdmin SQL Dump
-- version 3.3.7
-- http://www.phpmyadmin.net
--
-- 主机: localhost
-- 生成日期: 2012 年 08 月 24 日 09:09
-- 服务器版本: 5.5.17
-- PHP 版本: 5.2.14

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 数据库: `timesheet`
--
CREATE DATABASE `MAIL` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;
USE `mail`;

-- --------------------------------------------------------

--
-- 表的结构 `configuration`
--

CREATE TABLE IF NOT EXISTS `configuration` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `value` text COLLATE utf8_bin,
  `inuse` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=3 ;

--
-- 转存表中的数据 `configuration`
--

INSERT INTO `configuration` (`id`, `name`, `value`, `inuse`) VALUES
(1, 'lastUpdateTime', 0x323031322d30382d32342031363a32373a3130, 1),
(2, 'updateCycle', 0x35, 1),
(3, 'insertCount', 0x30, 1),
(4, 'updateCount', 0x30, 1),
(5, 'deleteCount', 0x30, 1);


-- --------------------------------------------------------

--
-- 表的结构 `person`
--

CREATE TABLE IF NOT EXISTS `person` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `mail` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `dn` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `cn` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `sn` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `ou` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `description` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `invalid` tinyint(4) NOT NULL,
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `loginName` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `loginPassword` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `cellPhone` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `phoneExt` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
