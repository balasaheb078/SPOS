import java.util.*;

public class OptimalPageReplacement {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of frames: ");
        int numFrames = scanner.nextInt();

        System.out.print("Enter the number of pages: ");
        int numPages = scanner.nextInt();

        System.out.print("Enter the reference string (space-separated): ");
        int[] referenceString = new int[numPages];
        for (int i = 0; i < numPages; i++) {
            referenceString[i] = scanner.nextInt();
        }

        int pageHits = optimalPageReplacement(numFrames, referenceString);
        int pageFaults = numPages - pageHits;

        System.out.println("Page hits: " + pageHits);
        System.out.println("Page faults: " + pageFaults);
    }

    public static int optimalPageReplacement(int numFrames, int[] referenceString) {
        int pageHits = 0;
        List<Integer> frames = new ArrayList<>();
        Map<Integer, Integer> nextPageOccurrence = new HashMap<>();

        for (int i = 0; i < referenceString.length; i++) {
            int page = referenceString[i];

            if (frames.contains(page)) {
                // Page hit
                pageHits++;
            } else {
                // Page fault
                if (frames.size() < numFrames) {
                    // There is space in frames, add the page
                    frames.add(page);
                } else {
                    // Find the page in frames that will not be used for the longest time
                    int farthestPage = -1;
                    int farthestDistance = -1;
                    for (int j = 0; j < frames.size(); j++) {
                        int nextPage = frames.get(j);
                        int nextOccurrence = getNextOccurrence(referenceString, i, nextPage);
                        if (nextOccurrence == -1) {
                            farthestPage = nextPage;
                            break;
                        }
                        if (nextOccurrence > farthestDistance) {
                            farthestPage = nextPage;
                            farthestDistance = nextOccurrence;
                        }
                    }

                    // Remove the farthest page and add the new page
                    frames.remove(Integer.valueOf(farthestPage));
                    frames.add(page);
                }
            }

            // Update the next occurrence of each page in frames
            for (int j = 0; j < frames.size(); j++) {
                int nextPage = frames.get(j);
                int nextOccurrence = getNextOccurrence(referenceString, i, nextPage);
                nextPageOccurrence.put(nextPage, nextOccurrence);
            }
        }

        return pageHits;
    }

    private static int getNextOccurrence(int[] referenceString, int currentIndex, int page) {
        for (int i = currentIndex + 1; i < referenceString.length; i++) {
            if (referenceString[i] == page) {
                return i;
            }
        }
        return -1;
    }
}


/*
 Enter the number of frames: 4
Enter the number of pages: 20
Enter the reference string (space-separated): 7 0 1 2 0 3 0 4 2 3 0 3 2 1 2 0 1 7 0 1
Page hits: 12
Page faults: 8
 */