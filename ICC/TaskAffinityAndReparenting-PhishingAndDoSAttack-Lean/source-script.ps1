function before_install_vuln() {
}

function before_test_vuln() {
  #put any task that needs to be performed before testing vulnerable
}

function after_uninstall_vuln() {
  #put any task that needs to be performed after uninstalling vulnerable
}

function before_install_secure() {
  if ( $strict -eq 0 ) {
      & $ANDROID_HOME\platform-tools\adb.exe reboot
      wait_for_boot_completion
  }
  #put any task that needs to be performed before installing secure
}

function before_test_secure() {
  #put any task that needs to be performed before testing secure
}

function after_uninstall_secure() {

}
