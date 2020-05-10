# Exploring 100,000 Wikipedia Documents

Explored a set of 100,000 Wikipedia documents: [100KWikiText.txt](https://web.njit.edu/~chasewu/Courses/Spring2020/CS644BigData/HW/100KWikiText.txt), in which each line consists of the plain text extracted from an individual Wikipedia document

## AWS Instance Steps
  - Configured and ran a stable release of Apache Hadoop in a pseudo-distributed mode.
  - Developed a MapReduce-based approach in my Hadoop system to compute the relative frequencies of each word that occurs in all the documents in [100KWikiText.txt](https://web.njit.edu/~chasewu/Courses/Spring2020/CS644BigData/HW/100KWikiText.txt), and output the top 100 word pairs sorted in a decreasing order of relative frequency.
  - Repeated the above steps using at least 2 VM instances in my Hadoop system running in a fully-distributed mode.

### Files
- [***commands.txt***](https://github.com/Gonnuru/MapReduce_Wikipedia/blob/master/command.txt) - file that lists all the commands I used to run my code and produce the required results in both pseudo and fully distributed modes
- [***top100.txt***](https://github.com/Gonnuru/MapReduce_Wikipedia/blob/master/top100.txt) - file that stores the final results (only the top 100 word pairs sorted in a decreasing order of relative frequency)
- [***relativefrequencey.java***](https://github.com/Gonnuru/MapReduce_Wikipedia/blob/master/relativeFrequency.java) - The source code of MapReduce solution 
- [***algorithm.txt***](https://github.com/Gonnuru/MapReduce_Wikipedia/blob/master/algorithm.txt) - describes the algorithm used to solve the problem
- [***settings.txt***](https://github.com/Gonnuru/MapReduce_Wikipedia/blob/master/settings.txt) - 
- - the input/output format in each Hadoop task, i.e., the keys for the mappers and reducers.
- - the Hadoop cluster settings  used, i.e., number of VM instances, number of mappers and reducers, etc. 
- - the running time for my MapReduce approach in both pseudo and fully distributed modes


