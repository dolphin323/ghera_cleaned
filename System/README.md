1. **CheckCallingOrSelfPermission-PrivilegeEscalation-Lean**
Apps that use *checkCallingOrSelfPermission* to verify access control are vulnerable
to privilege escalation attacks.

2. **CheckPermission-PrivilegeEscalation-Lean**
Apps that use *checkPermission* to verify access control are vulnerable
to privilege escalation attacks.  

3. **ClipboardUse-InformationExposure-Lean**
Apps that allow copying of *sensitive information to clipboard* may leak information.

4. **DynamicCodeLoading-CodeInjection-Lean**
Apps that rely on dynamic code loading without verifying integrity and authenticity of the loaded code may be vulnerable to code injection.

5. **EnforceCallingOrSelfPermission-PrivilegeEscalation-Lean**
Apps that use *enforceCallingOrSelfPermission* to enforce access control are vulnerable
to privilege escalation attacks.

6. **EnforcePermission-PrivilegeEscalation-Lean**
Apps that use *enforcePermission* to enforce access control are vulnerable
to privilege escalation attacks.

7. **UniqueIDs-IdentityLeak-Lean**
Apps that use device identifiers to uniquely identify app instances are vulnerable to
exposing device-specific sensitive information.
