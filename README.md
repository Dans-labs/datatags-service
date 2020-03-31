# DataTags Service

##### EOSC-Hub 

EOSC-hub is the largest implementation project of the European Open Science Cloud (EOSC) initiative. It is a central contact point for European researchers and innovators 
to discover, access, use and reuse a broad spectrum of resources for advanced data-driven research. 

The consortium of 100 partners from more than 50 countries will develop the vision of the Hub as the integration and 
management system of the future European Open Science Cloud. EOSC-hub is the largest implementation project of the European Open Science Cloud (EOSC) initiative. 
It is a central contact point for European researchers and innovators 
to discover, access, use and reuse a broad spectrum of resources for advanced data-driven research. 

#### WP2

The EOSC-Hub WP2 concerns development of DataTags as a service for data support to the EOSC-hub.

## Overview

The service is developed in a generic way; therefore it can be used by any applications that support REST API.

In the sections below more details are provided about:

*	[Architecture](#datatags-architecture)
*   [Open API 3.0 Spec](https://github.com/swagger-api/swagger-core)
*   [Frameworks]()
    *   [swagger-codegen](https://github.com/swagger-api/swagger-codegen)
    *   [Spring Boot](https://spring.io/projects/spring-boot)
    *   [Spring WebFlow](https://docs.spring.io/spring-webflow/docs/current/reference/htmlsingle/)
*   [DataTags Schema](#datatags-schema)
    *   [DANS DataTags Schema](#datatags-schema-dans)
*   [DataTags Schema Creator(GUI)](#datatags-schema-creator)
    *   [Creating DataTags Schema using Zinktree](https://zingtree.com/)
    *   [Creating DataTags Schema using yEd Graph Editor](https://www.yworks.com/products/yed)
*   [Push Plugins]()
*   [Dockerizing the DataTags Service](#datatags-service-docker)
*   [Deploying, Running and Using DataTags Service on Kubernetes](#datatags-kubernetes)
*   [Connecting to DataTags Service](#datatags-connection)
    *   [Dataverse](#datatags-dataverse)
    *   [B2Share](#datatags-b2share)
    *	[Your own application](#datatags-apps)
*   [iRODS Rules Output](https://irods.org/)

### <a name="datatags-architecture"></a>DataTags Architecture
![DataTagse-Architecture](readme-imgs/Datatags-Architecture.png "DataTags Service")

### Open API 3.0 Specification

### Frameworks

### <a name="datatags-schema-dans"></a>DANS DataTags Schema
![DANS DataTags Schema](readme-imgs/Datatags-2nd-prototype.jpg "DANS DataTags Schema")
![DANS DataTags UI](readme-imgs/DANS-DataTags-Schema.png "DANS DataTags UI")



### <a name="datatags-schema-creator-zinktree"></a>Zinktree Visual Designer

![Zinktree DataTags Schema](readme-imgs/zinktree-visual-designer.png "Zingtree DataTags Schema Creator")

### <a name="datatags-schema-creator-yed"></a>yEd Graph Editor

![Zinktree DataTags Schema](readme-imgs/yed-designer.png "Zingtree DataTags Schema Creator")

### <a name="datatags-connection"></a>Connection to DataTag Service

### <a name="datatags-dataverse"></a>Connection to DataTag Service
        {
            "dataTagServiceUrl": "http://localhost:8888/dans/v1", 
            "encryptKey": "@km1-10Dec04",
            "validity-duration": 600   
        }
 
 dataTagServiceUrl = is the        