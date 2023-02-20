# COP-4520-Assignment-2
## RUNNING
- For both GlassVaseQueue.java and Lab.java run javac [Program Name].java to compile it. In the same directory run java [Program Name]. Both programs will ask for how many guests, the range is 0 < N <= 100.
- Both programs wil then ask how many runs you want to do. This is to help resolve the random nature of both programs by producing enough test data to produce the mean, stdev, and bell curve.

## OUTPUT
- Time in milliseconds

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
  -  
    ![image](https://user-images.githubusercontent.com/105519245/220185984-8e0a8c6e-28c3-434f-b6b8-d9f165cb7cbe.png)

- N = 10:
  - Mean: 0.5 ms
  - SD: 0.08
  - 
   ![image](https://user-images.githubusercontent.com/105519245/220185279-4102d429-dfeb-450d-bfa1-843e4b538406.png)
- N = 50:
  - Mean: 2.53 ms
  - SD: 0.41
  - 
   ![image](https://user-images.githubusercontent.com/105519245/220185615-b77689f5-52c8-4cb6-8573-9863ea8151e9.png)
- N = 100:
  - Mean: 4.97 ms
  - SD: 0.78
  -  
   ![image](https://user-images.githubusercontent.com/105519245/220185817-db94bc33-6e8d-48c6-aea6-eb92171df57f.png)


