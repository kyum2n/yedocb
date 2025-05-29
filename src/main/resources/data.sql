INSERT INTO admin (aid, aemail, apwd, role, created_by)
VALUES ('super', 'superadmin@naver.com', 'super1234', 'super', NULL);

INSERT INTO users (uid, uname, uemail, upwd, uphone)
VALUES ('test', '테스트유저', 'test@example.com', '1234', '010-0000-0000');

INSERT INTO reservations (uid, tname, consultdate, consulttime, status)
VALUES ('test', '보톡스 시술', '2025-06-01', '14:30:00', '대기');