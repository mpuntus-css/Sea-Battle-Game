import java.util.Scanner;

public class SeaBattle {
   
    public static void main(String[] args) {
        

        Scanner scanner = new Scanner(System.in);

        // constants
        final int HAVE_NOT_SHOT = 0;
        final int SHIP = 1;
        final int MISS = 2;
        final int VERTICAL = 3;
        final int HORIZONTAL = 4;
        final int GRID_SIZE = 10;
        final int ALL_HITS = 20;

        // boolean condition for the check
        boolean coordinateCondition = true;

        //coordinates of the ships
        int[][] battleShipsCoordinates = new int[GRID_SIZE][GRID_SIZE];
        int[][] battleShipsCoordinates_4_Squares = new int[2][4];
        


        // counter to end the game
        int counterOfHits = 0;

        // coordinates of the shots
        int[][] shots = new int[GRID_SIZE][GRID_SIZE];

        // global variables to use them in the methods
        int arrangementChoice = 0;
        int xBowCoordinate = 0;
        int yBowCoordinate = 0;
        int xShot = 0;
        int yShot = 0;
        int squares = 4;

        
        // begining of the program
        System.out.println();

        
        //1 -st step to draw the game grid
        drawGrid(GRID_SIZE);

        
        // placing ships start from 4-square ship
        for (squares = 4; squares > 0; squares--) {   
            for (int i = 0; i < 5 - squares; i++) {

                do {

                    arrangementChoice = getArrangement(squares, scanner, coordinateCondition);
                    xBowCoordinate = getXcoordinate(squares, scanner, GRID_SIZE, coordinateCondition);
                    yBowCoordinate = getYcoordinate(squares, scanner, GRID_SIZE, coordinateCondition);

                    // coordinate check that the user entered
                    coordinateCondition = check(xBowCoordinate, yBowCoordinate, xShot, yShot, squares, arrangementChoice, battleShipsCoordinates, VERTICAL, HORIZONTAL, GRID_SIZE, shots);

                    if (squares == 4 && i == 0) {
                        //fill the array method
                        battleShipsCoordinates_4_Squares = fillBattleShipsArrays(squares, xBowCoordinate, yBowCoordinate, arrangementChoice, VERTICAL); 
                    }

                } while (!coordinateCondition);

              
                // drawing the Battle Ships 
                drawShips(xBowCoordinate, yBowCoordinate, battleShipsCoordinates, SHIP, GRID_SIZE, squares, VERTICAL, arrangementChoice);

            }// end of inner for loop
        }// end of outer for loop

        
        
        do {

            // display the shots of the user    
            drawingTheShots(GRID_SIZE, shots, HAVE_NOT_SHOT, MISS, SHIP);

            do {

                xShot = xCoordinateShots(scanner);
                yShot = yCoordinateShots(scanner);
                coordinateCondition = check(xBowCoordinate, yBowCoordinate, xShot, yShot, squares, arrangementChoice, battleShipsCoordinates, VERTICAL, HORIZONTAL, GRID_SIZE, shots);

            } while (!coordinateCondition);

            if (battleShipsCoordinates[xShot][yShot] == SHIP) {
                System.out.println("You hitted the ship");
                shots[xShot][yShot] = SHIP;
                counterOfHits++;
            } 
            else {
                System.out.println("You missed the ship");
                shots[xShot][yShot] = MISS;
            }

            
        } while (counterOfHits != ALL_HITS);

        drawingTheShots(GRID_SIZE, shots, HAVE_NOT_SHOT, MISS, SHIP);
        System.out.println("Congratulation! You destroyed ALL the Batlle Ships");

        System.out.println();//end of the program
        

        for (int i = 0; i < battleShipsCoordinates_4_Squares.length; i++) {
            for (int j = 0; j < battleShipsCoordinates_4_Squares[i].length; j++) {
                System.out.print(battleShipsCoordinates_4_Squares[i][j] + " ");
            }
            System.out.println();
        }

   






        
   
    }// end of the main method



    private static int[][] fillBattleShipsArrays(int squares, int x, int y, int arrangement, int VERTICAL) {
        int[][] array = new int[2][squares];
        
        for (int i = 0; i < squares; i++) {
            if (arrangement == VERTICAL) {
                array[0][i] = x;
                array[1][i] = y + i;
            }
            else {
                array[0][i] = x + i;
                array[1][i] = y; 
            }
        }

        return array;
    }


    private static void checkInputForInteger(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            String word = scanner.nextLine();
            System.out.println(word + " is not correct coordinate, please enter the valid coordinate");
        }
    }

    private static boolean check(int x, int y, int xShot, int yShot, int squares, int arrangementChoice, int[][] battleShipsCoordinates, int VERTICAL, int HORIZONTAL, int GRID_SIZE, int[][] shots) {
        
        // check the input of X and Y in the grid of the size of 10
        if (x < 0 || x > 9) {
            System.out.println("Not correct coordinate, please enter the valid coordinate");
            return false;
        }
        if (y < 0 || y > 9) {
            System.out.println("Not correct coordinate, please enter the valid coordinate");
            return false;
        }

        
        // check for placing the Battle Ships
        if (arrangementChoice == VERTICAL) {
            if (y + squares > GRID_SIZE || y + squares < 0) {
                System.out.println("Not correct coordinate, please enter the valid coordinate");
                return false;

            }
        }
        if (arrangementChoice == HORIZONTAL) {
            if (x + squares > GRID_SIZE || x + squares < 0) {
                System.out.println("Not correct coordinate, please enter the valid coordinate");
                return false;
            }
        }

        // check for +1 squares
        for (int j = squares; j > 0; j--) {
            for (int i = 0; i < squares; i++) {
                int dx = 0;
                int dy = 0;
                if (arrangementChoice == VERTICAL) {
                    dy = i;
                } else {
                    dx = i;
                }

                if (x + 1 + dx < GRID_SIZE && x + 1 + dx >= 0) {
                    if (battleShipsCoordinates[x + 1 + dx][y + dy] != 0) {
                        System.out.println("Not correct coordinate because of rule +1, please enter the valid coordinate");
                        return false;
                    }
                }
                if (x - 1 + dx < GRID_SIZE && x - 1 + dx >= 0) {
                    if (battleShipsCoordinates[x - 1 + dx][y + dy] != 0) {
                        System.out.println("Not correct coordinate because of rule +1, please enter the valid coordinate");
                        return false;
                    }
                }
                if (y + 1 + dy < GRID_SIZE && y + 1 + dy >= 0) {
                    if (battleShipsCoordinates[x + dx][y + 1 + dy] != 0) {
                        System.out.println("Not correct coordinate because of rule +1, please enter the valid coordinate");
                        return false;
                    }
                }
                if (y - 1 + dy < GRID_SIZE && y - 1 + dy >= 0) {
                    if (battleShipsCoordinates[x + dx][y - 1 + dy] != 0) {
                        System.out.println("Not correct coordinate because of rule +1, please enter the valid coordinate");
                        return false;
                    }
                }

                // Check for diagonal cells
                if (x + 1 + dx < GRID_SIZE && y + 1 + dy < GRID_SIZE && x + 1 + dx >= 0 && y + 1 + dy >= 0) {
                    if (battleShipsCoordinates[x + 1 + dx][y + 1 + dy] != 0) {
                        System.out.println("Not correct coordinate because of rule +1, please enter the valid coordinate");
                        return false;
                    }
                }
                if (x - 1 + dx < GRID_SIZE && y - 1 + dy < GRID_SIZE && x - 1 + dx >= 0 && y - 1 + dy >= 0) {
                    if (battleShipsCoordinates[x - 1 + dx][y - 1 + dy] != 0) {
                        System.out.println("Not correct coordinate because of rule +1, please enter the valid coordinate");
                        return false;
                    }
                }
                if (x + 1 + dx < GRID_SIZE && y - 1 + dy < GRID_SIZE && x + 1 + dx >= 0 && y - 1 + dy >= 0) {
                    if (battleShipsCoordinates[x + 1 + dx][y - 1 + dy] != 0) {
                        System.out.println("Not correct coordinate because of rule +1, please enter the valid coordinate");
                        return false;
                    }
                }
                if (x - 1 + dx < GRID_SIZE && y + 1 + dy < GRID_SIZE && x - 1 + dx >= 0 && y + 1 + dy >= 0) {
                    if (battleShipsCoordinates[x - 1 + dx][y + 1 + dy] != 0) {
                        System.out.println("Not correct coordinate because of rule +1, please enter the valid coordinate");
                        return false;
                    }
                }
                
            }// end of inner for loop
        }// end of outer big for loop

        
        // check for shots
        if (xShot >= GRID_SIZE || xShot < 0) {
            System.out.println("Not correct coordinate for a shot, please enter the valid coordinate");
            return false;
        }
        if (yShot >= GRID_SIZE || yShot < 0) {
            System.out.println("Not correct coordinate for a shot, please enter the valid coordinate");
            return false;
        }

        // check for the input of the shots by user (can't place all the shot to one square)
        if (shots[xShot][yShot] != 0) {
            System.out.println("You've already shot at these coordinates. Please choose different ones.");
            return false;
        }

        return true;
    }

    private static void drawGrid(int GRID_SIZE) {

        System.out.println("Welcome to the Sea Battle Game!!!");
        System.out.printf("%22s", "Your Game Grid");
        System.out.println();

        System.out.print("  ");
        for (int i = 0; i < GRID_SIZE; i++) {

            System.out.print(i + " ");
        }

        System.out.println();

        for (int i = 0; i < GRID_SIZE; i++) {
            System.out.print(i);
            for (int j = 0; j < GRID_SIZE; j++) {

                System.out.print(" -");

            }
            System.out.println();
        }

    }

    public static int getArrangement(int squares, Scanner scanner, boolean coordinateCondition) {

        int arrangementChoice = 0;
        
        System.out.println();
        System.out.println("Place your " + squares + "-square Battle Ship");
        System.out.println();

        do {
            System.out.println("What is the arrangement of your " + squares + "-square Battle Ship:");
            System.out.println("3. Vertical arrangement?");
            System.out.println("4. Horizontal  arrangement?");
            System.out.println("Please, enter the number");

            checkInputForInteger(scanner);

            //arrangementChoice = scanner.nextInt();
            arrangementChoice = (int)(2 * Math.random() + 3);

            if (arrangementChoice < 5 && arrangementChoice > 2) {
                coordinateCondition = true;
            } else {
                coordinateCondition = false;
                System.out.println("Not correct number, please enter the valid number");
            }
        } while (!coordinateCondition);
        return arrangementChoice;
    }

    public static int getXcoordinate(int squares, Scanner scanner, int GRID_SIZE, boolean coordinateCondition) {
        
        int xBowCoordinate = 0;
        
        System.out.println();
        System.out.println("Place your " + squares + "-square Battle Ship");
        System.out.println();
        System.out.println("Enter the coordinate of your " + squares + "-square Battle Ship's Bow");

        System.out.println("X-coordinate:");

        checkInputForInteger(scanner);

        //xBowCoordinate = scanner.nextInt();
        xBowCoordinate = (int)(10 * Math.random());
        
        
        return xBowCoordinate;
    }

    public static int getYcoordinate(int squares, Scanner scanner, int GRID_SIZE, boolean coordinateCondition) {
        
        int yBowCoordinate = 0;
        
        System.out.println("Y-coordinate:");
        checkInputForInteger(scanner);
        
        
        
        //yBowCoordinate = scanner.nextInt();
        yBowCoordinate = (int)(10 * Math.random());

        return yBowCoordinate;
    }

    private static void drawShips(int x, int y, int[][] battleShipsCoordinates, int SHIP, int GRID_SIZE, int squares, int VERTICAL, int arrangementChoice) {

        for (int i = 0; i < squares; i++) {
            if (arrangementChoice == VERTICAL) {

                battleShipsCoordinates[x][y + i] = SHIP;
            } else {

                battleShipsCoordinates[x + i][y] = SHIP;

            }
        }

        System.out.print("  ");
        for (int i = 0; i < GRID_SIZE; i++) {

            System.out.print(i + " ");
        }

        System.out.println();
        for (int i = 0; i < GRID_SIZE; i++) {
            System.out.print(i);
            for (int j = 0; j < GRID_SIZE; j++) {
                if (battleShipsCoordinates[j][i] == SHIP) {
                    System.out.print(" X");

                } else {
                    System.out.print(" -");
                }
            }
            System.out.println();
        }
    }

    private static void drawingTheShots(int GRID_SIZE, int[][] shots, int HAVE_NOT_SHOT, int MISS, int SHIP) {

        System.out.printf("%22s", "Your Game Grid to make a shot");
        System.out.println();
        System.out.print("  ");
        
        for (int i = 0; i < GRID_SIZE; i++) {
            System.out.print(i + " ");
        }

        System.out.println();

        for (int i = 0; i < GRID_SIZE; i++) {
            System.out.print(i);
            for (int j = 0; j < GRID_SIZE; j++) {
                if (shots[j][i] == HAVE_NOT_SHOT) {
                    System.out.print(" -");

                } else if (shots[j][i] == MISS) {
                    System.out.print(" .");
                } 
                // hit the Battle Ship
                else if (shots[j][i] == SHIP) {
                    System.out.print(" X");
                }
            }
            System.out.println();
        }
    }

    private static int xCoordinateShots(Scanner scanner) {
        
        int xShot = 0;
        
        System.out.println("Please make your shot");
        System.out.println("Enter the coordinate of your shot");
        System.out.println("X coordinate: ");

        checkInputForInteger(scanner);
        
        
        
        //xShot = scanner.nextInt();
        xShot = (int)(10 * Math.random());

        return xShot;
    }

    private static int yCoordinateShots(Scanner scanner) {
        
        int yShot = 0;

        System.out.println("Y coordinate: ");

        checkInputForInteger(scanner);
        
        
        //yShot = scanner.nextInt();
        yShot = (int)(10 * Math.random());


        return yShot;
    }

 

}// end of the class



