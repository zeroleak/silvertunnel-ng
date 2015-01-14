package org.silvertunnel_ng.netlib.layer.tor.util;


import java.io.IOException;
import java.math.BigInteger;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.RSAPrivateCrtKeySpec;

import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.pkcs.RSAPrivateKey;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.crypto.params.RSAPrivateCrtKeyParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.KeyUtil;

/**
 * A provider representation for a RSA private key, with CRT factors included.
 */
public class TempJCERSAPrivateCrtKey
    extends TempJCERSAPrivateKey
    implements RSAPrivateCrtKey
{
    static final long serialVersionUID = 7834723820638524718L;
    
    private BigInteger  publicExponent;
    private BigInteger  primeP;
    private BigInteger  primeQ;
    private BigInteger  primeExponentP;
    private BigInteger  primeExponentQ;
    private BigInteger  crtCoefficient;

    /**
     * construct a private key from it's org.spongycastle.crypto equivalent.
     *
     * @param key the parameters object representing the private key.
     */
    TempJCERSAPrivateCrtKey(
        RSAPrivateCrtKeyParameters key)
    {
        super(key);

        this.publicExponent = key.getPublicExponent();
        this.primeP = key.getP();
        this.primeQ = key.getQ();
        this.primeExponentP = key.getDP();
        this.primeExponentQ = key.getDQ();
        this.crtCoefficient = key.getQInv();
    }

    /**
     * construct a private key from an RSAPrivateCrtKeySpec
     *
     * @param spec the spec to be used in construction.
     */
    TempJCERSAPrivateCrtKey(
        RSAPrivateCrtKeySpec spec)
    {
        this.modulus = spec.getModulus();
        this.publicExponent = spec.getPublicExponent();
        this.privateExponent = spec.getPrivateExponent();
        this.primeP = spec.getPrimeP();
        this.primeQ = spec.getPrimeQ();
        this.primeExponentP = spec.getPrimeExponentP();
        this.primeExponentQ = spec.getPrimeExponentQ();
        this.crtCoefficient = spec.getCrtCoefficient();
    }

    /**
     * construct a private key from another RSAPrivateCrtKey.
     *
     * @param key the object implementing the RSAPrivateCrtKey interface.
     */
    TempJCERSAPrivateCrtKey(
        RSAPrivateCrtKey key)
    {
        this.modulus = key.getModulus();
        this.publicExponent = key.getPublicExponent();
        this.privateExponent = key.getPrivateExponent();
        this.primeP = key.getPrimeP();
        this.primeQ = key.getPrimeQ();
        this.primeExponentP = key.getPrimeExponentP();
        this.primeExponentQ = key.getPrimeExponentQ();
        this.crtCoefficient = key.getCrtCoefficient();
    }

    /**
     * construct an RSA key from a private key info object.
     */
    TempJCERSAPrivateCrtKey(
        PrivateKeyInfo  info)
        throws IOException
    {
        this(org.spongycastle.asn1.pkcs.RSAPrivateKey.getInstance(info.parsePrivateKey()));
    }

    /**
     * construct an RSA key from a ASN.1 RSA private key object.
     */
    TempJCERSAPrivateCrtKey(
        RSAPrivateKey  key)
    {
        this.modulus = key.getModulus();
        this.publicExponent = key.getPublicExponent();
        this.privateExponent = key.getPrivateExponent();
        this.primeP = key.getPrime1();
        this.primeQ = key.getPrime2();
        this.primeExponentP = key.getExponent1();
        this.primeExponentQ = key.getExponent2();
        this.crtCoefficient = key.getCoefficient();
    }

    /**
     * return the encoding format we produce in getEncoded().
     *
     * @return the encoding format we produce in getEncoded().
     */
    public String getFormat()
    {
        return "PKCS#8";
    }

    /**
     * Return a PKCS8 representation of the key. The sequence returned
     * represents a full PrivateKeyInfo object.
     *
     * @return a PKCS8 representation of the key.
     */
    public byte[] getEncoded()
    {
        return KeyUtil.getEncodedPrivateKeyInfo(new AlgorithmIdentifier(PKCSObjectIdentifiers.rsaEncryption, DERNull.INSTANCE), new RSAPrivateKey(getModulus(), getPublicExponent(), getPrivateExponent(), getPrimeP(), getPrimeQ(), getPrimeExponentP(), getPrimeExponentQ(), getCrtCoefficient()));
    }

    /**
     * return the public exponent.
     *
     * @return the public exponent.
     */
    public BigInteger getPublicExponent()
    {
        return publicExponent;
    }

    /**
     * return the prime P.
     *
     * @return the prime P.
     */
    public BigInteger getPrimeP()
    {
        return primeP;
    }

    /**
     * return the prime Q.
     *
     * @return the prime Q.
     */
    public BigInteger getPrimeQ()
    {
        return primeQ;
    }

    /**
     * return the prime exponent for P.
     *
     * @return the prime exponent for P.
     */
    public BigInteger getPrimeExponentP()
    {
        return primeExponentP;
    }

    /**
     * return the prime exponent for Q.
     *
     * @return the prime exponent for Q.
     */
    public BigInteger getPrimeExponentQ()
    {
        return primeExponentQ;
    }

    /**
     * return the CRT coefficient.
     *
     * @return the CRT coefficient.
     */
    public BigInteger getCrtCoefficient()
    {
        return crtCoefficient;
    }

    public int hashCode()
    {
        return this.getModulus().hashCode()
               ^ this.getPublicExponent().hashCode()
               ^ this.getPrivateExponent().hashCode();
    }

    public boolean equals(Object o)
    {
        if (o == this)
        {
            return true;
        }

        if (!(o instanceof RSAPrivateCrtKey))
        {
            return false;
        }

        RSAPrivateCrtKey key = (RSAPrivateCrtKey)o;

        return this.getModulus().equals(key.getModulus())
         && this.getPublicExponent().equals(key.getPublicExponent())
         && this.getPrivateExponent().equals(key.getPrivateExponent())
         && this.getPrimeP().equals(key.getPrimeP())
         && this.getPrimeQ().equals(key.getPrimeQ())
         && this.getPrimeExponentP().equals(key.getPrimeExponentP())
         && this.getPrimeExponentQ().equals(key.getPrimeExponentQ())
         && this.getCrtCoefficient().equals(key.getCrtCoefficient());
    }

    public String toString()
    {
        StringBuffer    buf = new StringBuffer();
        String          nl = System.getProperty("line.separator");

        buf.append("RSA Private CRT Key").append(nl);
        buf.append("            modulus: ").append(this.getModulus().toString(16)).append(nl);
        buf.append("    public exponent: ").append(this.getPublicExponent().toString(16)).append(nl);
        buf.append("   private exponent: ").append(this.getPrivateExponent().toString(16)).append(nl);
        buf.append("             primeP: ").append(this.getPrimeP().toString(16)).append(nl);
        buf.append("             primeQ: ").append(this.getPrimeQ().toString(16)).append(nl);
        buf.append("     primeExponentP: ").append(this.getPrimeExponentP().toString(16)).append(nl);
        buf.append("     primeExponentQ: ").append(this.getPrimeExponentQ().toString(16)).append(nl);
        buf.append("     crtCoefficient: ").append(this.getCrtCoefficient().toString(16)).append(nl);

        return buf.toString();
    }
}
