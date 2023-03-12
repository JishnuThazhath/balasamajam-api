import psycopg2 as ps

db_host = "localhost"
db_name = "balasamajam"
db_port = 5432
db_username = "postgres"
db_password = "postgres"


def get_databse_connection():
    return ps.connect(database=db_name, user=db_username, password=db_password)


def insert_member_record():