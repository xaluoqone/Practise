cmake_minimum_required(VERSION 3.22.1)
project("compress")

# 第一个参数是生成的动态库的名字
# 第二个参数是生成的动态库的类型，SHARED 表示生成的是动态库，STATIC 表示生成的是静态库
# 第三个参数是生成的动态库的源文件，可以是多个。如果是导入的库，用 IMPORTED
add_library(${CMAKE_PROJECT_NAME} SHARED compress.cpp)

# 引入 libjpeg 动态库
add_library(libjpeg SHARED IMPORTED)
# 如果不是源代码构建，需要设置 libjpeg.so 动态库的路径
set_target_properties(libjpeg PROPERTIES IMPORTED_LOCATION ${CMAKE_CURRENT_SOURCE_DIR}/libs/libjpeg.so)

# 引入 libjpeg 头文件
include_directories(${CMAKE_CURRENT_SOURCE_DIR}/include)

target_link_libraries(${CMAKE_PROJECT_NAME}
        android
        libjpeg
        jnigraphics
        log)