/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpkripto;
import java.security.SecureRandom;
import java.math.BigInteger;
import java.util.Arrays;

public class BlumBlumShub implements RandomGenerator {

    // pre-compute a few values
    private static final BigInteger two = BigInteger.valueOf(2L);

    private static final BigInteger three = BigInteger.valueOf(3L);

    private static final BigInteger four = BigInteger.valueOf(4L);

    /**
     * main parameter
     */
    static BigInteger n;

    private BigInteger state;
    
    static byte[] IV = null;

    /**
     * This generates the "n value" -- the multiplication of two equally sized
     * random prime numbers -- for use in the Blum-Blum-Shub algorithm.
     *
     * @param bits
     *            The number of bits of security
     * @param rand
     *            A random instance to aid in generating primes
     * @return A BigInteger, the <i>n</i>.
     */
     public static BigInteger generateN() {
        BigInteger p =  new BigInteger("132310900031");
        BigInteger q =  new BigInteger("18531056003791");

        return p.multiply(q);
    }
    
     public BlumBlumShub() {
        this(generateN());
    }

    /**
     * A constructor to specify the "n-value" to the Blum-Blum-Shub algorithm.
     * The inital seed is computed using Java's internal "true" random number
     * generator.
     *
     * @param n
     *            The n-value.
     */
    public BlumBlumShub(BigInteger n) {
	this(n, SecureRandom.getSeed(n.bitLength() / 8));
    }

    /**
     * A constructor to specify both the n-value and the seed to the
     * Blum-Blum-Shub algorithm.
     *
     * @param n
     *            The n-value using a BigInteger
     * @param seed
     *            The seed value using a byte[] array.
     */
    public BlumBlumShub(BigInteger n, byte[] seed) {
	this.n = n;
	setSeed(seed);
    }

    /**
     * Sets or resets the seed value and internal state
     *
     * @param seedBytes
     *            The new seed.
     */
    public void setSeed(byte[] seedBytes) {
	BigInteger seed = new BigInteger("123457");
        state = seed.mod(n);
    }

    /**
     * Returns up to numBit random bits
     *
     * @return int
     */
    public int next(int numBits) {
	int result = 0;
	for (int i = numBits; i != 0; --i) {
	    state = state.modPow(two, n);
	    result = (result << 1) | (state.testBit(0) == true ? 1 : 0);
	}
	return result;
    }
    
    public static byte[] setIV() 
    {

        int bitsize = 64;
	BigInteger nval = BlumBlumShub.generateN();

	// get a seed
	byte[] seed = new byte[bitsize/8];


	// create an instance of BlumBlumShub
	BlumBlumShub bbs = new BlumBlumShub(nval, seed);
        
        byte [] ivValue = new byte [16];
	System.out.println("Generating 16 bytes");
        
	for (int i = 0; i < 16; ++i) {
            ivValue[i] = (byte) bbs.next(8);
	    System.out.println(ivValue[i]);
	}
        return BlumBlumShub.IV = (ivValue);
    }
    
        public static byte[] setIV_DES() 
    {

        int bitsize = 64;
	BigInteger nval = BlumBlumShub.generateN();

	// get a seed
	byte[] seed = new byte[bitsize/8];

	// create an instance of BlumBlumShub
	BlumBlumShub bbs = new BlumBlumShub(nval, seed);
        
        byte [] ivValue = new byte [8];
	System.out.println("Generating 8 bytes");
        
	for (int i = 0; i < 8; ++i) {
            ivValue[i] = (byte) bbs.next(8);
	    System.out.println(ivValue[i]);
	}
        return BlumBlumShub.IV = (ivValue);
    }

    
    /**
     * A quickie test application for BlumBlumShub.
     */
        
        public static void main(String[] args) {
               
	BlumBlumShub.setIV();
        System.out.println("Nilai p*q : ");
        System.out.println(n);

    }
}
