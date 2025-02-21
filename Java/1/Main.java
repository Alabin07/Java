public class Main {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 2}; // Example array with duplicate
        boolean Output = false; // Variable to store if duplicate is found
        int[] newList = new int[nums.length]; // Temporary list
        int index = 0; // Track newList size

        for (int i = 0; i < nums.length; i++) {
            // Check if nums[i] already exists in newList
            for (int j = 0; j < index; j++) {
                if (nums[i] == newList[j]) {
                    Output = true;
                    break;
                }
            }

            // If no duplicate was found, add to newList
            if (!Output) {
                newList[index++] = nums[i];
            }
        }

        System.out.println("Duplicate found: " + Output);
    }
}
