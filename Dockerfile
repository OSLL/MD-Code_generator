FROM tknerr/baseimage-ubuntu:18.04
RUN apt-get update
RUN apt-get -y install default-jdk

RUN apt -y install unzip zip
RUN bash -c 'curl -s https://get.sdkman.io | sh'
RUN bash -c 'source /root/.sdkman/bin/sdkman-init.sh && sdk install kotlin'

RUN apt update
RUN apt -y install gcc git

RUN apt -y install clang-format
RUN apt-get clean

ADD ./Code_generate /server
WORKDIR /server
RUN chmod +x ./run.sh
RUN chmod +x ./gradlew
RUN ./gradlew --no-daemon jar
CMD ./gradlew --no-daemon run
