# Movie Script Analysis Using Hadoop MapReduce

## Project Overview
Hadoop MapReduce program to analyze a dataset of movie script dialogues. Therefore, the aim is to process and draw insights from the dataset, including:

-determining the most commonly said words by characters

-Total length of dialogues per character.

-Finding all the characters and the unique words each of them has used.


## Approach and Implementation
### **Mapper Logic:**
- Parses through every line, captures the character name and dialogue.

- Breaks down conversations into words to count word occurrences and unique tokens.

- Generates key-value pairs for the number of words, number of dialogues, and unique words.

- Keeps total lines, words, and characters processed using Hadoop Counters.

### **Reducer Logic:**
  -  Aggregates word count data to find the most popular spoken words.

   - Returns an aggregate of dialogue length per character.

   - Gathers distinct words per character via a HashSet.

   - Provides structured results for further exploration.

## Execution Steps
### **1. Start Hadoop Cluster Using Docker**
```bash
docker-compose up -d
```

### **2. Build Java Code with Maven**
```bash
mvn clean install
```

### **3. Move JAR and Input File to Hadoop Container**
```bash
docker cp target/movie-analysis.jar resourcemanager:/opt/hadoop-3.2.1/share/hadoop/mapreduce/
docker cp input/movie_dialogues.txt resourcemanager:/opt/hadoop-3.2.1/share/hadoop/mapreduce/
```

### **4. Execute the MapReduce Job**
```bash
hadoop jar movie-analysis.jar com.movie.script.analysis.MovieScriptAnalysis /input/movie_scripts/movie_dialogues.txt /output
```

### **5. View the Output**
```bash
hadoop fs -cat /output/task1/part-r-00000  # Most Frequent Words
hadoop fs -cat /output/task2/part-r-00000  # Dialogue Length per Character
hadoop fs -cat /output/task3/part-r-00000  # Unique Words per Character
```

## Challenges Faced & Solutions
**a)Handling special characters and punctuation in dialogues:**
   - Used Regular expression for tokenization to get rid of unwanted characters.


## Sample Input and Output
### **Input:**
```
HARRY: We need to stop Voldemort.
RON: Yes, we do! But how?
HERMIONE: We need to be careful. He’s powerful.
HARRY: We can’t waste time. Let’s go!
```

#### **Expected Output:**

##### **1. Most Frequently Spoken Words by Characters**
```
we 4
to 4
need 2
stop 1
yes 1
do 1
how 1
but 1
careful 1
he’s 1
powerful 1
can’t 1
waste 1
time 1
let’s 1
go 1
```

##### **2. Total Dialogue Length per Character**
```
HARRY 42
RON 32
HERMIONE 41
```

##### **3. Unique Words Used by Each Character**
```
HARRY [we, need, to, stop, voldemort, can’t, waste, time, let’s, go]
RON [yes, do, how, but]
HERMIONE [need, be, careful, he’s, powerful]
```
