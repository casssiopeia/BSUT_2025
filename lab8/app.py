from flask import Flask
import psycopg2

app = Flask(__name__)

def get_db_connection():
    return psycopg2.connect(
        host="db",          # имя сервиса БД (из docker-compose)
        database="testdb",
        user="testuser",
        password="testpass"
    )

@app.route("/")
def index():
    try:
        conn = get_db_connection()
        cur = conn.cursor()
        cur.execute("SELECT NOW();")
        result = cur.fetchone()
        conn.close()
        return f"Соединение с БД успешно! Время: {result[0]}"
    except Exception as e:
        return f"Ошибка подключения к БД: {e}"


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=8080)
