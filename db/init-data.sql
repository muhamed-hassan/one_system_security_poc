-- Configurations

INSERT INTO `screen_type` (`type`) VALUES ('WEB'), ('MOBILE');

INSERT INTO `system_actor` (`type`) VALUES ('user_a'), ('user_b');

INSERT INTO `system_security_configuration` (`jwt_secret`, `jwt_expiration`, `authentication_path`, `automated_system_name`) VALUES ('QiiKLh_g0-CmQlx-foyJ0HA_Qnqk5hB5deXTiFCnLzs3NLSwjGzazVOiWEZvB3sCxyQiR2IQJcl5PAqfxzzF-69E18tozdtDXVKChEqT4gTIv66WGKGrfR_anOayiKoZ139CZ_RL0mv9bYYE18J0EdttknsNTR9s7ONc4DNs8Nc', 86400000, '/authenticate', 'dummy_name_of_automated_system');

INSERT INTO `ui_screen` (`screen_name`, `screen_type_id`) VALUES ('screen_name_x1', 1), ('screen_name_x2', 1), ('screen_name_x3', 1), ('screen_name_x4', 2), ('screen_name_x5', 2), ('screen_name_x6', 2);

INSERT INTO `granted_authority` (`system_actor_id`, `ui_screen_id`) VALUES (1, 1), (1, 2), (1, 3), (2, 4), (2, 5), (2, 6);