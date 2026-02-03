package com.example.myfashion.presentasion.camera

import android.content.Context
import android.net.Uri
import java.io.File

fun copyUriToInternalStorage(
    context: Context,
    sourceUri: Uri
): String {
    val inputStream = context.contentResolver.openInputStream(sourceUri)!!

    val file = File(
        context.filesDir,
        "clothing_${System.currentTimeMillis()}.jpg"
    )

    file.outputStream().use { output ->
        inputStream.copyTo(output)
    }

    return file.absolutePath
}
