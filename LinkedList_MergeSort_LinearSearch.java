import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.LinkedList;
import java.util.Scanner;

public class LinkedListMergeSortLinearSearch {
    // Timer to calculate the runtime for methods
    private static class Timer {
        private long startTime; 
        private long userTime; 

        public void start() {
            startTime = System.nanoTime(); // Get the current time in nanoseconds and store it in startTime
        }

        public void stop() {
            userTime = System.nanoTime() - startTime; // Calculate the elapsed time by subtracting startTime from the current time
        }

        public long getElapsedTime() {
            return userTime; // Return the elapsed time of the method
        }
    }

    public static void main(String[] args) {
        // Database file path
        String inputFilePath = "C:\\Users\\Acer\\Documents\\Y2S2\\Database.txt";

        try {
        	// Read data from database and store it in LinkedList
            LinkedList<Product> dataList = readDataFromFile(inputFilePath);

            // Sorting the data using Merge Sort
            mergeSort(dataList);

            // Saving the sorted data to the database
            saveDataToFile(dataList, inputFilePath);

            // Prompt user to choose an action
            Scanner scanner = new Scanner(System.in);
            int choice;

            do {
            	// Display options
                System.out.println("Type a number to select an action:");
                System.out.println("1. Add a product");
                System.out.println("2. Delete a product by ID");
                System.out.println("3. Search for a product by ID");
                System.out.println("4. Search for a product by name");
                System.out.println("5. Exit");
                choice = scanner.nextInt();

                switch (choice) {
                case 1:
                    // User chose to add a new product
                    Timer addTimer = new Timer(); // Create a new Timer instance to measure the runtime of the add operation
                    addTimer.start(); // Start the timer

                    // Create a new product from user input
                    Product newProduct = createProductFromUserInput(scanner);

                    // Check if the product ID already exists in the database
                    if (hasDuplicateId(dataList, newProduct.getId())) {
                        System.out.println("The product with ID " + newProduct.getId() + " already exists in the database.");
                        System.out.println(" ");
                        
                    } else {
                        // If the product ID is unique, add the new product to the list
                        dataList.add(newProduct);
                        
                        // Sort the updated list again using Merge Sort to maintain the sorted order
                        mergeSort(dataList);
                        
                        System.out.println("Product with ID " + newProduct.getId() + " added successfully.");
                        System.out.println(" ");
                        
                        // Update the database by saving the sorted list to the file
                        saveDataToFile(dataList, inputFilePath);
                    }
                    
                        addTimer.stop(); // Stop the timer after the add operation
                        System.out.println("Add method runtime (excluding user input): " + addTimer.getElapsedTime() + " nanoseconds");
                        System.out.println("Memory Used By Add: " + getMemoryUsedDuringMethod() + " bytes");
                        System.out.println(" ");
                    
                    break;
                    
                case 2:
                    // User chose to delete a product by ID
                    System.out.print("Enter the ID of the product to delete: ");
                    int deleteID = scanner.nextInt();

                    Timer deleteTimer = new Timer(); // Create a new Timer instance to measure the runtime of the delete operation
                    deleteTimer.start(); // Start the timer

                    Product productToDelete = null;
                    // Find the product with the specified ID in the list
                    for (Product product : dataList) {
                        if (product.getId() == deleteID) {
                            productToDelete = product;
                            break;
                        }
                    }

                    // Check if the product with the specified ID exists in the list
                    if (productToDelete != null) {
                        // If the product exists, remove it from the list
                        dataList.remove(productToDelete);
                        
                        System.out.println("Product with ID " + deleteID + " deleted successfully.");
                        System.out.println(" ");
                        
                        // Update the output file by saving the updated list
                        saveDataToFile(dataList, inputFilePath);
                        
                    } else {
                        // If the product with the specified ID does not exist in the list
                        System.out.println("Product with ID " + deleteID + " does not exist in the database.");
                        System.out.println(" ");
                    }
                    
                    deleteTimer.stop(); // Stop the timer after the delete operation
                    System.out.println("Delete method runtime (excluding user input): " + deleteTimer.getElapsedTime() + " nanoseconds");
                    System.out.println("Memory Used By Delete: " + getMemoryUsedDuringMethod() + " bytes");
                    System.out.println(" ");
                    break;
                    
                case 3:
                    mergeSort(dataList); // Sort the list using Merge Sort (based on product ID)
                    
                    System.out.print("Enter the target ID to search: ");
                    int searchID = scanner.nextInt();

                    Timer searchbyIDTimer = new Timer(); // Create a new Timer instance to measure the runtime of the search operation
                    searchbyIDTimer.start(); // Start the timer

                    // Perform linear search on the sorted list for the target ID
                    int index = linearSearchById(dataList, searchID);

                    if (index != -1) {
                        // If the target ID is found, retrieve the product from the list and print its details
                        Product product = dataList.get(index);
                        
                        System.out.println("Product found. Name: " + product.getName() + ", Price: " + product.getPrice() + "$, Quantity: " + product.getQuantity());
                        System.out.println(" ");
                        
                    } else {
                        // If the target ID is not found in the list
                        System.out.println("Product with ID " + searchID + " not found.");
                        System.out.println(" ");
                    }

                    searchbyIDTimer.stop(); // Stop the timer after the search operation
                    System.out.println("Search method runtime (excluding user input): " + searchbyIDTimer.getElapsedTime() + " nanoseconds");
                    System.out.println("Memory Used By Search: " + getMemoryUsedDuringMethod() + " bytes");
                    System.out.println(" ");
                    break;
                    
                case 4:
                    mergeSort(dataList); // Sort the list using Merge Sort (based on product ID)
                    
                    scanner.nextLine();
                    System.out.print("Enter the target name to search: ");
                    String searchName = scanner.nextLine(); // Read the target name entered by the user

                    Timer searchTimer = new Timer(); // Create a new Timer instance to measure the runtime of the search operation
                    searchTimer.start(); // Start the timer

                    // Perform linear search on the sorted list for the target name
                    Product foundProductByName = linearSearchByName(dataList, searchName);

                    if (foundProductByName != null) {
                        // If the product is found, print its details
                        System.out.println("Product found by name: " + foundProductByName.getName() + " (ID: " + foundProductByName.getId() + 
                        		"), Price: " + foundProductByName.getPrice() + "$, Quantity: " + foundProductByName.getQuantity());
                        
                    } else {
                        // If the product with the target name is not found in the list
                        System.out.println("Product with name '" + searchName + "' not found.");
                    }
                    
                    searchTimer.stop(); // Stop the timer after the search operation
                    System.out.println("Search method runtime (excluding user input): " + searchTimer.getElapsedTime() + " nanoseconds");
                    System.out.println("Memory Used By Search: " + getMemoryUsedDuringMethod() + " bytes");
                    System.out.println(" ");
                    break;
                    
                    case 5:
                        System.out.println("Exiting the program.");
                        break;
                        
                    default:
                        System.out.println("Invalid choice.");
                        break;
                        
                }
            } while (choice != 5);
            scanner.close(); // Close the Scanner

            } catch (IOException e) {
                // If an IOException occurs during the execution of the code, print stack trace to help identifying the issue
                e.printStackTrace();
            }
    }

    // Read data from the input file and store it in a LinkedList
    private static LinkedList<Product> readDataFromFile(String filePath) throws IOException {
        // Create a new LinkedList to store Product objects read from the file
        LinkedList<Product> productList = new LinkedList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Read each line of the input file
            while ((line = reader.readLine()) != null) {
                // Split the line into separate parts using comma as the delimiter
                String[] parts = line.split(",");
                
                // Extract the product ID from the first part and parse it to an integer
                int id = Integer.parseInt(parts[0]);
                
                // Extract the product name from the second part
                String name = parts[1];
                
                // Remove the dollar sign and extract the product price from the third part and parse it to a double
                double price = Double.parseDouble(parts[2].replace("$", ""));
                
                // Extract the product quantity from the fourth part and parse it to an integer
                int quantity = Integer.parseInt(parts[3]);
                
                // Create a new Product object using the extracted data and add it to the LinkedList
                productList.add(new Product(id, name, price, quantity));
            }
        }

        // Return the LinkedList containing all the Product objects read from the file
        return productList;
    }

    // Create a new Product object based on user input
    private static Product createProductFromUserInput(Scanner scanner) {
    	// Prompt user to key in product ID
        System.out.println("Enter the product ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        // Prompt user to key in product name
        System.out.println("Enter the product name: ");
        String name = scanner.nextLine();
        
        // Prompt user to key in product price
        System.out.println("Enter the product price: (numbers only)");
        double price = scanner.nextDouble();
        
        // Prompt user to key in quantity
        System.out.println("Enter the product quantity: (numbers only)");
        int quantity = scanner.nextInt();

        // Create a new Product object using the user-provided data and return it
        return new Product(id, name, price, quantity);
    }

    // Save data to the output file
    private static void saveDataToFile(LinkedList<Product> productList, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Loop through each product in the productList
            for (Product product : productList) {
                // Ensure the ID stored will always be 4 digits
                String formattedId = String.format("%04d", product.getId());
                
                // Write the product information to the file
                writer.write(formattedId + "," + product.getName() + "," + product.getPrice() + "$," + product.getQuantity() + "\n");
            }
        }
    }


    // Merge Sort Algorithm based on Product IDs
    private static void mergeSort(LinkedList<Product> dataList) {
        if (dataList.size() <= 1) {
            return; // The list is already sorted
        }

        // Split the list into two halves
        int mid = dataList.size() / 2;
        LinkedList<Product> left = new LinkedList<>(dataList.subList(0, mid));
        LinkedList<Product> right = new LinkedList<>(dataList.subList(mid, dataList.size()));

        // Recursively sort both halves
        mergeSort(left);
        mergeSort(right);

        // Merge the sorted halves
        merge(dataList, left, right);
    }

    // Merge two sorted lists (left and right) into the main data list (dataList).
    private static void merge(LinkedList<Product> dataList, LinkedList<Product> left, LinkedList<Product> right) {
        int leftIndex = 0; // Index for the left list
        int rightIndex = 0; // Index for the right list
        int dataIndex = 0; // Index for the main data list (dataList)

        // Compare elements from both left and right lists and merge them in sorted order
        while (leftIndex < left.size() && rightIndex < right.size()) {
            if (left.get(leftIndex).getId() < right.get(rightIndex).getId()) {
                // If the ID of the left element is smaller, add it to the main data list
                dataList.set(dataIndex++, left.get(leftIndex++));
            } else {
                // If the ID of the right element is smaller, add it to the main data list
                dataList.set(dataIndex++, right.get(rightIndex++));
            }
        }

        // Copy any remaining elements from the left and right lists to the main data list
        while (leftIndex < left.size()) {
            dataList.set(dataIndex++, left.get(leftIndex++));
        }
        while (rightIndex < right.size()) {
            dataList.set(dataIndex++, right.get(rightIndex++));
        }
    }

    // Linear search method to find a product by ID
    private static int linearSearchById(LinkedList<Product> dataList, int target) {
        int index = 0;
        // Loop through each element in the dataList
        for (Product element : dataList) {
            // Compare the ID of the current element with the target
            if (element.getId() == target) {
                return index; // Target found, return the index where it was found
            }
            index++;
        }
        // Target not found, return -1
        return -1;
    }

    // Linear search method to find a product by name
    private static Product linearSearchByName(LinkedList<Product> dataList, String targetName) {
        // Loop through each product in the dataList
        for (Product product : dataList) {
            // Search for a product with the name that matches the targetName, ignoring case
            if (product.getName().equalsIgnoreCase(targetName)) {
                return product; // Product found, return the product object
            }
        }
        // Product with the specified name not found, return null
        return null;
    }

    
    // Check if the LinkedList contains a product with the given ID
    private static boolean hasDuplicateId(LinkedList<Product> dataList, int id) {
        for (Product product : dataList) {
            if (product.getId() == id) {
                return true; // Found a product with the same ID
            }
        }
        return false; // No product with the same ID found
    }

    // Product class
    private static class Product {
        private int id;
        private String name;
        private double price;
        private int quantity;

        // Constructor to create a new Product object with the given attributes
        public Product(int id, String name, double price, int quantity) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }
        
        // Getter method to retrieve the product's ID
        public int getId() {
            return id;
        }
        // Getter method to retrieve the product's name
        public String getName() {
            return name;
        }
        // Getter method to retrieve the product's price
        public double getPrice() {
            return price;
        }
        // Getter method to retrieve the product's quantity
        public int getQuantity() {
        	return quantity;
        }
        @Override
        public String toString() {
            return "Product [ID=" + id + ", Name=" + name + ", Price=" + price + ", Quantity=" + quantity +"]";
        }
    }
    
    // Method to get the amount of memory used by the Java application during its execution
    private static long getMemoryUsedDuringMethod() {
        // Get the MemoryMXBean which provides access to memory system management functionality
        MemoryMXBean memory = ManagementFactory.getMemoryMXBean();
        
        // Get the current memory usage of the heap, which is the memory area where objects are allocated
        MemoryUsage memoryUsage = memory.getHeapMemoryUsage();
        
        // Return the amount of used memory in bytes
        return memoryUsage.getUsed();
    }

}
