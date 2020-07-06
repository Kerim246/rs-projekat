BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "Account" (
	"id"	INTEGER NOT NULL,
	"username"	TEXT NOT NULL,
	"password"	TEXT NOT NULL,
	"profesor"	INTEGER NOT NULL,
	PRIMARY KEY("id"),
	FOREIGN KEY("profesor") REFERENCES "Professor"("id")
);
CREATE TABLE IF NOT EXISTS "Type" (
	"id"	INTEGER NOT NULL,
	"name"	TEXT NOT NULL,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "Professor" (
	"id"	INTEGER NOT NULL,
	"name"	TEXT NOT NULL,
	"last_name"	TEXT NOT NULL,
	"employee_date"	TEXT NOT NULL,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "Subject" (
	"id"	INTEGER NOT NULL,
	"name"	INTEGER NOT NULL,
	"semestar"	INTEGER NOT NULL,
	"ects"	INTEGER NOT NULL,
	"obligatory"	INTEGER NOT NULL,
	"professor"	INTEGER NOT NULL,
	PRIMARY KEY("id"),
	FOREIGN KEY("professor") REFERENCES "Professor"("id")
);
CREATE TABLE IF NOT EXISTS "Material" (
	"id"	INTEGER NOT NULL,
	"name"	TEXT NOT NULL,
	"type"	INTEGER NOT NULL,
	"content"	TEXT NOT NULL,
	"publication_date"	TEXT NOT NULL,
	"subject_id"	INTEGER NOT NULL,
	PRIMARY KEY("id"),
	FOREIGN KEY("subject_id") REFERENCES "Subject"("id")
);
COMMIT;
