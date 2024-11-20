CREATE DATABASE logistic_management;

USE logistic_management;

CREATE TABLE `role` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL COMMENT "Tên vai trò",
  `created_at` TIMESTAMP NOT NULL DEFAULT now(),
  `updated_at` TIMESTAMP NOT NULL DEFAULT now(),
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

INSERT INTO role (id, name, created_at, updated_at)
VALUES (1, "ADMIN", now(), now()),
		(2, "ACCOUNTANT", now(), now()),
        (3, "MANAGER", now(), now()),
        (4, "DRIVER", now(), now());

CREATE TABLE `user` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `role_id` INT UNSIGNED NOT NULL COMMENT "Khóa ngoại đến vai trò",
  `status` INT NOT NULL DEFAULT 1 COMMENT "Trạng thái người dùng: 0 - Không hoạt động, 1 - Đang hoạt động",
  `created_at` TIMESTAMP NOT NULL DEFAULT now(),
  `updated_at` TIMESTAMP NOT NULL DEFAULT now(),
  PRIMARY KEY (`id`),
  FOREIGN KEY (`role_id`) REFERENCES `role`(`id`)
) ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `inbound_transaction` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` INT UNSIGNED NOT NULL COMMENT "Khóa ngoại đến người dùng - Người chịu trách nhiệm cho lần giao dịch",
  `intake_time` TIMESTAMP NOT NULL COMMENT "Thời gian giao dịch",
  `total_amount` FLOAT NOT NULL DEFAULT 0 COMMENT "Tổng tiền trong một giao dịch",
  `created_at` TIMESTAMP NOT NULL DEFAULT now(),
  `updated_at` TIMESTAMP NOT NULL DEFAULT now(),
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `user`(`id`)
) ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `goods` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `quantity` INT NOT NULL DEFAULT 0,
  `price` FLOAT NOT NULL DEFAULT 0,
  `created_at` TIMESTAMP NOT NULL DEFAULT now(),
  `updated_at` TIMESTAMP NOT NULL DEFAULT now(),
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

INSERT INTO `goods` (id, name, quantity, price, created_at, updated_at)
VALUES (1, "Gạch", 300, 5000000, now(), now()),
		(2, "Đá", 100, 3500000, now(), now()),
        (3, "Xi măng", 500, 7000000, now(), now());

CREATE TABLE `inbound_transaction_detail` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `inbound_transaction_id` INT UNSIGNED NOT NULL,
  `goods_id` INT UNSIGNED NOT NULL,
  `origin` VARCHAR(255) NOT NULL COMMENT "Nguồn gốc chuyến hàng",
  `quantity` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`inbound_transaction_id`) REFERENCES `inbound_transaction`(`id`),
  FOREIGN KEY (`goods_id`) REFERENCES `goods`(`id`)
) ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `permission` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT now(),
  `updated_at` TIMESTAMP NOT NULL DEFAULT now(),
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- INSERT INTO permission (id, title, name, created_at, updated_at)
-- VALUES (1, "Quản lý tài khoản", "ACCOUNT_MANAGE", now(), now()),
-- 		(2, "Quản lý tài chính", "FINANCE_MANAGE", now(), now()),
--         (3, "Quản lý tài khoản", "ACCOUNT_MANAGE", now(), now()),
--         (4, "Quản lý lịch trình", "SCHEDULE_MANAGE", now(), now()),
--         (5, "Quản lý tài khoản", "ACCOUNT_MANAGE", now(), now());
        

CREATE TABLE `role_permission` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `role_id` INT UNSIGNED NOT NULL COMMENT "Khóa ngoại đến vai trò",
  `permission_id` INT UNSIGNED NOT NULL COMMENT "Khóa ngoại đến quyền hạn",
  `can_write` BIT NOT NULL DEFAULT b'0' COMMENT "Quyền ghi: 0 - Không được phép, 1 - Được phép",
  `can_view` BIT NOT NULL DEFAULT b'0' COMMENT "Quyền xem: 0 - Không được phép, 1 - Được phép",
  `can_approve` BIT NOT NULL DEFAULT b'0' COMMENT "Quyền duyệt: 0 - Không được phép, 1 - Được phép",
  `can_delete` BIT NOT NULL DEFAULT b'0' COMMENT "Quyền xóa: 0 - Không được phép, 1 - Được phép",
  `created_at` TIMESTAMP NOT NULL DEFAULT now(),
  `updated_at` TIMESTAMP NOT NULL DEFAULT now(),
  PRIMARY KEY (`id`),
  FOREIGN KEY (`permission_id`) REFERENCES `permission`(`id`),
  FOREIGN KEY (`role_id`) REFERENCES `role`(`id`)
) ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `outbound_transaction` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` INT UNSIGNED NOT NULL COMMENT "Khóa ngoại đến người dùng - Người chịu trách nhiệm",
  `schedule_id` INT UNSIGNED NOT NULL COMMENT "Khóa ngoại đến lịch trình",
  `approved_time` TIMESTAMP COMMENT "Thời gian duyệt",
  `total_amount` FLOAT NOT NULL DEFAULT 0 COMMENT "Tổng tiền giao dịch",
  `status` INT NOT NULL DEFAULT 0 COMMENT "Trạng thái giao dịch: (-1) - Không duyệt, 0 - Chờ duyệt, 1 - Đã duyệt, 2 - Đã hoàn thành",
  `created_at` TIMESTAMP NOT NULL DEFAULT now(),
  `updated_at` TIMESTAMP NOT NULL DEFAULT now(),
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
  FOREIGN KEY (`schedule_id`) REFERENCES `schedule`(`id`)
) ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `truck` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `license_plate` VARCHAR(255) NOT NULL,
  `capacity` FLOAT NOT NULL DEFAULT 0 COMMENT "Tải trọng của xe",
  `note` TEXT COMMENT "Ghi chú thêm",
  `status` INT NOT NULL DEFAULT 1 COMMENT "Trạng thái của xe: -1: Bảo trì, 0 - Không có sẵn, 1 - Có sẵn",
  `created_at` TIMESTAMP NOT NULL DEFAULT now(),
  `updated_at` TIMESTAMP NOT NULL DEFAULT now(),
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

insert into truck (id, license_plate, capacity, note, status, created_at, updated_at)
values (2, "BBBBBBBBBB", 20, "Chiec xe tai de test 2", 1, now(), now());

CREATE TABLE `schedule_config` (
  `id` INT UNSIGNED AUTO_INCREMENT,
  `place_a` VARCHAR(255) NOT NULL,
  `place_b` VARCHAR(255) NOT NULL,
  `commission` FLOAT NOT NULL DEFAULT 0 COMMENT "Hoa hồng cho mỗi lịch trình",
  `created_at` TIMESTAMP NOT NULL DEFAULT now(),
  `updated_at` TIMESTAMP NOT NULL DEFAULT now(),
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

INSERT INTO `schedule_config` (id, place_a, place_b, commission, created_at, updated_at)
VALUES (1, "Hà Nội", "Thành phố Hồ Chí Minh", 10000000, now(), now()),
		(2, "Hà Nội", "Đà Nẵng", 7000000, now(), now()),
        (3, "Hà Nội", "Thừa Thiên - Huế", 5500000, now(), now()),
        (4, "Hà Nội", "Bình Dương", 9000000, now(), now()),
        (5, "Hà Nội", "Bắc Giang", 4000000, now(), now()),
        (6, "Hà Nội", "Lào Cai", 4500000, now(), now());

CREATE TABLE `schedule` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `schedule_config_id` INT UNSIGNED NOT NULL COMMENT "Khóa ngoại đến cấu hình lịch trình",
  `truck_id` INT UNSIGNED NOT NULL COMMENT "Khóa ngoại đến xe tải",
  `driver_id` INT UNSIGNED NOT NULL COMMENT "Khóa ngoại đến người dùng - Tài xế lái xe",
  `path_attach_document` VARCHAR(255) COMMENT "Đường dẫn lưu tài liệu bổ sung",
  `departure_time` TIMESTAMP NOT NULL COMMENT "Thời gian khởi hành",
  `arrival_time` TIMESTAMP COMMENT "Thời gian hoàn thành",
  `status` INT NOT NULL DEFAULT 0 COMMENT "Trạng thái lịch trình: -1 - Không duyệt, 0 - Đang chờ, 1 - Đã duyệt và chưa hoàn thành, 2 - Đã hoàn thành",
  `expenses_status` INT NOT NULL DEFAULT -1 COMMENT "Trạng thái thanh toán chi phí: -1 - Chưa có chi phí phát sinh, 0 - Chưa thanh toán, 1 - Đã thanh toán",
  `created_at` TIMESTAMP NOT NULL DEFAULT now(),
  `updated_at` TIMESTAMP NOT NULL DEFAULT now(),
  PRIMARY KEY (`id`),
  FOREIGN KEY (`driver_id`) REFERENCES `user`(`id`),
  FOREIGN KEY (`truck_id`) REFERENCES `truck`(`id`),
  FOREIGN KEY (`schedule_config_id`) REFERENCES `schedule_config`(`id`)
) ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `salary` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` INT UNSIGNED NOT NULL COMMENT "Khóa ngoại đến người dùng",
  `allowance` FLOAT DEFAULT 0 COMMENT "Tiền phụ cấp",
  `basic_salary` FLOAT DEFAULT 0 COMMENT "Lương cơ bản",
  `advance` FLOAT DEFAULT 0 COMMENT "Tiền tạm ứng",
  `period` VARCHAR(7) NOT NULL COMMENT "Chu kỳ thanh toán lương, format: YYYY-MM",
  `status` INT NOT NULL DEFAULT 0 COMMENT "Trạng thái: 0 - Chưa thanh toán, 1 - Đã thanh toán",
  `created_at` TIMESTAMP NOT NULL DEFAULT now(),
  `updated_at` TIMESTAMP NOT NULL DEFAULT now(),
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `user`(`id`)
) ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `outbound_transaction_detail` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `goods_id` INT UNSIGNED NOT NULL,
  `outbound_transaction_id` INT UNSIGNED NOT NULL COMMENT "Khóa ngoại đến outbound_transaction",
  `quantity` INT NOT NULL DEFAULT 0,
  `price` FLOAT NOT NULL DEFAULT 0,
  `destination` VARCHAR(255) NOT NULL COMMENT "Điểm đến",
  PRIMARY KEY (`id`),
  FOREIGN KEY (`outbound_transaction_id`) REFERENCES `outbound_transaction`(`id`),
  FOREIGN KEY (`goods_id`) REFERENCES `goods`(`id`)
) ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- CREATE TABLE `advance_payment_detail` (
--   `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
--   `user_id` INT UNSIGNED NOT NULL COMMENT "Khóa ngoại đến user",
--   `advance_payment` FLOAT DEFAULT 0 COMMENT "Tiền ứng",
--   `status` INT NOT NULL DEFAULT 0 COMMENT "Trạng thái ứng tiền: -1 - Không duyệt, 0 - Chờ duyệt, 1 - Đã duyệt",
--   `period` VARCHAR(255) NOT NULL COMMENT "Chu kỳ lương",
--   `reason` TEXT NOT NULL COMMENT "Lý do ứng lương",
--   `created_at` TIMESTAMP NOT NULL DEFAULT now(),
--   `updated_at` TIMESTAMP NOT NULL DEFAULT now(),
--   PRIMARY KEY (`id`),
--   FOREIGN KEY (`user_id`) REFERENCES `user`(`id`)
-- ) ENGINE = InnoDB
-- DEFAULT CHARSET = utf8mb4
-- COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `expenses` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `schedule_id` INT UNSIGNED NOT NULL COMMENT "Khóa ngoại đến lịch trình",
  `description` TEXT NOT NULL COMMENT "Mô tả chi phí",
  `amount` FLOAT NOT NULL DEFAULT 0 COMMENT "Giá tiền",
  `created_at` TIMESTAMP NOT NULL DEFAULT now(),
  `updated_at` TIMESTAMP NOT NULL DEFAULT now(),
  PRIMARY KEY (`id`),
  FOREIGN KEY (`schedule_id`) REFERENCES `schedule`(`id`)
) ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

insert into expenses (id, schedule_id, amount, description, created_at, updated_at)
values (2, 3, 1000000, "qqqqqqqq", now(), now());

select * from expenses e, schedule s where e.schedule_id = 3 and e.schedule_id = s.id;
select * from schedule;

select * from salary s
left join user u on s.user_id = u.id
left join schedule sch on sch.driver_id = s.user_id
left join expenses e on e.schedule_id = sch.id;

insert into expenses (id, schedule_id, amount, description, created_at, updated_at)
values (3, 3, 3000000, "qqqqqqqq", now(), now());

insert into schedule (id, schedule_config_id, truck_id, driver_id, path_attach_document, departure_time, arrival_time, status, expenses_status, created_at, updated_at)
values (2, 2, 1, 2, null, now(), null, 1, 1, now(), now()),
		(3, 2, 1, 3, null, now(), null, 1, 1, now(), now()),
        (4, 3, 1, 3, null, now(), null, 1, 1, now(), now()),
        (5, 2, 1, 4, null, now(), null, 1, 1, now(), now());


insert into user (id, username, password, role_id, status, created_at, updated_at)
values (3, "driver2", "p2", 4, 1, now(), now()),
		(4, "driver3", "p3", 4, 1, now(), now());

-- Chi tiết lương
select
	s.user_id, u.username, u.role_id,
    s.basic_salary, s.allowance, s.advance,
    (select coalesce(sum(commission), 0) from schedule sch, schedule_config sc
		where sch.schedule_config_id = sc.id and sch.driver_id = s.user_id)
        as totalCommission,
	(select coalesce(sum(amount), 0) from schedule sch, expenses e
		where sch.id = e.schedule_id and sch.driver_id = s.user_id)
        as totalExpenses
from salary s
left join user u on s.user_id = u.id
where s.period < "2025-01" and s.period > "2024-01";

-- Tổng quan tiền lương
select
    u.role_id,
    count(distinct s.user_id) as numberOfUser,
    sum(basic_salary) as totalBasicSalary,
    sum(allowance) as totalAllowance,
    (select sum(commission) from schedule_config sc, schedule sch
		where sc.id = sch.schedule_config_id and sch.driver_id = s.user_id)
        as totalCommission,
    sum(
		(select coalesce(sum(amount), 0) from schedule sch, expenses e
		where sch.id = e.schedule_id and sch.driver_id = s.user_id)
	) as totalExpenses,
    sum(advance) as totalAdvance
from salary s
left join user u on s.user_id = u.id
where s.period < "2025-01" and s.period > "2024-01"
group by u.role_id, s.user_id;

SELECT
    u.role_id,
    COUNT(s.user_id) AS numberOfUser, -- Đếm số lượng người dùng duy nhất
    SUM(s.basic_salary) AS totalBasicSalary, -- Tổng basic_salary
    SUM(s.allowance) AS totalAllowance, -- Tổng allowance
    (
        SELECT SUM(sc.commission)
        FROM schedule_config sc
        JOIN schedule sch ON sc.id = sch.schedule_config_id
        WHERE sch.driver_id IN (
            SELECT s1.user_id
            FROM salary s1
            JOIN user u1 ON s1.user_id = u1.id
            WHERE s1.period < "2025-01"
              AND s1.period > "2024-01"
              AND u1.role_id = u.role_id
        )
    ) AS totalCommission, -- Tổng commission theo role_id
    (
        SELECT SUM(e.amount)
        FROM schedule sch
        JOIN expenses e ON sch.id = e.schedule_id
        WHERE sch.driver_id IN (
            SELECT s1.user_id
            FROM salary s1
            JOIN user u1 ON s1.user_id = u1.id
            WHERE s1.period < "2025-01"
              AND s1.period > "2024-01"
              AND u1.role_id = u.role_id
        )
    ) AS totalExpenses, -- Tổng amount từ bảng expenses theo role_id
    SUM(s.advance) AS totalAdvance -- Tổng advance
FROM salary s
LEFT JOIN user u ON s.user_id = u.id
WHERE s.period < "2025-01" AND s.period > "2024-01"
GROUP BY u.role_id;

select
   *
from salary s
left join user u on s.user_id = u.id
left join schedule sch on sch.driver_id = u.id
left join schedule_config sc on sch.schedule_config_id = sc.id
left join expenses e on e.schedule_id = sch.id
where s.period < "2025-01" and s.period > "2024-01";

select * from salary s
left join user u on s.user_id = u.id
where u.role_id = 4;

select * from expenses e, schedule s
where e.schedule_id = s.id and s.driver_id = 3;
select * from salary where id = 2;

insert into salary (id, user_id, allowance, basic_salary, advance, period, status, created_at, updated_at)
values (4, 3, 1000000, 5000000, 0, "2024-11", 0, now(), now());