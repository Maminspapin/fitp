CREATE TABLE IF NOT EXISTS quote
(
    id   int NOT NULL AUTO_INCREMENT,
    isin varchar(200),
    bid  double(50,4),
    ask  double(50,4),
    time_received date,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS elvl
(
    isin varchar(200) NOT NULL,
    elvl  double(50,4),
    PRIMARY KEY (isin)
);