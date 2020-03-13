FROM tknerr/baseimage-ubuntu:18.04
RUN apt-get update
RUN apt-get -y install default-jdk

#RUN apt -y install unzip
#RUN apt -y install zip
#RUN curl -s https://get.sdkman.io | bash
#RUN sdk install kotlin

#RUN sudo apt -y install snapd
#RUN snap install --classic kotlin

#RUN wget -O sdk.install.sh 'https://get.sdkman.io' --quiet
#RUN bash sdk.install.sh \
#source "/root/.sdkman/bin/sdkman-init.sh"\
#sdk install kotlin \
#sdk install gradle 4.4

RUN kotlinc

RUN apt -y install clang-format
RUN apt-get clean

# Insert here all other commands

ADD ./Code_generate /server
WORKDIR /server
RUN ./gradlew jar
CMD ./gradlew run