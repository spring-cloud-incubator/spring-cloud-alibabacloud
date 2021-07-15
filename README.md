# Spring Cloud Alibaba

[![CircleCI](https://circleci.com/gh/alibaba/spring-cloud-alibaba/tree/master.svg?style=svg)](https://circleci.com/gh/alibaba/spring-cloud-alibaba/tree/master)
[![Financial Contributors on Open Collective](https://opencollective.com/spring-cloud-alibaba/all/badge.svg?label=financial+contributors)](https://opencollective.com/spring-cloud-alibaba) [![Maven Central](https://img.shields.io/maven-central/v/com.alibaba.cloud/spring-cloud-alibaba-dependencies.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:com.alibaba.cloud%20AND%20a:spring-cloud-alibaba-dependencies)
[![Codecov](https://codecov.io/gh/alibaba/spring-cloud-alibaba/branch/master/graph/badge.svg)](https://codecov.io/gh/alibaba/spring-cloud-alibaba)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

A project maintained by Alibaba.

See the [中文文档](https://github.com/alibaba/spring-cloud-alibaba/blob/master/README-zh.md) for Chinese readme.

Spring Cloud Alibaba provides a one-stop solution for distributed application development. It contains all the components required to develop distributed applications, making it easy for you to develop your applications using Spring Cloud.

With Spring Cloud Alibaba, you only need to add some annotations and a small amount of configurations to connect Spring Cloud applications to the distributed solutions of Alibaba, and build a distributed application system with Alibaba middleware.


## Features

* **Flow control and service degradation**：Flow control for HTTP services is supported by default. You can also customize flow control and service degradation rules using annotations. The rules can be changed dynamically.
* **Service registration and discovery**：Service can be registered and clients can discover the instances using Spring-managed beans, auto integration Ribbon.
* **Distributed configuration**：support for externalized configuration in a distributed system, auto refresh when configuration changes.
* **Event-driven**：support for building highly scalable event-driven microservices connected with shared messaging systems.
* **Distributed Transaction**：support for distributed transaction solution with high performance and ease of use.
* **Alibaba Cloud Object Storage**：massive, secure, low-cost, and highly reliable cloud storage services. Support for storing and accessing any type of data in any application, anytime, anywhere.
* **Alibaba Cloud SchedulerX**：accurate, highly reliable, and highly available scheduled job scheduling services with response time within seconds.
* **Alibaba Cloud SMS**： A messaging service that covers the globe, Alibaba SMS provides convenient, efficient, and intelligent communication capabilities that help businesses quickly contact their customers.

For more features, please refer to [Roadmap](https://github.com/alibaba/spring-cloud-alibaba/blob/master/Roadmap.md).


## Components

**[Sentinel](https://github.com/alibaba/Sentinel)**: Sentinel takes "traffic flow" as the breakthrough point, and provides solutions in areas such as flow control, concurrency, circuit breaking, and load protection to protect service stability.

**[Nacos](https://github.com/alibaba/Nacos)**: An easy-to-use dynamic service discovery, configuration and service management platform for building cloud native applications.

**[RocketMQ](https://rocketmq.apache.org/)**：A distributed messaging and streaming platform with low latency, high performance and reliability, trillion-level capacity and flexible scalability.

**[Dubbo](https://github.com/apache/dubbo)**：A high-performance, Java based open source RPC framework.

**[Seata](https://github.com/seata/seata)**：A distributed transaction solution with high performance and ease of use for microservices architecture.

**[Alibaba Cloud ACM](https://www.aliyun.com/product/acm)**：An application configuration center that enables you to centralize the management of application configurations, and accomplish real-time configuration push in a distributed environment.

**[Alibaba Cloud OSS](https://www.aliyun.com/product/oss)**: An encrypted and secure cloud storage service which stores, processes and accesses massive amounts of data from anywhere in the world.

**[Alibaba Cloud SMS](https://www.aliyun.com/product/sms)**: A messaging service that covers the globe, Alibaba SMS provides convenient, efficient, and intelligent communication capabilities that help businesses quickly contact their customers.

**[Alibaba Cloud SchedulerX](https://www.aliyun.com/aliware/schedulerx?spm=5176.10695662.784137.1.4b07363dej23L3)**:accurate, highly reliable, and highly available scheduled job scheduling services with response time within seconds..

For more features please refer to [Roadmap](https://github.com/alibaba/spring-cloud-alibaba/blob/master/Roadmap.md).

## How to build
* **2020.0 branch**: Corresponds to Spring Cloud 2020 & Spring Boot 2.4.x. JDK 1.8 or later versions are supported.
* **master branch**: Corresponds to Spring Cloud Hoxton & Spring Boot 2.2.x. JDK 1.8 or later versions are supported.
* **greenwich branch**: Corresponds to Spring Cloud Greenwich & Spring Boot 2.1.x. JDK 1.8 or later versions are supported.
* **finchley branch**: Corresponds to Spring Cloud Finchley & Spring Boot 2.0.x. JDK 1.8 or later versions are supported.
* **1.x branch**: Corresponds to Spring Cloud Edgware & Spring Boot 1.x, JDK 1.7 or later versions are supported.

Spring Cloud uses Maven for most build-related activities, and you should be able to get off the ground quite quickly by cloning the project you are interested in and typing:
```bash
	./mvnw install
```

## How to Use

### Add maven dependency 

These artifacts are available from Maven Central and Spring Release repository via BOM:
```xml
	<dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                <version>2.2.5.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
```
add the module in  `dependencies`.


### Reference Doc

[Contents](https://github.com/alibaba/spring-cloud-alibaba/blob/master/spring-cloud-alibaba-docs/src/main/asciidoc-zh/spring-cloud-alibaba.adoc)

[Nacos Config](https://github.com/alibaba/spring-cloud-alibaba/blob/master/spring-cloud-alibaba-docs/src/main/asciidoc-zh/nacos-config.adoc)

[Nacos Discovery](https://github.com/alibaba/spring-cloud-alibaba/blob/master/spring-cloud-alibaba-docs/src/main/asciidoc-zh/nacos-discovery.adoc)


## Examples

A `spring-cloud-alibaba-examples` module is included in our project for you to get started with Spring Cloud Alibaba quickly. It contains an example, and you can refer to the readme file in the example project for a quick walkthrough.

Examples：

[Sentinel Example](https://github.com/alibaba/spring-cloud-alibaba/tree/master/spring-cloud-alibaba-examples/sentinel-example/sentinel-core-example/readme.md)

[Nacos Config Example](https://github.com/alibaba/spring-cloud-alibaba/blob/master/spring-cloud-alibaba-examples/nacos-example/nacos-config-example/readme.md)

[Nacos Discovery Example](https://github.com/alibaba/spring-cloud-alibaba/blob/master/spring-cloud-alibaba-examples/nacos-example/nacos-discovery-example/readme.md)

[RocketMQ Example](https://github.com/alibaba/spring-cloud-alibaba/blob/master/spring-cloud-alibaba-examples/rocketmq-example/readme.md)

[Alibaba Cloud OSS Example](https://github.com/alibaba/aliyun-spring-boot/tree/master/aliyun-spring-boot-samples/aliyun-oss-spring-boot-sample)

[Dubbo Spring Cloud Example](https://github.com/alibaba/spring-cloud-alibaba/blob/master/spring-cloud-alibaba-examples/spring-cloud-alibaba-dubbo-examples/README_CN.md)

## Version control guidelines
The version number of the project is in the form of x.x.x, where x is a number, starting from 0, and is not limited to the range 0~9. When the project is in the incubator phase, the version number is 0.x.x.

As the interfaces and annotations of Spring Boot 1 and Spring Boot 2 have been changed significantly in the Actuator module, and spring-cloud-commons is also changed quite a lot from 1.x.x to 2.0.0, we take the same version rule as SpringBoot version number.

* 1.5.x for Spring Boot 1.5.x
* 2.0.x for Spring Boot 2.0.x
* 2.1.x for Spring Boot 2.1.x
* 2.2.x for Spring Boot 2.2.x
* 2020.x for Spring Boot 2.4.x

## Code of Conduct
This project is a sub-project of Spring Cloud, it adheres to the Contributor Covenant [code of conduct](https://github.com/spring-cloud/spring-cloud-build/blob/master/docs/src/main/asciidoc/code-of-conduct.adoc). By participating, you are expected to uphold this code. Please report unacceptable behavior to spring-code-of-conduct@pivotal.io.

## Code Conventions and Housekeeping
None of these is essential for a pull request, but they will all help. They can also be added after the original pull request but before a merge.

Use the Spring Framework code format conventions. If you use Eclipse you can import formatter settings using the eclipse-code-formatter.xml file from the Spring Cloud Build project. If using IntelliJ, you can use the Eclipse Code Formatter Plugin to import the same file.

Make sure all new .java files to have a simple Javadoc class comment with at least an @author tag identifying you, and preferably at least a paragraph on what the class is for.

Add the ASF license header comment to all new .java files (copy from existing files in the project)

Add yourself as an @author to the .java files that you modify substantially (more than cosmetic changes).

Add some Javadocs and, if you change the namespace, some XSD doc elements.

A few unit tests would help a lot as well — someone has to do it.

If no-one else is using your branch, please rebase it against the current master (or other target branch in the main project).

When writing a commit message please follow these conventions, if you are fixing an existing issue please add Fixes gh-XXXX at the end of the commit message (where XXXX is the issue number).

## Contact Us
Mailing list is recommended for discussing almost anything related to spring-cloud-alibaba. 

spring-cloud-alibaba@googlegroups.com:You can ask questions here if you encounter any problem when using or developing spring-cloud-alibaba.

## Contributors

### Code Contributors

This project exists thanks to all the people who contribute. [[Contribute](CONTRIBUTING.md)].
<a href="https://github.com/alibaba/spring-cloud-alibaba/graphs/contributors"><img src="https://opencollective.com/spring-cloud-alibaba/contributors.svg?width=890&button=false" /></a>

### Financial Contributors

Become a financial contributor and help us sustain our community. [[Contribute](https://opencollective.com/spring-cloud-alibaba/contribute)]

#### Individuals

<a href="https://opencollective.com/spring-cloud-alibaba"><img src="https://opencollective.com/spring-cloud-alibaba/individuals.svg?width=890"></a>

#### Organizations

Support this project with your organization. Your logo will show up here with a link to your website. [[Contribute](https://opencollective.com/spring-cloud-alibaba/contribute)]

<a href="https://opencollective.com/spring-cloud-alibaba/organization/0/website"><img src="https://opencollective.com/spring-cloud-alibaba/organization/0/avatar.svg"></a>
<a href="https://opencollective.com/spring-cloud-alibaba/organization/1/website"><img src="https://opencollective.com/spring-cloud-alibaba/organization/1/avatar.svg"></a>
<a href="https://opencollective.com/spring-cloud-alibaba/organization/2/website"><img src="https://opencollective.com/spring-cloud-alibaba/organization/2/avatar.svg"></a>
<a href="https://opencollective.com/spring-cloud-alibaba/organization/3/website"><img src="https://opencollective.com/spring-cloud-alibaba/organization/3/avatar.svg"></a>
<a href="https://opencollective.com/spring-cloud-alibaba/organization/4/website"><img src="https://opencollective.com/spring-cloud-alibaba/organization/4/avatar.svg"></a>
<a href="https://opencollective.com/spring-cloud-alibaba/organization/5/website"><img src="https://opencollective.com/spring-cloud-alibaba/organization/5/avatar.svg"></a>
<a href="https://opencollective.com/spring-cloud-alibaba/organization/6/website"><img src="https://opencollective.com/spring-cloud-alibaba/organization/6/avatar.svg"></a>
<a href="https://opencollective.com/spring-cloud-alibaba/organization/7/website"><img src="https://opencollective.com/spring-cloud-alibaba/organization/7/avatar.svg"></a>
<a href="https://opencollective.com/spring-cloud-alibaba/organization/8/website"><img src="https://opencollective.com/spring-cloud-alibaba/organization/8/avatar.svg"></a>
<a href="https://opencollective.com/spring-cloud-alibaba/organization/9/website"><img src="https://opencollective.com/spring-cloud-alibaba/organization/9/avatar.svg"></a>
