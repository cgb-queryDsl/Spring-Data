-- 사용자
CREATE TABLE `users`
(
    user_id    INTEGER     NOT NULL AUTO_INCREMENT,
    username   Varchar(45) NOT NULL UNIQUE,
    password   Varchar(45) NOT NULL,
    user_role  Varchar(30) NOT NULL,
    created_at Datetime    NOT NULL,

    PRIMARY KEY (user_id)
);

INSERT INTO users(username, password, user_role, created_at)
VALUES ('admin', '12345', 'ROLE_ADMIN', now());
INSERT INTO users(username, password, user_role, created_at)
VALUES ('user', '12345', 'ROLE_USER', now());

-- 게시글
CREATE TABLE `boards`
(
    board_id      INTEGER       NOT NULL AUTO_INCREMENT,
    user_id       INTEGER       NOT NULL,
    title         Varchar(60)   NOT NULL,
    content       Varchar(2000) NOT NULL,
    editor_name   Varchar(45)   NOT NULL,
    created_at    Datetime      NOT NULL,
    updated_at    Datetime      NOT NULL,
    deleted       TinyInt DEFAULT 0,
    comment_count int     DEFAULT 0,

    PRIMARY KEY (board_id),
    FOREIGN KEY (user_id) REFERENCES `users` (user_id)
);

INSERT INTO boards(user_id, title, content, editor_name, created_at, updated_at)
VALUES (1, '게시글1', '게시글 내용', 'admin', now(), now());

-- 좋아요
CREATE TABLE `board_likes`
(
    user_id  INTEGER NOT NULL,
    board_id INTEGER NOT NULL,

    PRIMARY KEY (user_id, board_id)
);

INSERT INTO board_likes(user_id, board_id)
VALUES (1, 1);

-- 파일
CREATE TABLE `files`
(
    file_id  INTEGER      NOT NULL AUTO_INCREMENT,
    board_id INTEGER      NOT NULL,
    filename Varchar(300) NOT NULL,

    PRIMARY KEY (file_id),
    FOREIGN KEY (board_id) REFERENCES `boards` (board_id)
);

INSERT INTO files(board_id, filename)
VALUES (1, '파일이름');

-- 댓글
CREATE TABLE `comments`
(
    comment_id INTEGER      NOT NULL AUTO_INCREMENT,
    board_id   INTEGER      NOT NULL,
    commenter  Varchar(45)  NOT NULL,
    text       Varchar(300) NOT NULL,
    created_at Datetime     NOT NULL,
    updated_at Datetime     NOT NULL,

    PRIMARY KEY (comment_id),
    FOREIGN KEY (board_id) REFERENCES boards (board_id)
);

INSERT INTO comments(board_id, commenter, text, created_at, updated_at)
VALUES (1, 'user', '댓글 본문', now(), now());