# Image Convolution Application

Приложение для выполнения свёртки изображений

## Возможности

- Поддержка **различных видов свёртки**:
  - Последовательная реализация
  - Асинхронная по пикселям
  - Асинхронная по строка
  - Асинхронная по столбцам
  - Асинхронная по прямоугольникам

### Требования
- Java 21+
- Gradle 8+

## Установка

```bash
git clone https://github.com/sevenbunu/Convolution.git
cd Convolution
./gradlew build
```

## Флаги
С помощью флагов можно выбирать фильтры, типы свёрток, входные и выходные файлы


| Флаг                  | Трактовка | Возможные значения |
|-------------------------|------------|--------------------------------|
| -i (--input)        | Директория/файл для обработки       | имя директории/файла                        |
| -o (--output)   | Директория/файл для результатов        | имя директории/файла                          |
| -m (--mode)       |     Тип свёртки    | "seq", "par_pixels", "par_rows", "par_cols", "par_rects"                         |
| -b (--batch) |     Количество строк/столбцов для параллельной обработки    | Число > 0                           |
| -h (--height) | Высота прямоугольника для обработки | Число > 0                           |
| -w (--width) | Ширина прямоугольника для обработки | Число > 0                           |
| -f (--filter) | Тип фильтра | "id", "blur3", "blur5", "gblur3", "gblur5", "motion", "edges", "sharpen", "emboss" |
| -p (--pipeline) | Тип обработки директории | "sync", "async" |

## Пример запуска

```bash
./gradlew run --args='-i=69999.png -o=69999.png -m=par_rows -b=10 -f=blur5'
```

### Бенчмарк
Производился над изображением app/src/main/resources/70000.png
CPU: AMD Ryzen 7 7840HS
RAM: 16GB

```
seq           |   96ms ± 9ms
par_pixels     |    1.57s ± 0.2s
par_cols1       |   206ms ± 13ms
par_cols10       |   35ms ± 3ms
par_cols100        |  33ms ± 4ms
par_rows1        |  45ms ± 4ms
par_rows10      |  43ms ± 2ms
par_rows100      |   38ms ± 4ms
par_rects1x1      |   1.1s ± 0.1ms
par_rects10x10    |  35ms ± 5ms
par_rects100x100    |  32ms ± 3ms
```
