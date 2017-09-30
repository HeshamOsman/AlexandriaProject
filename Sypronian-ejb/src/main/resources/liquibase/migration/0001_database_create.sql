--liquibase formatted sql
--changeset hesham:0001

CREATE TABLE `tbl_action` (
  `id` int(11) NOT NULL,
  `description` text NOT NULL,
  `user_id` int(11) NOT NULL,
  `action_date` date DEFAULT NULL,
  `create_date` date NOT NULL,
  `complaint_id` int(11) DEFAULT NULL,
  `suggestion_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



CREATE TABLE `tbl_complaint` (
  `id` int(11) NOT NULL,
  `subject` varchar(255) NOT NULL,
  `user_id` int(11) NOT NULL,
  `complaint_definition` text NOT NULL,
  `anonymous` tinyint(1) NOT NULL,
  `requested_resolution` text,
  `complaint_identifier` varchar(190) NOT NULL,
  `status_id` int(11) NOT NULL,
  `create_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `tbl_department` (
  `id` int(11) NOT NULL,
  `name` varchar(190) NOT NULL,
  `manager_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



CREATE TABLE `tbl_department_role` (
  `id` int(11) NOT NULL,
  `department_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



CREATE TABLE `tbl_permission` (
  `id` int(11) NOT NULL,
  `name` varchar(190) NOT NULL,
  `scope` varchar(190) NOT NULL,
  `method` varchar(190) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `tbl_role` (
  `id` int(11) NOT NULL,
  `name` varchar(190) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



CREATE TABLE `tbl_role_permission` (
  `id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



CREATE TABLE `tbl_status` (
  `id` int(11) NOT NULL,
  `name` varchar(190) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



CREATE TABLE `tbl_suggestion` (
  `id` int(11) NOT NULL,
  `subject` varchar(255) NOT NULL,
  `user_id` int(11) NOT NULL,
  `suggestion_definition` text NOT NULL,
  `suggestion_impact` text,
  `status_id` int(11) NOT NULL,
  `anonymous` tinyint(1) NOT NULL,
  `suggestion_identifier` varchar(190) NOT NULL,
  `create_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



CREATE TABLE `tbl_user` (
  `id` int(11) NOT NULL,
  `first_name` varchar(250) NOT NULL,
  `last_name` varchar(250) NOT NULL,
  `email` varchar(150) NOT NULL,
  `tel` varchar(100) DEFAULT NULL,
  `create_date` timestamp NULL DEFAULT NULL,
  `password` varchar(150) NOT NULL,
  `department_role_id` int(11) DEFAULT NULL,
  `active` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE `tbl_action`
  ADD PRIMARY KEY (`id`),
  ADD KEY `action_user` (`user_id`),
  ADD KEY `action_complaint` (`complaint_id`),
  ADD KEY `action_suggestion` (`suggestion_id`);


ALTER TABLE `tbl_complaint`
  ADD PRIMARY KEY (`id`),
  ADD KEY `complaint_user` (`user_id`),
  ADD KEY `complaint_status` (`status_id`);


ALTER TABLE `tbl_department`
  ADD PRIMARY KEY (`id`),
  ADD KEY `department_user_manager` (`manager_id`);

--
-- Indexes for table `tbl_department_role`
--
ALTER TABLE `tbl_department_role`
  ADD PRIMARY KEY (`id`),
  ADD KEY `department_role_department` (`department_id`),
  ADD KEY `department_role_role` (`role_id`);

--
-- Indexes for table `tbl_permission`
--
ALTER TABLE `tbl_permission`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_role`
--
ALTER TABLE `tbl_role`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_role_permission`
--
ALTER TABLE `tbl_role_permission`
  ADD PRIMARY KEY (`id`),
  ADD KEY `role_permission_role` (`role_id`),
  ADD KEY `role_permission_permission` (`permission_id`);

--
-- Indexes for table `tbl_status`
--
ALTER TABLE `tbl_status`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_suggestion`
--
ALTER TABLE `tbl_suggestion`
  ADD PRIMARY KEY (`id`),
  ADD KEY `suggestion_user` (`user_id`),
  ADD KEY `suggestion_status` (`status_id`);

--
-- Indexes for table `tbl_user`
--
ALTER TABLE `tbl_user`
  ADD PRIMARY KEY (`id`),
  ADD KEY `email` (`email`),
  ADD KEY `user_department_role` (`department_role_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbl_action`
--
ALTER TABLE `tbl_action`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `tbl_complaint`
--
ALTER TABLE `tbl_complaint`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `tbl_department`
--
ALTER TABLE `tbl_department`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `tbl_department_role`
--
ALTER TABLE `tbl_department_role`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT for table `tbl_permission`
--
ALTER TABLE `tbl_permission`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
--
-- AUTO_INCREMENT for table `tbl_role`
--
ALTER TABLE `tbl_role`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `tbl_role_permission`
--
ALTER TABLE `tbl_role_permission`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;
--
-- AUTO_INCREMENT for table `tbl_status`
--
ALTER TABLE `tbl_status`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `tbl_suggestion`
--
ALTER TABLE `tbl_suggestion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `tbl_user`
--
ALTER TABLE `tbl_user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `tbl_action`
--
ALTER TABLE `tbl_action`
  ADD CONSTRAINT `action_complaint` FOREIGN KEY (`complaint_id`) REFERENCES `tbl_complaint` (`id`),
  ADD CONSTRAINT `action_suggestion` FOREIGN KEY (`suggestion_id`) REFERENCES `tbl_suggestion` (`id`),
  ADD CONSTRAINT `action_user` FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`id`);

--
-- Constraints for table `tbl_complaint`
--
ALTER TABLE `tbl_complaint`
  ADD CONSTRAINT `complaint_status` FOREIGN KEY (`status_id`) REFERENCES `tbl_status` (`id`),
  ADD CONSTRAINT `complaint_user` FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`id`);

--
-- Constraints for table `tbl_department`
--
ALTER TABLE `tbl_department`
  ADD CONSTRAINT `department_user_manager` FOREIGN KEY (`manager_id`) REFERENCES `tbl_user` (`id`);

--
-- Constraints for table `tbl_department_role`
--
ALTER TABLE `tbl_department_role`
  ADD CONSTRAINT `department_role_department` FOREIGN KEY (`department_id`) REFERENCES `tbl_department` (`id`),
  ADD CONSTRAINT `department_role_role` FOREIGN KEY (`role_id`) REFERENCES `tbl_role` (`id`);

--
-- Constraints for table `tbl_role_permission`
--
ALTER TABLE `tbl_role_permission`
  ADD CONSTRAINT `role_permission_permission` FOREIGN KEY (`permission_id`) REFERENCES `tbl_permission` (`id`),
  ADD CONSTRAINT `role_permission_role` FOREIGN KEY (`role_id`) REFERENCES `tbl_role` (`id`);

--
-- Constraints for table `tbl_suggestion`
--
ALTER TABLE `tbl_suggestion`
  ADD CONSTRAINT `suggestion_status` FOREIGN KEY (`status_id`) REFERENCES `tbl_status` (`id`),
  ADD CONSTRAINT `suggestion_user` FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`id`);

--
-- Constraints for table `tbl_user`
--
ALTER TABLE `tbl_user`
  ADD CONSTRAINT `user_department_role` FOREIGN KEY (`department_role_id`) REFERENCES `tbl_department_role` (`id`);

