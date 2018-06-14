function before_any_app_build() {
    "1.0", "1.1" | % {
        Push-Location Library/version$_
        & ./gradlew.bat clean assembleDebug --build-cache
        if ( $? -ne $true ) {
            exit $?
        }
        Pop-Location
    }
}
