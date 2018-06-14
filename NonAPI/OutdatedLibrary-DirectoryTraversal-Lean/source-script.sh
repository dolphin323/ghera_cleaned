function before_any_app_build() {
    for i in "1.0" "1.1" ; do 
        pushd Library/version$i
        ./gradlew clean assembleDebug --build-cache
        if [ $? -ne 0 ] ; then
            exit $?
        fi
        popd
    done
}
