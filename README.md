# COP-4520-Assignment-2
## RUNNING
For both GlassVaseQueue.java and Lab.java run javac [Program Name].java to compile it. In the same directory run java [Program Name]. Both programs will ask for how many guests, the range is 0 < N <= 100. The output will show what guests visited the cake/vase.

## OUTPUT
- A list of guests that visited the vase/cake
- Time in seconds

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
- N = 10:
  - Mean: 1.011
  - SD: 0.42
  - 
   ![image](https://user-images.githubusercontent.com/105519245/220181531-8eea4409-3312-4715-a819-d14b23a79e4b.png)

