#Summary
Apps that use only path permission to protect a content provider providing sensitive information are vulnerable to leaking sensitive information.

#Versions of Android in which the vulnerability can occur
Tested on Android 5.1.1 - Android 8.1

#Description of the vulnerability and corresponding exploit
Content Providers in Android provide an interface to components through which they can interact with the underlying data store.
Components running in the same process as that of the content provider have unlimited access to its methods.
If a content provider is *exported* then components running in processes other than its own also have unlimited access to its methods.
However, a content provider can limit access to components running in processes other than its own via *permissions*.
A permission can be declared to limit access to all of the data provided by the content provider or to a subset of the data provided by the content provider.
The content provider can do the latter by specifying <path-permission android:readPermission="some.string.identifier" android:path="some/path">
or <path-permission android:readPermission="some.string.identifier" android:pathPrefix="some/prefix"> or <path-permission android:readPermission="some.string.identifier" android:pathPattern="some/pattern">.

1.	android:path means that the permission applies to the exact path declared in android:path
2.	android:pathPrefix means that the permission applies to any path beginning with the value declared in pathPrefix.
3.	android:pathPattern means that the permission applies to any pattern that matches the value in android:pathPattern. android:pathPattern accepts two wildcard characters, "\*" i.e. sequence of 0 to many occurrences of the immediately preceding character, and ".\*" i.e. sequence of 0 or more characters.

*Issue:* **path-permission** does not protect an app's data. If a content provider is exported then a malicious component can access the content provider to get access to the data protected by path-permission without requesting for any kind of permission.   

*Example:* This vulnerability is demonstrated by *Benign* and *Malicious*. The *AndroidManifest.xml* file in *Benign/app/src/main* defines a content provider called *UserDetailsContentProvider* in the <provider> tag, which is exported. The content provider protects the path prefix */user* with a signature-level permission, as specified in the *<path-permission>* tag. Signature-level permissions are granted to only apps that  share the same signature. Requests by apps that do not share the same signature will be disallowed by the system. However, when *UserDetailsContentProvider* is queried from *Malicious/.../MalActivity* for data stored in */user/ssn*, *UserDetailsContentProvider* returns the data in spite of *Malicious* not having requested the necessary *path-permission*.

# Steps to build the sample apps and to exploit the vulnerability

1. List targets:

    `$ avdmanager list targets`

2. List available Android Virtual Devices:

    `$ avdmanager list avd`

3. Create an emulator:

    `$ avdmanager create avd -n <name> -k <target>`

    *<target>* is obtained from the command listed in 1. *<name>* is the name you choose to give to the avd.

4. Start emulator:

    `$ emulator -avd <avd_name>`

    *<avd-name>* is obtained from the command listed in 2.

5. Build and install *Benign*:

    `$ cd Benign`

    Make sure there is a file called local.properties with an entry sdk=/path/to/Android/sdk.

    `$ ./gradlew installApi[VERSION]Debug`

6. Build and install *Malicious*:

    `$ cd Malicious`

    Make sure there is a file called local.properties with an entry sdk=/path/to/Android/sdk.

    `$ ./gradlew installApi[VERSION]Debug`

7. Open *Benign*.

    Enter the number 1 in place of ID

    Click "Get SSN" button. You should see a number displayed at the bottom of the screen.

8. Open *Malicious* in the emulator and click the button "Get SSN".

9. You will see a number appear in the display. This is the same number as the one displayed in *Benign*.

# References

1.  [Official Android Documentation](https://developer.android.com/guide/topics/providers/content-provider-creating.html)

2.  [Inspecting Android Apps with Drozer](https://blog.abstractj.org/articles/inspecting-android-apps-drozer.html)
