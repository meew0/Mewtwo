FROM       java:8-jdk
MAINTAINER meew0

ADD . /mewtwo-src/
WORKDIR /mewtwo-src/

RUN ./gradlew fatJar copyToLib buildGems

ONBUILD ADD . /mewtwo-modules/
ONBUILD RUN cp -R /mewtwo-src/lib/ /mewtwo-modules/
ONBUILD WORKDIR /mewtwo-modules/

CMD ["/usr/bin/java", "-Dfile.encoding=UTF-8", "-cp", "/mewtwo-src/build/libs/Mewtwo-all.jar", "meew0.mewtwo.MewtwoMain"]
