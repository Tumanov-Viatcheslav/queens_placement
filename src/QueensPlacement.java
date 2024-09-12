import java.util.ArrayList;
import java.util.List;

public class QueensPlacement {
    static int NUMBER_OF_QUEENS_TO_PLACE = 8;
    static byte BOARD_SIZE = 8;

    public static boolean isContained(List<Position> currentPlacement, List<List<Position>> successfulPlacements) {
        boolean allContained, isContained;

        for (List<Position> successfulPlacement: successfulPlacements) {
            if (currentPlacement.size() != successfulPlacement.size())
                continue;
            allContained = true;
            for (Position p1: currentPlacement) {
                isContained = false;
                for (Position p2: successfulPlacement) {
                    if (p1.isEqualPos(p2)){
                        isContained = true;
                        break;
                    }
                }
                if (!isContained) {
                    allContained = false;
                    break;
                }
            }
            if (allContained)
                return true;
        }
        return false;
    }

    public static boolean placeQueen(List<Position> previousPlacement, List<List<Position>> successfulPlacements) {
        if (previousPlacement.size() >= NUMBER_OF_QUEENS_TO_PLACE) {
            if (QueensPlacement.isContained(previousPlacement, successfulPlacements))
                return false;
            successfulPlacements.addLast(previousPlacement);
            return true;
        }
        boolean placeIncorrect;
        for (byte x = 0; x < BOARD_SIZE; x++) {
            for (byte y = 0; y < BOARD_SIZE; y++) {
                placeIncorrect = false;
                for (Position placement: previousPlacement) {
                    if (placement.sameX(x, y) || placement.sameY(x, y) || placement.sameDiagonal(x, y)) {
                        placeIncorrect = true;
                        break;
                    }
                }
                if (placeIncorrect)
                    continue;
                Position currentPlacement = new Position(x, y);
                previousPlacement.addLast(currentPlacement);
                if (placeQueen(previousPlacement, successfulPlacements)) {
                    return true;
                }
                previousPlacement.removeLast();
            }
        }
        return false;
    }

    public static void printPlacement(List<Position> queensPlacement) {
        boolean placeOccupied;
        for (byte x = 0; x < BOARD_SIZE; x++) {
            for (byte y = 0; y < BOARD_SIZE; y++) {
                placeOccupied = false;
                for (Position placement: queensPlacement) {
                    if (placement.isEqual(x, y)) {
                        placeOccupied = true;
                        break;
                    }
                }
                if (placeOccupied)
                    System.out.print("Q ");
                else
                    System.out.print("x ");
            }
            System.out.println("\n");
        }
        System.out.println("----------------");
    }

    public static void main(String[] args) {
        List<Position> queensPlacement;
        List<List<Position>> allPlacements = new ArrayList<List<Position>>();
        int counter = 0;

        do  {
            queensPlacement = new ArrayList<Position>();
            System.out.println("Counter: " + String.valueOf(counter));
            counter++;
        } while (placeQueen(queensPlacement, allPlacements));
    }
}