# US G001 - As Project Manager, I want the team to follow the technical constraints and concerns of the project.

## 1. Context

This task is being developed to make sure, each member of the team follows the constraints of the project.

## 2. Requirements

G001 - As Project Manager, I want the team to follow the technical constraints and concerns of the project.

**Acceptance Criteria:**

- G001.1. The programming language in which this project is developed is Java;
- G001.2. The development of each US must be reported and will have an available documentation in the "docs" folder alongside a markdown file and, if necessary, diagrams in accordance to the UML notation.
- G001.3. Every US, Class or Method must have a group of relevant automated tests.
- G001.4. The project's code along with its documentation and artifacts must be versioned in a GitHub repository in which only the main branch is used.
- G001.5. The GitHub repository will provide night builds with publishing of results and metrics 
- G001.6. The repository must include the necessary scripts to build and deploy the project in, at least, Linux and Windows, alongside a readme.md file, in the root folder, explaining how to build, deploy and execute the project.
- G001.7. The system can support data persistence either "in memory" or through a relational database, but the final deployment must involve a persistent relational database.
- G001.8. The system must apply authentication and authorization for all of its users and functionalities.
- G001.9. [LPROG] The support for this functionality must follow specific technical requirements, specified in LPROG. The ANTLR tool should be used.
- G001.10. [RCOMP] Functionalities related to the Candidate and Customer Apps as well as the Follow Up Server must follow a client-server architecture, where the client application is used to access the server application and not the relational database. Communications between these two components must follow specific protocols described in a RCOMP document ("Application Protocol").
- G001.11. [RCOMP] The solution should be deployed using several network nodes. It's expected that the relational database server and the Follow Up server are deployed in nodes different from the localhost, preferably in the cloud. The e-mail notification tasks must be done in the background by the Follow Up server.
- G001.12. [SCOMP] The base solution for the upload of files must be made in C, with processes, signals and pipes. Other specific requirements will be provided in SCOMP.
- G001.13. [SCOMP] There must be an alternative solution for the upload of files made in C, using shared memory and semaphores. Other specific requirements will be provided in SCOMP.
- G001.14. [SCOMP] The process to count words of very large files should implement parallelism and concurrency using Java and threads. Other specific requirements will be provided in SCOMP.
- G001.15. [LAPR4] The project has specific requirements related to communication and its own presentation, related to the presentation in the sprint reviews of the LAPR4 TP classes. Further specification of this requirement will be provided in LAPR4.

**Dependencies/References:**

No dependencies nor references stated.

## 3. Analysis

The team implemented this US by following, strictly, the acceptance criteria through the files provided by the client.

## 4. Design

This task doesn't implement a specific functionality to the project, so it isn't possible to represent the design, nor test can be created.

## 5. Implementation

This task doesn't implement a specific functionality to the project, so it isn't possible to represent the implementation.

## 6. Integration/Demonstration

This task doesn't implement a specific functionality to the project, so it isn't possible to demonstrate it nor show how it interacts with the rest of the project.

## 7. Observations

None for this task.