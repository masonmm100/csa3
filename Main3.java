import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Main3 {
    public static void main(String[] args) {
        RedBlackTree tree = new RedBlackTree();

        // Use ClassLoader to load the CSV file from the same package/directory
        try (InputStream inputStream = Main.class.getResourceAsStream("amazon-product-data.csv")) {
            if (inputStream == null) {
                System.err.println("Error: File 'amazon-product-data.csv' not found in the package.");
                return;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            // Skip the header row
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] data = parseCsvLine(line);

                if (data == null) {
                    System.err.println("Error: Malformed CSV line. Skipping: " + line);
                    continue;
                }

                String productId = data[0];
                String name = data[1];
                String category = data[2];
                double price = Double.parseDouble(data[3].replace("$", "").trim());

                tree.insert(productId, name, category, price);
            }
        } catch (Exception e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        // Perform search and insertion operations
        System.out.println("Search Results:");
        tree.printProductDetails("4c69b61db1fc16e7013b43fc926e502d"); // Example product ID
        tree.printProductDetails("66d49bbed043f5be260fa9f7fbff5957"); // Example product ID
        tree.printProductDetails("nonexistent-id"); // Non-existent ID

        System.out.println("\nInsert Results:");
        tree.insert("new-unique-id", "New Product", "Category X", 199.99);
        tree.insert("4c69b61db1fc16e7013b43fc926e502d", "Duplicate Product", "Category Y", 19.99); // Duplicate ID
    }

    /**
     * Parse a line from the CSV file while handling embedded quotes and commas.
     *
     * @param line The CSV line to parse.
     * @return An array of fields, or null if parsing fails.
     */
    private static String[] parseCsvLine(String line) {
        try {
            // Handle quoted fields and split the line
            String[] fields = new String[4];
            int currentIndex = 0;
            boolean inQuotes = false;
            StringBuilder currentField = new StringBuilder();

            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);

                if (c == '\"') {
                    inQuotes = !inQuotes; // Toggle inQuotes state
                } else if (c == ',' && !inQuotes) {
                    // Split the field when encountering a comma outside quotes
                    fields[currentIndex++] = currentField.toString().trim();
                    currentField.setLength(0); // Clear the field
                } else {
                    currentField.append(c); // Append character to the field
                }
            }

            // Add the last field
            fields[currentIndex] = currentField.toString().trim();

            // Ensure we have exactly 4 fields
            if (fields.length != 4) {
                throw new IllegalArgumentException("Unexpected number of fields in line: " + line);
            }

            return fields;

        } catch (Exception e) {
            System.err.println("Error parsing line: " + e.getMessage());
            return null;
        }
    }
}