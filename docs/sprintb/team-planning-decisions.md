# Team Planning Decisions

In this file, the team registers decisions made during meetings and records interactions that may affect the content
of the project, for example: shared feedback, shift of work focus and help shared between peers.

## Project Decisions

### Task Division

The team divided every task equally between each member, while leaving 2 USs that weren't possible to equally atribute to each element.

To compensate, special tasks that were required to be done, but not considered USs, were created, being distributed in the following manner:

- **Framework Implementation and G007** - *Alfredo Ferreira*
- **Connection to the H2 database** - *Tiago Silva*
- **Team management related tasks (documentation and gitHub management)** - *Ricardo Dias*
- **Global documentation** - *João Botelho* 
- **US 2001 [SCOMP US]** - *Globally assigned US*

### Base-Project and Framework

The team decided to follow the guide-lines from the EAPLI PL teacher and implement the Base-Project and its Framework.

### Connections between Users

Throughout the project, there are several examples that include connections between entities like CustomerManager - Customer and Customer - JobOffer.

To make this connections we agreed that **if an entity is a SystemUser, then that entity can't have the connection registered in their object.** It will **always** be the other entity, since we have full control of it.

The thought process is registered in the **26/04/2024 Meeting** and the main reasoning is the prevention of framework changes, which isn't allowed.

### Plugins Decision

Although it doesn't affect the program in a high scale, there is a clear presence of external plugins that are configured within the application. 
Here we document the main decision while handling this implementations:

- The value object PluginJarFile will contain the name of the file and not the path of it.
  - Since we need to store the file in some way on the program, we decided to implement a variable that has the plugins' directory file path and store only the name of each plugin file.
  This is only possible since the path for all the plugins files is the same for everyone.
  - This allows us to change the directory of the plugins and not need to change the path for each individual plugin.

### Non-specified Object Restrictions

There are many attributes for certain objects that don't present a specific restriction on the project description and that are too insignificant to ask the client.
This decisions will be recorded here to make it easier to know which restrictions were made and what needs to be changed.

- **CustomerName -** Only allows letters and has to start with a capital one.
- **CustomerCode -** 10 characters limit (requested by the client) and can only be numbers or capital letters.
- **PhoneNumber -** 9 digits limit and has to start with a 9.
- **FilePath -** Must be in the format of a directory (dir/.../).

## Meetings

> **15/04/2024 18h30**
> 
> In this meeting, we read each US and discussed the best way to divide them. We were able to do this equally between members, except the G07.
> 
> This remained assigned to the member who already was trying to implement it, as well as the framework implementation - **Alfredo Ferreira**.
> 
> Additional off-record task (required task that aren't USs) were:
> 
> - Connection to the H2 database - **Tiago Silva**
> - Team management related tasks (documentation and gitHub management) - **Ricardo Dias**
> - Global documentation - **João Botelho**
> 
> We also updated the gitHub to accomodate this decisions and each task assigned.

> **26/04/2024 15h30**
> 
> In this meeting, we tackled a common issue of connecting users and objects. 
> 
> The example used was the CustomerManager's creation of Customer (Entities/Companies) - When a CustomerManager creates a Customer, that entity should be connected to the creator in some way.
> The main 2 options debated were:
> - **Creation of a new type of user (CustomerManager)** - This new user would be a child of the current SystemUser, that would have an extra attribute: a list of its created Customers.
>   - **Advantages:** This would facilitate the search and taking action on the Customers connected to the CustomerManager.
>   - **Disadvantages:** This would require the creation of a new type of user for every single user that had a connection to another entity. Doing this would defeat the whole purpose of the attribute "Role" and we can't change the SystemUser attributes to remove the redundancy (since it's from the framework).
> - **Creation of a specific attribute** - This would add a specific attribute to the Customer that would link it to its CustomerManager (an attribute with the object of a SystemUser).
>   - **Advantages:** Since the "Customer" entity is already a new concept created for this US, we have full control of its attributes. This way, we wouldn't change the base Framework and still remain the connection required.
>   - **Disadvantages:** The search and taking action from the CustomerManager would be slower and more expensive usage of the CPU, since the process would require to get all the Customers and searching for the ones which have the CustomerManager information (for example: the same email).
> 
> We decided to follow the second approach since it wouldn't require to change the original framework. Both options were possible and allowed by the teachers, but we agreed the disadvantage of the second option was better to handle than the first one's.

> **30/04/2024 18h00** 
> 
> In this meeting, we tackled issues related to the LPROG and SCOMP tasks.
> ***
> The first topic talked was what the US1008 required to do. It was discussed that, although the main objective is not hard to achieve, **the answers that the client provided for that task implied the implementation of several new smaller tasks that would take a long time to individually complete.**
> 
> The US in itself only required the configuration of plugins, however, the client didn't provide any plugin to configure. In the answers, he stated that they had to be **implemented by ourselves and that they would be grammars**. He also required 4 different plugins for the demonstration.
> 
> With that said, we also noticed that none of the other LPROG USs had a portion for the creation of a grammar. **This creation was out-of-scope, which means no one was tasked to do it**. So, we divided it between ourselves. Since there are 4 grammars required to be done, each one of us was tasked to do one.
> ***
> The second topic was about the SCOMP US. Although this was already a discussed topic, we had reached an awkward position in terms of **how to divide this global task**.
> 
> The task wasn't hard to complete, but it would take sometime. The division into 5 members (this US had the extra member) was **slowing down the execution of the task**, since we had to effectively wait for each member to complete the work before them. 
> 
> So we **reorganized the task division**, making it easier to know what each one of us needed to do and also allowing a better communication between consecutive parts.
> ***
> Finally, we also discussed some global decision, that were written on this file.

## Team Logs

> **5/04/2024**
>
> During the EAPLI class, the team agreed to implement the base-project and the framework recommended by the PL teacher.

> **19/04/2024**
> 
> The teacher recommended to leave the connection to the H2 database for last, since it is a low priority task and working on it might slow down the 
> coding process (thanks to connection delays and possible issues).