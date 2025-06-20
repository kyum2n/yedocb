
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

INSERT INTO noticeEvent (
    netitle, necontent, neimageurl, netype, nestartdate, neenddate, necreatedat, neupdatedat
) VALUES
('이벤트1', '이벤트입니다', 'https://res.cloudinary.com/dml7imecd/image/upload/v1749713047/eventPopup3_qu7fok.png', '이벤트', NULL, NULL, '2025-06-12 17:44:07.312307', '2025-06-12 17:44:07.312307'),
('공지사항1', '공지사항입니다', 'https://res.cloudinary.com/dml7imecd/image/upload/v1749713067/notise_dshlec.png', '공지사항', NULL, NULL, '2025-06-12 17:44:07.312307', '2025-06-12 17:44:07.312307');


INSERT INTO inquiry (uId, uName, uEmail, visit, qContent, createdAt, qStatus)
VALUES ('test', '테스트유저', 'test@example.com', true, '시술 전 주의사항에 대해 궁금합니다.', '2025-06-01 00:00:00', '답변 대기');
