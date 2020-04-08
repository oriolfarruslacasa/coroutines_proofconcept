package oriol.test.coroutines.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import oriol.test.coroutines.ui.main.modules.MainModule

class MainViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(MainModule.getRepository(), DefaultDispatcherProvider()) as T
    }
}