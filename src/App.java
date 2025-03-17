import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;


public class App {
    public static void main(String[] args) throws Exception {
        int n = 0;
        String fName = "";
        String fName2 = "";
        Scanner inputHandler = new Scanner(System.in);
        while (n != 4) {
            System.out.println("Please enter 1 to generate a random array. 2 to enter a filename to sort using bubble sort, 3 to enter a filename to sort using merge sort, and 4 to end the process");
            n = inputHandler.nextInt();
            inputHandler.nextLine();
            if (n == 1) {
                System.out.println("Please enter how many numbers you would like the array to be?");
                int length = inputHandler.nextInt();
                inputHandler.nextLine();
                int[] temp = createRandomArray(length);
                System.out.println("Please enter the filename including type (.txt) you would like to save this array to.");
                fName = inputHandler.nextLine();
                writeArrayToFile(temp, fName);
                //System.out.println("Saved random int array too: " + fName);
            } else if (n == 2) {
                System.out.println("Please enter a file name including type (.txt) you'd like to sort using bubble sort.");
                fName = inputHandler.nextLine();
                System.out.println("Please enter a file name including type (.txt) you'd like to save the sorted array to.");
                fName2 = inputHandler.nextLine();
                writeArrayToFile(bubbleSort(readFileToArray(fName)), fName2);
                System.out.println("Sorted file " + fName + " and saved too: " + fName2);
            } else if (n == 3) {
                System.out.println("Please enter a file name including type (.txt) you'd like to sort using merge sort.");
                fName = inputHandler.nextLine();
                System.out.println("Please enter a file name including type (.txt) you'd like to save the sorted array to.");
                fName2 = inputHandler.nextLine();
                int temp[] = readFileToArray(fName);
                writeArrayToFile(mergeSort(temp, 0, temp.length-1), fName2);
                System.out.println("Sorted file " + fName + " and saved too: " + fName2);
            } else if (n != 4) {
                System.out.println("Error please input a valid number (1, 2, or 3)");
            }
        }
        System.out.println("Program closing.");
        inputHandler.close();
        System.exit(0);
    }

    // Given arrayLength as argument, create an array of random integers between 0 and 100; return the created array.
    public static int[] createRandomArray(int arrayLength) {
        int n[] = new int[arrayLength];
        Random rand = new Random();
        for (int i = 0; i < n.length; i++) {
            n[i] = rand.nextInt(101);
        }
        return n;
    }

 
    // Given an integer array and filename, write the array to the file, with each line containing one integer in the array.
    public static void writeArrayToFile(int[] array, String filename) {
        try {
            File myObj = new File(filename);
            if (myObj.createNewFile()) {
                System.out.println("File " + filename + " created");
            }  
        } catch (Exception e) {
            System.out.println("File " + filename + "alreadys exists writing too it.");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int i = 0; i < array.length; i++) {
                if (i !=0)
                    writer.newLine();
                writer.write(String.valueOf(array[i]));
            }
            System.out.println("Array written to " + filename);
        } catch (IOException e) {
            System.err.println("Error writing file.");
        }
    }

 

    // This is the reverse of writeArrayToFile; Given the filename, read the integers (one line per integer) to an array, and return the array
    public static int[] readFileToArray(String filename) {
        try {
            File fileObj = new File(filename);
            Scanner fileCounter = new Scanner(fileObj);
            int  numInts = 0;
                while (fileCounter.hasNextInt()) {
                    fileCounter.nextInt();
                    numInts++;
                }
                fileCounter.close();
                int n[] = new int[numInts];
                Scanner fileReader = new Scanner (fileObj);
                for(int i = 0; i < numInts; i++) {
                    n[i] = fileReader.nextInt();
                }
                fileReader.close();
                return n;
            } catch (FileNotFoundException e) {
                System.out.println("A error has occured. " + filename + " is not a valid File");
                return new int[1];
            }
    }


    public static int[] bubbleSort(int[] array) {
        int len = array.length;
        for (int i = 0; i < len - 1; i++) {
            for (int j = 0; j < len - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
        return array;
    }
    public static int[] mergeSort(int[] array, int l, int r) {
        if (l < r) {
            int m = l+(r-l)/2;
            mergeSort(array, l, m);
            mergeSort(array, m + 1, r);
            merge(array, l, m, r);
        }
        return array;
    }
    static int[] merge(int array[], int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;
        int arr1[] = new int[n1];
        int arr2[] = new int[n2];
        for (int i = 0; i < n1; ++i)
            arr1[i] = array[l + i];
        for (int j = 0; j < n2; ++j)
            arr2[j] = array[m + 1 + j];
        int i = 0, j = 0;
        int k = l;
        while (i < n1 && j < n2) {
            if (arr1[i] <= arr2[j]) {
                array[k] = arr1[i];
                i++;
            }
            else {
                array[k] = arr2[j];
                j++;
            }
            k++;
        }
        while (i < n1) {
            array[k] = arr1[i];
            i++;
            k++;
        }
        while (j < n2) {
            array[k] = arr2[j];
            j++;
            k++;
        }
        return array;
    }


}