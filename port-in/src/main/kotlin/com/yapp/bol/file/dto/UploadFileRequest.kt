package com.yapp.bol.file.dto

import com.yapp.bol.file.FilePurpose
import java.io.InputStream

data class UploadFileRequest(
    val userId: Long,
    val contentType: String,
    val content: InputStream,
    val purpose: FilePurpose,
)