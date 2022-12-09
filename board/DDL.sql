use nhn_academy_25;

-- 사용자
CREATE TABLE Users (
	userId INTEGER NOT NULL AUTO_INCREMENT,
	username Varchar(45) NOT NULL UNIQUE,
	password Varchar(45) NOT NULL,
	userRole Varchar(30) NOT NULL,
	createdAt Datetime NOT NULL,
	
	PRIMARY KEY(userId)
) ENGINE = InnoDB;

INSERT INTO Users(username, password, userRole, createdAt) VALUES ('admin', '12345', 'ROLE_ADMIN', DATE(now()));
INSERT INTO Users(username, password, userRole, createdAt) VALUES ('user', '12345', 'ROLE_USER', DATE(now()));

-- 게시글
CREATE TABLE Boards (
	boardId INTEGER NOT NULL AUTO_INCREMENT,
	writerId INTEGER NOT NULL,
	title Varchar(60) NOT NULL,
	content Varchar(2000) NOT NULL,
	editorName Varchar(45) NOT NULL,
	createdAt Datetime NOT NULL,
	updatedAt Datetime NOT NULL,
	deleted Tinyint(1) DEFAULT 0,
	commentCount int DEFAULT 0,
	
	PRIMARY KEY(boardId),
	FOREIGN KEY(writerId) REFERENCES Users(userId),
	INDEX(title)
) ENGINE = InnoDB;

-- 좋아요
CREATE TABLE Board_Likes (
	boardLikeId INTEGER NOT NULL AUTO_INCREMENT,
	userId INTEGER NOT NULL,
	boardId INTEGER NOT NULL,
	
	PRIMARY KEY(boardLikeId),
	FOREIGN KEY(userId) REFERENCES Users(userId),
	FOREIGN KEY(boardId) REFERENCES Boards(boardId)
) ENGINE = InnoDB;

-- 파일
CREATE TABLE Files (
	fileId INTEGER NOT NULL AUTO_INCREMENT,
	boardId INTEGER NOT NULL,
	filename Varchar(300) NOT NULL,
	
	PRIMARY KEY(fileId),
	FOREIGN KEY(boardId) REFERENCES Boards(boardId)
) ENGINE = InnoDB;

-- 댓글
CREATE TABLE Comments (
	commentId INTEGER NOT NULL AUTO_INCREMENT,
	boardId INTEGER NOT NULL,
	commenter Varchar(45) NOT NULL,
	text Varchar(300) NOT NULL,
	createdAt Datetime NOT NULL,
	updatedAt Datetime NOT NULL,
	
	PRIMARY KEY(commentId),
	FOREIGN KEY(boardId) REFERENCES Boards(boardId)
) ENGINE = InnoDB;