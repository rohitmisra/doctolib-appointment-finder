FROM gradle AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle bootJar --no-daemon

FROM adoptopenjdk/openjdk11:slim
COPY --from=build /home/gradle/src/build/libs/*.jar /app/app.jar

