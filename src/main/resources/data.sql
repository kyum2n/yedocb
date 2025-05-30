
INSERT INTO admin (aId, aEmail, aPwd, role, createdBy)
VALUES ('super', 'superadmin@naver.com', 'super1234', 'super', NULL);

INSERT INTO users (uId, uName, uEmail, uPwd, uPhone)
VALUES ('test', '테스트유저', 'test@example.com', '1234', '010-0000-0000');

INSERT INTO reservations (uId, tName, consultDate, consultTime, status)
VALUES ('test', '보톡스 시술', '2025-06-01', '14:30:00', '대기');

