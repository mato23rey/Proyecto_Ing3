-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 04-10-2015 a las 18:14:10
-- Versión del servidor: 5.6.17
-- Versión de PHP: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `is3`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pharmacy`
--

CREATE TABLE IF NOT EXISTS `pharmacy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Volcado de datos para la tabla `pharmacy`
--

INSERT INTO `pharmacy` (`id`, `name`) VALUES
(1, 'Test1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `phsucursal`
--

CREATE TABLE IF NOT EXISTS `phsucursal` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pharmacy_id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `address` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Volcado de datos para la tabla `phsucursal`
--

INSERT INTO `phsucursal` (`id`, `pharmacy_id`, `name`, `address`) VALUES
(1, 1, 'Sucursal 1', '8 de Octubre 2738'),
(2, 1, 'Sucursal 2', 'Echeandia 2788'),
(3, 1, 'Sucursal 3', 'Javier Barrior Amorin 1139');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `product`
--

CREATE TABLE IF NOT EXISTS `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pharmacy_id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `price` float NOT NULL,
  `desc` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Volcado de datos para la tabla `product`
--

INSERT INTO `product` (`id`, `pharmacy_id`, `name`, `price`, `desc`) VALUES
(1, 1, 'Product 1', 10, 'Hola Mundo?'),
(2, 1, 'Producto 2', 50, 'Perifar 600 flexz ultra');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `product_sucursal`
--

CREATE TABLE IF NOT EXISTS `product_sucursal` (
  `product_id` int(11) NOT NULL,
  `sucursal_id` int(11) NOT NULL,
  PRIMARY KEY (`product_id`,`sucursal_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `product_sucursal`
--

INSERT INTO `product_sucursal` (`product_id`, `sucursal_id`) VALUES
(1, 1),
(2, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sale`
--

CREATE TABLE IF NOT EXISTS `sale` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL,
  `user_id` int(11) NOT NULL,
  `address` varchar(50) NOT NULL,
  `comment` text,
  `score` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=27 ;

--
-- Volcado de datos para la tabla `sale`
--

INSERT INTO `sale` (`id`, `date`, `user_id`, `address`, `comment`, `score`) VALUES
(1, '2015-10-03 00:00:00', 25, 'Echeandia 2788', '', 0),
(2, '2015-10-03 00:00:00', 25, 'Ejido 1365', NULL, 0),
(3, '2015-10-03 00:00:00', 25, 'Echeandia 2788', NULL, 0),
(4, '2015-10-03 00:00:00', 25, 'Echeandia 2788', NULL, 0),
(5, '2015-10-03 00:00:00', 25, 'Echeandia 2788', NULL, 0),
(6, '2015-10-03 00:00:00', 25, 'Echeandia 2788', NULL, 0),
(7, '2015-10-03 00:00:00', 25, 'Echeandia 2788', NULL, 0),
(8, '2015-10-03 00:00:00', 25, 'Echeandia 2788', NULL, 0),
(9, '2015-10-03 00:00:00', 25, 'Echeandia 2788', NULL, 0),
(10, '2015-10-03 00:00:00', 25, 'Echeandia 2788', NULL, 0),
(11, '2015-10-03 00:00:00', 25, 'Echeandia 2788', NULL, 0),
(12, '2015-10-03 00:00:00', 25, 'Echeandia 2788', NULL, 0),
(13, '2015-10-03 00:00:00', 25, 'Echeandia 2788', NULL, 0),
(14, '2015-10-03 00:00:00', 25, 'Echeandia 2788', NULL, 0),
(15, '2015-10-03 00:00:00', 25, 'Echeandia 2788', NULL, 0),
(16, '2015-10-03 00:00:00', 25, 'Echeandia 2788', NULL, 0),
(17, '2015-10-03 00:00:00', 25, 'Echeadia 2788', NULL, 0),
(18, '2015-10-03 00:00:00', 25, 'Echeandia 2788', NULL, 0),
(19, '2015-10-03 00:00:00', 25, 'Echeandia 2788', NULL, 0),
(20, '2015-10-03 00:00:00', 25, 'Echeandia 2788', NULL, 0),
(21, '2015-10-03 00:00:00', 25, 'Echeandia 2788', NULL, 0),
(22, '2015-10-03 00:00:00', 25, 'Echeandia 2788', NULL, 0),
(23, '2015-10-03 00:00:00', 25, 'Echeandia 2788', NULL, 0),
(24, '2015-10-03 00:00:00', 25, 'Echeandia 2788', NULL, 0),
(25, '2015-10-03 00:00:00', 25, 'Echeandia 2788', 'Hola mundo', 2),
(26, '2015-10-04 00:00:00', 25, 'Echeandia 2788', '', 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sale_product`
--

CREATE TABLE IF NOT EXISTS `sale_product` (
  `sale_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `cant` int(11) NOT NULL,
  PRIMARY KEY (`sale_id`,`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `sale_product`
--

INSERT INTO `sale_product` (`sale_id`, `product_id`, `cant`) VALUES
(10, 2, 20),
(11, 2, 10),
(12, 2, 12),
(13, 2, 10),
(14, 2, 10),
(15, 2, 10),
(16, 2, 10),
(17, 2, 30),
(18, 2, 30),
(19, 1, 31),
(19, 2, 115),
(20, 1, 1),
(20, 2, 1),
(21, 1, 1),
(21, 2, 1),
(22, 1, 1),
(23, 1, 1),
(24, 1, 1),
(24, 2, 1),
(25, 1, 1),
(25, 2, 1),
(26, 1, 1),
(26, 2, 12);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `EMAIL` varchar(255) NOT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `VALIDATIONCODE` varchar(255) DEFAULT NULL,
  `ACTIVATED` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=27 ;

--
-- Volcado de datos para la tabla `user`
--

INSERT INTO `user` (`ID`, `EMAIL`, `PASSWORD`, `VALIDATIONCODE`, `ACTIVATED`) VALUES
(1, 'qwe', 'a51c008b213c7a0462e49895df164b50', '123456789', 1),
(25, 'Seba_tl@hotmail.com', 'a5c7cbc5c4e60094e7ff66762aff0bc5', 'qireerocvcnivgn635n0nemb6i783fsjpa9oeo15qkff', 1),
(26, 'Sebastiantorreslemos@gmail.com', '131c96754fb6a66f20f5e36b3332cd8e', 'viu0ijtcdlt6dj383kfvap9xczer7sunrhsjgkbbi4xi', 0);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
