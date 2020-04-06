package oriol.test.coroutines.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import oriol.test.coroutines.R

class MainFragment : Fragment() {

    lateinit var message : TextView

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

        message = view!!.findViewById(R.id.message)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        showData()
    }

    private fun showData() {
        viewModel.data.observe(viewLifecycleOwner, Observer {
            var messageText = ""

            it.forEach {
                messageText+= it.name + "\b"
            }

            message.text = messageText
        })
    }

}
