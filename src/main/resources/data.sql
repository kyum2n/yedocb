
INSERT INTO admin (aId, aEmail, aPwd, role, createdBy)
VALUES ('super', 'superadmin@naver.com', '$2a$10$OB/fhsHmiXUMSU3WVY5y0.qQUWNIgi3NuYmWisgvWHyoSkZfu/Qge', 'SUPERADMIN', NULL);
-- 암호 : super1234

INSERT INTO admin (aId, aEmail, aPwd, role, createdBy)
VALUES ('staff1', 'aphrod207@naver.com', '$2a$10$XGCHaJYV7Z0CQdH5RRHPAe/NyRUMgWpW9oqlkQHeimK6UyOfTaPxK', 'ADMIN', 'SUPERADMIN');
-- 암호 : staff1111

INSERT INTO users (uId, uName, uEmail, uPwd, uPhone)
VALUES ('test', '테스트유저', 'test@example.com', '$2a$10$tTrBmsF8CaOmimgOngeFx.20sgxGKUAtH5XjY43xKykvhezN0kcEC', '010-0000-0000');
-- 암호 : 1234

INSERT INTO reservations (uId, tName, consultDate, consultTime, status)
VALUES ('test', '보톡스 시술', '2025-06-01', '14:30:00', '대기');