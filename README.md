# 🛒 Supermarket Inventory Management System

A Java-based inventory management system developed for BER2013: Algorithm Design and Analysis (May–Aug 2023). This application streamlines supermarket operations such as stock tracking, price management, and data sorting using key data structures and algorithms.

---

## 🚀 Features

### 📦 Core Functionalities
- Add, delete, and search products by **Name** or **ID**
- Sort inventory using **Bubble Sort** or **Merge Sort**
- Search inventory using **Linear Search** or **Binary Search**
- Monitor **runtime** and **memory usage** of operations
- Persist inventory updates to output files

### 🧪 Dual Implementation
- **Application 1:** ArrayList + Binary Search + Bubble Sort
- **Application 2:** LinkedList + Linear Search + Merge Sort

---

## 🧱 Tech Stack

- **Language:** Java  
- **Data Structures:** `ArrayList`, `LinkedList`  
- **Algorithms:** Linear Search, Binary Search, Bubble Sort, Merge Sort  
- **File I/O:** BufferedReader, BufferedWriter  
- **Tools:** MemoryMXBean for memory profiling

---

## 📊 Architecture Overview

### 🔹 Application 1: (IDs/Names)
- Stores inventory in an `ArrayList`
- Uses **Bubble Sort** to sort data
- Performs **Binary Search** on sorted entries
- Adds, deletes, and updates entries based on **product ID** or **name**
- Tracks performance metrics (runtime and memory)

### 🔹 Application 2: (IDs/Names)
- Uses a `LinkedList<Product>` structure
- Applies **Merge Sort** for sorting by ID
- Implements **Linear Search** for name-based queries
- Includes I/O handling and real-time list manipulation

---

## 📈 Algorithms & Complexity

| Operation     | ArrayList Version            | LinkedList Version           |
|---------------|------------------------------|------------------------------|
| Add/Delete    | O(n)                         | O(n)                         |
| Search        | O(log n) (binary)            | O(n) (linear)                |
| Sort          | O(n²) (bubble sort)          | O(n log n) (merge sort)      |
| Memory Use    | Medium (ArrayList resizing)  | Lower (no contiguous blocks) |

---

## 📌 Future Improvements

- 🔍 Use `HashMap` to improve search speed
- 🧼 Add data validation and user-friendly error messages
- 💾 Allow sorting/filtering by multiple fields (price, quantity, etc.)
- 🎨 Implement GUI for better usability

---

## 👥 Authors

- Lim Chee Yang   
- Ali Isam Husam Al-Turaihi 
- Mohamed Tarek Essam Hessein Emad
- Lim Jiun Hong 
- Chong Wen Qi 

---

## 📝 License

This project was developed for academic use. Contributions and forks are welcome with proper credit to the original authors.

