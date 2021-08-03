# Sets the minimum version of CMake required to build your native library.
# This ensures that a certain set of CMake features is available to
# your build.

cmake_minimum_required(VERSION 3.4.1)

configure_shared_stl("llvm-libc++" "c++_shared")

include_directories(src)

add_library( indy
        SHARED
        IMPORTED )

set_target_properties(
        indy
        PROPERTIES IMPORTED_LOCATION
        src/main/jniLibs/{$ANDROID_ABI}/libindy.so
)