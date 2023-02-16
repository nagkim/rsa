package rsamod;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author nagkim
 */
public class RSAMod {

    private static Set<Integer> prime = new TreeSet<>();
    private static int publicKey;
    private static int privateKey;
    private static int n;

    public static void main(String[] args) {
        primeFiller();
        setKeys();
        String message = "hello world";

        int[] coded = encoder(message);
        System.out.println("Initial message:");
        System.out.println(message);
        System.out.println("\nThe encoded message(encrypted by public key)");
        for (int p : coded) {
            System.out.print(p + " ");
        }
        System.out.println("\n\nThe decoded message(decrypted by private key)");
        System.out.println(decoder(coded));
    }

    private static void primeFiller() {
        boolean[] seive = new boolean[127];
        for (int i = 2; i < 127; i++) {
            if (seive[i]) {
                for (int j = i * 2; j < 127; j += i) {
                    seive[j] = false;
                }
            } else {
                seive[i] = true;
            }
        }
        for (int i = 0; i < seive.length; i++) {
            if (seive[i]) {
                prime.add(i);
            }
        }
    }

    private static int pickRandomPrime() {
        Random random = new Random();
        int k = random.nextInt(prime.size());
        int ret = 0;
        for (int p : prime) {
            if (k == 0) {
                ret = p;
                break;
            }
            k--;
        }
        prime.remove(ret);
        return ret;
    }

    private static void setKeys() {
        int prime1 = pickRandomPrime();
        int prime2 = pickRandomPrime();
        n = prime1 * prime2;
        int fi = (prime1 - 1) * (prime2 - 1);
        int e = 2;
        while (true) {
            if (gcd(e, fi) == 1) {
                break;
            }
            e++;
        }
        publicKey = e;
        int d = 2;
        while (true) {
            if ((d * e) % fi == 1) {
                break;
            }
            d++;
        }
        privateKey = d;
    }

    private static long encrypt(int message) {
        int e = publicKey;
        long encryptedText = 1;
        while (e-- > 0) {
            encryptedText *= message;
            encryptedText %= n;
        }
        return encryptedText;
    }

    private static long decrypt(int encryptedText) {
        int d = privateKey;
        long decrypted = 1;
        while (d-- > 0) {
            decrypted = (decrypted * encryptedText) % n;
        }
        return decrypted % n;
    }

    private static int[] encoder(String message) {
        int[] form = new int[message.length()];
        for (int i = 0; i < message.length(); i++) {
            form[i] = (int) encrypt((int) message.charAt(i));
        }
        return form;
    }

    private static String decoder(int[] encoded) {
        StringBuilder sb = new StringBuilder();
 

        for (int num : encoded) {

          sb.append((char) (decrypt(num) % n));
        }
        return sb.toString();
    }

    private static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);

    }
}
