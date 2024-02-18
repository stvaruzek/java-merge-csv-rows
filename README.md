# Java Merge CSV Rows
This is a simple code to merge CSV rows in a file based on a delimiter.
It is written in Java and uses the Apache Commons CSV library to read and write CSV files.

## Background
I was working on an integration project where I had to deliver CSV files to a third-party system.
After we entered the UAT phase, a requirement came up to merge certain rows in the CSV file based on ID and Date.

Two days before my vacation, UAT in full swing... I spent quite some time ironing out the details and writing the code.

I couldn't find a ready-made solution, so I put in on GitHub.
It might save someone else's day, and you can use it as a starting point for your own solution.

## Sample CSV File
For example, let's say we have a CSV file like this:
```csv
ID,Date,Amount
1,2021-01-01,100
1,2021-01-01,100
1,2021-01-02,300
```

In the above example, row 1 and 2 are identical and need to be merged into one row. So the final CSV file should look like this:
```csv
ID,Date,Amount
1,2021-01-01,100
1,2021-01-02,300
```

## Test Driven Development (TDD)
I use webMethods Integration Platform at work to integrate systems.
The platform can run Java code, but it's not easy to debug.
So I decided to take a TDD approach to this problem.

First, I prepared CSV files (much more complicated what you see in the resources folder).
One file having the input and the other having the expected output.

Then I started on the implementation and fine-tuned the code until all the test files passed.

Finally, I took the code, bent it, and plugged it into the webMethods Integration Platform.

## How to Use
You will find only one test in the repository. The test takes a list of CSV files, processed them, and compares the output with the expected output.

```maven
mvn test
```

