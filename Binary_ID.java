import java.io .*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.lang.management.*;


public class Binary_IDs {

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

    public static void main(String[] args) throws IOException {

        // The path to your input file.
        String inputFilePath = "D:\\Desktop files\\University\\Year 2\\semester 3\\Algorithm Design & Analysis\\assignment\\Data\\Database Names & IDs.txt";
        // The path where you want to save the sorted data.
        String outputFilePath = "D:\\Desktop files\\University\\Year 2\\semester 3\\Algorithm Design & Analysis\\assignment\\Data\\NewSortedData (By IDs).txt";

        try {
            ArrayList<String> dataList = readDataFromFile(inputFilePath);
            ArrayList<String> formated = new ArrayList<>();
            // Sorting the data based on the Integer part (IDs) using Custom Comparator
            dataList.sort(new IDComparator());
            for (String string : dataList) {
                String[] splitStrings = string.split(",");
                for (int j = 0;j<3;++j)
                	formated.add(splitStrings[j]);
                // Print the split strings
            }
            
         // Sort the updated list using merge sort based on the Integer part (IDs)
            mergeSort(dataList, 0, dataList.size() - 1);

            
            // Saving the sorted data to a new file
            saveDataToFile(dataList, outputFilePath);

            Scanner scanner = new Scanner(System.in);
            int choice;

            do {
                System.out.println("Select an action:");
                System.out.println("1. Add an ID");
                System.out.println("2. Delete an ID");
                System.out.println("3. Search for an ID");
                System.out.println("4. Exit");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.print("Enter the ID to add: ");
                        int targetID = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character

                        System.out.print("Enter the name: ");
                        String name = scanner.nextLine();

                        System.out.print("Enter the price: ");
                        double price = scanner.nextDouble();
                        
                        System.out.print("Enter the quantity: ");
                        int quantity = scanner.nextInt();

                        Timer addTimer = new Timer();
                        addTimer.start();

                        // Check if the ID already exists
                        if (containsID(dataList, targetID)) {
                            System.out.println("The ID already exists in the database.");
                            System.out.println(" ");
                        } else {
                            // Add the new data to the list
                            String newData = targetID + "," + name + "," + price + "$," + quantity;
                            dataList.add(newData);

                            // Sort the updated list again based on the Integer part (IDs)
                            ArrayList<String> dataList1 = readDataFromFile(inputFilePath);
                            dataList1 = mergeSort(dataList, targetID, targetID);


                            System.out.println("ID " + targetID + " added successfully.");
                            System.out.println(" ");

                            // Update the output file
                            saveDataToFile(dataList1, outputFilePath);
                        }

                        addTimer.stop();
                        System.out.println("Add method runtime (excluding user input): " + addTimer.getElapsedTime() + " nanoseconds");
                        System.out.println("Memory Used By Add: " + getMemoryUsedDuringMethod() + " bytes");
                        System.out.println(" ");
                        break;
                    case 2:
                        System.out.print("Enter the ID to delete: ");
                        int deleteID = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character

                        Timer deleteTimer = new Timer();
                        deleteTimer.start();

                        // Check if the ID exists in the list
                        if (!containsID(dataList, deleteID)) {
                            System.out.println("The ID does not exist in the database.");
                            System.out.println(" ");
                        } else {
                            // Remove the data with the given ID from the list
                            dataList.removeIf(data -> getIDFromData(data) == deleteID);
                            System.out.println("ID " + deleteID + " deleted successfully.");
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
                        // Binary search on sorted data based on the Integer part (IDs)
                        System.out.print("Enter the target ID to search: ");
                        int searchID = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character

                        Timer searchTimer = new Timer();
                        searchTimer.start();
                        ArrayList<Integer> IdList = new ArrayList<>();
                        for (int i =0 ;i<formated.size();i+=3)
                        {
                        	IdList.add(Integer.parseInt(formated.get(i)));
                        }
                        int index = binarySearch(dataList, searchID);

                        if (index != -1) {
                            String foundData = dataList.get(index);
                            System.out.println(foundData);
                            System.out.println(" ");
                        } else {
                            System.out.println("ID " + searchID + " not found.");
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
                // Assuming each line contains an integer ID, followed by a name, then a price, and a quantity separated by commas
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
/*
 // Bubble sort algorithm to sort the data based on the Integer part (IDs)
    private static ArrayList<String> bubbleSort(ArrayList<String> dataList) {
        int n = dataList.size();

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                int id1 = getIDFromData(dataList.get(j));
                int id2 = getIDFromData(dataList.get(j + 1));

                if (id1 > id2) {
                    // Swap the elements
                    String temp = dataList.get(j);
                    dataList.set(j, dataList.get(j + 1));
                    dataList.set(j + 1, temp);
                }
            }
        }
        return dataList;  
    }
*/

 // Merge sort algorithm to sort the data based on the Integer part (IDs)
    private static ArrayList<String> mergeSort(ArrayList<String> dataList, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;

            mergeSort(dataList, left, mid);
            mergeSort(dataList, mid + 1, right);

            merge(dataList, left, mid, right);
        }
		return dataList;
    }


    
    private static void merge(ArrayList<String> dataList, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        ArrayList<String> leftList = new ArrayList<>();
        ArrayList<String> rightList = new ArrayList<>();

        for (int i = 0; i < n1; ++i) {
            leftList.add(dataList.get(left + i));
        }
        for (int j = 0; j < n2; ++j) {
            rightList.add(dataList.get(mid + 1 + j));
        }

        int i = 0, j = 0;
        int k = left;

        while (i < n1 && j < n2) {
            if (getIDFromData(leftList.get(i)) <= getIDFromData(rightList.get(j))) {
                dataList.set(k, leftList.get(i));
                i++;
            } else {
                dataList.set(k, rightList.get(j));
                j++;
            }
            k++;
        }

        while (i < n1) {
            dataList.set(k, leftList.get(i));
            i++;
            k++;
        }

        while (j < n2) {
            dataList.set(k, rightList.get(j));
            j++;
            k++;
        }
    }


    // Custom Comparator to sort based on the Integer part (IDs)
    private static class IDComparator implements Comparator<String> {
    	@Override
    	public int compare(String data1, String data2) {
    		int id1 = Binary_IDs.getIDFromData(data1);
    		int id2 = Binary_IDs.getIDFromData(data2);
    		return Integer.compare(id1, id2);
        }
    }


    // Helper method to extract the ID from the data string
    private static int getIDFromData(String data) {
        String[] parts = data.split(",");
        return Integer.parseInt(parts[0]);
    }

    // Binary search on sorted data based on the Integer part (IDs)
    private static int binarySearch(ArrayList<String> dataList, int target) {
        int left = 0;
        int right = dataList.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int midValue = getIDFromData(dataList.get(mid));

            if (midValue == target) {
                return mid; // Found the target
            } else if (midValue < target) {
                left = mid + 1; // Target is in the right half
            } else {
                right = mid - 1; // Target is in the left half
            }
        }

        return -1; // Target not found
    }

    
  /* 
 // Linear search method
    private static int linearSearch(ArrayList<String> dataList, int target) {
        for (int i = 0; i < dataList.size(); i++) {
            int id = getIDFromData(dataList.get(i));
            if (id == target) {
                return i; // Found the target
            }
        }
        return -1; // Target not found
    }
    
   */
    // Helper method to check if the ArrayList contains a given ID
    private static boolean containsID(ArrayList<String> dataList, int id) {
        for (String data : dataList) {
            if (getIDFromData(data) == id) {
                return true;
            }
        }
        return false;
    }
    
    private static long getMemoryUsedDuringMethod() {
        MemoryMXBean memory = ManagementFactory.getMemoryMXBean();
        MemoryUsage memoryUsage = memory.getHeapMemoryUsage();
        return memoryUsage.getUsed();
    }
}

