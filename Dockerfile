# Use an official OpenJDK runtime as a parent image
# FROM openjdk:8-jre-slim 
# Use base image (Ubuntu 22.04 + OpenSplice)
FROM blekkk/opensplice-runtime:1.0.0-java8

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container at /app
COPY be-tactical-figure.jar /app/

# Copy your native library to a directory in the container
COPY libs/* /app/lib/
EXPOSE 8082
EXPOSE 8083

# Set the java.library.path to include the directory containing the native library
ENV LD_LIBRARY_PATH=/app/lib:$LD_LIBRARY_PATH

ENTRYPOINT ["java", "-Djava.library.path=/app/lib/", "-jar", "be-tactical-figure.jar"]
# Run the JAR file when the container starts
# CMD ["java", "-Djava.library.path=/app/lib", "-jar", "be-tactical-figure.jar"]