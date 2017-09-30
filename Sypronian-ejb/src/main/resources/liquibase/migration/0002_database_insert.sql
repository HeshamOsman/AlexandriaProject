--liquibase formatted sql
--changeset hesham:0002

INSERT INTO `tbl_status` (`id`, `name`) VALUES
(1, 'new'),
(2, 'viewed'),
(3, 'in_progress'),
(4, 'resolved'),
(5, 'archived');

commit;

INSERT INTO `tbl_role` (`id`, `name`) VALUES
(1, 'employer'),
(2, 'manager'),
(3, 'board_member'),
(4, 'admin');
commit;

INSERT INTO `tbl_permission` (`id`, `name`, `scope`, `method`) VALUES
(1, 'complaint', 'department', 'list'),
(2, 'complaint', 'user', 'list'),
(3, 'complaint', 'company', 'list'),
(4, 'suggestion', 'user', 'list'),
(5, 'suggestion', 'department', 'list'),
(6, 'suggestion', 'company', 'list'),
(7, 'complaint', 'non', 'add'),
(8, 'suggestion', 'non', 'add'),
(9, 'user', 'non', 'add'),
(10, 'user', 'non', 'list'),
(11, 'super', 'all', 'all');
commit;
INSERT INTO `tbl_role_permission` (`id`, `role_id`, `permission_id`) VALUES
(1, 4, 9),
(2, 1, 2),
(3, 1, 4),
(4, 1, 7),
(5, 1, 8),
(6, 2, 7),
(7, 2, 8),
(8, 2, 1),
(9, 2, 5),
(10, 3, 3),
(11, 3, 6),
(12, 4, 10),
(13, 2, 2),
(14, 2, 4),
(15, 1, 11),
(16, 2, 11),
(17, 3, 11),
(18, 4, 11);
commit;

INSERT INTO `tbl_department` (`id`, `name`, `manager_id`) VALUES
(1, 'software', NULL),
(2, 'advanced_development ', NULL),
(3, 'robotics', NULL),
(4, 'board', NULL),
(5, 'administration', NULL);

commit;

INSERT INTO `tbl_department_role` (`id`, `department_id`, `role_id`) VALUES
(1, 1, 1),
(2, 2, 1),
(3, 3, 1),
(4, 1, 2),
(5, 2, 2),
(6, 3, 2),
(7, 4, 2),
(8, 4, 3),
(9, 5, 4);
commit;
INSERT INTO `tbl_user` (`id`, `first_name`, `last_name`, `email`, `tel`, `create_date`, `password`, `department_role_id`, `active`) VALUES
(10, 'Hesham', 'Osman', 'hesham.osman28@yahoo.com', NULL, NULL, 'e10adc3949ba59abbe56e057f20f883e', 9, 1),
(11, 'Alaa', 'Ehsan', 'Alaa@yahoo.com', NULL, NULL, 'e10adc3949ba59abbe56e057f20f883e', 8, 1),
(12, 'admin', 'admin', 'admin@admin.com', NULL, NULL, 'e10adc3949ba59abbe56e057f20f883e', 9, 1),
(13, 'asda', 'dasd', 'adas', NULL, NULL, '7815696ecbf1c96e6894b779456d330e', 1, 1),
(14, 'osman', 'Osman', 'osman', NULL, NULL, 'eff99cfe6876008c6a6e080e4a382be1', 3, 1),
(15, 'soft', 'emp', 'soft@emp.com', NULL, NULL, 'e10adc3949ba59abbe56e057f20f883e', 1, 1),
(16, 'soft', 'man', 'soft@man.com', NULL, NULL, 'e10adc3949ba59abbe56e057f20f883e', 4, 1);

commit;

INSERT INTO `tbl_complaint` (`id`, `subject`, `user_id`, `complaint_definition`, `anonymous`, `requested_resolution`, `complaint_identifier`, `status_id`, `create_date`) VALUES
(1, 'new', 15, 'no', 0, 'y', 'SYP14929959853232', 2, '2017-09-22'),
(2, 'gdhfjgk', 15, 'xvfcgh', 1, 'dfdgh', 'SYP58680809360994', 2, '2017-09-23');
commit;
INSERT INTO `tbl_action` (`id`, `description`, `user_id`, `action_date`, `create_date`, `complaint_id`, `suggestion_id`) VALUES
(1, 'new Ac', 15, '2017-09-22', '2017-09-22', 1, NULL),
(2, 'new 2', 15, '2017-09-22', '2017-09-22', 1, NULL),
(3, 'dsfdgh', 16, '2017-09-23', '2017-09-23', 2, NULL),
(4, 'dfbcbn', 16, '2017-09-23', '2017-09-23', 2, NULL),
(5, 'kyhkijul4563', 16, '2017-09-23', '2017-09-23', 2, NULL);

commit;