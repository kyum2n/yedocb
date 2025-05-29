DROP TABLE IF EXISTS reservations;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS admin;

CREATE TABLE admin (
	aid VARCHAR(30) NOT NULL UNIQUE PRIMARY KEY,
	aemail VARCHAR(100) NOT NULL UNIQUE,
	apwd VARCHAR(255) NOT NULL,
	role VARCHAR(20) NOT NULL,
	createdby VARCHAR(30) UNIQUE
);

CREATE TABLE users (
    uid VARCHAR(30) PRIMARY KEY,
    uname VARCHAR(50) NOT NULL,
    uemail VARCHAR(100) NOT NULL UNIQUE,
    upwd VARCHAR(255) NOT NULL,
    uphone VARCHAR(20)
);

CREATE TABLE reservations (
    rid SERIAL PRIMARY KEY,                          -- 예약 ID
    uid VARCHAR(30) NOT NULL,                        -- 예약한 사용자 ID (FK)
    tname VARCHAR(100) NOT NULL,                     -- 예약 시술 항목명
    consultdate DATE NOT NULL,                       -- 예약 날짜
    consulttime TIME NOT NULL,                       -- 예약 시간
    status VARCHAR(20) NOT NULL,                     -- 예약 상태 (대기/확정/취소)

    CONSTRAINT fk_reservations_user FOREIGN KEY (uid) REFERENCES users(uid) ON DELETE CASCADE
);