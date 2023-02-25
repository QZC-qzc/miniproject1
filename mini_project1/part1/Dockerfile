FROM ubuntu:16.04

USER root

ENV hadoop_path /opt/hadoop
ENV java_path /usr/lib/jvm/java-8-openjdk-amd64

# install packages
RUN \
  apt-get update && apt-get install -y \
  ssh \
  rsync \
  vim \
  openjdk-8-jdk

RUN \
        wget https://archive.apache.org/dist/hadoop/common/hadoop-3.2.1/hadoop-3.2.1.tar.gz && \
        tar -xzf hadoop-3.2.1.tar.gz && \
        mv hadoop-3.2.1 $hadoop_path && \
        echo "export JAVA_HOME=$java_path" >> $hadoop_path/etc/hadoop/hadoop-env.sh && \
        echo "PATH=$PATH:$hadoop_path/bin" >> ~/.bashrc

ADD /*xml $hadoop_path/etc/hadoop/

RUN mkdir -p /opt/hadoop/input
RUN cp /opt/hadoop/etc/hadoop/core-site.xml /opt/hadoop/input/
