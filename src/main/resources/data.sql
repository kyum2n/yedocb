
INSERT INTO admin (aId, aEmail, aPwd, role, createdBy)
VALUES ('super', 'superadmin@naver.com', '$2a$10$uqAJJtoClySC3dE6r0NqF.HKFZCtTpap/rbNI3kEF/fq76cr2.5uG', 'SUPERADMIN', NULL);
-- 암호 : super1234

INSERT INTO users (uId, uName, uEmail, uPwd, uPhone)
VALUES ('test', '테스트유저', 'test@example.com', '$2a$10$tTrBmsF8CaOmimgOngeFx.20sgxGKUAtH5XjY43xKykvhezN0kcEC', '010-0000-0000');
-- 암호 : 1234

INSERT INTO reservations (uId, tName, consultDate, consultTime, status)
VALUES ('test', '눈 성형', '2025-06-17', '10:00:00', '대기');
VALUES ('test', '눈 성형', '2025-06-17', '10:30:00', '대기');
VALUES ('test', '눈 성형', '2025-06-17', '11:00:00', '대기');
VALUES ('test', '눈 성형', '2025-06-17', '11:30:00', '대기');
VALUES ('test', '눈 성형', '2025-06-17', '14:00:00', '대기');
VALUES ('test', '눈 성형', '2025-06-17', '14:30:00', '대기');
VALUES ('test', '코 성형', '2025-06-17', '15:00:00', '대기');
VALUES ('test', '코 성형', '2025-06-17', '15:30:00', '대기');
VALUES ('test', '코 성형', '2025-06-17', '16:00:00', '대기');
VALUES ('test', '코 성형', '2025-06-17', '16:30:00', '대기');
-- 중복예약 테스트용
