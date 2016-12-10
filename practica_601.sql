-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 29-04-2016 a las 16:26:33
-- Versión del servidor: 10.1.9-MariaDB
-- Versión de PHP: 5.6.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `practica_601`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `answered_questions`
--

CREATE TABLE `answered_questions` (
  `answerId` int(11) NOT NULL,
  `relevance` int(11) NOT NULL,
  `option_id` int(11) DEFAULT NULL,
  `user_eMail` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `friend_request_messages`
--

CREATE TABLE `friend_request_messages` (
  `messageId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `messages`
--

CREATE TABLE `messages` (
  `messageId` int(11) NOT NULL,
  `isRead` bit(1) NOT NULL,
  `msgTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `source_eMail` varchar(255) DEFAULT NULL,
  `target_eMail` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `options`
--

CREATE TABLE `options` (
  `id` int(11) NOT NULL,
  `numeroOrden` int(11) NOT NULL,
  `texto` varchar(255) NOT NULL,
  `preguntaMadre_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `questions`
--

CREATE TABLE `questions` (
  `id` int(11) NOT NULL,
  `enunciado` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `question_invite_messages`
--

CREATE TABLE `question_invite_messages` (
  `messageId` int(11) NOT NULL,
  `questionInvite_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `text_messages`
--

CREATE TABLE `text_messages` (
  `msgText` varchar(255) NOT NULL,
  `messageId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
--

CREATE TABLE `users` (
  `eMail` varchar(255) NOT NULL,
  `avatar` longblob,
  `birthDate` date DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `gender` varchar(255) NOT NULL,
  `interestedIn` varchar(255) NOT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users_users`
--

CREATE TABLE `users_users` (
  `User_eMail` varchar(255) NOT NULL,
  `friends_eMail` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user_hobbies`
--

CREATE TABLE `user_hobbies` (
  `User_eMail` varchar(255) NOT NULL,
  `hobbies` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `answered_questions`
--
ALTER TABLE `answered_questions`
  ADD PRIMARY KEY (`answerId`),
  ADD KEY `FK2sp71adwi88e29b3247gd9j41` (`option_id`),
  ADD KEY `FKbp5k90bihg5rn99rsy5l46lt5` (`user_eMail`);

--
-- Indices de la tabla `friend_request_messages`
--
ALTER TABLE `friend_request_messages`
  ADD PRIMARY KEY (`messageId`);

--
-- Indices de la tabla `messages`
--
ALTER TABLE `messages`
  ADD PRIMARY KEY (`messageId`),
  ADD KEY `FK36ybqg8i3l53ivefjmgxe80do` (`source_eMail`),
  ADD KEY `FKqrgwi2iy4g53hsglc8t0e8udt` (`target_eMail`);

--
-- Indices de la tabla `options`
--
ALTER TABLE `options`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKi57k6hov24vwhv9tsfpkx9ovh` (`preguntaMadre_id`);

--
-- Indices de la tabla `questions`
--
ALTER TABLE `questions`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `question_invite_messages`
--
ALTER TABLE `question_invite_messages`
  ADD PRIMARY KEY (`messageId`),
  ADD KEY `FKdy5cak2ui1evhma1bmxwpv8g6` (`questionInvite_id`);

--
-- Indices de la tabla `text_messages`
--
ALTER TABLE `text_messages`
  ADD PRIMARY KEY (`messageId`);

--
-- Indices de la tabla `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`eMail`);

--
-- Indices de la tabla `users_users`
--
ALTER TABLE `users_users`
  ADD PRIMARY KEY (`User_eMail`,`friends_eMail`),
  ADD KEY `FK614er3srbn7f74fs9ud133bpe` (`friends_eMail`);

--
-- Indices de la tabla `user_hobbies`
--
ALTER TABLE `user_hobbies`
  ADD KEY `FKl942kqnhlht50r5c9rep2d4xa` (`User_eMail`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `answered_questions`
--
ALTER TABLE `answered_questions`
  MODIFY `answerId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `messages`
--
ALTER TABLE `messages`
  MODIFY `messageId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `options`
--
ALTER TABLE `options`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `questions`
--
ALTER TABLE `questions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `answered_questions`
--
ALTER TABLE `answered_questions`
  ADD CONSTRAINT `FK2sp71adwi88e29b3247gd9j41` FOREIGN KEY (`option_id`) REFERENCES `options` (`id`),
  ADD CONSTRAINT `FKbp5k90bihg5rn99rsy5l46lt5` FOREIGN KEY (`user_eMail`) REFERENCES `users` (`eMail`);

--
-- Filtros para la tabla `friend_request_messages`
--
ALTER TABLE `friend_request_messages`
  ADD CONSTRAINT `FKdfda38mwddx2k3ajlddxe8i5d` FOREIGN KEY (`messageId`) REFERENCES `messages` (`messageId`);

--
-- Filtros para la tabla `messages`
--
ALTER TABLE `messages`
  ADD CONSTRAINT `FK36ybqg8i3l53ivefjmgxe80do` FOREIGN KEY (`source_eMail`) REFERENCES `users` (`eMail`),
  ADD CONSTRAINT `FKqrgwi2iy4g53hsglc8t0e8udt` FOREIGN KEY (`target_eMail`) REFERENCES `users` (`eMail`);

--
-- Filtros para la tabla `options`
--
ALTER TABLE `options`
  ADD CONSTRAINT `FKi57k6hov24vwhv9tsfpkx9ovh` FOREIGN KEY (`preguntaMadre_id`) REFERENCES `questions` (`id`);

--
-- Filtros para la tabla `question_invite_messages`
--
ALTER TABLE `question_invite_messages`
  ADD CONSTRAINT `FK1p06vxrtkurod279m9ej21akg` FOREIGN KEY (`messageId`) REFERENCES `messages` (`messageId`),
  ADD CONSTRAINT `FKdy5cak2ui1evhma1bmxwpv8g6` FOREIGN KEY (`questionInvite_id`) REFERENCES `questions` (`id`);

--
-- Filtros para la tabla `text_messages`
--
ALTER TABLE `text_messages`
  ADD CONSTRAINT `FKiv482dc0blf34qwh986w0tpk2` FOREIGN KEY (`messageId`) REFERENCES `messages` (`messageId`);

--
-- Filtros para la tabla `users_users`
--
ALTER TABLE `users_users`
  ADD CONSTRAINT `FK614er3srbn7f74fs9ud133bpe` FOREIGN KEY (`friends_eMail`) REFERENCES `users` (`eMail`),
  ADD CONSTRAINT `FKsqch23akghlywlnhtwqx22orw` FOREIGN KEY (`User_eMail`) REFERENCES `users` (`eMail`);

--
-- Filtros para la tabla `user_hobbies`
--
ALTER TABLE `user_hobbies`
  ADD CONSTRAINT `FKl942kqnhlht50r5c9rep2d4xa` FOREIGN KEY (`User_eMail`) REFERENCES `users` (`eMail`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
