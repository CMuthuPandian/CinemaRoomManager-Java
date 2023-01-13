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

/*

Output:

Enter the number of rows:
9
Enter the number of seats in each row:
9

1. Show the seats
2. Buy a ticket
3. Statistics
0. Exit
1

Cinema:
  1 2 3 4 5 6 7 8 9 
1 S S S S S S S S S 
2 S S S S S S S S S 
3 S S S S S S S S S 
4 S S S S S S S S S 
5 S S S S S S S S S 
6 S S S S S S S S S 
7 S S S S S S S S S 
8 S S S S S S S S S 
9 S S S S S S S S S 

1. Show the seats
2. Buy a ticket
3. Statistics
0. Exit
3

Number of purchased tickets: 0
Percentage: 0.00%
Current income: $0
Total income:$720

1. Show the seats
2. Buy a ticket
3. Statistics
0. Exit
2

Enter a row number:
3
Enter a seat number in that row:
3

Ticket price: $10

1. Show the seats
2. Buy a ticket
3. Statistics
0. Exit
1

Cinema:
  1 2 3 4 5 6 7 8 9 
1 S S S S S S S S S 
2 S S S S S S S S S 
3 S S B S S S S S S 
4 S S S S S S S S S 
5 S S S S S S S S S 
6 S S S S S S S S S 
7 S S S S S S S S S 
8 S S S S S S S S S 
9 S S S S S S S S S 

1. Show the seats
2. Buy a ticket
3. Statistics
0. Exit
3

Number of purchased tickets: 1
Percentage: 1.23%
Current income: $10
Total income:$720

1. Show the seats
2. Buy a ticket
3. Statistics
0. Exit
2

Enter a row number:
5
Enter a seat number in that row:
5

Ticket price: $8

1. Show the seats
2. Buy a ticket
3. Statistics
0. Exit
2

Enter a row number:
5
Enter a seat number in that row:
5

That ticket has already been purchased!

Enter a row number:
10
Enter a seat number in that row:
20

Wrong input!

Enter a row number:
6
Enter a seat number in that row:
8

Ticket price: $8

1. Show the seats
2. Buy a ticket
3. Statistics
0. Exit
3

Number of purchased tickets: 3
Percentage: 3.70%
Current income: $26
Total income:$720

1. Show the seats
2. Buy a ticket
3. Statistics
0. Exit
2

Enter a row number:
4
Enter a seat number in that row:
7

Ticket price: $10

1. Show the seats
2. Buy a ticket
3. Statistics
0. Exit
8

1. Show the seats
2. Buy a ticket
3. Statistics
0. Exit
2

Enter a row number:
8
Enter a seat number in that row:
2

Ticket price: $8

1. Show the seats
2. Buy a ticket
3. Statistics
0. Exit
3

Number of purchased tickets: 5
Percentage: 6.17%
Current income: $44
Total income:$720

1. Show the seats
2. Buy a ticket
3. Statistics
0. Exit
1

Cinema:
  1 2 3 4 5 6 7 8 9 
1 S S S S S S S S S 
2 S S S S S S S S S 
3 S S B S S S S S S 
4 S S S S S S B S S 
5 S S S S B S S S S 
6 S S S S S S S B S 
7 S S S S S S S S S 
8 S B S S S S S S S 
9 S S S S S S S S S 

1. Show the seats
2. Buy a ticket
3. Statistics
0. Exit
0


Process finished with exit code 0

 */
