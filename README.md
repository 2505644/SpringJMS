Задача
Реализовать приложение, которое разворачивает Embedded брокер,
а так запускает 2 потока: отправитель и получатель.
Отправитель с некоторой периодичностью генерирует сообщения и отправляет брокер.
Получатель слушает очередь, и при появлении там сообщения
производит запись сообщения в БД через JDBC.
Запись производится в 2 таблицы, в одной тело сообщения, в другой - заголовки.
Связь между таблицами реализовать с помощью внешнего ключа.
Явно запускать потоки не следует.
Настройку, запуск, конфигурирование осуществлять при помощи средств Spring Framework.