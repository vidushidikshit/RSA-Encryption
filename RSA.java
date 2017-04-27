package project1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Random;

public class RSA {
	// Code by Vidushi Dikshit
	private HashMap<String, BigInteger> private_key;
	HashMap<String, BigInteger> public_key;

	BigInteger N, e, d;

	/*
	 * RSA(n) (constructor): generates a public (N; e) and private(N; d) RSA key
	 * pair,where N; e; d are numbers of approximately n bits in length. The
	 * private key is stored as a private field of the class and the public key
	 * is printed to standard output.
	 */
	public RSA(int n)
	{
		// key generation by using a function called key generation.
		// Didn't performed functions in the constructor because calling the
		// constructor would have resulted in access usage of memory
		key_generation(n);
	}

	/*
	 * RSA(n, filename) (constructor): same as RSA(n) but in addition it stores
	 * the public and private key pair in filename.
	 */
	RSA(int n, String filename) throws IOException 
	{
		// generation of keys
		key_generation(n);
		// storing the keys in variables
		String key_private = private_key.toString();
		String key_public = public_key.toString();
		String keys = key_private + key_public;

		// writing the keys in the file using Write Function
		WriteFile(filename, keys);
	}

	/*
	 * RSA(filename) (constructor): reads in the private key stored in the file
	 * filename.
	 */
	static BigInteger key_private_read;
	BigInteger key_public_read;
	static BigInteger key_N_read;

	RSA(String filename) throws IOException 
	{
		// Reading the contents of file using function ReadFile
		String line = ReadFile(filename);
		System.out.println(line);

		// Extracting private key from the text read out of the file filename
		String key_private = line.substring(line.indexOf('d') + 2, line.indexOf(','));

		// Extracting private key from the text read out of the file filename
		String key_public = line.substring(line.indexOf('e') + 2, line.lastIndexOf(','));

		String key_N = line.substring(line.indexOf('N') + 2, line.indexOf('}'));

		// Converting the keys which were extracted and stored in String to
		// BigInteger
		this.key_private_read = new BigInteger(key_private);
		this.key_public_read = new BigInteger(key_public);
		this.key_N_read = new BigInteger(key_N);

		// Printing the keys
		System.out.println("private key read from File" + filename + " is :" + key_private_read + " " + key_N_read);
		System.out.println("public key read from File" + filename + " is :" + key_public_read + " " + key_N_read);

	}

	/*
	 * c = encrypt(m, N, e): for a given integer m < N and public key (N; e)
	 * return the encrypted message c = m^e(mod N).
	 */
	public static BigInteger encrypt(BigInteger m, BigInteger N, BigInteger e)
	{
		// System.out.println(N +" "+ e);
		// encrypted message c = m^e(mod N)
		BigInteger c = ModularArithmetic.modexp(m, e, N);

		return c;

	}

	/*
	 * m = decrypt(c): for an integer c < N, use the private key to return the
	 * decrypted message m = c^d(mod N)
	 */
	public static BigInteger decrypt(BigInteger c)
	{
		// decrypted message m = c^d(mod N)
		BigInteger m = ModularArithmetic.modexp(c, key_private_read, key_N_read);
		return m;

	}

	/*
	 * encryptFile(filename, N, e): for a givenfile (with extension txt) and
	 * public key (N; e), it creates a file of the same name (with extension
	 * enc) containing the encrypted data to be transmitted over an insecure
	 * communication line.
	 */
	public static String encryptFile(String filename, BigInteger N, BigInteger e)
	{
		StringBuilder sb = new StringBuilder();
		BigInteger m, cipher;
		// Reading the contents of file using function ReadFile
		String line = ReadFile(filename);

		// Implementing the for loop to extract each character for encription
		for (int i = 0; i < line.length(); i++) {
			char c = line.charAt(i);

			// converting character into its ascii value
			int ascii = (int) c;

			// converting integer ascii to BigInteger
			m = new BigInteger(String.valueOf(ascii));

			// calling encrypt function to encrypt each character
			cipher = encrypt(m, N, e);
			sb.append(cipher.toString());
			sb.append(",");
		}
		String cipher_text = sb.toString();

		// Storing the encrypted values in filename.enc
		String FileName = filename.substring(0, filename.indexOf('.')) + ".enc";
		WriteFile(FileName, cipher_text);
		return FileName;

	}

	/*
	 * decryptFile(filename): for a given file (with extension enc) it uses the
	 * private key to decrypt the content of the file and print it to standard
	 * output.
	 */
	public static void decryptFile(String filename)
	{
		String cipher[] = null;

		BigInteger cipher_value;
		BigInteger ascii_value;

		// Reading the encrypted values from filename.enc
		String line = ReadFile(filename);

		// Implementing for loop to split the values for each character by ,
		for (int i = 0; i < line.length(); i++) {
			cipher = line.split(",");

		}
		BigInteger intcipher[] = new BigInteger[cipher.length];
		char ch[] = new char[cipher.length];
		int j = 0;
		for (String str : cipher) {

			intcipher[j] = new BigInteger(str);

			// decrypting the values back to original text
			ascii_value = decrypt(intcipher[j]);

			// converting the ascii values to characters
			ch[j] = (char) (ascii_value.intValue());
			j++;

		}

		// Printing the standard output of decrypted text
		for (char plain : ch) {
			System.out.print(plain);
		}

	}

	// Function that generates key
	public void key_generation(int n) 
	{
		BigInteger p, q, bi1, bi2, Phi;

		// gets a random prime p
		p = ModularArithmetic.genPrime(n);

		// if p and q are same , q is generated again

		do {
			// gets a random prime q
			q = ModularArithmetic.genPrime(n);
		} while (p.equals(q));
		System.out.println("p is : " + p + "\n q is : " + q);

		// Calculates the value of N = p x q
		N = p.multiply(q);
		System.out.println("N is : " + N);

		bi1 = new BigInteger("1");
		bi2 = new BigInteger("-1");

		// calculates the phi(N)as Phi = (p-1) x (q-1)
		Phi = (p.subtract(bi1).multiply(q.subtract(bi1)));
		System.out.println("phi(N) is : " + Phi);

		// checks whether gcd(e,phi(N))=1 , 1 < e < phi(N)
		do {
			e = new BigInteger(n, new Random());
			if (e.gcd(Phi).equals(bi1) && e.compareTo(Phi) < 0 && !e.equals(bi1))
				break;
		} while (true);

		System.out.println("e is : " + e);

		// calculates d = e^-1 mod phi(N)
		d = ModularArithmetic.modexp(e, bi2, Phi);
		System.out.println("d is : " + d);

		this.private_key = new java.util.HashMap<String, BigInteger>();
		private_key.put("d", d);
		private_key.put("N", N);
		System.out.println("private key is : " + private_key);

		this.public_key = new java.util.HashMap<String, BigInteger>();
		public_key.put("e", e);
		public_key.put("N", N);
		System.out.println("public key is : " + public_key);
	}

	// this function will write the key_private and key_public to the given
	// filename
	public static void WriteFile(String filename, String content)
	{

		try {
			File file = new File(filename);

			// create new if file doesnt exist
			if (!file.exists()) {
				file.createNewFile();
			}

			// delete the file if it already exists
			else {
				file.delete();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);

			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static String ReadFile(String filename) 
	{

		// This will reference one line at a time
		String line = null;
		StringBuilder sb = new StringBuilder();
		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(filename);

			// Always wrap FileReader in BufferedReader.
			BufferedReader br = new BufferedReader(fileReader);

			while ((line = br.readLine()) != null) {

				sb.append(line);

			}

			// Always close files.
			br.close();
		}

		catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + filename + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + filename + "'");
			// Or we could just do this:
			// ex.printStackTrace();
		}
		String contents = sb.toString();
		// System.out.println(contents);
		return contents;

	}

}