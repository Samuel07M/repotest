# Samuel Molina Garcés - Juan Diego Parra Castañeda
#### Operating System: Windows 10 Pro
#### Programming Language: Java
#### Tools: OnlineGDB Compiler
## Execution Instructions
#### Step 1: Unzip the folder with the file Main.java
#### Step 2: Open the website OnlineGDB (https://www.onlinegdb.com/#)
#### Step 3: In the website OnlineGDB click the button "Upload File", which is located in the left of the "Run" button
#### Step 4: Select the file Main.java saved in the unzip folder
#### Step 5: Select the programming language "Java" in the upper right-corner list
#### Step 6: Run the file
## Algorithm Explanation
#### The KMP algorithm works because it avoids unnecessary comparisons by leveraging previously computed information about the pattern through the failure function. This function indicates how much of the pattern matches itself, allowing the algorithm, in case of a mismatch, to avoid restarting the search from the beginning and instead continue from a valid previously computed position. In the implemented solution, the text is traversed only once while maintaining a counter of partial matches of the pattern; when a mismatch occurs, the failure function is used to efficiently adjust this counter.
