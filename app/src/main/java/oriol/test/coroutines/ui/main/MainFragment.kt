package oriol.test.coroutines.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import oriol.test.coroutines.R
import oriol.test.coroutines.ui.main.model.Country

class MainFragment : Fragment() {

    lateinit var layout: LinearLayout

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        layout = view!!.findViewById(R.id.layout)
        viewModel = ViewModelProvider(this, MainViewModelFactory()).get(MainViewModel::class.java)
//        showDataSequentialCalls()
        showDataParallelCalls()
    }

    private fun showDataSequentialCalls() {
        viewModel.liveDataSequential.observe(viewLifecycleOwner, Observer {
            printList(it)
        })
    }

    private fun showDataParallelCalls() {
        viewModel.getVariousItems()
        viewModel.liveDataParallel.observe(viewLifecycleOwner, Observer {
            printList(it)
        })
    }

    private fun printList(list: List<Country>) {
        list.forEach {
            addText(it.name ?: "")
        }
    }

    private fun addText(text: String) {
        val textView = TextView(context)
        textView.text = text
        layout.addView(textView)
    }

}
