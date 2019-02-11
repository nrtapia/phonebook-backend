SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";

--
-- Database: `phonebook`
--

-- --------------------------------------------------------

--
-- Table structure for table `contact`
--

CREATE TABLE `contact` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `first_name` varchar(100) COLLATE utf8_spanish_ci NOT NULL,
  `last_name` varchar(100) COLLATE utf8_spanish_ci NOT NULL,
  `phone` varchar(10) COLLATE utf8_spanish_ci DEFAULT NULL,
  `update_at` datetime DEFAULT NULL,
  `version` bigint(20) DEFAULT NULL,
  `text_data` varchar(130) COLLATE utf8_spanish_ci DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Dumping data for table `contact`
--

INSERT INTO `contact` (`id`, `created_at`, `first_name`, `last_name`, `phone`, `update_at`, `version`, `text_data`) VALUES
(1, '2019-02-09 18:22:19', 'Bart', 'Simpson', '35234523', '2019-02-09 18:22:19', 0, 'BARTSIMPSON35234523'),
(2, '2019-02-09 18:22:12', 'Homer', 'Simpson', '35234523', '2019-02-09 18:22:12', 0, 'HOMERSIMPSON35234523'),
(3, '2019-02-10 03:28:46', 'Neider', 'Avila', '3008027218', '2019-02-10 03:28:46', 0, 'NEIDERAVILA3008027218');

-- --------------------------------------------------------

--
-- Table structure for table `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Dumping data for table `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(10);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `contact`
--
ALTER TABLE `contact`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `unique_value` (`first_name`,`last_name`),
  ADD KEY `search_index` (`text_data`);
COMMIT;
