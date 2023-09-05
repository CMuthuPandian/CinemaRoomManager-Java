package CinemaTicketBooking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class CinemaRoomManagerDB {
    private static int purchasedTickets = 0;
    private static int currentIncome = 0;
    private static Connection connection;

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        establishDatabaseConnection();

        System.out.println("Enter the number of rows:");
        int row = s.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seat = s.nextInt();

        String[][] seatArray = new String[row + 1][seat + 1];
        initializeSeatArray(row, seat, seatArray);

        boolean check = true;
        System.out.println();

        while (check) {
            System.out.println("1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");
            int n = s.nextInt();
            System.out.println();
            switch (n) {
                case 1:
                    showSeats(row, seat, seatArray);
                    break;
                case 2:
                    buyTickets(row, seat, seatArray);
                    break;
                case 3:
                    statistics(row, seat);
                    break;
                case 0:
                    check = false;
                    break;
                default:
                    break;
            }
        }

        closeDatabaseConnection();
    }

    public static void establishDatabaseConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cinematicketbooking", "root", "@MuthuPandianC25");
            initializeDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void initializeDatabase() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS bookings (id INT AUTO_INCREMENT PRIMARY KEY, rowNo INT, seatNo INT, price INT)";
        try (PreparedStatement statement = connection.prepareStatement(createTableSQL)) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeDatabaseConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void initializeSeatArray(int row, int seat, String[][] seatArray) {
        for (int i = 0; i <= row; i++) {
            for (int j = 0; j <= seat; j++) {
                if (i == 0 && j == 0) {
                    seatArray[i][j] = "  ";
                } else if (i == 0) {
                    seatArray[i][j] = j + " ";
                } else if (j == 0) {
                    seatArray[i][j] = i + " ";
                } else {
                    seatArray[i][j] = "S ";
                }
            }
        }
    }

    public static void showSeats(int row, int seat, String[][] seatArray) {
        System.out.println("Cinema:");
        for (int i = 0; i <= row; i++) {
            for (int j = 0; j <= seat; j++) {
                System.out.print(seatArray[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void buyTickets(int row, int seat, String[][] seatArray) {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter a row number:");
        int rowNo = s.nextInt();
        System.out.println("Enter a seat number in that row:");
        int seatNo = s.nextInt();

        if (rowNo > row || seatNo > seat) {
            System.out.println();
            System.out.println("Wrong input!");
            System.out.println();
            buyTickets(row, seat, seatArray);
            return;
        }

        if ("B ".equals(seatArray[rowNo][seatNo])) {
            System.out.println();
            System.out.println("That ticket has already been purchased!");
            System.out.println();
            buyTickets(row, seat, seatArray);
            return;
        }

        purchasedTickets += 1;
        int price = calculateTicketPrice(row, rowNo, seat);
        System.out.println();
        System.out.println("Ticket price: $" + price);
        System.out.println();
        seatArray[rowNo][seatNo] = "B ";
        currentIncome += price;

        // Store booking information in the database
        storeBooking(rowNo, seatNo, price);
    }

    public static int calculateTicketPrice(int row, int rowNo, int seat) {
        int totalSeats = row * seat;
        if (totalSeats <= 60 || rowNo <= row / 2) {
            return 10;
        } else {
            return 8;
        }
    }

    public static void storeBooking(int rowNo, int seatNo, int price) {
        if (connection != null) {
            String insertBookingSQL = "INSERT INTO bookings (rowNo, seatNo, price) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(insertBookingSQL)) {
                statement.setInt(1, rowNo);
                statement.setInt(2, seatNo);
                statement.setInt(3, price);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void statistics(int row, int seat) {
        System.out.print("Number of purchased tickets: ");
        System.out.println(purchasedTickets);

        float percentage = ((float) purchasedTickets / (float) (row * seat)) * 100;
        System.out.printf("Percentage:");
        System.out.printf(" %.2f", percentage);
        System.out.print("%");

        System.out.println();
        System.out.print("Current income: ");
        System.out.println("$" + currentIncome);

        int totalIncome = calculateTotalIncome(row, seat);
        System.out.print("Total income: ");
        System.out.println("$" + totalIncome);
        System.out.println();
    }

    public static int calculateTotalIncome(int row, int seat) {
        int totalSeats = row * seat;
        if (totalSeats <= 60) {
            return totalSeats * 10;
        } else {
            int firstHalfRows = row / 2;
            int secondHalfRows = row - firstHalfRows;
            return (firstHalfRows * seat * 10) + (secondHalfRows * seat * 8);
        }
    }
}
