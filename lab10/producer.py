from kafka import KafkaProducer
import json
from datetime import datetime

producer = KafkaProducer(
    bootstrap_servers='localhost:9092',
    value_serializer=lambda v: json.dumps(v).encode('utf-8')
)

while True:
    user_id = input("Введите user_id: ")
    action = input("Введите действие (например, 'purchase'): ")

    message = {
        "user_id": int(user_id),
        "action": action,
        "timestamp": datetime.now().isoformat()
    }

    producer.send('user_actions', message)
    print(f"[OK] Отправлено: {message}")
