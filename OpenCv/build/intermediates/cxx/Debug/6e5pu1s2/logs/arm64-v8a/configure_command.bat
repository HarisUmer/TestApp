@echo off
"C:\\Users\\HarisUmer\\AppData\\Local\\Android\\sdk\\cmake\\3.22.1\\bin\\cmake.exe" ^
  "-HE:\\flutter\\MyApplication2\\OpenCv\\libcxx_helper" ^
  "-DCMAKE_SYSTEM_NAME=Android" ^
  "-DCMAKE_EXPORT_COMPILE_COMMANDS=ON" ^
  "-DCMAKE_SYSTEM_VERSION=28" ^
  "-DANDROID_PLATFORM=android-28" ^
  "-DANDROID_ABI=arm64-v8a" ^
  "-DCMAKE_ANDROID_ARCH_ABI=arm64-v8a" ^
  "-DANDROID_NDK=C:\\Users\\HarisUmer\\AppData\\Local\\Android\\sdk\\ndk\\26.2.11394342" ^
  "-DCMAKE_ANDROID_NDK=C:\\Users\\HarisUmer\\AppData\\Local\\Android\\sdk\\ndk\\26.2.11394342" ^
  "-DCMAKE_TOOLCHAIN_FILE=C:\\Users\\HarisUmer\\AppData\\Local\\Android\\sdk\\ndk\\26.2.11394342\\build\\cmake\\android.toolchain.cmake" ^
  "-DCMAKE_MAKE_PROGRAM=C:\\Users\\HarisUmer\\AppData\\Local\\Android\\sdk\\cmake\\3.22.1\\bin\\ninja.exe" ^
  "-DCMAKE_LIBRARY_OUTPUT_DIRECTORY=E:\\flutter\\MyApplication2\\OpenCv\\build\\intermediates\\cxx\\Debug\\6e5pu1s2\\obj\\arm64-v8a" ^
  "-DCMAKE_RUNTIME_OUTPUT_DIRECTORY=E:\\flutter\\MyApplication2\\OpenCv\\build\\intermediates\\cxx\\Debug\\6e5pu1s2\\obj\\arm64-v8a" ^
  "-DCMAKE_BUILD_TYPE=Debug" ^
  "-BE:\\flutter\\MyApplication2\\OpenCv\\.cxx\\Debug\\6e5pu1s2\\arm64-v8a" ^
  -GNinja ^
  "-DANDROID_STL=c++_shared"
