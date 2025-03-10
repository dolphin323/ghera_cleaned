1. **CheckValidity-InformationExposure-Lean**
Apps that do not check for the validity of a certificate when communicating with a server are
vulnerable to information exposure attacks

2. **IncorrectHostNameVerification-MITM-Lean** Apps that do not check for the validity of a host name when communicating with a server are vulnerable to Man-in-the-Middle (MitM) attacks

3. **InsecureSSLSocket-MITM-Lean** Apps that use the SSLCertificateSocketFactory.getSocket(InetAddress, ...) method are vulnerable to MITM attacks. 

3. **InsecureSSLSocketFactory-MITM-Lean** Apps that use the SSLCertificateSocketFactory.getInsecure() method are vulnerable to MITM attacks.

4. **InvalidCertificateAuthority-MITM-Lean** Apps that do not check for the validity of a Certificate Authority when communicating with a server are vulnerable to MitM attacks

5. **OpenSocket-InformationLeak-Lean**
Apps that communicate with a remote server over an open port are vulnerable to information leak.

6. **UnEncryptedSocketComm-MITM-Lean**
Apps that communicate with a server over TCP/IP without encryption allow Man-in-the-Middle attackers to spoof server IPs by intercepting client-server data streams.

7. **UnpinnedCertificates-MITM-Lean** Apps that do not pin the certificates of servers they trust are vulnerable to MITM attacks

**Cross List**

This sections lists the benchmarks that can be considered in this category

1. **HttpConnection-MITM-Lean**
Apps that connect to a server via the *HTTP* protocol are vulnerable to Man-in-the-Middle (MITM) attacks

**Contributors**

- Catherine Mansfield implemented *InsecureSSLSocket-MITM-Lean* and *InsecureSSLSocketFactory-MITM-Lean* benchmarks during the Summer of 2018.