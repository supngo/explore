FROM openjdk:17-jdk-alpine as base
LABEL maintainer="thongo5430@gmail.com"

RUN apk --update --no-cache add curl tar bash procps

ARG MAVEN_VERSION=3.6.3        
ARG USER_HOME_DIR="/root"
ARG BASE_URL=https://apache.osuosl.org/maven/maven-3/${MAVEN_VERSION}/binaries

RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
  && echo "Downlodding maven" \
  && curl -fsSL -o /tmp/apache-maven.tar.gz ${BASE_URL}/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
  \
  && echo "Unziping maven" \
  && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
  \
  && echo "Cleaning and setting links" \
  && rm -f /tmp/apache-maven.tar.gz \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"
CMD ["mvn", "--version"]

FROM base

VOLUME /tmp
EXPOSE 8080

ADD . /
RUN mvn clean package -DskipTests=true
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/target/explore-1.0.0.jar"]