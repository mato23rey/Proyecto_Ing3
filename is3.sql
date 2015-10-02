-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 02-10-2015 a las 23:42:44
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
  `date` date NOT NULL,
  `user_id` int(11) NOT NULL,
  `address` varchar(50) NOT NULL,
  `comment` text NOT NULL,
  `score` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sale_product`
--

CREATE TABLE IF NOT EXISTS `sale_product` (
  `sale_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  PRIMARY KEY (`sale_id`,`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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
