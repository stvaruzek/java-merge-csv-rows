import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;

import java.io.File;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MergeTest {

    // check the resources folder for test samples
    private static final String[] FILES = {
            "sample1", // sample #1 from readme
            "sample2", // sample #2 from readme
            "ABC",  // three rows can be merged into one
            "A",    // two rows can be merged into one
            "B",    // rows cannot be merged
            "C",    // rows can be merged into one
            "D",    // merge five rows into one
            "E"     // merge five rows into one

            // add more files to see if the solution works for you
    };

    @Test
    public void testFiles() throws Exception {
        // adjust the CSV format to your needs
        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                // in my case, the CSV files have no header
                .setSkipHeaderRecord(true)
                .setDelimiter(",")
                .build();

        // iterate over the files
        for (String fileName : FILES) {
            System.out.println("Testing file: " + fileName + ".csv");

            // read the CSV file
            String data = getResource(fileName + ".csv");
            Reader in = new StringReader(data);

            // parse the CSV file
            Iterable<CSVRecord> records = csvFormat.parse(in);

            // convert the records to a list
            List<CSVRecord> list = new ArrayList<>();
            for (CSVRecord csvRecord : records) {
                list.add(csvRecord);
            }

            // list to store merged rows
            List<String[]> mergedRows = new ArrayList<>();

            // iterate over the records and merge them
            for (CSVRecord row : list) {
                if (mergedRows.isEmpty()) {
                    mergedRows.add(row.values());
                    continue;
                }

                boolean rowProcessed = false;

                for (String[] mergedRow : mergedRows) {
                    if (canMerge(row.values(), mergedRow)) {
                        String[] mergedRowNew = mergeRows(row.values(), mergedRow);
                        mergedRows.set(mergedRows.indexOf(mergedRow), mergedRowNew);
                        rowProcessed = true;
                        break;
                    }
                }

                if (!rowProcessed) {
                    mergedRows.add(row.values());
                }
            }

            // write the merged rows to a CSV file
            StringWriter out = new StringWriter();
            CSVPrinter writer = new CSVPrinter(out, csvFormat);
            writer.printRecords(mergedRows);
            writer.close();

            // compare the result with the expected result
            String result = out.toString().trim();
            String expectedResult = getResource(fileName + "-out.csv").trim();

            System.out.println("Result: " + result);
            System.out.println("Expected result: " + expectedResult);

            assertEquals(result, expectedResult);
        }
    }

    /**
     * Check if two rows can be merged into one.
     * In my case, two rows can be merged if they have the same values in the same columns or if one of the values is empty.
     *
     * @param firstRow first row
     * @param secondRow second row
     * @return true if rows can be merged
     */
    boolean canMerge(String[] firstRow, String[] secondRow) {
        boolean canMerge = true;
        for (int i = 0; i < firstRow.length; i++) {
            if (!firstRow[i].equals(secondRow[i]) && !firstRow[i].isEmpty() && !secondRow[i].isEmpty()) {
                canMerge = false;
                break;
            }
        }
        return canMerge;
    }

    /**
     * Merge two rows into one.
     *
     * @param firstRow first row
     * @param secondRow second row
     * @return merged row
     */
    String[] mergeRows(String[] firstRow, String[] secondRow) {
        String[] mergedRow = new String[firstRow.length];
        for (int k = 0; k < firstRow.length; k++) {
            String firstValue = firstRow[k];
            String secondValue = secondRow[k];
            if (firstValue.isEmpty()) {
                mergedRow[k] = secondValue;
            } else if (secondValue.isEmpty()) {
                mergedRow[k] = firstValue;
            } else {
                mergedRow[k] = firstValue;
            }
        }
        return mergedRow;
    }
    private String getResource(String resource) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(resource).getFile());
        byte[] bytes = Files.readAllBytes(file.toPath());
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
