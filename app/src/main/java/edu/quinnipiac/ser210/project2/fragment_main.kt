package edu.quinnipiac.ser210.project2
/**
 * FourInARow first screen
 * @author Harsh Gandhi
 * @date 2/25/2022
 */
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController

class fragment_main : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    lateinit var navController: NavController

    // this is the onCreate view method and it inflates the layout.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    // this is the onViewCreated method and it calls the super class and it sets the onClickListener to the play button.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        view.findViewById<Button>(R.id.play).setOnClickListener(this)
    }

    // this is the onClick method and it passes the name passed by the user to the gameFragment. It is required that user provides a name or else it will give a toast which says to enter the name.
    override fun onClick(v: View?){
        val name: EditText = requireView().findViewById(R.id.personName)
        if(!TextUtils.isEmpty(name.text.toString())){
            val player = name.text.toString()
            val action = fragment_mainDirections.actionFragmentMainToGameFragment(player)
            v?.findNavController()?.navigate(action)
        } else {
            Toast.makeText(context, "Please enter your name", Toast.LENGTH_SHORT).show()
        }
    }
}