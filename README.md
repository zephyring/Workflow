# Workflow

A simple workflow engine that orchestrate job executions.

## How to buid
1. ``mvn clean install``
2. ``cd target``
3. ``java -jar workflow-manager-1.0-SNAPSHOT.jar``
4. ``Go to http://localhost:8080/swagger-ui.html``

## Scalability
1. Currently it's an in-memory workflow engine. To scale out, we can add real database implementation for the stores under db package. 
2. To allow individual job to be distributed across different nodes, more job status management is needed.
3. Customized job execution logic can be extended more to make the async executor a cluster-wide executor.
4. Locking might be needed so as to avoid concurrent job execution. MySQL row-level locking is a possible implementation.

## Potential Applications
1. A business process management service that allow user to manager their daily tasks.
2. A job scheduler service for other services to use internally to track job status, especially useful for long-living operation.