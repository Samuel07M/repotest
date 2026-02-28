# Samuel Molina Garcés - Juan Diego Parra Castañeda
## Versions Used
#### Operating System: Windows 10 Pro
#### Programming Language: Java
#### Tools: None

## Execution Instructions
#### Step 1: Unzip the folder with the file Main.java
#### Step 2: Open the website OnlineGDB (https://www.onlinegdb.com/#)
#### Step 3: In the website OnlineGDB click the button "Upload File", which is located in the left of the "Run" button
#### Step 4: Select the file Main.java saved in the unzip folder
#### Step 5: Select the programming language "Java" in the upper right-corner list
#### Step 6: Run the file

## Algorithm Explanation
#### The algorithm computes the failure function by iterating through the pattern from left to right while maintaining a variable t, which represents the length of the current longest prefix that is also a suffix. For each position s, it compares the current character with the character at position t. If they match, the prefix length is extended by incrementing t. If #### they do not match, the algorithm does not restart from zero; instead, it uses previously computed values in the failure array to update t to a smaller valid prefix length. This process continues until a match is found or t becomes zero. By reusing earlier computations, the algorithm efficiently builds the failure function in linear time, avoiding redundant comparisons.
