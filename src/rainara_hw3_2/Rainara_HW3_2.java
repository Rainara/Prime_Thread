/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rainara_hw3_2;
import java.util.Scanner;
/**
 *
 * @author Rainara
 */
public class Rainara_HW3_2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			@SuppressWarnings("resource")
			Scanner scan = new Scanner(System.in);
			System.out.println("Max number and thread number:");
		
			new PrimeThread(scan.next(), scan.next());

		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
}
