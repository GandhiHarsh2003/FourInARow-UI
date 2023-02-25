package edu.quinnipiac.ser210.project2
/**
 * FourInARow game fragment
 * @author Harsh Gandhi
 * @date 2/25/2022
 */
import FourInARow
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class game_fragment : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    lateinit var name: String
    lateinit var navController: NavController
    val boardButtons =  arrayOf(R.id.button00, R.id.button01, R.id.button02, R.id.button03, R.id.button04, R.id.button05, R.id.button06, R.id.button07, R.id.button08, R.id.button09, R.id.button10, R.id.button11, R.id.button12, R.id.button13, R.id.button14, R.id.button15, R.id.button16, R.id.button17, R.id.button18, R.id.button19, R.id.button20, R.id.button21, R.id.button22, R.id.button23, R.id.button24, R.id.button25, R.id.button26, R.id.button27, R.id.button28, R.id.button29, R.id.button30, R.id.button31, R.id.button32, R.id.button33, R.id.button34, R.id.button35)
    val buttonListeners = arrayListOf<Button>()
    val rotationStoring: Array<Int> = Array(boardButtons.size) { 0 }
    var FIR_board = FourInARow()

    // this is the onCreate method and it retrieves the value called playerName from the bundle and assigns it to a variable name.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if(bundle == null){
            Log.e("No name", "The name was not received")
            return
        }
        name = game_fragmentArgs.fromBundle(bundle).playerName.toString()
    }

    // This is the onCreateView method which inflates the view and also add all the buttons to the buttonListener arraylist and it also sets the on click listener for every button
    // This method also sets a background image for every button.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_game, container, false)
        for(i in 0 until boardButtons.size){
            buttonListeners.add(view.findViewById<Button>(boardButtons[i]));
        }
        for(i in 0 until buttonListeners.size){
            buttonListeners.get(i).setOnClickListener(this)
            buttonListeners.get(i).setBackgroundResource(R.drawable.starting)
        }
        return view
    }

    // This is the onView created method and it calls the super class and it also assigns the textView to the name which was inputted in previous fragment.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        view.findViewById<TextView>(R.id.name).text = name
    }

    // this is the onSavedInstanceState method and the main purpose of this method is to save the state of the game before it was flipped. It has two arrays, One to see which buttons were clicked and another to see which button was clicked by player and which by computer.
    // buttonStates array is set to false if the button inside that index is not clickable. restoringImage is just copying the rotationStoring array. The purpose of that array is to keep track of which button was clicked by player and which by computer.
    // it also stores what the winner label currently has in it right now.
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val buttonStates = BooleanArray(buttonListeners.size)
        val restoringImage = IntArray(rotationStoring.size)
        val winner: String = view?.findViewById<TextView>(R.id.declareWinner)?.text as String
        for (i in rotationStoring.indices) {
            if(rotationStoring[i] == 1){
                restoringImage[i] = 1
            }
            if(rotationStoring[i] == 2){
                restoringImage[i] = 2
            }
        }
        for (i in 0 until buttonListeners.size) {
            buttonStates[i] = buttonListeners[i].isClickable != false
        }
        outState.putString("wins", winner)
        outState.putBooleanArray("button_states", buttonStates)
        outState.putIntArray("restore_images", restoringImage)
    }

    // this is the onViewStateRestored and its main purpose is to restore the state of the game before it was rotated. So do that it gets the arguments which passed by bundles and restores them.
    // It gets the two array and the string from bundle. if restore has 1 in the index then it sets the background to face2 and passes the move. and if it is 2 then it sets the background to face1 and sets the move for computer.
    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            val buttonStates = savedInstanceState.getBooleanArray("button_states")
            val restore = savedInstanceState.getIntArray("restore_images")
            val winnerDeclare = savedInstanceState.getString("wins")
            if (buttonStates != null) {
                for (i in 0 until buttonStates.size) {
                    if(buttonStates[i] == false){
                        buttonListeners.get(i).isClickable = false
                    }
                }
            }
            if(restore != null){
                for(i in 0 until restore.size){
                    if(restore[i] == 1){
                        FIR_board.setMove(1, i)
                        buttonListeners.get(i).background = ContextCompat.getDrawable(requireContext(), R.drawable.face2)
                    }
                    if(restore[i] == 2){
                        FIR_board.setMove(2, i)
                        buttonListeners.get(i).background = ContextCompat.getDrawable(requireContext(), R.drawable.face1)
                    }
                }
            }
            val winnerText: TextView? = view?.findViewById<TextView>(R.id.declareWinner)
            winnerText?.text = winnerDeclare
        }
    }

    // this is the onClick method and it implements the logic of all of the buttons inside the board and the reset button. It checks which button was clicked and which index it is at in the arrayList and passes that index as the move.
    // It checks that if player clicked on button then it changes the background to face2 and if it is computer then it changes the button background to face1 and if there is a winner it makes every button unclickable and says who won.
    // the reset button changes to the default background of buttons and class the clear board method in FourInARow class.
    override fun onClick(v: View?) {
        val resetButton: Button? = view?.findViewById<Button>(R.id.reset)
        resetButton?.setOnClickListener{
            FIR_board.clearBoard()
            val winnerText: TextView? = view?.findViewById<TextView>(R.id.declareWinner)
            winnerText?.text = ""
            for(i in 0 until buttonListeners.size){
                buttonListeners.get(i).isClickable = true
                buttonListeners.get(i).setBackgroundResource(R.drawable.starting)
            }
            for(i in 0 until rotationStoring.size){
                rotationStoring[i] = 0
            }
        }

        for(i in 0 until buttonListeners.size){
            val clicked: Button = view?.findViewById(boardButtons[i])!!
            if(clicked == v){
                clicked.isClickable = false
                clicked.background = ContextCompat.getDrawable(requireContext(), R.drawable.face2)
                FIR_board.setMove(1, i)
                rotationStoring[i] = 1
            }
        }
        var winner: Int = FIR_board.checkForWinner()
        val winnerText: TextView? = view?.findViewById<TextView>(R.id.declareWinner)
        if(winner == GameConstants.BLUE_WON){
            winnerText?.text = "You Won!!!!!"
            unClickable()
        } else if(winner == GameConstants.RED_WON){
            winnerText?.text = "Computer Won!!!!!"
            unClickable()
        } else if(winner == GameConstants.TIE){
            winnerText?.text = "TIE!!!!"
            unClickable()
        }
        Toast.makeText(context, "Computer Move", Toast.LENGTH_SHORT).show()
        var computerMoveNumber = FIR_board.computerMove
        buttonListeners.get(computerMoveNumber).isClickable = false
        FIR_board.setMove(2, computerMoveNumber)
        println(computerMoveNumber)
        rotationStoring[computerMoveNumber] = 2
        buttonListeners[computerMoveNumber].background = ContextCompat.getDrawable(requireContext(), R.drawable.face1)
        winner = FIR_board.checkForWinner()
        if(winner == GameConstants.BLUE_WON){
            winnerText?.text = "You Won!!!!!"
            unClickable()
        } else if(winner == GameConstants.RED_WON){
            winnerText?.text = "Computer Won!!!!!"
            unClickable()
        } else if(winner == GameConstants.TIE){
            winnerText?.text = "TIE!!!!"
            unClickable()
        }
        Toast.makeText(context, "Your Move", Toast.LENGTH_SHORT).show()
    }

    // this method just makes every button unclickable so after someone has won the player can't click on anymore buttons.
    private fun unClickable(){
        for(i in 0 until buttonListeners.size){
            buttonListeners.get(i).isClickable = false
        }
    }
}