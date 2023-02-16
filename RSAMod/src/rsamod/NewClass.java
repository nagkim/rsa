
import java.math.BigInteger;
import java.util.Random;

public class NewClass {

    private BigInteger p;
    private BigInteger q;
    private BigInteger n;
    private BigInteger phi;
    private BigInteger e;
    private BigInteger d;
    private int bitlength = 1024;
    private Random r;

    public NewClass() {
        r = new Random();
        p = BigInteger.probablePrime(bitlength, r);
        q = BigInteger.probablePrime(bitlength, r);
        n = p.multiply(q);
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        e = BigInteger.probablePrime(bitlength / 2, r);
        while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0) {
            e.add(BigInteger.ONE);
        }
        d = e.modInverse(phi);
    }

    public NewClass(BigInteger e, BigInteger d, BigInteger n) {
        this.e = e;
        this.d = d;
        this.n = n;
    }

    public BigInteger[] encrypt(String message) {
        byte[] bytes = message.getBytes();
        BigInteger[] encrypted = new BigInteger[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            encrypted[i] = new BigInteger(new byte[]{bytes[i]}).modPow(e, n);
        }
        return encrypted;
    }

    public byte[] decrypt(BigInteger[] encrypted) {
        byte[] decrypted = new byte[encrypted.length];
        for (int i = 0; i < encrypted.length; i++) {
            decrypted[i] = encrypted[i].modPow(d, n).byteValue();
        }
        return decrypted;
    }

    public static void main(String[] args) {
        NewClass rsa = new NewClass();
        String message = "Hello World";
        System.out.println("Original Message: " + message);
        BigInteger[] encrypted = rsa.encrypt(message);
        System.out.println(rsa.encrypt(message));
        
        System.out.println("");
        byte[] decrypted = rsa.decrypt(encrypted);
        System.out.println("Decrypted Message: " + new String(decrypted));
    }
}
