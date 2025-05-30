DROP TABLE IF EXISTS reservations;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS admin;

CREATE TABLE admin (
	aId VARCHAR(30) NOT NULL UNIQUE PRIMARY KEY,
	aEmail VARCHAR(100) NOT NULL UNIQUE,
	aPwd VARCHAR(255) NOT NULL,
	role VARCHAR(20) NOT NULL,
	createdBy VARCHAR(30) UNIQUE
);

CREATE TABLE users (
    uId VARCHAR(30) PRIMARY KEY,
    uName VARCHAR(50) NOT NULL,
    uEmail VARCHAR(100) NOT NULL UNIQUE,
    uPwd VARCHAR(255) NOT NULL,
    uPhone VARCHAR(20)
);

CREATE TABLE reservations (
    rId SERIAL PRIMARY KEY,                          -- 예약 ID
    uId VARCHAR(30) NOT NULL,                        -- 예약한 사용자 ID (FK)
    tName VARCHAR(100) NOT NULL,                     -- 예약 시술 항목명
    consultDate DATE NOT NULL,                       -- 예약 날짜
    consultTime TIME NOT NULL,                       -- 예약 시간
    status VARCHAR(20) NOT NULL,                     -- 예약 상태 (대기/확정/취소)

    CONSTRAINT fk_reservations_user FOREIGN KEY (uid) REFERENCES users(uid) ON DELETE CASCADE
);