package com.emma.mycollaborators20.model.restapi.response


import com.emma.mycollaborators20.model.restapi.response.Data

data class FileWebServiceResponse(
    var code: Int,
    var `data`: Data,
    var success: Boolean
)