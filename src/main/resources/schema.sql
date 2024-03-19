CREATE TABLE class
  (
    id_combo INTEGER PRIMARY KEY,
    primary_secondary VARCHAR(50) NOT NULL
  );

CREATE TABLE operator
  (
    name    VARCHAR(50) PRIMARY KEY NOT NULL,
    UNIQUE(name),
    position_op    VARCHAR(20) NOT NULL,
    attack  VARCHAR(50) NOT NULL,
    alter_op    BOOLEAN NOT NULL,
    class INTEGER REFERENCES class(id_combo) ON DELETE CASCADE NOT NULL
  );

CREATE TABLE skill
  (
    operator_name   VARCHAR(50) NOT NULL,
    FOREIGN KEY (operator_name) REFERENCES operator(name) ON DELETE CASCADE,
    name    VARCHAR(255) NOT NULL,
    charge  VARCHAR(50) NOT NULL,
    duration    VARCHAR(50) NOT NULL,
    cost    INTEGER NOT NULL,
    initial INTEGER NOT NULL,
    auto BOOLEAN NOT NULL
  );