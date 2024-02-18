# Java Merge CSV Rows
Java code that compares CSV rows and merges them into one.
It is written in Java and uses the Apache Commons CSV library to read and write CSV files.

# Background
I was working on an integration project where I had to deliver CSV files to a third-party system.
After we entered the UAT phase, a requirement emerged to merge specific rows in the CSV file based on `ID` and `Date`.

Two days before my vacation, UAT was in full swing... I spent quite some time ironing out the details and writing the code.

I couldn't find a ready-made solution, so I put it on GitHub.
It might save someone else's day, and you can use it as a starting point for your solution.

# Sample #1 — Remove Duplicate Rows
For example, let's say we have a CSV file like this:
```csv
ID,Date,Amount
1,2021-01-01,100
1,2021-01-01,100
1,2021-01-02,300
```

In the above example, rows 1 and 2 are identical and must be merged into one row. So, the final CSV file should look like this:
```csv
ID,Date,Amount
1,2021-01-01,100
1,2021-01-02,300
```

# Sample #2 - Merge Rows
Another example:
```csv
ID,Date,Amount,Currency,Country
1,2021-01-01,,,Germany
1,2021-01-01,100,,
1,2021-01-01,300,USD,USA
1,2021-01-01,,EUR,Germany
```

In the above example, rows 1, 2 and 4 can be merged. So, the final CSV file should look like this:
```csv
ID,Date,Amount,Currency,Country
1,2021-01-01,100,EUR,Germany
1,2021-01-01,300,USD,USA
```

# Test Driven Development (TDD)
I use the webMethods Integration Platform at work to integrate systems.
The platform can run Java code, but it's not easy to debug.
So, I decided to take a TDD approach to this problem.

First, I prepared CSV files (much more complicated than what you see in the resources folder).
One file has the input, and the other has the expected output.

Then, I started on the implementation and fine-tuned the code until all the test files passed.

Finally, I took the code, bent it, and plugged it into the webMethods Integration Platform.

# How to Use
You will find only one test in the repository. The test takes a list of CSV files, processes them, and compares the output with the expected output.

Add your CSV files to the resources folder and update the test file accordingly.

```maven
mvn test
```

