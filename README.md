# MD-Code_generator

## Установка

Для использования данного инструмента потребуется установка Java, Kotlin, gcc, а так же clang-format. Убедитесь, что в системе установлена система автоматической сборки проектов Gradle.

Установка Java по ссылке: 
https://www.digitalocean.com/community/tutorials/java-ubuntu-apt-get-ru

Установка Kotlin по ссылке:
https://kotlinlang.org/docs/tutorials/command-line.html

Установка gcc через терминал:
> sudo apt update

> sudo apt -y install gcc

Установка clang-format через терминал:
> sudo apt install clang-format

***

## Запуск

### Запуск системы генерации возможен с помощью IntelliJ IDEA

### Запуск системы генерации через терминал
> ./gradlew jar

> ./gradlew run


Также можно использовать docker образ (убедитесь, что в системе установлен docker):

> newgrp docker

> docker build -t c_syntax_teacher_image .

> docker run -p 8080:8080 -t c_syntax_teacher_image

Сборка проекта таким образом может оказаться долгой.

Для завершения через другой терминал просмотрите запущенные контейнеры

> docker ps

и закройте запущенный контейнер по имени

> docker kill _<container_name>_
