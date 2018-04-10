package platform.common.utils;


import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.mortbay.util.UrlEncoded;

import com.alibaba.fastjson.JSONException;


/*-- JAVA --*/

/**
 * Is used for encrypting and decrypting Strings and JSONObjects. <br>
 * The JSON Objects can then be sent to a PHP script where they can be encrypted
 * and decrypted with the same algorithm.
 * 
 * @throws CryptingException
 */
public class Cryptor {

  private Cipher          cipher;
  private final String    secretKey   = "cn.show.hovn.www";
  private final String    iv          = "cn.show.hovn.www";
  private final String    CIPHER_MODE = "AES/CFB8/NoPadding";

  private SecretKey       keySpec;
  private IvParameterSpec ivSpec;
  private Charset         CHARSET     = Charset.forName("UTF-8");

  public Cryptor() {
    keySpec = new SecretKeySpec(secretKey.getBytes(CHARSET), "AES");
    ivSpec = new IvParameterSpec(iv.getBytes(CHARSET));
    try {
      cipher = Cipher.getInstance(CIPHER_MODE);
    }
    catch(NoSuchAlgorithmException e) {
      throw new SecurityException(e);
    }
    catch(NoSuchPaddingException e) {
      throw new SecurityException(e);
    }
  }

  /**
   * @param input
   *          A "AES/CFB8/NoPadding" encrypted String
   * @return The decrypted String
   * @throws CryptingException
   */
  public String decrypt( String input ) {
    // logger.info("Client sent request >>uriParams："+input);
    if(input == null || input.equals("")) {
      throw new IllegalArgumentException("参数为空");
    }
    try {
      cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
      return new String(
        cipher.doFinal(DatatypeConverter.parseBase64Binary(new String(input.getBytes(CHARSET), CHARSET))), CHARSET);
    }
    catch(IllegalBlockSizeException e) {
      throw new SecurityException(e);
    }
    catch(BadPaddingException e) {
      throw new SecurityException(e);
    }
    catch(InvalidKeyException e) {
      throw new SecurityException(e);
    }
    catch(InvalidAlgorithmParameterException e) {
      throw new SecurityException(e);
    }
  }

  /**
   * @param input
   *          Any String to be encrypted
   * @return An "AES/CFB8/NoPadding" encrypted String
   * @throws CryptingException
   */
  public String encrypt( String input ) {
    try {
      cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
      return DatatypeConverter.printBase64Binary(cipher.doFinal(input.getBytes(CHARSET))).trim();
    }
    catch(InvalidKeyException e) {
      throw new SecurityException(e);
    }
    catch(InvalidAlgorithmParameterException e) {
      throw new SecurityException(e);
    }
    catch(IllegalBlockSizeException e) {
      throw new SecurityException(e);
    }
    catch(BadPaddingException e) {
      throw new SecurityException(e);
    }
  }

  public static void main( String Args[] ) throws JSONException, MalformedURLException, UnsupportedEncodingException {
    /*
     * String
     * username=(String)(paramsMap.get("username")==null?null:paramsMap.get
     * ("username")[0]); String
     * tokenid=(String)(paramsMap.get("tokenid")==null?null
     * :paramsMap.get("tokenid")[0]); String
     * anchorId=(String)(paramsMap.get("anchorId"
     * )==null?null:paramsMap.get("anchorId")[0]); String
     * userId=(String)(paramsMap
     * .get("userId")==null?null:paramsMap.get("userId")[0]);
     */
    Cryptor c = new Cryptor();
    System.out.println(c.encrypt("uid=U100099&token=abc1234567890&type=3&ft=png"));;
    String city = UrlEncoded.encodeString("娜美=星", "UTF-8");
    String original =
        "token=3785c6521cd54197a5eb84017447e1ab&user_id=u100012&nickname=妹子&birthday=2012=19-10&city=" + city
            + "&sex=0";
    // String original = "username=212&password=9090";
    System.out.println("Original: " + original);
    /****
     * String
     * username=(String)(paramsMap.get("username")==null?null:paramsMap.get
     * ("username")[0]); String
     * tokenid=(String)(paramsMap.get("tokenid")==null?null
     * :paramsMap.get("tokenid")[0]); String
     * userId=(String)(paramsMap.get("userId"
     * )==null?null:paramsMap.get("userId")[0]); String
     * productId=(String)(paramsMap
     * .get("productId")==null?null:paramsMap.get("productId")[0]); Integer
     * order_type
     * =(Integer)(paramsMap.get("order_type")==null?null:Integer.valueOf
     * (paramsMap.get("order_type")[0])); String
     * version=(String)(paramsMap.get("version"
     * )==null?null:paramsMap.get("version")[0]); Integer
     * channel=(Integer)(paramsMap
     * .get("channel")==null?null:Integer.valueOf(paramsMap.get("channel")[0]));
     * Integer
     * dev_type=(Integer)(paramsMap.get("dev_type")==null?null:Integer.valueOf
     * (paramsMap.get("dev_type")[0]));
     */
  }
}
