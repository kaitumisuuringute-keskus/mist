[![Build Status](https://ci.hydrosphere.io/buildStatus/icon?job=hydrosphere.io/mist/master)](https://ci.hydrosphere.io/job/hydrosphere.io/job/mist/job/master/)
[![Build Status](https://travis-ci.org/Hydrospheredata/mist.svg?branch=master)](https://travis-ci.org/Hydrospheredata)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.hydrosphere/mist-lib_2.11/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.hydrosphere/mist-lib_2.11/)
[![Docker Hub Pulls](https://img.shields.io/docker/pulls/hydrosphere/mist.svg)](https://img.shields.io/docker/pulls/hydrosphere/mist.svg)
# Hydrosphere Mist

[![Join the chat at https://gitter.im/Hydrospheredata/mist](https://badges.gitter.im/Hydrospheredata/mist.svg)](https://gitter.im/Hydrospheredata/mist?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

## Fork
This fork is mainly created to support Spark 3.0. For other changes, it has:

- Artifact sizes can be up to 1GB
- Is built on Hadoop 3.2, as earlier versions had issues with AWS S3
- Is built having Fargate and AWS ECS in mind

build: `sbt -DscalaVersion=2.12.7 -DsparkVersion=3.0.1 docker`

This fork includes a third variable in case the image built is for a private repo. It's called imagePath and can be used as follows:
`sbt -DscalaVersion=2.12.7 -DsparkVersion=3.0.1 -DimagePath=REPO_NAME_HERE docker`

Docker images:

- kaitumisuuringutekeskus/mist:1.1.3-3.0.1-scala-2.12-hadoop3.2

### Running it locally


First pull the mist image using
`docker pull kaitumisuuringutekeskus/mist:1.1.3-3.0.1-scala-2.12-hadoop3.2`

Then run it using docker:
`docker run -p 2004:2004 -p 4040:4040 -v /var/run/docker.sock:/var/run/docker.sock kaitumisuuringutekeskus/mist:1.1.3-3.0.1-scala-2.12-hadoop3.2`

If the docker instance needs access to localhost urls, use:
`docker run -p 2004:2004 -p 4040:4040 --add-host=host.docker.internal:host-gateway -v /var/run/docker.sock:/var/run/docker.sock kaitumisuuringutekeskus/mist:1.1.3-3.0.1-scala-2.12-hadoop3.2`

Localhost inside the container will be "host.docker.internal"

Now you can connect to the instance on the ip and port `localhost:2004`

## Mist
[Hydrosphere](http://hydrosphere.io) Mist is a serverless proxy for Spark cluster.
Mist provides a new functional programming framework and deployment model for Spark applications. 

Please see our [quick start guide](https://hydrosphere.io/mist-docs/quick_start.html) and [documentation](https://hydrosphere.io/mist-docs/)

Features:
* **Spark Function as a Service**. Deploy Spark functions rather than notebooks or scripts.
* Spark Cluster and Session management. Fully managed Spark sessions backed by on-demand EMR, Hortonworks, Cloudera, DC/OS and vanilla Spark clusters.
* **Typesafe** programming framework that clearly defines inputs and outputs of every Spark job.
* **REST** HTTP & Messaging (MQTT, Kafka) API for Scala & Python Spark jobs.
* Multi-cluster mode: Seamless Spark cluster on-demand provisioning, autoscaling and termination(**pending**)
![Cluster of Spark Clusters](http://dv9c7babquml0.cloudfront.net/docs-images/mist-cluster-of-spark-clusters.gif)

It creates a unified API layer for building enterprise solutions and microservices on top of a Spark functions.

![Mist use cases](http://dv9c7babquml0.cloudfront.net/docs-images/mist-use-case.png)

## High Level Architecture

![High Level Architecture](http://dv9c7babquml0.cloudfront.net/docs-images/mist-highlevel-architecture.png)

## Contact

Please report bugs/problems to: 
<https://github.com/Hydrospheredata/mist/issues>.

<http://hydrosphere.io/>

[LinkedIn](https://www.linkedin.com/company/hydrospherebigdata)

[Facebook](https://www.facebook.com/hydrosphere.io/)

[Twitter](https://twitter.com/hydrospheredata)
