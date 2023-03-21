# Drone Delivery Service

This is a solution for the Drone Delivery Service problem, which involves assigning delivery locations to drones in the most efficient way possible.

## Problem Description

The problem involves assigning delivery locations to drones, where each drone has a maximum weight capacity and each delivery location has a weight. The goal is to assign delivery locations to drones in the most efficient way possible, such that the total number of trips taken by all drones is minimized.

## Solution Overview

The solution involves sorting the list of delivery locations in ascending order based on their weight, and sorting the list of drones in descending order based on their maximum weight capacity. Then, delivery locations are assigned to each drone until all locations are assigned.

For each drone, a list of delivery locations is maintained until the total weight of the delivery locations in the list is greater than or equal to the maximum weight capacity of the drone. At that point, the list is added to the drone's list of trips, and a new list is started for the next set of delivery locations.

## Algorithm
The algorithm used in the [DroneDeliveryService](domain/src/main/java/org/drone/delivery/service/DroneDeliveryServiceImpl.java) is a greedy algorithm that assigns delivery locations to drones based on their weight capacity. The algorithm sorts the delivery locations list in ascending order based on their weight, and the drones list in descending order based on their maximum weight capacity. Then, it assigns delivery locations to each drone until all locations are assigned. For each drone, the algorithm creates a list of delivery locations that can be delivered by that drone based on its weight capacity, and adds it to the drone's trips list. The implementation of the algorithm uses nested loops and conditional statements to perform the assignment of the delivery locations to drones.

## Approach
The approach to developing a solution for the Drone Delivery Service problem involves formulating the problem as an optimization problem, where the objective is to minimize the number of trips made by the drones while satisfying the weight capacity constraints. The problem can be modeled as an instance of the Multiple Knapsack Problem (MKP), which is a well-known combinatorial optimization problem.

To solve the problem, various optimization algorithms and heuristics can be applied, such as dynamic programming, branch and bound, and greedy algorithms. In the case of the DroneDeliveryServiceImpl, the approach taken is a heuristic-based algorithm that uses a greedy strategy to assign delivery locations to drones based on their weight capacity.

In general, if the input data is relatively small and the solution quality is not critical, a greedy algorithm like the one used in the DroneDeliveryServiceImpl can provide a simple and fast solution. Greedy algorithms are usually easier to implement and faster than more complex algorithms like the MKP, and can provide reasonable solutions in many cases. However, greedy algorithms do not guarantee an optimal solution, and their performance can degrade quickly as the input data size increases or the problem constraints become more complex.

The problem statement provides some assumptions, such as assuming that time and distance to each location do not matter, and that the size of each package is irrelevant. Additionally, the problem assumes that the cost to refuel and restock each drone is a constant and does not vary between drones. However, the specific assumptions and constraints may vary depending on the implementation and the requirements of the problem.

## Solution Implementation

The solution is implemented in Java and consists of three classes: Drone, Location, and DroneDeliveryService.

### Drone

The [Drone](domain/src/main/java/org/drone/delivery/domain/Drone.java) class represents a drone, and has the following attributes:

- `name` (String): The name of the drone.
- `maxWeight` (int): The maximum weight capacity of the drone.
- `trips` (List<List<Location>>): A list of trips, where each trip is a list of delivery locations.

### Location

The [Location](domain/src/main/java/org/drone/delivery/domain/Location.java) class represents a delivery location, and has the following attributes:

- `name` (String): The name of the delivery location.
- `weight` (int): The weight of the delivery location.

### DroneDeliveryService

The [DroneDeliveryService](domain/src/main/java/org/drone/delivery/service/DroneDeliveryServiceImpl.java) class is the main class that contains the solution algorithm. It has the following methods:

- `assignDeliveries(List<Drone> drones, List<Location> locations)`: Assigns delivery locations to drones in the most efficient way possible. It takes two arguments: a list of drones and a list of delivery locations.
- `getTotalTrips(List<Drone> drones)`: Calculates the total number of trips taken by all drones.
- `printResults(List<Drone> drones)`: Prints the results of the delivery assignments.

## How to Use

To use the solution, simply create a list of drones and a list of delivery locations, and pass them to the `assignDeliveries()` method of the DroneDeliveryService class. Then, call the `printResults()` method to print the results of the delivery assignments.

Here is an example of how to use the solution:
1. Open the project folder.
2. Navigate to bootstrap folder.
3. Run the command line:
```sh
./gradlew runApp --args='[INPUT_FILE] debug'
```
4. Sample:
```sh
./gradlew runApp --args='C:\git\java\drone-delivery\bootstrap\src\main\resources\01.txt debug'
```

- The debug argument is optional. If you set the parameter debug, the output will be printed on the terminal instead of the file.

- The debug argument is optional. If you include the word debug as a command-line argument, the output will be printed to the terminal instead of being written to a file.
If you do not include the debug argument, the output will be written to a file with a name that follows this pattern: output_[YYYY.MM.DD.HH.mm.ss.txt]. The output file will be generated in the same folder as the input file.

## Complexity
The space complexity of the solution algorithm is O(n), where n is the number of delivery locations. This is because the algorithm maintains a list of trips for each drone, which can have a worst-case size of n.

## Time Complexity
The time complexity of the solution algorithm is O(n log n), where n is the number of delivery locations. This is because the algorithm involves sorting the list of delivery locations and the list of drones, which have a worst-case time complexity of O(n log n) each. Then, delivery locations are assigned to each drone in a single pass through the sorted lists, which has a time complexity of O(n).

The sort() method used to sort the lists of locations and drones is implemented using a variation of the quicksort algorithm, which has an average-case time complexity of O(n log n) and a worst-case time complexity of O(n^2).

The nested loop used to assign delivery locations to drones has a worst-case time complexity of O(n^2), but this worst case is reached only when all delivery locations have the same weight and each drone can carry only one delivery at a time. In practice, the distribution of delivery weights and drone capacities is likely to be more diverse, which results in a more efficient allocation of deliveries.

The time complexity of the solution algorithm is dominated by the sorting operations, which have a time complexity of O(n log n). The algorithm is efficient for moderate-sized inputs and can handle large inputs as well, although the sorting operations may become a bottleneck for very large inputs.

## Architecture
In the hexagonal architecture, the layers are organized around the business logic and not around the technical infrastructure. This allows the system to be more modular, testable, and flexible, as each layer can be developed and tested independently of the others. The architecture also promotes the separation of concerns, as each layer is responsible for a specific set of tasks and does not depend on the details of the other layers.
### Application Layer
The application layer is responsible for coordinating the interaction between the user and the system. This layer contains the use cases or business logic that represents the behavior of the system. It interacts with the domain layer to execute the business logic and provides the necessary interfaces to allow users to interact with the system.

### Domain Layer
The domain layer contains the core business logic of the system. This layer represents the real-world objects, entities, and rules that govern the behavior of the system. The domain layer is the most important layer in the hexagonal architecture because it defines the core functionality of the system.

### Infrastructure Layer
The infrastructure layer provides the technical infrastructure necessary to support the application and domain layers. This layer contains the code that interacts with external systems, such as databases, web services, or other applications. It also contains the code that implements the necessary adapters to allow the application and domain layers to communicate with each other.

### Bootstrap Layer
The bootstrap layer is responsible for bootstrapping the application and wiring all the layers together. It provides the entry point into the system and initializes the necessary components to start the application.

## Tests
| # | Layer          | Class                                                |
|--|----------------|-------------------------------------------------------|
| 1 | Domain        | [DroneDeliveryServiceImplTest](domain/src/test/java/org/drone/delivery/domain/test/DroneDeliveryServiceImplTest.java) | 
| 2 | Domain        | [DroneTest](domain/src/test/java/org/drone/delivery/domain/test/DroneTest.java) |
| 3 | Domain        | [InputDataTest](domain/src/test/java/org/drone/delivery/domain/test/InputDataTest.java) |  
| 4 | Domain        | [InputDataTest](domain/src/test/java/org/drone/delivery/domain/test/LocationTest.java) |  
| 5 | Infrastructure        | [InputFileParserImpl](infrastructure/src/test/java/org/drone/delivery/infrastructure/test/InputFileParserImplTest.java) |
| 6 | Infrastructure        | [GreedyDroneDeliveryServiceTest](infrastructure/src/test/java/research/selected/algorithm/greedy/GreedyDroneDeliveryServiceTest.java) |  
| 7 | Infrastructure        | [Research](infrastructure/src/test/java/research) |  

## Libs
I will not be using frameworks such as Spring Framework, Quarkus, or Micronaut due to the size of the system. However, the architecture is designed to be easily compatible with any of these frameworks.
| # | Lib          | Reason | |
|--|----------------|----------------|----------------|
| 1 | Domain        | org.junit.jupiter:junit-jupiter-api:5.9.2 | Unit Tests |
| 2 | Domain        | org.junit.jupiter:junit-jupiter-engine:5.9.2 |Unit Tests |
| 3 | Domain        | org.mockito:mockito-core:5.2.0 | Unit Tests |
| 4 | Bootstrap        | org.junit.jupiter:junit-jupiter-api:5.9.2 | Unit Tests |
| 5 | Bootstrap        | bootstrap:org.junit.jupiter:junit-jupiter-engine:5.9.2 | Unit Tests |
| 6 | Infrastructure        | org.junit.jupiter:junit-jupiter-api:5.9.2 | Unit Tests |
| 5 | Infrastructure        | org.junit.jupiter:junit-jupiter-engine:5.9.2 | Unit Tests |
| 5 | Infrastructure        | org.mockito:mockito-core:5.2.0 | Unit Tests |
| 5 | Infrastructure        | org.mockito:mockito-core:5.2.0 | Unit Tests |