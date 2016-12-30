<a name="documentr_top"></a>[![Build Status](https://travis-ci.org/synapticloopltd/scaleway-java-api.svg?branch=master)](https://travis-ci.org/synapticloopltd/scaleway-java-api) [![Download](https://api.bintray.com/packages/synapticloop/maven/scaleway-java-api/images/download.svg)](https://bintray.com/synapticloop/maven/scaleway-java-api/_latestVersion) [![GitHub Release](https://img.shields.io/github/release/synapticloopltd/scaleway-java-api.svg)](https://github.com/synapticloopltd/scaleway-java-api/releases) 

> **This project requires JVM version of at least 1.7**






<a name="documentr_heading_0"></a>

# Table of Contents <sup><sup>[top](#documentr_top)</sup></sup>



 - [Table of Contents](#documentr_heading_0)
 - [scaleway-java-api](#documentr_heading_1)
 - [Overview](#documentr_heading_2)
 - [Building the Package](#documentr_heading_3)
   - [*NIX/Mac OS X](#documentr_heading_4)
   - [Windows](#documentr_heading_5)
 - [Running the Tests](#documentr_heading_6)
   - [*NIX/Mac OS X](#documentr_heading_7)
   - [Windows](#documentr_heading_8)
 - [Logging - slf4j](#documentr_heading_9)
   - [Log4j](#documentr_heading_10)
 - [Artefact Publishing - Github](#documentr_heading_15)
 - [Artefact Publishing - Bintray](#documentr_heading_16)
   - [maven setup](#documentr_heading_17)
   - [gradle setup](#documentr_heading_18)
   - [Dependencies - Gradle](#documentr_heading_19)
   - [Dependencies - Maven](#documentr_heading_20)
   - [Dependencies - Downloads](#documentr_heading_21)
 - [License](#documentr_heading_27)






<a name="documentr_heading_1"></a>

# scaleway-java-api <sup><sup>[top](#documentr_top)</sup></sup>



> A java api for the scaleway service





<a name="documentr_heading_2"></a>

# Overview <sup><sup>[top](#documentr_top)</sup></sup>

This is a Java implementation of the Scaleway API which allows you to build and
deploy servers on the Scaleway cloud provider.

a simple example is below:



```
package synapticloop.scaleway.api;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import synapticloop.scaleway.api.exception.ScalewayApiException;
import synapticloop.scaleway.api.model.Image;
import synapticloop.scaleway.api.model.Server;
import synapticloop.scaleway.api.model.ServerAction;
import synapticloop.scaleway.api.model.ServerTask;
import synapticloop.scaleway.api.model.ServerTaskStatus;
import synapticloop.scaleway.api.model.ServerType;
import synapticloop.scaleway.api.model.Volume;

public class Main {
	private static String getUbuntuImage(ScalewayApiClient scalewayApiClient) throws ScalewayApiException {
		for (int i = 1; i < Integer.MAX_VALUE; i++) {
			List<Image> images = scalewayApiClient.getAllImages(i, 100).getImages();
			for (Image image : images) {
				if("Ubuntu Xenial (16.04 latest)".equals(image.getName())) {
					return(image.getId());
				}
			}
		}

		return(null);
	}

	private static String getOrganizationId(ScalewayApiClient scalewayApiClient) throws ScalewayApiException {
		return(scalewayApiClient.getAllOrganizations().get(0).getId());
	}


	public static void main(String[] args) throws ScalewayApiException {
		// you can spin up a VM in either Amsterdam or Paris
		ScalewayApiClient scalewayApiClient = new ScalewayApiClient(System.getenv("YOUR_SCALEWAY_API_TOKEN_GOES_HERE"), Region.PARIS1);

		// a simple creation of a server
		Server server = scalewayApiClient.createServer("This is a test server", 
				getUbuntuImage(scalewayApiClient), 
				getOrganizationId(scalewayApiClient), 
				ServerType.VC1S, 
				new String[] {"lots", "of", "tags"});

		// now that we have created the server (and a volume is also created for it)
		// we need to power it on this may take some time - so we need to wait until
		// it is finished
		ServerTask powerOnServerTask = scalewayApiClient.executeServerAction(server.getId(), ServerAction.POWERON);
		boolean isStarted = false;
		while(!isStarted) {
			ServerTask taskStatus = scalewayApiClient.getTaskStatus(powerOnServerTask.getId());
			System.out.println(String.format("Server task with id '%s' is in current state '%s' (progress '%s')", taskStatus.getId(), taskStatus.getStatus(), taskStatus.getProgress()));
			try {
				Thread.sleep(10000);
			} catch (InterruptedException ex) {
				System.err.println("The sleeping thread was interrupted, continuing...");
			}
			if(taskStatus.getStatus() == ServerTaskStatus.SUCCESS) {
				isStarted = true;
			}
		}

		// now we can power down the server
		ServerTask powerOffServerTask = scalewayApiClient.executeServerAction(server.getId(), ServerAction.POWEROFF);
		boolean isEnded = false;
		while(!isEnded) {
			ServerTask taskStatus = scalewayApiClient.getTaskStatus(powerOffServerTask.getId());
			System.out.println(String.format("Server task with id '%s' is in current state '%s' (progress '%s')", taskStatus.getId(), taskStatus.getStatus(), taskStatus.getProgress()));
			try {
				Thread.sleep(10000);
			} catch (InterruptedException ex) {
				System.err.println("The sleeping thread was interrupted, continuing...");
			}

			if(taskStatus.getStatus() == ServerTaskStatus.SUCCESS) {
				isEnded = true;
			}
		}

		// now delete the server
		scalewayApiClient.deleteServer(server.getId());

		// don't forget to delete any attached volumes
		Map<String, Volume> volumes = server.getVolumes();
		Iterator<String> iterator = volumes.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			Volume volume = volumes.get(key);
			scalewayApiClient.deleteVolume(volume.getId());
		}
	}

}

```







<a name="documentr_heading_3"></a>

# Building the Package <sup><sup>[top](#documentr_top)</sup></sup>



<a name="documentr_heading_4"></a>

## *NIX/Mac OS X <sup><sup>[top](#documentr_top)</sup></sup>

From the root of the project, simply run

`./gradlew build`




<a name="documentr_heading_5"></a>

## Windows <sup><sup>[top](#documentr_top)</sup></sup>

`./gradlew.bat build`


This will compile and assemble the artefacts into the `build/libs/` directory.

Note that this may also run tests (if applicable see the Testing notes)



<a name="documentr_heading_6"></a>

# Running the Tests <sup><sup>[top](#documentr_top)</sup></sup>



<a name="documentr_heading_7"></a>

## *NIX/Mac OS X <sup><sup>[top](#documentr_top)</sup></sup>

From the root of the project, simply run

`gradle --info test`

if you do not have gradle installed, try:

`gradlew --info test`



<a name="documentr_heading_8"></a>

## Windows <sup><sup>[top](#documentr_top)</sup></sup>

From the root of the project, simply run

`gradle --info test`

if you do not have gradle installed, try:

`./gradlew.bat --info test`


The `--info` switch will also output logging for the tests


**WARNING:** These tests make calls against resources (either API calls to a service provider, or consumption of resources from a service provider) which may contribute to your limits, which may lead to a cost.



<a name="documentr_heading_9"></a>

# Logging - slf4j <sup><sup>[top](#documentr_top)</sup></sup>

slf4j is the logging framework used for this project.  In order to set up a logging framework with this project, sample configurations are below:



<a name="documentr_heading_10"></a>

## Log4j <sup><sup>[top](#documentr_top)</sup></sup>


You will need to include dependencies for this - note that the versions may need to be updated.

### Maven



```
<dependency>
	<groupId>org.apache.logging.log4j</groupId>
	<artifactId>log4j-slf4j-impl</artifactId>
	<version>2.5</version>
	<scope>runtime</scope>
</dependency>

<dependency>
	<groupId>org.apache.logging.log4j</groupId>
	<artifactId>log4j-core</artifactId>
	<version>2.5</version>
	<scope>runtime</scope>
</dependency>

```



### Gradle &lt; 2.1



```
dependencies {
	...
	runtime(group: 'org.apache.logging.log4j', name: 'log4j-slf4j-impl', version: '2.5', ext: 'jar')
	runtime(group: 'org.apache.logging.log4j', name: 'log4j-core', version: '2.5', ext: 'jar')
	...
}
```


### Gradle &gt;= 2.1



```
dependencies {
	...
	runtime 'org.apache.logging.log4j:log4j-slf4j-impl:2.5'
	runtime 'org.apache.logging.log4j:log4j-core:2.5'
	...
}
```




### Setting up the logging:

A sample `log4j2.xml` is below:



```
<Configuration status="WARN">
	<Appenders>
		<Console name="Console" target="SYSTEM_OUT">
			<PatternLayout pattern="%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
		</Console>
	</Appenders>
	<Loggers>
		<Root level="trace">
			<AppenderRef ref="Console"/>
		</Root>
	</Loggers>
</Configuration>
```





<a name="documentr_heading_15"></a>

# Artefact Publishing - Github <sup><sup>[top](#documentr_top)</sup></sup>

This project publishes artefacts to [GitHub](https://github.com/)

> Note that the latest version can be found [https://github.com/synapticloopltd/scaleway-java-api/releases](https://github.com/synapticloopltd/scaleway-java-api/releases)

As such, this is not a repository, but a location to download files from.



<a name="documentr_heading_16"></a>

# Artefact Publishing - Bintray <sup><sup>[top](#documentr_top)</sup></sup>

This project publishes artefacts to [bintray](https://bintray.com/)

> Note that the latest version can be found [https://bintray.com/synapticloop/maven/scaleway-java-api/view](https://bintray.com/synapticloop/maven/scaleway-java-api/view)



<a name="documentr_heading_17"></a>

## maven setup <sup><sup>[top](#documentr_top)</sup></sup>

this comes from the jcenter bintray, to set up your repository:



```
<?xml version="1.0" encoding="UTF-8" ?>
<settings xsi:schemaLocation='http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd' xmlns='http://maven.apache.org/SETTINGS/1.0.0' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'>
  <profiles>
    <profile>
      <repositories>
        <repository>
          <snapshots>
            <enabled>false</enabled>
          </snapshots>
          <id>central</id>
          <name>bintray</name>
          <url>http://jcenter.bintray.com</url>
        </repository>
      </repositories>
      <pluginRepositories>
        <pluginRepository>
          <snapshots>
            <enabled>false</enabled>
          </snapshots>
          <id>central</id>
          <name>bintray-plugins</name>
          <url>http://jcenter.bintray.com</url>
        </pluginRepository>
      </pluginRepositories>
      <id>bintray</id>
    </profile>
  </profiles>
  <activeProfiles>
    <activeProfile>bintray</activeProfile>
  </activeProfiles>
</settings>
```





<a name="documentr_heading_18"></a>

## gradle setup <sup><sup>[top](#documentr_top)</sup></sup>

Repository



```
repositories {
	maven {
		url  "http://jcenter.bintray.com" 
	}
}
```



or just



```
repositories {
	jcenter()
}
```





<a name="documentr_heading_19"></a>

## Dependencies - Gradle <sup><sup>[top](#documentr_top)</sup></sup>



```
dependencies {
	runtime(group: 'synapticloopltd', name: 'scaleway-java-api', version: '0.7.0', ext: 'jar')

	compile(group: 'synapticloopltd', name: 'scaleway-java-api', version: '0.7.0', ext: 'jar')
}
```



or, more simply for versions of gradle greater than 2.1



```
dependencies {
	runtime 'synapticloopltd:scaleway-java-api:0.7.0'

	compile 'synapticloopltd:scaleway-java-api:0.7.0'
}
```





<a name="documentr_heading_20"></a>

## Dependencies - Maven <sup><sup>[top](#documentr_top)</sup></sup>



```
<dependency>
	<groupId>synapticloopltd</groupId>
	<artifactId>scaleway-java-api</artifactId>
	<version>0.7.0</version>
	<type>jar</type>
</dependency>
```





<a name="documentr_heading_21"></a>

## Dependencies - Downloads <sup><sup>[top](#documentr_top)</sup></sup>


You will also need to download the following dependencies:



### cobertura dependencies

  - `net.sourceforge.cobertura:cobertura:2.1.1`: (It may be available on one of: [bintray](https://bintray.com/net.sourceforge.cobertura/maven/cobertura/2.1.1/view#files/net.sourceforge.cobertura/cobertura/2.1.1) [mvn central](http://search.maven.org/#artifactdetails|net.sourceforge.cobertura|cobertura|2.1.1|jar))


### compile dependencies

  - `org.apache.httpcomponents:httpclient:4.5.1`: (It may be available on one of: [bintray](https://bintray.com/org.apache.httpcomponents/maven/httpclient/4.5.1/view#files/org.apache.httpcomponents/httpclient/4.5.1) [mvn central](http://search.maven.org/#artifactdetails|org.apache.httpcomponents|httpclient|4.5.1|jar))
  - `commons-io:commons-io:2.4`: (It may be available on one of: [bintray](https://bintray.com/commons-io/maven/commons-io/2.4/view#files/commons-io/commons-io/2.4) [mvn central](http://search.maven.org/#artifactdetails|commons-io|commons-io|2.4|jar))
  - `com.fasterxml.jackson.core:jackson-databind:2.8.5`: (It may be available on one of: [bintray](https://bintray.com/com.fasterxml.jackson.core/maven/jackson-databind/2.8.5/view#files/com.fasterxml.jackson.core/jackson-databind/2.8.5) [mvn central](http://search.maven.org/#artifactdetails|com.fasterxml.jackson.core|jackson-databind|2.8.5|jar))
  - `org.slf4j:slf4j-api:1.7.13`: (It may be available on one of: [bintray](https://bintray.com/org.slf4j/maven/slf4j-api/1.7.13/view#files/org.slf4j/slf4j-api/1.7.13) [mvn central](http://search.maven.org/#artifactdetails|org.slf4j|slf4j-api|1.7.13|jar))


### runtime dependencies

  - `org.apache.httpcomponents:httpclient:4.5.1`: (It may be available on one of: [bintray](https://bintray.com/org.apache.httpcomponents/maven/httpclient/4.5.1/view#files/org.apache.httpcomponents/httpclient/4.5.1) [mvn central](http://search.maven.org/#artifactdetails|org.apache.httpcomponents|httpclient|4.5.1|jar))
  - `commons-io:commons-io:2.4`: (It may be available on one of: [bintray](https://bintray.com/commons-io/maven/commons-io/2.4/view#files/commons-io/commons-io/2.4) [mvn central](http://search.maven.org/#artifactdetails|commons-io|commons-io|2.4|jar))
  - `com.fasterxml.jackson.core:jackson-databind:2.8.5`: (It may be available on one of: [bintray](https://bintray.com/com.fasterxml.jackson.core/maven/jackson-databind/2.8.5/view#files/com.fasterxml.jackson.core/jackson-databind/2.8.5) [mvn central](http://search.maven.org/#artifactdetails|com.fasterxml.jackson.core|jackson-databind|2.8.5|jar))
  - `org.slf4j:slf4j-api:1.7.13`: (It may be available on one of: [bintray](https://bintray.com/org.slf4j/maven/slf4j-api/1.7.13/view#files/org.slf4j/slf4j-api/1.7.13) [mvn central](http://search.maven.org/#artifactdetails|org.slf4j|slf4j-api|1.7.13|jar))


### testCompile dependencies

  - `junit:junit:4.12`: (It may be available on one of: [bintray](https://bintray.com/junit/maven/junit/4.12/view#files/junit/junit/4.12) [mvn central](http://search.maven.org/#artifactdetails|junit|junit|4.12|jar))
  - `org.apache.logging.log4j:log4j-slf4j-impl:2.5`: (It may be available on one of: [bintray](https://bintray.com/org.apache.logging.log4j/maven/log4j-slf4j-impl/2.5/view#files/org.apache.logging.log4j/log4j-slf4j-impl/2.5) [mvn central](http://search.maven.org/#artifactdetails|org.apache.logging.log4j|log4j-slf4j-impl|2.5|jar))
  - `org.apache.logging.log4j:log4j-core:2.5`: (It may be available on one of: [bintray](https://bintray.com/org.apache.logging.log4j/maven/log4j-core/2.5/view#files/org.apache.logging.log4j/log4j-core/2.5) [mvn central](http://search.maven.org/#artifactdetails|org.apache.logging.log4j|log4j-core|2.5|jar))


### testRuntime dependencies

  - `junit:junit:4.12`: (It may be available on one of: [bintray](https://bintray.com/junit/maven/junit/4.12/view#files/junit/junit/4.12) [mvn central](http://search.maven.org/#artifactdetails|junit|junit|4.12|jar))
  - `org.apache.logging.log4j:log4j-slf4j-impl:2.5`: (It may be available on one of: [bintray](https://bintray.com/org.apache.logging.log4j/maven/log4j-slf4j-impl/2.5/view#files/org.apache.logging.log4j/log4j-slf4j-impl/2.5) [mvn central](http://search.maven.org/#artifactdetails|org.apache.logging.log4j|log4j-slf4j-impl|2.5|jar))
  - `org.apache.logging.log4j:log4j-core:2.5`: (It may be available on one of: [bintray](https://bintray.com/org.apache.logging.log4j/maven/log4j-core/2.5/view#files/org.apache.logging.log4j/log4j-core/2.5) [mvn central](http://search.maven.org/#artifactdetails|org.apache.logging.log4j|log4j-core|2.5|jar))

**NOTE:** You may need to download any dependencies of the above dependencies in turn (i.e. the transitive dependencies)



<a name="documentr_heading_27"></a>

# License <sup><sup>[top](#documentr_top)</sup></sup>



```
The MIT License (MIT)

Copyright (c) 2016 synapticloopltd

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```




--

> `This README.md file was hand-crafted with care utilising synapticloop`[`templar`](https://github.com/synapticloop/templar/)`->`[`documentr`](https://github.com/synapticloop/documentr/)

--
