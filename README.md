# Задача для инженера по управлению трафиком
Есть летательный аппарат(ЛА) к примеру самолет(Airplane), с характеристиками(AirplaneCharacteristics):<br>
- Максимальная скорость м/c
- Скорость изменения скорости(Максимальное ускорение) м/c^2
- Скорость изменения высоты м/c
- Скорость изменения курса град./с <br>

Нужно получить список точек(TemporaryPoint), которые он пройдет в течении полета между узловыми
точками(WayPoint), заданными оператором. Т.е. построить маршрут полета. В каждую точку ЛА должен пройти
с указанными параметрами. Считаем, что временной интервал между TemporaryPoint 1с <br>

Параметры летательного аппарата (Airplane)<br>
- Id Long
- AirplaneCharacteristics
- TemporaryPoint position
- List<Flight> flights <br>

Параметры полета Flight <br>
- Number Long
- List<WayPoint>
- List<TemporaryPoint> passedPoints <br>

Параметры узловых точек(WayPoint): <br>
- Широта
- Долгота
- Высота пролета м
- Скорость пролета м/с <br>

Параметры точек(TemporaryPoint):<br>
- Широта
- Долгота
- Высота полета м
- Скорость полета м/с
- Курс град.<br>

В данной задаче мы не учитываем кривизну Земли, считаем, что она плоская.
Задачу нужно сделать на Java версии 17, т.к. это наш главный язык разработки на сервере.
Spring Boot 2, Spring Data MongoDb, MongoDb 5, maven сборщик, желательно mongodb поставить через docker,
хотя не принципиально.<br>

Основной метод алгоритма должен быть таким:<br>
List<TemporaryPoint> points = planeCalculation.calculateRoute(AirplaneCharacteristics characteristics,
List<WayPoint> wayPoints);

Каждый ЛА должен быть сохранен в MongoDB.
Запускаем 3 летательных аппарата в полет по точкам, можно использовать любой scheduler. создаем новый
полет и просто обновляем ЛА position и добавляем в список passedpoint полета новую точку. Сохраняем после
каждого обновления ЛА в базу данных
Перед полетом выводим сообщение о предыдущих полетах(Количество и длительность)

----
***Після запуску програми, створюється та запускаються в тестовий політ 3 ЛА. По завершенню програми все відображається в базі даних.***

***Використані технології***
- Java 17.0.5
- Spring boot 2.7.8
- MongoDB v5.0.15
- Apache Maven 3.8.1