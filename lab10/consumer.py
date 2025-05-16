from kafka import KafkaConsumer
import json
from collections import Counter
import psycopg2

# Настройка подключения к PostgreSQL (находится в Docker)
conn = psycopg2.connect(
    dbname="test_db", user="postgres", password="1234", host="localhost", port=5432
)
cursor = conn.cursor()

# Настройка Kafka-консьюмера
consumer = KafkaConsumer(
    'user_actions',
    bootstrap_servers='localhost:9092',
    value_deserializer=lambda m: json.loads(m.decode('utf-8')),
    group_id='action_group'
)

action_counter = Counter()
print("Слушаю сообщения...")

try:
    for message in consumer:
        try:
            data = message.value

            # Проверка структуры сообщения
            if "user_id" not in data or "action" not in data or "timestamp" not in data:
                raise ValueError("Некорректное сообщение")

            # Обрабатываем только действия типа "purchase"
            if data['action'] == 'purchase':
                action_counter[data['action']] += 1

                print(f"Покупка от пользователя {data['user_id']} в {data['timestamp']}")
                print(f"Статистика: {dict(action_counter)}")

                # Сохраняем сообщение в базу данных
                cursor.execute(
                    "INSERT INTO actions (user_id, action, timestamp) VALUES (%s, %s, %s)",
                    (data['user_id'], data['action'], data['timestamp'])
                )
                conn.commit()

        except Exception as e:
            print(f"[Ошибка] {e} — сообщение можно отправить в DLT (Dead Letter Topic)")

except KeyboardInterrupt:
    print("\nОстановлено пользователем.")
finally:
    cursor.close()
    conn.close()
    print("Соединение с БД закрыто.")
