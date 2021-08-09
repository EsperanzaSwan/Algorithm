This project models a composite system of ```n * n``` sites. Each site can be either open (white) or closed (black). A site is _full_ (blue) if it is connected to a open site at the top through neighboring open sites. The system **percolates** when a bottom open site is connected to a top open site. This can be used to model metallic connectivity or if underground oil would percolate to the surface. Go to ["Percolation" assignment](https://coursera.cs.princeton.edu/algs4/assignments/percolation/specification.php) for a complete description of the assignment.

![text](https://github.com/EsperanzaSwan/Algorithm/blob/3a164f7e990357fe57853c3390309e21e96901da/Percolation/percolation.png)

Researchers are interested in find outing the percolation threshhold, namely if sites are opened randomly, how many sites have to be open before the system percolates. The percolation threshhold is calculated as ```numOFOpenSites / (n*n) ``` and researchers found the value is 0.593. ```PercolationStats``` uses Monte Carlo simulation to perform M trials and calculate the percolation threshold. During each trial, sites are randomly open till the system percolates. I/O and statistic classes are from ```algs4.jar``` provided by Princeton University.   

# Download and run the app
``` bash
git clone https://github.com/EsperanzaSwan/Algorithm.git
cd Percolation
javac -cp algs4.jar PercolationStats.java Percolation.java
java -cp algs4.jar: PercolationStats 100 50
```
``PercolationStats``` takes two parameters, first is the size n, the second is the number of trials.

