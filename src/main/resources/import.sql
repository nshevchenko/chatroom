--
-- JBoss, Home of Professional Open Source
-- Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
-- contributors by the @authors tag. See the copyright.txt in the
-- distribution for a full listing of individual contributors.
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
-- http://www.apache.org/licenses/LICENSE-2.0
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

INSERT INTO USERS (id, username, password, loggedIn) VALUES (-1, 'admin', 'admin', 'TRUE');
INSERT INTO USERS (id, username, password, loggedIn) VALUES (-2, 'nik', 'asdf', 'FALSE');

INSERT INTO ChatMessages (id, username, message) VALUES (-1, 'henrik', 'hi HENRIK -1 ');
INSERT INTO ChatMessages (id, username, message) VALUES (-2, 'nik', 'hi NIK -2 ');
INSERT INTO ChatMessages (id, username, message) VALUES (0, 'henrik', 'hi HENRIK 0');
INSERT INTO ChatMessages (id, username, message) VALUES (1, 'nik', 'hi NIK 1');
INSERT INTO ChatMessages (id, username, message) VALUES (2, 'henrik', 'hi HENRIK 2 ');
INSERT INTO ChatMessages (id, username, message) VALUES (3, 'nik', 'hi NIK3');
INSERT INTO ChatMessages (id, username, message) VALUES (4, 'henrik', 'hi HENRIK 4');
INSERT INTO ChatMessages (id, username, message) VALUES (5, 'nik', 'hi NIK5');
INSERT INTO ChatMessages (id, username, message) VALUES (6, 'henrik', 'hi HENRIK6');
INSERT INTO ChatMessages (id, username, message) VALUES (7, 'nik', 'hi NIK   7');
INSERT INTO ChatMessages (id, username, message) VALUES (8, 'henrik', 'hi HENRIK  8');
INSERT INTO ChatMessages (id, username, message) VALUES (9, 'nik', 'hi NIK   9');
INSERT INTO ChatMessages (id, username, message) VALUES (10, 'henrik', 'hi HENRIK  10');
INSERT INTO ChatMessages (id, username, message) VALUES (11, 'nik', 'hi NIK   11');
