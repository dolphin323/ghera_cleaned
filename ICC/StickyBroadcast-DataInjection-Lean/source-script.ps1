function before_install_vuln() {
  #put any task that needs to be performed before installing vulnerable
}

function before_test_vuln() {
  #put any task that needs to be performed before testing vulnerable
}

function after_uninstall_vuln() {
  #put any task that needs to be performed after testing vulnerable
}

function before_install_secure() {
  if ( $strict -eq 0 ) {
      & $ANDROID_HOME\platform-tools\adb.exe reboot
      wait_for_boot_completion
  }
}

function before_test_secure() {
  #put any task that needs to be performed before testing secure
}

function after_uninstall_secure() {
  #put any task that needs to be performed after testing secure
}
