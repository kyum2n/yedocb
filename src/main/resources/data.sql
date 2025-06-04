
INSERT INTO admin (aId, aEmail, aPwd, role, createdBy)
VALUES ('super', 'superadmin@naver.com', '$2a$10$uqAJJtoClySC3dE6r0NqF.HKFZCtTpap/rbNI3kEF/fq76cr2.5uG', 'SUPERADMIN', NULL);
-- 암호 : super1234

INSERT INTO users (uId, uName, uEmail, uPwd, uPhone)
VALUES ('test', '테스트유저', 'test@example.com', '$2a$10$tTrBmsF8CaOmimgOngeFx.20sgxGKUAtH5XjY43xKykvhezN0kcEC', '010-0000-0000');
-- 암호 : 1234

INSERT INTO reservations (uId, tName, consultDate, consultTime, status)
VALUES ('test', '보톡스 시술', '2025-06-01', '14:30:00', '대기');

