package cp3.lab04.crypt;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JCryptThread implements Runnable{

    JCryptUtil.Options opts;
    int index;

    public JCryptThread(JCryptUtil.Options opts, int index)
    {
        this.opts = opts;
        this.index = index;
    }
    public void run()  {
        try {
            byte[] decryptedText = null;
            if (opts.decryptionPassword.length() > 0) { // option requests file to be decrypted
                System.out.println("Decrypting " + opts.filenames[index]);
                JCryptUtil.EncryptedData encryptedText = JCryptUtil.readEncryptedFile(opts.filenames[index]);
                decryptedText = JCryptUtil.decrypt(opts.decryptionPassword, encryptedText);
            } else if (opts.crack) { // option requests file to be cracked
                System.out.println("Cracking " + opts.filenames[index]);
                JCryptUtil.EncryptedData encryptedText = JCryptUtil.readEncryptedFile(opts.filenames[index]);
                decryptedText = crack(encryptedText);
            }
            if (opts.encryptionPassword.length() > 0) { // option requests file to be encrypted
                JCryptUtil.EncryptedData encryptedText;
                if (decryptedText == null) {
                    System.out.println("Encrypting " + opts.filenames[index]);
                    byte[] buf = JCryptUtil.readRawFile(opts.filenames[index]);
                    encryptedText = JCryptUtil.encrypt(opts.encryptionPassword, buf);
                } else {
                    System.out.println("Encrypting text");
                    encryptedText = JCryptUtil.encrypt(opts.encryptionPassword, decryptedText);
                }
                if (opts.saveToFile) { // save encrypted data to file
                    JCryptUtil.writeEncryptedFile(encryptedText, new String(new File(opts.filenames[index]).getName() + ".encrypted"));
                } else { // print encrypted data to standard out
                    System.out.println(new String(encryptedText.content));
                }
            } else {
                if (opts.saveToFile) { // save decrypted data to file
                    String filename = (new File(opts.filenames[index])).getName();
                    String pathname = (new File(opts.filenames[index])).getParent();
                    if (filename.substring(filename.length() - ".encrypted".length()).equalsIgnoreCase(".encrypted")) {
                        filename = filename.substring(0, filename.length() - ".encrypted".length());
                    } else {
                        filename = filename + ".decrypted";
                    }
                    JCryptUtil.writeRawFile(decryptedText, pathname + File.separator + filename);
                } else { // print decrypted data to standard out
                    System.out.println(new String(decryptedText));
                }
            }
        } catch (JCryptUtil.Problem e) {
            e.printStackTrace();
        }
    }

    /**
     * Crack encrypted data without knowing the password.
     * Note: you are expected to implement this for Checkpoint 3.
     */
    public static byte[] crack(JCryptUtil.EncryptedData ciphertext) throws JCryptUtil.Problem {
        throw new JCryptUtil.Problem("Not implemented");
    }
}
