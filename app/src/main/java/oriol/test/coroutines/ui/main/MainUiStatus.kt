package oriol.test.coroutines.ui.main

import oriol.test.coroutines.ui.main.model.Country

sealed class MainUiStatus {
    data class SuccessStatus(val list: List<Country>) : MainUiStatus()
    data class ErrorStatus(val message: String) : MainUiStatus()
}