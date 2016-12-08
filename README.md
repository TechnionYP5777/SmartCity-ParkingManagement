# SmartCity-Parking Management & Navigation

(https://s24.postimg.org/j36nmvh8l/smart_parking.png) 
Smart Parking system is a dynamic management system of parking slots within the Technion. On every parking slot there will be placed a detection sensor that will update in real time the parking slot status.
Note that a parking slot's status is defined as a tuple of : color , location and taken/not taken.

The system has two type of users : car owners and the security department of the Technion.

Given the location and the customer details, the system will provide location of the nearest available parking slot and will update in real time in case of nearer parking slot became available. The system will present a map of the Technion with mapping of the parking slots and navigates the customer toward the chosen parking slot, in addition there will be a possibility to communicate with other car owners.

The security department of the Technion aims to maximize the occupancy of the parking spaces at the Technion along the clock by dynamically divide the parking areas to the various parking stickers. The system will use various statistics on vehicles at different times and will analyze this data in order to optimize the division of the parking area.
