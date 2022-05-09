INSERT INTO studentGroup(code) VALUES ('test_group');
INSERT INTO practices(location, status) VALUES ('test_location', 'STARTED');
INSERT INTO users(name, surname, middleName, login, password, role, practiceId, groupId) VALUES ('admin', 'admin', 'admin', 'admin', 'admin', 'ADMIN', 1, 1);
INSERT INTO marks(practiceId, userId, mark) VALUES (1, 1, 1);
INSERT INTO documents(path, userId, status) VALUES ('test_path', 1, 'UPLOADED');
INSERT INTO eventCalendar(startAt, endAt, timezoneStartAt, summary, color, calendarId, userId, practiceId) VALUES ('2022-05-12T18:00:00.000Z', '2022-05-13T18:00:00.000Z', 'Europe/Berlin', 'test', 'blue', 'work', 1, 1);
