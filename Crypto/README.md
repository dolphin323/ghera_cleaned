1. **BlockCipher-ECB-InformationExposure-Lean (2)**
Apps that use a block cipher algorithm in ECB mode for encrypting sensitive information are vulnerable to leaking sensitive information.

2. **BlockCipher-NonandomIV-InformationExposure-Lean**
Apps that use a block cipher algorithm in CBC mode  with a non random Initialization Vector to encrypt sensitive information are vulnerable to leaking sensitive information.

3. **PBE-ConstantSalt-InformationExposure-Lean**
Apps that us constant salt for Password-based Encryption are vulnerable to information exposure via dictionary attacks.

4. **ConstantKey-ForgeryAttack-Lean**
Apps that store the encryption key in the source code are vulnerable to leaking encrypted information.

5. **ExposedCredentials-InformationExposure-Lean**
Apps that store encryption keys without any protection parameter in a keystore accessible by other apps are vulnerable to information exposure.
