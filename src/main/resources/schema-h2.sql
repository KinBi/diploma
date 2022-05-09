drop table if exists eventCalendar;
drop table if exists documents;
drop table if exists marks;
drop table if exists users;
drop table if exists practices;
drop table if exists studentGroup;

CREATE TABLE studentGroup (
    id BIGINT AUTO_INCREMENT primary key,
    code VARCHAR(50)
);

CREATE TABLE practices (
    id BIGINT AUTO_INCREMENT primary key,
    location VARCHAR(254) NOT NULL,
    status VARCHAR(254) NOT NULL
);

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT primary key,
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    middleName VARCHAR(50) NOT NULL,
    login VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL,
    practiceId BIGINT,
    groupId BIGINT NOT NULL,
    UNIQUE (login),
    CONSTRAINT FK_users_practices FOREIGN KEY (practiceId) REFERENCES practices (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_users_groups FOREIGN KEY (groupId) REFERENCES studentGroup (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE marks (
    practiceId BIGINT NOT NULL,
    userId BIGINT NOT NULL,
    mark SMALLINT NOT NULL,
    UNIQUE (practiceId),
    CONSTRAINT FK_marks_practices FOREIGN KEY (practiceId) REFERENCES practices (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_marks_users FOREIGN KEY (userId) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE documents (
    id BIGINT AUTO_INCREMENT primary key,
    path VARCHAR(254) NOT NULL,
    userId BIGINT,
    status VARCHAR(254) NOT NULL,
    CONSTRAINT FK_documents_users FOREIGN KEY (userId) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
);

CREATE TABLE eventCalendar (
    id BIGINT AUTO_INCREMENT primary key,
    startAt VARCHAR(254) NOT NULL,
    endAt VARCHAR(254) NOT NULL,
    timezoneStartAt VARCHAR(254) NOT NULL,
    summary VARCHAR(254) NOT NULL,
    color VARCHAR(254) NOT NULL,
    calendarId VARCHAR(254) NOT NULL,
    userId BIGINT NOT NULL,
    practiceId BIGINT NOT NULL,
    CONSTRAINT FK_events_users FOREIGN KEY (userId) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_events_practices FOREIGN KEY (practiceId) REFERENCES practices (id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO studentGroup(code) VALUES ('test_group');
INSERT INTO practices(location, status) VALUES ('test_location', 'STARTED');
INSERT INTO users(name, surname, middleName, login, password, role, practiceId, groupId) VALUES ('admin', 'admin', 'admin', 'admin', 'admin', 'ADMIN', 1, 1);
INSERT INTO marks(practiceId, userId, mark) VALUES (1, 1, 1);
INSERT INTO documents(path, userId, status) VALUES ('test_path', 1, 'UPLOADED');
INSERT INTO eventCalendar(startAt, endAt, timezoneStartAt, summary, color, calendarId, userId, practiceId) VALUES ('2022-05-12T18:00:00.000Z', '2022-05-13T18:00:00.000Z', 'Europe/Berlin', 'test', 'blue', 'work', 1, 1);
