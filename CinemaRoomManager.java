import java.util.Scanner;

public class CinemaRoomManager {
    private static int purchasedTickets=0;
    private static int currentIncome=0;

    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int row = s.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seat = s.nextInt();
        int purchasedTickets = 0;
        String[][] seatArray = new String[row+1][seat+1];
        for(int i=0;i<=row;i++){
            for(int j=0;j<=seat;j++){
                if(i==0 && j==0){
                    seatArray[i][j]="  ";
                }
                else if(i==0){
                    seatArray[i][j]=j+" ";
                }
                else if(j==0){
                    seatArray[i][j]=i+" ";
                }
                else{
                    seatArray[i][j]="S ";
                }
            }
        }

        boolean check=true;
        System.out.println();

        while(check){
            System.out.println("1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");
            int n = s.nextInt();
            System.out.println();
            switch(n){
                case 1:
                    showSeats(row,seat,seatArray);
                    break;
                case 2:
                    buyTickets(row,seat,seatArray);
                    break;
                case 3:
                    statistics(row,seat);
                    break;
                case 0:
                    check=false;
                    break;
                default:
                    break;
            }
        }
    }

    public static void showSeats(int row, int seat, String[][] seatArray){
        System.out.println("Cinema:");
        for(int i=0;i<=row;i++){
            for(int j=0;j<=seat;j++){
                System.out.print(seatArray[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void buyTickets(int row,int seat,String[][] seatArray){
        Scanner s = new Scanner(System.in);
        System.out.println("Enter a row number:");
        int rowNo = s.nextInt();
        System.out.println("Enter a seat number in that row:");
        int seatNo = s.nextInt();

        if(rowNo>row||seatNo>seat){
            System.out.println();
            System.out.println("Wrong input!");
            System.out.println();
            buyTickets(row,seat,seatArray);
        }
        else if(seatArray[rowNo][seatNo]=="B "){
            System.out.println();
            System.out.println("That ticket has already been purchased!");
            System.out.println();
            buyTickets(row,seat,seatArray);
        }
        else{
            purchasedTickets+=1;
            if((row*seat)<60){
                System.out.println();
                System.out.println("Ticket price: $10");
                System.out.println();
                seatArray[rowNo][seatNo]="B ";
                currentIncome+=10;
            }
            else{
                if(row/2>=rowNo){
                    System.out.println();
                    System.out.println("Ticket price: $10");
                    System.out.println();
                    seatArray[rowNo][seatNo]="B ";
                    currentIncome+=10;
                }
                else{
                    System.out.println();
                    System.out.println("Ticket price: $8");
                    System.out.println();
                    seatArray[rowNo][seatNo]="B ";
                    currentIncome+=8;
                }
            }
        }
    }

    public static void statistics(int row,int seat){
        System.out.print("Number of purchased tickets: ");
        System.out.println(purchasedTickets);

        float percentage= ((float)purchasedTickets/(float)(row*seat))*100;
        System.out.printf("Percentage:");
        System.out.printf(" %.2f",percentage);
        System.out.print("%");

        System.out.println();
        System.out.print("Current income: ");
        System.out.println("$"+currentIncome);

        if((row*seat)<60){
            System.out.print("Total income: ");
            System.out.println("$"+(row*seat*10));
        }
        else{
            int first_half = row/2;
            int second_half = row-first_half;
            int sum=0;
            sum+=(first_half*seat*10);
            sum+=(second_half*seat*8);
            System.out.print("Total income:");
            System.out.println("$"+sum);
        }
        System.out.println();
    }

}

