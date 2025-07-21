# SwiftCart - Concurrent Programming Assignment

---
## Case Study
I have been assigned to **simulate the operations of SwiftCart**, a highly automated e-
commerce centre that **handles online orders** for a major shopping platform.

## Basic Requirements
SwiftCart is in Selangor and is built to operate with minimal human intervention. It consists of
the following *six key sections*:
1. **Order Intake System**
   - Orders arrive from the online platform at a rate of 1 order every 500ms.
   - Each order is verified for payment, inventory availability, and shipping address.
2. **Picking Station**
   - Robotic arms pick items from shelves and place them into order bins.
   - Up to 4 orders can be picked at a time.
   - Orders are verified for missing items.
3. **Packing Station**
   - Completed bins are packed into shipping boxes (1 order at a time).
   - A scanner checks each box to ensure contents match the order.
4. **Labelling Station**
   - Each box is assigned a shipping label with destination and tracking.
   - Boxes pass through a quality scanner (1 at a time).
5. **Sorting Area**
   - Boxes are sorted into batches of 6 boxes based on regional zones.
   - Batches are loaded into transport containers (30 boxes per container).
6. **Loading Bay & Transport**
   - 3 autonomous loaders (AGVs) transfer containers to 2 outbound loading bays.
   - Trucks take up to 18 containers and leave for delivery hubs.
   - If both bays are occupied, incoming trucks must wait.

---
## Additional Requirements
1. Defective Orders
   - Orders may be rejected at any stage (e.g., out-of-stock items, packing errors,
mislabelling).
   - Defective orders are removed by a reject handler and logged.
2. Autonomous Loaders
   - Trucks can only be loaded when a loader and bay are free.
3. Concurrent Activities
   - Loaders and outbound trucks operate concurrently.
   - Simulate congestion: e.g., 1 truck is waiting while both loading bays are in use.
