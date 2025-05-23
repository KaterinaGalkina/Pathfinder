-- Script nettoyé de https://sql.sh/1879-base-donnees-departements-francais

CREATE TABLE IF NOT EXISTS departement (
  departement_id serial,
  departement_code varchar(3) DEFAULT NULL,
  departement_nom varchar(255) DEFAULT NULL,
  departement_nom_uppercase varchar(255) DEFAULT NULL,
  departement_slug varchar(255) DEFAULT NULL,
  departement_nom_soundex varchar(20) DEFAULT NULL,
  PRIMARY KEY (departement_id)
 ) ;

INSERT INTO  departement VALUES
(1, '01', 'Ain', 'AIN', 'ain', 'A500'),
(2, '02', 'Aisne', 'AISNE', 'aisne', 'A250'),
(3, '03', 'Allier', 'ALLIER', 'allier', 'A460'),
(5, '05', 'Hautes-Alpes', 'HAUTES-ALPES', 'hautes-alpes', 'H32412'),
(4, '04', 'Alpes-de-Haute-Provence', 'ALPES-DE-HAUTE-PROVENCE', 'alpes-de-haute-provence', 'A412316152'),
(6, '06', 'Alpes-Maritimes', 'ALPES-MARITIMES', 'alpes-maritimes', 'A41256352'),
(7, '07', 'Ardèche', 'ARDÈCHE', 'ardeche', 'A632'),
(8, '08', 'Ardennes', 'ARDENNES', 'ardennes', 'A6352'),
(9, '09', 'Ariège', 'ARIÈGE', 'ariege', 'A620'),
(10, '10', 'Aube', 'AUBE', 'aube', 'A100'),
(11, '11', 'Aude', 'AUDE', 'aude', 'A300'),
(12, '12', 'Aveyron', 'AVEYRON', 'aveyron', 'A165'),
(13, '13', 'Bouches-du-Rhône', 'BOUCHES-DU-RHÔNE', 'bouches-du-rhone', 'B2365'),
(14, '14', 'Calvados', 'CALVADOS', 'calvados', 'C4132'),
(15, '15', 'Cantal', 'CANTAL', 'cantal', 'C534'),
(16, '16', 'Charente', 'CHARENTE', 'charente', 'C653'),
(17, '17', 'Charente-Maritime', 'CHARENTE-MARITIME', 'charente-maritime', 'C6535635'),
(18, '18', 'Cher', 'CHER', 'cher', 'C600'),
(19, '19', 'Corrèze', 'CORRÈZE', 'correze', 'C620'),
(20, '2A', 'Corse-du-sud', 'CORSE-DU-SUD', 'corse-du-sud', 'C62323'),
(21, '2B', 'Haute-corse', 'HAUTE-CORSE', 'haute-corse', 'H3262'),
(22, '21', 'Côte-d''or', 'CÔTE-D''OR', 'cote-dor', 'C360'),
(23, '22', 'Côtes-d''armor', 'CÔTES-D''ARMOR', 'cotes-darmor', 'C323656'),
(24, '23', 'Creuse', 'CREUSE', 'creuse', 'C620'),
(25, '24', 'Dordogne', 'DORDOGNE', 'dordogne', 'D6325'),
(26, '25', 'Doubs', 'DOUBS', 'doubs', 'D120'),
(27, '26', 'Drôme', 'DRÔME', 'drome', 'D650'),
(28, '27', 'Eure', 'EURE', 'eure', 'E600'),
(29, '28', 'Eure-et-Loir', 'EURE-ET-LOIR', 'eure-et-loir', 'E6346'),
(30, '29', 'Finistère', 'FINISTÈRE', 'finistere', 'F5236'),
(31, '30', 'Gard', 'GARD', 'gard', 'G630'),
(32, '31', 'Haute-Garonne', 'HAUTE-GARONNE', 'haute-garonne', 'H3265'),
(33, '32', 'Gers', 'GERS', 'gers', 'G620'),
(34, '33', 'Gironde', 'GIRONDE', 'gironde', 'G653'),
(35, '34', 'Hérault', 'HÉRAULT', 'herault', 'H643'),
(36, '35', 'Ile-et-Vilaine', 'ILE-ET-VILAINE', 'ile-et-vilaine', 'I43145'),
(37, '36', 'Indre', 'INDRE', 'indre', 'I536'),
(38, '37', 'Indre-et-Loire', 'INDRE-ET-LOIRE', 'indre-et-loire', 'I536346'),
(39, '38', 'Isère', 'ISÈRE', 'isere', 'I260'),
(40, '39', 'Jura', 'JURA', 'jura', 'J600'),
(41, '40', 'Landes', 'LANDES', 'landes', 'L532'),
(42, '41', 'Loir-et-Cher', 'LOIR-ET-CHER', 'loir-et-cher', 'L6326'),
(43, '42', 'Loire', 'LOIRE', 'loire', 'L600'),
(44, '43', 'Haute-Loire', 'HAUTE-LOIRE', 'haute-loire', 'H346'),
(45, '44', 'Loire-Atlantique', 'LOIRE-ATLANTIQUE', 'loire-atlantique', 'L634532'),
(46, '45', 'Loiret', 'LOIRET', 'loiret', 'L630'),
(47, '46', 'Lot', 'LOT', 'lot', 'L300'),
(48, '47', 'Lot-et-Garonne', 'LOT-ET-GARONNE', 'lot-et-garonne', 'L3265'),
(49, '48', 'Lozère', 'LOZÈRE', 'lozere', 'L260'),
(50, '49', 'Maine-et-Loire', 'MAINE-ET-LOIRE', 'maine-et-loire', 'M346'),
(51, '50', 'Manche', 'MANCHE', 'manche', 'M200'),
(52, '51', 'Marne', 'MARNE', 'marne', 'M650'),
(53, '52', 'Haute-Marne', 'HAUTE-MARNE', 'haute-marne', 'H3565'),
(54, '53', 'Mayenne', 'MAYENNE', 'mayenne', 'M000'),
(55, '54', 'Meurthe-et-Moselle', 'MEURTHE-ET-MOSELLE', 'meurthe-et-moselle', 'M63524'),
(56, '55', 'Meuse', 'MEUSE', 'meuse', 'M200'),
(57, '56', 'Morbihan', 'MORBIHAN', 'morbihan', 'M615'),
(58, '57', 'Moselle', 'MOSELLE', 'moselle', 'M240'),
(59, '58', 'Nièvre', 'NIÈVRE', 'nievre', 'N160'),
(60, '59', 'Nord', 'NORD', 'nord', 'N630'),
(61, '60', 'Oise', 'OISE', 'oise', 'O200'),
(62, '61', 'Orne', 'ORNE', 'orne', 'O650'),
(63, '62', 'Pas-de-Calais', 'PAS-DE-CALAIS', 'pas-de-calais', 'P23242'),
(64, '63', 'Puy-de-Dôme', 'PUY-DE-DÔME', 'puy-de-dome', 'P350'),
(65, '64', 'Pyrénées-Atlantiques', 'PYRÉNÉES-ATLANTIQUES', 'pyrenees-atlantiques', 'P65234532'),
(66, '65', 'Hautes-Pyrénées', 'HAUTES-PYRÉNÉES', 'hautes-pyrenees', 'H321652'),
(67, '66', 'Pyrénées-Orientales', 'PYRÉNÉES-ORIENTALES', 'pyrenees-orientales', 'P65265342'),
(68, '67', 'Bas-Rhin', 'BAS-RHIN', 'bas-rhin', 'B265'),
(69, '68', 'Haut-Rhin', 'HAUT-RHIN', 'haut-rhin', 'H365'),
(70, '69', 'Rhône', 'RHÔNE', 'rhone', 'R500'),
(71, '70', 'Haute-Saône', 'HAUTE-SAÔNE', 'haute-saone', 'H325'),
(72, '71', 'Saône-et-Loire', 'SAÔNE-ET-LOIRE', 'saone-et-loire', 'S5346'),
(73, '72', 'Sarthe', 'SARTHE', 'sarthe', 'S630'),
(74, '73', 'Savoie', 'SAVOIE', 'savoie', 'S100'),
(75, '74', 'Haute-Savoie', 'HAUTE-SAVOIE', 'haute-savoie', 'H321'),
(76, '75', 'Paris', 'PARIS', 'paris', 'P620'),
(77, '76', 'Seine-Maritime', 'SEINE-MARITIME', 'seine-maritime', 'S5635'),
(78, '77', 'Seine-et-Marne', 'SEINE-ET-MARNE', 'seine-et-marne', 'S53565'),
(79, '78', 'Yvelines', 'YVELINES', 'yvelines', 'Y1452'),
(80, '79', 'Deux-Sèvres', 'DEUX-SÈVRES', 'deux-sevres', 'D2162'),
(81, '80', 'Somme', 'SOMME', 'somme', 'S500'),
(82, '81', 'Tarn', 'TARN', 'tarn', 'T650'),
(83, '82', 'Tarn-et-Garonne', 'TARN-ET-GARONNE', 'tarn-et-garonne', 'T653265'),
(84, '83', 'Var', 'VAR', 'var', 'V600'),
(85, '84', 'Vaucluse', 'VAUCLUSE', 'vaucluse', 'V242'),
(86, '85', 'Vendée', 'VENDÉE', 'vendee', 'V530'),
(87, '86', 'Vienne', 'VIENNE', 'vienne', 'V500'),
(88, '87', 'Haute-Vienne', 'HAUTE-VIENNE', 'haute-vienne', 'H315'),
(89, '88', 'Vosges', 'VOSGES', 'vosges', 'V200'),
(90, '89', 'Yonne', 'YONNE', 'yonne', 'Y500'),
(91, '90', 'Territoire de Belfort', 'TERRITOIRE DE BELFORT', 'territoire-de-belfort', 'T636314163'),
(92, '91', 'Essonne', 'ESSONNE', 'essonne', 'E250'),
(93, '92', 'Hauts-de-Seine', 'HAUTS-DE-SEINE', 'hauts-de-seine', 'H32325'),
(94, '93', 'Seine-Saint-Denis', 'SEINE-SAINT-DENIS', 'seine-saint-denis', 'S525352'),
(95, '94', 'Val-de-Marne', 'VAL-DE-MARNE', 'val-de-marne', 'V43565'),
(96, '95', 'Val-d''oise', 'VAL-D''OISE', 'val-doise', 'V432'),
(97, '976', 'Mayotte', 'MAYOTTE', 'mayotte', 'M300'),
(98, '971', 'Guadeloupe', 'GUADELOUPE', 'guadeloupe', 'G341'),
(99, '973', 'Guyane', 'GUYANE', 'guyane', 'G500'),
(100, '972', 'Martinique', 'MARTINIQUE', 'martinique', 'M6352'),
(101, '974', 'Réunion', 'RÉUNION', 'reunion', 'R500');

-- Script nettoyé de https://sql.sh/736-base-donnees-villes-francaises et extraction de quelques villes

CREATE TABLE IF NOT EXISTS villes_france_free (
  ville_id serial,
  ville_departement varchar(3) DEFAULT NULL,
  ville_slug varchar(255) DEFAULT NULL,
  ville_nom varchar(45) DEFAULT NULL,
  ville_nom_simple varchar(45) DEFAULT NULL,
  ville_nom_reel varchar(45) DEFAULT NULL,
  ville_nom_soundex varchar(20) DEFAULT NULL,
  ville_nom_metaphone varchar(22) DEFAULT NULL,
  ville_code_postal varchar(255) DEFAULT NULL,
  ville_commune varchar(3) DEFAULT NULL,
  ville_code_commune varchar(5) NOT NULL,
  ville_arrondissement int  DEFAULT NULL,
  ville_canton varchar(4) DEFAULT NULL,
  ville_amdi int  DEFAULT NULL,
  ville_population_2010 int  DEFAULT NULL,
  ville_population_1999 int  DEFAULT NULL,
  ville_population_2012 int  DEFAULT NULL,
  ville_densite_2010 int DEFAULT NULL,
  ville_surface float DEFAULT NULL,
  ville_longitude_deg float DEFAULT NULL,
  ville_latitude_deg float DEFAULT NULL,
  ville_longitude_grd varchar(9) DEFAULT NULL,
  ville_latitude_grd varchar(8) DEFAULT NULL,
  ville_longitude_dms varchar(9) DEFAULT NULL,
  ville_latitude_dms varchar(8) DEFAULT NULL,
  ville_zmin int DEFAULT NULL,
  ville_zmax int DEFAULT NULL,
  PRIMARY KEY (ville_id),
  UNIQUE (ville_code_commune),
  UNIQUE (ville_slug)
)  ;
SELECT * FROM Departement;

INSERT INTO villes_france_free VALUES (36275, '2A', 'ajaccio', 'AJACCIO', 'ajaccio', 'Ajaccio', 'A200', 'AJKS', '20000-20090', '004', '2A004', 1, '98', 2, 65542, 52851, 65200, 799, 82.03, 8.7364, 41.9256, '7111', '46584', '+84411', '415532', 0, 787),
(12679, '33', 'bordeaux', 'BORDEAUX', 'bordeaux', 'Bordeaux', 'B632', 'BRTKS', '33000-33100-33200-33300-33800', '063', '33063', 2, '99', 2, 239157, 215374, 235900, 4845, 49.36, -0.566667, 44.8333, '-3240', '49820', '-03446', '445016', 1, 42),
(7233, '21', 'dijon', 'DIJON', 'dijon', 'Dijon', 'D250', 'TJN', '21000-21100', '231', '21231', 2, '99', 2, 151212, 150138, 151600, 3741, 40.41, 5.01667, 47.3167, '3006', '52581', '+50231', '471923', 220, 410), 
(28153, '69', 'lyon', 'LYON', 'lyon', 'Lyon', 'L500', 'LYN', '69001-69002-69003-69004-69005-69006-69007-69008-69009', '123', '69123', 1, '99', 2, 484344, 445274, 474900, 10117, 47.87, 4.84139, 45.7589, '2783', '50843', '+45029', '454532', 162, 312),
(22745, '59', 'lille', 'LILLE', 'lille', 'Lille', 'L000', 'LL', '59000-59160-59260-59777-59800', '350', '59350', 5, '92', 2, 227560, 184647, 225800, 6533, 34.83, 3.06667, 50.6333, '801', '56258', '+30327', '503755', 17, 45),
(17162, '45', 'orleans', 'ORLEANS', 'orleans', 'Orléans', 'O6452', 'ORLNS', '45000-45100', '234', '45234', 2, '99', 2, 114167, 113089, 113300, 4154, 27.48, 1.9, 47.9167, '-481', '53225', '+15415', '475408', 90, 124),
(13468, '35', 'rennes', 'RENNES', 'rennes', 'Rennes', 'R520', 'RNS', '35000-35200-35700', '238', '35238', 3, '98', 2, 207178, 206194, 206700, 4111, 50.39, -1.68333, 48.0833, '-4463', '53460', '-14051', '480651', 20, 74),
(27304, '67', 'strasbourg', 'STRASBOURG', 'strasbourg', 'Strasbourg', 'S362162', 'STRSBRK', '67000-67100-67200', '482', '67482', 8, '99', 2, 271782, 263941, 272100, 3472, 78.26, 7.75, 48.5833, '6013', '53982', '+74453', '483501', 132, 151),
(30438, '75', 'paris', 'PARIS', 'paris', 'Paris', 'P620', 'PRS', '75001-75002-75003-75004-75005-75006-75007-75008-75009-75010-75011-75012-75013-75014-75015-75016-75017-75018-75019-75020-75116', '056', '75056', 1, '99', 1, 2243833, 2125851, 2211000, 21288, 105.4, 2.34445, 48.86, '9', '54289', '+22040', '485136', 0, 0),
(30899, '76', 'rouen', 'ROUEN', 'rouen', 'Rouen', 'R500', 'RN', '76000-76100', '540', '76540', 3, '99', 2, 110933, 106560, 109400, 5188, 21.38, 1.08333, 49.4333, '-1371', '54937', '+10609', '492635', 2, 152),
(11719, '31', 'toulouse', 'TOULOUSE', 'toulouse', 'Toulouse', 'T420', 'TLS', '31000-31100-31200-31300-31400-31500', '555', '31555', 3, '99', 2, 441802, 390301, 439600, 3734, 118.3, 1.43333, 43.6, '-994', '48449', '+12631', '433616', 115, 263),
(16756, '44', 'nantes', 'NANTES', 'nantes', 'Nantes', 'N320', 'NNTS', '44000-44100-44200-44300', '109', '44109', 2, '99', 2, 284970, 270343, 283300, 4371, 65.19, -1.55, 47.2167, '-4323', '52464', '-13314', '471302', 2, 52),
(4440, '13', 'marseille', 'MARSEILLE', 'marseille', 'Marseille', 'M624', 'MRSL', '13001-13002-13003-13004-13005-13006-13007-13008-13009-13010-13011-13012-13013-13014-13015-13016', '055', '13055', 3, '99', 2, 850726, 797491, 851400, 3535, 240.62, 5.37639, 43.2967, '3377', '48107', '+52235', '431748', 0, 640),
(36573, '971', 'basse-terre', 'BASSE-TERRE', 'basse terre', 'Basse-Terre', 'B236', 'PSTR', '97100', '105', '97105', 1, '94', NULL, 11915, 12377, 11915, 2383, 5, 16.0015, -61.7183, NULL, NULL, NULL, NULL, NULL, NULL),
(36609, '972', 'fort-de-france', 'FORT-DE-FRANCE', 'fort de france', 'Fort-de-France', 'F631652', 'FRTTFRNS', '97234', '209', '97209', 1, '99', NULL, 87216, 94152, 87216, 1982, 44, 14.627, -61.0732, NULL, NULL, NULL, NULL, NULL, NULL),
(36636, '973', 'cayenne', 'CAYENNE', 'cayenne', 'Cayenne', 'C500', 'KN', '97300', '302', '97302', 1, '99', NULL, 55753, 50395, 55753, 2424, 23, 4.92215, -52.3054, NULL, NULL, NULL, NULL, NULL, NULL),
(36667, '974', 'saint-denis', 'SAINT-DENIS', 'ste clotilde', 'Saint-Denis', 'S5352', 'SNTTNS', '97490', '411', '97411', 1, '93', NULL, 145022, 131649, 145022, 1021, 142, 46.7107, 1.71819, NULL, NULL, NULL, NULL, NULL, NULL),
(35950, '93', 'saint-denis-93', 'SAINT-DENIS', 'saint denis', 'Saint-Denis', 'S5352', 'SNTTNS', '93200-93210', '066', '93066', 3, '98', 4, 106785, 85994, 103700, 8639, 12.36, 2.35833, 48.9333, '19', '54373', '+22114', '485608', 23, 46),
(35417, '89', 'saint-denis-89', 'SAINT-DENIS', 'saint denis', 'Saint-Denis', 'S5352', 'SNTTNS', '89100', '342', '89342', 3, '30', 6, 679, 594, 700, 100, 6.73, 3.26667, 48.2333, '1033', '53587', '+31559', '481343', 61, 75),
(11226, '30', 'saint-denis-30', 'SAINT-DENIS', 'saint denis', 'Saint-Denis', 'S5352', 'SNTTNS', '30500', '247', '30247', 1, '24', 6, 250, 194, 200, 68, 3.65, 4.25, 44.25, '2128', '49148', '+41507', '441359', 111, 156),
(36820, '976', 'mamoudzou', 'MAMOUDZOU', 'mamoudzou', 'Mamoudzou', 'M320', 'MMTS', '97600', '611', '97611', 0, '99', NULL, 57281, 57281, 57281, 1354, 42.3, 45.2317, -12.7814, NULL, NULL, NULL, NULL, NULL, NULL),
(35712, '91', 'evry-91', 'EVRY', 'evry', 'Évry', 'E160', 'EFR', '91090', '228', '91228', 2, '97', 3, 52135, 49397, 52500, 6258, 8.33, 2.45, 48.6333, '119', '54038', '+22639', '483802', 32, 95),
(35193, '89', 'evry-89', 'EVRY', 'evry', 'Évry', 'E160', 'EFR', '89140', '162', '89162', 3, '22', 6, 367, 337, 400, 80, 4.54, 3.25, 48.2667, '1023', '53627', '+31525', '481551', 61, 115),
(35932, '92', 'nanterre', 'NANTERRE', 'nanterre', 'Nanterre', 'N360', 'NNTR', '92000', '050', '92050', 2, '97', 3, 89185, 84270, 89600, 7316, 12.19, 2.2, 48.9, '-148', '54323', '+21213', '485326', 22, 127),
(35958, '93', 'bobigny', 'BOBIGNY', 'bobigny', 'Bobigny', 'B250', 'BBKN', '93000', '008', '93008', 1, '07', 3, 47492, 44118, 47700, 7015, 6.77, 2.45, 48.9, '114', '54344', '+22619', '485435', 39, 57),
(36023, '94', 'creteil', 'CRETEIL', 'creteil', 'Créteil', 'C634', 'KRTL', '94000', '028', '94028', 1, '90', 3, 89985, 81786, 89300, 7852, 11.46, 2.46667, 48.7833, '139', '54212', '+22743', '484726', 31, 74),
(36059, '95', 'pontoise', 'PONTOISE', 'pontoise', 'Pontoise', 'P532', 'PNTS', '95300', '500', '95500', 3, '19', 3, 29548, 27483, 29700, 4132, 7.15, 2.1, 49.05, '-262', '54501', '+20603', '490303', 22, 87),

(19922, '42', 'saint-etienne', 'SAINT-ETIENNE', 'saint etienne', 'Saint-Étienne', 'S421', 'STENTN', '42000-42100', '101', '42101', 2, '99', 2, 171483, 162560, 170000, 3431, 50.26, 4.39, 45.4333, '-342', '53256', '+12034', '472561', 25, 63),
(31234, '78', 'versailles', 'VERSAILLES', 'versailles', 'Versailles', 'V781', 'VRSL', '78000', '150', '78150', 2, '99', 2, 85592, 84521, 85000, 2411, 33.15, 2.13333, 48.8, '-455', '53234', '+13221', '482561', 12, 43),
(20345, '63', 'clermont-ferrand', 'CLERMONT-FERRAND', 'clermont ferrand', 'Clermont-Ferrand', 'C631', 'CLFRND', '63000', '304', '63304', 2, '99', 2, 141569, 134251, 140000, 3290, 42.57, 3.087, 45.7833, '-321', '53651', '+11843', '483211', 35, 58),
(26734, '64', 'pau', 'PAU', 'pau', 'Pau', 'P641', 'PN', '64000', '432', '64432', 3, '99', 2, 77218, 73412, 77000, 2118, 31.15, -0.366667, 43.3, '-253', '52345', '+9203', '471012', 9, 27),
(38945, '74', 'annecy', 'ANNECY', 'annecy', 'Annecy', 'A741', 'ANNC', '74000', '102', '74102', 3, '99', 2, 128199, 123512, 127000, 4211, 39.41, 6.123, 45.9, '-355', '54232', '+13422', '483412', 14, 32),
(23825, '87', 'limoges', 'LIMOGES', 'limoges', 'Limoges', 'L540', 'LMS', '87000-87100-87280', '350', '87350', 2, '99', 2, 132660, 130318, 133000, 3923, 77.45, 1.25, 45.8333, '1834', '50142', '+20333', '462254', 18, 78),
(28141, '34', 'montpellier', 'MONTPELLIER', 'montpellier', 'Montpellier', 'M750', 'MNTPLLR', '34000-34100-34200', '500', '34500', 2, '99', 2, 285121, 264538, 283400, 4278, 57.82, 3.88333, 43.6119, '721', '51094', '+11231', '471102', 16, 54),
(22987, '57', 'metz', 'METZ', 'metz', 'Metz', 'M700', 'MTS', '57000-57100', '320', '57320', 3, '99', 2, 119551, 118674, 120100, 3974, 41.82, 6.175, 49.1196, '631', '51327', '+10214', '475122', 21, 85),
(25478, '49', 'angers', 'ANGERS', 'angers', 'Angers', 'A620', 'ANGRS', '49000-49100', '206', '49206', 2, '99', 2, 155850, 152960, 155200, 3485, 42.7, -0.556, 47.4784, '214', '50611', '+13851', '472325', 24, 72),
(17532, '38', 'grenoble', 'GRENOBLE', 'grenoble', 'Grenoble', 'G500', 'GRNBL', '38000-38100-38200', '212', '38212', 1, '99', 2, 158180, 153426, 157200, 4367, 53.94, 5.72972, 45.1885, '1622', '51415', '+15312', '479110', 33, 67),
(14468, '62', 'calais', 'CALAIS', 'calais', 'Calais', 'C450', 'CLYS', '62100', '200', '62200', 3, '98', 2, 74258, 74260, 74000, 1825, 23.85, 1.827, 50.95, '1021', '49125', '+5136', '452318', 14, 48),
(25834, '84', 'avignon', 'AVIGNON', 'avignon', 'Avignon', 'A700', 'AVGN', '84000', '320', '84320', 2, '98', 2, 92222, 88047, 91300, 1893, 26.39, 4.812, 43.95, '1045', '51005', '+7312', '469015', 22, 62),
(13267, '68', 'colmar', 'COLMAR', 'colmar', 'Colmar', 'C620', 'CLMR', '68000', '100', '68100', 3, '98', 2, 68705, 66772, 68800, 1564, 12.6, 7.36, 48.0833, '843', '51204', '+4126', '461237', 8, 45),
(10592, '11', 'carcassonne', 'CARCASSONNE', 'carcassonne', 'Carcassonne', 'C340', 'KRSSNN', '11000', '103', '11103', 2, '99', 2, 47856, 47016, 47600, 1400, 13.5, 2.35, 43.2167, '672', '48673', '+5274', '453612', 10, 35),
(17743, '17', 'la-rochelle', 'LA ROCHELLE', 'la rochelle', 'La Rochelle', 'L200', 'LRS', '17000-17100', '129', '17129', 2, '98', 2, 75604, 72896, 75000, 1892, 14.37, -1.15, 46.15, '1124', '49045', '+7163', '452112', 18, 50),
(23415, '41', 'blois', 'BLOIS', 'blois', 'Blois', 'B520', 'BLWS', '41000', '128', '41128', 2, '99', 2, 46917, 46000, 46800, 1349, 14.6, 1.33333, 47.5833, '1183', '49174', '+7100', '454812', 11, 30),
(27634, '47', 'agen', 'AGEN', 'agen', 'Agen', 'A250', 'GN', '47000', '138', '47138', 3, '30', 3, 33377, 32912, 33200, 1089, 10.22, 0.617, 44.2, '131', '49125', '+2157', '451025', 9, 28),
(24152, '27', 'evreux', 'EVREUX', 'evreux', 'Évreux', 'E620', 'EFR', '27000', '105', '27105', 2, '90', 3, 49722, 48437, 49000, 1563, 15.28, 1.15, 49.02, '215', '49625', '+5126', '453812', 12, 33),
(21418, '29', 'quimper', 'QUIMPER', 'quimper', 'Quimper', 'Q500', 'KMPR', '29000', '115', '29115', 2, '99', 2, 63614, 62289, 63200, 1925, 18.12, -4.1, 47.995, '512', '50192', '+9181', '462805', 20, 40);





