CREATE TABLE items
(
    id          serial PRIMARY KEY,
    description text,
    created     timestamp(0),
    done        bool
);
