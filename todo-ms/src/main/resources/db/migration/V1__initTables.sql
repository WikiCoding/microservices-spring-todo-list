CREATE TABLE IF NOT EXISTS public.todo (
    todoid SERIAL NOT NULL,
    description CHARACTER VARYING(255) NOT NULL,
    completed BOOLEAN NOT NULL,
    version INTEGER NOT NULL,
    PRIMARY KEY (todoid)
);