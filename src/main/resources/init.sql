-- Table: public.product

-- DROP TABLE IF EXISTS public.product;
CREATE SEQUENCE IF NOT EXISTS product_product_id_seq START WITH 1;
CREATE TABLE IF NOT EXISTS public.product
(
    id bigint NOT NULL DEFAULT nextval('product_product_id_seq'::regclass) PRIMARY KEY,
    description character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    price numeric(19,2) NOT NULL
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.product
    OWNER to postgres;

INSERT INTO public.product (name, description, price)
VALUES (' Clean Code', 'A Handbook of Agile Software Craftsmanship', 44.82),
('Introduction to Algorithms', 'I also like the authors approach to detailing complex algorithms in an accessible way', 29.99);




-- Table: public.customer

-- DROP TABLE IF EXISTS public.customer;
CREATE SEQUENCE IF NOT EXISTS customer_customer_id_seq START WITH 1;
CREATE TABLE IF NOT EXISTS public.customer
(
     id bigint NOT NULL DEFAULT nextval('customer_customer_id_seq'::regclass) PRIMARY KEY,
    email character varying(255) COLLATE pg_catalog."default" NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.customer
    OWNER to postgres;

INSERT INTO public.customer (email, name)
VALUES ('huong.v.bui@dektech.com.au', 'Bui Van Huong'),
('nguyen.v.a@dektech.com.au', 'Nguyen Van A');