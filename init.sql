
CREATE TABLE IF NOT EXISTS orders (
    id BIGSERIAL PRIMARY KEY,
    start_latitude numeric NOT NULL,
    start_longitude numeric NOT NULL,
    end_latitude numeric NOT NULL,
    end_longitude numeric NOT NULL,
    distance numeric NOT NULL,
    status VARCHAR(255) NOT NULL
);
