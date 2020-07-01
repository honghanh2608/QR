package com.nullexcom.qrscanner

import android.graphics.ImageFormat.*
import android.os.Build
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.zxing.*
import com.google.zxing.common.HybridBinarizer
import java.nio.ByteBuffer

private fun ByteBuffer.toByteArray(): ByteArray {
    rewind()
    val data = ByteArray(remaining())
    get(data)
    return data
}

class QrCodeAnalyzer(
        private val onQrCodesDetected: (qrCode: Result) -> Unit
) : ImageAnalysis.Analyzer {

    private val yuvFormats = mutableListOf(YUV_420_888)

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            yuvFormats.addAll(listOf(YUV_422_888, YUV_444_888))
        }
    }

    private val reader = MultiFormatReader().apply {
        val map = mapOf(
                DecodeHintType.POSSIBLE_FORMATS to arrayListOf(BarcodeFormat.QR_CODE)
        )
        setHints(map)
    }

    override fun analyze(image: ImageProxy) {
        // We are using YUV format because, ImageProxy internally uses ImageReader to get the image
        // by default ImageReader uses YUV format unless changed.
        if (image.format !in yuvFormats) {
            Log.e("QRCodeAnalyzer", "Expected YUV, now = ${image.format}")
            return
        }

        val width = image.width;
        val height = image.height;

        val data = image.planes[0].buffer.toByteArray()
        val array = ByteArray(data.size)
        for (i in 0 until  height) {
            for (j in 0 until width) {
                val p = data[i * width + j]
                array[j * height + i] = p;
            }
        }

        val newWidth = image.height;
        val newHeight = image.width;

        val source = PlanarYUVLuminanceSource(
                data,
                image.width,
                image.height,
                0,
                0,
                image.width,
                image.height,
                false
        )

        val source1 = PlanarYUVLuminanceSource(
                array,
                newWidth,
                newHeight,
                0,
                0,
                newWidth,
                newHeight,
                false
        )

        val binaryBitmap = BinaryBitmap(HybridBinarizer(source1))
        try {
            // Whenever reader fails to detect a QR code in image
            // it throws NotFoundException
            Log.d("QRCodeAnalyzer", "${binaryBitmap.width}x${binaryBitmap.height}")
            val result = reader.decode(binaryBitmap)
            onQrCodesDetected(result)
        } catch (e: NotFoundException) {
//            Log.d("QRCodeAnalyzer", e.message ?: "error")
        }
        image.close()
    }
}
