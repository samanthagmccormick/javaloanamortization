/*
 * Samantha McCormick CSC240 Java Programming
 */

/*
 * For this assignment, you will use a loop structure to create a loan amortization schedule based
 * on user input for loan amount (principal), the number of months of the loan period and the
 * interest rate (expressed as an annual percentage rate, or APR). The program will display the
 * monthly payment and the total of all payments as shown in the sample output.
 * 
 * In the output, show no answer with more than two decimal places. Be careful to write your code
 * using proper formatting, including a header, appropriate indentation and adequate code comments.
 * The program should also use good user dialog. The program should continuously allow users to
 * enter data and generate new loan information until they enter an appropriate sentinel value.
 * Include only the .java file and the .class file in a single .zip file for submission. See the
 * Module 2 Assignment 2 Example to see a sample run of the program. In the sample, user input is
 * underlined.
 */

package assignment2;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

public class LoanAmortization {

  public static void main(String[] args) {
    final int DECIMALPLACES = 2;
    final RoundingMode ROUNDINGMODE = RoundingMode.HALF_UP;

    System.out.println("Welcome to the Loan Amortization Program of XYZ Banking");
    System.out.println("This program will help you determine the payment structure of your loan.");
    System.out.println("Let us get started.");

    // Initialize scanner
    @SuppressWarnings("resource")
    Scanner input = new Scanner(System.in);

    String repeat = "y";

    do {
      System.out.print("\nEnter the loan amount you are requesting: ");
      double principal = input.nextDouble();

      System.out.print("Enter the number of months for the loan: ");
      int numberOfMonths = input.nextInt();

      System.out.print("Enter the APR you have been quoted: ");
      double apr = input.nextDouble();
      double aprDecimal = apr / 100;

      // Get the monthly interest rate (divide by 12 months)
      double monthlyRate = aprDecimal / 12;

      // Monthly payment:
      double monthlyPayment =
          principal * (monthlyRate * Math.pow((1 + monthlyRate), numberOfMonths))
              / (Math.pow((1 + monthlyRate), numberOfMonths) - 1);
      // Round to 2 decimal places
      BigDecimal monthlyPaymentDollars =
          new BigDecimal(monthlyPayment).setScale(DECIMALPLACES, ROUNDINGMODE);
      System.out.println("\nThe monthly payment will be: $" + monthlyPaymentDollars);

      System.out.println("The total paid (with interest) will be: $"
          + monthlyPaymentDollars.multiply(new BigDecimal(numberOfMonths)));

      // Print amortization schedule for this loan
      // Formatting is four rows with 10 spaces between, then a line break
      // The minus means align left
      String format = "%-10s %-10s %-10s %-10s %n";
      System.out.println("\nYour Amortization Schedule\n");
      System.out.format(format, "Payment", "Interest", "Principal", "Balance");

      double interestPayment;
      double principalPayment;
      double balance;
      int paymentNumber = 1;
      while (paymentNumber <= numberOfMonths) {
        interestPayment = principal * monthlyRate;

        principalPayment = monthlyPayment - interestPayment;

        balance = principal - principalPayment;

        // Update the balance for the next iteration
        principal = balance;

        // Print a formatted table of payments
        System.out.format(format, paymentNumber,
            ("$" + new BigDecimal(interestPayment).setScale(DECIMALPLACES, ROUNDINGMODE)),
            ("$" + new BigDecimal(principalPayment).setScale(DECIMALPLACES, ROUNDINGMODE)),
            ("$" + new BigDecimal(balance).setScale(DECIMALPLACES, ROUNDINGMODE)));

        // Onto the next
        paymentNumber++;
      }
      System.out.print("\nWould you like to calculate another loan (Y/N): ");
      repeat = input.next();

    } while (repeat.toLowerCase().equals("y"));

    System.out
        .println("\nThank you for using the Loan Amortization Program of XYZ Banking. Goodbye.");
  }

}
