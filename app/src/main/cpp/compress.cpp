#include <jni.h>
#include <string>
#include <malloc.h>
#include <android/bitmap.h>
#include <jpeglib.h>
#include <android/log.h>

void write_jpeg_file(uint8_t *data, int width, int height, jint quality, const char *path);

extern "C"
JNIEXPORT void JNICALL
Java_com_xaluoqone_practise_compress_NativeCompress_nativeCompress(
        JNIEnv *env,
        jobject thiz,
        jobject bitmap,
        jint quality,
        jstring save_path
) {
    // 将 Java 字符串转换为 C 字符串
    const char *path = env->GetStringUTFChars(save_path, 0);

    AndroidBitmapInfo info;
    AndroidBitmap_getInfo(env, bitmap, &info);

    uint8_t *pixels;
    AndroidBitmap_lockPixels(env, bitmap, (void **) &pixels);

    // 去掉bitmap透明度
    int width = info.width;
    int height = info.height;

    __android_log_print(ANDROID_LOG_INFO, "Native", "width = %d, height = %d", width, height);

    int color;
    uint8_t *data = (uint8_t *) malloc(width * height * 3);
    uint8_t *temp = data;
    uint8_t r, g, b;
    for (int i = 0; i < width; ++i) {
        for (int j = 0; j < height; ++j) {
            color = *(int *) pixels;
            r = (color >> 16) & 0xff;
            g = (color >> 8) & 0xff;
            b = color & 0xff;
            // jpeg 色彩通道顺序是 BGR
            *data = b;
            *(data + 1) = g;
            *(data + 2) = r;
            data += 3;
            pixels += 4;
        }
    }

    // 将压缩（去掉 alpha 信息）后的数据写入文件
    write_jpeg_file(temp, width, height, quality, path);

    free(temp);
    AndroidBitmap_unlockPixels(env, bitmap);
    // 释放之前通过 GetStringUTFChars 获取的字符串。防止内存泄漏。
    env->ReleaseStringUTFChars(save_path, path);
}

void write_jpeg_file(uint8_t *data, int width, int height, jint quality, const char *path) {
    // 1、创建 jpeg 对象
    jpeg_compress_struct jcs;
    // 设置错误回调
    jpeg_error_mgr error;
    jcs.err = jpeg_std_error(&error);
    // 创建压缩对象
    jpeg_create_compress(&jcs);
    // 2、指定存储文件
    FILE *f = fopen(path, "wb");
    jpeg_stdio_dest(&jcs, f);
    // 3、设置压缩参数
    jcs.image_width = width;
    jcs.image_height = height;
    jcs.input_components = 3;
    jcs.in_color_space = JCS_RGB;
    jpeg_set_defaults(&jcs);
    // 开启哈夫曼编码
    jcs.optimize_coding = true;
    jpeg_set_quality(&jcs, quality, true);
    // 4、开始压缩
    jpeg_start_compress(&jcs, true);
    // 5、写入数据
    int row_stride = width * 3;
    JSAMPROW row_pointer[1];
    while (jcs.next_scanline < jcs.image_height) {
        // 取一行数据
        uint8_t *row_pixels = data + jcs.next_scanline * row_stride;
        row_pointer[0] = row_pixels;
        jpeg_write_scanlines(&jcs, row_pointer, 1);
    }
    // 6、压缩完成
    jpeg_finish_compress(&jcs);
    // 7、释放 jpeg 对象
    fclose(f);
    jpeg_destroy_compress(&jcs);
}