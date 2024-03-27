
CREATE TABLE IF NOT EXISTS orders (
    id BIGSERIAL PRIMARY KEY,
    start_latitude VARCHAR(255) NOT NULL,
    start_longitude VARCHAR(255) NOT NULL,
    end_latitude VARCHAR(255) NOT NULL,
    end_longitude VARCHAR(255) NOT NULL,
    distance numeric NOT NULL,
    status VARCHAR(255) NOT NULL
);
