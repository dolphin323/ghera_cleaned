1. **DynamicRegBroadcastReceiver-UnrestrictedAccess-Lean**
Apps that register a broadcast receiver dynamically are vulnerable to granting unrestricted access to the broadcast receiver.

2. **EmptyPendingIntent-PrivEscalation-Lean**
Apps that use a Pending Intent with base intent empty are vulnerable to leaking privilege.

3. **FragmentInjection-PrivEscalation-Lean**
Apps that use reflection to dynamically load fragments into an activity are vulnerable to fragment injection attacks.

4. **HighPriority-ActivityHijack-Lean**
Apps with low priority activity are vulnerable to Activity Hijack attacks.

5. **ImplicitPendingIntent-IntentHijack-Lean**
Apps that use a Pending Intent with implicit base intent are vulnerable to Intent Hijack attacks

6. **InadequatePathPermission-InformationExposure-Lean**
Apps that use inadequate path permission to protect a content provider providing sensitive information are vulnerable to leaking sensitive information.

7. **IncorrectImplicitIntent-UnauthorizedAccess-Lean**
Apps that have components that accept implicit intents but do not handle the implicit intents properly are vulnerable to unauthorized access.

8. **NoValidityCheckOnBroadcastMessage-UnintendedInvocation-Lean**
Apps that contain a broadcast receiver registered to receive system broadcast but does not check for validity of the broadcast message before performing corresponding operations are vulnerable to unintended invocation.

9. **OrderedBroadcast-DataInjection-Lean**
Apps that contain a broadcast receiver registered to receive ordered broadcasts are vulnerable to receive data from a malicous broadcast receiver with higher priority.

10. **StickyBroadcast-DataInjection-Lean**
Apps that send sticky broadcasts are vulnerable to leaking sensitive information and data injection attacks

11. **TaskAffinity-ActivityHijack-Lean**
Apps that allow its activities to be started in a new task are vulnerable to Activity Hijack attack.

12. **TaskAffinity-LauncherActivity-PhishingAttack-Lean**
Apps are vulnerable to phishing attacks and denial of service attacks if the device contains malicious apps that use taskAffinity.

13. **TaskAffinity-PhishingAttack-Lean**
Apps that allow its activities to be started in a new Task are vulnerable to phishing attacks

14. **TaskAffinityAndReparenting-PhishingAndDoSAttack-Lean**
Apps are vulnerable to phishing attacks and denial of serivce attacks if the device contains malicious apps.

15. **UnhandledException-DOS-Lean**
Apps that do not handle exceptions are vulnerable to Denial of Service attack.

16. **UnprotectedBroadcastRecv-PrivEscalation-(Fat|Lean)**
Apps that export components with permission to perform privileged operations are vulnerable to privilege escalation.

17. **WeakChecksOnDynamicInvoation-InformationExposure-Lean**
Apps that use the *call* method in the Content Provider API are vulnerable to exposing the underlying data store to unauthorized read and write.

**Cross List**

This sections lists the benchmarks that can be considered in this category.

1. **Permission/WeakPermission-UnauthorizedAccess-Lean**
Apps that use weak permissions to protect exported components are akin to apps that do not protect exported components at all.

2. **Web/UnsafeIntentURLImpl-InformationExposure-Lean** Apps that do not safely handle an incoming intent embedded inside a URI are vulnerable to information exposure via intent hijacking.


**Retired**

This sections lists the benchmarks that were retired from this category.

1. **ImplicitIntent-ServiceHijack-Lean**
Apps that contain a service that can be started via an implicit intent are vulnerable to Service Hijack attacks
