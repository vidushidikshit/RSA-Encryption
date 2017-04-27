package project1;

import java.math.BigInteger;
import java.util.Random;

public class ModularArithmetic {
	
	// Code by Vidushi Dikshit
	// c = modadd(a, b, N) : returns c = a + b (mod N)
	public static BigInteger modadd(BigInteger a, BigInteger b, BigInteger N) {
		BigInteger c = (a.add(b)).mod(N);
		return c;
	}

	// c = modmult(a, b, N): returns c = a * b (mod N)
	public static BigInteger modmult(BigInteger a, BigInteger b, BigInteger N) {
		BigInteger c = (a.multiply(b)).mod(N);
		return c;

	}

	// c = moddiv(a, b, N): returns c = a/b (mod N)
	public static BigInteger moddiv(BigInteger a, BigInteger b, BigInteger N) {
		BigInteger c = (a.divide(b)).mod(N);
		return c;
	}

	// c = modexp(a, b, N): returns c = a^b (mod N)
	public static BigInteger modexp(BigInteger a, BigInteger b, BigInteger N) {
		BigInteger c = (a.modPow(b, N));
		return c;
	}

	// b = isPrime(N, k): returns true if N is prime with probability 1/2^k, or
	// false if N is not prime.
	public static boolean isPrime(BigInteger N, int k) {
		return N.isProbablePrime((int) Math.pow(0.5, k));

	}

	// N = genPrime(n): returns a n-bit prime number N
	public static BigInteger genPrime(int n) {
		BigInteger prime;
		Random rnd = new Random();
		prime = BigInteger.probablePrime(n, rnd);
		return prime;

	}

}