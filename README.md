# COP-4520-Assignment-2
## RUNNING
- For both GlassVaseQueue.java and Lab.java run javac [Program Name].java to compile it. In the same directory run java [Program Name]. Both programs will ask for how many guests, the range is 0 < N <= 100.
- Both programs wil then ask how many runs you want to do. This is to help resolve the random nature of both programs by producing enough test data to produce the mean, stdev, and bell curve.

## OUTPUT
- Output to file
- Time in milliseconds M times with M being the number of runs

## APPROACH LAB.JAVA
- Different guests would randomly be asked by the Minotaur to go into the maze. 
- The guests decided there would be one assigned counter. 
- If the guest saw the cake for the first time they'd eat it but not replace it, including the counter.
- Each time the counter saw an empty plate they'd replace the cake and increment a counter in their head, even if the counter themselves ate the cake in the same visit. - Any time the guests saw an empty plate nothing would be done. 
- Once the number the counter sees equals to the amount of guests at the party the counter would report to the Minotaur that everyone has visited the Labyrinth at least once.

## CORRECTNESS LAB.JAVA
- Each guest will visit the Labryinth at least N times, with N being the number of guests.
- The program will keep going until all guests have eaten at least one cake and the counter registered that they've eaten it.
- If each guest will have at most one cake (and no one but the counter replaces the cake) the counter will replace N cakes. The counter keeping track of how many cakes he replaced will correlate with how many guests have eaten the cake and therefore visited the Labryinth.

## EFFICIENCY LAB.JAVA
- In order to ensure each guests had for SURE entered the Labryinth they will enter the maze multiple times to try and eat their first cake.
- On average, each guest will enter the maze N times until everyone has eaten a cake.

## EXPERIMENTAL EVALUATION LAB.JAVA
Each N was tested with 100 runs.
- N = 1:
  - Mean: 0.13 ms
  - SD: 0.03
  
    ![image](https://user-images.githubusercontent.com/105519245/220185984-8e0a8c6e-28c3-434f-b6b8-d9f165cb7cbe.png)
    
- N = 10:
  - Mean: 0.5 ms
  - SD: 0.08
  
   ![image](https://user-images.githubusercontent.com/105519245/220185279-4102d429-dfeb-450d-bfa1-843e4b538406.png)
   
- N = 50:
  - Mean: 2.53 ms
  - SD: 0.41
  
   ![image](https://user-images.githubusercontent.com/105519245/220185615-b77689f5-52c8-4cb6-8573-9863ea8151e9.png)
   
- N = 100:
  - Mean: 4.97 ms
  - SD: 0.78 
  
   ![image](https://user-images.githubusercontent.com/105519245/220185817-db94bc33-6e8d-48c6-aea6-eb92171df57f.png)

## DISCUSSION GLASSVASEQUEUE.JAVA
- I chose option 3 to implement, the queue, below is the discussion of all 3 options

### PROS: OPTION 1
- Guests could choose when they wanted to try and see the vase
- Equal chance for all guests to see the vase
### CONS: OPTION 1
- Guests could not guarentee that they could check the vase, this could cause starvation among the guests (threads)
- Guests would be waiting for a long time. This means it could cause some deadlocking as some guests hog up the space within the vase room because they got lucky, making a given guest wait.
- Guests would waste time checking the vase room to see if its open. Alot of other guests are also checking to see if its open. This means the guests can't do other things like walk around the castle

### PROS: OPTION 2
- Allows guests to check the room whenever they want
- Allows all guests an equal chance to see the vase
- Allows all guests to roam around the castle and do other things as they won't even attempt to check the room if the sign is flipped. This avoids the disorganized queue in option 1.
### CONS: OPTION 2
- If a guest gets unlucky starvation could occur as other guests constantly get to the vase room before the guest can visit. The guest can't guarentee that they can see the vase when they want

### PROS: OPTION 3
- Can guarentee order of guests going to visit the vase. Guests can see the vase when they want (if they wait for a bit). We prevent starvation here.
- Avoids crowding around the room, gives organization infront of the vase room.

### CONS: OPTION 3
- Can't do other stuff, like roaming around the castle, while waiting. They are forced to wait, causing problems with waiting.
- Some guests will visit multiple times before others visit even once.

## Why 3 is correct and the other 2 suck (why I picked 3)
- Number 3 guarentees every guest gets the visit the vase in a reasonable amount of time
- Number 1 and 2 introduces the possibillity for a given guest to starve. One guest can hog the room and keep out other guests, leading to larger runtimes if someone gets unlucky
- Number 3 guarentees the program can run in a reasonable amount of time, ending when all guests have visited the vase
- Although number 3 doesn't allow the threads to do anything else due to forcing them to wait, it speeds up the execution time of every guest visiting the vase

## APPROACH GLASSVASEQUEUE.JAVA
- I made each thread sleep for a random amount of time
- Once they woke up they'd check the room to see if it's empty (value is -1). If it was empty they'd replace the value in the room with their own ID.
- They'd enter a while loop that kept track if the amount of guests that visited the room for the first time is equal to the amount of guests (if it is get out of the while loop and end the program)
- After, they'd queue themselves in the queue. The queue was implemented as a circular array to prevent slow runtimes with constantly replacing and reorganizing the array.
- A synchronized block represented the room. All threads except the one checking if its their turn would be locked at the beginning of the synchronized block. 
- If was the threads turn to check their turn they'd check if the room was empty first, if it was replace the value in the room with their ID.
- If the ID in the room was theirs they'd proceed. If it wasn't they'd return to the synchronized block at the beginning of the while loop.
- In the room they'd register themselves as visited and increment the counter.
- If the queue had someone in it they'd set the ID of the room to the person next in the queue. If there was no one in the queue at the moment they'd set the room as empty.

## CORRECTNESS GLASSVASEQUEUE.JAVA
- Once a given guest queues they can guarentee that they'll visit the vase once everyone in front of them has gone.
- The synchronized block locks everyone but one guest. That guest is free to check if it's their turn at that moment. If it isn't the variables of the room remain unchanged unless the room is empty.
- The only way the room becomes empty at this point is if the queue was initially empty at the time the person that was last in the room check it.
- This guarentee of the room remaining unchanged (unless the one condition was met) makes the queue work.
- The insert and pop operations of the array are implemented with synchronized block, ensuring only one thread is inserting themselves or popping someone one at a time.

## EFFICIENCY GLASSVASEQUEUE.JAVA
- Implementing the queue in a circular array optimizies the guests going in and out of the queue.
- It makes it so that the array doesn't have to be reconfigured everytime something is inserted or popped.

## EXPERIMENTAL EVALUATION GLASSVASEQUEUE.JAVA
Each N was tested with 100 runs.
- N: 1
  - Mean: 0.21 ms
  - SD: 0.09
  
    ![image](https://user-images.githubusercontent.com/105519245/220193803-647c60b2-f853-4aa4-a3d8-a36a99b1f482.png)
    
- N: 10
  - Mean: 0.82 ms
  - SD: 0.44
    
    ![image](https://user-images.githubusercontent.com/105519245/220194256-8338d360-24f3-4209-b21c-d45c1f1ba2bf.png)

- N: 50
  - Mean: 3.52 ms
  - SD: 0.5

    ![image](https://user-images.githubusercontent.com/105519245/220194704-af5522d2-193b-49dc-8d3c-9a20bb37039c.png)
    
- N: 100
  - Mean: 6.95 ms
  - SD: 0.51

    ![image](https://user-images.githubusercontent.com/105519245/220194932-178c40ae-35c4-41dc-8899-4587306a17f5.png)

