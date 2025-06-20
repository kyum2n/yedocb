DROP TABLE IF EXISTS inquiry;
DROP TABLE IF EXISTS noticeEvent;
DROP TABLE IF EXISTS reservations;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS admin;

-- 관리자 테이블
CREATE TABLE admin (
	aId VARCHAR(30) NOT NULL UNIQUE PRIMARY KEY,
	aEmail VARCHAR(100) NOT NULL UNIQUE,
	aPwd VARCHAR(255) NOT NULL,
	role VARCHAR(20) NOT NULL,
	createdBy VARCHAR(30)
);

-- 사용자 테이블
CREATE TABLE users (
    uId VARCHAR(30) PRIMARY KEY,
    uName VARCHAR(50) NOT NULL,
    uEmail VARCHAR(100) NOT NULL UNIQUE,
    uPwd VARCHAR(255) NOT NULL,
    uPhone VARCHAR(20)
);

-- 예약 테이블
CREATE TABLE reservations (
    rId SERIAL PRIMARY KEY,                          -- 예약 ID
    uId VARCHAR(30) NOT NULL,                        -- 예약한 사용자 ID (FK)
    tName VARCHAR(100) NOT NULL,                     -- 예약 시술 항목명
    consultDate DATE NOT NULL,                       -- 예약 날짜
    consultTime TIME NOT NULL,                       -- 예약 시간
    status VARCHAR(20) NOT NULL,                     -- 예약 상태 (대기/확정/취소)

    CONSTRAINT fk_reservations_user FOREIGN KEY (uid) REFERENCES users(uid) ON DELETE CASCADE
);

-- 공지사항 테이블
CREATE TABLE noticeEvent (
    neId SERIAL PRIMARY KEY,
    neTitle VARCHAR(200) NOT NULL,
    neContent TEXT NOT NULL,
    neImageUrl VARCHAR(500),
    neType VARCHAR(20) NOT NULL,
    neStartDate DATE,
    neEndDate DATE,
    neCreatedAt TIMESTAMP NOT NULL DEFAULT NOW(),
    neUpdatedAt TIMESTAMP NOT NULL DEFAULT NOW()
);

-- 1:1 문의 테이블
CREATE TABLE inquiry (
    qId SERIAL PRIMARY KEY,                        
    uId VARCHAR(50) NOT NULL,                      
    uName VARCHAR(50) NOT NULL,                    
    uEmail VARCHAR(100) NOT NULL,                  
    visit BOOLEAN NOT NULL,                        
    qContent TEXT,                                 
    createdAt TIMESTAMP NOT NULL,                  
    qAnswer TEXT,                                 
    answeredAt TIMESTAMP,                          
    qStatus VARCHAR(50) NOT NULL,                 

	-- 사용자가 삭제되면 문의도 함께 삭제됨
    CONSTRAINT fk_inquiry_user FOREIGN KEY (uId)
        REFERENCES users(uId)
        ON DELETE CASCADE
);
