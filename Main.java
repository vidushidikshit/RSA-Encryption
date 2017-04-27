package project1;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;

// Code by Vidushi Dikshit
public class Main {

	public static void main(String[] args) throws IOException {

		// Generate 1024-bit RSA encryption keys
		RSA rsa1 = new RSA(5);

		// Generate 1024-bit RSA encryption keys and saving in file in
		// key_vidushi.txt
		RSA rsa2 = new RSA(5, "key_vidushi.txt");

		// Reading the key values from key_vidushi.txt
		RSA rsa3 = new RSA("key_vidushi.txt");

		// Encrypting the value m= 70
		BigInteger c = RSA.encrypt(new BigInteger("70"), rsa3.key_N_read, rsa3.key_public_read);
		System.out.println("Cipher Text is :" + c);

		// Decrypting the value c which we received from previous encryption of
		// m = 70
		BigInteger m = RSA.decrypt(c);
		// m should match the m= 70
		System.out.println("Plain text is : " + m);

		// Encrypting the content of the test.txt and creating test.enc
		String filename = RSA.encryptFile("test.txt", rsa3.key_N_read, rsa3.key_public_read);

		// decrypting test.enc and printing the standard output
		System.out.println("DECRYPTED TEXT FROM FILE : " + filename);
		RSA.decryptFile(filename);

	}

}