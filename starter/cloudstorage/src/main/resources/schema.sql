CREATE TABLE IF NOT EXISTS USERS (
  userid INT PRIMARY KEY auto_increment,
  username VARCHAR(255),
  salt VARCHAR,
  password VARCHAR,
  firstname VARCHAR(255),
  lastname VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS NOTES (
    noteId INT PRIMARY KEY auto_increment,
    noteTitle VARCHAR(255),
    noteDescription VARCHAR (1000),
    userId INT,
    foreign key (userid) references USERS(userid)
);

CREATE TABLE IF NOT EXISTS FILES (
    fileId INT PRIMARY KEY auto_increment,
    filename VARCHAR,
    contenttype VARCHAR,
    filesize VARCHAR,
    userid INT,
    filedata BLOB,
    foreign key (userid) references USERS(userid)
);

CREATE TABLE IF NOT EXISTS CREDENTIALS (
    credentialid INT PRIMARY KEY auto_increment,
    url VARCHAR(255),
    username VARCHAR (255),
    key VARCHAR(255),
    password VARCHAR,
    userid INT,
    foreign key (userid) references USERS(userid)
);