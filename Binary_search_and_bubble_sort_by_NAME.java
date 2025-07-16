import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.management.*;


public class Binary_Names {

    // Timer to calculate the runtime for methods
    private static class Timer {
        private long startTime;
        private long userTime;

        public void start() {
            startTime = System.nanoTime();
        }

        public void stop() {
            userTime = System.nanoTime() - startTime;
        }

        public long getElapsedTime() {
            return userTime;
        }
    }

    public static void main(String[] args) {
        // The path to your input file.
        String inputFilePath = "D:\\Desktop files\\University\\Year 2\\semester 3\\Algorithm Design & Analysis\\assignment\\Data\\Database Names & IDs.txt";
        // The path where you want to save the sorted data.
        String outputFilePath = "D:\\Desktop files\\University\\Year 2\\semester 3\\Algorithm Design & Analysis\\assignment\\Data\\NewSortedData (By Names).txt";

        try {
            ArrayList<String> dataList = readDataFromFile(inputFilePath);

            // Sorting the data based on the product name (String) using Bubble Sort
            dataList = mergeSort(dataList);

            // Saving the sorted data to a new file
            saveDataToFile(dataList, outputFilePath);

            Scanner scanner = new Scanner(System.in);
            int choice;

            do {
                System.out.println("Select an action:");
                System.out.println("1. Add a product");
                System.out.println("2. Delete a product");
                System.out.println("3. Search for a product");
                System.out.println("4. Exit");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        System.out.print("Enter the product name to add: ");
                        String targetName = scanner.nextLine();

                        System.out.print("Enter the product ID: ");
                        int targetID = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character

                        System.out.print("Enter the price: ");
                        double price = scanner.nextDouble();
                        scanner.nextLine(); // Consume the newline character
                        
                        System.out.print("Enter the quantity: ");
                        int quantity = scanner.nextInt();

                        Timer addTimer = new Timer();
                        addTimer.start();

                        // Check if the product name already exists
                        if (containsName(dataList, targetName)) {
                            System.out.println("The product name already exists in the database.");
                            System.out.println(" ");
                        } else {
                            // Add the new data to the list
                            String newData = targetID + "," + targetName + "," + price + "$," + quantity;
                            dataList.add(newData);                            
                            // Sort the updated list again based on the product name (String)
                            
                            dataList = mergeSort(dataList);

                            System.out.println("Product " + targetName + " added successfully.");
                            System.out.println(" ");
                            // Update the output file
                            saveDataToFile(dataList, outputFilePath);
                        }

                        addTimer.stop();
                        System.out.println("Add method runtime (excluding user input): " + addTimer.getElapsedTime() + " nanoseconds");
                        System.out.println("Memory Used By Add: " + getMemoryUsedDuringMethod() + " bytes");
                        System.out.println(" ");
                        break;
                    case 2:
                        System.out.print("Enter the product name to delete: ");
                        String deleteName = scanner.nextLine();

                        Timer deleteTimer = new Timer();
                        deleteTimer.start();

                        // Check if the product name exists in the list
                        if (!containsName(dataList, deleteName)) {
                            System.out.println("The product name does not exist in the database.");
                            System.out.println(" ");
                        } else {
                            // Remove the data with the given product name from the list
                            dataList.removeIf(data -> getNameFromData(data).equals(deleteName));
                            System.out.println("Product " + deleteName + " deleted successfully.");
                            System.out.println(" ");

                            // Update the output file
                            saveDataToFile(dataList, outputFilePath);
                        }

                        deleteTimer.stop();
                        System.out.println("Delete method runtime (excluding user input): " + deleteTimer.getElapsedTime() + " nanoseconds");
                        System.out.println("Memory Used By Delete: " + getMemoryUsedDuringMethod() + " bytes");
                        System.out.println(" ");
                        break;
                    case 3:
                        // Binary search on sorted data based on the product name (String)
                        System.out.print("Enter the target product name to search: ");
                        String searchName = scanner.nextLine();

                        Timer searchTimer = new Timer();
                        searchTimer.start();

                        int index = binarySearch(dataList, searchName);

                        if (index != -1) {
                            String foundData = dataList.get(index);
                            String[] parts = foundData.split(",");
                            int foundID = Integer.parseInt(parts[0]);
                            String priceFound = parts[2];
                            String quantityfound = parts[3];
                            
                            System.out.println("Product Name: " + searchName + ", ID: " + foundID + ", Price: " + priceFound + ", Quantity: " + quantityfound);
                            System.out.println(" ");
                        } else {
                            System.out.println("Product " + searchName + " not found.");
                            System.out.println(" ");
                        }

                        searchTimer.stop();
                        System.out.println("Search method runtime (excluding user input): " + searchTimer.getElapsedTime() + " nanoseconds");
                        System.out.println("Memory Used By Search: " + getMemoryUsedDuringMethod() + " bytes");
                        System.out.println(" ");
                        break;
                    case 4:
                        System.out.println("Exiting the program.");
                        break;
                    default:
                        System.out.println("Invalid choice.");
                        System.out.println(" ");
                        break;
                }
            } while (choice != 4);

            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



	// Read data from the input file and store it in an ArrayList
    private static ArrayList<String> readDataFromFile(String filePath) throws IOException {
        ArrayList<String> dataList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Assuming each line contains a product name, followed by an integer ID, and a price, and quantity separated by a comma
                dataList.add(line.trim());
            }
        }
        return dataList;
    }

    // Save data to a new file
    private static void saveDataToFile(ArrayList<String> dataList, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String data : dataList) {
                writer.write(data + "\n");
            }
        }
    }



    // Helper method to extract the product name from the data string
    private static String getNameFromData(String data) {
    	
        String[] parts = data.split(",");
        return parts[1]; // Return the second part which is the product name
    }

    // Binary search on sorted data based on the product name (String)
    private static int binarySearch(ArrayList<String> dataList, String target) {
        int left = 0;
        int right = dataList.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            String midName = getNameFromData(dataList.get(mid));

            int result = midName.compareToIgnoreCase(target);
            if (result == 0) {
                return mid; // Found the target
            } else if (result < 0) {
                left = mid + 1; // Target is in the right half
            } else {
                right = mid - 1; // Target is in the left half
            }
        }

        return -1; // Target not found
    }
    
    /*
    // Linear search method
       private static int linearSearch(ArrayList<String> dataList, String target) {
           for (int i = 0; i < dataList.size(); i++) {
               String name = getNameFromData(dataList.get(i));
               if (name.equalsIgnoreCase(target)) {
                   return i; // Found the target
               }
           }
           return -1; // Target not found
       }
       */
       

    // Helper method to check if the ArrayList contains a given product name
    private static boolean containsName(ArrayList<String> dataList, String name) {
        for (String data : dataList) {
            if (getNameFromData(data).equals(name)) {
                return true;
            }
        }
        return false;
    }
/*
    // Sorting the data based on the product name (String) using Bubble Sort
    private static ArrayList<String> bubbleSort(ArrayList<String> dataList) {
        int n = dataList.size();
        for (int i = 0 ; i < n ; ++i) {
            // System.out.println("inside first for loop");
        	for (int j =i+1 ; j < n ; j++) {
        		String name1 = getNameFromData(dataList.get(i));
        		String name2 = getNameFromData(dataList.get(j));
        		if (name1.compareToIgnoreCase(name2) > 0) {  
                   String temp = dataList.get(j);
                   dataList.set(j, dataList.get(i));
                   dataList.set(i, temp);
                    
                }

        	}
        }
        return dataList;
    }
  */  
 // Sorting the data based on the product name (String) using Merge Sort
    private static ArrayList<String> mergeSort(ArrayList<String> dataList) {
        if (dataList.size() <= 1) {
            return dataList;
        }

        int mid = dataList.size() / 2;
        ArrayList<String> left = new ArrayList<>(dataList.subList(0, mid));
        ArrayList<String> right = new ArrayList<>(dataList.subList(mid, dataList.size()));

        left = mergeSort(left);
        right = mergeSort(right);

        return merge(dataList, left, right);
    }

    private static ArrayList<String> merge(ArrayList<String> dataList, ArrayList<String> left, ArrayList<String> right) {
        int leftIndex = 0;
        int rightIndex = 0;
        int dataIndex = 0;

        while (leftIndex < left.size() && rightIndex < right.size()) {
            String leftName = getNameFromData(left.get(leftIndex));
            String rightName = getNameFromData(right.get(rightIndex));

            if (leftName.compareToIgnoreCase(rightName) <= 0) {
                dataList.set(dataIndex, left.get(leftIndex));
                leftIndex++;
            } else {
                dataList.set(dataIndex, right.get(rightIndex));
                rightIndex++;
            }
            dataIndex++;
        }

        while (leftIndex < left.size()) {
            dataList.set(dataIndex, left.get(leftIndex));
            leftIndex++;
            dataIndex++;
        }

        while (rightIndex < right.size()) {
            dataList.set(dataIndex, right.get(rightIndex));
            rightIndex++;
            dataIndex++;
        }

        return dataList;
    }
    
   
    
    private static long getMemoryUsedDuringMethod() {
        MemoryMXBean memory = ManagementFactory.getMemoryMXBean();
        MemoryUsage memoryUsage = memory.getHeapMemoryUsage();
        return memoryUsage.getUsed();
    }
}
